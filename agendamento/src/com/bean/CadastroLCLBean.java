package com.bean;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

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
import com.util.JSFMessageUtil;

import conexao.com.bean.AbstractLazyDataModel;
import conexao.com.enums.MenssagensUtilEnum;
import conexao.com.enums.TipoFiltroEnum;
import conexao.com.rn.CadastroBLAnexosRN;
import conexao.com.rn.CadastroBLContanierLCLRN;
import conexao.com.rn.CadastroBLContanierRN;
import conexao.com.rn.ProgramacaoNavioRN;
import conexao.com.util.FiltroTabelaBean;
import conexao.com.util.IGenericBean;
import conexao.com.util.JSFUtil;
import seguranca.com.entidade.CadastroBL;
import seguranca.com.entidade.CadastroBLAnexos;
import seguranca.com.entidade.CadastroBLContanier;
import seguranca.com.entidade.CadastroBLContanierLCL;
import seguranca.com.enums.ModalidadeBLEnum;
import seguranca.com.enums.Role;
import seguranca.com.enums.StatusAtiLclFclEnum;
import seguranca.com.enums.TipoAnexosEnum;
import seguranca.com.enums.TipoSimNaoEnum;
import seguranca.com.enums.TipomodalEnum;

@ViewScoped
@ManagedBean
public class CadastroLCLBean extends CadastroDtcDtaComumBean implements IGenericBean {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;

	private CadastroBL entidadeBLEdicao;
	private CadastroBLContanierLCL novoLCL;
	private CadastroBLContanierLCL containerLCL;
	private CadastroBLContanierLCL containerLCLSelecionado;
	private CadastroDTCDTA entidade;
	private boolean controlarDados = false;

	private boolean controlarBtSalvarBL = true;

	private String controlarImagem = "fa fa-angle-double-down";
	private TipoSimNaoEnum selecionadoCargaImoPorBL;
	private StreamedContent pdf;

	private List<CadastroBL> listaBL;
	private List<CadastroBLContanierLCL> listaLCLMemoria;
	private List<CadastroBLContanierLCL> listaLCLIncluirBL;
	private CadastroBLContanierLCL lclSelecionar;

	private LazyDataModel<CadastroDTCDTA> lazyModel;

	private CadastroDTCDTARN cadDtcDtaRN;
	private CadastroBLContanierLCLRN lclRN;

	private CadastroBLAnexosRN cadBLAnexosRN;

	private JSFUtil jsfUtil;

	private CadastroBLAnexos anexo;

	private ProgramacaoNavioRN programacaoNavioRN;

	private UserComissariaUsuariosRN userComissariaUsuariosRN;

	private CadastroBLContanierRN containerRN;

	private List<CadastroBLContanier> listaContainerBL;

	public List<CadastroBL> getListaBL() {
		if (listaBL == null && getContainerLCL() != null) {
			listaBL = getContainerLCL().getListaCadastroBL();
		}
		return listaBL;
	}

	public List<CadastroBLContanier> getListaContainerBL() {
		return listaContainerBL;
	}
	
	public void setListaContainerBL(List<CadastroBLContanier> listaContainerBL) {
		this.listaContainerBL = listaContainerBL;
	}
	
	public void instanciarLista(CadastroBL item) {
		setListaContainerBL(item.getListaCadastroBLContanier());
	}

	public void setListaBL(List<CadastroBL> listaBL) {
		this.listaBL = listaBL;
	}
	
