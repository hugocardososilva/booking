package seguranca.com.util;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

public class GenericoEntidade {

	@Transient
	private String logNomeUsuario;

	@Transient
	@Temporal(TemporalType.TIMESTAMP)
	private Date logDataAlteracao;

	public String getLogNomeUsuario() {
		return logNomeUsuario;
	}

	public void setLogNomeUsuario(String logNomeUsuario) {
		this.logNomeUsuario = logNomeUsuario;
	}

	public Date getLogDataAlteracao() {
		return logDataAlteracao;
	}

	public void setLogDataAlteracao(Date logDataAlteracao) {
		this.logDataAlteracao = logDataAlteracao;
	}
	
	
	
}
