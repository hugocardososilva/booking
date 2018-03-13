package conexao.com.rn;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.mail.EmailException;

import conexao.com.dao.CadastroBLDAO;
import conexao.com.dao.CadastroBLRiscoDAO;
import conexao.com.util.JSFUtil;
import seguranca.com.entidade.CadastroBL;
import seguranca.com.entidade.CadastroBLContanier;
import seguranca.com.entidade.CadastroBLContanierLCL;
import seguranca.com.entidade.CadastroBLRiscoFitossanitario;
import seguranca.com.entidade.CadastroBlLogAlteracao;
import seguranca.com.entidade.User;
import seguranca.com.enums.Role;
import seguranca.com.enums.StatusBLEnum;

public class CadastroBLRN {

	private CadastroBLDAO cadBLDAO;
	private CadastroBLLogRN logRN;
	private JSFUtil email;
	private UserComissarioRN userComissarioRN;
	private CadastroBLRiscoDAO riscoDAO;

	public CadastroBLDAO getCadastroBLDAO() {
		if (cadBLDAO == null) {
			cadBLDAO = new CadastroBLDAO();
		}
		return cadBLDAO;
	}

	public CadastroBL incluir(CadastroBL item) throws Exception {
		getCadastroBLDAO().beginTransaction();
		setarInformacoesBL(item);
		getCadastroBLDAO().save(item);
		getCadastroBLDAO().commitAndCloseTransaction();

		return localizar(item.getId());
	}

	public CadastroBL incluirSemFecharTransacao(CadastroBL item) throws Exception {
		getCadastroBLDAO().beginTransaction();

		setarInformacoesBL(item);

		CadastroBL bl = getCadastroBLDAO().alterar(item);
		getCadastroBLDAO().commit();
		return bl;
	}

	private void setarInformacoesBL(CadastroBL item) {
		if (item.getId() == null || item.getId() == 0) {
			item.setDataCadastro(new Date());
			item.setStatusBLEnum(StatusBLEnum.PENDENTE_ANEXO);
		}
		item.setDescricaoBL(item.getDescricaoBL().toUpperCase());
		if (item.getDescricaoMercadoria() != null) {
			item.setDescricaoMercadoria(item.getDescricaoMercadoria().toUpperCase());
		}
	}

	public void fecharConexaoBL() {
		getCadastroBLDAO().closeTransaction();
	}

	public CadastroBL alterarStatus(CadastroBL item, User user) throws Exception {
		boolean alteradoDescricaoMercadoria = item.isAlteracaoDescricaoMercadoria();
		boolean alteradoInspecao = item.isAlteradoInspecao();
		boolean alteradoLiberado = item.isAlteradoLiberado();

		getCadastroBLDAO().beginTransaction();
		item = getCadastroBLDAO().alterar(item);
		getCadastroBLDAO().commit();

		CadastroBlLogAlteracao log = getLogRN().getInstanciarLog(null, item, user);
		log.setDataAlteracao(new Date());
		if (alteradoDescricaoMercadoria) {
			log.setDescricaoMercadoria(true);
		}
		if (alteradoInspecao) {
			log.setInspecao(true);
		}
		if (alteradoLiberado) {
			log.setLiberado(true);
		}
		log.setCadastroBL(item);
		getLogRN().alterar(log);

		getLogRN().fecharConexaoLog();
		fecharConexaoBL();

		if (item.getStatusBLEnum() == StatusBLEnum.LIBERADO_E_VISTORIADO
				|| item.getStatusBLEnum() == StatusBLEnum.LIBERADO_SEM_VISTORIA) {
			enviarEmail(item, Role.CLIENTE);
		}

		return item;
	}

	public CadastroBL salvar(CadastroBL item) throws Exception {
		getCadastroBLDAO().beginTransaction();
		item = getCadastroBLDAO().alterar(item);
		getCadastroBLDAO().commitAndCloseTransaction();
		return item;
	}
	public CadastroBL adicionarRiscoFitossanitario(CadastroBL item) throws Exception {
		//TODO pega a ultima bl do importador
		//TODO copia o risco fitossanitario da ultima bl
		try {
		  
		getRiscoDAO().beginTransaction();
		CadastroBLRiscoFitossanitario ultRisco = getRiscoDAO().getUltimoRiscoPorNcm(item);
		
		
		
		if(ultRisco != null) {
			

		if(!ultRisco.getCadastroBL().getId().equals(item.getId())){
			
			CadastroBLRiscoFitossanitario risco = new CadastroBLRiscoFitossanitario();
			risco.setCadastroBL(item);
			risco.setDataCadastro(new Date());
			risco.setUser(ultRisco.getUser());
			risco.setRiscoFitossanitarioEnum(ultRisco.getRiscoFitossanitarioEnum());
			item.addRisco(risco);
			//getRiscoDAO().alterar(risco);
			//getRiscoDAO().commitAndCloseTransaction();
			
			}
		}else {
			getRiscoDAO().closeTransaction();
		}
		
		
		
		}finally {
			
		}
		return item;
	
	}

