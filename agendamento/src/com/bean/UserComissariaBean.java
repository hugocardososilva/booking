package com.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.entidade.UserComissariaAgendamento;
import com.entidade.UserComissariaUsuarios;
import com.rn.UserComissariaAgendamentoRN;
import com.rn.UserComissariaUsuariosRN;
import com.util.JSFMessageUtil;

import conexao.com.bean.AbstractMB;
import conexao.com.rn.UserComissarioRN;
import conexao.com.rn.UserFacade;
import conexao.com.util.FiltroTabelaBean;
import conexao.com.util.IGenericBean;
import seguranca.com.entidade.CadastroComissario;
import seguranca.com.entidade.ProgramacaoNavio;
import seguranca.com.entidade.User;
import seguranca.com.entidade.UserComissario;
import seguranca.com.enums.Role;

@ViewScoped
@ManagedBean
public class UserComissariaBean extends AbstractMB implements IGenericBean {

	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;

	private boolean controlarFormListar = true;
	private boolean controlarFormCadastrar = false;
	
	private UserComissariaAgendamento entidade;
	private User entidadeUser;
	
	private List<UserComissariaAgendamento> lazyModel;
	private UserFacade userRN;

	private UserComissariaAgendamentoRN userComissariaAgendamentoRN;

	private UserComissariaUsuariosRN comissariaUsuariosRN;

	private List<UserComissariaUsuarios> listaComissariaUsuarios;

	private UserComissarioRN userComissarioRN;
	private CadastroComissario comissaria;
	
	private UserFacade getUserRN() {
		if (userRN == null) {
			userRN = new UserFacade();
		}
		return userRN;
	}
	
	public void verificaUserCadastrado() {
		String cpf = getEntidadeUser().getCpf();
		setEntidadeUser( getUserRN().localizarUsuarioCpf(getEntidadeUser().getCpf()) );
		
		if (getEntidadeUser() == null) {
			setEntidadeUser(new User());
			getEntidadeUser().setCpf(cpf);
		} else {
			incluirUsuario();
			JSFMessageUtil.adicionarMensagemSucesso("Operação realizada com Sucesso!");
		}
	}

	public void incluirUsuario() {
		try {
			if (getEntidadeUser().getId() == 0) {
				setEntidadeUser( getUserRN().incluirUsuarioDespachante(getEntidadeUser(), Role.DESPACHANTE) );
			} else {
				if (getEntidadeUser().getRole() != Role.DESPACHANTE) {
					getEntidadeUser().setRole(Role.DESPACHANTE);
					setEntidadeUser( getUserRN().salvar(getEntidadeUser()));
				}
			}
			
			UserComissariaUsuarios entidade = new UserComissariaUsuarios();
			entidade.setComissariaMaster(getEntidade());
			entidade.setUser(getEntidadeUser());
			entidade = getComissariaUsuariosRN().alterar(entidade);
			
			getEntidade().getListaComissariaUsuarios().add(entidade);
			
			setEntidadeUser(new User());
			JSFMessageUtil.adicionarMensagemSucesso("Operação realizada com Sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	private UserComissariaUsuariosRN getComissariaUsuariosRN() {
		if (comissariaUsuariosRN == null) {
			comissariaUsuariosRN = new UserComissariaUsuariosRN();
		}
		
		return comissariaUsuariosRN;
	}
	
	private UserComissariaAgendamentoRN getUserComissariaAgendamentoRN() {
		if (userComissariaAgendamentoRN == null) {
			userComissariaAgendamentoRN = new UserComissariaAgendamentoRN();
		}
		
		return userComissariaAgendamentoRN;
	}

	public List<UserComissariaAgendamento> getLazyModel() {
		if (lazyModel == null) {
			lazyModel = getUserComissariaAgendamentoRN().listaPorUsuario(getUserMB().getUser());
		}

		return lazyModel;
	}

	@Override
	public void filtroTabela() {
		try {
			lazyModel = null;
			setWhereSQL(null);

			if (!getFiltroGeral().isEmpty()) {

				getFiltrosParametros().add(ProgramacaoNavio.CAMPO_NAVIO);
				getFiltrosParametros().add(ProgramacaoNavio.CAMPO_NAVIOVIAGEM);

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

	public List<UserComissariaUsuarios> getListaComissariaUsuarios() {
		if (listaComissariaUsuarios == null) {
			if (getEntidade() != null) {
				listaComissariaUsuarios = getEntidade().getListaComissariaUsuarios();
			}
		}
		return listaComissariaUsuarios;
	}

	public void editarEntidade(UserComissariaAgendamento entidade) {
		try {
			setControlarFormCadastrar(true);
			setControlarFormListar(false);
			setEntidade(entidade);
			atualizarFormCadastroUsuario();
			
			instanciarEntidades();

			List<UserComissario> userImportador = getUserComissarioRN().listaComissariasPorUsuario(getUserMB().getUser());
			for (UserComissario item : userImportador) {
				setComissaria(item.getCadComissario());
			}

		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	private UserComissarioRN getUserComissarioRN() {
		if (userComissarioRN == null) {
			userComissarioRN = new UserComissarioRN();
		}
		return userComissarioRN;
	}

	private void atualizarFormCadastroUsuario() {
		RequestContext.getCurrentInstance().update(PG_FORM_CADASTRAR_ENTIDADE);
	}

	public void salvarEntidade() {
		try {
			setControlarFormCadastrar(false);
			setControlarFormListar(true);

			atualizarFormCadastroUsuario();
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void voltarPgPrincipal() {
		setControlarFormCadastrar(false);
		setControlarFormListar(true);
		atualizarFormCadastroUsuario();
	}

	public void redirecionarCadastro() {
		try {
			setControlarFormCadastrar(true);
			setControlarFormListar(false);
			
			instanciarEntidades();
			
			atualizarFormCadastroUsuario();
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	private void instanciarEntidades() {
		setEntidadeUser(new User());
		listaComissariaUsuarios = null;
		
	}

	public User getEntidadeUser() {
		return entidadeUser;
	}

	public void setEntidadeUser(User entidadeUser) {
		this.entidadeUser = entidadeUser;
	}

	public UserMB getUserMB() {
		return userMB;
	}

	public void setUserMB(UserMB userMB) {
		this.userMB = userMB;
	}

	public UserComissariaAgendamento getEntidade() {
		return entidade;
	}

	public void setEntidade(UserComissariaAgendamento entidade) {
		this.entidade = entidade;
	}

	public String controlarIcone(UserComissariaUsuarios item) {
		String valor = "fa fa-check";
		
		User user = item.getUser();
		
		if (!user.isUserDesativado()) {
			valor = "fa fa-check";
		} else {
			valor = "fa fa-ban";
		}
		
		return valor;
	}

	public String controlarCorIcone(UserComissariaUsuarios item) {
		String valor = "color: #228B22";
		
		User user = item.getUser();
		
		if (!user.isUserDesativado()) {
			valor = "color: #228B22";
		} else {
			valor = "color: #FF0000";
		}
		
		return valor;
	}

	public void ativarDesativarUser(UserComissariaUsuarios entidade) {
		try {
			User user = entidade.getUser();
			
			if (!user.isUserDesativado()) {
				user.setUserDesativado(true);
			} else {
				user.setUserDesativado(false);
			}
			
			getUserRN().salvar(user);
			
			JSFMessageUtil.adicionarMensagemSucesso("Opera��o realizada com Sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public CadastroComissario getComissaria() {
		return comissaria;
	}

	public void setComissaria(CadastroComissario comissaria) {
		this.comissaria = comissaria;
	}
	
}