	public void excluirContainer(CadastroBLContanier entidade) {
		try {
			if (entidade != null) {
				getContainerRN().deletar(entidade);
				entidade.getCadastroBL().getListaCadastroBLContanier().remove(entidade);
				instanciarLista(entidade.getCadastroBL());
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	// private UserImportadorRN getUserImportadorRN() {
	// if (userImportadorRN == null) {
	// userImportadorRN = new UserImportadorRN();
	// }
	//
	// return userImportadorRN;
	// }

	public LazyDataModel<CadastroDTCDTA> getLazyModel() {
		if (lazyModel == null) {
			if (getWhereSQL() == null) {
				setWhereSQL(FiltroTabelaBean.montaCondicaoSqlWhereAND(null, CadastroDTCDTA.CAMPO_MODALIDADEBLENUM,
						TipoFiltroEnum.IGUAL_COM_WHERE, ModalidadeBLEnum.LCL.getCodigo()));

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
						CadastroDTCDTA.CAMPO_MODALIDADEBLENUM, TipoFiltroEnum.IGUAL, ModalidadeBLEnum.LCL.getCodigo()));

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

	public void redirecionarCadastro() {
		try {
			setBloquearNavio(false);

			setControlarFormCadastrar(true);
			setControlarFormListar(false);

			setSelecionadoCargaImo(null);
			setPaisSelecionado(null);
			setPaisProcedenciaSelecionado(null);
			setNcmSelecionado(null);
			setPortoSelecionado(null);
			setEspecieSelecionado(null);
			setEmbalagemEncontradaSelecionado(null);
			setImportadorSelecionado(null);
			setRepresentanteSelecionado(null);
			setSelecionadoCargaImoPorBL(null);
			setProgramacaoNavioSelecionado(null);
			setFaturarContraSelecionado(null);
			setResponsavelLoteSelecionado(null);
			setContainerLCLSelecionado(null);
			setComissarioSelecionado(null);

			setListaBL(null);
			listaBL = null;
			setListaLCLMemoria(null);
			listaLCLMemoria = null;

			setEntidade(new CadastroBL());
			setContainerLCL(new CadastroBLContanierLCL());
			setNovoLCL(new CadastroBLContanierLCL());
			setEntidadeDTCDTA(new CadastroDTCDTA());

			atualizarFormCadastroUsuario();
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	private void atualizarFormCadastroUsuario() {
		RequestContext.getCurrentInstance().update(PG_FORM_CADASTRAR_ENTIDADE);
	}

	public void editarEntidade(CadastroDTCDTA entidade) {
		try {
			if (entidade != null) {
				setEntidade(new CadastroBL());

				setEntidadeDTCDTA(entidade);
				setPaisSelecionado(entidade.getPaisOrigem());
				setPaisProcedenciaSelecionado(entidade.getPaisProcedencia());
				setNcmSelecionado(entidade.getNcm());
				setPortoSelecionado(entidade.getPortos());
				setEspecieSelecionado(entidade.getTipoEmbalagemEspecie());
				setEmbalagemEncontradaSelecionado(entidade.getTipoEmbalagemEncontrada());
				setImportadorSelecionado(entidade.getImportador());
				setRepresentanteSelecionado(entidade.getRepresentante());
				setSelecionadoCargaImoPorBL(entidade.getCargaImo());
				setResponsavelLoteSelecionado(entidade.getResponsavelLote());
				setFaturarContraSelecionado(entidade.getFaturarContra());
				setComissarioSelecionado(entidade.getCadComissario());

				setContainerLCL(entidade.getContainerLCL());
				if (getContainerLCL() != null) {
					setSelecionadoCargaImo(getContainerLCL().getCargaImo());
				}

				CadastroBL bl = entidade.getCadastroBL();
				if (listaLCLMemoria == null) {
					listaLCLMemoria = new ArrayList<CadastroBLContanierLCL>();
				}
				for (CadastroBLContanier item : bl.getListaCadastroBLContanier()) {
					listaLCLMemoria.add(item.getContainerLCL());
				}

				setBloquearNavio(false);
				if (entidade.getTipoModalEnum() != TipomodalEnum.DTC) {
					setBloquearNavio(true);
				}
			}
			listaBL = null;

			setControlarFormCadastrar(true);
			setControlarFormListar(false);
			atualizarFormCadastroUsuario();
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public CadastroDTCDTARN getCadastroDtcDtaRN() {
		if (cadDtcDtaRN == null) {
			cadDtcDtaRN = new CadastroDTCDTARN();
		}
		return cadDtcDtaRN;
	}

	private CadastroBLContanierLCLRN getLclRN() {
		if (lclRN == null) {
			lclRN = new CadastroBLContanierLCLRN();
		}
		return lclRN;
	}

	public void remover(CadastroDTCDTA entidade) {
		try {
			getCadastroDtcDtaRN().deletar(entidade);
			atualizarFormPrincipal();
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	private void atualizarFormPrincipal() {
		RequestContext.getCurrentInstance().update(PG_FORM_PRINCIPAL);
	}

	public void expandirdDados() {
		if (isControlarDados()) {
			setControlarDados(false);
			setControlarImagem("fa fa-angle-double-down");
		} else {
			setControlarDados(true);
			setControlarImagem("fa fa-angle-double-up");
		}
	}

	public List<CadastroBLContanierLCL> listaLCL(String pesquisa) {
		List<CadastroBLContanierLCL> lista = new ArrayList<CadastroBLContanierLCL>();
		if (getContainerLCL() != null) {
			lista = getLclRN().listaTodosAutoComplete(pesquisa);
		}
		return lista;
	}

	public void incluirNovoLCL() {
		// setContainerLCL(new CadastroBLContanierLCL());
	}

	private CadastroBLContanierRN getContainerRN() {
		if (containerRN == null) {
			containerRN = new CadastroBLContanierRN();
		}

		return containerRN;
	}

	public void salvarLCL() {
		try {
			if (getContainerRN().validarContanier(getContainerLCL().getNumeroContanier()) == false) {
				throw new Exception("Numero do container não válido! ");
			}

			getNovoLCL().setCargaImo(getContainerLCL().getCargaImo());
			getNovoLCL().setImportadorLCL(getContainerLCL().getImportadorLCL());
			getNovoLCL().setNumeroBLMaster(getContainerLCL().getNumeroBLMaster());
			getNovoLCL().setNumeroContanier(getContainerLCL().getNumeroContanier());
			getNovoLCL().setNumeroLacre(getContainerLCL().getNumeroLacre());
			getNovoLCL().setQuantidadeBL(getContainerLCL().getQuantidadeBL());
			getNovoLCL().setQuantidadeMercadoria(getContainerLCL().getQuantidadeMercadoria());
			getNovoLCL().setQuantidadeVolume(getContainerLCL().getQuantidadeVolume());

			setContainerLCL(getLclRN().alterar(getNovoLCL()));
			getNovoLCL().setId(getContainerLCL().getId());

			getListaLCLMemoria().add(getNovoLCL());

			setNovoLCL(new CadastroBLContanierLCL());

			JSFMessageUtil.adicionarMensagemSucesso(MenssagensUtilEnum.OPERACAO_SUCESSO.getDescricao());
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void editarBL(CadastroBL entidade) {
		try {
			setEntidadeDTCDTA(getCadastroDtcDtaRN().localizarPorReferencia(entidade.getId()));

			setPaisSelecionado(getEntidadeDTCDTA().getPaisOrigem());
			setPaisProcedenciaSelecionado(getEntidadeDTCDTA().getPaisProcedencia());
			setNcmSelecionado(getEntidadeDTCDTA().getNcm());
			setPortoSelecionado(getEntidadeDTCDTA().getPortos());
			setEspecieSelecionado(getEntidadeDTCDTA().getTipoEmbalagemEspecie());
			setComissarioSelecionado(entidade.getCadComissario());

			setEmbalagemEncontradaSelecionado(getEntidadeDTCDTA().getTipoEmbalagemEncontrada());
			setImportadorSelecionado(getEntidadeDTCDTA().getImportador());
			setRepresentanteSelecionado(getEntidadeDTCDTA().getRepresentante());
			setSelecionadoCargaImoPorBL(getEntidadeDTCDTA().getCargaImo());
			setResponsavelLoteSelecionado(getEntidadeDTCDTA().getResponsavelLote());

			setFaturarContraSelecionado(getEntidadeDTCDTA().getFaturarContra());
			setProgramacaoNavioSelecionado(getEntidadeDTCDTA().getProgramacaoNavio());

			setControlarBtSalvarBL(false);

		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void incluirBL(boolean incluirSalvar) {
		try {
			getContainerLCL().setCargaImo(getSelecionadoCargaImo());

			getEntidadeDTCDTA().setPaisOrigem(getPaisSelecionado());
			getEntidadeDTCDTA().setPaisProcedencia(getPaisProcedenciaSelecionado());
			getEntidadeDTCDTA().setNcm(getNcmSelecionado());
			getEntidadeDTCDTA().setPortos(getPortoSelecionado());
			getEntidadeDTCDTA().setTipoEmbalagemEspecie(getEspecieSelecionado());
			getEntidadeDTCDTA().setTipoEmbalagemEncontrada(getEmbalagemEncontradaSelecionado());
			getEntidadeDTCDTA().setImportador(getImportadorSelecionado());
			getEntidadeDTCDTA().setRepresentante(getRepresentanteSelecionado());
			getEntidadeDTCDTA().setCargaImo(getSelecionadoCargaImoPorBL());
			getEntidadeDTCDTA().setResponsavelLote(getResponsavelLoteSelecionado());
			getEntidadeDTCDTA().setFaturarContra(getFaturarContraSelecionado());
			getEntidadeDTCDTA().setProgramacaoNavio(getProgramacaoNavioSelecionado());
			getEntidadeDTCDTA().setCadComissario(getComissarioSelecionado());
			getEntidadeDTCDTA().setUserCadastroDtc(getUserMB().getUser());

			if (getContainerLCLSelecionado() != null) {
				setContainerLCL(getContainerLCLSelecionado());
			}

			try {
				if (incluirSalvar) {
					if (getContainerLCL().getId() == null) {
						throw new Exception("Obrigatario Cadastrar BL Master! ");
					}

					setEntidadeDTCDTA(getCadastroDtcDtaRN().incluirDtcBlContainer(getContainerLCL(),
							getEntidadeDTCDTA(), getListaMapaNCM(), getListaLCLIncluirBL()));

					getContainerLCL().getListaCadastroBL().add(getEntidadeDTCDTA().getCadastroBL());

				} else {
					setEntidadeDTCDTA(getCadastroDtcDtaRN().alterar(getEntidadeDTCDTA(), getListaMapaNCM()));

					setControlarBtSalvarBL(true);
				}
			} catch (Throwable e) {
				JSFMessageUtil.adicionarMensagemErro(e.getMessage());
				e.printStackTrace();
			}
			listaBL = null;
			listaLCLIncluirBL = null;
			setLclSelecionar(null);

			if (getEntidadeDTCDTA().getProgramacaoNavio() != null) {
				if (getEntidadeDTCDTA().getProgramacaoNavio().getDataETA() != null) {
					if (getProgramacaoNavioRN().prazoSegregacao24AntesEta(getEntidadeDTCDTA().getProgramacaoNavio())) {
						JSFMessageUtil.adicionarMensagemAlerta(
								"Salvo com Sucesso! Prazo segregacao 24h antes do ETA do Navio!");
					} else {
						JSFMessageUtil.adicionarMensagemSucesso(MenssagensUtilEnum.OPERACAO_SUCESSO.getDescricao());
					}
				}
			} else {
				if (getContainerLCL().getId() == null) {
					throw new Exception("Obrigatorio Cadastrar BL Master! ");
				}
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

	public CadastroBLContanierLCL getContainerLCL() {
		return containerLCL;
	}

	public void setContainerLCL(CadastroBLContanierLCL containerLCL) {
		this.containerLCL = containerLCL;
	}

	public CadastroBL getEntidadeBLEdicao() {
		return entidadeBLEdicao;
	}

	public void setEntidadeBLEdicao(CadastroBL entidadeBLEdicao) {
		this.entidadeBLEdicao = entidadeBLEdicao;
	}

	public boolean isControlarDados() {
		return controlarDados;
	}

	public void setControlarDados(boolean controlarDados) {
		this.controlarDados = controlarDados;
	}

	public String getControlarImagem() {
		return controlarImagem;
	}

	public void setControlarImagem(String controlarImagem) {
		this.controlarImagem = controlarImagem;
	}

	public void voltarPgPrincipal() {
		try {
			getLclRN().validarQuantidadesVolumes(getContainerLCL());

			setControlarFormCadastrar(false);
			setControlarFormListar(true);
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public TipoSimNaoEnum getSelecionadoCargaImoPorBL() {
		return selecionadoCargaImoPorBL;
	}

	public void setSelecionadoCargaImoPorBL(TipoSimNaoEnum selecionadoCargaImoPorBL) {
		this.selecionadoCargaImoPorBL = selecionadoCargaImoPorBL;
	}

	public void setarInformacaoNavio() {
		if (getEntidadeDTCDTA() != null) {
			getEntidadeDTCDTA().setProgramacaoNavio(getProgramacaoNavioSelecionado());
			getEntidadeDTCDTA().setNavioViagem(getProgramacaoNavioSelecionado().getNavio());
			getEntidadeDTCDTA().setViagem(getProgramacaoNavioSelecionado().getNavioViagem());
			getEntidadeDTCDTA().setDataAtracacao(getProgramacaoNavioSelecionado().getDataATA());
		}
	}

	public CadastroDTCDTA getEntidade() {
		return entidade;
	}

	public void setEntidade(CadastroDTCDTA entidade) {
		this.entidade = entidade;
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

				CadastroBL bl = getEntidadeDTCDTA().getCadastroBL();

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

	private CadastroBLAnexosRN getCadBLAnexosRN() {
		if (cadBLAnexosRN == null) {
			cadBLAnexosRN = new CadastroBLAnexosRN();
		}

		return cadBLAnexosRN;
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

	public void upload(FileUploadEvent evento) throws SQLException, EmailException {
		try {
			CadastroBLAnexos itemAnexo = new CadastroBLAnexos();
			Path arquivoTemp = Files.createTempFile(null, null);
			Files.copy(evento.getFile().getInputstream(), arquivoTemp, StandardCopyOption.REPLACE_EXISTING);

			itemAnexo.setCadastroBL(getEntidadeBL());

			itemAnexo.setTipoAnexosEnum(getSelecionadoTipoAnexos());

			itemAnexo.setCaminhoAnexo(evento.getFile().getFileName().replace(".pdf", "") + "_"
					+ getEntidadeBL().getDescricaoBL() + "_" + getSelecionadoTipoAnexos().getDescricao() + ".pdf");

			Path origem = Paths.get(arquivoTemp.toString());

			String destino_caminho = getJsfUtil().getCaminhoAnexos(getSelecionadoTipoAnexos());

			destino_caminho = destino_caminho + itemAnexo.getCaminhoAnexo();
			Path destino = Paths.get(destino_caminho);

			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);

			itemAnexo = getCadBLAnexosRN().alterar(itemAnexo, getUserMB().getUser());

			JSFMessageUtil.adicionarMensagemSucesso(MenssagensUtilEnum.OPERACAO_UPLOAD.getDescricao());
		} catch (Throwable e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

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

	public void instanciarEntidadeAnexos(CadastroBL entidade) {
		try {

			boolean abriTela = false;

			if (entidade != null) {
				abriTela = true;
				setEntidade(entidade);
			} else {
				setEntidade(getEntidade());
			}

			CadastroBL bl = entidade;

			setRetornoBL(getCadBLAnexosRN().executarQtdeRegistros(bl, TipoAnexosEnum.BL));
			setRetornoCE(getCadBLAnexosRN().executarQtdeRegistros(bl, TipoAnexosEnum.CE));
			setRetornoDTC_DTA(getCadBLAnexosRN().executarQtdeRegistros(bl, TipoAnexosEnum.DTC_DTA));
			setRetornoINVOICE(getCadBLAnexosRN().executarQtdeRegistros(bl, TipoAnexosEnum.INVOICE));
			setRetornoPACKING(getCadBLAnexosRN().executarQtdeRegistros(bl, TipoAnexosEnum.PACKING));

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

	private JSFUtil getJsfUtil() {
		if (jsfUtil == null) {
			jsfUtil = new JSFUtil();
		}

		return jsfUtil;
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

			RequestContext.getCurrentInstance().update("anexosGenerico:abas:"
					+ getSelecionadoTipoAnexos().getPosicaoAbaTela() + ":frmAnexosImportacao:tblAnexosImportacao");
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public CadastroBLContanierLCL getContainerLCLSelecionado() {
		return containerLCLSelecionado;
	}

	public void setContainerLCLSelecionado(CadastroBLContanierLCL containerLCLSelecionado) {
		this.containerLCLSelecionado = containerLCLSelecionado;
	}

	public boolean isControlarBtSalvarBL() {
		return controlarBtSalvarBL;
	}

	public void setControlarBtSalvarBL(boolean controlarBtSalvarBL) {
		this.controlarBtSalvarBL = controlarBtSalvarBL;
	}

	public List<CadastroBLContanierLCL> getListaLCLMemoria() {
		if (listaLCLMemoria == null) {
			listaLCLMemoria = new ArrayList<CadastroBLContanierLCL>();
		}
		return listaLCLMemoria;
	}

	public void setListaLCLMemoria(List<CadastroBLContanierLCL> listaLCLMemoria) {
		this.listaLCLMemoria = listaLCLMemoria;
	}

	public CadastroBLContanierLCL getNovoLCL() {
		return novoLCL;
	}

	public void setNovoLCL(CadastroBLContanierLCL novoLCL) {
		this.novoLCL = novoLCL;
	}

	public CadastroBLContanierLCL getLclSelecionar() {
		return lclSelecionar;
	}

	public void setLclSelecionar(CadastroBLContanierLCL lclSelecionar) {
		this.lclSelecionar = lclSelecionar;
	}

	public List<CadastroBLContanierLCL> getListaLCLIncluirBL() {
		if (listaLCLIncluirBL == null) {
			listaLCLIncluirBL = new ArrayList<CadastroBLContanierLCL>();
		}
		return listaLCLIncluirBL;
	}

	public void setListaLCLIncluirBL(List<CadastroBLContanierLCL> listaLCLIncluirBL) {
		this.listaLCLIncluirBL = listaLCLIncluirBL;
	}

	public void vincularLCL() {
		getListaLCLIncluirBL().add(getLclSelecionar());

		JSFMessageUtil.adicionarMensagemSucesso(MenssagensUtilEnum.OPERACAO_VINCULADO_SUCESSO.getDescricao());
	}
}
