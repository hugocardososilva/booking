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

@Entity
@Table(name="MENSAGEM_SOLICITACAO")
@Audited
public class AnexosSolicitacao implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_ANEXO_SOLICITACAO_ID", sequenceName = "SEQ_ANEXO_SOLICITACAO_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_ANEXO_SOLICITACAO_ID")
	@Column(name="ANEXO_SOLICITACAO_ID")
	private Integer id;
	
	private String caminhoAnexo;
	
	@ManyToOne
	@JoinColumn(name ="SOLICITACAO_ID", nullable = true, insertable= true, updatable = true)
	@ForeignKey(name="FK_SOLICITACAO_ID")
	private Solicitacao solicitacao;
	
	public AnexosSolicitacao() {
		// TODO Auto-generated constructor stub
	}
		
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCaminhoAnexo() {
		return caminhoAnexo;
	}
	public void setCaminhoAnexo(String caminhoAnexo) {
		this.caminhoAnexo = caminhoAnexo;
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
		if (obj instanceof AnexosSolicitacao) {
			AnexosSolicitacao an = (AnexosSolicitacao) obj;
			return an.getId() == id;
		}

		return false;
	}

	
	

}
