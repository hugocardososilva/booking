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
@Table(name="MAPA_CAD_BL_LOGALTERACAO")
public class CadastroBlLogAlteracao implements Serializable{
	private static final long serialVersionUID = 1L;

	public final static String sqlQtdeRegistrosBLAnexos = "select COUNT(p) from CadastroBlLogAlteracao p where p.cadastroBL = :cadastroBL";
	public final static String CAMPO_ID = "CadastroBlLogAlteracao.id";

	@Id
	@SequenceGenerator(name = "SEQ_LOGALTERACAO_ID", sequenceName = "SEQ_LOGALTERACAO_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_LOGALTERACAO_ID")
	@Column(name="LOGALTERACAO_ID")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "CONTANIER_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_LOG_ALT_CONTANIER_BL")
	private CadastroBLContanier cadastroBLContanier;

	@ManyToOne
	@JoinColumn(name = "CADASTROBL_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_LOG_BL")
	private CadastroBL cadastroBL;

	@Column(name="DATA_ALTERACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAlteracao;

	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_LOG_USER")
	private User user;
	
	@Column(name="INSPECAO" , columnDefinition = "int default 0")
	private boolean inspecao;

	@Column(name="LIBERADO" , columnDefinition = "int default 0")
	private boolean liberado;

	@Column(name="ANEXO" , columnDefinition = "int default 0")
	private boolean anexo;

	@Column(name="DESC_MERCADORIA" , columnDefinition = "int default 0")
	private boolean descricaoMercadoria;

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
		if (obj instanceof CadastroBlLogAlteracao) {
			CadastroBlLogAlteracao item = (CadastroBlLogAlteracao) obj;
			return item.getId() == id;
		}

		return false;
	}

	public CadastroBLContanier getCadastroBLContanier() {
		return cadastroBLContanier;
	}

	public void setCadastroBLContanier(CadastroBLContanier cadastroBLContanier) {
		this.cadastroBLContanier = cadastroBLContanier;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isInspecao() {
		return inspecao;
	}

	public void setInspecao(boolean inspecao) {
		this.inspecao = inspecao;
	}

	public boolean isLiberado() {
		return liberado;
	}

	public void setLiberado(boolean liberado) {
		this.liberado = liberado;
	}

	public boolean isAnexo() {
		return anexo;
	}

	public void setAnexo(boolean anexo) {
		this.anexo = anexo;
	}

	public boolean isDescricaoMercadoria() {
		return descricaoMercadoria;
	}

	public void setDescricaoMercadoria(boolean descricaoMercadoria) {
		this.descricaoMercadoria = descricaoMercadoria;
	}

	public CadastroBL getCadastroBL() {
		return cadastroBL;
	}

	public void setCadastroBL(CadastroBL cadastroBL) {
		this.cadastroBL = cadastroBL;
	}

}
