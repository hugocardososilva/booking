package conexao.com.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

import conexao.com.rn.CadastroComissarioRN;
import conexao.com.rn.CadastroResponsavelLoteRN;
import conexao.com.rn.UserComissarioRN;
import conexao.com.rn.UserFacade;
import conexao.com.rn.UserImportadorRN;
import conexao.com.rn.UserPermissoesRN;
import conexao.com.rn.UserResponsavelLoteRN;
import conexao.com.util.FiltroTabelaBean;
import conexao.com.util.JSFMessageUtil;
import conexao.com.util.JSFUtil;
import seguranca.com.entidade.CadastroComissario;
import seguranca.com.entidade.CadastroImportador;
import seguranca.com.entidade.CadastroResponsavelLote;
import seguranca.com.entidade.User;
import seguranca.com.entidade.UserComissario;
import seguranca.com.entidade.UserImportador;
import seguranca.com.entidade.UserPermissoes;
import seguranca.com.entidade.UserResponsavelLote;
import seguranca.com.enums.Role;
import seguranca.com.enums.TelasEntidadesEnum;
import seguranca.com.enums.TipoCryptografiaEnum;

public class UserCadastroComumBean extends CadastroIncluirGenericoBean {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	private static final String PG_FORM_USUARIO = "frmListarUsuarios";

	private boolean controlarFormListar = true;
	private boolean controlarFormCadastrarUsr = false;
	protected boolean salvarImportadorMaster;
	protected boolean salvarComissariaMaster;
	private boolean mostrarEmail = true;

	private User usuario;
	private UserComissario usrComissario;
	private UserResponsavelLote usrResponsavelLote;
	private UserImportador usrImportador;
	private CadastroComissario cadComissarioSelecionado;
	private CadastroImportador importadorSelecionado;
	private UserPermissoes usrPermissoes;
	private TelasEntidadesEnum telasEntidadeSelecionado;
	private CadastroResponsavelLote responsavelLoteSelecionado;

	private UserFacade userRN;
	private CadastroComissarioRN cadComissarioRN;
	private UserComissarioRN userComissarioRN;
	private UserPermissoesRN userPermissoesRN;
	private UserImportadorRN userImportadorRN;

	private List<User> listaUsuarios;
	private List<UserComissario> listaTodosUsuariosComissarios;
	private List<UserPermissoes> listaTodasPermissoes;

	private List<Role> todosRoleEnum;
	private List<TelasEntidadesEnum> todasTelasEntidadesEnum;

	private LazyDataModel<User> lazyModel;

	private String senhaAtual;
	private String senhaDescriptografada;

	private List<UserImportador> listaTodosImportadoresPorUsuarios;

	private CadastroResponsavelLoteRN responsavelLoteR;

	private List<CadastroResponsavelLote> listaTodosResponsavelLote;

	private List<UserResponsavelLote> listaTodasUserResponsavelLote;

	private UserResponsavelLoteRN userResponsavelLoteRN;

	/** Enum Telas */
	public List<Role> getTodosRoleEnum() {
		if (todosRoleEnum == null && isControlarFormCadastrarUsr()) {
			todosRoleEnum = new ArrayList<Role>();
			todosRoleEnum = new ArrayList<Role>(Arrays.asList(Role.values()));
		}
		return todosRoleEnum;
	}

	public List<TelasEntidadesEnum> getTodasTelasEntidadeEnum() {
		if (todasTelasEntidadesEnum == null) {
			todasTelasEntidadesEnum = new ArrayList<TelasEntidadesEnum>();
			todasTelasEntidadesEnum = new ArrayList<TelasEntidadesEnum>(Arrays.asList(TelasEntidadesEnum.values()));
		}
		return todasTelasEntidadesEnum;
	}

	public List<CadastroResponsavelLote> getListaTodosResponsavelLote() throws Exception {
		if (listaTodosResponsavelLote == null) {
			listaTodosResponsavelLote = getResponsavelLoteRN().listaTodos();
		}
		return listaTodosResponsavelLote;
	}
	
	private CadastroResponsavelLoteRN getResponsavelLoteRN() {
		if (responsavelLoteR == null) {
			responsavelLoteR = new CadastroResponsavelLoteRN();
		}
		
		return responsavelLoteR;
	}

