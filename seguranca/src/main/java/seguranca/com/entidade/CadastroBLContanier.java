package seguranca.com.entidade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.envers.Audited;

import seguranca.com.enums.RiscoFitossanitarioEnum;
import seguranca.com.enums.StatusBLEnum;
import seguranca.com.enums.TipoSimNaoEnum;
import seguranca.com.util.GenericoEntidade;

@Entity
@Audited
@Table(name = "MAPA_CAD_BL_CONTANIER")
public class CadastroBLContanier extends GenericoEntidade implements Serializable {
	private static final long serialVersionUID = 1L;

	public final static String sql = "select p from CadastroBLContanier p ";
	public final static String sqlCount = "select COUNT(p) from CadastroBLContanier p ";

	public final static String sqlCountTotalRegistros = "select COUNT(p) from CadastroBLContanier p where p.cadastroBL.id = :cadastroBL ";
	public final static String sqlCountTotalInspecao = "select COUNT(p) from CadastroBLContanier p where p.cadastroBL.id = :cadastroBL and p.inspecao = 1";
	public final static String sqlCountTotalLiberado = "select COUNT(p) from CadastroBLContanier p where p.cadastroBL.id = :cadastroBL and p.liberado = 1";
	public final static String sqlCountRegistrosStatus = "select COUNT(p) from CadastroBLContanier p where p.cadastroBL.id = :cadastroBL and statusBLEnum = :statusBLEnum";

	public final static String UPDATE_ATUALIZA_DADOS = "update CadastroBLContanier p "
			+ "set liberado = :liberado, inspecao = :inspecao, statusBLEnum = :statusBLEnum"
			+ " where p.cadastroBL.id = :cadastroBL";

	public final static String UPDATE_ATUALIZA_STATUS_LIBERADO_SEM_VISTORIA = "update CadastroBLContanier p "
			+ "set statusBLEnum = 2" + " where p.cadastroBL.id = :cadastroBL";

	public final static String UPDATE_ATUALIZA_DADOS_LCL = "update CadastroBLContanier p "
			+ "set numeroContanier = :numeroContanier, numeroLacre = :numeroLacre where p.containerLCL.id = :containerLCL";

	public final static String sqlQtdeRegistrosBLAnexos = "select COUNT(p) from CadastroBLContanier p where p.cadastroBL = :cadastroBL";
	public static final String FILTRO_DELETE_REGISTRO = "delete CadastroBLContanier u where u.id = :id";
	public final static String BL_CONTAINER_ID = "id";

	public final static String CADASTRO_BL_DESCRICAOBL = "cadastroBL.descricaoBL";
	public final static String CAMPO_LIBERADO = "liberado";
	public final static String CAMPO_CADASTROBL = "cadastroBL";
	public final static String CAMPO_CONTAINERLCL = "containerLCL";
	public final static String CAMPO_INSPECAO = "inspecao";
	public final static String CAMPO_STATUSBLENUM = "statusBLEnum";
	public final static String CAMPO_NUMEROCONTANIER = "numeroContanier";
	public final static String CAMPO_NUMEROLACRE = "numeroLacre";
	public final static String CADASTRO_PAIS_ORIGEM_DESCRICAO = "cadastroBL.paisOrigem.descricaoPais";

	@Id
	@SequenceGenerator(name = "SEQ_CONTANIER_ID", sequenceName = "SEQ_CONTANIER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CONTANIER_ID")
	@Column(name = "CONTANIER_ID")
	private Integer id;

	@Column(name = "NUMEROCONTANIER", length = 350)
	private String numeroContanier;

	@Column(name = "NUMEROLACRE", length = 350)
	private String numeroLacre;

	@Column(name = "NUMERO_DTC_PROCESSO", length = 350)
	private String numeroDtcProcesso;

	@ManyToOne
	@JoinColumn(name = "CADASTROBL_ID", nullable = false, insertable = true, updatable = true)
	@ForeignKey(name = "FK_BL_ANEXOS_CONTANIER")
	private CadastroBL cadastroBL;

