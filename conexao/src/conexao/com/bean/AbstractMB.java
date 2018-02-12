package conexao.com.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

import conexao.com.util.FiltroTabelaBean;
import conexao.com.util.JSFMessageUtil;

public class AbstractMB {
	private static final String KEEP_DIALOG_OPENED = "KEEP_DIALOG_OPENED";
	
	protected  static final String PG_FORM_PRINCIPAL = "frmListarPrincipal";
	protected  static final String PG_FORM_CADASTRAR_ENTIDADE = "frmCadastrarEntidade";
	
	protected String whereSQL;
	protected String filtroGeral;
	protected List<String> filtrosParametros = new ArrayList<String>();
	protected FiltroTabelaBean filtroTabelaBean;
	protected String nomeTela;

	public AbstractMB() {
		super();
	}
	
	public void redirecionarPaginas(String caminho) throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(caminho);
	}

	protected void displayErrorMessageToUser(String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
	}

	protected void displayWarnMessageToUser (String message) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, null));
	}

	protected void displayInfoMessageToUser(String message) {
		JSFMessageUtil messageUtil = new JSFMessageUtil();
		messageUtil.sendInfoMessageToUser(message);
	}

	protected void closeDialog() {
		getRequestContext().addCallbackParam(KEEP_DIALOG_OPENED, false);
	}

	protected void keepDialogOpen() {
		getRequestContext().addCallbackParam(KEEP_DIALOG_OPENED, true);
	}

	protected RequestContext getRequestContext() {
		return RequestContext.getCurrentInstance();
	}

	protected String getWhereSQL() {
		return whereSQL;
	}

	protected void setWhereSQL(String whereSQL) {
		this.whereSQL = whereSQL;
	}

	protected List<String> getFiltrosParametros() {
		return filtrosParametros;
	}

	protected void setFiltrosParametros(List<String> filtrosParametros) {
		this.filtrosParametros = filtrosParametros;
	}
}