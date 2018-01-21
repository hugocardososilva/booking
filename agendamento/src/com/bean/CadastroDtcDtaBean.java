package com.bean;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.commons.mail.EmailException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.StreamedContent;

import com.entidade.CadastroDTCDTA;
import com.entidade.UserComissariaUsuarios;
import com.rn.CadastroDTCDTARN;
import com.rn.UserComissariaUsuariosRN;
import com.rn.UserImportadorUsuariosRN;
import com.util.DataUTIL;
import com.util.JSFMessageUtil;

import conexao.com.bean.AbstractLazyDataModel;
import conexao.com.enums.MenssagensUtilEnum;
import conexao.com.enums.TipoFiltroEnum;
import conexao.com.rn.CadastroBLAnexosRN;
import conexao.com.rn.CadastroBLContanierRN;
import conexao.com.rn.ProgramacaoNavioRN;
import conexao.com.util.FiltroTabelaBean;
import conexao.com.util.IGenericBean;
import conexao.com.util.JSFUtil;
import seguranca.com.entidade.CadastroBL;
import seguranca.com.entidade.CadastroBLAnexos;
import seguranca.com.entidade.CadastroBLContanier;
import seguranca.com.enums.ModalidadeBLEnum;
import seguranca.com.enums.Role;
import seguranca.com.enums.StatusAtiLclFclEnum;
import seguranca.com.enums.StatusBLEnum;
import seguranca.com.enums.TipoAnexosEnum;
import seguranca.com.enums.TipomodalEnum;

@ViewScoped
@ManagedBean
public class CadastroDtcDtaBean extends CadastroDtcDtaComumBean implements IGenericBean {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	private static final String GRID_CONTAINER = "frmCadastrarEntidade:formCadEntidadeBL:tbListaTodosContanier";

	private String numeroContainer;
	private String lacreContainer;
	private boolean camposObrigatorios;
	private boolean controlaSalvar;
	private boolean controlaSalvarRasqunho;

	private CadastroDTCDTA entidade;

	private CadastroDTCDTARN cadDtcDtaRN;
	private CadastroBLAnexosRN cadBLAnexosRN;
	private CadastroBLContanierRN cadBLContanierRN;

	private List<CadastroBLContanier> listaBLContanier;

	private LazyDataModel<CadastroDTCDTA> lazyModel;

	private JSFUtil jsfUtil;

	private CadastroBLAnexos anexo;

	private StreamedContent pdf;

	private ProgramacaoNavioRN programacaoNavioRN;

	private UserImportadorUsuariosRN importadorUsuariosRN;

	private UserComissariaUsuariosRN userComissariaUsuariosRN;

	public CadastroBLContanierRN getCadBLContanierRN() {
		if (cadBLContanierRN == null) {
			cadBLContanierRN = new CadastroBLContanierRN();
		}

		return cadBLContanierRN;
	}

	public CadastroBLAnexosRN getCadBLAnexosRN() {
		if (cadBLAnexosRN == null) {
			cadBLAnexosRN = new CadastroBLAnexosRN();
		}
		return cadBLAnexosRN;
	}

	public CadastroDTCDTARN getCadastroDtcDtaRN() {
		if (cadDtcDtaRN == null) {
			cadDtcDtaRN = new CadastroDTCDTARN();
		}
		return cadDtcDtaRN;
	}

