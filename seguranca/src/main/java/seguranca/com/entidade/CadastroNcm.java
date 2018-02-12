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
@Table(name="CAD_NCM")
@Audited

public class CadastroNcm implements Serializable{
	private static final long serialVersionUID = 1L;
	public final static String sql = "select p from CadastroNcm p ";
	public final static String sqlCount = "select COUNT(p) from CadastroNcm p ";
	
	public final static String NCM_DESCRICAO = "descricaoNcm";
	public final static String NCM_ID = "id";
	public final static String NCM_ABREVIACAO = "ncmCodigo";
	public final static String sql_pesquisa_autocomplete = "select p from CadastroNcm p where p.ncmCodigo like :ncmCodigo order by p.ncmCodigo ";

	
	@Id
	@SequenceGenerator(name = "SEQ_NCM_ID", sequenceName = "SEQ_NCM_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_NCM_ID")

	@Column(name="NCM_ID")
	private Integer id;

	@Column(name="DESCRICAO", length = 500)
	private String descricaoNcm;

	@Column(name="NCM_CODIGO", length = 150)
	private String ncmCodigo;

	@Column(name="PRODUTO_ANUENCIA" , columnDefinition = "int default 0")
	private boolean produtoAnuencia;

	public String getProdutoAnuenciaSimNao() {
		String valor = "Não";
		if (produtoAnuencia) {
			valor = "Sim";
		}
		return valor;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricaoNcm() {
		return descricaoNcm;
	}

	public void setDescricaoNcm(String descricaoNcm) {
		this.descricaoNcm = descricaoNcm;
	}

	public String getNcmCodigo() {
		return ncmCodigo;
	}

	public void setNcmCodigo(String ncmCodigo) {
		this.ncmCodigo = ncmCodigo;
	}

	@Override
	public int hashCode() {
		return id;
	}
	
	@Override
	public String toString() {
		return ncmCodigo;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CadastroNcm) {
			CadastroNcm item = (CadastroNcm) obj;
			return item.getId() == id;
		}

		return false;
	}

	public boolean isProdutoAnuencia() {
		return produtoAnuencia;
	}

	public void setProdutoAnuencia(boolean produtoAnuencia) {
		this.produtoAnuencia = produtoAnuencia;
	}
}
