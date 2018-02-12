package com.bean;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

import com.rn.AuditoriaLogRN;
import com.util.JSFMessageUtil;

import conexao.com.bean.AbstractLazyDataModel;
import conexao.com.bean.AbstractMB;
import conexao.com.enums.TipoFiltroEnum;
import conexao.com.rn.CadastroBLAnexosRN;
import conexao.com.rn.CadastroBLContanierRN;
import conexao.com.rn.CadastroBLDescricaoMercadoriaRN;
import conexao.com.rn.CadastroBLRN;
import conexao.com.rn.CadastroBLRiscoRN;
import conexao.com.rn.CadastroEspecieRN;
import conexao.com.rn.UserComissarioRN;
import conexao.com.rn.UserImportadorRN;
import conexao.com.util.FiltroTabelaBean;
import conexao.com.util.IGenericBean;
import conexao.com.util.JSFUtil;
import seguranca.com.entidade.CadastroBL;
import seguranca.com.entidade.CadastroBLAnexos;
import seguranca.com.entidade.CadastroBLContanier;
import seguranca.com.entidade.CadastroBLDescricaoMercadoria;
import seguranca.com.entidade.CadastroBLRiscoFitossanitario;
import seguranca.com.entidade.CadastroBlLogAlteracao;
import seguranca.com.entidade.CadastroEspecie;
import seguranca.com.entidade.LogInspecaoLiberadoMapa;
import seguranca.com.entidade.MapaNcm;
import seguranca.com.enums.RiscoFitossanitarioEnum;
import seguranca.com.enums.Role;
import seguranca.com.enums.StatusBLEnum;
import seguranca.com.enums.TipoAnexosEnum;
import seguranca.com.enums.TipomodalEnum;

@ViewScoped
@ManagedBean
public class CadastroBLConsultaBean extends AbstractMB implements IGenericBean {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	private static final String GRID_DATA_TABLE_PRINCIPAL = "tbListarEntidade";
	private static final String GRID_DATA_TABLE_PRINCIPAL_EXPANDIR = "tbListaTodosContainers";
	private String orderBy = "order by tipoModalEnum desc";

	private static final String DIRETORIO_WIN = "//10.1.5.111/d$/Anexos/Agendamento/bl/";
	private static final String DIRETORIO_LINUX = "/var/lib/tomcat7/webapps/PortalCLIF/Anexos/";

	private String descricaoMercadoria;
	private String controlarImagem = "fa fa-angle-double-up";

	private boolean expandirDados = true;

	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;

	private StreamedContent pdf;

	private CadastroBL entidade;
	private CadastroBLDescricaoMercadoria entidadeDescMercadoria;
	private CadastroBL entidadeBLAlteracao;
	private CadastroBLContanier entidadeContanier;
	private CadastroBLRiscoFitossanitario entidadeRisco;
	private CadastroBLAnexos anexo;
	private StatusBLEnum statusSelecionadoEnum;
	private TipomodalEnum tipoModalSelecionadoEnum;
	private RiscoFitossanitarioEnum riscosSelecionadoEnum;
	private CadastroEspecie embalagemEncontradaSelecionado;

	private CadastroBLRN cadBLRN;
	private CadastroBLRiscoRN riscoRN;
	private CadastroBLContanierRN cadBLContanierRN;
	private CadastroBLAnexosRN cadBLAnexosRN;
	private CadastroEspecieRN cadEspecieRN;
	private CadastroBLDescricaoMercadoriaRN descricaoMercadoriaRN;

	private List<CadastroBLAnexos> listaBLTodosAnexos;

	private List<CadastroBL> listaControleEnvioBLLiberadoInteiro;
	private List<CadastroBL> listaControleEnvioBLInspecaoInteiro;
	private List<LogInspecaoLiberadoMapa> listaLogInspecaoLiberadoMapa;

	private List<CadastroBLContanier> listaControleEnvioContainerLiberado;
	private List<CadastroBLContanier> listaControleEnvioContainerInspecao;

	private List<CadastroBLDescricaoMercadoria> listaBLTodosDescMercadoria;
	private List<CadastroBLRiscoFitossanitario> listaTodosRiscos;
	private List<StatusBLEnum> todosStatusEnum;
	private List<TipomodalEnum> todosTipoModalEnum;
	private List<RiscoFitossanitarioEnum> todosRiscosEnum;

	private LazyDataModel<CadastroBL> lazyModel;

	private UserComissarioRN userComissarioRN;

	private UserImportadorRN userImportadorRN;

	private AuditoriaLogRN auditoriaLogRN;

	private int posicaoLinhaDataTable = -1;

	public List<CadastroEspecie> listaCadastroEspecie(String pesquisa) {
		List<CadastroEspecie> listaCadastroEspecie = new ArrayList<CadastroEspecie>();
		if (getEntidadeDescMercadoria() != null) {
			listaCadastroEspecie = getCadEspecieRN().listaTodosAutoComplete(pesquisa);
		}
		return listaCadastroEspecie;
	}

	private CadastroEspecieRN getCadEspecieRN() {
		if (cadEspecieRN == null) {
			cadEspecieRN = new CadastroEspecieRN();
		}
		return cadEspecieRN;
	}

	public CadastroBLDescricaoMercadoriaRN getDescricaoMercadoriaRN() {
		if (descricaoMercadoriaRN == null) {
			descricaoMercadoriaRN = new CadastroBLDescricaoMercadoriaRN();
		}
		return descricaoMercadoriaRN;
	}

	public CadastroBLRiscoRN getRiscoRN() {
		if (riscoRN == null) {
			riscoRN = new CadastroBLRiscoRN();
		}
		return riscoRN;
	}

	public List<CadastroBLRiscoFitossanitario> getListaTodosRiscos() {
		if (listaTodosRiscos == null && getEntidade() != null) {
			listaTodosRiscos = getEntidade().getListaBLRisco();
		}
		return listaTodosRiscos;
	}

	public List<RiscoFitossanitarioEnum> getTodosRiscosEnum() {
		if (todosRiscosEnum == null) {
			todosRiscosEnum = new ArrayList<RiscoFitossanitarioEnum>();
			todosRiscosEnum = new ArrayList<RiscoFitossanitarioEnum>(Arrays.asList(RiscoFitossanitarioEnum.values()));
		}
		return todosRiscosEnum;
	}

