package com.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

import com.util.JSFMessageUtil;

import conexao.com.bean.AbstractLazyDataModel;
import conexao.com.bean.AbstractMB;
import conexao.com.rn.CadastroBLAnexosRN;
import conexao.com.rn.CadastroBLContanierRN;
import conexao.com.rn.CadastroBLRN;
import conexao.com.rn.CadastroCodigoPaisRN;
import conexao.com.rn.CadastroComissarioRN;
import conexao.com.rn.CadastroEspecieRN;
import conexao.com.rn.CadastroImportadorRN;
import conexao.com.rn.CadastroNcmRN;
import conexao.com.rn.UserComissarioRN;
import conexao.com.util.FiltroTabelaBean;
import conexao.com.util.IGenericBean;
import seguranca.com.entidade.CadastroBL;
import seguranca.com.entidade.CadastroBLContanier;
import seguranca.com.entidade.CadastroCodigoPais;
import seguranca.com.entidade.CadastroComissario;
import seguranca.com.entidade.CadastroEspecie;
import seguranca.com.entidade.CadastroImportador;
import seguranca.com.entidade.CadastroNcm;
import seguranca.com.entidade.UserComissario;
import seguranca.com.enums.ModalidadeBLEnum;
import seguranca.com.enums.StatusBLEnum;
import seguranca.com.enums.TipoContainerEnum;
import seguranca.com.enums.TipoPessoaFisicaJuridicaEnum;
import seguranca.com.enums.TipomodalEnum;

@ViewScoped
@ManagedBean
public class CadastroBLBean extends AbstractMB implements IGenericBean {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	private static final String GRID_CONTAINER = "frmCadastrarEntidade:formCadEntidadeBL:tbListaTodosContanier";

	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;

	private boolean controlarFormListar = true;
	private boolean controlarFormCadastrar = false;
	private String numeroContainer;
	private String lacreContainer;
	private String razaoSocial;
	private String cnpj;

	private CadastroBL entidade;
	private CadastroImportador entidadeImportador;
	private CadastroComissario entidadeCamissaria;
	private CadastroCodigoPais paisSelecionado;
	private CadastroImportador importadorSelecionado;
	private CadastroNcm ncmSelecionado;
	private CadastroEspecie especieSelecionado;
	private CadastroEspecie embalagemEncontradaSelecionado;
	private CadastroCodigoPais paisProcedenciaSelecionado;
	private UserComissario userComissario;
	private CadastroBLRN cadBLRN;
	private CadastroBLAnexosRN cadBLAnexosRN;
	private CadastroNcmRN cadNcmRN;
	private CadastroEspecieRN cadEspecieRN;
	private CadastroBLContanierRN cadBLContanierRN;
	private CadastroCodigoPaisRN cadCodigoPaisRN;
	private CadastroImportadorRN importadorRN;
	private CadastroComissarioRN cadastroComissarioRN;
	private UserComissarioRN userComissarioRN;

	private List<TipoContainerEnum> todasTipoContainerEnum;
	private List<ModalidadeBLEnum> todasModalidadeBLEnum;
	private List<TipomodalEnum> todasTipoModalEnum;
	private List<CadastroComissario> listaUserComissario;
	private List<CadastroBLContanier> listaBLContanier;

	private LazyDataModel<CadastroBL> lazyModel;

	public CadastroComissarioRN getCadastroComissarioRN() {
		if (cadastroComissarioRN == null) {
			cadastroComissarioRN = new CadastroComissarioRN();
		}
		return cadastroComissarioRN;
	}

	public UserComissarioRN getUserComissarioRN() {
		if (userComissarioRN == null) {
			userComissarioRN = new UserComissarioRN();
		}
		return userComissarioRN;
	}

	public CadastroImportadorRN getImportadorRN() {
		if (importadorRN == null) {
			importadorRN = new CadastroImportadorRN();
		}
		return importadorRN;
	}

	public List<CadastroComissario> getListaUserComissario() throws Exception {
		if (listaUserComissario == null) {

			if (userMB.getUser().isCliente()) {
				List<UserComissario> list = getUserComissarioRN().listaComissariasPorUsuario(userMB.getUser());
				listaUserComissario = new ArrayList<CadastroComissario>();
				for (UserComissario item : list) {
					listaUserComissario.add(item.getCadComissario());
				}
			} else {
				listaUserComissario = getCadastroComissarioRN().listaTodos();
			}
		}
		return listaUserComissario;
	}