	public void redirecionarCadastro(Boolean obrigatorio) {
		try {
			// atualizaProgramacaoNavio();
			setBloquearNavio(false);

			if (obrigatorio) {
				setCamposObrigatorios(true);
				setControlaSalvar(true);
				setControlaSalvarRasqunho(false);
			} else {
				setCamposObrigatorios(false);
				setControlaSalvar(false);
				setControlaSalvarRasqunho(true);
			}

			setListaMapaNCM(null);
			setPaisSelecionado(null);
			setPaisProcedenciaSelecionado(null);
			setNcmSelecionado(null);
			setPortoSelecionado(null);
			setEspecieSelecionado(null);
			setEmbalagemEncontradaSelecionado(null);
			setImportadorSelecionado(null);
			setRepresentanteSelecionado(null);
			setProgramacaoNavioSelecionado(null);
			setFaturarContraSelecionado(null);
			setResponsavelLoteSelecionado(null);
			setComissarioSelecionado(null);

			setControlarFormCadastrar(true);
			setControlarFormListar(false);
			setEntidade(new CadastroDTCDTA());
			setEntidadeDTCDTA(getEntidade());
			atualizarFormCadastroUsuario();
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	private void atualizarFormCadastroUsuario() {
		listaBLContanier = null;

		RequestContext.getCurrentInstance().update(PG_FORM_CADASTRAR_ENTIDADE);
		RequestContext.getCurrentInstance().update(GRID_CONTAINER);
	}

	public void editarEntidade(CadastroDTCDTA entidade) {
		try {
			setBloquearNavio(false);
			if (entidade.getTipoModalEnum() != TipomodalEnum.DTC) {
				setBloquearNavio(true);
			}

			setControlaSalvar(true);
			setControlaSalvarRasqunho(false);
			setCamposObrigatorios(true);

			setPaisSelecionado(entidade.getPaisOrigem());
			setPaisProcedenciaSelecionado(entidade.getPaisProcedencia());
			setNcmSelecionado(entidade.getNcm());
			setPortoSelecionado(entidade.getPortos());
			setEspecieSelecionado(entidade.getTipoEmbalagemEspecie());
			setEmbalagemEncontradaSelecionado(entidade.getTipoEmbalagemEncontrada());
			setImportadorSelecionado(entidade.getImportador());
			setRepresentanteSelecionado(entidade.getRepresentante());
			setResponsavelLoteSelecionado(entidade.getResponsavelLote());
			setFaturarContraSelecionado(entidade.getFaturarContra());
			setProgramacaoNavioSelecionado(entidade.getProgramacaoNavio());
			setComissarioSelecionado(entidade.getCadComissario());

			setControlarFormCadastrar(true);
			setControlarFormListar(false);
			setEntidade(entidade);
			setEntidadeDTCDTA(entidade);
			atualizarFormCadastroUsuario();
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void incluirContainer() {
		try {
			if (getCadBLContanierRN().validarContanier(getNumeroContainer().toUpperCase())) {
				CadastroBLContanier blContanier = new CadastroBLContanier();
				blContanier.setNumeroContanier(getNumeroContainer().toUpperCase());
				blContanier.setNumeroLacre(getLacreContainer().toUpperCase());
				blContanier.setCargaImo(getSelecionadoCargaImo());
				blContanier.setStatusBLEnum(StatusBLEnum.PENDENTE_ANEXO);

				if (getEntidade().getCadastroBL() != null) {
					blContanier.setCadastroBL(getEntidade().getCadastroBL());
				}

				getListaBLContanier().add(blContanier);
				getCadBLContanierRN().incluir(blContanier);

				setEntidade(getCadastroDtcDtaRN().localizar(getEntidade().getId()));

				numeroContainer = null;
				lacreContainer = null;
				setNumeroContainer(null);
				setLacreContainer(null);
				JSFMessageUtil.adicionarMensagemSucesso(MenssagensUtilEnum.OPERACAO_SUCESSO.getDescricao());
			} else {
				JSFMessageUtil.adicionarMensagemErro("Numero container nao valido !");
			}
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
			e.printStackTrace();
		}
	}

	public List<CadastroBLContanier> getListaBLContanier() {
		try {
			if (listaBLContanier == null) {
				if (getEntidade() != null) {
					if (getEntidade().getCadastroBL() != null) {
						listaBLContanier = getEntidade().getCadastroBL().getListaCadastroBLContanier();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaBLContanier;
	}

	public void removerContanier(CadastroBLContanier contanier) {
		try {
			if (contanier.getId() != null) {
				getCadBLContanierRN().deletar(contanier);
			}
			getEntidade().getCadastroBL().getListaCadastroBLContanier().remove(contanier);
			listaBLContanier = null;

		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
			e.printStackTrace();
		}
	}

	public void voltarPgPrincipal() {
		setControlarFormCadastrar(false);
		setControlarFormListar(true);
	}

	public void salvarEntidadeRasqunho() {
		try {
			setarInformacaoAntesSalvar(getEntidade());

			setControlarFormCadastrar(true);
			setControlarFormListar(false);
			getEntidade().setModalidadeBLEnum(ModalidadeBLEnum.FCL);
			setEntidade(getCadastroDtcDtaRN().salvarSemRelacionaMento(getEntidade()));

			RequestContext.getCurrentInstance().update(GRID_CONTAINER);
		} catch (Throwable e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void setarInformacaoNavio() {
		try {
			if (getEntidade() != null) {
				getEntidade().setProgramacaoNavio(getProgramacaoNavioSelecionado());
				getEntidade().setNavioViagem(getProgramacaoNavioSelecionado().getNavio());
				getEntidade().setViagem(getProgramacaoNavioSelecionado().getNavioViagem());
				getEntidade().setDataAtracacao(getProgramacaoNavioSelecionado().getDataATA());

				if (getUserMB().getUser().getRole() == Role.CLIENTE
						&& getUserMB().getUser().getRole() == Role.DESPACHANTE) {

					if (getProgramacaoNavioRN().prazoSegregacao24AntesEta(getEntidade().getProgramacaoNavio())) {
						geraAvisoNavioPassou48Horas();
					}

					Date dataHoje = new Date();
					if (DataUTIL.verificaFinalDeSemana(dataHoje) == 7) {
						SimpleDateFormat sdf = new SimpleDateFormat("HH");
						int hora = Integer.valueOf(sdf.format(dataHoje));

						if (hora >= 11) {
							geraAvisoNavioPassou48Horas();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void geraAvisoNavioPassou48Horas() {
		RequestContext.getCurrentInstance().execute("PF('dlgAviso').show();");
		setEntidade(new CadastroBL());
		setProgramacaoNavioSelecionado(null);
		RequestContext.getCurrentInstance().update("frmCadastrarEntidade");
	}

	public void salvarEntidade() {
		try {
			getEntidade().setEnvioMapaValidar(false);

			setarInformacaoAntesSalvar(getEntidade());

			setControlarFormCadastrar(true);
			setControlarFormListar(false);
			getEntidade().setModalidadeBLEnum(ModalidadeBLEnum.FCL);
			getEntidade().setUserLogado(getUserMB().getUser());
			setEntidade(getCadastroDtcDtaRN().alterar(getEntidade(), getListaMapaNCM()));

			setEntidadeDTCDTA(getEntidade());

			RequestContext.getCurrentInstance().update(GRID_CONTAINER);

			if (getEntidade().getProgramacaoNavio() != null) {
				if (getEntidade().getProgramacaoNavio().getId() != 232) {
					if (getProgramacaoNavioRN().prazoSegregacao24AntesEta(getEntidade().getProgramacaoNavio())) {
						JSFMessageUtil.adicionarMensagemAlerta(
								"Salvo com Sucesso! Prazo segregacao 24h antes do ETA do Navio!");
					} else {
						JSFMessageUtil.adicionarMensagemSucesso(MenssagensUtilEnum.OPERACAO_SUCESSO.getDescricao());
					}
				} else {
					JSFMessageUtil.adicionarMensagemSucesso(MenssagensUtilEnum.OPERACAO_SUCESSO.getDescricao());
				}
			} else {
				JSFMessageUtil.adicionarMensagemSucesso(MenssagensUtilEnum.OPERACAO_SUCESSO.getDescricao());
			}

		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	private ProgramacaoNavioRN getProgramacaoNavioRN() {
		if (programacaoNavioRN == null) {
			programacaoNavioRN = new ProgramacaoNavioRN();
		}

		return programacaoNavioRN;
	}

	private void setarInformacaoAntesSalvar(CadastroDTCDTA entidade) {
		getEntidade().setPaisOrigem(getPaisSelecionado());
		getEntidade().setPaisProcedencia(getPaisProcedenciaSelecionado());
		getEntidade().setNcm(getNcmSelecionado());
		getEntidade().setPortos(getPortoSelecionado());
		getEntidade().setTipoEmbalagemEspecie(getEspecieSelecionado());
		getEntidade().setTipoEmbalagemEncontrada(getEmbalagemEncontradaSelecionado());
		getEntidade().setImportador(getImportadorSelecionado());
		getEntidade().setRepresentante(getRepresentanteSelecionado());
		getEntidade().setFaturarContra(getFaturarContraSelecionado());
		getEntidade().setResponsavelLote(getResponsavelLoteSelecionado());
		getEntidade().setProgramacaoNavio(getProgramacaoNavioSelecionado());
		getEntidade().setCadComissario(getComissarioSelecionado());
	}

	private void atualizarFormPrincipal() {
		RequestContext.getCurrentInstance().update(PG_FORM_PRINCIPAL);
	}

	public void remover(CadastroDTCDTA entidade) {
		try {
			getCadastroDtcDtaRN().deletar(entidade);
			atualizarFormPrincipal();
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public CadastroDTCDTA getEntidade() {
		return entidade;
	}

	public void setEntidade(CadastroDTCDTA entidade) {
		this.entidade = entidade;
	}

	public void salvarStatusAnexos() {
		try {
			setEntidade(getCadastroDtcDtaRN().salvarSemRelacionaMento(getEntidade()));

		} catch (Throwable e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	private UserImportadorUsuariosRN getImportadorUsuariosRN() {
		if (importadorUsuariosRN == null) {
			importadorUsuariosRN = new UserImportadorUsuariosRN();
		}
		return importadorUsuariosRN;
	}

	public LazyDataModel<CadastroDTCDTA> getLazyModel() {
		if (lazyModel == null) {
			if (getWhereSQL() == null) {
				setWhereSQL(FiltroTabelaBean.montaCondicaoSqlWhereAND(null, CadastroDTCDTA.CAMPO_MODALIDADEBLENUM,
						TipoFiltroEnum.IGUAL_COM_WHERE, ModalidadeBLEnum.FCL.getCodigo()));

				if (getUserMB().getUser().getRole() != Role.ADMIN) {
					if (getUserMB().getUser().getRole() == Role.IMPORTADOR_MASTER) {
						setWhereSQL(getCadastroDtcDtaRN().retornaCondicaoFiltroTabelaImportadorMaster(
								getUserMB().getUser(), getWhereSQL(), TipoFiltroEnum.IN_SEM_WHERE));
					}
					if (getUserMB().getUser().getRole() == Role.CLIENTE) {
						setWhereSQL(getCadastroDtcDtaRN().retornaCondicaoFiltroTabelaImportadorCliente(
								getUserMB().getUser(), getWhereSQL(), TipoFiltroEnum.IN_SEM_WHERE));
					}

					if (getUserMB().getUser().getRole() == Role.COMISSARIA_MASTER) {
						setWhereSQL(getCadastroDtcDtaRN().retornaCondicaoFiltroTabelaComissariaMasterOuNotify(
								getUserMB().getUser(), getWhereSQL(), TipoFiltroEnum.IN_SEM_WHERE));

						setWhereSQL(getCadastroDtcDtaRN().retornaCondicaoFiltroTabelaImportador(getUserMB().getUser(),
								getWhereSQL(), TipoFiltroEnum.IN_SEM_WHERE));
					}

					if (getUserMB().getUser().getRole() == Role.DESPACHANTE) {
						UserComissariaUsuarios userComissaria = getUserComissariaUsuariosRN()
								.retornaUsuarioComissaria(getUserMB().getUser());

						if (userComissaria == null) {
							setWhereSQL(getCadastroDtcDtaRN().retornaCondicaoFiltroTabelaComissariaMaster(
									getUserMB().getUser(), getWhereSQL(), TipoFiltroEnum.IN_SEM_WHERE));
						} else {
							setWhereSQL(getCadastroDtcDtaRN().retornaCondicaoFiltroTabelaComissariaMasterOuNotify(
									userComissaria.getComissariaMaster().getUsuario(), getWhereSQL(),
									TipoFiltroEnum.IN_SEM_WHERE));

							setWhereSQL(getCadastroDtcDtaRN().retornaCondicaoFiltroTabelaImportador(
									userComissaria.getComissariaMaster().getUsuario(), getWhereSQL(),
									TipoFiltroEnum.IN_SEM_WHERE));
						}
					}
				}
			}

			lazyModel = new AbstractLazyDataModel<CadastroDTCDTA>(CadastroDTCDTA.sql, CadastroDTCDTA.sqlCount,
					getWhereSQL(), null);
		}

		return lazyModel;
	}

	@Override
	public void filtroTabela() {
		try {
			lazyModel = null;
			setWhereSQL(null);

			if (!getFiltroGeral().isEmpty()) {

				getFiltrosParametros().add(CadastroDTCDTA.CADASTRO_BL_DESCRICAOBL);
				getFiltrosParametros().add(CadastroDTCDTA.CAMPO_NUMEROATI);
				getFiltrosParametros().add(CadastroDTCDTA.CAMPO_IMPORTADOR);

				setWhereSQL(FiltroTabelaBean.montaCondicaoSqlWhereOR(getFiltroGeral(), getFiltrosParametros()));

				setWhereSQL(FiltroTabelaBean.montaCondicaoSqlWhereAND(getWhereSQL(),
						CadastroDTCDTA.CAMPO_MODALIDADEBLENUM, TipoFiltroEnum.IGUAL, ModalidadeBLEnum.FCL.getCodigo()));

				if (getUserMB().getUser().getRole() != Role.ADMIN) {
					if (getUserMB().getUser().getRole() == Role.IMPORTADOR_MASTER) {
						setWhereSQL(getCadastroDtcDtaRN().retornaCondicaoFiltroTabelaImportadorMaster(
								getUserMB().getUser(), getWhereSQL(), TipoFiltroEnum.IN_SEM_WHERE));
					}
					if (getUserMB().getUser().getRole() == Role.CLIENTE) {
						setWhereSQL(getCadastroDtcDtaRN().retornaCondicaoFiltroTabelaImportadorCliente(
								getUserMB().getUser(), getWhereSQL(), TipoFiltroEnum.IN_SEM_WHERE));
					}

					if (getUserMB().getUser().getRole() == Role.COMISSARIA_MASTER) {
						setWhereSQL(getCadastroDtcDtaRN().retornaCondicaoFiltroTabelaComissariaMasterOuNotify(
								getUserMB().getUser(), getWhereSQL(), TipoFiltroEnum.IN_SEM_WHERE));

						setWhereSQL(getCadastroDtcDtaRN().retornaCondicaoFiltroTabelaImportador(getUserMB().getUser(),
								getWhereSQL(), TipoFiltroEnum.IN_SEM_WHERE));
					}

					// despachante
					if (getUserMB().getUser().getRole() == Role.DESPACHANTE) {
						UserComissariaUsuarios userComissaria = getUserComissariaUsuariosRN()
								.retornaUsuarioComissaria(getUserMB().getUser());

						if (userComissaria == null) {
							setWhereSQL(getCadastroDtcDtaRN().retornaCondicaoFiltroTabelaComissariaMaster(
									getUserMB().getUser(), getWhereSQL(), TipoFiltroEnum.IN_SEM_WHERE));
						} else {
							setWhereSQL(getCadastroDtcDtaRN().retornaCondicaoFiltroTabelaComissariaMasterOuNotify(
									userComissaria.getComissariaMaster().getUsuario(), getWhereSQL(),
									TipoFiltroEnum.IN_SEM_WHERE));

							setWhereSQL(getCadastroDtcDtaRN().retornaCondicaoFiltroTabelaImportador(
									userComissaria.getComissariaMaster().getUsuario(), getWhereSQL(),
									TipoFiltroEnum.IN_SEM_WHERE));
						}
					}
				}
			}
		} catch (Exception e) {
			setFiltrosParametros(null);
		}
	}

	private UserComissariaUsuariosRN getUserComissariaUsuariosRN() {
		if (userComissariaUsuariosRN == null) {
			userComissariaUsuariosRN = new UserComissariaUsuariosRN();
		}
		return userComissariaUsuariosRN;
	}

	@Override
	public String getFiltroGeral() {
		return filtroGeral;
	}

	@Override
	public void setFiltroGeral(String filtroGeral) {
		this.filtroGeral = filtroGeral;
	}

	public String getNumeroContainer() {
		return numeroContainer;
	}

	public void setNumeroContainer(String numeroContainer) {
		this.numeroContainer = numeroContainer;
	}

	public String getLacreContainer() {
		return lacreContainer;
	}

	public void setLacreContainer(String lacreContainer) {
		this.lacreContainer = lacreContainer;
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

			JSFMessageUtil.adicionarMensagemSucesso(MenssagensUtilEnum.OPERACAO_UPLOAD.getDescricao());
		} catch (Throwable e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
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

	private JSFUtil getJsfUtil() {
		if (jsfUtil == null) {
			jsfUtil = new JSFUtil();
		}
		return jsfUtil;
	}

	public void download(ActionEvent evento) {
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

	public void removerAnexo(CadastroBLAnexos item) {
		try {
			getCadBLAnexosRN().deletar(item);

			listaBLTodosAnexos(getSelecionadoTipoAnexos());

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

	public boolean isCamposObrigatorios() {
		return camposObrigatorios;
	}

	public void setCamposObrigatorios(boolean camposObrigatorios) {
		this.camposObrigatorios = camposObrigatorios;
	}

	public boolean isControlaSalvar() {
		return controlaSalvar;
	}

	public void setControlaSalvar(boolean controlaSalvar) {
		this.controlaSalvar = controlaSalvar;
	}

	public boolean isControlaSalvarRasqunho() {
		return controlaSalvarRasqunho;
	}

	public void setControlaSalvarRasqunho(boolean controlaSalvarRasqunho) {
		this.controlaSalvarRasqunho = controlaSalvarRasqunho;
	}

}