	public CadastroBL alterar(CadastroBL item, User user) throws Exception {
		boolean alteradoDescricaoMercadoria = item.isAlteracaoDescricaoMercadoria();
		boolean alteradoInspecao = item.isAlteradoInspecao();
		boolean alteradoLiberado = item.isAlteradoLiberado();
		getCadastroBLDAO().beginTransaction();
		if (item.getId() == null || item.getId() == 0) {
			item.setStatusBLEnum(StatusBLEnum.PENDENTE_ANEXO);			
			item.setDataCadastro(new Date());
		}
		item.setDescricaoBL(item.getDescricaoBL().toUpperCase());
		if (item.getDescricaoMercadoria() != null) {
			item.setDescricaoMercadoria(item.getDescricaoMercadoria().toUpperCase());
		}
		
		//DONE adiciona o risco na lista antes de salvar
		item = adicionarRiscoFitossanitario(item);
		item = getCadastroBLDAO().alterar(item);
		getCadastroBLDAO().commit();

		CadastroBlLogAlteracao log = getLogRN().getInstanciarLog(null, item, user);
		log.setDataAlteracao(new Date());
		if (alteradoDescricaoMercadoria) {
			log.setDescricaoMercadoria(true);
		}
		if (alteradoInspecao) {
			log.setInspecao(true);
		}
		if (alteradoLiberado) {
			log.setLiberado(true);
		}
		log.setCadastroBL(item);
		getLogRN().alterar(log);

		getCadastroBLDAO().closeTransaction();

		if (item.getStatusBLEnum() == StatusBLEnum.PENDENTE_ANEXO
				|| item.getStatusBLEnum() == StatusBLEnum.AGUARDANDO_LIBERACAO_PROCESSOS_QUE_ANEXARAM
				|| item.getStatusBLEnum() == StatusBLEnum.LIBERADO_E_VISTORIADO
				|| item.getStatusBLEnum() == StatusBLEnum.LIBERADO_SEM_VISTORIA
				|| item.getStatusBLEnum() == StatusBLEnum.SEPARADO_PARA_VISTORIA) {
			
			enviarEmail(item, Role.CLIENTE);
			
		}

		return item;
	}

	private CadastroBLLogRN getLogRN() {
		if (logRN == null) {
			logRN = new CadastroBLLogRN();
		}
		return logRN;
	}

	public CadastroBL localizar(int itemId) {
		getCadastroBLDAO().beginTransaction();
		CadastroBL item = getCadastroBLDAO().localizarPorID(itemId);
		// getCadastroBLDAO().closeTransaction();
		return item;
	}

	public CadastroBL localizarNaMesmaTransacao(int itemId) {
		getCadastroBLDAO().beginTransaction();
		CadastroBL item = getCadastroBLDAO().localizarPorID(itemId);
		getCadastroBLDAO().closeTransaction();
		return item;
	}

	public void deletar(CadastroBL item) {
		getCadastroBLDAO().beginTransaction();
		getCadastroBLDAO().delete(item);
		getCadastroBLDAO().commitAndCloseTransaction();
	}

	public List<CadastroBL> listaTodos() {
		getCadastroBLDAO().beginTransaction();
		List<CadastroBL> result = getCadastroBLDAO().listaTodosRegistros();
		getCadastroBLDAO().closeTransaction();
		return result;
	}

	public void updateAtualizaDados(CadastroBLContanier entidade) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		// parameters.put(CadastroBLContanier.CAMPO_INSPECAO,
		// entidade.isInspecao());
		// parameters.put(CadastroBLContanier.CAMPO_LIBERADO,
		// entidade.isLiberado());
		parameters.put(CadastroBL.CADASTRO_BL_ID, entidade.getCadastroBL().getId());
		parameters.put(CadastroBL.CADASTRO_STATUS, entidade.getStatusBLEnum());

		getCadastroBLDAO().beginTransaction();

		getCadastroBLDAO().atualizaDadosUpdate(parameters);

		getCadastroBLDAO().commitAndCloseTransaction();
	}

	private UserComissarioRN getUserComissarioRN() {
		if (userComissarioRN == null) {
			userComissarioRN = new UserComissarioRN();
		}
		return userComissarioRN;
	}

	public void enviarEmail(CadastroBL entidade, Role permissao) throws EmailException, IOException {
		getJsfUtil().envioEmailSimples(new StringTokenizer(entidade.getUserCadastroDtc().getEmail(), ";"),
				"Portal MAPA CLIF - BL: " + entidade.getDescricaoBL(),
				"BL: " + entidade.getDescricaoBL() + " - Status ->> " + entidade.getStatusBLEnum().getDescricao());
		

		// List<UserComissario> listaUsersComissarias = getUserComissarioRN()
		// .listaTodosUsuariosComissarias(entidade.getCadComissario());
		//
		// for (UserComissario item : listaUsersComissarias) {
		// if (item.getUsuario().isReceberEmail() && item.getUsuario().getRole()
		// == Role.CLIENTE) {
		// getJsfUtil().envioEmailSimples(item.getUsuario().getEmail(),
		// "Portal MAPA CLIF - BL: " + entidade.getDescricaoBL(), "BL: " +
		// entidade.getDescricaoBL()
		// + " - Status ->> " + entidade.getStatusBLEnum().getDescricao());
		// }
		// }
	}

	private JSFUtil getJsfUtil() {
		if (email == null) {
			email = new JSFUtil();
		}
		return email;
	}

	public float retornaQtdeRegistros(CadastroBLContanierLCL entidade, int tipoSql) {
		float retorno = 0;
		String sql = null;

		Map<String, Object> parameters = new HashMap<String, Object>();
		if (tipoSql == 1) {
			sql = CadastroBL.SQL_COUNT_BLS;
		}
		if (tipoSql == 2) {
			sql = CadastroBL.SQL_SOMA_MERCADORIA;
		}
		if (tipoSql == 3) {
			sql = CadastroBL.SQL_SOMA_VOLUME;
		}
		parameters.put(CadastroBL.CAMPO_CONTAINERLCL, entidade.getId());

		getCadastroBLDAO().beginTransaction();

		retorno = getCadastroBLDAO().executaQtdeRegistros(sql, parameters);

		getCadastroBLDAO().closeTransaction();

		return retorno;
	}

	public CadastroBLRiscoDAO getRiscoDAO() {
		if(riscoDAO == null) riscoDAO = new CadastroBLRiscoDAO();
		return riscoDAO;
	}
	

}