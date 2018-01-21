package com.rn;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.mail.EmailException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Row;

import com.dao.CadastroDTCDTADAO;
import com.entidade.CadastroDTCDTA;
import com.entidade.UserImportadorComissaria;
import com.entidade.UserImportadorUsuarios;
import com.enums.TipoPendenciaDocumentalEmailEnum;

import conexao.com.enums.TipoFiltroEnum;
import conexao.com.rn.CadastroBLAnexosRN;
import conexao.com.rn.CadastroBLContanierLCLRN;
import conexao.com.rn.CadastroBLContanierRN;
import conexao.com.rn.CadastroBLDescricaoMercadoriaRN;
import conexao.com.rn.CadastroBLRN;
import conexao.com.rn.CadastroBLRiscoRN;
import conexao.com.rn.CadastroFaturarContraRN;
import conexao.com.rn.ProgramacaoNavioRN;
import conexao.com.rn.UserComissarioRN;
import conexao.com.rn.UserFacade;
import conexao.com.rn.UserImportadorRN;
import conexao.com.util.FiltroTabelaBean;
import conexao.com.util.JSFUtil;
import seguranca.com.entidade.CadastroBL;
import seguranca.com.entidade.CadastroBLContanier;
import seguranca.com.entidade.CadastroBLContanierLCL;
import seguranca.com.entidade.CadastroBLDescricaoMercadoria;
import seguranca.com.entidade.CadastroBLRiscoFitossanitario;
import seguranca.com.entidade.CadastroComissario;
import seguranca.com.entidade.MapaNcm;
import seguranca.com.entidade.ProgramacaoNavio;
import seguranca.com.entidade.User;
import seguranca.com.entidade.UserComissario;
import seguranca.com.enums.ModalidadeBLEnum;
import seguranca.com.enums.Role;
import seguranca.com.enums.StatusAtiLclFclEnum;
import seguranca.com.enums.StatusBLEnum;
import seguranca.com.enums.TipoAnexosEnum;
import seguranca.com.enums.TipoSimNaoEnum;
import seguranca.com.enums.TipomodalEnum;

public class CadastroDTCDTARN implements Serializable {

	private static final long serialVersionUID = 1L;

	private CadastroDTCDTADAO dao;

	private CadastroBLRN cadastroBLRN;
	private CadastroBLAnexosRN anexosRN;
	private CadastroBLContanierRN contanierRN;
	private CadastroBLContanierLCLRN contanierLCLRN;
	private ProgramacaoNavioRN programacaoNavioRN;
	private CadastroFaturarContraRN faturarContraRN;
	private UserImportadorRN userImportadorRN;
	private UserImportadorUsuariosRN importadorUsuariosRN;
	private UserImportadorComissariaRN importadorComissariaRN;
	private UserComissarioRN userComissarioRN;
	private UserFacade userRN;

	private JSFUtil email;

	private CadastroBLRiscoRN riscoRN;

	private CadastroBLDescricaoMercadoriaRN mercadoriaRN;

	private UserImportadorComissariaRN getImportadorComissariaRN() {
		if (importadorComissariaRN == null) {
			importadorComissariaRN = new UserImportadorComissariaRN();
		}
		return importadorComissariaRN;
	}

	public CadastroDTCDTADAO getDAO() {
		if (dao == null) {
			dao = new CadastroDTCDTADAO();
		}
		return dao;
	}

	private CadastroBLAnexosRN getAnexosRN() {
		if (anexosRN == null) {
			anexosRN = new CadastroBLAnexosRN();
		}

		return anexosRN;
	}

