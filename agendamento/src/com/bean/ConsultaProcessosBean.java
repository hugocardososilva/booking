package com.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.mail.EmailException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.StreamedContent;

import com.entidade.CadastroDTCDTA;
import com.entidade.UserComissariaUsuarios;
import com.enums.TipoPendenciaDocumentalEmailEnum;
import com.lazy.NewLazyDataModel;

import com.rn.CadastroDTCDTARN;
import com.rn.UserComissariaUsuariosRN;
import com.util.JSFMessageUtil;

import conexao.com.bean.AbstractLazyDataModel;
import conexao.com.enums.MenssagensUtilEnum;
import conexao.com.enums.TipoExtensaoArquivoEnum;
import conexao.com.enums.TipoFiltroEnum;
import conexao.com.rn.CadastroBLAnexosRN;
import conexao.com.rn.CadastroBLContanierLCLRN;
import conexao.com.rn.UserFacade;
import conexao.com.util.EnvioEmailParametrosUTIL;
import conexao.com.util.FiltroTabelaBean;
import conexao.com.util.IGenericBean;
import conexao.com.util.JSFUtil;
import conexao.com.util.LoginController;
import pentahoatual.com.tuil.ServicoPentahoPDI;
import seguranca.com.entidade.CadastroBL;
import seguranca.com.entidade.CadastroBLAnexos;
import seguranca.com.entidade.CadastroBLContanier;
import seguranca.com.entidade.CadastroBLContanierLCL;
import seguranca.com.entidade.User;
import seguranca.com.enums.ModalidadeBLEnum;
import seguranca.com.enums.Role;
import seguranca.com.enums.StatusAtiLclFclEnum;
import seguranca.com.enums.StatusBLEnum;
import seguranca.com.enums.TipoAnexosEnum;
import seguranca.com.enums.TipoRelatoriosEnum;
import seguranca.com.enums.TipoSimNaoEnum;
import seguranca.com.enums.TipomodalEnum;

@ViewScoped
@ManagedBean
public class ConsultaProcessosBean extends CadastroDtcDtaComumBean implements IGenericBean, Serializable {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;

	private boolean controlarFormListar = true;
	private boolean controlarFormCadastrar = false;
	private boolean controlarDados = false;
	private boolean controlarAnexos = false;
	private boolean controlarEdicaoDadosBL = false;
	private boolean habilitarBotaoEnvioMapa = false;
	private boolean habilitarDeferimento = true;
	private int utilizarEnvioEmailRetiraSegregacao = 0;
	
	
	private StatusBLEnum statusBLEnum;
	
	

	private boolean mostrarCampo = true;
	private boolean mostrarCampoAssunto = false;

	private TipoSimNaoEnum paramContainerparte;

	private float valorConvertidoReais;
	private float txDolar;

	private String controlarImagem = "fa fa-angle-double-down";

	private String textoMotivoCancelamento;
	private String tipoEnvioEmail;

	private String retornoBL;
	private String assuntoEmail;
	private String retornoCE;
	private String retornoDTC_DTA;
	private String retornoINVOICE;
	private String retornoPACKING;
	private String textoMaoLivreEmail;

	private CadastroDTCDTA entidade;
	private CadastroBLAnexos anexo;

	private StreamedContent pdf;

	private TipoAnexosEnum selecionadoTipoAnexos;
	private TipoSimNaoEnum selecionadoCargaImoPorBL;
	private TipoPendenciaDocumentalEmailEnum selecionadoPendenciasEmailEnum;

	private List<StatusAtiLclFclEnum> todasStatusAtiLclFclEnum;
	private List<TipoPendenciaDocumentalEmailEnum> todosPendenciasEmailEnum;

	private LazyDataModel<CadastroDTCDTA> lazyModel;

	private List<CadastroBLContanier> listaBLContanier;

	private CadastroDTCDTARN dtcDtaRN;
	private CadastroBLContanierLCLRN lclRN;
	private List<CadastroBLContanierLCL> listaLCL;
	private CadastroBLContanierLCL lclSelecionado;

	private JSFUtil jsfUtil;

	private CadastroBLAnexosRN cadBLAnexosRN;
	private List<TipoAnexosEnum> todasTipoAnexosEnum;
	private UserComissariaUsuariosRN userComissariaUsuariosRN;
	private UserFacade userRN;
	private TipomodalEnum tipoModalSelecionadoEnum;
	private List<TipomodalEnum> todosTipoModalEnum;
	
	private List<CadastroDTCDTA> entidadeFiltrodadas;

	private CadastroBLContanierLCLRN getLclRN() {
		if (lclRN == null) {
			lclRN = new CadastroBLContanierLCLRN();
		}
		return lclRN;
	}

	public List<CadastroBLContanierLCL> getListaLCL() {
		if (listaLCL == null) {
			listaLCL = getLclRN().listaBlMasterLCL();
		}
		return listaLCL;
	}

	public void setListaLCL(List<CadastroBLContanierLCL> listaLCL) {
		this.listaLCL = listaLCL;
	}

	private CadastroDTCDTARN getDtcDtaRN() {
		if (dtcDtaRN == null) {
			dtcDtaRN = new CadastroDTCDTARN();
		}
		return dtcDtaRN;
	}

	public List<StatusAtiLclFclEnum> getTodasStatusAtiLclFclEnum() {
		if (todasStatusAtiLclFclEnum == null && isControlarFormCadastrar()) {
			todasStatusAtiLclFclEnum = new ArrayList<StatusAtiLclFclEnum>();
			todasStatusAtiLclFclEnum = new ArrayList<StatusAtiLclFclEnum>(Arrays.asList(StatusAtiLclFclEnum.values()));
		}
		return todasStatusAtiLclFclEnum;
	}

	public List<TipoPendenciaDocumentalEmailEnum> getTodosPendenciasEmailEnum() {
		if (todosPendenciasEmailEnum == null && isControlarFormCadastrar()) {
			todosPendenciasEmailEnum = new ArrayList<TipoPendenciaDocumentalEmailEnum>();
			todosPendenciasEmailEnum = new ArrayList<TipoPendenciaDocumentalEmailEnum>(
					Arrays.asList(TipoPendenciaDocumentalEmailEnum.values()));
		}
		return todosPendenciasEmailEnum;
	}

	private UserComissariaUsuariosRN getUserComissariaUsuariosRN() {
		if (userComissariaUsuariosRN == null) {
			userComissariaUsuariosRN = new UserComissariaUsuariosRN();
		}

		return userComissariaUsuariosRN;
	}

	public TipomodalEnum getTipoModalSelecionadoEnum() {
		return tipoModalSelecionadoEnum;
	}

	public void setTipoModalSelecionadoEnum(TipomodalEnum tipoModalSelecionadoEnum) {
		this.tipoModalSelecionadoEnum = tipoModalSelecionadoEnum;
	}

	public List<TipomodalEnum> getTodosTipoModalEnum() {
		if (todosTipoModalEnum == null) {
			todosTipoModalEnum = new ArrayList<TipomodalEnum>(Arrays.asList(TipomodalEnum.values()));
		}
		return todosTipoModalEnum;
	}

