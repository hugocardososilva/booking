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
@Table(name="CAD_PORTOS")
@Audited
public class CadastroPortos implements Serializable{
	private static final long serialVersionUID = 1L;
	public final static String sql = "select p from CadastroPortos p ";
	public final static String sqlCount = "select COUNT(p) from CadastroPortos p ";
	
	public final static String CAMPO_NOME = "nome";
	public final static String sql_pesquisa_autocomplete = "select p from CadastroPortos p where p.nome like :nome ";
	
	@Id
	@SequenceGenerator(name = "SEQ_PORTOS_ID", sequenceName = "SEQ_PORTOS_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_PORTOS_ID")

	@Column(name="PORTOS_ID")
	private Integer id;

	@Column(name="NOME", length = 150)
	private String nome;

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
		return nome;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CadastroPortos) {
			CadastroPortos item = (CadastroPortos) obj;
			return item.getId() == id;
		}

		return false;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