	public List<StatusBLEnum> getTodosStatusEnum() {
		if (todosStatusEnum == null) {
			todosStatusEnum = new ArrayList<StatusBLEnum>();
			// if (userMB.getUser().isDespachante()) {
			// todosStatusEnum.add(StatusBLEnum.PENDENTE_ANEXO);
			// todosStatusEnum.add(StatusBLEnum.TODOS);
			// }

			if (userMB.getUser().isUser()) {
				todosStatusEnum.add(StatusBLEnum.TODOS);
				todosStatusEnum.add(StatusBLEnum.AGUARDANDO_LIBERACAO_PROCESSOS_QUE_ANEXARAM);
				todosStatusEnum.add(StatusBLEnum.LIBERADO_E_VISTORIADO);
				todosStatusEnum.add(StatusBLEnum.LIBERADO_SEM_VISTORIA);
				todosStatusEnum.add(StatusBLEnum.SEPARADO_PARA_VISTORIA);
			}

			if (userMB.getUser().isAdmin() || userMB.getUser().isClif() || userMB.getUser().isCliente()
					|| userMB.getUser().isDespachante()) {
				todosStatusEnum = new ArrayList<StatusBLEnum>(Arrays.asList(StatusBLEnum.values()));
			}
		}
		return todosStatusEnum;
	}

	public List<TipomodalEnum> getTodosTipoModalEnum() {
		if (todosTipoModalEnum == null) {
			todosTipoModalEnum = new ArrayList<TipomodalEnum>(Arrays.asList(TipomodalEnum.values()));
		}
		return todosTipoModalEnum;
	}

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

	public CadastroBLRN getCadastroBLRN() {
		if (cadBLRN == null) {
			cadBLRN = new CadastroBLRN();
		}
		return cadBLRN;
	}

	public List<CadastroBLContanier> listaBLFiscal(CadastroBL item) {
		List<CadastroBLContanier> listaContainers = null;
		if (item != null) {
			listaContainers = item.getListaCadastroBLContanier();
		}
		return listaContainers;
	}

	public void instanciarBLAnexos(CadastroBL item) {
		if (item != null) {
			setEntidade(item);
			setListaBLTodosAnexos(getCadBLAnexosRN().listaTodosPorTipoAnexosMapa(getEntidade()));
		}
	}

	private void atualizarGridDataTable() {
		RequestContext.getCurrentInstance().update(GRID_DATA_TABLE_PRINCIPAL);
		RequestContext.getCurrentInstance().update(GRID_DATA_TABLE_PRINCIPAL_EXPANDIR);
	}

	public boolean campoLiberado(CadastroBLContanier entidade) {
		return entidade.isLiberado();
	}

