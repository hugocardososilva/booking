package conexao.com.util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class JSFMessageUtil {
	public void sendInfoMessageToUser(String message) {
		FacesMessage facesMessage = createMessage(FacesMessage.SEVERITY_INFO, message);
		addMessageToJsfContext(facesMessage);
	}

	public void sendErrorMessageToUser(String message) {
		FacesMessage facesMessage = createMessage(FacesMessage.SEVERITY_WARN, message);
		addMessageToJsfContext(facesMessage);
	}

	private FacesMessage createMessage(Severity severity, String mensagemErro) {
		return new FacesMessage(severity, mensagemErro, mensagemErro);
	}

	private void addMessageToJsfContext(FacesMessage facesMessage) {
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	public static void adicionarMensagemErro(String mensagem) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, mensagem);
		FacesContext contexto = FacesContext.getCurrentInstance();
		contexto.addMessage(null, msg);
	}

	public static void adicionarMensagemSucesso(String mensagem) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, mensagem);
		FacesContext contexto = FacesContext.getCurrentInstance();
		contexto.addMessage(null, msg);
	}
}