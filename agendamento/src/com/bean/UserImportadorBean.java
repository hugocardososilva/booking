package com.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.entidade.UserImportadorAgendamento;
import com.entidade.UserImportadorComissaria;
import com.entidade.UserImportadorUsuarios;
import com.rn.UserImportadorAgendamentoRN;
import com.rn.UserImportadorComissariaRN;
import com.rn.UserImportadorUsuariosRN;
import com.util.JSFMessageUtil;

import conexao.com.bean.CadastroIncluirGenericoBean;
import conexao.com.rn.CadastroComissarioRN;
import conexao.com.rn.CadastroResponsavelLoteRN;
import conexao.com.rn.UserFacade;
import conexao.com.rn.UserImportadorRN;
import conexao.com.util.FiltroTabelaBean;
import seguranca.com.entidade.CadastroComissario;
import seguranca.com.entidade.CadastroImportador;
import seguranca.com.entidade.CadastroResponsavelLote;
import seguranca.com.entidade.ProgramacaoNavio;
import seguranca.com.entidade.User;
import seguranca.com.entidade.UserImportador;
import seguranca.com.enums.Role;
import seguranca.com.enums.TipoPessoaFisicaJuridicaEnum;

@ViewScoped
@ManagedBean
public class UserImportadorBean extends CadastroIncluirGenericoBean {

	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;

	private boolean controlarFormListar = true;
	private boolean controlarFormCadastrar = false;
	private boolean controlarMascaraCnpjCpf;
	
	private String mascaraCnpjCpfInput;
	private String cnpjDespachante;
	private String cnpjNotify;
	private String controleIcon;
	
	private User user;
	private UserImportadorAgendamento entidade;
	private CadastroImportador importadorSelecionado;
	private CadastroComissario comissariaSelecionado;
	private CadastroResponsavelLote notifySelecionado;
		
	private UserImportadorAgendamentoRN userImportadorAgendamentoRN;
	private UserFacade userRN;
	private UserImportadorComissariaRN userImportadorComissariaRN;
	private CadastroComissarioRN comissariaRN;
	private CadastroResponsavelLoteRN notifyRN;

	private List<UserImportadorAgendamento> lazyModel;
	private List<UserImportadorComissaria> listaImportadorComissaria;
	private List<UserImportadorUsuarios> listaImportadorUsuarios;
	private List<CadastroImportador> listaImportador;
	private List<CadastroComissario> listaComissaria;
	private List<CadastroResponsavelLote> listaNotify;

	private UserImportadorRN userImportadorRN;

	private UserImportadorUsuariosRN importadorUsuariosRN;

	private CadastroImportador importador;

	private UserFacade getUserRN() {
		if (userRN == null) {
			userRN = new UserFacade();
		}
		return userRN;
	}
	
	private UserImportadorComissariaRN getUserImportadorComissariaRN() {
		if (userImportadorComissariaRN == null) {
			userImportadorComissariaRN = new UserImportadorComissariaRN();
		}
		return userImportadorComissariaRN;
	}
	
	private CadastroComissarioRN getComissariaRN() {
		if (comissariaRN == null) {
			comissariaRN = new CadastroComissarioRN();
		}
		return comissariaRN;
	}
	
	private CadastroResponsavelLoteRN getNotifyRN() {
		if (notifyRN == null) {
			notifyRN = new CadastroResponsavelLoteRN();
		}
		return notifyRN;
	}
	
