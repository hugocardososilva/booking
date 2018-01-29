package com.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.envers.Audited;

import seguranca.com.entidade.User;

@Entity
@Table(name="HISTORICO_SOLICITACAO")
@Audited
public class Historico implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_HISTORICO_ID", sequenceName = "SEQ_HISTORICO_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_HISTORICO_ID")
	@Column(name="HISTORICO_ID")
	private Integer id;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name ="SOLICITACAO_ID", nullable = true, insertable= true, updatable = true)
	@ForeignKey(name="FK_SOLICITACAO_ID")
	private Solicitacao solicitacao;
	
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_USUARIO_ID")
	private User usuario;
	
	public Historico() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}
	
	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Historico) {
			Historico his = (Historico) obj;
			return his.getId() == id;
		}

		return false;
	}


}