	public CadastroDTCDTA incluir(CadastroDTCDTA item) throws Exception {
		getDAO().beginTransaction();
		try {
			item = getDAO().alterar(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();

		return item;
	}

	public CadastroDTCDTA alterarSemRelacionamento(CadastroDTCDTA item) throws Exception {
		getDAO().beginTransaction();
		try {
			item.setDescricaoBL(item.getDescricaoBL().replace("/", ""));

			item = getDAO().alterar(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();

		return item;
	}

	/** Limpa as mascaras que vier para validar os caracteres informado. */
	private static String limparMascaras(String valor) {
		valor = valor.replace(".", "");
		valor = valor.replace("-", "");
		valor = valor.replace("/", "");
		return valor.trim();
	}

	private String removerAcentosMascarasCampoVazio(String str) {
		if (str != null && !str.equals("")) {
			return Normalizer.normalize(limparMascaras(str), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		}

		return null;
	}

	private ProgramacaoNavioRN getProgramacaoNavioRN() {
		if (programacaoNavioRN == null) {
			programacaoNavioRN = new ProgramacaoNavioRN();
		}
		return programacaoNavioRN;
	}

	public CadastroDTCDTA alterarSomenteEntidadeDtcDta(CadastroDTCDTA item) throws Exception {
		getDAO().beginTransaction();
		item = getDAO().alterar(item);
		getDAO().commitAndCloseTransaction();
		return item;
	}

	public CadastroDTCDTA alterar(CadastroDTCDTA item, List<MapaNcm> listaMapaNCM) throws Exception {
		boolean envioAlteracaoStatusClif = false;
		boolean envioEmailPrimeiroCadastro = false;
		boolean envioMapaValidar = item.isEnvioMapaValidar();
		boolean envioEmailAlteracaoNavio = false;
		ProgramacaoNavio navioAntigo = null;
		User userLogado = item.getUserLogado();

		if (item.isEnviarParaMapa() && envioMapaValidar) {

			if (item.getTipoModalEnum() != TipomodalEnum.DTC
					&& (userLogado.isDespachante() || userLogado.isCliente())) {
				item.setEnviarParaMapa(false);
				item.setStatusBLEnum(StatusBLEnum.PENDENTE_ANEXO);
				item.getCadastroBL().setStatusBLEnum(StatusBLEnum.PENDENTE_ANEXO);
				throw new Exception("Esse processo sera enviado pelo CLIF apos validação do dados");
			}

			int qtdeRegistrosContainer = item.getCadastroBL().getListaCadastroBLContanier().size();
			float qtdeReg = getAnexosRN().executarQtdeRegistrosValor(item.getCadastroBL(), TipoAnexosEnum.BL);

			if (qtdeReg == 0 || (qtdeRegistrosContainer == 0 && item.getTipoModalEnum() == TipomodalEnum.DTC)) {
				String msgErro = null;
				item.setEnviarParaMapa(false);
				item.setStatusBLEnum(StatusBLEnum.PENDENTE_ANEXO);
				item.getCadastroBL().setStatusBLEnum(StatusBLEnum.PENDENTE_ANEXO);
				if (qtdeReg == 0) {
					msgErro = "Não possivel enviar o processo, para o Mapa não existe o BL anexos ! ";
				}

				if (qtdeRegistrosContainer == 0) {
					msgErro = "Não é possivel enviar o processo, para o Mapa não existe Container vinculado ao BL ! ";
				}

				throw new Exception(msgErro);
			} else {
				item.setStatusBLEnum(StatusBLEnum.AGUARDANDO_LIBERACAO_PROCESSOS_QUE_ANEXARAM);
			}
		}

		if (item.getProgramacaoNavio() == null) {
			item.setProgramacaoNavio(getProgramacaoNavioRN().localizar(232));
		}

		getDAO().beginTransaction();

		CadastroDTCDTA entidadeAntiga = null;
		if (item.getId() != null) {
			entidadeAntiga = localizarMesmaTransacao(item.getId());

			if (item.getProgramacaoNavio().getId().equals(entidadeAntiga.getProgramacaoNavio().getId()) == false) {
				envioEmailAlteracaoNavio = true;
				navioAntigo = entidadeAntiga.getProgramacaoNavio();
			}
		} else {
			if (getDAO().pesquisaDescricaoBL(item.getDescricaoBL())) {
				throw new Exception("Já existe um processo cadastro com o BL " + item.getDescricaoBL());
			}

			if (item.getUserCadastroDtc() == null) {
				item.setUserCadastroDtc(userLogado);
			}
		}

		item.setDescricaoBL(removerAcentosMascarasCampoVazio(item.getDescricaoBL()));

		item.setDescricaoBL(item.getDescricaoBL().toUpperCase());
		if (item.getDataCadastro() == null) {
			item.setDataCadastro(new Date());
		}
		if (item.getStatusBLEnum() == null) {
			item.setStatusBLEnum(StatusBLEnum.PENDENTE_ANEXO);
		}

		if (item.getNumeroATI() == null) {
			envioEmailPrimeiroCadastro = true;

			item.setStatusAtiLclFclEnum(StatusAtiLclFclEnum.PENDENTE);
			item = getDAO().alterar(item);

			DateFormat df = new SimpleDateFormat("yyyy");
			String dateA = df.format(item.getDataCadastro());

			String nro = null;
			nro = item.getId() + "/" + dateA;
			item.setNumeroATI(nro);
		}

		item.setCadastroBL(incluirBL(item, listaMapaNCM));

		if (entidadeAntiga != null) {
			if (entidadeAntiga.getStatusAtiLclFclEnum() != item.getStatusAtiLclFclEnum()) {
				envioAlteracaoStatusClif = true;
			}
		}

		item.setFaturarContraEmail(item.getFaturarContraEmail().replace(";", "; "));
		if (item.getFaturarContraEmail().length() >= 150) {
			item.setFaturarContraEmail(item.getFaturarContraEmail().replaceAll(" ", ""));
			item.setFaturarContraEmail(item.getFaturarContraEmail().replace(";", "; "));
		}

		item = getDAO().alterar(item);

		getDAO().commit();
		if (item.isEnviarParaMapa() && envioMapaValidar) {
			item.getCadastroBL().setStatusBLEnum(StatusBLEnum.AGUARDANDO_LIBERACAO_PROCESSOS_QUE_ANEXARAM);
			getContanierRN().updateAtualizaDados(item.getCadastroBL());

			getCadastroBLRN().enviarEmail(item.getCadastroBL(), Role.CLIENTE);
		}

		try {
			if (envioAlteracaoStatusClif) {
				enviarEmailAlteracaoStatusClif(item, Role.CLIENTE);
			}
			if (envioEmailPrimeiroCadastro) {
				enviarEmailPrimeiroCadastro(item);
			}
			if (envioEmailAlteracaoNavio) {
				enviarEmailAlteracaoNavio(item, navioAntigo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		getDAO().closeTransaction();

		return item;
	}

	private void enviarEmailAlteracaoStatusClif(CadastroDTCDTA entidade, Role permissao)
			throws EmailException, IOException {
		List<UserComissario> listaUsersComissarias = getUserComissarioRN()
				.listaTodosUsuariosComissarias(entidade.getCadComissario());

		for (UserComissario item : listaUsersComissarias) {
			if (item.getUsuario().isReceberEmail() && item.getUsuario().getRole() == Role.CLIENTE) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

				String assunto = "Agendamento ATI CLIF - BL: " + entidade.getDescricaoBL();

				String corpoEmail = "ATI: " + entidade.getNumeroATI() + " - Alterado Status da ATI ->> "
						+ entidade.getStatusAtiLclFclEnum().getDescricao() + " Navio ->> "
						+ entidade.getProgramacaoNavio().getNavio() + " ETA ->> "
						+ sdf.format(entidade.getProgramacaoNavio().getDataETA()) + " Cliente ->> "
						+ entidade.getCadComissario().getNome();

				getJsfUtil().envioEmailSimples(new StringTokenizer(item.getUsuario().getEmail(), ";"), assunto,
						corpoEmail);
			}
		}
	}

	private void enviarEmailAlteracaoNavio(CadastroDTCDTA entidade, ProgramacaoNavio navioAntigo)
			throws EmailException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

		List<User> userPerfil = getUserRN().localizarUsuarioPorPerfil(Role.CLIF);

		String assunto = "Agendamento Alteração Navio ATI CLIF - : " + entidade.getNumeroATI();

		String corpoEmail = "ATI: " + entidade.getNumeroATI() + " - Alteração Navio ->> " + " Navio Novo ->> "
				+ entidade.getProgramacaoNavio().getNavio() + " ETA ->> "
				+ sdf.format(entidade.getProgramacaoNavio().getDataETA()) + " Cliente ->> "
				+ entidade.getCadComissario().getNome() + " Navio Antigo =>> " + navioAntigo.getNavio();

		String destinatario = new String();
		for (User item : userPerfil) {
			if (item.getId() != 191) {
				if (destinatario == null) {
					destinatario = item.getEmail();
				} else {
					destinatario = destinatario + ";" + item.getEmail();
				}
			}
		}
		getJsfUtil().envioEmailSimples(new StringTokenizer(destinatario, ";"), assunto, corpoEmail);

	}

	private void enviarEmailPrimeiroCadastro(CadastroDTCDTA entidade) throws EmailException, IOException {
		List<User> userPerfil = getUserRN().localizarUsuarioPorPerfil(Role.CLIF);

		String assunto = null;
		String corpoEmail = null;
		if (userPerfil.size() > 0) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

			String dataEta = "";
			String navio = "";
			if (entidade.getProgramacaoNavio() != null) {
				if (entidade.getProgramacaoNavio().getDataETA() != null) {
					dataEta = sdf.format(entidade.getProgramacaoNavio().getDataETA());
				}
				navio = entidade.getProgramacaoNavio().getNavio();
			}
			String imo = "";
			if (entidade.getCargaImo() == TipoSimNaoEnum.SIM) {
				imo = " - IMO ";
			}

			assunto = "Agendamento ATI CLIF - BL: " + entidade.getDescricaoBL() + imo;
			corpoEmail = "ATI: " + entidade.getNumeroATI() + " - Efetuado Cadastro da ATI ->> "
					+ entidade.getNumeroATI() + " Navio ->> " + navio + " ETA ->> " + dataEta + " Cliente ->> "
					+ entidade.getCadComissario().getNome();
		}

		String destinatario = new String();
		for (User item : userPerfil) {
			if (item.getId() != 191) {
				if (destinatario == null) {
					destinatario = item.getEmail();
				} else {
					destinatario = destinatario + ";" + item.getEmail();
				}
			}
		}
		getJsfUtil().envioEmailSimples(new StringTokenizer(destinatario, ";"), assunto, corpoEmail);

		if (entidade.getCargaImo() == TipoSimNaoEnum.SIM) {
			getJsfUtil().envioEmailSimples(new StringTokenizer("imo@oclif.com.br", ";"), assunto + " - IMO ",
					corpoEmail);
		}
	}

	private UserFacade getUserRN() {
		if (userRN == null) {
			userRN = new UserFacade();
		}

		return userRN;
	}

	public void enviarEmailDocumentosFaltantes(CadastroDTCDTA entidade, User userLogado,
			TipoPendenciaDocumentalEmailEnum tipoEmail, String textoLivre) throws Exception {

		String corpoEmail = null;
		String assunto = null;

		assunto = "ATI CLIF " + entidade.getNumeroATI() + " - Pendencia documental - ( "
				+ entidade.getImportador().getRazaoSocial() + " ) - ( " + entidade.getCadastroBL() + " )";

		corpoEmail = getJsfUtil().getPropertyRecuperaInformcao("tipo.pendencia." + tipoEmail.getSeq() + ".primeiro",
				"mensagensEmail.properties");

		corpoEmail = corpoEmail + entidade.getNumeroATI();

		corpoEmail = corpoEmail + " " + getJsfUtil().getPropertyRecuperaInformcao(
				"tipo.pendencia." + tipoEmail.getSeq() + ".segundo", "mensagensEmail.properties");

		if (textoLivre != null) {
			if (!textoLivre.isEmpty()) {
				corpoEmail = corpoEmail + " \n" + " \n" + textoLivre;
			}
		}

		User userCadastroDtc = null;
		if (entidade.getUserCadastroDtc() == null) {
			throw new Exception("Não foi possivel, enviar e-mail ! ");
		} else {
			userCadastroDtc = getUserRN().localizar(entidade.getUserCadastroDtc().getId());
		}

		getJsfUtil().envioEmailSimplesComCopia(userCadastroDtc.getEmail(), userLogado.getEmail(), assunto, corpoEmail,
				true);
	}

	private JSFUtil getJsfUtil() {
		if (email == null) {
			email = new JSFUtil();
		}
		return email;
	}

	private UserComissarioRN getUserComissarioRN() {
		if (userComissarioRN == null) {
			userComissarioRN = new UserComissarioRN();
		}
		return userComissarioRN;
	}

	private CadastroBL incluirBL(CadastroDTCDTA item, List<MapaNcm> listaMapaNCM) throws Exception {
		CadastroBL bl = null;
		boolean anuencia = false;
		if (item.getCadastroBL() == null) {
			bl = new CadastroBL();
			
			for (MapaNcm itens : listaMapaNCM) {
				itens.setCadastroBL(bl);
				bl.getListaMapaNcm().add(itens);
				
				if (itens.getNcm().isProdutoAnuencia()) {
					anuencia = true;
				}
			}

//			bl.setListaMapaNcm(listaMapaNCM);
			bl.setInspecao(item.isInspecao());
			bl.setLiberado(item.isLiberado());
			bl.setStatusBLEnum(item.getStatusBLEnum());
		} else {
			bl = item.getCadastroBL();

			CadastroBL entidadeBLBanco = getCadastroBLRN().localizar(item.getCadastroBL().getId());
			if (!entidadeBLBanco.isEnviarParaMapa()) {
				bl.setStatusBLEnum(item.getStatusBLEnum());
			}
		}
		bl.setAnuenciaMapa(anuencia);
		bl.setTemperaturaString(item.getTemperaturaString());

		bl.setUserCadastroDtc(item.getUserCadastroDtc());
		bl.setProgramacaoNavio(item.getProgramacaoNavio());
		bl.setEnviarParaMapa(item.isEnviarParaMapa());
		bl.setStatusAtiLclFclEnum(item.getStatusAtiLclFclEnum());
		bl.setContainerLCL(item.getContainerLCL());
		bl.setCadComissario(item.getCadComissario());
		bl.setCargaComMadeira(item.getCargaComMadeira());
		bl.setCargaImo(item.getCargaImo());
		bl.setCargaReefer(item.getCargaReefer());
		bl.setDataEnvioMapa(item.getDataEnvioMapa());
		bl.setDataCadastro(item.getDataCadastro());
		bl.setDeferido(item.isDeferido());
		bl.setDescricaoBL(item.getDescricaoBL());
		bl.setDescricaoMercadoria(item.getDescricaoMercadoria());
		bl.setArmador(item.getArmador());
		bl.setNavioViagem(item.getNavioViagem());
		bl.setViagem(item.getViagem());
		bl.setDataAtracacao(item.getDataAtracacao());
		bl.setQuantidade(item.getQuantidade());
		bl.setCifMercadoria(item.getCifMercadoria());
		bl.setReferenciaCliente(item.getReferenciaCliente());
		bl.setRepresentante(item.getRepresentante());
		bl.setPortos(item.getPortos());
		bl.setNumeroATI(item.getNumeroATI());
		bl.setTemperatura(item.getTemperatura());
		bl.setFreeTime(item.getFreeTime());
		bl.setDesunitizacao(item.isDesunitizacao());
		bl.setReunitizar(item.isReunitizar());
		bl.setEntrePosto(item.getEntrePosto());
		bl.setImportador(item.getImportador());
		bl.setModalidadeBLEnum(item.getModalidadeBLEnum());
		bl.setNcm(item.getNcm());
		bl.setPaisOrigem(item.getPaisOrigem());
		bl.setPaisProcedencia(item.getPaisProcedencia());
		bl.setTipoContainerEnum(item.getTipoContainerEnum());
		bl.setTipoEmbalagemEspecie(item.getTipoEmbalagemEspecie());
		bl.setTipoEmbalagemEncontrada(item.getTipoEmbalagemEncontrada());
		bl.setTipoModalEnum(item.getTipoModalEnum());

		return getCadastroBLRN().incluirSemFecharTransacao(bl);
	}

	private CadastroBLRN getCadastroBLRN() {
		if (cadastroBLRN == null) {
			cadastroBLRN = new CadastroBLRN();
		}
		return cadastroBLRN;
	}

	private CadastroBLContanierRN getContanierRN() {
		if (contanierRN == null) {
			contanierRN = new CadastroBLContanierRN();
		}
		return contanierRN;
	}

	private CadastroBLContanierLCLRN getContanierLCLRN() {
		if (contanierLCLRN == null) {
			contanierLCLRN = new CadastroBLContanierLCLRN();
		}
		return contanierLCLRN;
	}

	public CadastroDTCDTA localizar(int itemId) throws Exception {
		getDAO().beginTransaction();
		CadastroDTCDTA item = null;
		try {
			item = getDAO().localizarPorID(itemId);
		} catch (Exception e) {
			getDAO().rollback();
			throw new Exception(e.getMessage());
		}
		getDAO().closeTransaction();
		return item;
	}

	public CadastroDTCDTA localizarPorReferencia(int itemId) throws Exception {
		getDAO().beginTransaction();
		CadastroDTCDTA item = null;
		try {
			item = getDAO().localizarDTCDTA(itemId);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().closeTransaction();
		return item;
	}

	public CadastroDTCDTA localizarMesmaTransacao(int itemId) {
		// getDAO().beginTransaction();
		CadastroDTCDTA item = getDAO().localizarPorID(itemId);
		// getDAO().closeTransaction();
		return item;
	}

	public void deletar(CadastroDTCDTA item) throws Exception {
		getDAO().beginTransaction();
		try {
			getDAO().delete(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();
	}

	public List<CadastroDTCDTA> listaTodos() {
		getDAO().beginTransaction();
		List<CadastroDTCDTA> result = getDAO().listaTodosRegistros();
		getDAO().closeTransaction();
		return result;
	}

	public CadastroDTCDTA incluirDtcBlContainer(CadastroBLContanierLCL entidade, CadastroDTCDTA entidadeDTCDTA,
			List<MapaNcm> listaMapaNCM, List<CadastroBLContanierLCL> listaLCL) throws Throwable {
		
		if (listaLCL == null || listaLCL.size() == 0) {
			throw new Exception("Obrigatario Vincular BL Master! ");
		}
		
		if (entidade != null && entidade.getId() == null) {
			entidade = getContanierLCLRN().incluir(entidade);
		}
		entidadeDTCDTA.setContainerLCL(entidade);

		CadastroDTCDTA entidadeDTCDTA_novo = new CadastroDTCDTA();
		entidadeDTCDTA_novo = entidadeDTCDTA;
		entidadeDTCDTA_novo.setCadastroBL(null);
		entidadeDTCDTA_novo.setId(null);
		entidadeDTCDTA_novo.setNumeroATI(null);
		entidadeDTCDTA_novo.setModalidadeBLEnum(ModalidadeBLEnum.LCL);
		entidadeDTCDTA_novo.setStatusAtiLclFclEnum(StatusAtiLclFclEnum.PENDENTE);
		entidadeDTCDTA_novo.setImportadorLCL(entidade.getImportadorLCL() );
		entidadeDTCDTA = alterar(entidadeDTCDTA_novo, listaMapaNCM);

		for (CadastroBLContanierLCL itens : listaLCL) {
			CadastroBLContanier contanier = new CadastroBLContanier();
			contanier.setCadastroBL(entidadeDTCDTA.getCadastroBL());
			contanier.setContainerLCL(itens);
			contanier.setNumeroContanier(itens.getNumeroContanier());
			contanier.setNumeroLacre(itens.getNumeroLacre());
			contanier.setCargaImo(itens.getCargaImo());
			contanier.setStatusBLEnum(StatusBLEnum.PENDENTE_ANEXO);
			getContanierRN().incluir(contanier);
			
		}

//		CadastroBLContanier contanier = new CadastroBLContanier();
//		contanier.setCadastroBL(entidadeDTCDTA.getCadastroBL());
//		contanier.setContainerLCL(entidade);
//		contanier.setNumeroContanier(entidade.getNumeroContanier());
//		contanier.setNumeroLacre(entidade.getNumeroLacre());
//		contanier.setCargaImo(entidade.getCargaImo());
//		contanier.setStatusBLEnum(StatusBLEnum.PENDENTE_ANEXO);
//		getContanierRN().incluir(contanier);

		return entidadeDTCDTA;
	}

	public CadastroDTCDTA salvarSemRelacionaMento(CadastroDTCDTA entidade) throws Throwable {
		getDAO().beginTransaction();
		try {
			entidade.setDescricaoBL(entidade.getDescricaoBL().replace("/", ""));

//			StringTokenizer stPara = new StringTokenizer(entidade.getFaturarContraEmail(), ";");
//			StringBuilder test = new StringBuilder();
//			while (stPara.hasMoreTokens()) {
//				test.append(stPara.nextToken()+";"+"\n");
//			}
//			if (entidade.getFaturarContraEmail() != null) {
//				if (entidade.getFaturarContraEmail().length() >= 850) {
//					entidade.setFaturarContraEmail(entidade.getFaturarContraEmail().replaceAll(" ", ""));
//					while (stPara.hasMoreTokens()) {
//						test.append(stPara.nextToken()+";"+"\n");
//					}
//				}
//			}
//			entidade.setFaturarContraEmail(test.toString());
			
			if (entidade.getFaturarContraEmail() != null) {
				entidade.setFaturarContraEmail(entidade.getFaturarContraEmail().replace(";", "; "));
				if (entidade.getFaturarContraEmail().length() >= 150) {
					entidade.setFaturarContraEmail(entidade.getFaturarContraEmail().replaceAll(" ", ""));
					entidade.setFaturarContraEmail(entidade.getFaturarContraEmail().replace(";", "; "));
				}
			}

			entidade = getDAO().alterar(entidade);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();

		return entidade;
	}

	private CadastroFaturarContraRN getFaturarContraRN() {
		if (faturarContraRN == null) {
			faturarContraRN = new CadastroFaturarContraRN();
		}
		return faturarContraRN;
	}

	public CadastroDTCDTA bloquearEdicaoRegistro(CadastroDTCDTA entidade, User usuario, boolean desbloquear)
			throws Exception {

		if (usuario.isDespachante() || usuario.isCliente()) {
			entidade = localizar(entidade.getId());

			if (entidade.getProcessoBloqueado() != null) {
				if (entidade.getProcessoBloqueado().getId() != usuario.getId()) {
					throw new Exception("Não é possivel editar este processo! Editado por usuário.. "
							+ entidade.getProcessoBloqueado().getName());
				} else {
					if (desbloquear) {
						entidade.setProcessoBloqueado(null);
					}
				}
			} else {
				if (desbloquear) {
					entidade.setProcessoBloqueado(null);
				} else {
					entidade.setProcessoBloqueado(usuario);
				}
			}
			getDAO().beginTransaction();
			try {
				entidade = getDAO().alterar(entidade);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
			getDAO().commitAndCloseTransaction();
		}

		return entidade;
	}

	public void updateAtualizaDados() {
		Map<String, Object> parameters = new HashMap<String, Object>();

		getDAO().beginTransaction();

		getDAO().atualizaDadosProcessosBloqueadosUpdate(parameters);

		getDAO().commitAndCloseTransaction();
	}

	public float retornarTaxaDolar() throws IOException {
		float txDolar = 0;

		String destino = "C:/services/arquivo/txDolar.xml";

		BufferedReader br = new BufferedReader(new FileReader(destino));
		String linha = null;
		while (br.ready()) {
			linha = br.readLine();
		}
		br.close();

		linha = linha.replace("\"", "");
		int posInicial = linha.indexOf("valor:") + 6;
		int posFinal = linha.indexOf(",ultima_consulta:");

		txDolar = Float.parseFloat(linha.substring(posInicial, posFinal));

		return txDolar;
	}

	public float converteValorCifDolar(CadastroDTCDTA entidade) throws IOException {
		float valorCifConvertido = 0;

		String destino = "C:/services/arquivo/txDolar.xml";

		BufferedReader br = new BufferedReader(new FileReader(destino));
		String linha = null;
		while (br.ready()) {
			linha = br.readLine();
		}
		br.close();

		linha = linha.replace("\"", "");
		int posInicial = linha.indexOf("valor:") + 6;
		int posFinal = linha.indexOf(",ultima_consulta:");

		float txDolar = Float.parseFloat(linha.substring(posInicial, posFinal));

		valorCifConvertido = entidade.getCifMercadoria() * txDolar;

		return valorCifConvertido;
	}

	private UserImportadorRN getUserImportadorRN() {
		if (userImportadorRN == null) {
			userImportadorRN = new UserImportadorRN();
		}

		return userImportadorRN;
	}

	private UserImportadorUsuariosRN getImportadorUsuariosRN() {
		if (importadorUsuariosRN == null) {
			importadorUsuariosRN = new UserImportadorUsuariosRN();
		}
		return importadorUsuariosRN;
	}

	/**
	 * Retorna a condi��o dos importadore Master
	 * 
	 * @param entidade
	 * @param sql
	 * @return String
	 */
	public String retornaCondicaoFiltroTabelaImportadorMaster(User entidade, String sql, TipoFiltroEnum tipoFiltro) {
		String condicao = null;

		String valorImportador = getUserImportadorRN().importadorPorUsuarios(entidade);

		if (tipoFiltro == TipoFiltroEnum.IN) {
			condicao = FiltroTabelaBean.montaCondicaoSqlWhereIN(CadastroDTCDTA.CAMPO_IMPORTADOR_FILTRO, valorImportador,
					TipoFiltroEnum.IN, sql);
		}
		if (tipoFiltro == TipoFiltroEnum.IN_SEM_WHERE) {
			condicao = FiltroTabelaBean.montaCondicaoSqlWhereIN(CadastroDTCDTA.CAMPO_IMPORTADOR_FILTRO, sql,
					TipoFiltroEnum.IN_SEM_WHERE, valorImportador);
		}

		return condicao;

	}

	/**
	 * Retorna a condi��o dos importadore cliente
	 * 
	 * @param entidade
	 * @param sql
	 * @return String
	 */
	public String retornaCondicaoFiltroTabelaImportadorCliente(User entidade, String sql, TipoFiltroEnum tipoFiltro) {
		String condicao = null;

		UserImportadorUsuarios userMaster = getImportadorUsuariosRN().retornaImportadorUsuarioMaster(entidade);

		String valorImportador = null;
		if (userMaster == null) {
			valorImportador = getUserImportadorRN().importadorPorUsuarios(entidade);
		} else {
			valorImportador = getUserImportadorRN()
					.importadorPorUsuarios(userMaster.getImportadorComissaria().getUsuario());
		}

		if (tipoFiltro == TipoFiltroEnum.IN) {
			condicao = FiltroTabelaBean.montaCondicaoSqlWhereIN(CadastroDTCDTA.CAMPO_IMPORTADOR_FILTRO, valorImportador,
					TipoFiltroEnum.IN, sql);
		}
		if (tipoFiltro == TipoFiltroEnum.IN_SEM_WHERE) {
			condicao = FiltroTabelaBean.montaCondicaoSqlWhereIN(CadastroDTCDTA.CAMPO_IMPORTADOR_FILTRO, sql,
					TipoFiltroEnum.IN_SEM_WHERE, valorImportador);
		}
		if (entidade.isUtilizarFiltroLCL()) {
			condicao = FiltroTabelaBean.montaCondicaoSqlWhereIN(CadastroDTCDTA.CAMPO_IMPORTADOR_LCL, condicao,
					TipoFiltroEnum.IN_SEM_WHERE_OR, valorImportador);
		}

		return condicao;

	}

	public String retornaCondicaoFiltroTabelaComissariaMaster(User entidade, String sql, TipoFiltroEnum tipoFiltro) {
		String condicao = null;

		String valor = getUserComissarioRN().comissariaPorUsuarios(entidade);

		if (tipoFiltro == TipoFiltroEnum.IN) {
			condicao = FiltroTabelaBean.montaCondicaoSqlWhereIN(CadastroDTCDTA.CADASTRO_COMISSARIO, valor,
					TipoFiltroEnum.IN, sql);
		}
		if (tipoFiltro == TipoFiltroEnum.IN_SEM_WHERE) {
			condicao = FiltroTabelaBean.montaCondicaoSqlWhereIN(CadastroDTCDTA.CADASTRO_COMISSARIO, sql,
					TipoFiltroEnum.IN_SEM_WHERE, valor);
		}
		if (entidade.isUtilizarFiltroLCL()) {
			condicao = FiltroTabelaBean.montaCondicaoSqlWhereIN(CadastroDTCDTA.CAMPO_IMPORTADOR_LCL, condicao,
					TipoFiltroEnum.IN_SEM_WHERE_OR, valor);
		}

		return condicao;

	}

	public String retornaCondicaoFiltroTabelaComissariaMasterOuNotify(User entidade, String sql,
			TipoFiltroEnum tipoFiltro) {
		String condicao = null;

		CadastroComissario cadComissaria = getUserComissarioRN().retornaComissariasUsuarioMaster(entidade);

		List<UserImportadorComissaria> list = getImportadorComissariaRN().retornaComisarias(cadComissaria);

		String valorComissaria = null;
		String valorNotify = null;
		int seqComissaria = 0;
		int seqNotify = 0;
		for (UserImportadorComissaria item : list) {
			if (item.getNotifyRespLote() != null) {
				seqNotify = seqNotify + 1;
				if (seqNotify == 1) {
					valorNotify = "( " + item.getNotifyRespLote().getId().toString();
				} else {
					valorNotify = valorNotify + "," + item.getNotifyRespLote().getId().toString();
				}
			}
			if (item.getComissaria() != null) {
				seqComissaria = seqComissaria + 1;
				if (seqComissaria == 1) {
					valorComissaria = "( " + item.getComissaria().getId().toString();
				} else {
					valorComissaria = valorComissaria + "," + item.getComissaria().getId().toString();
				}
			}
		}
		String filtroCampo = null;
		String valorFiltro = null;
		boolean filtrarPorNotify = false;
		if (valorComissaria != null && !valorComissaria.isEmpty()) {
			valorComissaria = valorComissaria + " ) ";
			filtroCampo = CadastroDTCDTA.CADASTRO_COMISSARIO;
		}
		if (valorNotify != null && !valorNotify.isEmpty()) {
			valorNotify = valorNotify + " ) ";
			filtroCampo = CadastroDTCDTA.CAMPO_RESPONSAVELLOTE_FILTRO;
			filtrarPorNotify = true;
		}

		if (filtrarPorNotify) {
			valorFiltro = valorNotify;
		} else {
			valorFiltro = valorComissaria;
		}

		if (tipoFiltro == TipoFiltroEnum.IN) {
			condicao = FiltroTabelaBean.montaCondicaoSqlWhereIN(filtroCampo, valorFiltro, TipoFiltroEnum.IN, sql);
		}
		if (tipoFiltro == TipoFiltroEnum.IN_SEM_WHERE) {
			condicao = FiltroTabelaBean.montaCondicaoSqlWhereIN(filtroCampo, sql, TipoFiltroEnum.IN_SEM_WHERE,
					valorFiltro);
		}

		return condicao;

	}

	public String retornaCondicaoFiltroTabelaImportador(User entidade, String sql, TipoFiltroEnum tipoFiltro) {
		String condicao = null;

		CadastroComissario cadComissaria = getUserComissarioRN().retornaComissariasUsuarioMaster(entidade);

		List<UserImportadorComissaria> list = getImportadorComissariaRN().retornaComisarias(cadComissaria);

		String valorFiltro = null;
		int seqComissaria = 0;
		for (UserImportadorComissaria item : list) {
			if (item.getImportador() != null) {
				seqComissaria = seqComissaria + 1;
				if (seqComissaria == 1) {
					valorFiltro = "( " + item.getImportador().getId().toString();
				} else {
					valorFiltro = valorFiltro + "," + item.getImportador().getId().toString();
				}
			}
		}
		valorFiltro = valorFiltro + " ) ";

		if (tipoFiltro == TipoFiltroEnum.IN) {
			condicao = FiltroTabelaBean.montaCondicaoSqlWhereIN(CadastroDTCDTA.CAMPO_IMPORTADOR_FILTRO, valorFiltro,
					TipoFiltroEnum.IN, sql);
		}
		if (tipoFiltro == TipoFiltroEnum.IN_SEM_WHERE) {
			condicao = FiltroTabelaBean.montaCondicaoSqlWhereIN(CadastroDTCDTA.CAMPO_IMPORTADOR_FILTRO, sql,
					TipoFiltroEnum.IN_SEM_WHERE, valorFiltro);
		}

		return condicao;
	}

	public String gerarSegregacaoExcel(CadastroDTCDTA entidade) {
		String caminho = getJsfUtil().caminhoTemporario();
		caminho = caminho + "segregacao.xls";

		CadastroBL cadastroBL = getCadastroBLRN().localizar(entidade.getCadastroBL().getId());

		try {
			// Cria um Arquivo Excel
			HSSFWorkbook wb = new HSSFWorkbook();

			// Cria uma planilha Excel
			HSSFSheet sheet = wb.createSheet("Segregacao");

			// Cria uma linha na Planilha.
			Row linha1 = sheet.createRow((short) 0);
			Row linha2 = sheet.createRow((short) 1);
			Row linha3 = sheet.createRow((short) 2);
			Row linha4 = sheet.createRow((short) 3);
			Row linha5 = sheet.createRow((short) 4);
			Row linha6 = sheet.createRow((short) 5);

			// Cria as c�lulas na linha
			gerarLinha1(sheet, linha1);

			gerarLinha2(sheet, linha2);

			gerarLinha3(entidade, sheet, linha3);

			gerarLinha4(entidade, sheet, linha4);

			gerarLinha5(entidade, sheet, linha5);

			gerarLinha6(sheet, linha6);

			int celulaExcel = gerarLinha7(cadastroBL, sheet);

			celulaExcel = gerarLinha11(sheet, celulaExcel);

			celulaExcel = gerarLinha12(sheet, celulaExcel);

			celulaExcel = gerarLinha13(sheet, celulaExcel);

			celulaExcel = gerarLinha14(sheet, celulaExcel);

			celulaExcel = gerarlinha15(sheet, celulaExcel);

			gerarLinha16(sheet, celulaExcel);

			try (FileOutputStream fileOut = new FileOutputStream(caminho)) {
				wb.write(fileOut);
			}

			return caminho;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	private void gerarLinha16(HSSFSheet sheet, int celulaExcel) {
		celulaExcel = celulaExcel + 1;
		Row linha16 = sheet.createRow((short) celulaExcel);
		linha16.createCell(0).setCellValue(
				"O formulario devera ser preenchido e enviado para o endereco eletronico: opr-dtc@portoitapoa.com.br");
		sheet.addMergedRegion(new CellRangeAddress(celulaExcel, celulaExcel, 0, 10));
	}

	private int gerarlinha15(HSSFSheet sheet, int celulaExcel) {
		celulaExcel = celulaExcel + 1;
		Row linha15 = sheet.createRow((short) celulaExcel);
		linha15.createCell(0).setCellValue("Solicitamos a segregacao e custos serao direcionados para:");
		sheet.addMergedRegion(new CellRangeAddress(celulaExcel, celulaExcel, 0, 10));
		return celulaExcel;
	}

	private int gerarLinha14(HSSFSheet sheet, int celulaExcel) {
		celulaExcel = celulaExcel + 1;
		Row linha14 = sheet.createRow((short) celulaExcel);
		linha14.createCell(0).setCellValue("Prazo para segregacao: 24h antes do ETA do Navio");
		sheet.addMergedRegion(new CellRangeAddress(celulaExcel, celulaExcel, 0, 10));
		return celulaExcel;
	}

	private int gerarLinha13(HSSFSheet sheet, int celulaExcel) {
		celulaExcel = celulaExcel + 1;
		Row linha13 = sheet.createRow((short) celulaExcel);
		linha13.createCell(0)
				.setCellValue("CNPJ NVOCC/AGENTE informado deve ser o constante do CE House quando houver");
		sheet.addMergedRegion(new CellRangeAddress(celulaExcel, celulaExcel, 0, 10));
		return celulaExcel;
	}

	private int gerarLinha12(HSSFSheet sheet, int celulaExcel) {
		celulaExcel = celulaExcel + 1;
		Row linha12 = sheet.createRow((short) celulaExcel);
		linha12.createCell(0).setCellValue("Lote completo do CE Master");
		sheet.addMergedRegion(new CellRangeAddress(celulaExcel, celulaExcel, 0, 10));
		return celulaExcel;
	}

	private int gerarLinha11(HSSFSheet sheet, int celulaExcel) {
		celulaExcel = celulaExcel + 3;
		Row linha11 = sheet.createRow((short) celulaExcel);
		linha11.createCell(0).setCellValue("REQUISITOS PARA SEGREGACAO");
		sheet.addMergedRegion(new CellRangeAddress(celulaExcel, celulaExcel, 0, 10));
		return celulaExcel;
	}

	private int gerarLinha7(CadastroBL cadastroBL, HSSFSheet sheet) {
		int celulaExcel = 6;
		for (CadastroBLContanier itens : cadastroBL.getListaCadastroBLContanier()) {
			Row linha7 = sheet.createRow((short) celulaExcel);
			linha7.createCell(0).setCellValue("(X) Segregacao (_____) Cancelamento de segregacao");
			sheet.addMergedRegion(new CellRangeAddress(celulaExcel, celulaExcel, 0, 3));
			linha7.createCell(4).setCellValue(itens.getNumeroContanier().trim().replaceAll(" ", ""));
			sheet.addMergedRegion(new CellRangeAddress(celulaExcel, celulaExcel, 4, 10));

			celulaExcel = celulaExcel + 1;
		}
		return celulaExcel;
	}

	private void gerarLinha6(HSSFSheet sheet, Row linha6) {
		linha6.createCell(0).setCellValue("Solicitacao");
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 0, 3));
		linha6.createCell(4).setCellValue("CONTAINER");
		sheet.addMergedRegion(new CellRangeAddress(5, 5, 4, 10));
	}

	private void gerarLinha5(CadastroDTCDTA entidade, HSSFSheet sheet, Row linha5) {
		linha5.createCell(4).setCellValue("CE HOUSE:");
		linha5.createCell(5).setCellValue(entidade.getSegregacaoCeHouse());
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 5, 10));
	}

	private void gerarLinha4(CadastroDTCDTA entidade, HSSFSheet sheet, Row linha4) {
		linha4.createCell(0).setCellValue("CNPJ NVOCC/AGENTE:");
		linha4.createCell(1).setCellValue(entidade.getAgenteCnpj() + " / " + entidade.getAgenteNome().toUpperCase());
		linha4.createCell(4).setCellValue("CE MASTER:");
		linha4.createCell(5).setCellValue(entidade.getSegregacaoCeMaster());
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 5, 10));
	}

	private void gerarLinha3(CadastroDTCDTA entidade, HSSFSheet sheet, Row linha3) {
		DateFormat df = new SimpleDateFormat("dd/MMM");
		linha3.createCell(0).setCellValue("NAVIO/VG:");
		linha3.createCell(1).setCellValue(entidade.getProgramacaoNavio().getNavio());
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 3));
		linha3.createCell(4).setCellValue("ATRACACAO:");
		linha3.createCell(5).setCellValue(df.format(new Date()));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 5, 10));
	}

	private void gerarLinha2(HSSFSheet sheet, Row linha2) {
		linha2.createCell(0).setCellValue("Codigo F.OPR.IEX.38");
		linha2.createCell(1).setCellValue("Formulario REQUISICAO DE SEGREGACAO DE DTC");
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 8));
		linha2.createCell(9).setCellValue("Pagina 1/1");
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 9, 10));
	}

	private void gerarLinha1(HSSFSheet sheet, Row linha1) {
		linha1.createCell(0).setCellValue("porto de itapoa");
		linha1.createCell(1).setCellValue("Itapoa Terminais Portuarios S.A.");
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 8));
		linha1.createCell(9).setCellValue("SGI");
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 10));
	}

	private CadastroBLRiscoRN getRiscoRN() {
		if (riscoRN == null) {
			riscoRN = new CadastroBLRiscoRN();
		}

		return riscoRN;
	}

	private CadastroBLDescricaoMercadoriaRN getMercadoriaRN() {
		if (mercadoriaRN == null) {
			mercadoriaRN = new CadastroBLDescricaoMercadoriaRN();
		}

		return mercadoriaRN;
	}

	public CadastroDTCDTA localizarRegistroFitosanitario(CadastroDTCDTA entidade) throws Exception {
		CadastroDTCDTA retorno = null;
		getDAO().beginTransaction();

		retorno = localizarMesmaTransacao(getDAO().localizarRegistroFitosanitarioDAO(entidade));

		CadastroBL cadastroBL = getCadastroBLRN().localizar(retorno.getCadastroBL().getId());

		for (CadastroBLRiscoFitossanitario item : cadastroBL.getListaBLRisco()) {
			CadastroBLRiscoFitossanitario risco = new CadastroBLRiscoFitossanitario();
			risco.setCadastroBL(entidade.getCadastroBL());
			risco.setDataCadastro(item.getDataCadastro());
			risco.setRiscoFitossanitarioEnum(item.getRiscoFitossanitarioEnum());

			getRiscoRN().incluirMesmaTransacao(risco);
		}

		for (CadastroBLDescricaoMercadoria item : cadastroBL.getListaBLDescricaoMercadoria()) {
			CadastroBLDescricaoMercadoria mercadoria = new CadastroBLDescricaoMercadoria();
			mercadoria.setCadastroBL(entidade.getCadastroBL());
			mercadoria.setDataCadastro(item.getDataCadastro());
			mercadoria.setTipoEmbalagemEncontrada(item.getTipoEmbalagemEncontrada());
			mercadoria.setDescricaoMercadoria(item.getDescricaoMercadoria());

			getMercadoriaRN().alterar(mercadoria);
		}

		getDAO().closeTransaction();

		return retorno;
	}

}