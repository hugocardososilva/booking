package seguranca.com.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.envers.Audited;

@Entity
@Table(name="MAPA_NCM")
@Audited

public class MapaNcm implements Serializable{
	private static final long serialVersionUID = 1L;
	public final static String sql = "select p from CadastroBLNcm p ";
	public final static String sqlCount = "select COUNT(p) from CadastroBLNcm p ";
	
	@Id
	@SequenceGenerator(name = "SEQ_MAPA_NCM_ID", sequenceName = "SEQ_MAPA_NCM_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_MAPA_NCM_ID")
	@Column(name="MAPA_NCM_ID")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "NCM_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_MAPANCM_ID")
	private CadastroNcm ncm;

	@ManyToOne
	@JoinColumn(name = "CADASTROBL_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_MAPANCM_BL_ID")
	private CadastroBL cadastroBL;

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
	public boolean equals(Object obj) {
		if (obj instanceof MapaNcm) {
			MapaNcm item = (MapaNcm) obj;
			return item.getId() == id;
		}

		return false;
	}

	public CadastroNcm getNcm() {
		return ncm;
	}

	public void setNcm(CadastroNcm ncm) {
		this.ncm = ncm;
	}

	public CadastroBL getCadastroBL() {
		return cadastroBL;
	}

	public void setCadastroBL(CadastroBL cadastroBL) {
		this.cadastroBL = cadastroBL;
	}
}
