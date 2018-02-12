package com.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.model.LazyDataModel;

import conexao.com.bean.AbstractLazyDataModel;
import conexao.com.bean.AbstractMB;
import conexao.com.rn.UserFacade;
import conexao.com.util.FiltroTabelaBean;
import conexao.com.util.IGenericBean;
import seguranca.com.entidade.User;
import seguranca.com.entidade.UserPermissoes;
import seguranca.com.enums.Role;
import seguranca.com.enums.TelasEntidadesEnum;

@SessionScoped
@ManagedBean(name="userMB")
public class UserMB extends AbstractMB implements IGenericBean {
	public static final String INJECTION_NAME = "#{userMB}";

	private String nomeTela;
	private User user;
	private UserFacade rn;
	
	private boolean telaLiberadaComissario;
	private boolean telaLiberadaBL;
	private boolean telaLiberadaConsultarBL;
	private boolean telaLiberadaConsultarBLClif;
	private boolean telaLiberadaImportador;
	private boolean telaLiberadaConsultarLog;

	private int modoEdicao = 0;
	private LazyDataModel<User> lazyModel = null;
	private List<Role> todosAdminUserEnum;

	public boolean isAdmin() {
		return user.isAdmin();
	}

	public boolean isDefaultUser() {
		return user.isUser();
	}

	public String logOut() {
		getRequest().getSession().invalidate();
		return "/pages/public/login.xhtml";
	}

