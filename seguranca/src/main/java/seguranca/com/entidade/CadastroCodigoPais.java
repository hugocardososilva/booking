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
@Table(name="CAD_CODIGOPAIS")
@Audited

public class CadastroCodigoPais implements Serializable{
	private static final long serialVersionUID = 1L;
	public final static String sql = "select p from CadastroCodigoPais p ";
	public final static String sqlCount = "select COUNT(p) from CadastroCodigoPais p ";

	public final static String sql_pesquisa_autocomplete = "select p from CadastroCodigoPais p "
			+ "where p.abreviacaoPais like :abreviacaoPais or p.descricaoPais like :descricaoPais";
	
	public final static String CODIGOPAIS_CODIGOPAISIBGE = "codigoPaisIBGE";
	public final static String CODIGOPAIS_DESCRICAO_PAIS = "descricaoPais";
	public final static String CODIGOPAIS_ABREVIACAOPAIS = "abreviacaoPais";

	@Id
	@SequenceGenerator(name = "SEQ_CODIGOPAIS_ID", sequenceName = "SEQ_CODIGOPAIS_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CODIGOPAIS_ID")

	@Column(name="CODIGOPAIS_ID")
	private Integer id;

	@Column(name="CODIGOPAIS_IBGE_ID")
	private Integer codigoPaisIBGE;

	@Column(name="DESCRICAO_PAIS", length = 150)
	private String descricaoPais;

	@Column(name="ABREVIACAO_PAIS", length = 10)
	private String abreviacaoPais;

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
		return abreviacaoPais;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CadastroCodigoPais) {
			CadastroCodigoPais item = (CadastroCodigoPais) obj;
			return item.getId() == id;
		}

		return false;
	}

	public Integer getCodigoPaisIBGE() {
		return codigoPaisIBGE;
	}

	public void setCodigoPaisIBGE(Integer codigoPaisIBGE) {
		this.codigoPaisIBGE = codigoPaisIBGE;
	}

	public String getDescricaoPais() {
		return descricaoPais;
	}

	public void setDescricaoPais(String descricaoPais) {
		this.descricaoPais = descricaoPais;
	}

	public String getAbreviacaoPais() {
		return abreviacaoPais;
	}

	public void setAbreviacaoPais(String abreviacaoPais) {
		this.abreviacaoPais = abreviacaoPais;
	}
}
