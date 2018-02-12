package seguranca.com.entidade;

import java.io.Serializable;
import java.util.ArrayList;
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

import org.hibernate.annotations.ForeignKey;
import org.hibernate.envers.Audited;

import seguranca.com.enums.TipoSimNaoEnum;

@Entity
@Audited
@Table(name="MAPA_CAD_BL_LCL")
public class CadastroBLContanierLCL implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final String FILTRO_DELETE_REGISTRO = "delete CadastroBLContanierLCL u where u.id = :id";
	public final static String sql_pesquisa_autocomplete = "select p from CadastroBLContanierLCL p where p.numeroContanier like :numeroContanier";

	public final static String LISTA_BL_MASTER = "select p from CadastroBLContanierLCL p where p.numeroBLMaster is not null";

	public final static String CAMPO_ID = "id";
	public final static String CAMPO_LIBERADO = "liberado";
	public final static String CAMPO_NUMEROCONTANIER = "numeroContanier";
	
	
	@Id
	@SequenceGenerator(name = "SEQ_LCL_ID", sequenceName = "SEQ_LCL_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_LCL_ID")
	@Column(name="LCL_ID")
	private Integer id;

	@Column(name="NUMEROCONTANIER", length = 350)
	private String numeroContanier;
	
	@Column(name="NUMEROLACRE", length = 350)
	private String numeroLacre;

	@Column(name="QUANTIDADE_BL", columnDefinition = "int default 0")
	private int quantidadeBL;

	@Column(name="QUANTIDADE_VOLUME", columnDefinition = "int default 0")
	private float quantidadeVolume;

	@Column(name="QUANTIDADE_MERCADORIA", columnDefinition = "int default 0")
	private float quantidadeMercadoria;

	@Column(name="CARGA_IMO")
	private TipoSimNaoEnum cargaImo;

	@Column(name = "NUMERO_BL_MASTER", length = 350)
	private String numeroBLMaster;

	@ManyToOne
	@JoinColumn(name = "IMPORTADOR_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_LCL_IMPORTADOR_ID")
	private CadastroImportador importadorLCL;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "containerLCL", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CadastroBL> listaCadastroBL;

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
		return numeroBLMaster;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CadastroBLContanierLCL) {
			CadastroBLContanierLCL item = (CadastroBLContanierLCL) obj;
			return item.getId() == id;
		}

		return false;
	}

	public String getNumeroContanier() {
		return numeroContanier;
	}

	public void setNumeroContanier(String numeroContanier) {
		this.numeroContanier = numeroContanier;
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

	public List<CadastroBL> getListaCadastroBL() {
		if (listaCadastroBL == null) {
			listaCadastroBL = new ArrayList<CadastroBL>();
		}
		return listaCadastroBL;
	}

	public void setListaCadastroBL(List<CadastroBL> listaCadastroBL) {
		this.listaCadastroBL = listaCadastroBL;
	}

	public int getQuantidadeBL() {
		return quantidadeBL;
	}

	public void setQuantidadeBL(int quantidadeBL) {
		this.quantidadeBL = quantidadeBL;
	}

	public float getQuantidadeVolume() {
		return quantidadeVolume;
	}

	public void setQuantidadeVolume(float quantidadeVolume) {
		this.quantidadeVolume = quantidadeVolume;
	}

	public float getQuantidadeMercadoria() {
		return quantidadeMercadoria;
	}

	public void setQuantidadeMercadoria(float quantidadeMercadoria) {
		this.quantidadeMercadoria = quantidadeMercadoria;
	}

	public String getNumeroBLMaster() {
		return numeroBLMaster;
	}

	public void setNumeroBLMaster(String numeroBLMaster) {
		this.numeroBLMaster = numeroBLMaster;
	}

	public CadastroImportador getImportadorLCL() {
		return importadorLCL;
	}

	public void setImportadorLCL(CadastroImportador importadorLCL) {
		this.importadorLCL = importadorLCL;
	}
	
	public String getNumeroContainerBLMaster() {
		return numeroContanier + " - " + numeroBLMaster;
	}

}
