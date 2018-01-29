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
@Table(name="MENSAGEM_SOLICITACAO")
@Audited
public class Mensagem implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_MENSAGEM_ID", sequenceName = "SEQ_MENSAGEM_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_MENSAGEM_ID")
	@Column(name="MENSAGEM_ID")
	private Integer id;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	

	private String conteudo;
	
	@ManyToOne
	@JoinColumn(name = "ID_USUARIO", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_USUARIO_ID")
	private User usuario;
	
	@ManyToOne
	@JoinColumn(name ="SOLICITACAO_ID", nullable = true, insertable= true, updatable = true)
	@ForeignKey(name="FK_SOLICITACAO_ID")
	private Solicitacao solicitacao;
	
	public Mensagem() {
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



	public String getConteudo() {
		return conteudo;
	}



	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}



	public User getUsuario() {
		return usuario;
	}



	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}



	public Solicitacao getSolicitacao() {
		return solicitacao;
	}



	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}



	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Solicitacao) {
			Solicitacao sol = (Solicitacao) obj;
			return sol.getId() == id;
		}

		return false;
	}

	

}
