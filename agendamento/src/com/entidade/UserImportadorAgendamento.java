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
@Table(name="CAD_USERS_IMPORTADOR")

public class UserImportadorAgendamento implements Serializable{
	private static final long serialVersionUID = 1L;

	public static final String FILTRO_DELETE_REGISTRO = "delete UserImportadorAgendamento u where u.id = :id";
	public final static String USERS_COMISSARIO_ID = "id";
	public final static String sql_pesquisa_autocomplete = "select p from UserImportadorAgendamento p "
			+ "where p.usuario = :usuario";
	public final static String sql_pesquisa_users_comissarios = "SELECT p FROM UserImportadorAgendamento p where p.importador.id = :importador";

	public final static String SQL_LISTA_USUARIOS = "select p from UserImportadorAgendamento p where p.usuario.id = :usuario";
	
	public final static String CAMPO_USUARIO = "usuario";
	public final static String CAMPO_IMPORTADOR = "cadimportador";
	
	@Id
	@SequenceGenerator(name = "SEQ_USERS_IMPORTADOR_MASTER_ID", sequenceName = "SEQ_USERS_IMPORTADOR_MASTER_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_USERS_IMPORTADOR_MASTER_ID")
	@Column(name="USERS_IMPORTADOR_MASTER_ID")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "ID_USUARIO", nullable = false, insertable = true, updatable = true, unique=true )
	@ForeignKey(name = "FK_CAD_USR_IMPORTADOR")
	private User usuario;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "importadorComissaria", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserImportadorComissaria> listaImportadorComissaria;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "importadorComissaria", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserImportadorUsuarios> listaImportadorUsuarios;

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
		if (obj instanceof UserImportadorAgendamento) {
			UserImportadorAgendamento item = (UserImportadorAgendamento) obj;
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
	
	public List<UserImportadorComissaria> getListaImportadorComissaria() {
		if (listaImportadorComissaria == null) {
			listaImportadorComissaria = new ArrayList<UserImportadorComissaria>();
		}
		return listaImportadorComissaria;
	}
	
	public void setListaImportadorComissaria(List<UserImportadorComissaria> listaImportadorComissaria) {
		this.listaImportadorComissaria = listaImportadorComissaria;
	}

	public List<UserImportadorUsuarios> getListaImportadorUsuarios() {
		if (listaImportadorUsuarios == null) {
			listaImportadorUsuarios = new ArrayList<UserImportadorUsuarios>();
		}
		return listaImportadorUsuarios;
	}

	public void setListaImportadorUsuarios(List<UserImportadorUsuarios> listaImportadorUsuarios) {
		this.listaImportadorUsuarios = listaImportadorUsuarios;
	}
}
