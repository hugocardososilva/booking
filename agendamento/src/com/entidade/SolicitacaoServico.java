package com.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.envers.Audited;

import com.enums.StatusServicos;

import seguranca.com.entidade.CadastroBLContanier;

@Entity
@Table(name="SOLICITACAO_SEERVICO")
@Audited
public class SolicitacaoServico implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_SOL_SERV_ID", sequenceName = "SEQ_SOL_SERV_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_SOL_SERV_ID")
	private Integer id;
	
	private String outrosServicos;
	
	@ManyToOne
	@JoinColumn(name="CONTAINER_ID", insertable= true, updatable= true, nullable = false)
	@ForeignKey(name="FK_CONTAINER_ID")
	private CadastroBLContanier container;
	
	@ManyToOne
	@JoinColumn(name="SERVICO_ID", insertable = true, updatable = true, nullable = false)
	@ForeignKey(name="FK_SERVICO_ID")
	private Servico servico;
	
	@ManyToOne
	@JoinColumn(name="SOLICITACAO_ID", insertable= true, updatable= true, nullable = false)
	@ForeignKey(name="FK_SOLICITACAO_ID")
	private Solicitacao solicitacao;
	
	@Enumerated(EnumType.STRING)
	private StatusServicos statusServicos;
	
	
	public SolicitacaoServico() {
		// TODO Auto-generated constructor stub
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getOutrosServicos() {
		return outrosServicos;
	}


	public void setOutrosServicos(String outrosServicos) {
		this.outrosServicos = outrosServicos;
	}


	public CadastroBLContanier getContainer() {
		return container;
	}


	public void setContainer(CadastroBLContanier container) {
		this.container = container;
	}


	public Servico getServico() {
		return servico;
	}


	public void setServico(Servico servico) {
		this.servico = servico;
	}


	public Solicitacao getSolicitacao() {
		return solicitacao;
	}


	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}
	public StatusServicos getStatusServicos() {
		return statusServicos;
	}

	public void setStatusServicos(StatusServicos statusServicos) {
		this.statusServicos = statusServicos;
	}
	

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SolicitacaoServico) {
			SolicitacaoServico serv = (SolicitacaoServico) obj;
			return serv.getId() == id;
		}

		return false;
	}


}