	public void alterarContanier(CadastroBLContanier entidade, boolean liberado) {
		CadastroBLContanier cadastroBLContanier = getCadBLContanierRN().localizar(entidade.getId());
		try {
			if (entidade != null) {

				// if (liberado) {
				// bloquearRiscoFitoSeNaoHouverLancamento(entidade, null);
				// }

				// CadastroBL cadastroBL =
				// getCadastroBLRN().localizar(entidade.getCadastroBL().getId());

				if (cadastroBLContanier.isInspecao() != entidade.isInspecao()) {
					entidade.setStatusBLEnum(StatusBLEnum.SEPARADO_PARA_VISTORIA);
				}
				if (cadastroBLContanier.isLiberado() != entidade.isLiberado()) {
					if (entidade.isInspecao()) {
						entidade.setStatusBLEnum(StatusBLEnum.LIBERADO_E_VISTORIADO);
					} else {
						entidade.setStatusBLEnum(StatusBLEnum.LIBERADO_SEM_VISTORIA);
					}
				}

				entidade = getCadBLContanierRN().alterarStatus(entidade, getUserMB().getUser(), cadastroBLContanier);

				// if (cadastroBL.getStatusBLEnum() ==
				// StatusBLEnum.AGUARDANDO_LIBERACAO_PROCESSOS_QUE_ANEXARAM) {
				// cadastroBL.setStatusBLEnum(entidade.getStatusBLEnum());
				// getCadastroBLRN().alterar(cadastroBL, userMB.getUser());
				// lazyModel = null;
				// }
				//
				// int totalRegistros = 0;
				// int totalRegistrosStatus = 0;
				// if (entidade.isLiberado()) {
				// totalRegistros = getCadBLContanierRN()
				// .executaQtdeRegistros(CadastroBLContanier.sqlCountTotalRegistros,
				// entidade, 1);
				// totalRegistrosStatus = getCadBLContanierRN()
				// .executaQtdeRegistros(CadastroBLContanier.sqlCountRegistrosStatus,
				// entidade, 2);
				//
				// if (totalRegistros == totalRegistrosStatus) {
				// getCadastroBLRN().updateAtualizaDados(entidade);
				// }
				// }

				atualizarGridDataTable();
				filtroTabela();
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	private void bloquearRiscoFitoSeNaoHouverLancamento(CadastroBLContanier entidadeContainer, CadastroBL entidadeBL)
			throws Exception, ParseException {
		CadastroBLRiscoFitossanitario ultRegistro = null;
		CadastroBLDescricaoMercadoria ultRegistroEmbEncontrada = null;
		CadastroBL blValidacao = null;

		if (entidadeContainer != null) {
			blValidacao = entidadeContainer.getCadastroBL();
			if (entidadeContainer.getCadastroBL().getListaBLRisco().size() > 0) {
				ultRegistro = entidadeContainer.getCadastroBL().getListaBLRisco()
						.get(entidadeContainer.getCadastroBL().getListaBLRisco().size() - 1);
			}
			if (entidadeContainer.getCadastroBL().getListaBLDescricaoMercadoria().size() > 0) {
				ultRegistroEmbEncontrada = entidadeContainer.getCadastroBL().getListaBLDescricaoMercadoria()
						.get(entidadeContainer.getCadastroBL().getListaBLDescricaoMercadoria().size() - 1);
			}
		}

		if (entidadeBL != null) {
			blValidacao = entidadeBL;
			if (entidadeBL.getListaBLRisco().size() > 0) {
				ultRegistro = entidadeBL.getListaBLRisco().get(entidadeBL.getListaBLRisco().size() - 1);
			}
			if (entidadeBL.getListaBLDescricaoMercadoria().size() > 0) {
				ultRegistroEmbEncontrada = entidadeBL.getListaBLDescricaoMercadoria()
						.get(entidadeBL.getListaBLDescricaoMercadoria().size() - 1);
			}
		}

		// if (blValidacao.getTipoModalEnum() != TipomodalEnum.DTA_MARITIMO) {
		if (ultRegistro == null || ultRegistroEmbEncontrada == null) {
			throw new Exception("Obrigat�rio lan�ar um risco de Fitossanit�rio !");
			// } else {
			// DateFormat df = new SimpleDateFormat("yyyyMMdd");
			//
			// Date dataHoje = new Date();
			//
			// // Converte para Date
			// Date dateA = df.parse(df.format(ultRegistro.getDataCadastro()));
			// Date dateB = df.parse(df.format(dataHoje));
			//
			// if (dateA.compareTo(dateB) == -1) {
			// throw new Exception("Obrigat�rio lan�ar um risco de
			// Fitossanit�rio !");
			// }
		}
		// }
	}

	public void controlarInspecaoBL(CadastroBL entidade) {
		if (listaControleEnvioBLInspecaoInteiro == null) {
			listaControleEnvioBLInspecaoInteiro = new ArrayList<CadastroBL>();
		}

		if (entidade.isInspecaoControle()) {
			listaControleEnvioBLInspecaoInteiro.add(entidade);

			if (entidade.getListaCadastroBLContanier().size() > 0) {
				if (listaControleEnvioContainerInspecao == null) {
					listaControleEnvioContainerInspecao = new ArrayList<CadastroBLContanier>();
				}
			}
			for (CadastroBLContanier item : entidade.getListaCadastroBLContanier()) {
				item.setInspecaoControle(true);
				listaControleEnvioContainerInspecao.add(item);
			}
		} else {
			listaControleEnvioBLInspecaoInteiro.remove(entidade);
			if (listaControleEnvioContainerInspecao != null) {
				if (listaControleEnvioContainerInspecao.size() > 0) {
					for (CadastroBLContanier item : entidade.getListaCadastroBLContanier()) {
						item.setInspecaoControle(false);
						listaControleEnvioContainerInspecao.remove(item);
					}
				}
			}
		}
	}

	public void controlarLiberadoBL(CadastroBL entidade) {
		if (listaControleEnvioBLLiberadoInteiro == null) {
			listaControleEnvioBLLiberadoInteiro = new ArrayList<CadastroBL>();
		}

		if (entidade.isLiberadoControle()) {
			listaControleEnvioBLLiberadoInteiro.add(entidade);

			if (entidade.getListaCadastroBLContanier().size() > 0) {
				if (listaControleEnvioContainerLiberado == null) {
					listaControleEnvioContainerLiberado = new ArrayList<CadastroBLContanier>();
				}
			}
			for (CadastroBLContanier item : entidade.getListaCadastroBLContanier()) {
				item.setLiberadoControle(true);
				listaControleEnvioContainerLiberado.add(item);
			}

		} else {
			listaControleEnvioBLLiberadoInteiro.remove(entidade);
			if (listaControleEnvioContainerLiberado != null) {
				if (listaControleEnvioContainerLiberado.size() > 0) {
					for (CadastroBLContanier item : entidade.getListaCadastroBLContanier()) {
						item.setLiberadoControle(false);
						listaControleEnvioContainerLiberado.remove(item);
					}
				}
			}
		}
	}

	public void controlarInspecaoContainer(CadastroBLContanier entidade) {
		if (listaControleEnvioContainerInspecao == null) {
			listaControleEnvioContainerInspecao = new ArrayList<CadastroBLContanier>();
		}
		if (listaControleEnvioBLInspecaoInteiro == null) {
			listaControleEnvioBLInspecaoInteiro = new ArrayList<CadastroBL>();
		}

		if (entidade.isInspecaoControle()) {
			listaControleEnvioContainerInspecao.add(entidade);

			listaControleEnvioBLInspecaoInteiro.add(entidade.getCadastroBL());
		} else {
			listaControleEnvioContainerInspecao.remove(entidade);

			listaControleEnvioBLInspecaoInteiro.remove(entidade.getCadastroBL());
		}
	}

	public void controlarLiberadoContainer(CadastroBLContanier entidade) {
		if (listaControleEnvioContainerLiberado == null) {
			listaControleEnvioContainerLiberado = new ArrayList<CadastroBLContanier>();
		}

		if (entidade.isLiberadoControle()) {
			listaControleEnvioContainerLiberado.add(entidade);
		} else {
			listaControleEnvioContainerLiberado.remove(entidade);
		}
	}

	public void envioModificaoMapa() {
		boolean mostrar = false;

		if (listaControleEnvioContainerInspecao != null) {
			for (CadastroBLContanier item : listaControleEnvioContainerInspecao) {
				item.setInspecao(true);
				alterarContanier(item, false);
			}
			listaControleEnvioContainerInspecao = null;
			setListaControleEnvioContainerInspecao(null);
			mostrar = true;
		}

		if (listaControleEnvioContainerLiberado != null) {
			for (CadastroBLContanier item : listaControleEnvioContainerLiberado) {
				item.setLiberado(true);
				alterarContanier(item, true);
			}
			listaControleEnvioContainerLiberado = null;
			setListaControleEnvioContainerLiberado(null);
			mostrar = true;
		}

		if (listaControleEnvioBLInspecaoInteiro != null) {
			for (CadastroBL item : listaControleEnvioBLInspecaoInteiro) {
				item.setInspecao(true);
				alterarBL(item, false);
			}

			mostrar = true;
			listaControleEnvioBLInspecaoInteiro = null;
			setListaControleEnvioBLInspecaoInteiro(null);
		}

		if (listaControleEnvioBLLiberadoInteiro != null) {
			for (CadastroBL item : listaControleEnvioBLLiberadoInteiro) {
				item.setLiberado(true);
				alterarBL(item, true);
			}
			listaControleEnvioBLLiberadoInteiro = null;
			setListaControleEnvioBLLiberadoInteiro(null);
			mostrar = true;
		}

		filtroTabela();

		if (mostrar) {
			JSFMessageUtil.adicionarMensagemSucesso("Envio realizado com sucesso !");
		}
	}

	public void alterarBL(CadastroBL entidade, boolean liberado) {
		try {
			if (entidade != null) {

				// if (liberado) {
				// bloquearRiscoFitoSeNaoHouverLancamento(null, entidade);
				// }

				CadastroBL cadastroBL = getCadastroBLRN().localizar(entidade.getId());
				if (cadastroBL.isInspecao() != entidade.isInspecao()) {
					entidade.setStatusBLEnum(StatusBLEnum.SEPARADO_PARA_VISTORIA);
					entidade.setAlteradoInspecao(true);
				}

				if (cadastroBL.getStatusBLEnum() == StatusBLEnum.AGUARDANDO_LIBERACAO_PROCESSOS_QUE_ANEXARAM) {
					entidade.setStatusBLEnum(StatusBLEnum.LIBERADO_SEM_VISTORIA);
					entidade.setAlteradoLiberado(true);
				} else {
					if (cadastroBL.isLiberado() != entidade.isLiberado()) {
						entidade.setStatusBLEnum(StatusBLEnum.LIBERADO_E_VISTORIADO);
					}
				}

				int qtdeRegistros = getCadBLContanierRN()
						.executaQtdeRegistros(CadastroBLContanier.sqlCountTotalRegistros, entidade, 1);

				int qtdeRegistrosInspecaoLiberado = 0;
				if (liberado) {
					qtdeRegistrosInspecaoLiberado = getCadBLContanierRN().executaQtdeRegistrosLiberado(entidade);
				}
				int qtdeRegistrosInspecao = getCadBLContanierRN().executaQtdeRegistrosInspecao(entidade);

				boolean executar = false;
				boolean executarLibSemVistoria = false;
				if (liberado) {
					if (qtdeRegistrosInspecao == 0 && qtdeRegistros == qtdeRegistrosInspecaoLiberado) {
						executar = true;
						executarLibSemVistoria = true;
						entidade.setStatusBLEnum(StatusBLEnum.LIBERADO_SEM_VISTORIA);
						entidade.setAlteradoLiberado(true);
					} else {
						if (qtdeRegistros == qtdeRegistrosInspecaoLiberado) {
							entidade.setAlteradoLiberado(true);
							executar = true;
							entidade.setStatusBLEnum(StatusBLEnum.LIBERADO_E_VISTORIADO);
						}
					}
				} else {
					if (qtdeRegistros == qtdeRegistrosInspecao) {
						executar = true;
						entidade.setStatusBLEnum(StatusBLEnum.SEPARADO_PARA_VISTORIA);
					} else {
						executar = true;
						entidade.setStatusBLEnum(StatusBLEnum.SEPARADO_PARA_VISTORIA);
					}
				}
				if (executar) {
					entidade = getCadastroBLRN().alterarStatus(entidade, userMB.getUser());

					if (executarLibSemVistoria) {
						getCadBLContanierRN().updateAtualizaDadosLiberadoSemVistoria(entidade);
					}
				} else {
					getCadBLContanierRN().fecharConexao();
					getCadastroBLRN().fecharConexaoBL();
				}

				// getCadBLContanierRN().updateAtualizaDados(entidade);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void alterarBLDeferido(CadastroBL entidade) {
		try {
			if (entidade != null) {
				entidade = getCadastroBLRN().alterar(entidade, userMB.getUser());

				getCadBLContanierRN().updateAtualizaDados(entidade);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public CadastroBL getEntidade() {
		return entidade;
	}

	public void setEntidade(CadastroBL entidade) {
		this.entidade = entidade;
	}

	public UserComissarioRN getUserComissarioRN() {
		if (userComissarioRN == null) {
			userComissarioRN = new UserComissarioRN();
		}
		return userComissarioRN;
	}

	private UserImportadorRN getUserImportadorRN() {
		if (userImportadorRN == null) {
			userImportadorRN = new UserImportadorRN();
		}

		return userImportadorRN;
	}

	public LazyDataModel<CadastroBL> getLazyModel() {
		if (lazyModel == null) {
			// if (getWhereSQL() == null) {
			// setWhereSQL(FiltroTabelaBean.montaCondicaoSqlWhereAND(null,
			// CadastroBL.CAMPO_ENVIARPARAMAPA,
			// TipoFiltroEnum.IGUAL_COM_WHERE, 1));
			// }

			lazyModel = new AbstractLazyDataModel<CadastroBL>(CadastroBL.sqlMapa, CadastroBL.sqlMapaCount,
					getWhereSQL(), orderBy);
		}

		return lazyModel;
	}

	@Override
	public void filtroTabela() {
		try {
			lazyModel = null;
			setWhereSQL(null);
			setStatusSelecionadoEnum(statusSelecionadoEnum);
			setTipoModalSelecionadoEnum(tipoModalSelecionadoEnum);

			if (!getFiltroGeral().isEmpty() || getStatusSelecionadoEnum() != null
					|| getTipoModalSelecionadoEnum() != null) {

				if (getFiltrosParametros() == null) {
					setFiltrosParametros(new ArrayList<String>());
				}

				getFiltrosParametros().add(CadastroBL.CADASTRO_BL_DESCRICAOBL);
				getFiltrosParametros().add(CadastroBL.CADASTRO_BL_ID);
				getFiltrosParametros().add(CadastroBL.CADASTRO_PAIS_ORIGEM_DESCRICAO);
				getFiltrosParametros().add(CadastroBL.CADASTRO_PAIS_PROCEDENCIA_DESCRICAO);
				getFiltrosParametros().add(CadastroBL.CADASTRO_NCM_DESCRICAO);
				getFiltrosParametros().add(CadastroBL.CADASTRO_ESPECIE_DESCRICAO);
				getFiltrosParametros().add(CadastroBL.CADASTRO_DESCRICAOMERCADORIA);

				setWhereSQL(FiltroTabelaBean.montaCondicaoSqlSemWhereOR(getFiltroGeral(), getFiltrosParametros()));

				if (getStatusSelecionadoEnum() == null) {
					setStatusSelecionadoEnum(StatusBLEnum.TODOS);
				}

				if (getStatusSelecionadoEnum().equals(StatusBLEnum.TODOS)) {
					setWhereSQL(FiltroTabelaBean.montaCondicaoSqlWhereAND(getWhereSQL(), CadastroBL.CADASTRO_STATUS,
							TipoFiltroEnum.TODOS, 0));
				} else {
					setWhereSQL(FiltroTabelaBean.montaCondicaoSqlWhereAND(getWhereSQL(), CadastroBL.CADASTRO_STATUS,
							TipoFiltroEnum.IGUAL, getStatusSelecionadoEnum().getCodigo()));
				}

				if (getTipoModalSelecionadoEnum() != null) {
					setWhereSQL(FiltroTabelaBean.montaCondicaoSqlWhereAND(getWhereSQL(), CadastroBL.CAMPO_TIPO_MODAL,
							TipoFiltroEnum.IGUAL, getTipoModalSelecionadoEnum().getCodigo()));
				}

				setWhereSQL(FiltroTabelaBean.montaCondicaoSqlWhereAND(getWhereSQL(), CadastroBL.CAMPO_ENVIARPARAMAPA,
						TipoFiltroEnum.IGUAL, 1));

			}
		} catch (Exception e) {
			setFiltrosParametros(null);
			setWhereSQL(null);
			e.printStackTrace();
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

	public UserMB getUserMB() {
		return userMB;
	}

	public void setUserMB(UserMB userMB) {
		this.userMB = userMB;
	}

	public List<CadastroBLDescricaoMercadoria> getListaBLTodosDescMercadoria() {
		if (listaBLTodosDescMercadoria == null) {
			if (getEntidade() != null) {
				listaBLTodosDescMercadoria = getEntidade().getListaBLDescricaoMercadoria();
			}
		}
		return listaBLTodosDescMercadoria;
	}

	public List<CadastroBLAnexos> getListaBLTodosAnexos() {
		if (listaBLTodosAnexos == null) {
			if (getEntidade() != null) {
				listaBLTodosAnexos = getCadBLAnexosRN().listaTodosPorTipoAnexosMapa(getEntidade());
				// listaBLTodosAnexos =
				// getCadBLAnexosRN().listaTodosPorTipoAnexos(getEntidade(),
				// TipoAnexosEnum.BL);
			}
		}
		return listaBLTodosAnexos;
	}

	public void setListaBLTodosAnexos(List<CadastroBLAnexos> listaBLTodosAnexos) {
		this.listaBLTodosAnexos = listaBLTodosAnexos;
	}

	public void download(javax.faces.event.ActionEvent evento) {
		try {
			anexo = (CadastroBLAnexos) evento.getComponent().getAttributes().get("anexoSelecionado");

			FileInputStream stream = null;
			if (JSFUtil.getVerificaSistemaOperacional().equals("WIN")) {
				if (anexo.getTipoAnexosEnum() == TipoAnexosEnum.MAPA_ANEXO
						|| anexo.getTipoAnexosEnum() == TipoAnexosEnum.ANEXO_LI
						|| anexo.getTipoAnexosEnum() == TipoAnexosEnum.DTC_DTA ) {
					stream = new FileInputStream(
							"//10.1.5.111/d$/Anexos/Agendamento/outros/" + anexo.getCaminhoAnexo());
				} else {
					stream = new FileInputStream(DIRETORIO_WIN + anexo.getCaminhoAnexo());
				}
			} else {
				stream = new FileInputStream(DIRETORIO_LINUX + anexo.getCaminhoAnexo());
			}

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

	public void upload(FileUploadEvent evento) throws SQLException, EmailException {
		try {
			CadastroBLAnexos itemAnexo = new CadastroBLAnexos();
			Path arquivoTemp = Files.createTempFile(null, null);
			Files.copy(evento.getFile().getInputstream(), arquivoTemp, StandardCopyOption.REPLACE_EXISTING);

			itemAnexo.setCadastroBL(getEntidade());
			itemAnexo.setCaminhoAnexo(evento.getFile().getFileName().replace(".pdf", "") + ".pdf");

			Path origem = Paths.get(arquivoTemp.toString());

			Path destino = null;
			if (JSFUtil.getVerificaSistemaOperacional().equals("WIN")) {
				destino = Paths.get(DIRETORIO_WIN + itemAnexo.getCaminhoAnexo());
			} else {
				destino = Paths.get(DIRETORIO_LINUX + itemAnexo.getCaminhoAnexo());
			}

			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);

			itemAnexo = getCadBLAnexosRN().alterar(itemAnexo, userMB.getUser());
			getEntidade().getListaCadastroBLAnexos().add(itemAnexo);

			getEntidade().setStatusBLEnum(StatusBLEnum.AGUARDANDO_LIBERACAO_PROCESSOS_QUE_ANEXARAM);
			getCadastroBLRN().alterar(getEntidade(), userMB.getUser());
			for (CadastroBLContanier item : getEntidade().getListaCadastroBLContanier()) {
				item.setStatusBLEnum(StatusBLEnum.AGUARDANDO_LIBERACAO_PROCESSOS_QUE_ANEXARAM);
				getCadBLContanierRN().alterar(item, getUserMB().getUser(), null);
			}

			setListaBLTodosAnexos(null);
			listaBLTodosAnexos = null;
			setStatusSelecionadoEnum(StatusBLEnum.PENDENTE_ANEXO);
			filtroTabela();

			JSFMessageUtil.adicionarMensagemSucesso("Upload realizado com sucesso.");
		} catch (IOException e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		} catch (Throwable e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public StatusBLEnum getStatusSelecionadoEnum() {
		return statusSelecionadoEnum;
	}

	public void setStatusSelecionadoEnum(StatusBLEnum statusSelecionadoEnum) {
		this.statusSelecionadoEnum = statusSelecionadoEnum;
	}

	public void instanciarBLRisco(CadastroBL entidade, int valor) {

		if (getPosicaoLinhaDataTable() == -1) {
			setPosicaoLinhaDataTable(valor);
		}

		setEntidade(entidade);
		setEntidadeRisco(new CadastroBLRiscoFitossanitario());
		setRiscosSelecionadoEnum(null);

		listaTodosRiscos = null;
	}

	public void instanciarBLDescMercadoria(CadastroBL entidade, int valor) {
		if (getPosicaoLinhaDataTable() == -1) {
			setPosicaoLinhaDataTable(valor);
		}

		setEntidade(entidade);
		setDescricaoMercadoria(null);
		setEntidadeDescMercadoria(new CadastroBLDescricaoMercadoria());
		setEmbalagemEncontradaSelecionado(null);
		embalagemEncontradaSelecionado = null;

		listaBLTodosDescMercadoria = null;
	}

	public void incluirDescMercadoria() {
		try {
			getEntidadeDescMercadoria().setDescricaoMercadoria(getEmbalagemEncontradaSelecionado().getDescricao());
			getEntidadeDescMercadoria().setUser(userMB.getUser());
			getEntidadeDescMercadoria().setCadastroBL(getEntidade());

			CadastroBLDescricaoMercadoria retMercadoria = getDescricaoMercadoriaRN()
					.incluir(getEntidadeDescMercadoria());

			getEntidade().getListaBLDescricaoMercadoria().add(retMercadoria);

			instanciarBLDescMercadoria(getEntidade(), 0);
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
			e.printStackTrace();
		}
	}

	public void incluirRisco() {
		try {
			getEntidadeRisco().setCadastroBL(getEntidade());
			getEntidadeRisco().setRiscoFitossanitarioEnum(getRiscosSelecionadoEnum());
			getEntidadeRisco().setUser(userMB.getUser());

			CadastroBLRiscoFitossanitario teste = getRiscoRN().incluir(getEntidadeRisco());
			getEntidade().getListaBLRisco().add(teste);

			listaTodosRiscos = null;

			instanciarBLRisco(getEntidade(), 0);
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro("Obrigat�rio selecionar um Risco Fitossanit�rio !");
			e.printStackTrace();
		}
	}

	public String pintarColuna(CadastroBLRiscoFitossanitario entidade) {
		String style = "";
		if (entidade != null) {
			style = "background-color:" + entidade.getRiscoFitossanitarioEnum().getCor();
		}
		return style;
	}

	public String retornaUltimoRegistro(CadastroBL entidade) {
		String tipoRisco = "";
		CadastroBLRiscoFitossanitario ultRegistro = null;
		if (entidade != null) {
			if (entidade.getListaBLRisco().size() > 0) {
				ultRegistro = entidade.getListaBLRisco().get(entidade.getListaBLRisco().size() - 1);
			}
		}

		if (ultRegistro != null) {
			tipoRisco = ultRegistro.getRiscoFitossanitarioEnum().getDescricao();
		}

		return tipoRisco;
	}

	public String retornaUltimoUsuario(CadastroBLContanier entidade) {
		String nomeUsuario = "";
		CadastroBlLogAlteracao ultRegistro = null;
		if (entidade != null) {
			if (entidade.getListaBLLogs().size() > 0) {
				ultRegistro = entidade.getListaBLLogs().get(entidade.getListaBLLogs().size() - 1);
			}
		}

		if (ultRegistro != null) {
			nomeUsuario = ultRegistro.getUser().getEmail();
		}
		return nomeUsuario;
	}

	private AuditoriaLogRN getAuditoriaLogRN() {
		if (auditoriaLogRN == null) {
			auditoriaLogRN = new AuditoriaLogRN();
		}
		return auditoriaLogRN;
	}

	public void instanciarBLLog(CadastroBL item) {
		listaLogAuditoria(item);
	}

	public String anuenciaMapaSimNao(CadastroBL bl) {
		String valor = "Não";
		
		if (bl.isAnuenciaMapa()) {
			valor = "Sim";
		}
		if (bl != null && bl.getNcm() != null) {
			if (bl.getNcm().isProdutoAnuencia()) {
				valor = "Sim";
			}
		}
		return valor;
	}
	
	private Date dataEnvioMapaLOG;

	private List<MapaNcm> listaMapaNCM;


	public List<LogInspecaoLiberadoMapa> listaLogAuditoria(CadastroBL item) {
		try {
			if (item != null) {
				listaLogInspecaoLiberadoMapa = getAuditoriaLogRN().getLogRegistrosContainer(item);

				dataEnvioMapaLOG = item.getDataEnvioMapa();
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
		return listaLogInspecaoLiberadoMapa;
	}

	public String pintarTextoUltimaAlteracao(CadastroBL entidade) {
		String style = "";
		CadastroBLRiscoFitossanitario ultRegistro = null;
		if (entidade != null) {
			if (entidade.getListaBLRisco().size() > 0) {
				ultRegistro = entidade.getListaBLRisco().get(entidade.getListaBLRisco().size() - 1);
			}
		}
		if (ultRegistro != null) {
			style = "color:" + ultRegistro.getRiscoFitossanitarioEnum().getCor();
		}

		return style;
	}

	public String pintarBotaoUltimaAlteracao(CadastroBL entidade) {
		String style = "";
		CadastroBLRiscoFitossanitario ultRegistro = null;
		if (entidade != null) {
			if (entidade.getListaBLRisco().size() > 0) {
				ultRegistro = entidade.getListaBLRisco().get(entidade.getListaBLRisco().size() - 1);
			}
		}
		if (ultRegistro != null) {
			style = "background-color:" + ultRegistro.getRiscoFitossanitarioEnum().getCor();
		}

		return style;
	}

	public String retornaUltimaDescricaoMercadoria(CadastroBL entidade) {
		String style = "";
		CadastroBLDescricaoMercadoria ultRegistro = null;
		if (entidade != null) {
			if (entidade.getListaBLDescricaoMercadoria().size() > 0) {
				ultRegistro = entidade.getListaBLDescricaoMercadoria()
						.get(entidade.getListaBLDescricaoMercadoria().size() - 1);
			}
		}
		if (ultRegistro != null) {
			style = ultRegistro.getDescricaoMercadoria();
		}

		return style;
	}

	public void instanciarBLAlteracao(CadastroBLContanier entidade) {
		setEmbalagemEncontradaSelecionado(entidade.getCadastroBL().getTipoEmbalagemEncontrada());
		setEntidadeBLAlteracao(entidade.getCadastroBL());
	}

	public void instanciarBLEmbEncontrada(CadastroBL entidade) {
		setEntidadeBLAlteracao(entidade);
		setEmbalagemEncontradaSelecionado(entidade.getTipoEmbalagemEncontrada());
	}

	public void salvarAlteracao() {
		try {
			getEntidadeBLAlteracao().setTipoEmbalagemEncontrada(getEmbalagemEncontradaSelecionado());
			getEntidadeBLAlteracao().setAlteracaoDescricaoMercadoria(true);
			getCadastroBLRN().alterar(getEntidadeBLAlteracao(), userMB.getUser());

			setEmbalagemEncontradaSelecionado(null);

			RequestContext.getCurrentInstance().execute("PF('dlgAlteracao').hide()");
			RequestContext.getCurrentInstance().execute("PF('dlgAlteracaoEmbEncontrada').hide()");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fecharRisco() {
		RequestContext.getCurrentInstance().execute("PF('dlgRisco').hide()");

		RequestContext.getCurrentInstance().update("frmListarPrincipal:tabItensConsultaBL:tbListarEntidade:"
				+ getPosicaoLinhaDataTable() + ":prinLiberado");
		RequestContext.getCurrentInstance().update(
				"frmListarPrincipal:tabItensConsultaBL:tbListarEntidade:" + getPosicaoLinhaDataTable() + ":btRisco");
		RequestContext.getCurrentInstance().update(
				"frmListarPrincipal:tabItensConsultaBL:tbListarEntidade:" + getPosicaoLinhaDataTable() + ":textoRisco");

		int pos = 0;
		for (CadastroBLContanier item : getEntidade().getListaCadastroBLContanier()) {
			RequestContext.getCurrentInstance()
					.update("frmListarPrincipal:tabItensConsultaBL:tbListarEntidade:" + getPosicaoLinhaDataTable()
							+ ":tabColunasPrincipal:tbListaTodosContainers:" + pos + ":expLiberado");

			pos = pos + 1;
		}

		setPosicaoLinhaDataTable(-1);
	}

	public void fecharLog() {
		RequestContext.getCurrentInstance().execute("PF('dlgLogBL').hide()");
	}

	public void fecharDescricaoMercadoria() {
		RequestContext.getCurrentInstance().execute("PF('dlgDescMercadoria').hide()");

		RequestContext.getCurrentInstance().update("frmListarPrincipal:tabItensConsultaBL:tbListarEntidade:"
				+ getPosicaoLinhaDataTable() + ":prinLiberado");
		RequestContext.getCurrentInstance().update("frmListarPrincipal:tabItensConsultaBL:tbListarEntidade:"
				+ getPosicaoLinhaDataTable() + ":btEmbEncontrada");
		RequestContext.getCurrentInstance().update("frmListarPrincipal:tabItensConsultaBL:tbListarEntidade:"
				+ getPosicaoLinhaDataTable() + ":textoEmbEncontrada");

		int pos = 0;
		for (CadastroBLContanier item : getEntidade().getListaCadastroBLContanier()) {
			RequestContext.getCurrentInstance()
					.update("frmListarPrincipal:tabItensConsultaBL:tbListarEntidade:" + getPosicaoLinhaDataTable()
							+ ":tabColunasPrincipal:tbListaTodosContainers:" + pos + ":expLiberado");

			pos = pos + 1;
		}

		setPosicaoLinhaDataTable(-1);
	}

	public String retornaUltimoDataAlteracao(CadastroBLContanier entidade) {
		String dataAlterado = "";
		CadastroBlLogAlteracao ultRegistro = null;
		if (entidade != null) {
			if (entidade.getListaBLLogs().size() > 0) {
				ultRegistro = entidade.getListaBLLogs().get(entidade.getListaBLLogs().size() - 1);
			}
		}

		if (ultRegistro != null) {
			dataAlterado = ultRegistro.getDataAlteracao().toString();
		}
		return dataAlterado;
	}

	public CadastroBLContanier getEntidadeContanier() {
		return entidadeContanier;
	}

	public void setEntidadeContanier(CadastroBLContanier entidadeContanier) {
		this.entidadeContanier = entidadeContanier;
	}

	public RiscoFitossanitarioEnum getRiscosSelecionadoEnum() {
		return riscosSelecionadoEnum;
	}

	public void setRiscosSelecionadoEnum(RiscoFitossanitarioEnum riscosSelecionadoEnum) {
		this.riscosSelecionadoEnum = riscosSelecionadoEnum;
	}

	public CadastroBLRiscoFitossanitario getEntidadeRisco() {
		return entidadeRisco;
	}

	public void setEntidadeRisco(CadastroBLRiscoFitossanitario entidadeRisco) {
		this.entidadeRisco = entidadeRisco;
	}

	public CadastroBL getEntidadeBLAlteracao() {
		return entidadeBLAlteracao;
	}

	public void setEntidadeBLAlteracao(CadastroBL entidadeBLAlteracao) {
		this.entidadeBLAlteracao = entidadeBLAlteracao;
	}

	public CadastroEspecie getEmbalagemEncontradaSelecionado() {
		return embalagemEncontradaSelecionado;
	}

	public void setEmbalagemEncontradaSelecionado(CadastroEspecie embalagemEncontradaSelecionado) {
		this.embalagemEncontradaSelecionado = embalagemEncontradaSelecionado;
	}

	public String produtoAnuencia(CadastroBLContanier item) {
		String prodComAnuencia = "N�o";
		if (item != null && item.getCadastroBL().getNcm() != null) {
			if (item.getCadastroBL().getNcm().isProdutoAnuencia()) {
				prodComAnuencia = "Sim";
			}
		}
		return prodComAnuencia;
	}

	public boolean userFiscalNaoAltera(CadastroBLContanier entidade) {
		boolean valor = false;
		if (entidade != null) {
			if (userMB.getUser().getRole().equals(Role.USER) || userMB.getUser().isClif()) {
				valor = true;
			}
		}
		return valor;
	}

	public boolean validarUsuarioDespachante(CadastroBL entidadeBL, CadastroBLContanier entidadeContanier) {
		boolean validar = false;

		if (entidadeBL != null) {
			if (entidadeBL.isInspecao()) {
				validar = true;
			}
		}

		if (entidadeContanier != null) {
			if (entidadeContanier.isInspecao()) {
				validar = true;
			}
		}

		if (userMB.getUser().isCliente() || userMB.getUser().isClif() || userMB.getUser().isAdmin()
				|| userMB.getUser().isAcessoMapaConsulta()) {
			validar = true;
		}

		return validar;
	}

	public boolean validarRiscoBloqueio(CadastroBL entidadeBL, CadastroBLContanier entidadeContanier) {
		boolean validar = false;

		if (userMB.getUser().isClif() || userMB.getUser().isAdmin() || userMB.getUser().isAcessoMapaConsulta()) {
			validar = true;
		}

		return validar;
	}

	public boolean validarEmbalagemEncontrada(CadastroBL entidadeBL) {
		boolean validar = false;

		if (userMB.getUser().isClif() || userMB.getUser().isAdmin() || userMB.getUser().isAcessoMapaConsulta()) {
			validar = true;
		}

		return validar;
	}

	public boolean validarInformacaoLiberado(CadastroBLContanier item, CadastroBL bl) {
		boolean validar = false;
		CadastroBL blVerificarDTA = null;

		if (item != null) {
			try {
				blVerificarDTA = item.getCadastroBL();

				if (item.isInspecao()) {
					bloquearRiscoFitoSeNaoHouverLancamento(item, null);
				}
			} catch (Exception e) {
				validar = true;
			}
		}

		if (bl != null) {
			try {
				blVerificarDTA = bl;

				if (bl.isInspecao()) {
					bloquearRiscoFitoSeNaoHouverLancamento(null, bl);
				}
			} catch (Exception e) {
				validar = true;
			}
		}

		// if (blVerificarDTA.getTipoModalEnum() == TipomodalEnum.DTA_MARITIMO)
		// {
		// validar = false;
		// }

		if (userMB.getUser().isCliente() || userMB.getUser().isClif() || userMB.getUser().isAdmin()
				|| userMB.getUser().isAcessoMapaConsulta()) {
			validar = true;
		}

		if (blVerificarDTA.isLiberado()) {
			validar = blVerificarDTA.isLiberado();
		}

		return validar;
	}

	public String tollTipLiberado(CadastroBLContanier item, CadastroBL bl) {
		String valor = "";
		if (item != null) {
			try {
				if (item.isInspecao()) {
					bloquearRiscoFitoSeNaoHouverLancamento(item, null);
				}
			} catch (Exception e) {
				valor = "Obrigat�rio informar, um risco fitossanit�rio !";
			}
		}

		if (bl != null) {
			try {
				if (bl.isInspecao()) {
					bloquearRiscoFitoSeNaoHouverLancamento(null, bl);
				}
			} catch (Exception e) {
				valor = "Obrigat�rio informar, um risco fitossanit�rio !";
			}
		}
		return valor;
	}

	public boolean bloquearDescMercadoria(CadastroBLContanier entidade) {
		boolean validar = false;

		if (entidade != null) {
			if (entidade.getStatusBLEnum() != StatusBLEnum.PENDENTE_ANEXO) {
				validar = true;
			}
		}
		return validar;
	}

	public String getDescricaoMercadoria() {
		return descricaoMercadoria;
	}

	public void setDescricaoMercadoria(String descricaoMercadoria) {
		this.descricaoMercadoria = descricaoMercadoria;
	}

	public CadastroBLDescricaoMercadoria getEntidadeDescMercadoria() {
		return entidadeDescMercadoria;
	}

	public void setEntidadeDescMercadoria(CadastroBLDescricaoMercadoria entidadeDescMercadoria) {
		this.entidadeDescMercadoria = entidadeDescMercadoria;
	}

	public TipomodalEnum getTipoModalSelecionadoEnum() {
		return tipoModalSelecionadoEnum;
	}

	public void setTipoModalSelecionadoEnum(TipomodalEnum tipoModalSelecionadoEnum) {
		this.tipoModalSelecionadoEnum = tipoModalSelecionadoEnum;
	}

	public List<CadastroBL> getListaControleEnvioBLLiberadoInteiro() {
		return listaControleEnvioBLLiberadoInteiro;
	}

	public void setListaControleEnvioBLLiberadoInteiro(List<CadastroBL> listaControleEnvioBLLiberadoInteiro) {
		this.listaControleEnvioBLLiberadoInteiro = listaControleEnvioBLLiberadoInteiro;
	}

	public List<CadastroBL> getListaControleEnvioBLInspecaoInteiro() {
		return listaControleEnvioBLInspecaoInteiro;
	}

	public void setListaControleEnvioBLInspecaoInteiro(List<CadastroBL> listaControleEnvioBLInspecaoInteiro) {
		this.listaControleEnvioBLInspecaoInteiro = listaControleEnvioBLInspecaoInteiro;
	}

	public List<CadastroBLContanier> getListaControleEnvioContainerLiberado() {
		return listaControleEnvioContainerLiberado;
	}

	public void setListaControleEnvioContainerLiberado(List<CadastroBLContanier> listaControleEnvioContainerLiberado) {
		this.listaControleEnvioContainerLiberado = listaControleEnvioContainerLiberado;
	}

	public List<CadastroBLContanier> getListaControleEnvioContainerInspecao() {
		return listaControleEnvioContainerInspecao;
	}

	public void setListaControleEnvioContainerInspecao(List<CadastroBLContanier> listaControleEnvioContainerInspecao) {
		this.listaControleEnvioContainerInspecao = listaControleEnvioContainerInspecao;
	}

	public int getPosicaoLinhaDataTable() {
		return posicaoLinhaDataTable;
	}

	public void setPosicaoLinhaDataTable(int posicaoLinhaDataTable) {
		this.posicaoLinhaDataTable = posicaoLinhaDataTable;
	}

	public boolean isExpandirDados() {
		return expandirDados;
	}

	public void setExpandirDados(boolean expandirDados) {
		this.expandirDados = expandirDados;
	}

	public void expandirDados() {
		if (isExpandirDados()) {
			setExpandirDados(false);
			setControlarImagem("fa fa-angle-double-down");
		} else {
			setExpandirDados(true);
			setControlarImagem("fa fa-angle-double-up");
		}
	}

	public String getControlarImagem() {
		return controlarImagem;
	}

	public void setControlarImagem(String controlarImagem) {
		this.controlarImagem = controlarImagem;
	}

	public Date getDataEnvioMapaLOG() {
		return dataEnvioMapaLOG;
	}

	public void setDataEnvioMapaLOG(Date dataEnvioMapaLOG) {
		this.dataEnvioMapaLOG = dataEnvioMapaLOG;
	}

	public void carregarNCM(CadastroBL item) {
		setListaMapaNCM(item.getListaMapaNcm());
	}

	public List<MapaNcm> getListaMapaNCM() {
		return listaMapaNCM;
	}
	
	public void setListaMapaNCM(List<MapaNcm> listaMapaNCM) {
		this.listaMapaNCM = listaMapaNCM;
	}
	
	
}
