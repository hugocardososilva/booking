package com.bean;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.util.JSFMessageUtil;

import conexao.com.bean.AbstractMB;
import conexao.com.rn.UserFacade;
import conexao.com.rn.UserLoginRN;
import conexao.com.util.JSFUtil;
import conexao.com.util.LoginController;
import seguranca.com.entidade.User;
import seguranca.com.enums.TipoLoginEnum;

@RequestScoped
@ManagedBean
public class LoginMB extends AbstractMB {
	public static final String PAGINA_PRINCIPAL = JSFUtil.getCaminhoAplicacaoPath()+"/faces/pages/privado/principal.xhtml";
	public static final String PAGINA_LOGIN = JSFUtil.getCaminhoAplicacaoPath()+"/";

	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;
	private User user;

	private UserFacade userRN;

	private String email;
	private String password;
	private String novaSenha;
	private String confirmarNovaSenha;
	private LoginController loginController;
	private JSFUtil jSFUtil;
	private UserLoginRN userLoginRN;

	public UserFacade getUserRN() {
		if (userRN == null) {
			userRN = new UserFacade();
		}
		return userRN;
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

	public String timeout() throws IOException  {
		JSFUtil jsfUTIL = new JSFUtil();
		
		try {
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}

		return jsfUTIL.getPropertyIniConfiguracao("prop.sessao.timeout");
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

	public void baixarManual() {
		try {
			String caminho = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/resources/documentos/Manual de Utilização Portal do MAPA.pdf");

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

	public void login() {
		try {
			UserFacade userFacade = new UserFacade();

			user = userFacade.validarLogin(email.toLowerCase(), password);

			if (user != null && (user.isAcessoMapa() || user.isAdmin()) ) {
				userMB.setUser(user);
				userMB.verificarPermissoesTelas();

				if (loginController == null) {
					loginController = new LoginController();

					loginController.setarInformacaoUsuarioLogado(user);
				}
				
				getUserLoginRN().alterar(user, TipoLoginEnum.LOGIN);

				redirecionarPaginas(PAGINA_PRINCIPAL);
			} else {
				JSFMessageUtil.adicionarMensagemErro("Usuário ou senha inválidos.");
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

					redirecionarPaginas(JSFUtil.getCaminhoAplicacaoPath()+"/faces/pages/privado/alterarSenha.xhtml");
				}
			}
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}
}