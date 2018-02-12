package conexao.com.rn;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import conexao.com.dao.CadastroBLContainerDAO;
import conexao.com.util.JSFUtil;
import seguranca.com.entidade.CadastroBL;
import seguranca.com.entidade.CadastroBLContanier;
import seguranca.com.entidade.CadastroBLContanierLCL;
import seguranca.com.entidade.CadastroBlLogAlteracao;
import seguranca.com.entidade.User;
import seguranca.com.enums.TabelaContainerEnum;
import seguranca.com.enums.TipoSimNaoEnum;

public class CadastroBLContanierRN {

	private CadastroBLContainerDAO cadBLContanierDAO;
	private CadastroBLLogRN logRN;
	private JSFUtil jsfUtil;

	public CadastroBLLogRN getLogRN() {
		if (logRN == null) {
			logRN = new CadastroBLLogRN();
		}
		return logRN;
	}

	public CadastroBLContainerDAO getDAO() {
		if (cadBLContanierDAO == null) {
			cadBLContanierDAO = new CadastroBLContainerDAO();
		}
		return cadBLContanierDAO;
	}

	public CadastroBLContanier incluir(CadastroBLContanier item) throws Exception {
		getDAO().beginTransaction();
		if (item.getNumeroContanier() != null) {
			item.setNumeroContanier(item.getNumeroContanier().toUpperCase());
		}

		if (item.getNumeroLacre() != null) {
			item.setNumeroLacre(item.getNumeroLacre().toUpperCase());
		}
		try {
			getDAO().save(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		if (item.getCargaImo() == TipoSimNaoEnum.SIM) {
			String assunto = null;
			String corpoEmail = null;

			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");

			String dataEta = "";
			String navio = "";
			if (item.getCadastroBL().getProgramacaoNavio() != null) {
				if (item.getCadastroBL().getProgramacaoNavio().getDataETA() != null) {
					dataEta = sdf.format(item.getCadastroBL().getProgramacaoNavio().getDataETA());
				}
				navio = item.getCadastroBL().getProgramacaoNavio().getNavio();
			}

			assunto = "Agendamento ATI CLIF - BL - IMO: " + item.getCadastroBL().getDescricaoBL();
			corpoEmail = "ATI: " + item.getCadastroBL().getNumeroATI() + " - Efetuado Cadastro da ATI ->> "
					+ item.getCadastroBL().getNumeroATI() + " Navio ->> " + navio + " ETA ->> " + dataEta
					+ " Cliente ->> " + item.getCadastroBL().getCadComissario().getNome();

			getJsfUtil().envioEmailSimples(new StringTokenizer("imo@oclif.com.br", ";"), assunto, corpoEmail);
		}

		getDAO().commitAndCloseTransaction();

		return localizar(item.getId());
	}

	private JSFUtil getJsfUtil() {
		if (jsfUtil == null) {
			jsfUtil = new JSFUtil();
		}
		return jsfUtil;
	}

	public void salvarListaContanier(CadastroBL cadBL, List<CadastroBLContanier> listaContanier) throws Exception {
		if (cadBL.getId() > 0) {
			for (CadastroBLContanier item : listaContanier) {
				if (item.getId() == null || item.getId() == 0) {
					item.setCadastroBL(cadBL);
					item.setStatusBLEnum(cadBL.getStatusBLEnum());
					getDAO().save(item);
				}
			}
		}
	}

	public CadastroBLContanier alterarStatus(CadastroBLContanier item, User user, CadastroBLContanier itemAntigo)
			throws Exception {
		getDAO().beginTransaction();
		item = getDAO().alterar(item);
		getDAO().commit();
		return item;
	}

	public CadastroBLContanier alterar(CadastroBLContanier item, User user, CadastroBLContanier itemAntigo)
			throws Exception {
		getDAO().beginTransaction();

		if (item.getNumeroContanier() != null) {
			item.setNumeroContanier(item.getNumeroContanier().toUpperCase());
		}

		if (item.getNumeroLacre() != null) {
			item.setNumeroLacre(item.getNumeroLacre().toUpperCase());
		}

		item = getDAO().alterar(item);
		getDAO().commit();

		CadastroBlLogAlteracao log = new CadastroBlLogAlteracao();
		log.setCadastroBLContanier(item);
		log.setCadastroBL(item.getCadastroBL());
		log.setDataAlteracao(new Date());

		if (itemAntigo != null) {
			if (itemAntigo.isInspecao() != item.isInspecao()) {
				log.setInspecao(item.isInspecao());
			}
			if (itemAntigo.isLiberado() != item.isLiberado()) {
				log.setLiberado(item.isLiberado());
			}
		}

		if (user != null) {
			log.setUser(user);
		}
		try {
			getLogRN().alterar(log);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		getDAO().closeTransaction();

		return item;
	}

	public CadastroBLContanier localizar(int itemId) {
		getDAO().beginTransaction();
		CadastroBLContanier item = getDAO().localizarPorID(itemId);
		getDAO().closeTransaction();
		return item;
	}

	public void deletar(CadastroBLContanier item) {
		getDAO().beginTransaction();
		getDAO().deletaRegistrosObjeto(CadastroBLContanier.FILTRO_DELETE_REGISTRO, CadastroBLContanier.BL_CONTAINER_ID,
				item.getId().toString());
		getDAO().commitAndCloseTransaction();
	}

	public List<CadastroBLContanier> listaTodos() {
		getDAO().beginTransaction();
		List<CadastroBLContanier> result = getDAO().listaTodosRegistros();
		getDAO().closeTransaction();
		return result;
	}

	public void updateAtualizaDados(CadastroBL entidade) throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroBLContanier.CAMPO_INSPECAO, entidade.isInspecao());
		parameters.put(CadastroBLContanier.CAMPO_LIBERADO, entidade.isLiberado());
		parameters.put(CadastroBLContanier.CAMPO_CADASTROBL, entidade.getId());
		parameters.put(CadastroBLContanier.CAMPO_STATUSBLENUM, entidade.getStatusBLEnum());

		getDAO().beginTransaction();

		try {
			getDAO().atualizaDadosUpdate(parameters);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		getDAO().commitAndCloseTransaction();
	}

	public void updateAtualizaDadosLiberadoSemVistoria(CadastroBL entidade) throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroBLContanier.CAMPO_CADASTROBL, entidade.getId());

		getDAO().beginTransaction();

		try {
			getDAO().atualizaDadosUpdateLiberadoSemVistoria(parameters);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

		getDAO().commit();
	}

	public void updateAtualizaDadosLCL(CadastroBLContanierLCL entidade) throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroBLContanier.CAMPO_NUMEROCONTANIER, entidade.getNumeroContanier());
		parameters.put(CadastroBLContanier.CAMPO_NUMEROLACRE, entidade.getNumeroLacre());
		parameters.put(CadastroBLContanier.CAMPO_CONTAINERLCL, entidade.getId());

