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
@Table(name="MAPA_USERS_IMPORTADOR")

public class UserImportador implements Serializable{
	private static final long serialVersionUID = 1L;

	public static final String FILTRO_DELETE_REGISTRO = "delete UserImportador u where u.id = :id";
	public final static String USERS_COMISSARIO_ID = "id";
	public final static String sql_pesquisa_autocomplete = "select p from UserImportador p "
			+ "where p.usuario = :usuario";
	public final static String sql_pesquisa_users_comissarios = "SELECT p FROM UserImportador p where p.importador.id = :importador";

	public final static String SQL_LISTA_USUARIOS = "select p from UserImportador p where p.usuario.id = :usuario";
	
	public final static String CAMPO_USUARIO = "usuario";
	public final static String CAMPO_IMPORTADOR = "cadimportador";
	
	@Id
	@SequenceGenerator(name = "SEQ_USRIMPORTADOR_ID", sequenceName = "SEQ_USRIMPORTADOR_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_USRIMPORTADOR_ID")

	@Column(name="USERS_IMPORTADOR_ID")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO", nullable = false, insertable = true, updatable = true)
	@ForeignKey(name = "FK_USR_IMPORTADOR")
	private User usuario;

	@ManyToOne
	@JoinColumn(name = "IMPORTADOR_ID", nullable = false, insertable = true, updatable = true)
	@ForeignKey(name = "FK_USER_IMPORTADOR_ID")
	private CadastroImportador importador;

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
		if (obj instanceof UserImportador) {
			UserImportador item = (UserImportador) obj;
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

	public CadastroImportador getImportador() {
		return importador;
	}

	public void setImportador(CadastroImportador importador) {
		this.importador = importador;
	}
}