	public List<UserComissario> getListaTodosUsuariosComissarios() {
		if (listaTodosUsuariosComissarios == null && isControlarFormCadastrarUsr()) {
			listaTodosUsuariosComissarios = getUsuario().getListaUserComissaria();
		}
		return listaTodosUsuariosComissarios;
	}

	public List<UserImportador> getListaTodosImportadoresPorUsuarios() {
		if (listaTodosImportadoresPorUsuarios == null && isControlarFormCadastrarUsr()) {
			listaTodosImportadoresPorUsuarios = getUsuario().getListaUserImportadores();
		}
		return listaTodosImportadoresPorUsuarios;
	}

	public List<UserPermissoes> getListaTodasPermissoes() {
		if (listaTodasPermissoes == null && isControlarFormCadastrarUsr()) {
			listaTodasPermissoes = getUsuario().getListaUserPermissoes();
		}
		return listaTodasPermissoes;
	}

	public List<UserResponsavelLote> getListaTodasUserResponsavelLote() {
		if (listaTodasUserResponsavelLote == null && isControlarFormCadastrarUsr()) {
			listaTodasUserResponsavelLote = getUsuario().getListaUserResponsavelLote();
		}
		return listaTodasUserResponsavelLote;
	}

	public UserPermissoesRN getUserPermissoesRN() {
		if (userPermissoesRN == null) {
			userPermissoesRN = new UserPermissoesRN();
		}
		return userPermissoesRN;
	}

	public UserComissarioRN getUserComissarioRN() {
		if (userComissarioRN == null) {
			userComissarioRN = new UserComissarioRN();
		}
		return userComissarioRN;
	}

	public CadastroComissarioRN getCadastroComissarioRN() {
		if (cadComissarioRN == null) {
			cadComissarioRN = new CadastroComissarioRN();
		}
		return cadComissarioRN;
	}

	public UserFacade getUserRN() {
		if (userRN == null) {
			userRN = new UserFacade();
		}
		return userRN;
	}

	public List<User> getListaUsuarios() {
		if (listaUsuarios == null) {
			listaUsuarios = getUserRN().listaTodos();
		}
		return listaUsuarios;
	}

