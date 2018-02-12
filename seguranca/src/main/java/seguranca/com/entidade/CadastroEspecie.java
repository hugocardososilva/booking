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
@Table(name="CAD_ESPECIE")
@Audited

public class CadastroEspecie implements Serializable{
	private static final long serialVersionUID = 1L;
	public final static String sql = "select p from CadastroEspecie p ";
	public final static String sqlCount = "select COUNT(p) from CadastroEspecie p ";
	
	public final static String ESPECIE_DESCRICAO = "descricao";
	public final static String ESPECIE_ID = "id";
	public final static String ESPECIE_ABREVIACAO = "abreviacao";
	public final static String sql_pesquisa_autocomplete = "select p from CadastroEspecie p where p.abreviacao like :abreviacao or p.descricao like :descricao";
	
	
	@Id
	@SequenceGenerator(name = "SEQ_ESPECIEAMBALAGEM_ID", sequenceName = "SEQ_ESPECIEAMBALAGEM_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ESPECIEAMBALAGEM_ID")

	@Column(name="ESPECIEAMBALAGEM_ID")
	private Integer id;

	@Column(name="DESCRICAO", length = 150)
	private String descricao;

	@Column(name="ABREVIACAO", length = 10)
	private String abreviacao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getAbreviacao() {
		return abreviacao;
	}

	public void setAbreviacao(String abreviacao) {
		this.abreviacao = abreviacao;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
	
	@Override
	public String toString() {
		return abreviacao;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CadastroEspecie) {
			CadastroEspecie item = (CadastroEspecie) obj;
			return item.getId() == id;
		}

		return false;
	}
	
}
