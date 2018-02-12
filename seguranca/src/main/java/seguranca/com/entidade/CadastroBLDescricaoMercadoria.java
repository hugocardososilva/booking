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

@Entity
@Audited
@Table(name="MAPA_CAD_BL_EMBENCONTRADA")
public class CadastroBLDescricaoMercadoria implements Serializable{
	private static final long serialVersionUID = 1L;

	public final static String sqlQtdeRegistrosBLAnexos = "select COUNT(p) from CadastroBLDescricaoMercadoria p where p.cadastroBLContanier = :cadastroBLContanier";
	public static final String FILTRO_DELETE_REGISTRO = "delete CadastroBLDescricaoMercadoria u where u.id = :id";
	public final static String BL_CONTAINER_ID = "id";

	@Id
	@SequenceGenerator(name = "SEQ_EMBENCONTRADA_ID", sequenceName = "SEQ_EMBENCONTRADA_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_EMBENCONTRADA_ID")
	@Column(name="EMBENCONTRADA_ID")
	private Integer id;

	@Column(name="DATA_CADASTRO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;

	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_HIST_EMBS_USER")
	private User user;

	@ManyToOne
	@JoinColumn(name = "CADASTROBL_ID", nullable = false, insertable = true, updatable = true)
	@ForeignKey(name = "FK_DESCMERCADORIA_1_BL")
	private CadastroBL cadastroBL;

	@Column(name="DESCRICAOMERCADORIA", length = 350)
	private String descricaoMercadoria;

	@ManyToOne
	@JoinColumn(name = "EMBALAGEM_ENCONTRADA_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_DESC_EMB_ENCONTRADA_ID")
	private CadastroEspecie tipoEmbalagemEncontrada;

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
		if (obj instanceof CadastroBLDescricaoMercadoria) {
			CadastroBLDescricaoMercadoria item = (CadastroBLDescricaoMercadoria) obj;
			return item.getId() == id;
		}

		return false;
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

	public String getDescricaoMercadoria() {
		return descricaoMercadoria;
	}

	public void setDescricaoMercadoria(String descricaoMercadoria) {
		this.descricaoMercadoria = descricaoMercadoria;
	}

	public CadastroEspecie getTipoEmbalagemEncontrada() {
		return tipoEmbalagemEncontrada;
	}

	public void setTipoEmbalagemEncontrada(CadastroEspecie tipoEmbalagemEncontrada) {
		this.tipoEmbalagemEncontrada = tipoEmbalagemEncontrada;
	}

}
