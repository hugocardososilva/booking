package seguranca.com.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.envers.Audited;

@Entity
@Table(name="CAD_FATURARCONTRA", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "cnpj" }, name = "UQ_FAT_CONTRA_CNPJ") } )
@Audited

public class CadastroFaturarContra implements Serializable {
	private static final long serialVersionUID = 1L;
	public final static String sql = "select p from CadastroFaturarContra p ";
	public final static String sqlCount = "select COUNT(p) from CadastroFaturarContra p ";
	
	public final static String CAMPO_RAZAOSOCIAL = "razaoSocial";
	public final static String IMPORTADOR_ID = "id";
	public final static String CAMPO_CNPJ = "cnpj";
	public final static String sql_pesquisa_autocomplete = "select p from CadastroFaturarContra p where p.razaoSocial like :razaoSocial ";
	
	@Id
	@SequenceGenerator(name = "SEQ_FATURAR_CONTRA_ID", sequenceName = "SEQ_FATURAR_CONTRA_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_FATURAR_CONTRA_ID")

	@Column(name="FATURAR_CONTRA_ID")
	private Integer id;

	@Column(name="CNPJ", unique = true)
	private String cnpj;

	@Column(name="RAZAOSOCIAL", length = 150, nullable = false)
	private String razaoSocial;

	@Column(name="EMAIL", length = 150)
	private String email;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return id;
	}
	
	@Override
	public String toString() {
		return razaoSocial;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CadastroFaturarContra) {
			CadastroFaturarContra item = (CadastroFaturarContra) obj;
			return item.getId() == id;
		}

		return false;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
