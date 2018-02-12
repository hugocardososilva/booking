package seguranca.com.entidade;

import java.util.Date;

public class LogInspecaoLiberadoMapa {

	private boolean inspecao;

	private boolean liberado;

	private String logNomeUsuario;

	private String numeroContainer;

	private Date logDataAlteracao;

	private Date dataEnvioMapa;

	public boolean isInspecao() {
		return inspecao;
	}

	public void setInspecao(boolean inspecao) {
		this.inspecao = inspecao;
	}

	public boolean isLiberado() {
		return liberado;
	}

	public void setLiberado(boolean liberado) {
		this.liberado = liberado;
	}

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

	public String getNumeroContainer() {
		return numeroContainer;
	}

	public void setNumeroContainer(String numeroContainer) {
		this.numeroContainer = numeroContainer;
	}

	public Date getDataEnvioMapa() {
		return dataEnvioMapa;
	}

	public void setDataEnvioMapa(Date dataEnvioMapa) {
		this.dataEnvioMapa = dataEnvioMapa;
	}
	
}