	public void redirecionarCadastarUsuario() {
		try {
			listaTodosUsuariosComissarios = null;
			listaTodasPermissoes = null;
			setControlarFormCadastrarUsr(true);
			setControlarFormListar(false);
			setUsuario(new User());
			getUsuario().setName(null);
			getUsuario().setPassword(null);
			getUsuario().setCpf(null);
			getUsuario().setEmail(null);
			atualizarFormCadastroUsuario();
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	private void atualizarFormCadastroUsuario() {
		RequestContext.getCurrentInstance().update("frmCadastrarUsuario");
		RequestContext.getCurrentInstance().update("frmCadastrarUsuario:inpCPF");
		RequestContext.getCurrentInstance().update("frmCadastrarUsuario:txtSenhaAntiga");
		RequestContext.getCurrentInstance().update("frmPrincipalUsuarios:frmCadastrarUsuario:cadFormUsuario:inpNome");
		RequestContext.getCurrentInstance()
				.update("frmPrincipalUsuarios:frmCadastrarUsuario:cadFormUsuario:txtSenhaAntiga");
	}

	public void editarUsuario(User entidade) {
		try {
			listaTodosUsuariosComissarios = null;
			listaTodosImportadoresPorUsuarios = null;
			listaTodasPermissoes = null;
			listaTodasUserResponsavelLote = null;
			setResponsavelLoteSelecionado(null);
			setControlarFormCadastrarUsr(true);
			setControlarFormListar(false);
			setUsuario(entidade);
			
			setSenhaDescriptografada(JSFUtil.getCriptografarSenha(entidade.getPassword(), TipoCryptografiaEnum.DESCRIPTOGRAFAR));

			if (getUsuario().getPassword() != null) {
				setSenhaAtual(
						JSFUtil.getCriptografarSenha(getUsuario().getPassword(), TipoCryptografiaEnum.DESCRIPTOGRAFAR));
			}

			setUsrPermissoes(new UserPermissoes());
			
			
			atualizarFormCadastroUsuario();
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public void cancelarAltercao() {
		setControlarFormCadastrarUsr(false);
		setControlarFormListar(true);
		atualizarListaUsuario();
	}

	public void salvarUsuario() {
		boolean passou = true;
		try {
			if (getSenhaAtual() != null) {
				if (getSenhaAtual().equals(JSFUtil.getCriptografarSenha(getUsuario().getPassword(),
						TipoCryptografiaEnum.DESCRIPTOGRAFAR))) {
					getUsuario().setPassword(getSenhaAtual());
				}
			}

			setUsuario(getUserRN().alterar(getUsuario()));
			
			if (getUsuario().getRole()==Role.IMPORTADOR_MASTER) {
				setSalvarImportadorMaster(true);
			}
			if (getUsuario().getRole()==Role.COMISSARIA_MASTER) {
				setSalvarComissariaMaster(true);
			}
		} catch (Exception e) {
			// setUsuario( getUserRN().localizar(getUsuario().getId()) );
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
			passou = false;

		}
		if (passou) {
			setControlarFormCadastrarUsr(true);
			setControlarFormListar(false);
			atualizarListaUsuario();
			JSFMessageUtil.adicionarMensagemSucesso("Operação realizada com sucesso!");
		}
	}

	private void atualizarListaUsuario() {
		listaUsuarios = null;
		RequestContext.getCurrentInstance().update(PG_FORM_USUARIO);
	}

	public void remover(User entidade) {
		try {
			getUserRN().deletar(entidade);
			atualizarListaUsuario();
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public boolean isControlarFormListar() {
		return controlarFormListar;
	}

	public void setControlarFormListar(boolean controlarFormListar) {
		this.controlarFormListar = controlarFormListar;
	}

	public boolean isControlarFormCadastrarUsr() {
		return controlarFormCadastrarUsr;
	}

	public void setControlarFormCadastrarUsr(boolean controlarFormCadastrarUsr) {
		this.controlarFormCadastrarUsr = controlarFormCadastrarUsr;
	}

	public void incluirComissaria() {
		try {
			setUsrComissario(new UserComissario());
			if (getUsuario().getId() == 0) {
				setUsuario(getUserRN().alterar(getUsuario()));
			}

			getUsrComissario().setUsuario(getUsuario());
			getUsrComissario().setCadComissario(getCadComissarioSelecionado());

			UserComissario comissario = getUserComissarioRN().alterar(getUsrComissario());
			getUsuario().getListaUserComissaria().add(comissario);
			listaTodosUsuariosComissarios = null;

			setCadComissarioSelecionado(null);
			cadComissarioSelecionado = null;

			JSFMessageUtil.adicionarMensagemSucesso("Operação realizada com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void incluirImportador() {
		try {
			setUsrImportador(new UserImportador());
			if (getUsuario().getId() == 0) {
				setUsuario(getUserRN().alterar(getUsuario()));
			}

			getUsrImportador().setUsuario(getUsuario());
			getUsrImportador().setImportador(getImportadorSelecionado());

			UserImportador imp = getUserImportadorRN().alterar(getUsrImportador());
			getUsuario().getListaUserImportadores().add(imp);
			listaTodosImportadoresPorUsuarios = null;

			setImportadorSelecionado(null);
			importadorSelecionado = null;

			JSFMessageUtil.adicionarMensagemSucesso("Operação realizada com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void incluirNotify() {
		try {
			setUsrResponsavelLote(new UserResponsavelLote());
			if (getUsuario().getId() == 0) {
				setUsuario(getUserRN().alterar(getUsuario()));
			}
			
			getUsrResponsavelLote().setUsuario(getUsuario());
			getUsrResponsavelLote().setCadRespLote(getResponsavelLoteSelecionado());
			
			UserResponsavelLote userRespLote = getUserResponsavelLoteRN().alterar(getUsrResponsavelLote());
			
			getUsuario().getListaUserResponsavelLote().add(userRespLote);

			listaTodasUserResponsavelLote = null;
			
			setResponsavelLoteSelecionado(null);
			responsavelLoteSelecionado = null;
			
			JSFMessageUtil.adicionarMensagemSucesso("Operação realizada com sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	private UserImportadorRN getUserImportadorRN() {
		if (userImportadorRN == null) {
			userImportadorRN = new UserImportadorRN();
		}
		return userImportadorRN;
	}

	public void incluirPermissoes() {
		try {
			if (getUsrPermissoes() == null) {
				setUsrPermissoes(new UserPermissoes());
			}

			getUsrPermissoes().setUsuarioVinculado(getUsuario());
			getUsrPermissoes().setTelasEntidadesEnum(getTelasEntidadeSelecionado());

			UserPermissoes userPermissao = getUserPermissoesRN().alterar(getUsrPermissoes());
			getUsuario().getListaUserPermissoes().add(userPermissao);
			listaTodasPermissoes = null;

			setTelasEntidadeSelecionado(null);
			telasEntidadeSelecionado = null;

			JSFMessageUtil.adicionarMensagemSucesso("Operação realizada com sucesso!");
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	private void setarInformacaoPermissaoPadrao(TelasEntidadesEnum telas) throws Exception {
		setUsrPermissoes(new UserPermissoes());

		getUsrPermissoes().setUsuarioVinculado(getUsuario());
		getUsrPermissoes().setTelasEntidadesEnum(telas);

		getUserPermissoesRN().alterar(getUsrPermissoes());
	}

	public void incluirPermissaoPadrao() {
//		try {
//			for (UserPermissoes item : getUsuario().getListaUserPermissoes()) {
//				getUserPermissoesRN().deletar(item);
//				getUsuario().getListaUserPermissoes().remove(item);
//			} 
//			
//			if (getUsuario().isClif()) {
//				setarInformacaoPermissaoPadrao(TelasEntidadesEnum.CADASTRO_ATI_FCL);
//				setarInformacaoPermissaoPadrao(TelasEntidadesEnum.CADASTRO_ATI_LCL);
//				setarInformacaoPermissaoPadrao(TelasEntidadesEnum.CONSULTA_PROCESSOS);
//				setarInformacaoPermissaoPadrao(TelasEntidadesEnum.CONSULTAR_BL);
//				setarInformacaoPermissaoPadrao(TelasEntidadesEnum.CONSULTAR_CLIF_DEFERIDO);
//			}
//			if (getUsuario().isCliente()) {
//				setarInformacaoPermissaoPadrao(TelasEntidadesEnum.CADASTRO_ATI_FCL);
//				setarInformacaoPermissaoPadrao(TelasEntidadesEnum.CADASTRO_ATI_LCL);
//				setarInformacaoPermissaoPadrao(TelasEntidadesEnum.CONSULTA_PROCESSOS);
//				setarInformacaoPermissaoPadrao(TelasEntidadesEnum.CONSULTAR_BL);
//			}
//			if (getUsuario().isUser()) {
//				setarInformacaoPermissaoPadrao(TelasEntidadesEnum.CONSULTAR_BL);
//				setarInformacaoPermissaoPadrao(TelasEntidadesEnum.CONSULTAR_LOG);
//			}
//			listaTodasPermissoes = null;
//		} catch (Exception e) {
//			e.printStackTrace();
//			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
//		}
	}

	public void instanciarUserComissario() {
		setUsrComissario(new UserComissario());
	}

	public void instanciarUserPermissoes() {
		setUsrPermissoes(new UserPermissoes());
	}

	public UserComissario getUsrComissario() {
		return usrComissario;
	}

	public void setUsrComissario(UserComissario usrComissario) {
		this.usrComissario = usrComissario;
	}

	public void removerVinculoComissario(UserComissario entidade) {
		try {
			getUserComissarioRN().deletar(entidade);
			getUsuario().getListaUserComissaria().remove(entidade);
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void removerVinculoImportador(UserImportador entidade) {
		try {
			getUserImportadorRN().deletar(entidade);
			getUsuario().getListaUserImportadores().remove(entidade);
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void removerVinculoPermissoes(UserPermissoes entidade) {
		try {
			getUserPermissoesRN().deletar(entidade);
			getUsuario().getListaUserPermissoes().remove(entidade);
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}

	}
	public void removerVinculoNotify(UserResponsavelLote entidade) {
		try {
			getUserResponsavelLoteRN().deletar(entidade);
			getUsuario().getListaUserResponsavelLote().remove(entidade);
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	private UserResponsavelLoteRN getUserResponsavelLoteRN() {
		if (userResponsavelLoteRN == null) {
			userResponsavelLoteRN = new UserResponsavelLoteRN();
		}
		return userResponsavelLoteRN;
	}

	public UserPermissoes getUsrPermissoes() {
		return usrPermissoes;
	}

	public void setUsrPermissoes(UserPermissoes usrPermissoes) {
		this.usrPermissoes = usrPermissoes;
	}

	public LazyDataModel<User> getLazyModel() {
		if (lazyModel == null) {
			lazyModel = new AbstractLazyDataModel<User>(User.sql, User.sqlCount, getWhereSQL(), null);
		}

		return lazyModel;
	}

	@Override
	public void filtroTabela() {
		try {
			lazyModel = null;
			setWhereSQL(null);

			if (!getFiltroGeral().isEmpty()) {

				getFiltrosParametros().add(User.NAME);
				getFiltrosParametros().add(User.CAMPO_EMAIL);
				getFiltrosParametros().add(User.CAMPO_CPF);
				// getFiltrosParametros().add(User.CAMPO_ADM);
				getFiltrosParametros().add(User.CAMPO_ID);

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

	public CadastroComissario getCadComissarioSelecionado() {
		return cadComissarioSelecionado;
	}

	public void setCadComissarioSelecionado(CadastroComissario cadComissarioSelecionado) {
		this.cadComissarioSelecionado = cadComissarioSelecionado;
	}

	public TelasEntidadesEnum getTelasEntidadeSelecionado() {
		return telasEntidadeSelecionado;
	}

	public void setTelasEntidadeSelecionado(TelasEntidadesEnum telasEntidadeSelecionado) {
		this.telasEntidadeSelecionado = telasEntidadeSelecionado;
	}

	public CadastroImportador getImportadorSelecionado() {
		return importadorSelecionado;
	}

	public void setImportadorSelecionado(CadastroImportador importadorSelecionado) {
		this.importadorSelecionado = importadorSelecionado;
	}

	public UserImportador getUsrImportador() {
		return usrImportador;
	}

	public void setUsrImportador(UserImportador usrImportador) {
		this.usrImportador = usrImportador;
	}

	public CadastroResponsavelLote getResponsavelLoteSelecionado() {
		return responsavelLoteSelecionado;
	}

	public void setResponsavelLoteSelecionado(CadastroResponsavelLote responsavelLoteSelecionado) {
		this.responsavelLoteSelecionado = responsavelLoteSelecionado;
	}

	public UserResponsavelLote getUsrResponsavelLote() {
		return usrResponsavelLote;
	}

	public void setUsrResponsavelLote(UserResponsavelLote usrResponsavelLote) {
		this.usrResponsavelLote = usrResponsavelLote;
	}

	public String getSenhaDescriptografada() {
		return senhaDescriptografada;
	}

	public void setSenhaDescriptografada(String senhaDescriptografada) {
		this.senhaDescriptografada = senhaDescriptografada;
	}

	public boolean isSalvarImportadorMaster() {
		return salvarImportadorMaster;
	}

	public void setSalvarImportadorMaster(boolean salvarImportadorMaster) {
		this.salvarImportadorMaster = salvarImportadorMaster;
	}

	public boolean isSalvarComissariaMaster() {
		return salvarComissariaMaster;
	}

	public void setSalvarComissariaMaster(boolean salvarComissariaMaster) {
		this.salvarComissariaMaster = salvarComissariaMaster;
	}

	public boolean isMostrarEmail() {
		return mostrarEmail;
	}

	public void setMostrarEmail(boolean mostrarEmail) {
		this.mostrarEmail = mostrarEmail;
	}

	public String controlarIcone(User item) {
		String valor = "fa fa-check";
		
		User user = item;
		
		if (!user.isUserDesativado()) {
			valor = "fa fa-check";
		} else {
			valor = "fa fa-ban";
		}
		
		return valor;
	}

	public String controlarCorIcone(User item) {
		String valor = "color: #228B22";
		
		User user = item;
		
		if (!user.isUserDesativado()) {
			valor = "color: #228B22";
		} else {
			valor = "color: #FF0000";
		}
		
		return valor;
	}

	public void ativarDesativarUser(User entidade) {
		try {
			setUsuario(getUserRN().ativarDesativarUser(entidade));
			
//			User user = entidade;
//			
//			if (!user.isUserDesativado()) {
//				user.setUserDesativado(true);
//			} else {
//				user.setUserDesativado(false);
//			}
//			
//			salvar(user);
			
			JSFMessageUtil.adicionarMensagemSucesso("Operação realizada com Sucesso!");
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	

}