	@ManyToOne
	@JoinColumn(name = "LCL_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_CONTAINER_LCL_ID")
	private CadastroBLContanierLCL containerLCL;

	@Column(name = "INSPECAO", columnDefinition = "int default 0")
	private boolean inspecao;

	@Column(name = "LIBERADO", columnDefinition = "int default 0")
	private boolean liberado;

	@Transient
	private boolean inspecaoControle;

	@Transient
	private boolean liberadoControle;

	@Column(name = "STATUS_BL")
	private StatusBLEnum statusBLEnum;

	@Column(name = "RISCO_TIPO")
	private RiscoFitossanitarioEnum riscoFitossanitarioEnum;

	@Column(name = "CARGA_IMO")
	private TipoSimNaoEnum cargaImo;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cadastroBLContanier", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CadastroBlLogAlteracao> listaBLLogs;

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
		if (obj instanceof CadastroBLContanier) {
			CadastroBLContanier item = (CadastroBLContanier) obj;
			return item.getId() == id;
		}

		return false;
	}

	public CadastroBL getCadastroBL() {
		return cadastroBL;
	}

	public void setCadastroBL(CadastroBL cadastroBL) {
		this.cadastroBL = cadastroBL;
	}

	public String getNumeroContanier() {
		return numeroContanier;
	}

	public void setNumeroContanier(String numeroContanier) {
		this.numeroContanier = numeroContanier;
	}

	public StatusBLEnum getStatusBLEnum() {
		return statusBLEnum;
	}

	public void setStatusBLEnum(StatusBLEnum statusBLEnum) {
		this.statusBLEnum = statusBLEnum;
	}

	public boolean isLiberado() {
		return liberado;
	}

	public void setLiberado(boolean liberado) {
		this.liberado = liberado;
	}

	public boolean isInspecao() {
		return inspecao;
	}

	public void setInspecao(boolean inspecao) {
		this.inspecao = inspecao;
	}

	public RiscoFitossanitarioEnum getRiscoFitossanitarioEnum() {
		return riscoFitossanitarioEnum;
	}

	public void setRiscoFitossanitarioEnum(RiscoFitossanitarioEnum riscoFitossanitarioEnum) {
		this.riscoFitossanitarioEnum = riscoFitossanitarioEnum;
	}

	public List<CadastroBlLogAlteracao> getListaBLLogs() {
		return listaBLLogs;
	}

	public void setListaBLLogs(List<CadastroBlLogAlteracao> listaBLLogs) {
		this.listaBLLogs = listaBLLogs;
	}

	public String getNumeroLacre() {
		return numeroLacre;
	}

	public void setNumeroLacre(String numeroLacre) {
		this.numeroLacre = numeroLacre;
	}

	public TipoSimNaoEnum getCargaImo() {
		return cargaImo;
	}

	public void setCargaImo(TipoSimNaoEnum cargaImo) {
		this.cargaImo = cargaImo;
	}

	public CadastroBLContanierLCL getContainerLCL() {
		return containerLCL;
	}

	public void setContainerLCL(CadastroBLContanierLCL containerLCL) {
		this.containerLCL = containerLCL;
	}

	public boolean isInspecaoControle() {
		if (!inspecaoControle) {
			inspecaoControle = inspecao;
		}
		return inspecaoControle;
	}

	public void setInspecaoControle(boolean inspecaoControle) {
		this.inspecaoControle = inspecaoControle;
	}

	public boolean isLiberadoControle() {
		if (!liberadoControle) {
			liberadoControle = liberado;
		}
		return liberadoControle;
	}

	public void setLiberadoControle(boolean liberadoControle) {
		this.liberadoControle = liberadoControle;
	}

	public String getNumeroDtcProcesso() {
		return numeroDtcProcesso;
	}

	public void setNumeroDtcProcesso(String numeroDtcProcesso) {
		this.numeroDtcProcesso = numeroDtcProcesso;
	}

	@Override
	public String toString() {
		if(numeroContanier == null) {
			return "";
		}
		return numeroContanier;
	}
	

}
