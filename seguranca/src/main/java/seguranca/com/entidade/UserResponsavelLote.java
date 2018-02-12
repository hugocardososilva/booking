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
@Audited
@Table(name="MAPA_USERS_RESPONSAVEL")

public class UserResponsavelLote implements Serializable{
	private static final long serialVersionUID = 1L;

	public static final String FILTRO_DELETE_REGISTRO = "delete UserResponsavelLote u where u.id = :id";
	public final static String USERS_COMISSARIO_ID = "id";
	public final static String sql_pesquisa_autocomplete = "select p from UserResponsavelLote p "
			+ "where p.usuario = :usuario";
	
	public final static String sql_pesquisa_users_respLote = "SELECT p FROM UserResponsavelLote p where p.cadRespLot.id = :cadRespLot";

	public final static String SQL_LISTA_USUARIOS = "select p from UserResponsavelLote p where p.usuario.id = :usuario";
	
	public final static String CAMPO_USUARIO = "usuario";
	public final static String CAMPO_CADCOMISSARIO = "cadRespLote";
	
	@Id
	@SequenceGenerator(name = "SEQ_USRRESPONSAVEL_ID", sequenceName = "SEQ_USRRESPONSAVEL_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_USRRESPONSAVEL_ID")

	@Column(name="USERS_RESPONSAVEL_ID")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO", nullable = false, insertable = true, updatable = true)
	@ForeignKey(name = "FK_USR_RESP_LOTE_ATUAL")
	private User usuario;

	@ManyToOne
	@JoinColumn(name = "RESPONSAVEL_LOTE_ID", nullable = false, insertable = true, updatable = true)
	@ForeignKey(name = "FK_CAD_RESP_LOTE_ATUAL")
	private CadastroResponsavelLote cadRespLote;

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
		if (obj instanceof UserResponsavelLote) {
			UserResponsavelLote item = (UserResponsavelLote) obj;
			return item.getId() == id;
		}

		return false;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public CadastroResponsavelLote getCadRespLote() {
		return cadRespLote;
	}

	public void setCadRespLote(CadastroResponsavelLote cadRespLote) {
		this.cadRespLote = cadRespLote;
	}
}