	public LazyDataModel<CadastroDTCDTA> getLazyModel() {
		if (lazyModel == null) {
			if (getWhereSQL() == null) {

				if (getUserMB().getUser().getRole() != Role.ADMIN) {
					if (getUserMB().getUser().getRole() == Role.IMPORTADOR_MASTER) {
						setWhereSQL(getDtcDtaRN().retornaCondicaoFiltroTabelaImportadorMaster(getUserMB().getUser(),
								null, TipoFiltroEnum.IN));
					}
					if (userMB.getUser().isCliente()) {
						setWhereSQL(getDtcDtaRN().retornaCondicaoFiltroTabelaImportadorCliente(getUserMB().getUser(),
								null, TipoFiltroEnum.IN));
					}

					if (getUserMB().getUser().getRole() == Role.COMISSARIA_MASTER) {
						setWhereSQL(getDtcDtaRN().retornaCondicaoFiltroTabelaComissariaMasterOuNotify(
								getUserMB().getUser(), null, TipoFiltroEnum.IN));

						setWhereSQL(getDtcDtaRN().retornaCondicaoFiltroTabelaImportador(getUserMB().getUser(),
								getWhereSQL(), TipoFiltroEnum.IN_SEM_WHERE));
					}

					if (getUserMB().getUser().getRole() == Role.DESPACHANTE) {
						UserComissariaUsuarios userComissaria = getUserComissariaUsuariosRN()
								.retornaUsuarioComissaria(getUserMB().getUser());

						if (userComissaria == null) {
							setWhereSQL(getDtcDtaRN().retornaCondicaoFiltroTabelaComissariaMaster(getUserMB().getUser(),
									null, TipoFiltroEnum.IN));
						} else {
							setWhereSQL(getDtcDtaRN().retornaCondicaoFiltroTabelaComissariaMasterOuNotify(
									userComissaria.getComissariaMaster().getUsuario(), null, TipoFiltroEnum.IN));

							setWhereSQL(getDtcDtaRN().retornaCondicaoFiltroTabelaImportador(
									userComissaria.getComissariaMaster().getUsuario(), getWhereSQL(),
									TipoFiltroEnum.IN_SEM_WHERE));
						}
					}
				}
			}

			lazyModel = new NewLazyDataModel<CadastroDTCDTA>(getCamposTabelaPrincipal(),CadastroDTCDTA.sql, CadastroDTCDTA.sqlCount,
					getWhereSQL(), null);
		}

		return lazyModel;
	}

