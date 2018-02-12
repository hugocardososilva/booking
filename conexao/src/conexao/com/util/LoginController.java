package conexao.com.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import seguranca.com.entidade.User;

public class LoginController {

	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	public HttpSession getSession() {
		HttpSession httpSession = null;
		if (getFacesContext() != null) {
			httpSession = (HttpSession) getFacesContext().getExternalContext().getSession(false);
		}
		return httpSession;
	}

	public void setarInformacaoUsuarioLogado(User entidde) {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();

		getSession().setAttribute("usuarioSessao", entidde);
		getSession().setAttribute("usuarioSessaoNome", entidde.getName());
		getSession().setAttribute("usuarioIPSessao", request.getRemoteAddr());
	}
	
	public User getRetornaUsuarioLogado() {
		User user = null;
		if (getSession() != null) {
			user = (User) getSession().getAttribute("usuarioSessao");
			
		}
		return user;
	}

	public String getRetornaIPUsuarioLogado() {
		String valor = null;
		if (getSession() != null) {
			valor = (String) getSession().getAttribute("usuarioIPSessao");
		}
		return valor;
	}

}
