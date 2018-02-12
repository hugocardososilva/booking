package seguranca.com.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.envers.Audited;

import seguranca.com.enums.TipoPessoaFisicaJuridicaEnum;

@Entity
@Table(name="CAD_COMISSARIO", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "cpf" }, name = "UQ_COMISSARIA_CNPJ") } )
@Audited
@NamedQuery(name = "CadastroComissario.FIND_BY_ENTIDADE", query = "select u from CadastroComissario u where u.nome = :nome")

public class CadastroComissario implements Serializable {
	private static final long serialVersionUID = 1L;
	public final static String sql = "select p from CadastroComissario p ";
	public final static String sqlCount = "select COUNT(p) from CadastroComissario p ";

	public final static String COMISSARIO_NOME = "nome";
	public final static String COMISSARIO_CPF = "cpf";
	public final static String COMISSARIO_ID = "id";

	public static final String FIND_BY_ENTIDADE = "CadastroComissario.nome";
	public static final String FILTRO_NAME = "select u from CadastroComissario u where u.nome = :nome";
	public static final String FILTRO_CNPJ = "select u from CadastroComissario u where u.cpf = :cpf";
	public final static String sql_pesquisa_autocomplete = "select p from CadastroComissario p where p.nome like :nome ";

	
	@Id
	@SequenceGenerator(name = "SEQ_COMISSARIO_ID", sequenceName = "SEQ_COMISSARIO_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_COMISSARIO_ID")
	@Column(name="COMISSARIO_ID")
	private Integer id;

	@Column(name="NOME", length = 120, nullable = false)
	private String nome;

	@Column(name="CPF", length = 20, unique = true)
	private String cpf;
	
	@Column(name="TIPO_PESSOA")
	private TipoPessoaFisicaJuridicaEnum tipoPessoaEnum;

	@Column(name="EMAIL", length = 150)
	private String email;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
	
	@Override
	public String toString() {
		return nome;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CadastroComissario) {
			CadastroComissario item = (CadastroComissario) obj;
			return item.getId() == id;
		}

		return false;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public TipoPessoaFisicaJuridicaEnum getTipoPessoaEnum() {
		return tipoPessoaEnum;
	}

	public void setTipoPessoaEnum(TipoPessoaFisicaJuridicaEnum tipoPessoaEnum) {
		this.tipoPessoaEnum = tipoPessoaEnum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

}