	@Override
	public void filtroTabela() {
		try {
			lazyModel = null;
			setWhereSQL(null);

			if (!getFiltroGeral().isEmpty() || getTipoModalSelecionadoEnum() != null || getLclSelecionado() != null) {
				int pesquisaModalidade = ModalidadeBLEnum.getPesquisaTabela(getFiltroGeral());
				int pesquisaStatusClif = StatusAtiLclFclEnum.getPesquisaTabela(getFiltroGeral());
				if (pesquisaModalidade != -1 || pesquisaStatusClif != -1) {
					if (pesquisaModalidade != -1) {
						setWhereSQL(FiltroTabelaBean.montaCondicaoSqlWhereAND(getWhereSQL(),
								CadastroDTCDTA.CAMPO_MODALIDADEBLENUM, TipoFiltroEnum.IGUAL_COM_WHERE,
								pesquisaModalidade));
					}

					if (pesquisaStatusClif != -1) {
						setWhereSQL(FiltroTabelaBean.montaCondicaoSqlWhereAND(getWhereSQL(),
								CadastroDTCDTA.CAMPO_STATUSATILCLFCLENUM, TipoFiltroEnum.IGUAL_COM_WHERE,
								pesquisaStatusClif));
					}
				} else {
					getFiltrosParametros().add(CadastroDTCDTA.CAMPO_NUMEROATI);
					getFiltrosParametros().add(CadastroDTCDTA.CAMPO_NAVIOVIAGEM);
					getFiltrosParametros().add(CadastroDTCDTA.CAMPO_VIAGEM);
					getFiltrosParametros().add(CadastroDTCDTA.CADASTRO_BL_DESCRICAOBL);
					getFiltrosParametros().add(CadastroDTCDTA.CAMPO_IMPORTADOR);

					setWhereSQL(FiltroTabelaBean.montaCondicaoSqlWhereOR(getFiltroGeral(), getFiltrosParametros()));
				}

				if (getUserMB().getUser().getRole() != Role.ADMIN) {
					if (getUserMB().getUser().getRole() == Role.IMPORTADOR_MASTER) {
						setWhereSQL(getDtcDtaRN().retornaCondicaoFiltroTabelaImportadorMaster(getUserMB().getUser(),
								getWhereSQL(), TipoFiltroEnum.IN_SEM_WHERE));
					}
					if (getUserMB().getUser().getRole() == Role.CLIENTE) {
						setWhereSQL(getDtcDtaRN().retornaCondicaoFiltroTabelaImportadorCliente(getUserMB().getUser(),
								getWhereSQL(), TipoFiltroEnum.IN_SEM_WHERE));
					}

					if (getUserMB().getUser().getRole() == Role.COMISSARIA_MASTER) {

						setWhereSQL(getDtcDtaRN().retornaCondicaoFiltroTabelaComissariaMasterOuNotify(
								getUserMB().getUser(), getWhereSQL(), TipoFiltroEnum.IN_SEM_WHERE));

						setWhereSQL(getDtcDtaRN().retornaCondicaoFiltroTabelaImportador(getUserMB().getUser(),
								getWhereSQL(), TipoFiltroEnum.IN_SEM_WHERE));
					}

					if (getUserMB().getUser().getRole() == Role.DESPACHANTE) {
						UserComissariaUsuarios userComissaria = getUserComissariaUsuariosRN()
								.retornaUsuarioComissaria(getUserMB().getUser());

						if (userComissaria == null) {
							setWhereSQL(getDtcDtaRN().retornaCondicaoFiltroTabelaComissariaMaster(getUserMB().getUser(),
									getWhereSQL(), TipoFiltroEnum.IN_SEM_WHERE));
						} else {
							setWhereSQL(getDtcDtaRN().retornaCondicaoFiltroTabelaComissariaMasterOuNotify(
									userComissaria.getComissariaMaster().getUsuario(), getWhereSQL(),
									TipoFiltroEnum.IN_SEM_WHERE));

							setWhereSQL(getDtcDtaRN().retornaCondicaoFiltroTabelaImportador(
									userComissaria.getComissariaMaster().getUsuario(), getWhereSQL(),
									TipoFiltroEnum.IN_SEM_WHERE));
						}
					}
				}

				if (getTipoModalSelecionadoEnum() != null) {
					setWhereSQL(
							FiltroTabelaBean.montaCondicaoSqlWhereAND(getWhereSQL(), CadastroDTCDTA.CAMPO_TIPO_MODAL,
									TipoFiltroEnum.IGUAL, getTipoModalSelecionadoEnum().getCodigo()));
				}

				if (getLclSelecionado() != null) {
					setWhereSQL(FiltroTabelaBean.montaCondicaoSqlWhereAND(getWhereSQL(), CadastroDTCDTA.CAMPO_LCL,
							TipoFiltroEnum.IGUAL, getLclSelecionado().getId()));
				}

			}
		} catch (Exception e) {
			setFiltrosParametros(null);
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	@Override
	public String getFiltroGeral() {
		return filtroGeral;
	}

	@Override
	public void setFiltroGeral(String filtroGeral) {
		this.filtroGeral = filtroGeral;
	}

	public void redirecionarCadastro() {
		try {
			setControlarFormCadastrar(true);
			setControlarFormListar(false);
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void voltarPgPrincipal() {
		try {
			setControlarFormCadastrar(false);
			setControlarFormListar(true);

			getDtcDtaRN().bloquearEdicaoRegistro(entidade, userMB.getUser(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<CadastroBLContanier> getListaBLContanier() {
		if (listaBLContanier == null && getEntidade() != null) {
			listaBLContanier = getEntidade().getCadastroBL().getListaCadastroBLContanier();
		}
		return listaBLContanier;
	}

	public void instanciarStatusContainer(CadastroDTCDTA entidade) {
		setEntidade(entidade);
		getListaBLContanier();
	}

	public void fecharStatusContainer() {
		entidade = null;
		listaBLContanier = null;
	}

	public void salvarStatusClif() {
		try {
			getDtcDtaRN().alterarSomenteEntidadeDtcDta(getEntidade());
			JSFMessageUtil.adicionarMensagemSucesso(MenssagensUtilEnum.OPERACAO_SUCESSO.getDescricao());
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemAlerta(e.getMessage());
		}
	}

	public void diferimento() {
		try {
			if (getEntidade() != null || getEntidade().getCadastroBL() != null) {
				float qtdeRegistros = getCadBLAnexosRN().executarQtdeRegistrosValor(getEntidade().getCadastroBL(),
						TipoAnexosEnum.ANEXO_LI);

				if (qtdeRegistros == 0) {
					getEntidade().setDeferido(false);
					JSFMessageUtil
							.adicionarMensagemErro("Não foi possivel selecionar o Diferimento, falta anexo da LI !");
				} else {
					getDtcDtaRN().alterar(getEntidade(), getListaMapaNCM());
					JSFMessageUtil.adicionarMensagemSucesso(MenssagensUtilEnum.OPERACAO_SALVO_SUCESSO.getDescricao());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void editarEntidadeEdicaoBL(CadastroDTCDTA entidade) {
		try {
			if (entidade != null) {

				if (entidade.isEnviarParaMapa()) {
					setControlarEdicaoDadosBL(true);
				} else {
					setControlarEdicaoDadosBL(false);
				}

				if (userMB.isAdmin() || userMB.getUser().isClif()) {
					setHabilitarDeferimento(false);
				}

				if (entidade.isDeferido()) {
					setHabilitarDeferimento(true);
				}

				setEntidadeDTCDTA(entidade);

				setHabilitarBotaoEnvioMapa(userMB.getUser().isEnviarMapa());
				setEntidade(getDtcDtaRN().bloquearEdicaoRegistro(entidade, userMB.getUser(), false));
			}
			listaBLContanier = null;

			setControlarFormCadastrar(true);
			setControlarFormListar(false);
			setControlarDados(false);
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	@Override
	public boolean isControlarFormCadastrar() {
		return controlarFormCadastrar;
	}

	@Override
	public void setControlarFormCadastrar(boolean controlarFormCadastrar) {
		this.controlarFormCadastrar = controlarFormCadastrar;
	}

	@Override
	public boolean isControlarFormListar() {
		return controlarFormListar;
	}

	@Override
	public void setControlarFormListar(boolean controlarFormListar) {
		this.controlarFormListar = controlarFormListar;
	}

	public CadastroDTCDTA getEntidade() {
		return entidade;
	}

	public void setEntidade(CadastroDTCDTA entidade) {
		this.entidade = entidade;
	}

	public String getControlarImagem() {
		return controlarImagem;
	}

	public void setControlarImagem(String controlarImagem) {
		this.controlarImagem = controlarImagem;
	}

	public boolean isControlarDados() {
		return controlarDados;
	}

	public void setControlarDados(boolean controlarDados) {
		this.controlarDados = controlarDados;
	}

	public void expandirdDados() {
		if (isControlarDados()) {
			setControlarDados(false);
			setControlarImagem("fa fa-angle-double-down");

			try {
				setEntidade(getDtcDtaRN().localizar(getEntidade().getId()));
			} catch (Exception e) {
			}
		} else {
			setControlarDados(true);
			setControlarImagem("fa fa-angle-double-up");

			try {
				setEntidade(getDtcDtaRN().localizar(getEntidade().getId()));
			} catch (Exception e) {
			}

			setPaisSelecionado(getEntidade().getPaisOrigem());
			setPaisProcedenciaSelecionado(getEntidade().getPaisProcedencia());
			setNcmSelecionado(getEntidade().getNcm());
			setPortoSelecionado(getEntidade().getPortos());
			setEspecieSelecionado(getEntidade().getTipoEmbalagemEspecie());
			setEmbalagemEncontradaSelecionado(getEntidade().getTipoEmbalagemEncontrada());
			setImportadorSelecionado(getEntidade().getImportador());
			setRepresentanteSelecionado(getEntidade().getRepresentante());
			setSelecionadoCargaImoPorBL(getEntidade().getCargaImo());
			setResponsavelLoteSelecionado(getEntidade().getResponsavelLote());
			setFaturarContraSelecionado(getEntidade().getFaturarContra());
			setComissarioSelecionado(getEntidade().getCadComissario());
		}
	}

	public void salvar() {
		try {
			if (getEntidade() != null) {
				JSFMessageUtil.adicionarMensagemSucesso(MenssagensUtilEnum.OPERACAO_SALVO_SUCESSO.getDescricao());
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	private CadastroBLAnexosRN getCadBLAnexosRN() {
		if (cadBLAnexosRN == null) {
			cadBLAnexosRN = new CadastroBLAnexosRN();
		}
		return cadBLAnexosRN;
	}

	public void upload(FileUploadEvent evento) throws SQLException, EmailException {
		try {
			CadastroBLAnexos itemAnexo = new CadastroBLAnexos();
			Path arquivoTemp = Files.createTempFile(null, null);
			Files.copy(evento.getFile().getInputstream(), arquivoTemp, StandardCopyOption.REPLACE_EXISTING);

			itemAnexo.setCadastroBL(getEntidade().getCadastroBL());
			itemAnexo.setTipoAnexosEnum(getSelecionadoTipoAnexos());
			itemAnexo.setCaminhoAnexo(evento.getFile().getFileName().replace(".pdf", "") + "_"
					+ getEntidade().getCadastroBL().getDescricaoBL() + "_" + getSelecionadoTipoAnexos().getDescricao()
					+ ".pdf");

			Path origem = Paths.get(arquivoTemp.toString());

			String destino_caminho = getJsfUtil().getCaminhoAnexos(getSelecionadoTipoAnexos());
			destino_caminho = destino_caminho + itemAnexo.getCaminhoAnexo();
			Path destino = Paths.get(destino_caminho);

			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);

			itemAnexo = getCadBLAnexosRN().alterar(itemAnexo, getUserMB().getUser());

			setListaAnexosPorTipo(listaBLTodosAnexos(itemAnexo.getTipoAnexosEnum()));

			if (getSelecionadoTipoAnexos() == TipoAnexosEnum.FISPQ) {
				envioEmailFispq(itemAnexo);
			}

			if (getSelecionadoTipoAnexos() == TipoAnexosEnum.CLIF_ANEXO) {
				envioEmailClifAnexo();
			}

			JSFMessageUtil.adicionarMensagemSucesso(MenssagensUtilEnum.OPERACAO_UPLOAD.getDescricao());
		} catch (IOException e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		} catch (Throwable e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
			e.printStackTrace();
		}
	}

	private void envioEmailClifAnexo() throws EmailException, IOException {
		User userDespachante = getEntidade().getUserCadastroDtc();

		EnvioEmailParametrosUTIL param1 = new EnvioEmailParametrosUTIL();
		param1.setAssunto("ATI -  " + getEntidade().getNumeroATI() + " NOVOS DOCUMENTOS ");
		param1.setConteudoEmail("Favor atentar que foram anexados, novos documentos na ABA anexo CLIF, ATI "
				+ getEntidade().getNumeroATI());

		getJsfUtil().envioEmailSimples(new StringTokenizer(userDespachante.getEmail(), ";"), param1.getAssunto(),
				param1.getConteudoEmail());
	}

	private void envioEmailFispq(CadastroBLAnexos itemAnexo) throws IOException, EmailException {
		EnvioEmailParametrosUTIL param = new EnvioEmailParametrosUTIL();
		param.setAssunto("ATI - FISPQ " + getEntidade().getNumeroATI());
		param.setConteudoEmail("Segue em anexo, documento FISPQ - Segurança Produtos Quimicos");

		String caminho = getJsfUtil().getCaminhoAnexos(getSelecionadoTipoAnexos()) + itemAnexo.getCaminhoAnexo();

		List<User> userPerfil = getUserRN().localizarUsuarioPorPerfil(Role.CLIF);
		for (User user : userPerfil) {
			getJsfUtil().envioEmailComAnexo(caminho, null, user.getEmail(), param, TipoExtensaoArquivoEnum.PDF);
		}
	}

	private UserFacade getUserRN() {
		if (userRN == null) {
			userRN = new UserFacade();
		}
		return userRN;
	}

	@Override
	public List<TipoAnexosEnum> getTodasTipoAnexosEnum() {
		if (todasTipoAnexosEnum == null) {
			todasTipoAnexosEnum = new ArrayList<TipoAnexosEnum>();
			todasTipoAnexosEnum.add(TipoAnexosEnum.STATUS_ANEXO);
			todasTipoAnexosEnum.add(TipoAnexosEnum.BL);
			todasTipoAnexosEnum.add(TipoAnexosEnum.CE);
			todasTipoAnexosEnum.add(TipoAnexosEnum.INVOICE);
			todasTipoAnexosEnum.add(TipoAnexosEnum.PACKING);
			todasTipoAnexosEnum.add(TipoAnexosEnum.DTC_DTA);
			todasTipoAnexosEnum.add(TipoAnexosEnum.CLIF_ANEXO);
			todasTipoAnexosEnum.add(TipoAnexosEnum.MAPA_ANEXO);
			todasTipoAnexosEnum.add(TipoAnexosEnum.COMPLEMENTARES_ANEXO);
			todasTipoAnexosEnum.add(TipoAnexosEnum.ANEXO_LI);
			todasTipoAnexosEnum.add(TipoAnexosEnum.FISPQ);
		}
		return todasTipoAnexosEnum;
	}

	@Override
	public void instanciarAnexos(TipoAnexosEnum item) {
		try {

			if (item != null) {
				setSelecionadoTipoAnexos(item);
				setControlarAnexos(true);
				setListaAnexosPorTipo(listaBLTodosAnexos(item));

				if (item == TipoAnexosEnum.STATUS_ANEXO) {
					RequestContext.getCurrentInstance().update("anexosGenerico:abas:0:frmAnexosStatus");
				} else {
					RequestContext.getCurrentInstance().update("anexosGenerico:abas:" + item.getPosicaoAbaTela()
							+ ":frmAnexosImportacao:tblAnexosImportacao");
				}

			} else {
				CadastroBL bl = getEntidadeDTCDTA().getCadastroBL();

				setRetornoBL(getCadBLAnexosRN().executarQtdeRegistros(bl, TipoAnexosEnum.BL));
				setRetornoCE(getCadBLAnexosRN().executarQtdeRegistros(bl, TipoAnexosEnum.CE));
				setRetornoDTC_DTA(getCadBLAnexosRN().executarQtdeRegistros(bl, TipoAnexosEnum.DTC_DTA));
				setRetornoINVOICE(getCadBLAnexosRN().executarQtdeRegistros(bl, TipoAnexosEnum.INVOICE));
				setRetornoPACKING(getCadBLAnexosRN().executarQtdeRegistros(bl, TipoAnexosEnum.PACKING));

				setSelecionadoTipoAnexos(TipoAnexosEnum.STATUS_ANEXO);

				RequestContext.getCurrentInstance().update("anexosGenerico:abas:0:frmAnexosStatus");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	@Override
	public List<CadastroBLAnexos> listaBLTodosAnexos(TipoAnexosEnum tipo) {
		List<CadastroBLAnexos> listaBLTodosAnexos = null;
		if (isControlarAnexos()) {
			CadastroBL bl = null;
			if (getEntidade() != null) {
				bl = getEntidade().getCadastroBL();
			}

			if (bl != null) {
				listaBLTodosAnexos = getCadBLAnexosRN().listaTodosPorTipoAnexos(bl, tipo);
			}
		}
		return listaBLTodosAnexos;
	}

	public void instanciarEntidadeAnexos(CadastroDTCDTA entidade) {
		boolean abriTela = false;

		try {

			if (entidade != null) {
				abriTela = true;
				setEntidade(entidade);
			} else {
				setEntidade(getEntidade());
			}

			CadastroBL bl = getEntidade().getCadastroBL();

			// setRetornoBL(getCadBLAnexosRN().executarQtdeRegistros(bl,
			// TipoAnexosEnum.BL));
			// setRetornoCE(getCadBLAnexosRN().executarQtdeRegistros(bl,
			// TipoAnexosEnum.CE));
			// setRetornoDTC_DTA(getCadBLAnexosRN().executarQtdeRegistros(bl,
			// TipoAnexosEnum.DTC_DTA));
			// setRetornoINVOICE(getCadBLAnexosRN().executarQtdeRegistros(bl,
			// TipoAnexosEnum.INVOICE));
			// setRetornoPACKING(getCadBLAnexosRN().executarQtdeRegistros(bl,
			// TipoAnexosEnum.PACKING));

			if (getCadBLAnexosRN().executarQtdeRegistrosValor(bl, TipoAnexosEnum.BL) == 0) {
				setRetornoBL(TipoAnexosEnum.BL + " -->> Pendente de Anexo");
				setRetornoCorBL("color: red");
			} else {
				setRetornoBL(TipoAnexosEnum.BL + " -->> Documento Anexado");
				setRetornoCorBL("color: black");
			}

			if (getCadBLAnexosRN().executarQtdeRegistrosValor(bl, TipoAnexosEnum.CE) == 0) {
				setRetornoCE(TipoAnexosEnum.CE + " -->> Pendente de Anexo");
				setRetornoCorCE("color: red");
			} else {
				setRetornoCE(TipoAnexosEnum.CE + " -->> Documento Anexado");
				setRetornoCorCE("color: black");
			}

			if (getCadBLAnexosRN().executarQtdeRegistrosValor(bl, TipoAnexosEnum.DTC_DTA) == 0) {
				setRetornoDTC_DTA(TipoAnexosEnum.DTC_DTA + " -->> Pendente de Anexo");
				setRetornoCorDTC_DTA("color: red");
			} else {
				setRetornoDTC_DTA(TipoAnexosEnum.DTC_DTA + " -->> Documento Anexado");
				setRetornoCorDTC_DTA("color: black");
			}

			if (getCadBLAnexosRN().executarQtdeRegistrosValor(bl, TipoAnexosEnum.INVOICE) == 0) {
				setRetornoINVOICE(TipoAnexosEnum.INVOICE + " -->> Pendente de Anexo");
				setRetornoCorINVOICE("color: red");
			} else {
				setRetornoINVOICE(TipoAnexosEnum.INVOICE + " -->> Documento Anexado");
				setRetornoCorINVOICE("color: black");
			}

			if (getCadBLAnexosRN().executarQtdeRegistrosValor(bl, TipoAnexosEnum.PACKING) == 0) {
				setRetornoPACKING(TipoAnexosEnum.PACKING + " -->> Pendente de Anexo");
				setRetornoCorPACKING("color: red");
			} else {
				setRetornoPACKING(TipoAnexosEnum.PACKING + " -->> Documento Anexado");
				setRetornoCorPACKING("color: black");
			}

			setControlarAnexos(true);
			setSelecionadoTipoAnexos(TipoAnexosEnum.STATUS_ANEXO);

			if (abriTela) {
				RequestContext.getCurrentInstance().execute("PF('dlgAnexosGenerico').show();");
			}
			RequestContext.getCurrentInstance().update("anexosGenerico:abas:0:frmAnexosStatus");
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	@Override
	public boolean visualizarAnexosUpload(TipoAnexosEnum item) {
		boolean valor = true;
		if (item.getCodigo() == 5 && isControlarAnexos()) {
			valor = false;
		}
		return valor;
	}

	@Override
	public void fecharAnexo() {
		try {
			setControlarAnexos(false);
			getEntidade().setProcessoBloqueado(null);
			getDtcDtaRN().alterarSemRelacionamento(getEntidade());
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	@Override
	public boolean visualizarAnexosStatus(TipoAnexosEnum item) {
		boolean valor = false;
		if (item.getCodigo() == 5 && isControlarAnexos()) {
			valor = true;
		}
		return valor;
	}

	private JSFUtil getJsfUtil() {
		if (jsfUtil == null) {
			jsfUtil = new JSFUtil();
		}
		return jsfUtil;
	}

	@Override
	public TipoAnexosEnum getSelecionadoTipoAnexos() {
		return selecionadoTipoAnexos;
	}

	@Override
	public void setSelecionadoTipoAnexos(TipoAnexosEnum selecionadoTipoAnexos) {
		this.selecionadoTipoAnexos = selecionadoTipoAnexos;
	}

	@Override
	public UserMB getUserMB() {
		return userMB;
	}

	@Override
	public void setUserMB(UserMB userMB) {
		this.userMB = userMB;
	}

	// public List<CadastroBLAnexos> listaBLTodosAnexos(TipoAnexosEnum tipo) {
	// List<CadastroBLAnexos> listaBLTodosAnexos = null;
	// if (getEntidade() != null && isControlarAnexos()) {
	// if (getEntidade().getCadastroBL() != null) {
	// listaBLTodosAnexos =
	// getCadBLAnexosRN().listaTodosPorTipoAnexos(getEntidade().getCadastroBL(),
	// tipo);
	// }
	// }
	// return listaBLTodosAnexos;
	// }

	public void download(javax.faces.event.ActionEvent evento) {
		try {
			anexo = (CadastroBLAnexos) evento.getComponent().getAttributes().get("anexoSelecionado");

			FileInputStream stream = null;

			stream = new FileInputStream(
					getJsfUtil().getCaminhoAnexos(getSelecionadoTipoAnexos()) + anexo.getCaminhoAnexo());

			setPdf(new DefaultStreamedContent(stream, "application/pdf", anexo.getCaminhoAnexo()));
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public StreamedContent getPdf() {
		return pdf;
	}

	public void setPdf(StreamedContent pdf) {
		this.pdf = pdf;
	}

	@Override
	public boolean isControlarAnexos() {
		return controlarAnexos;
	}

	@Override
	public void setControlarAnexos(boolean controlarAnexos) {
		this.controlarAnexos = controlarAnexos;
	}

	public TipoSimNaoEnum getSelecionadoCargaImoPorBL() {
		return selecionadoCargaImoPorBL;
	}

	public void setSelecionadoCargaImoPorBL(TipoSimNaoEnum selecionadoCargaImoPorBL) {
		this.selecionadoCargaImoPorBL = selecionadoCargaImoPorBL;
	}

	public boolean isControlarEdicaoDadosBL() {
		return controlarEdicaoDadosBL;
	}

	public void setControlarEdicaoDadosBL(boolean controlarEdicaoDadosBL) {
		this.controlarEdicaoDadosBL = controlarEdicaoDadosBL;
	}

	@Override
	public String getRetornoBL() {
		return retornoBL;
	}

	@Override
	public void setRetornoBL(String retornoBL) {
		this.retornoBL = retornoBL;
	}

	@Override
	public String getRetornoCE() {
		return retornoCE;
	}

	@Override
	public void setRetornoCE(String retornoCE) {
		this.retornoCE = retornoCE;
	}

	@Override
	public String getRetornoDTC_DTA() {
		return retornoDTC_DTA;
	}

	@Override
	public void setRetornoDTC_DTA(String retornoDTC_DTA) {
		this.retornoDTC_DTA = retornoDTC_DTA;
	}

	@Override
	public String getRetornoINVOICE() {
		return retornoINVOICE;
	}

	@Override
	public void setRetornoINVOICE(String retornoINVOICE) {
		this.retornoINVOICE = retornoINVOICE;
	}

	@Override
	public String getRetornoPACKING() {
		return retornoPACKING;
	}

	@Override
	public void setRetornoPACKING(String retornoPACKING) {
		this.retornoPACKING = retornoPACKING;
	}

	public void removerAnexo(CadastroBLAnexos item) {
		try {
			getCadBLAnexosRN().deletar(item);

			setListaAnexosPorTipo(listaBLTodosAnexos(item.getTipoAnexosEnum()));

			RequestContext.getCurrentInstance().update("anexosGenerico:abas:"
					+ getSelecionadoTipoAnexos().getPosicaoAbaTela() + ":frmAnexosImportacao:tblAnexosImportacao");
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public boolean esconderColuna(CadastroBLAnexos item) {
		boolean valor = true;

		if (item != null) {
			if (item.getCadastroBL() != null) {
				if (item.getCadastroBL().getStatusAtiLclFclEnum().equals(StatusAtiLclFclEnum.AGUARDANDO_TRANSPORTE)) {
					valor = false;
				}
			}
		}

		return valor;
	}

	public String corStatusAnexo(CadastroDTCDTA entidade, int tipo) {
		String valor = "color: black";

		try {

			if (getSelecionadoTipoAnexos() == TipoAnexosEnum.STATUS_ANEXO) {
				TipoAnexosEnum tipoAnexo = null;
				if (tipo == 0) {
					tipoAnexo = TipoAnexosEnum.BL;
				}
				if (tipo == 1) {
					tipoAnexo = TipoAnexosEnum.CE;
				}
				if (tipo == 2) {
					tipoAnexo = TipoAnexosEnum.INVOICE;
				}
				if (tipo == 3) {
					tipoAnexo = TipoAnexosEnum.PACKING;
				}
				if (tipo == 4) {
					tipoAnexo = TipoAnexosEnum.DTC_DTA;
				}

				if (entidade != null) {
					setEntidade(entidade);
				} else {
					setEntidade(getEntidade());
				}

				CadastroBL bl = getEntidade().getCadastroBL();

				float qtde = getCadBLAnexosRN().executarQtdeRegistrosValor(bl, tipoAnexo);

				if (qtde == 0) {
					valor = "color: red";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
		return valor;
	}

	public String corBotaoAnexo(CadastroDTCDTA entidade) {
		String valor = "color: none";

		try {

			if (entidade != null) {
				setEntidade(entidade);
			} else {
				setEntidade(getEntidade());
			}

			CadastroBL bl = getEntidade().getCadastroBL();

			float blQtde = getCadBLAnexosRN().executarQtdeRegistrosValor(bl, TipoAnexosEnum.BL);
			float ceQtde = getCadBLAnexosRN().executarQtdeRegistrosValor(bl, TipoAnexosEnum.CE);
			float dtcDtaQtde = getCadBLAnexosRN().executarQtdeRegistrosValor(bl, TipoAnexosEnum.DTC_DTA);
			float invoiceQtde = getCadBLAnexosRN().executarQtdeRegistrosValor(bl, TipoAnexosEnum.INVOICE);
			float packingQtde = getCadBLAnexosRN().executarQtdeRegistrosValor(bl, TipoAnexosEnum.PACKING);

			if (blQtde > 0 && ceQtde > 0 && dtcDtaQtde > 0 && invoiceQtde > 0 && packingQtde > 0) {
				valor = "color: greenyellow";
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}

		return valor;
	}

	public void envioProcessoMapa() {
		try {
			if (getEntidade() != null) {
				getEntidade().setEnviarParaMapa(true);
				getEntidade().setEnvioMapaValidar(true);
				getEntidade().setUserLogado(userMB.getUser());
				getEntidade().setDataEnvioMapa(new Date());
				getEntidade().setStatusBLEnum(StatusBLEnum.AGUARDANDO_LIBERACAO_PROCESSOS_QUE_ANEXARAM);
				getEntidade().getCadastroBL().setStatusBLEnum(StatusBLEnum.AGUARDANDO_LIBERACAO_PROCESSOS_QUE_ANEXARAM);

				getDtcDtaRN().alterar(getEntidade(), getListaMapaNCM());

				getDtcDtaRN().localizarRegistroFitosanitario(getEntidade());

				JSFMessageUtil.adicionarMensagemSucesso(MenssagensUtilEnum.OPERACAO_PROCESSO_SUCESSO.getDescricao());
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage() != null) {
				if (e.getMessage().equals("Esse processo sera enviado pelo CLIF apos validacao do dados")) {
					JSFMessageUtil.adicionarMensagemAlerta(e.getMessage());
				} else {
					JSFMessageUtil.adicionarMensagemErro(e.getMessage());
				}
			}
		}
	}

	public void cancelarProcesso() {
		try {
			if (getEntidade() != null) {
				getEntidade().setEnvioMapaValidar(false);

				getEntidade().setStatusAtiLclFclEnum(StatusAtiLclFclEnum.CANCELADO);
				getEntidade().setMotivoCancelamento(getTextoMotivoCancelamento());
				getEntidade().setEnviarParaMapa(false);
				getDtcDtaRN().alterar(getEntidade(), getListaMapaNCM());
				JSFMessageUtil.adicionarMensagemSucesso(MenssagensUtilEnum.OPERACAO_CANCELADA.getDescricao());
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void instanciarCancelamento() {
		setTextoMotivoCancelamento(null);
	}

	public String getTextoMotivoCancelamento() {
		return textoMotivoCancelamento;
	}

	public void setTextoMotivoCancelamento(String textoMotivoCancelamento) {
		this.textoMotivoCancelamento = textoMotivoCancelamento;
	}

	public void envioEmailDocFaltantes() {
		try {
			getDtcDtaRN().enviarEmailDocumentosFaltantes(getEntidade(), userMB.getUser(),
					getSelecionadoPendenciasEmailEnum(), getTextoMaoLivreEmail());

			JSFMessageUtil.adicionarMensagemSucesso(MenssagensUtilEnum.OPERACAO_EMAIL_SUCESSO.getDescricao());
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void instanciarEmail(int valor) {
		try {
			String texto = "";

			setTextoMaoLivreEmail(null);
			setAssuntoEmail(null);

			setMostrarCampo(false);
			setMostrarCampoAssunto(false);
			if (valor == 0) {
				setMostrarCampo(true);
				setUtilizarEnvioEmailRetiraSegregacao(0);
				setTipoEnvioEmail("");
			}
			/**
			 * Tipo segrega��o
			 */
			if (valor == 1) {
				setUtilizarEnvioEmailRetiraSegregacao(2);
				setTipoEnvioEmail("Segrega��o");

				texto = "Bom dia, " + "\n\n";
				texto = texto + "Favor incluir os processos em anexo em segregacao para remocao via DTC. " + "\n\n";
				texto = texto + getEntidade().getSegregacaoCeMaster() + "\n\n";
				setAssuntoEmail("DTC - SEGREGADO " + getEntidade().getProgramacaoNavio().getNavio());
			}
			/**
			 * Tipo retirada
			 */
			if (valor == 2) {
				setUtilizarEnvioEmailRetiraSegregacao(1);
				setTipoEnvioEmail("Retirada Importa��o");

				texto = "Bom dia, " + "\n\n";
				texto = texto + "Segue documentacao para transferencia de DTC.  " + "\n\n";
				texto = texto + "Ficamos no aguardo da confirmacao da documentacao/faturamento.  " + "\n\n";
				texto = texto + "Faturamento, favor faturar o processo contra o Adquirente informado no formulario.  "
						+ "\n\n";
				texto = texto + getEntidade().getNumeroProcesso() + "\n\n";
				setAssuntoEmail("DTC - " + getEntidade().getNumeroProcesso() + " - "
						+ getEntidade().getImportador().getRazaoSocial().substring(0, 10) + " - "
						+ getEntidade().getProgramacaoNavio().getNavio() + " - " + getEntidade().getReferenciaCliente()
						+ " - ATI - " + getEntidade().getNumeroATI());
			}

			if (valor == 1 || valor == 2) {
				try {
					setEntidade(getDtcDtaRN().salvarSemRelacionaMento(getEntidade()));
				} catch (Throwable e) {
					e.printStackTrace();
				}

				setTextoMaoLivreEmail(texto);
				setMostrarCampoAssunto(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void imprimirRetiradaImportacao(TipoRelatoriosEnum tipoRel, boolean envioEmail, CadastroDTCDTA entidade,
			TipoExtensaoArquivoEnum extensao) {
		try {
			LoginController login = new LoginController();
			User user = login.getRetornaUsuarioLogado();

			String caminhoLogoPorto = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/resources/images/logoPorto.png");
			byte[] arquivoBytelogoPorto = getJsfUtil().converteArquivoToByte(caminhoLogoPorto);

			String caminhoLogoPortoSgi = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/resources/images/logoPortoSgi.png");
			byte[] arquivoBytelogoPortoSgi = getJsfUtil().converteArquivoToByte(caminhoLogoPortoSgi);

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("PED_ID", getEntidade().getId());
			parametros.put("PARAM_USERLOGADO", user.getName().toUpperCase());
			parametros.put("PARAM_CPF_USER", user.getCpf());
			parametros.put("PARAM_TITULO", tipoRel.getTituloRelatorio());
			parametros.put("PARAM_LOGO", getJsfUtil().getLogoImage(arquivoBytelogoPorto));
			parametros.put("PARAM_LOGO2", getJsfUtil().getLogoImage(arquivoBytelogoPortoSgi));
			parametros.put("PARAM_VALORCIF", getValorConvertidoReais());

			String tipoValorSim = " ";
			String tipoValorNao = " X ";
			if (paramContainerparte == TipoSimNaoEnum.SIM) {
				tipoValorSim = " X ";
				tipoValorNao = " ";
			}

			parametros.put("PARAM_CNTR_PARTE_SIM", tipoValorSim);
			parametros.put("PARAM_CNTR_PARTE_NAO", tipoValorNao);

			if (tipoRel == TipoRelatoriosEnum.REL_RETIRADAIMPORTACAO) {
				if (user.getImagem() != null) {
					parametros.put("PARAM_ASSINATURA", getJsfUtil().getLogoImage(user.getImagem()));
				}

				if (entidade.getId() >= 830) {
					parametros.put("FAT_CONTRA_EMAIL", entidade.getFaturarContraEmail());
				} else {
					parametros.put("FAT_CONTRA_EMAIL", entidade.getFaturarContra().getEmail());
				}
			}

			if (envioEmail) {
				EnvioEmailParametrosUTIL paramEmail = new EnvioEmailParametrosUTIL();
				String caminhoArquivo = null;
				String destinatario = null;
				File arqTemp = null;
				if (tipoRel == TipoRelatoriosEnum.REL_REQUISICAO_SEGREGACAO) {
					caminhoArquivo = getDtcDtaRN().gerarSegregacaoExcel(entidade);

					paramEmail.setComCopia(
							"faturamento.importacao@portoitapoa.com.br;Opr-Importacao@portoitapoa.com.br;transportes@oclif.com.br;cda@oclif.com.br");
					destinatario = "opr-dtc@portoitapoa.com.br";
				} else {
					arqTemp = getJsfUtil().gerarRelatorio(tipoRel, parametros, getEntidade().getId().toString(), false,
							true);

					caminhoArquivo = arqTemp.getPath();
				}

				paramEmail.setAssunto(getAssuntoEmail());
				paramEmail.setConteudoEmail(getTextoMaoLivreEmail());
				paramEmail.setNomeArquivo("DTC - " + entidade.getId());
				paramEmail.getVariosAnexos().put(caminhoArquivo, "DTC - " + entidade.getId());
				if (tipoRel == TipoRelatoriosEnum.REL_RETIRADAIMPORTACAO) {
					paramEmail.setComCopia(
							"faturamento.importacao@portoitapoa.com.br;Opr-Importacao@portoitapoa.com.br;transportes@oclif.com.br;cda@oclif.com.br");
					destinatario = "opr-dtc@portoitapoa.com.br";

					List<CadastroBLAnexos> list = getCadBLAnexosRN().listaAnexosPorTipoEmail(entidade.getCadastroBL());
					for (CadastroBLAnexos item : list) {
						String caminhoAnexos = getJsfUtil().getCaminhoAnexos(item.getTipoAnexosEnum());
						caminhoAnexos = caminhoAnexos + item.getCaminhoAnexo();

						paramEmail.getVariosAnexos().put(caminhoAnexos, "DTC - " + entidade.getId());
					}
				}

				// paramEmail.setComCopia("adilsoferreira@oclif.com.br;origemsistema@gmail.com");
				// destinatario =
				// "murilo.nadalin@gmail.com;origemsistema@gmail.com";

				getJsfUtil().envioEmailComAnexo(caminhoArquivo, user, destinatario, paramEmail, extensao);

				if (arqTemp != null) {
					getJsfUtil().deletarArquivoTemporario(arqTemp);
				}
			} else {
				getJsfUtil().gerarRelatorio(tipoRel, parametros, getEntidade().getId().toString(), false, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void instalanciarSegregacao() {
		try {
			setEntidade(getDtcDtaRN().localizar(getEntidade().getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void atualizaDadosAdquirente() {
		getEntidade().setAdquirenteNome(getEntidade().getFaturarContra().getRazaoSocial());
		getEntidade().setAdquirenteCNPJ(getEntidade().getFaturarContra().getCnpj());
	}

	public void salvarImprimir(int tipoRelatorio, boolean envioEmail) {
		try {
			CadastroDTCDTA antigo = getDtcDtaRN().localizar(getEntidade().getId());
			CadastroDTCDTA atual = getEntidade();
			setEntidade(antigo);

			TipoRelatoriosEnum rel = null;
			TipoExtensaoArquivoEnum extensao = null;
			if (tipoRelatorio == 1) {
				rel = TipoRelatoriosEnum.REL_RETIRADAIMPORTACAO;
				extensao = TipoExtensaoArquivoEnum.PDF;

				getEntidade().setNumeroBlMaster(atual.getNumeroBlMaster());
				getEntidade().setNumeroProcesso(atual.getNumeroProcesso());
				getEntidade().setAdquirenteNome(atual.getAdquirenteNome());
				getEntidade().setAdquirenteCNPJ(atual.getAdquirenteCNPJ());
				getEntidade().setAdquirenteBlHouse(atual.getAdquirenteBlHouse());
				getEntidade().setFaturarContraEmail(atual.getFaturarContraEmail());
				getEntidade().setObservacao(atual.getObservacao());
				getEntidade().setRetiradaObservacaoInvoice(atual.getRetiradaObservacaoInvoice());
				getEntidade().setCifMercadoria(getValorConvertidoReais());
			}
			if (tipoRelatorio == 2) {
				rel = TipoRelatoriosEnum.REL_REQUISICAO_SEGREGACAO;
				extensao = TipoExtensaoArquivoEnum.EXECEL_XLS;

				getEntidade().setAgenteCnpj(atual.getAgenteCnpj());
				getEntidade().setAgenteNome(atual.getAgenteNome());
				getEntidade().setSegregacaoCeHouse(atual.getSegregacaoCeHouse());
				getEntidade().setSegregacaoCeMaster(atual.getSegregacaoCeMaster());
			}

			setEntidade(getDtcDtaRN().salvarSemRelacionaMento(getEntidade()));
			imprimirRetiradaImportacao(rel, envioEmail, getEntidade(), extensao);

			if (envioEmail) {
				if (tipoRelatorio == 1) {
					getEntidade().setStatusAtiLclFclEnum(StatusAtiLclFclEnum.AGUARDANDO_TRANSPORTE);
				}
				if (tipoRelatorio == 2) {
					getEntidade().setStatusAtiLclFclEnum(StatusAtiLclFclEnum.SEGREDADO);
				}
				setEntidade(getDtcDtaRN().salvarSemRelacionaMento(getEntidade()));
				JSFMessageUtil.adicionarMensagemSucesso(MenssagensUtilEnum.OPERACAO_SUCESSO.getDescricao());
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		} catch (Throwable e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void antesImprimir() {
		try {
			setEntidade(getDtcDtaRN().localizar(getEntidade().getId()));

			setValorConvertidoReais(getEntidade().getCifMercadoria());
			// setTxDolar(getDtcDtaRN().retornarTaxaDolar());

			RequestContext.getCurrentInstance().update("imprimir:frmBLIncluir:inpValorCif");
			RequestContext.getCurrentInstance().update("imprimir:frmBLIncluir:inpTxDolar");
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public String getTextoMaoLivreEmail() {
		return textoMaoLivreEmail;
	}

	public void setTextoMaoLivreEmail(String textoMaoLivreEmail) {
		this.textoMaoLivreEmail = textoMaoLivreEmail;
	}

	public TipoPendenciaDocumentalEmailEnum getSelecionadoPendenciasEmailEnum() {
		return selecionadoPendenciasEmailEnum;
	}

	public void setSelecionadoPendenciasEmailEnum(TipoPendenciaDocumentalEmailEnum selecionadoPendenciasEmailEnum) {
		this.selecionadoPendenciasEmailEnum = selecionadoPendenciasEmailEnum;
	}

	public float getValorConvertidoReais() {
		return valorConvertidoReais;
	}

	public void setValorConvertidoReais(float valorConvertidoReais) {
		this.valorConvertidoReais = valorConvertidoReais;
	}

	public float getTxDolar() {
		return txDolar;
	}

	public void setTxDolar(float txDolar) {
		this.txDolar = txDolar;
	}

	public boolean isHabilitarBotaoEnvioMapa() {
		return habilitarBotaoEnvioMapa;
	}

	public void setHabilitarBotaoEnvioMapa(boolean habilitarBotaoEnvioMapa) {
		this.habilitarBotaoEnvioMapa = habilitarBotaoEnvioMapa;
	}

	public boolean isHabilitarDeferimento() {
		return habilitarDeferimento;
	}

	public void setHabilitarDeferimento(boolean habilitarDeferimento) {
		this.habilitarDeferimento = habilitarDeferimento;
	}

	public boolean controlaStatusClif() {
		boolean valor = true;

		if (getUserMB().getUser().isClif() || getUserMB().getUser().isAdmin()) {
			valor = false;
		}

		return valor;
	}

	public String getAssuntoEmail() {
		return assuntoEmail;
	}

	public void setAssuntoEmail(String assuntoEmail) {
		this.assuntoEmail = assuntoEmail;
	}

	public boolean isMostrarCampo() {
		return mostrarCampo;
	}

	public void setMostrarCampo(boolean mostrarCampo) {
		this.mostrarCampo = mostrarCampo;
	}

	public boolean isMostrarCampoAssunto() {
		return mostrarCampoAssunto;
	}

	public void setMostrarCampoAssunto(boolean mostrarCampoAssunto) {
		this.mostrarCampoAssunto = mostrarCampoAssunto;
	}

	public void salvarAnexoAlteracao(CadastroBLAnexos item) {
		try {
			getCadBLAnexosRN().salvar(item);
		} catch (Throwable e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public int getUtilizarEnvioEmailRetiraSegregacao() {
		return utilizarEnvioEmailRetiraSegregacao;
	}

	public void setUtilizarEnvioEmailRetiraSegregacao(int utilizarEnvioEmailRetiraSegregacao) {
		this.utilizarEnvioEmailRetiraSegregacao = utilizarEnvioEmailRetiraSegregacao;
	}

	public String getTipoEnvioEmail() {
		return tipoEnvioEmail;
	}

	public void setTipoEnvioEmail(String tipoEnvioEmail) {
		this.tipoEnvioEmail = tipoEnvioEmail;
	}

	public void onRowEdit(RowEditEvent event) {
		JSFMessageUtil.adicionarMensagemSucesso(MenssagensUtilEnum.OPERACAO_SUCESSO.getDescricao());
	}

	public void onRowCancel(RowEditEvent event) {
		JSFMessageUtil.adicionarMensagemAlerta(MenssagensUtilEnum.OPERACAO_CANCELADA.getDescricao());
	}

	public void fecharTela() {
		try {
			ServicoPentahoPDI pdi = new ServicoPentahoPDI();
			pdi.pentahoPdiExecutarJOB("C:/services/arquivo/pentaho/job_correcao_agendamento.kjb", null, null, null,
					null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public TipoSimNaoEnum getParamContainerparte() {
		return paramContainerparte;
	}

	public void setParamContainerparte(TipoSimNaoEnum paramContainerparte) {
		this.paramContainerparte = paramContainerparte;
	}

	public CadastroBLContanierLCL getLclSelecionado() {
		return lclSelecionado;
	}

	public void setLclSelecionado(CadastroBLContanierLCL lclSelecionado) {
		this.lclSelecionado = lclSelecionado;
	}

	public List<CadastroDTCDTA> getEntidadeFiltrodadas() {
	
		return entidadeFiltrodadas;
	}

	public void setEntidadeFiltrodadas(List<CadastroDTCDTA> entidadeFiltrodadas) {
		this.entidadeFiltrodadas = entidadeFiltrodadas;
	}

	public StatusBLEnum[] getStatusBLEnum() {
		return statusBLEnum.values();
	}

		
	public Map<String, String> getCamposTabelaPrincipal() {
		Map<String, String> campos= new HashMap<String, String>();
		campos.put("dataCadastro", "Date");
		campos.put("numeroATI", "String");
		campos.put("importador.razaoSocial", "String");
		campos.put("programacaoNavio.navio", "String");
		campos.put("cadastroBL.statusBLEnum", "int");
		return campos;
	}
	
	

}
