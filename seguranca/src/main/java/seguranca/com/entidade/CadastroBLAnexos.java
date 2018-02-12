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

import seguranca.com.enums.TipoAnexosEnum;
import seguranca.com.util.RemoverCaracterEspecialUTIL;

@Entity
@Audited
@Table(name="MAPA_CAD_BLANEXOS")
public class CadastroBLAnexos implements Serializable{
	private static final long serialVersionUID = 1L;

	public final static String sqlQtdeRegistrosBLAnexos = "select COUNT(p) from CadastroBLAnexos p where p.cadastroBL = :cadastroBL";
	public final static String SQL_LISTA_POR_TIPO_DE_ANEXOS = "select p from CadastroBLAnexos p where p.cadastroBL.id = :cadastroBL and tipoAnexosEnum = :tipoAnexosEnum";
	public final static String SQL_LISTA_POR_TIPO_DE_ANEXOS_MAPA = "select p from CadastroBLAnexos p where p.cadastroBL.id = :cadastroBL and tipoAnexosEnum in (0,7,9,4)";
	public final static String SQL_QTDE_POR_TIPO_DE_ANEXOS = "select COUNT(p) from CadastroBLAnexos p where p.cadastroBL.id = :cadastroBL and tipoAnexosEnum = :tipoAnexosEnum";

	public final static String SQL_LISTA_TIPO_EMAIL = "select p from CadastroBLAnexos p where p.cadastroBL.id = :cadastroBL and envioEmail = 1";

	public final static String CADASTRO_BL_ANEXOS = "cadastroBL.id";
	public final static String CAMPO_CADASTROBL = "cadastroBL";
	public final static String CAMPO_TIPOANEXOSENUM = "tipoAnexosEnum";

	@Id
	@SequenceGenerator(name = "SEQ_ANEXOS_ID", sequenceName = "SEQ_ANEXOS_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ANEXOS_ID")
	@Column(name="ANEXOS_ID")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "CADASTROBL_ID", nullable = false, insertable = true, updatable = true)
	@ForeignKey(name = "FK_BL_ANEXOS_BL")
	private CadastroBL cadastroBL;

	@Column(name="CAMINHO_ANEXO", length = 350)
	private String caminhoAnexo;

	@Column(name="TIPO_ANEXOS", columnDefinition = "int default 0")
	private TipoAnexosEnum tipoAnexosEnum;

	@Column(name="ENVIO_EMAIL", columnDefinition = "int default 0")
	private boolean envioEmail;

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
		if (obj instanceof CadastroBLAnexos) {
			CadastroBLAnexos item = (CadastroBLAnexos) obj;
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

	public String getCaminhoAnexo() {
		return caminhoAnexo;
	}

	public void setCaminhoAnexo(String caminhoAnexo) {
		this.caminhoAnexo = RemoverCaracterEspecialUTIL.removerAcentuacao(caminhoAnexo);
	}

	public TipoAnexosEnum getTipoAnexosEnum() {
		return tipoAnexosEnum;
	}

	public void setTipoAnexosEnum(TipoAnexosEnum tipoAnexosEnum) {
		this.tipoAnexosEnum = tipoAnexosEnum;
	}

	public boolean isEnvioEmail() {
		return envioEmail;
	}

	public void setEnvioEmail(boolean envioEmail) {
		this.envioEmail = envioEmail;
	}
}