	public List<CadastroComissario> getListaComissaria() {
		try {
			if (listaComissaria == null) {
				listaComissaria = getComissariaRN().listaTodos();
			}
		} catch (Exception e) {
			e.printStackTrace();
			conexao.com.util.JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
		return listaComissaria;
	}
	
	public List<CadastroResponsavelLote> getListaNotify() {
		if (listaNotify == null) {
			listaNotify = getNotifyRN().listaTodos();
		}
		return listaNotify;
	}
	
	public UserImportadorRN getUserImportadorRN() {
		if (userImportadorRN == null) {
			userImportadorRN = new UserImportadorRN();
		}

		return userImportadorRN;
	}

	public List<CadastroImportador> getListaImportador() {
		if (listaImportador == null) {
			List<UserImportador> userImportador = getUserImportadorRN().listaComissariasPorUsuario(getUserMB().getUser());
			List<CadastroImportador> listaImportadorNovo = new ArrayList<CadastroImportador>();
			for (UserImportador item : userImportador) {
				listaImportadorNovo.add(item.getImportador());
			}
			listaImportador = listaImportadorNovo;
		}
		return listaImportador;
	}
	
	public void exluirImportadorComissaria(UserImportadorComissaria entidade) {
		try {
			if (entidade != null) {
				getUserImportadorComissariaRN().deletar(entidade);
				
				getEntidade().getListaImportadorComissaria().remove(entidade);
			}
			
		} catch (Exception e) {
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
	
	public void ativarDesativarUser(UserImportadorUsuarios entidade) {
		try {
			User user = entidade.getUser();
			
			if (!user.isUserDesativado()) {
				user.setUserDesativado(true);
			} else {
				user.setUserDesativado(false);
			}
			
			getUserRN().salvar(user);
			
			JSFMessageUtil.adicionarMensagemSucesso("Operação realizada com Sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public String controlarIcone(UserImportadorUsuarios item) {
		String valor = "fa fa-check";
		
		User user = item.getUser();
		
		if (!user.isUserDesativado()) {
			valor = "fa fa-check";
		} else {
			valor = "fa fa-ban";
		}
		
		return valor;
	}

	public String controlarCorIcone(UserImportadorUsuarios item) {
		String valor = "color: #228B22";
		
		User user = item.getUser();
		
		if (!user.isUserDesativado()) {
			valor = "color: #228B22";
		} else {
			valor = "color: #FF0000";
		}
		
		return valor;
	}
	
	public void verificaUserCadastrado() {
		String cpf = getUser().getCpf();
		setUser( getUserRN().localizarUsuarioCpf(getUser().getCpf()) );
		
		if (getUser() == null) {
			setUser(new User());
			getUser().setCpf(cpf);
		} else {
			incluirUsuarios();
			JSFMessageUtil.adicionarMensagemSucesso("Operação realizada com Sucesso!");
		}
	}
	
	public void incluirUsuarios() {
		try {
			
			if (getUser().getId() == 0) {
				setUser( getUserRN().incluirUsuarioDespachante(getUser(), Role.CLIENTE) );
			} else {
				if (getUser().getRole() != Role.CLIENTE) {
					getUser().setRole(Role.CLIENTE);
					setUser( getUserRN().salvar(getUser()));
				}
			}
			
			UserImportadorUsuarios userImportador = new UserImportadorUsuarios();
			userImportador.setImportadorComissaria(getEntidade());
			userImportador.setUser(getUser());
			userImportador = getImportadorUsuariosRN().alterar(userImportador);
			
			getEntidade().getListaImportadorUsuarios().add(userImportador);
			
			setUser(new User());
			JSFMessageUtil.adicionarMensagemSucesso("Operação realizada com Sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void incluirImportadorComissaria() {
		try {
			if (!getCnpjDespachante().isEmpty()) {
				CadastroComissario comissario = getComissariaRN().pesquisaPorCNPJ(getCnpjDespachante());
				
				if (comissario == null) {
					
					RequestContext.getCurrentInstance().execute("PF('dlgBLIncluir').show()");

					setCnpj(getCnpjDespachante());
					setSelecionadoPessoaFisicaJuridicaEnum(TipoPessoaFisicaJuridicaEnum.PESSOA_JURIDICA);
					instanciarImportadorComissaria(2);

					RequestContext.getCurrentInstance().update("incluir:frmBLIncluir:inpCnpj");
					throw new Exception("Comissaria não localizado, favor cadastra-lo !");
				} else {
					setComissariaSelecionado( comissario );
				}
			}
			if (!getCnpjNotify().isEmpty()) {
				CadastroResponsavelLote notify = getNotifyRN().pesquisaPorCNPJ(getCnpjNotify() );
				if (notify == null) {
					setCnpj(getCnpjNotify());
					setSelecionadoPessoaFisicaJuridicaEnum(TipoPessoaFisicaJuridicaEnum.PESSOA_JURIDICA);
					instanciarImportadorComissaria(4);
					
					RequestContext.getCurrentInstance().execute("PF('dlgBLIncluir').show()");
					RequestContext.getCurrentInstance().update("incluir:frmBLIncluir:pnlIncluir");
					
					throw new Exception("Notify nao localizado, favor cadastra-lo !");
				} else {
					setNotifySelecionado( notify );
				}
			}
			
			UserImportadorComissaria item = new UserImportadorComissaria();
			item.setComissaria(getComissariaSelecionado());
			item.setImportador(getImportador());
			item.setNotifyRespLote(getNotifySelecionado());
			item.setImportadorComissaria(getEntidade());
			
			item = getUserImportadorComissariaRN().alterar(item);
			
			getEntidade().getListaImportadorComissaria().add(item);
			
			setarInformacao();
		} catch (Exception e) {
			setNotifySelecionado( null );
			setComissariaSelecionado( null );
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public List<UserImportadorAgendamento> getLazyModel() {
		if (lazyModel == null) {
			lazyModel = getUserImportadorAgendamentoRN().listaPorUsuario(getUserMB().getUser());
		}

		return lazyModel;
	}

	public List<UserImportadorUsuarios> getListaImportadorUsuarios() {
		if (listaImportadorUsuarios == null) {
			if (getEntidade() != null) {
				listaImportadorUsuarios = getEntidade().getListaImportadorUsuarios();
			}
		}
		return listaImportadorUsuarios;
	}
	
	public List<UserImportadorComissaria> getListaImportadorComissaria() {
		listaImportadorComissaria = null;
		if (getEntidade() != null) {
			listaImportadorComissaria = getEntidade().getListaImportadorComissaria();
		}
		return listaImportadorComissaria;
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
	
	public void alterarMasCaraInput() {
		if (isControlarMascaraCnpjCpf()) {
			setMascaraCnpjCpfInput("999.999.999-99");
			setControlarMascaraCnpjCpf(false);
			setControleIcon("fa fa-arrow-right");
			
		} else {
			setMascaraCnpjCpfInput("99.999.999/9999-99");
			setControlarMascaraCnpjCpf(true);
			setControleIcon("fa fa-arrow-left");
		}
	}

	
	public void editarEntidade(UserImportadorAgendamento entidade) {
		try {
			setMascaraCnpjCpfInput("99.999.999/9999-99");
			setControlarMascaraCnpjCpf(true);
			setControleIcon("fa fa-arrow-left");
			

			setControlarFormCadastrar(true);
			setControlarFormListar(false);
			setEntidade(entidade);
			
			listaImportadorComissaria = null;
			listaImportadorUsuarios = null;
			setarInformacao();
			
			atualizarFormCadastroUsuario();
			setUser(new User());

			List<UserImportador> userImportador = getUserImportadorRN().listaComissariasPorUsuario(getUserMB().getUser());
			for (UserImportador item : userImportador) {
				setImportador(item.getImportador());
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	private void setarInformacao() {
		setComissariaSelecionado(null);
		setImportadorSelecionado(null);
		setNotifySelecionado(null);
		setCnpjDespachante(null);
		setCnpjNotify(null);
	}

	private void atualizarFormCadastroUsuario() {
		RequestContext.getCurrentInstance().update(PG_FORM_CADASTRAR_ENTIDADE);
	}

	public UserImportadorAgendamento getEntidade() {
		return entidade;
	}

	public void setEntidade(UserImportadorAgendamento entidade) {
		this.entidade = entidade;
	}
	
	private UserImportadorAgendamentoRN getUserImportadorAgendamentoRN() {
		if (userImportadorAgendamentoRN == null) {
			userImportadorAgendamentoRN = new UserImportadorAgendamentoRN();
		}
		
		return userImportadorAgendamentoRN;
	}

	public void salvarEntidade() {
		try {
			setControlarFormCadastrar(false);
			setControlarFormListar(true);
			getUserImportadorAgendamentoRN().alterar(getEntidade());
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
			atualizarFormCadastroUsuario();
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public UserMB getUserMB() {
		return userMB;
	}

	public void setUserMB(UserMB userMB) {
		this.userMB = userMB;
	}

	public CadastroImportador getImportadorSelecionado() {
		return importadorSelecionado;
	}

	public void setImportadorSelecionado(CadastroImportador importadorSelecionado) {
		this.importadorSelecionado = importadorSelecionado;
	}

	public CadastroComissario getComissariaSelecionado() {
		return comissariaSelecionado;
	}

	public void setComissariaSelecionado(CadastroComissario comissariaSelecionado) {
		this.comissariaSelecionado = comissariaSelecionado;
	}

	public CadastroResponsavelLote getNotifySelecionado() {
		return notifySelecionado;
	}

	public void setNotifySelecionado(CadastroResponsavelLote notifySelecionado) {
		this.notifySelecionado = notifySelecionado;
	}

	public String getCnpjDespachante() {
		return cnpjDespachante;
	}

	public void setCnpjDespachante(String cnpjDespachante) {
		this.cnpjDespachante = cnpjDespachante;
	}

	public String getCnpjNotify() {
		return cnpjNotify;
	}

	public void setCnpjNotify(String cnpjNotify) {
		this.cnpjNotify = cnpjNotify;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CadastroImportador getImportador() {
		return importador;
	}

	public void setImportador(CadastroImportador importador) {
		this.importador = importador;
	}

	public boolean isControlarMascaraCnpjCpf() {
		return controlarMascaraCnpjCpf;
	}

	public void setControlarMascaraCnpjCpf(boolean controlarMascaraCnpjCpf) {
		this.controlarMascaraCnpjCpf = controlarMascaraCnpjCpf;
	}

	public String getControleIcon() {
		return controleIcon;
	}

	public void setControleIcon(String controleIcon) {
		this.controleIcon = controleIcon;
	}

	public String getMascaraCnpjCpfInput() {
		return mascaraCnpjCpfInput;
	}

	public void setMascaraCnpjCpfInput(String mascaraCnpjCpfInput) {
		this.mascaraCnpjCpfInput = mascaraCnpjCpfInput;
	}

	
}
