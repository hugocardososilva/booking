package com.bean;

import java.io.Serializable;
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
@ManagedBean(name = "userMB")
public class UserMB extends AbstractMB implements IGenericBean, Serializable {
	public static final String INJECTION_NAME = "#{userMB}";

	private String nomeTela;
	private User user;
	private UserFacade rn;

	private boolean telaLiberadaFCL;
	private boolean telaLiberadaLCL;
	private boolean telaLiberadaProgramacaoNavio;
	private boolean telaLiberadaConsultaProcesso;
	private boolean telaLiberadaUserImportadorNotify;
	private boolean telaLiberadaDespachanteUsuarios;
	private boolean telaLiberadaServicos;
	private boolean telaLiberadaSolicitacoes;
	private boolean telaLiberadaJanelaAtendimento;
	private boolean usuarioMaster;
	private boolean telaJanelaAtendimento;
	private boolean telaServicos;
	private boolean telaSolicitacaoServicos;

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
	

	public boolean isTelaJanelaAtendimento() {
		return telaJanelaAtendimento;
	}

	public void setTelaJanelaAtendimento(boolean telaJanelaAtendimento) {
		this.telaJanelaAtendimento = telaJanelaAtendimento;
	}

	public boolean isTelaServicos() {
		return telaServicos;
	}

	public void setTelaServicos(boolean telaServicos) {
		this.telaServicos = telaServicos;
	}

	public boolean isTelaSolicitacaoServicos() {
		return telaSolicitacaoServicos;
	}

	public void setTelaSolicitacaoServicos(boolean telaSolicitacaoServicos) {
		this.telaSolicitacaoServicos = telaSolicitacaoServicos;
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

	public void verificarPermissoesTelas() {
		if (user != null) {
			executarPermissoesDasTelas(false);
			if (user.isAdmin()) {
				executarPermissoesDasTelas(true);
			} else {
				for (UserPermissoes item : user.getListaUserPermissoes()) {
					if (item.getTelasEntidadesEnum().equals(TelasEntidadesEnum.CADASTRO_ATI_FCL)) {
						setTelaLiberadaFCL(true);
					}
					if (item.getTelasEntidadesEnum().equals(TelasEntidadesEnum.CADASTRO_ATI_LCL)) {
						setTelaLiberadaLCL(true);
					}
					if (item.getTelasEntidadesEnum().equals(TelasEntidadesEnum.CONSULTA_PROCESSOS)) {
						setTelaLiberadaConsultaProcesso(true);
					}
					if (item.getTelasEntidadesEnum().equals(TelasEntidadesEnum.PROGRAMACAO_NAVIO)) {
						setTelaLiberadaProgramacaoNavio(true);
					}
					if (item.getTelasEntidadesEnum().equals(TelasEntidadesEnum.IMPORTADOR_NOTIFY_DESPACHANTE)) {
						setTelaLiberadaUserImportadorNotify(true);
					}
					if (item.getTelasEntidadesEnum().equals(TelasEntidadesEnum.DESPACHANTE_USUARIOS)) {
						setTelaLiberadaDespachanteUsuarios(true);
					}
					if(item.getTelasEntidadesEnum().equals(TelasEntidadesEnum.SERVICOS)) {

						setTelaLiberadaServicos(true);
					}
					if(item.getTelasEntidadesEnum().equals(TelasEntidadesEnum.SOLICITACOES)) {
						setTelaLiberadaSolicitacoes(true);
					}
					if(item.getTelasEntidadesEnum().equals(TelasEntidadesEnum.JANELA_ATENDIMENTO)) {
						setTelaLiberadaJanelaAtendimento(true);
					}

				}

			}
		}
	}

	private void executarPermissoesDasTelas(boolean valor) {
		setTelaLiberadaFCL(valor);
		setTelaLiberadaLCL(valor);
		setTelaLiberadaConsultaProcesso(valor);
		setTelaLiberadaProgramacaoNavio(valor);
		setTelaLiberadaUserImportadorNotify(valor);
		setTelaLiberadaDespachanteUsuarios(valor);
		setTelaLiberadaServicos(valor);
		setTelaLiberadaSolicitacoes(valor);
		setTelaLiberadaJanelaAtendimento(valor);
	}
	
	public boolean isTelaLiberadaServicos() {
		return telaLiberadaServicos;
	}

	public void setTelaLiberadaServicos(boolean telaLiberadaServicos) {
		this.telaLiberadaServicos = telaLiberadaServicos;
	}

	public boolean isTelaLiberadaSolicitacoes() {
		return telaLiberadaSolicitacoes;
	}

	public void setTelaLiberadaSolicitacoes(boolean telaLiberadaSolicitacoes) {
		this.telaLiberadaSolicitacoes = telaLiberadaSolicitacoes;
	}

	public boolean isTelaLiberadaJanelaAtendimento() {
		return telaLiberadaJanelaAtendimento;
	}

	public void setTelaLiberadaJanelaAtendimento(boolean telaLiberadaJanelaAtendimento) {
		this.telaLiberadaJanelaAtendimento = telaLiberadaJanelaAtendimento;

	}

	public boolean isTelaLiberadaFCL() {
		return telaLiberadaFCL;
	}

	public void setTelaLiberadaFCL(boolean telaLiberadaFCL) {
		this.telaLiberadaFCL = telaLiberadaFCL;
	}

	public boolean isTelaLiberadaLCL() {
		return telaLiberadaLCL;
	}

	public void setTelaLiberadaLCL(boolean telaLiberadaLCL) {
		this.telaLiberadaLCL = telaLiberadaLCL;
	}

	public boolean isTelaLiberadaConsultaProcesso() {
		return telaLiberadaConsultaProcesso;
	}

	public void setTelaLiberadaConsultaProcesso(boolean telaLiberadaConsultaProcesso) {
		this.telaLiberadaConsultaProcesso = telaLiberadaConsultaProcesso;
	}

	public boolean isTelaLiberadaProgramacaoNavio() {
		return telaLiberadaProgramacaoNavio;
	}

	public void setTelaLiberadaProgramacaoNavio(boolean telaLiberadaProgramacaoNavio) {
		this.telaLiberadaProgramacaoNavio = telaLiberadaProgramacaoNavio;
	}

	public boolean isTelaLiberadaUserImportadorNotify() {
		return telaLiberadaUserImportadorNotify;
	}

	public void setTelaLiberadaUserImportadorNotify(boolean telaLiberadaUserImportadorNotify) {
		this.telaLiberadaUserImportadorNotify = telaLiberadaUserImportadorNotify;
	}

	public boolean isTelaLiberadaDespachanteUsuarios() {
		return telaLiberadaDespachanteUsuarios;
	}

	public void setTelaLiberadaDespachanteUsuarios(boolean telaLiberadaDespachanteUsuarios) {
		this.telaLiberadaDespachanteUsuarios = telaLiberadaDespachanteUsuarios;
	}

	public boolean isUsuarioMaster() {
		return usuarioMaster;
	}

	public void setUsuarioMaster(boolean usuarioMaster) {
		this.usuarioMaster = usuarioMaster;
	}
}