		getDAO().beginTransaction();

		try {
			getDAO().atualizaDadosUpdateLCL(parameters);
		} catch (Exception e) {
			getDAO().rollback();
			throw new Exception(e.getMessage());
		}

		getDAO().commitAndCloseTransaction();
	}

	public int executaQtdeRegistros(String sql, CadastroBLContanier entidade, int qtdeParametros) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (qtdeParametros == 1) {
			parameters.put(CadastroBLContanier.CAMPO_CADASTROBL, entidade.getCadastroBL().getId());
		}
		if (qtdeParametros == 2) {
			parameters.put(CadastroBLContanier.CAMPO_CADASTROBL, entidade.getCadastroBL().getId());
			parameters.put(CadastroBLContanier.CAMPO_STATUSBLENUM, entidade.getStatusBLEnum());
		}

		getDAO().beginTransaction();

		int retorno = getDAO().executaQtdeRegistros(sql, parameters);

		getDAO().closeTransaction();
		return retorno;
	}

	public int executaQtdeRegistros(String sql, CadastroBL entidade, int qtdeParametros) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (qtdeParametros == 1) {
			parameters.put(CadastroBLContanier.CAMPO_CADASTROBL, entidade.getId());
		}
		if (qtdeParametros == 2) {
			parameters.put(CadastroBLContanier.CAMPO_CADASTROBL, entidade.getId());
			parameters.put(CadastroBLContanier.CAMPO_STATUSBLENUM, entidade.getStatusBLEnum());
		}

		getDAO().beginTransaction();

		int retorno = getDAO().executaQtdeRegistros(sql, parameters);

		// getDAO().closeTransaction();
		return retorno;
	}

	public int executaQtdeRegistrosInspecao(CadastroBL entidade) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroBLContanier.CAMPO_CADASTROBL, entidade.getId());

		getDAO().beginTransaction();

		int retorno = getDAO().executaQtdeRegistros(CadastroBLContanier.sqlCountTotalInspecao, parameters);

		// getDAO().closeTransaction();
		return retorno;
	}

	public void fecharConexao() {
		getDAO().closeTransaction();
	}

	public int executaQtdeRegistrosLiberado(CadastroBL entidade) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroBLContanier.CAMPO_CADASTROBL, entidade.getId());

		getDAO().beginTransaction();

		int retorno = getDAO().executaQtdeRegistros(CadastroBLContanier.sqlCountTotalLiberado, parameters);

		// getDAO().closeTransaction();
		return retorno;
	}

	/**
	 * Efetua a validação do numero do container a regra foi utilizado, para
	 * validação por este link ( site -->>
	 * https://es.wikipedia.org/wiki/Contenedor //
	 * http://www.informatic.cl/valida_numero_contenedor.asp)
	 * 
	 * @param identificacao
	 * @return boolean
	 */
	public boolean validarContanier(String identificacao) {
		identificacao = identificacao.replaceAll(" ", "");

		boolean valida = true;

		TabelaContainerEnum rt0 = TabelaContainerEnum.getPesquisaTabela(identificacao.substring(0, 1));
		TabelaContainerEnum rt1 = TabelaContainerEnum.getPesquisaTabela(identificacao.substring(1, 2));
		TabelaContainerEnum rt2 = TabelaContainerEnum.getPesquisaTabela(identificacao.substring(2, 3));
		TabelaContainerEnum rt3 = TabelaContainerEnum.getPesquisaTabela(identificacao.substring(3, 4));

		int rt4 = Integer.valueOf(identificacao.substring(4, 5));
		int rt5 = Integer.valueOf(identificacao.substring(5, 6));
		int rt6 = Integer.valueOf(identificacao.substring(6, 7));
		int rt7 = Integer.valueOf(identificacao.substring(7, 8));
		int rt8 = Integer.valueOf(identificacao.substring(8, 9));
		int rt9 = Integer.valueOf(identificacao.substring(9, 10));
		int rtDigito = Integer.valueOf(identificacao.substring(10, 11));

		if (rt0 != null || rt1 != null || rt2 != null || rt3 != null) {
			float multiplicadoPor1 = rt0.getCodigo() * 1;
			float multiplicadoPor2 = rt1.getCodigo() * 2;
			float multiplicadoPor4 = rt2.getCodigo() * 4;
			float multiplicadoPor8 = rt3.getCodigo() * 8;
			float multiplicadoPor16 = rt4 * 16;
			float multiplicadoPor32 = rt5 * 32;
			float multiplicadoPor64 = rt6 * 64;
			float multiplicadoPor128 = rt7 * 128;
			float multiplicadoPor256 = rt8 * 256;
			float multiplicadoPor512 = rt9 * 512;

			float somaResultado = multiplicadoPor1 + multiplicadoPor2 + multiplicadoPor4 + multiplicadoPor8
					+ multiplicadoPor16 + multiplicadoPor32 + multiplicadoPor64 + multiplicadoPor128
					+ multiplicadoPor256 + multiplicadoPor512;

			float resultadoDivididoPorOnze = somaResultado / 11;

			int resultadoDivididoPorOnzeInteiro = (int) resultadoDivididoPorOnze;

			float resultadoMultiplicado11 = resultadoDivididoPorOnzeInteiro * 11;

			float digito = somaResultado - resultadoMultiplicado11;

			int digitoEncontrado = (int) digito;

			if (rtDigito != digitoEncontrado) {
				valida = false;
			}
			if (digitoEncontrado == 10) {
				valida = true;
			}
		}
		return valida;
	}

}