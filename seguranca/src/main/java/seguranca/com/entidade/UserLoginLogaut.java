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

import seguranca.com.enums.TipoLoginEnum;

@Entity
@Audited
@Table(name="USERS_LOGIN")

public class UserLoginLogaut implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_LOGIN_ID", sequenceName = "SEQ_LOGIN_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_LOGIN_ID")
	@Column(name="USERS_LOGIN_ID")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO", nullable = false, insertable = true, updatable = true)
	@ForeignKey(name = "FK_LOGIN_USERS")
	private User usuarioVinculado;

	@Column(name="TIPO_LOGIN")
	private TipoLoginEnum tipoLoginEnum; 

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
		if (obj instanceof UserLoginLogaut) {
			UserLoginLogaut item = (UserLoginLogaut) obj;
			return item.getId() == id;
		}

		return false;
	}

	public User getUsuarioVinculado() {
		return usuarioVinculado;
	}

	public void setUsuarioVinculado(User usuarioVinculado) {
		this.usuarioVinculado = usuarioVinculado;
	}

	public TipoLoginEnum getTipoLoginEnum() {
		return tipoLoginEnum;
	}

	public void setTipoLoginEnum(TipoLoginEnum tipoLoginEnum) {
		this.tipoLoginEnum = tipoLoginEnum;
	}

}
