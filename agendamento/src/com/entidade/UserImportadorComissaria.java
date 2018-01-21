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

import seguranca.com.entidade.CadastroComissario;
import seguranca.com.entidade.CadastroImportador;
import seguranca.com.entidade.CadastroResponsavelLote;

/**
 * Class representa os importadores que poderão visualizar
 * 
 * @author murilonadalin
 * @since 13-09-2017
 */

@Entity
@Audited
@Table(name = "CAD_USER_IMPORTADOR_COMISSARIA")

public class UserImportadorComissaria implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FILTRO_DELETE_REGISTRO = "delete UserImportadorComissaria u where u.id = :id";
	public final static String USERS_COMISSARIO_ID = "id";
	public final static String sql_pesquisa_autocomplete = "select p from UserImportadorComissaria p "
			+ "where p.usuario = :usuario";
	public final static String sql_pesquisa_users_comissarios = "SELECT p FROM UserImportadorComissaria p where p.importador.id = :importador";

	public final static String SQL_LISTA_USUARIOS = "select p from UserImportadorComissaria p where p.usuario.id = :usuario";
	public final static String SQL_LISTA_COMISSARIAS = "select p from UserImportadorComissaria p where p.importadorComissaria.id = :importadorComissaria";
	public final static String SQL_LISTA_POR_COMISSARIAS = "select p from UserImportadorComissaria p where p.comissaria.id = :comissaria order by p.importadorComissaria.id";

	public final static String CAMPO_USUARIO = "usuario";
	public final static String CAMPO_IMPORTADOR = "cadimportador";
	public final static String CAMPO_COMISSARIA = "comissaria";
	public final static String CAMPO_IMPORTADORCOMISSARIA = "importadorComissaria";

	@Id
	@SequenceGenerator(name = "SEQ_IMPORTADOR_COMISSARIA_ID", sequenceName = "SEQ_IMPORTADOR_COMISSARIA_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_IMPORTADOR_COMISSARIA_ID")

	@Column(name = "IMPORTADOR_COMISSARIA_ID")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "USERS_IMPORTADOR_MASTER_ID", nullable = false, insertable = true, updatable = true)
	@ForeignKey(name = "FK_IMP_COMISSARIA_ID")
	private UserImportadorAgendamento importadorComissaria;

	@ManyToOne
	@JoinColumn(name = "IMPORTADOR_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_IMP_COMISSARIA_IMPORTADOR")
	private CadastroImportador importador;

	@ManyToOne
	@JoinColumn(name = "COMISSARIO_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_IMP_COMISSARIA_COMISSARIA")
	private CadastroComissario comissaria;

	@ManyToOne
	@JoinColumn(name = "RESPONSAVEL_LOTE_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_IMP_COMISSARIA_NOTIFY")
	private CadastroResponsavelLote notifyRespLote;

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
		if (obj instanceof UserImportadorComissaria) {
			UserImportadorComissaria item = (UserImportadorComissaria) obj;
			return item.getId() == id;
		}

		return false;
	}

	public UserImportadorAgendamento getImportadorComissaria() {
		return importadorComissaria;
	}

	public void setImportadorComissaria(UserImportadorAgendamento importadorComissaria) {
		this.importadorComissaria = importadorComissaria;
	}

	public CadastroImportador getImportador() {
		return importador;
	}

	public void setImportador(CadastroImportador importador) {
		this.importador = importador;
	}

	public CadastroComissario getComissaria() {
		return comissaria;
	}

	public void setComissaria(CadastroComissario comissaria) {
		this.comissaria = comissaria;
	}

	public CadastroResponsavelLote getNotifyRespLote() {
		return notifyRespLote;
	}

	public void setNotifyRespLote(CadastroResponsavelLote notifyRespLote) {
		this.notifyRespLote = notifyRespLote;
	}
}
