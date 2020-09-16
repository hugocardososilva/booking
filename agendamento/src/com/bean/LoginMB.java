package com.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.entidade.UserComissariaUsuarios;
import com.entidade.UserImportadorUsuarios;
import com.rn.UserComissariaUsuariosRN;
import com.rn.UserImportadorUsuariosRN;
import com.util.JSFMessageUtil;

import clif.chat.app.frame.ClienteFrame;
import clif.chat.app.service.ServidorService;
import conexao.com.bean.AbstractMB;
import conexao.com.rn.UserFacade;
import conexao.com.rn.UserLoginRN;
import conexao.com.util.JSFUtil;
import conexao.com.util.LoginController;
import seguranca.com.entidade.User;
import seguranca.com.entidade.UserPermissoes;
import seguranca.com.enums.TelasEntidadesEnum;
import seguranca.com.enums.TipoLoginEnum;

@RequestScoped
@ManagedBean
public class LoginMB extends AbstractMB implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String PAGINA_PRINCIPAL = JSFUtil.getCaminhoAplicacaoPath()
			+ "/faces/pages/privado/principal.xhtml";
	public static final String PAGINA_LOGIN = JSFUtil.getCaminhoAplicacaoPath() + "/";

	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;
	private User user;
	private LoginController loginController;

	private List<TelasEntidadesEnum> todasTelasEnum;

	private UserFacade userRN;

	private String email;
	private String password;
	private String novaSenha;
	private String confirmarNovaSenha;
	private JSFUtil jSFUtil;
	private UserLoginRN userLoginRN;
	private UserImportadorUsuariosRN importadorUsuariosRN;
	private UserComissariaUsuariosRN comissariaUsuariosRN;

	public UserFacade getUserRN() {
		if (userRN == null) {
			userRN = new UserFacade();
		}
		return userRN;
	}

	public List<TelasEntidadesEnum> getTodasTelasEnum() {
		if (todasTelasEnum == null) {
			todasTelasEnum = new ArrayList<TelasEntidadesEnum>();
			for (TelasEntidadesEnum item : Arrays.asList(TelasEntidadesEnum.values())) {
				if (item.isAgendamento()) {
					todasTelasEnum.add(item);
				}
			}
		}

		return todasTelasEnum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void efetuarLogout() {
		try {
			getUserLoginRN().alterar(userMB.getUser(), TipoLoginEnum.LOGAUT);

			redirecionarPaginas(PAGINA_LOGIN);
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public String timeout() throws IOException  {
		JSFUtil jsfUTIL = new JSFUtil();
		
		try {
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}

		return jsfUTIL.getPropertyIniConfiguracao("prop.sessao.timeout");
	}

	private UserImportadorUsuariosRN getImportadorUsuariosRN() {
		if (importadorUsuariosRN == null) {
			importadorUsuariosRN = new UserImportadorUsuariosRN();
		}
		return importadorUsuariosRN;
	}

	private UserComissariaUsuariosRN getComissariaUsuariosRN() {
		if (comissariaUsuariosRN == null) {
			comissariaUsuariosRN = new UserComissariaUsuariosRN();
		}
		return comissariaUsuariosRN;
	}

	public void login() {
		try {
			UserFacade userFacade = new UserFacade();

			user = userFacade.validarLogin(email.toLowerCase(), password);

			if (user != null && !user.isAcessoMapa() && !user.isUserDesativado()) {
				userMB.setUser(user);
				userMB.verificarPermissoesTelas();

				userMB.setUsuarioMaster(false);
				if (user.isComissariaMaster() || user.isImportadorMaster()) {
					userMB.setUsuarioMaster(true);
				}
				if (user.isDespachante()) {
					UserComissariaUsuarios userComissaria = getComissariaUsuariosRN().retornaUsuarioComissaria(user);
					if (userComissaria != null) {
						userMB.setUsuarioMaster(true);
					}
				}
				if (user.isCliente()) {
					UserImportadorUsuarios userImportador = getImportadorUsuariosRN()
							.retornaImportadorUsuarioMaster(user);
					if (userImportador != null) {
						userMB.setUsuarioMaster(true);
					}
				}

				if (loginController == null) {
					loginController = new LoginController();

					loginController.setarInformacaoUsuarioLogado(user);
				}

				getUserLoginRN().alterar(user, TipoLoginEnum.LOGIN);
				FacesContext context= FacesContext.getCurrentInstance();
				HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
				request.getSession().setAttribute("user", user);

				redirecionarPaginas(PAGINA_PRINCIPAL);
			} else {
				JSFMessageUtil.adicionarMensagemErro("Usuario ou senha invalidos.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private UserLoginRN getUserLoginRN() {
		if (userLoginRN == null) {
			userLoginRN = new UserLoginRN();
		}
		return userLoginRN;
	}

	public void paginaPrincipal() {
		try {
			redirecionarPaginas(PAGINA_PRINCIPAL);
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void salvarSenha() {
		try {
			if (!novaSenha.equals(confirmarNovaSenha)) {
				throw new Exception("As duas senhas devem ser iguais! ");
			}

			UserFacade userFacade = new UserFacade();
			userMB.getUser().setNovaSenha(novaSenha);

			userFacade.alterarSenha(userMB.getUser());

			redirecionarPaginas(PAGINA_LOGIN);
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

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmarNovaSenha() {
		return confirmarNovaSenha;
	}

	public void setConfirmarNovaSenha(String confirmarNovaSenha) {
		this.confirmarNovaSenha = confirmarNovaSenha;
	}

	public void validarUsuario() {
		try {
			if (email != null) {
				if (getUserRN().validarUsuarioCpfString(email) == false && !email.equals("___.___.___-__")) {
					user = getUserRN().localizarUsuarioCpf(email.toLowerCase());

					if (user != null) {
						userMB.setUser(user);
					}

					redirecionarPaginas(JSFUtil.getCaminhoAplicacaoPath() + "/faces/pages/privado/alterarSenha.xhtml");
				}
			}
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public String redirecionar(TelasEntidadesEnum item) {
		String caminho = "";
		boolean liberarAcesso = false;

		User user = userMB.getUser();

		if (user.isAdmin()) {
			liberarAcesso = true;
		} else {
			for (UserPermissoes itens : user.getListaUserPermissoes()) {
				if (itens.getTelasEntidadesEnum().equals(item)) {
					liberarAcesso = true;
				}
			}
		}

		if (liberarAcesso) {
			caminho = item.getCaminhoTela();
		} else {
			JSFMessageUtil.adicionarMensagemErro("Voce nao tem permissao, para acessar esta Tela!");
			caminho = PAGINA_LOGIN;
		}

		return caminho;
	}
	
	public void iniciarChat() {
//		new ServidorService();

		new ClienteFrame().setVisible(true);
		System.out.println("teste");

	}

	public void baixarManual() {
		try {
			String caminho = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/resources/documentos/Manual de Utilização Agendamento.pdf");

			getJSFUtil().baixarDocumentos(caminho);
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	private JSFUtil getJSFUtil() {
		if (jSFUtil == null) {
			jSFUtil = new JSFUtil();
		}

		return jSFUtil;
	}

}