	private HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public User getUser() {
		if (user == null) {
			user = new User();
		}

		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNomeTela() {
		if (nomeTela == null) {
		}

		return nomeTela;
	}

	public void setNomeTela(String nomeTela) {
		this.nomeTela = nomeTela;
	}

	public LazyDataModel<User> getLazyModel() {
		if (lazyModel == null) {
			lazyModel = new AbstractLazyDataModel<User>(User.sql, User.sqlCount, null, null);			
		}

		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<User> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public UserFacade getRn() {
		if (rn == null) {
			rn = new UserFacade();
		}

		return rn;
	}

	public void setRn(UserFacade rn) {
		this.rn = rn;
	}

	public int getModoEdicao() {
		return modoEdicao;
	}

	public void setModoEdicao(int modoEdicao) {
		this.modoEdicao = modoEdicao;
	}

	/**
	 * Metodo remove excluir o registro que foi selecionado no parametro item
	 * 
	 * @param item
	 */
	public void remove(User item) {
		try {
			getRn().deletar(item);
			closeDialog();
			displayInfoMessageToUser("Excluido com Sucesso !");
			limparInstancia();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Nao foi possivel. Tente novamente mais tarde");
			e.printStackTrace();
		}
	}

	public void instanciarEditar(User item) {
		User retornaDadosAtuais = getRn().localizar(item.getId());

		setModoEdicao(-1);
		setUser(retornaDadosAtuais);
	}

	public void limparInstancia() {
		setUser(null);
	}

	public void instanciarIncluir() {
		setModoEdicao(0);
	}
	
	public String retornaPaginaInicial() {
		return "/pages/protected/index.xhtml";
	}
	
	/**
	 * Controla os campos lables para quando for incluir um novo registro não
	 * apareca o campo
	 * 
	 * @return
	 */
	public Boolean controlaCampo() {
		if (getModoEdicao() == -1) {
			return true;
		}
		return false;
	}
	
	/**
	 * Metodo controla botão alterar quando estiver incluindo nao apareca o
	 * botao de alterar
	 * 
	 * @return
	 */
	public Boolean controlaBoataoAlterar() {
		if (getModoEdicao() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo controla botão quando estiver ediando nao apareca o botao de
	 * incluir
	 * 
	 * @return
	 */
	public Boolean controlaBoataoIncluir() {
		if (getModoEdicao() == -1) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo alterar pega os dados do objeto e realiza a alterações dos dados
	 */
	public void alterar() {
		try {
			getRn().alterar(user);
			closeDialog();
			displayInfoMessageToUser("Alterado com Sucesso !");
			setLazyModel(null);
			limparInstancia();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Nao foi possivel. Tente novamente mais tarde");
			e.printStackTrace();
		}
	}

	/**
	 * Metodo salvar pega os daos do objeto e salva os dados
	 */
	public void salvar() {
		try {
			getRn().incluir(user);
			closeDialog();
			displayInfoMessageToUser("Salvo com Sucesso !");
			limparInstancia();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Nao foi possivel. Tente novamente mais tarde");
			e.printStackTrace();
		}
	}

	public List<Role> getTodosAdminUserEnum() {
		if (todosAdminUserEnum == null) {
			todosAdminUserEnum = new ArrayList<Role>();
			todosAdminUserEnum = new ArrayList<Role>(Arrays.asList(Role.values()));	
		}

		return todosAdminUserEnum;
	}

	public void setTodosAdminUserEnum(List<Role> todosAdminUserEnum) {
		this.todosAdminUserEnum = todosAdminUserEnum;
	}

	@Override
	public void filtroTabela() {
	}

	public FiltroTabelaBean getFiltroTabelaBean() {
		return null;
	}

	public void setFiltroTabelaBean(FiltroTabelaBean filtroTabelaBean) {
	}

	@Override
	public String getFiltroGeral() {
		return null;
	}

	@Override
	public void setFiltroGeral(String filtroGeral) {
	}

	public boolean isTelaLiberadaComissario() {
		return telaLiberadaComissario;
	}

	public void setTelaLiberadaComissario(boolean telaLiberadaComissario) {
		this.telaLiberadaComissario = telaLiberadaComissario;
	}
	
	public void verificarPermissoesTelas() {
		if (user != null) {
			executarPermissoesDasTelas(false);
			if (user.isUser() || user.isCliente() || user.isClif() || user.isDespachante() ) {
				for (UserPermissoes item : user.getListaUserPermissoes()) {
					if (item.getTelasEntidadesEnum().equals(TelasEntidadesEnum.CADASTRO_COMISSARIO)) {
						setTelaLiberadaComissario(true);
					}
					if (item.getTelasEntidadesEnum().equals(TelasEntidadesEnum.CADASTRO_BL)) {
						setTelaLiberadaBL(true);
					}
					if (item.getTelasEntidadesEnum().equals(TelasEntidadesEnum.CONSULTAR_BL)) {
						setTelaLiberadaConsultarBL(true);
					}
					if (item.getTelasEntidadesEnum().equals(TelasEntidadesEnum.CADASTRO_IMPORTADOR)) {
						setTelaLiberadaImportador(true);
					}
					if (item.getTelasEntidadesEnum().equals(TelasEntidadesEnum.CONSULTAR_LOG)) {
						setTelaLiberadaConsultarLog(true);
					}
					if (item.getTelasEntidadesEnum().equals(TelasEntidadesEnum.CONSULTAR_CLIF_DEFERIDO)) {
						setTelaLiberadaConsultarBLClif(true);
					}
				}
			}
			if (user.isAdmin()) {
				executarPermissoesDasTelas(true);
			}
		}
	}
	
	private void executarPermissoesDasTelas(boolean valor) {
		setTelaLiberadaComissario(valor);
		setTelaLiberadaBL(valor);
		setTelaLiberadaConsultarBL(valor);
		setTelaLiberadaImportador(valor);
		setTelaLiberadaConsultarLog(valor);
		setTelaLiberadaConsultarBLClif(valor);
	}

	public boolean isTelaLiberadaBL() {
		return telaLiberadaBL;
	}

	public void setTelaLiberadaBL(boolean telaLiberadaBL) {
		this.telaLiberadaBL = telaLiberadaBL;
	}

	public boolean isTelaLiberadaConsultarBL() {
		return telaLiberadaConsultarBL;
	}

	public void setTelaLiberadaConsultarBL(boolean telaLiberadaConsultarBL) {
		this.telaLiberadaConsultarBL = telaLiberadaConsultarBL;
	}

	public boolean isTelaLiberadaImportador() {
		return telaLiberadaImportador;
	}

	public void setTelaLiberadaImportador(boolean telaLiberadaImportador) {
		this.telaLiberadaImportador = telaLiberadaImportador;
	}

	public boolean isTelaLiberadaConsultarLog() {
		return telaLiberadaConsultarLog;
	}

	public void setTelaLiberadaConsultarLog(boolean telaLiberadaConsultarLog) {
		this.telaLiberadaConsultarLog = telaLiberadaConsultarLog;
	}

	public boolean isTelaLiberadaConsultarBLClif() {
		return telaLiberadaConsultarBLClif;
	}

	public void setTelaLiberadaConsultarBLClif(boolean telaLiberadaConsultarBLClif) {
		this.telaLiberadaConsultarBLClif = telaLiberadaConsultarBLClif;
	}
}
