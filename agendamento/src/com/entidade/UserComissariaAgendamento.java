package com.entidade;

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

import seguranca.com.entidade.User;

/**
 * Class representa os importadores que poderão visualizar
 * @author murilonadalin
 * @since 13-09-2017
 */

@Entity
@Audited
@Table(name="CAD_USERS_COMISSARIA")

public class UserComissariaAgendamento implements Serializable{
	private static final long serialVersionUID = 1L;

	public static final String FILTRO_DELETE_REGISTRO = "delete UserComissariaAgendamento u where u.id = :id";
	public final static String USERS_COMISSARIO_ID = "id";
	public final static String sql_pesquisa_autocomplete = "select p from UserComissariaAgendamento p "
			+ "where p.usuario = :usuario";
	public final static String sql_pesquisa_users_comissarios = "SELECT p FROM UserComissariaAgendamento p where p.importador.id = :importador";

	public final static String SQL_LISTA_USUARIOS = "select p from UserComissariaAgendamento p where p.usuario.id = :usuario";
	
	public final static String CAMPO_USUARIO = "usuario";
	public final static String CAMPO_IMPORTADOR = "cadimportador";
	
	@Id
	@SequenceGenerator(name = "SEQ_USERS_COMISSARIA_MASTER_ID", sequenceName = "SEQ_USERS_COMISSARIA_MASTER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_USERS_COMISSARIA_MASTER_ID")
	@Column(name="USERS_COMISSARIA_MASTER_ID")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO", nullable = false, insertable = true, updatable = true, unique=true )
	@ForeignKey(name = "FK_CAD_USR_COMISSARIA")
	private User usuario;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "comissariaMaster", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserComissariaUsuarios> listaComissariaUsuarios;

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
		if (obj instanceof UserComissariaAgendamento) {
			UserComissariaAgendamento item = (UserComissariaAgendamento) obj;
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

	public List<UserComissariaUsuarios> getListaComissariaUsuarios() {
		if (listaComissariaUsuarios == null) {
			listaComissariaUsuarios = new ArrayList<UserComissariaUsuarios>();
		}
		return listaComissariaUsuarios;
	}

	public void setListaComissariaUsuarios(List<UserComissariaUsuarios> listaComissariaUsuarios) {
		this.listaComissariaUsuarios = listaComissariaUsuarios;
	}
	
}
