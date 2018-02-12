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
@Table(name="MAPA_USERS_COMISSARIO")

public class UserComissario implements Serializable{
	private static final long serialVersionUID = 1L;

	public static final String FILTRO_DELETE_REGISTRO = "delete UserComissario u where u.id = :id";
	public final static String USERS_COMISSARIO_ID = "id";
	public final static String sql_pesquisa_autocomplete = "select p from UserComissario p "
			+ "where p.usuario = :usuario";
	public final static String sql_pesquisa_users_comissarios = "SELECT p FROM UserComissario p where p.cadComissario.id = :cadComissario";

	public final static String SQL_LISTA_USUARIOS = "select p from UserComissario p where p.usuario.id = :usuario";
	
	public final static String CAMPO_USUARIO = "usuario";
	public final static String CAMPO_CADCOMISSARIO = "cadComissario";
	
	@Id
	@SequenceGenerator(name = "SEQ_USRCOMISSARIO_ID", sequenceName = "SEQ_USRCOMISSARIO_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_USRCOMISSARIO_ID")

	@Column(name="USERS_COMISSARIO_ID")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO", nullable = false, insertable = true, updatable = true)
	@ForeignKey(name = "FK_USR_COMISSARIO_ATUAL")
	private User usuario;

	@ManyToOne
	@JoinColumn(name = "COMISSARIO_ID", nullable = false, insertable = true, updatable = true)
	@ForeignKey(name = "FK_CAD_COMISSARIO_ATUAL")
	private CadastroComissario cadComissario;

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
		if (obj instanceof UserComissario) {
			UserComissario item = (UserComissario) obj;
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

	public CadastroComissario getCadComissario() {
		return cadComissario;
	}

	public void setCadComissario(CadastroComissario cadComissario) {
		this.cadComissario = cadComissario;
	}
}
