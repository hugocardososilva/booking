package seguranca.com.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

@Entity
@Table(name = "SYS_ARQUIVOS_ESALES")
@Audited

public class ArquivosCobrancaEsalesSeguranca  {

	public static final String FILTRO_USUARIO_SENHA = "select u from ArquivosCobrancaEsalesSeguranca u where u.descricao = :descricao and u.password = :password";

	public static final String PROCESSADO = "processado";
	public static final String CODIGO_BANCO = "codigoBanco";
	
	@Id
	@SequenceGenerator(name = "ARQUIVOS_COBRANCA_SEQ", sequenceName = "ARQUIVOS_COBRANCA_SEQ")
	@GeneratedValue(generator = "ARQUIVOS_COBRANCA_SEQ", strategy = javax.persistence.GenerationType.IDENTITY)
	@Column(name = "ARQUIVO_COBRANCA_ID", nullable = false)
	
	private int ident;

	@Column(name = "DATA_LEITURA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataLeituraArquivo;	

	@Column(name = "DATA_LEITURA_NOVO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataLeituraArquivoTeste;	

	private String password;

	private String descricao;

	public int getIdent() {
		return ident;
	}

	public void setIdent(int ident) {
		this.ident = ident;
	}

	public Date getDataLeituraArquivo() {
		return dataLeituraArquivo;
	}

	public void setDataLeituraArquivo(Date dataLeituraArquivo) {
		this.dataLeituraArquivo = dataLeituraArquivo;
	}

	public Date getDataLeituraArquivoTeste() {
		return dataLeituraArquivoTeste;
	}

	public void setDataLeituraArquivoTeste(Date dataLeituraArquivoTeste) {
		this.dataLeituraArquivoTeste = dataLeituraArquivoTeste;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
