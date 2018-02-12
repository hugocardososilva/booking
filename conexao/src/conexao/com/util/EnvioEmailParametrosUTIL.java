package conexao.com.util;

import java.util.HashMap;
import java.util.Map;

public class EnvioEmailParametrosUTIL {
	
	private String assunto;
	private String conteudoEmail;
	private String nomeArquivo;
	private String comCopia;
	private Map<String, String> variosAnexos;
	
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getConteudoEmail() {
		return conteudoEmail;
	}
	public void setConteudoEmail(String conteudoEmail) {
		this.conteudoEmail = conteudoEmail;
	}
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	public String getComCopia() {
		return comCopia;
	}
	public void setComCopia(String comCopia) {
		this.comCopia = comCopia;
	}
	public Map<String, String> getVariosAnexos() {
		if (variosAnexos == null) {
			variosAnexos = new HashMap<String, String>();
		}
		return variosAnexos;
	}
	public void setVariosAnexos(Map<String, String> variosAnexos) {
		this.variosAnexos = variosAnexos;
	}
	
}
