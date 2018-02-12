package seguranca.com.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Entity
@Table(name="USERS_DADOSCONEXAO")
@Audited
public class UserDadosConexao implements Serializable{
	private static final long serialVersionUID = 1L;
	public final static String sql = "select p from UserDadosConexao p ";
	public final static String sqlCount = "select COUNT(p) from UserDadosConexao p ";
	
	
	@Id
	@SequenceGenerator(name = "SEQ_DADOSCONEXAO_ID", sequenceName = "SEQ_DADOSCONEXAO_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_DADOSCONEXAO_ID")
	@Column(name="DADOSCONEXAO_ID")
	private Integer id;

	@Column(name="CONNECTION_USER", length = 350)
	private String connectionUser;

	@Column(name="CONNECTION_BANCODADOS", length = 350)
	private String connectionBancoDados;

	@Column(name="CONNECTION_PASSWORD", length = 350)
	private String connectionPassword;

	@Column(name="CONNECTION_DRIVER", length = 350)
	private String connectionDriver;

	@Column(name="CONNECTION_URL", length = 350)
	private String connectionUrl;

	@Column(name="CONNECTION_DIALECT", length = 350)
	private String connectionDialect;

	@Column(name="CONNECTION_JASPER", length = 350)
	private String connectionJasper;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConnectionUser() {
		return connectionUser;
	}

	public void setConnectionUser(String connectionUser) {
		this.connectionUser = connectionUser;
	}

	public String getConnectionPassword() {
		return connectionPassword;
	}

	public void setConnectionPassword(String connectionPassword) {
		this.connectionPassword = connectionPassword;
	}

	public String getConnectionDriver() {
		return connectionDriver;
	}

	public void setConnectionDriver(String connectionDriver) {
		this.connectionDriver = connectionDriver;
	}

	public String getConnectionUrl() {
		return connectionUrl;
	}

	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	}

	public String getConnectionDialect() {
		return connectionDialect;
	}

	public void setConnectionDialect(String connectionDialect) {
		this.connectionDialect = connectionDialect;
	}

	public String getConnectionJasper() {
		return connectionJasper;
	}

	public void setConnectionJasper(String connectionJasper) {
		this.connectionJasper = connectionJasper;
	}

	@Override
	public int hashCode() {
		return id;
	}
	
	@Override
	public String toString() {
		return connectionUser;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserDadosConexao) {
			UserDadosConexao item = (UserDadosConexao) obj;
			return item.getId() == id;
		}

		return false;
	}

	public String getConnectionBancoDados() {
		return connectionBancoDados;
	}

	public void setConnectionBancoDados(String connectionBancoDados) {
		this.connectionBancoDados = connectionBancoDados;
	}
}
