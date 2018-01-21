package com.entidade;

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

import seguranca.com.entidade.User;

/**
 * Class representa os importadores que poderão visualizar
 * @author murilonadalin
 * @since 13-09-2017
 */

@Entity
@Audited
@Table(name="CAD_USER_COMISSARIA_USUARIOS")

public class UserComissariaUsuarios implements Serializable{
	private static final long serialVersionUID = 1L;

	public static final String FILTRO_DELETE_REGISTRO = "delete UserComissariaUsuarios u where u.id = :id";
	public final static String USERS_COMISSARIO_ID = "id";
	public final static String sql_pesquisa_autocomplete = "select p from UserComissariaUsuarios p "
			+ "where p.usuario = :usuario";
	public final static String sql_pesquisa_users_comissarios = "SELECT p FROM UserComissariaUsuarios p where p.comissariaMaster.id = :comissariaMaster";

	public final static String SQL_LISTA_USUARIOS = "select p from UserComissariaUsuarios p where p.user.id = :user";
	
	public final static String CAMPO_USUARIO = "user";
	public final static String CAMPO_COMISSARIAMASTER = "comissariaMaster";
	public final static String CAMPO_IMPORTADOR = "cadimportador";
	
	@Id
	@SequenceGenerator(name = "SEQ_COMISSARIA_USUARIOS_ID", sequenceName = "SEQ_COMISSARIA_USUARIOS_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_COMISSARIA_USUARIOS_ID")
	@Column(name="COMISSARIA_USUARIOS_ID")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "USERS_COMISSARIA_MASTER_ID", nullable = false, insertable = true, updatable = true)
	@ForeignKey(name = "FK_COMISSARIA_USUARIOS_ID")
	private UserComissariaAgendamento comissariaMaster;

	@ManyToOne
	@JoinColumn(name = "USERS_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_COMISSARIA_USUARIOS_USERS")
	private User user;

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
		if (obj instanceof UserComissariaUsuarios) {
			UserComissariaUsuarios item = (UserComissariaUsuarios) obj;
			return item.getId() == id;
		}

		return false;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserComissariaAgendamento getComissariaMaster() {
		return comissariaMaster;
	}

	public void setComissariaMaster(UserComissariaAgendamento comissariaMaster) {
		this.comissariaMaster = comissariaMaster;
	}
}