	public void carregarDadosComissarias() {
		listaUserComissario = null;
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

	public List<CadastroCodigoPais> listaCodigoPais(String pesquisa) {
		List<CadastroCodigoPais> listaCadastroCodigoPais = new ArrayList<CadastroCodigoPais>();
		if (getEntidade() != null) {
			listaCadastroCodigoPais = getCadCodigoPaisRN().listaTodosAutoComplete(pesquisa);
		}
		return listaCadastroCodigoPais;
	}

	public List<CadastroImportador> listaImportador(String pesquisa) {
		List<CadastroImportador> listaCadastroCodigoPais = new ArrayList<CadastroImportador>();
		if (getEntidade() != null) {
			listaCadastroCodigoPais = getImportadorRN().listaTodosAutoComplete(pesquisa);
		}
		return listaCadastroCodigoPais;
	}

	public List<CadastroEspecie> listaCadastroEspecie(String pesquisa) {
		List<CadastroEspecie> listaCadastroEspecie = new ArrayList<CadastroEspecie>();
		if (getEntidade() != null) {
			listaCadastroEspecie = getCadEspecieRN().listaTodosAutoComplete(pesquisa);
		}
		return listaCadastroEspecie;
	}

	public List<CadastroNcm> listaCadastroNCM(String pesquisa) {
		List<CadastroNcm> listaCadastroNCM = new ArrayList<CadastroNcm>();
		if (getEntidade() != null) {
			listaCadastroNCM = getCadNcmRN().listaTodosAutoComplete(pesquisa);
		}
		return listaCadastroNCM;
	}

	public List<TipomodalEnum> getTodasTipoModalEnum() {
		if (todasTipoModalEnum == null && isControlarFormCadastrar()) {
			todasTipoModalEnum = new ArrayList<TipomodalEnum>();
			todasTipoModalEnum = new ArrayList<TipomodalEnum>(Arrays.asList(TipomodalEnum.values()));
		}
		return todasTipoModalEnum;
	}

	public List<TipoContainerEnum> getTodosTiposContainerEnum() {
		if (todasTipoContainerEnum == null && isControlarFormCadastrar()) {
			todasTipoContainerEnum = new ArrayList<TipoContainerEnum>();
			todasTipoContainerEnum = new ArrayList<TipoContainerEnum>(Arrays.asList(TipoContainerEnum.values()));
		}
		return todasTipoContainerEnum;
	}

	public List<ModalidadeBLEnum> getTodasModalidadeBLEnum() {
		if (todasModalidadeBLEnum == null && isControlarFormCadastrar()) {
			todasModalidadeBLEnum = new ArrayList<ModalidadeBLEnum>();
			todasModalidadeBLEnum = new ArrayList<ModalidadeBLEnum>(Arrays.asList(ModalidadeBLEnum.values()));
		}
		return todasModalidadeBLEnum;
	}

	public CadastroCodigoPaisRN getCadCodigoPaisRN() {
		if (cadCodigoPaisRN == null) {
			cadCodigoPaisRN = new CadastroCodigoPaisRN();
		}
		return cadCodigoPaisRN;
	}

	public CadastroNcmRN getCadNcmRN() {
		if (cadNcmRN == null) {
			cadNcmRN = new CadastroNcmRN();
		}
		return cadNcmRN;
	}

	public CadastroEspecieRN getCadEspecieRN() {
		if (cadEspecieRN == null) {
			cadEspecieRN = new CadastroEspecieRN();
		}
		return cadEspecieRN;
	}

	public CadastroBLRN getCadastroBLRN() {
		if (cadBLRN == null) {
			cadBLRN = new CadastroBLRN();
		}
		return cadBLRN;
	}

	public void redirecionarCadastro() {
		try {
			setPaisSelecionado(null);
			setPaisProcedenciaSelecionado(null);
			setNcmSelecionado(null);
			setEspecieSelecionado(null);
			setEmbalagemEncontradaSelecionado(null);
			setImportadorSelecionado(null);

			setControlarFormCadastrar(true);
			setControlarFormListar(false);
			setEntidade(new CadastroBL());
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

	public void editarEntidade(CadastroBL entidade) {
		try {
			setPaisSelecionado(entidade.getPaisOrigem());
			setPaisProcedenciaSelecionado(entidade.getPaisProcedencia());
			setNcmSelecionado(entidade.getNcm());
			setEspecieSelecionado(entidade.getTipoEmbalagemEspecie());
			setEmbalagemEncontradaSelecionado(entidade.getTipoEmbalagemEncontrada());
			setImportadorSelecionado(entidade.getImportador());

			setControlarFormCadastrar(true);
			setControlarFormListar(false);
			setEntidade(entidade);
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
				blContanier.setStatusBLEnum(StatusBLEnum.PENDENTE_ANEXO);
				blContanier.setCadastroBL(getEntidade());

				getListaBLContanier().add(blContanier);

				numeroContainer = null;
				lacreContainer = null;
				setNumeroContainer(null);
				setLacreContainer(null);
			} else {
				JSFMessageUtil.adicionarMensagemErro("Número container não válido !");
			}
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public List<CadastroBLContanier> getListaBLContanier() {
		if (listaBLContanier == null) {
			if (getEntidade() != null) {
				listaBLContanier = getEntidade().getListaCadastroBLContanier();
			}
		}
		return listaBLContanier;
	}

	public void removerContanier(CadastroBLContanier contanier) {
		try {
			if (contanier.getId() != null) {
				getCadBLContanierRN().deletar(contanier);
			}
			getEntidade().getListaCadastroBLContanier().remove(contanier);
			listaBLContanier = null;

		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void voltarPgPrincipal() {
		setControlarFormCadastrar(false);
		setControlarFormListar(true);
	}

	public void salvarEntidade() {
		try {
			getEntidade().setPaisOrigem(getPaisSelecionado());
			getEntidade().setPaisProcedencia(getPaisProcedenciaSelecionado());
			getEntidade().setNcm(getNcmSelecionado());
			getEntidade().setTipoEmbalagemEspecie(getEspecieSelecionado());
			getEntidade().setTipoEmbalagemEncontrada(getEmbalagemEncontradaSelecionado());
			getEntidade().setImportador(getImportadorSelecionado());

			setControlarFormCadastrar(false);
			setControlarFormListar(true);
			setEntidade(getCadastroBLRN().alterar(getEntidade(), userMB.getUser()));
			atualizarFormPrincipal();

			RequestContext.getCurrentInstance().update(GRID_CONTAINER);
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	private void atualizarFormPrincipal() {
		RequestContext.getCurrentInstance().update(PG_FORM_PRINCIPAL);
	}

	public void remover(CadastroBL entidade) {
		try {
			getCadastroBLRN().deletar(entidade);
			atualizarFormPrincipal();
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public CadastroBL getEntidade() {
		return entidade;
	}

	public void setEntidade(CadastroBL entidade) {
		this.entidade = entidade;
	}

	public boolean isControlarFormListar() {
		return controlarFormListar;
	}

	public void setControlarFormListar(boolean controlarFormListar) {
		this.controlarFormListar = controlarFormListar;
	}

	public boolean isControlarFormCadastrar() {
		return controlarFormCadastrar;
	}

	public void setControlarFormCadastrar(boolean controlarFormCadastrar) {
		this.controlarFormCadastrar = controlarFormCadastrar;
	}

	public LazyDataModel<CadastroBL> getLazyModel() {
		if (lazyModel == null) {
			lazyModel = new AbstractLazyDataModel<CadastroBL>(CadastroBL.sql, CadastroBL.sqlCount, getWhereSQL(), null);
		}

		return lazyModel;
	}

	@Override
	public void filtroTabela() {
		try {
			lazyModel = null;
			setWhereSQL(null);

			if (!getFiltroGeral().isEmpty()) {

				getFiltrosParametros().add(CadastroBL.CADASTRO_BL_DESCRICAOBL);

				setWhereSQL(FiltroTabelaBean.montaCondicaoSqlWhereOR(getFiltroGeral(), getFiltrosParametros()));
			}
		} catch (Exception e) {
			setFiltrosParametros(null);
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

	public String getNumeroContainer() {
		return numeroContainer;
	}

	public void setNumeroContainer(String numeroContainer) {
		this.numeroContainer = numeroContainer;
	}

	public CadastroCodigoPais getPaisSelecionado() {
		return paisSelecionado;
	}

	public void setPaisSelecionado(CadastroCodigoPais paisSelecionado) {
		this.paisSelecionado = paisSelecionado;
	}

	public CadastroCodigoPais getPaisProcedenciaSelecionado() {
		return paisProcedenciaSelecionado;
	}

	public void setPaisProcedenciaSelecionado(CadastroCodigoPais paisProcedenciaSelecionado) {
		this.paisProcedenciaSelecionado = paisProcedenciaSelecionado;
	}

	public CadastroNcm getNcmSelecionado() {
		return ncmSelecionado;
	}

	public void setNcmSelecionado(CadastroNcm ncmSelecionado) {
		this.ncmSelecionado = ncmSelecionado;
	}

	public CadastroEspecie getEspecieSelecionado() {
		return especieSelecionado;
	}

	public void setEspecieSelecionado(CadastroEspecie especieSelecionado) {
		this.especieSelecionado = especieSelecionado;
	}

	public CadastroImportador getImportadorSelecionado() {
		return importadorSelecionado;
	}

	public void setImportadorSelecionado(CadastroImportador importadorSelecionado) {
		this.importadorSelecionado = importadorSelecionado;
	}

	public CadastroEspecie getEmbalagemEncontradaSelecionado() {
		return embalagemEncontradaSelecionado;
	}

	public void setEmbalagemEncontradaSelecionado(CadastroEspecie embalagemEncontradaSelecionado) {
		this.embalagemEncontradaSelecionado = embalagemEncontradaSelecionado;
	}

	public String getLacreContainer() {
		return lacreContainer;
	}

	public void setLacreContainer(String lacreContainer) {
		this.lacreContainer = lacreContainer;
	}

	public CadastroImportador getEntidadeImportador() {
		return entidadeImportador;
	}

	public void setEntidadeImportador(CadastroImportador entidadeImportador) {
		this.entidadeImportador = entidadeImportador;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public void salvarImportadorComissaria() {
		try {

			if (getEntidadeCamissaria() != null) {
				getEntidadeCamissaria().setCpf(getCnpj());
				getEntidadeCamissaria().setNome(getRazaoSocial());
				getEntidadeCamissaria().setTipoPessoaEnum(TipoPessoaFisicaJuridicaEnum.PESSOA_JURIDICA);

				CadastroComissario novoComissaria = getCadastroComissarioRN().incluir(getEntidadeCamissaria());

				setUserComissario(new UserComissario());
				getUserComissario().setUsuario(userMB.getUser());
				getUserComissario().setCadComissario(novoComissaria);
				getUserComissarioRN().alterar(getUserComissario());

				listaUserComissario = null;
				setEntidadeCamissaria(null);
			}

			if (getEntidadeImportador() != null) {
				getEntidadeImportador().setCnpj(getCnpj());
				getEntidadeImportador().setRazaoSocial(getRazaoSocial());

				getImportadorRN().incluir(getEntidadeImportador());
				setEntidadeImportador(null);
			}

			RequestContext.getCurrentInstance().execute("PF('dlgBLIncluir').hide()");
		} catch (Exception e) {
			e.printStackTrace();
			conexao.com.util.JSFMessageUtil.adicionarMensagemErro(e.getMessage());

		}
	}

	public void instanciarImportadorComissaria(int entidade) {
		setRazaoSocial(null);
		setCnpj(null);
		if (entidade == 1) {
			setEntidadeImportador(new CadastroImportador());
		}
		if (entidade == 2) {
			setEntidadeCamissaria(new CadastroComissario());
		}
	}

	public CadastroComissario getEntidadeCamissaria() {
		return entidadeCamissaria;
	}

	public void setEntidadeCamissaria(CadastroComissario entidadeCamissaria) {
		this.entidadeCamissaria = entidadeCamissaria;
	}

	public UserComissario getUserComissario() {
		return userComissario;
	}

	public void setUserComissario(UserComissario userComissario) {
		this.userComissario = userComissario;
	}
}