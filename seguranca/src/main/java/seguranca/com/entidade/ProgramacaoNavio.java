package seguranca.com.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

@Entity
@Table(name="CAD_PROGRAMACAONAVIO")
@Audited
public class ProgramacaoNavio implements Serializable{
	private static final long serialVersionUID = 1L;
	public final static String sql = "select p from ProgramacaoNavio p ";
	public final static String sqlCount = "select COUNT(p) from ProgramacaoNavio p ";
	
	public final static String CAMPO_NAVIO = "navio";
	public final static String CAMPO_NAVIOVIAGEM = "navioViagem";
	public final static String CAMPO_DATAETA = "dataETA";
	public final static String sql_pesquisa_autocomplete = "select p from ProgramacaoNavio p where p.navio like :navio and p.dataETA >= :dataETA ";
	public final static String sql_pesquisa_navio = "select p from ProgramacaoNavio p where p.navio = :navio and p.navioViagem = :navioViagem";
	
	@Id
	@SequenceGenerator(name = "SEQ_PROGRAMACAONAVIO_ID", sequenceName = "SEQ_PROGRAMACAONAVIO_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_PROGRAMACAONAVIO_ID")
	@Column(name="PROGRAMACAONAVIO_ID")
	private Integer id;

	@Column(name="NAVIO", length = 150)
	private String navio;

	@Column(name="NAVIO_VIAGEM", length = 150)
	private String navioViagem;

	@Column(name="DATA_ETA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataETA;

	@Column(name="DATA_ATA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataATA;

	@Column(name="DATA_ETS")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataETS;

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
		if (obj instanceof ProgramacaoNavio) {
			ProgramacaoNavio item = (ProgramacaoNavio) obj;
			return item.getId() == id;
		}

		return false;
	}

	public Date getDataETA() {
		return dataETA;
	}

	public void setDataETA(Date dataETA) {
		this.dataETA = dataETA;
	}

	public Date getDataATA() {
		return dataATA;
	}

	public void setDataATA(Date dataATA) {
		this.dataATA = dataATA;
	}

	public Date getDataETS() {
		return dataETS;
	}

	public void setDataETS(Date dataETS) {
		this.dataETS = dataETS;
	}

	public String getNavio() {
		return navio;
	}

	public void setNavio(String navio) {
		this.navio = navio;
	}

	public String getNavioViagem() {
		return navioViagem;
	}

	public void setNavioViagem(String navioViagem) {
		this.navioViagem = navioViagem;
	}

}
