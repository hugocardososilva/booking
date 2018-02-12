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

import seguranca.com.enums.TelasEntidadesEnum;

@Entity
@Audited
@Table(name="USERS_PERMISSOES")

public class UserPermissoes implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_PERMISSOES_ID", sequenceName = "SEQ_PERMISSOES_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_PERMISSOES_ID")
	@Column(name="USERS_PERMISSOES_ID")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO", nullable = false, insertable = true, updatable = true)
	@ForeignKey(name = "FK_USR_PERMISSOES")
	private User usuarioVinculado;

	@Column(name="TELA_LIBERACAO")
	private TelasEntidadesEnum telasEntidadesEnum; 

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
		if (obj instanceof UserPermissoes) {
			UserPermissoes item = (UserPermissoes) obj;
			return item.getId() == id;
		}

		return false;
	}

	public TelasEntidadesEnum getTelasEntidadesEnum() {
		return telasEntidadesEnum;
	}

	public void setTelasEntidadesEnum(TelasEntidadesEnum telasEntidadesEnum) {
		this.telasEntidadesEnum = telasEntidadesEnum;
	}

	public User getUsuarioVinculado() {
		return usuarioVinculado;
	}

	public void setUsuarioVinculado(User usuarioVinculado) {
		this.usuarioVinculado = usuarioVinculado;
	}

}
