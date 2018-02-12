package seguranca.com.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.envers.Audited;

import seguranca.com.enums.RiscoFitossanitarioEnum;

@Entity
@Audited
@Table(name="MAPA_CAD_BL_RISCO")
public class CadastroBLRiscoFitossanitario implements Serializable{
	private static final long serialVersionUID = 1L;

	public final static String sqlQtdeRegistrosBLAnexos = "select COUNT(p) from CadastroBLRiscoFitossanitario p where p.cadastroBLContanier = :cadastroBLContanier";
	public static final String FILTRO_DELETE_REGISTRO = "delete CadastroBLRiscoFitossanitario u where u.id = :id";
	public final static String BL_CONTAINER_ID = "id";

	@Id
	@SequenceGenerator(name = "SEQ_RISCO_ID", sequenceName = "SEQ_RISCO_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_RISCO_ID")
	@Column(name="RISCO_ID")
	private Integer id;

	@Column(name="DATA_CADASTRO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;

	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_RISCO_USER")
	private User user;

	@ManyToOne
	@JoinColumn(name = "CADASTROBL_ID", nullable = false, insertable = true, updatable = true)
	@ForeignKey(name = "FK_RISCO_BL")
	private CadastroBL cadastroBL;

	@Column(name="RISCO_TIPO")
	private RiscoFitossanitarioEnum riscoFitossanitarioEnum;

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
		if (obj instanceof CadastroBLRiscoFitossanitario) {
			CadastroBLRiscoFitossanitario item = (CadastroBLRiscoFitossanitario) obj;
			return item.getId() == id;
		}

		return false;
	}

	public RiscoFitossanitarioEnum getRiscoFitossanitarioEnum() {
		return riscoFitossanitarioEnum;
	}

	public void setRiscoFitossanitarioEnum(RiscoFitossanitarioEnum riscoFitossanitarioEnum) {
		this.riscoFitossanitarioEnum = riscoFitossanitarioEnum;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CadastroBL getCadastroBL() {
		return cadastroBL;
	}

	public void setCadastroBL(CadastroBL cadastroBL) {
		this.cadastroBL = cadastroBL;
	}

}
