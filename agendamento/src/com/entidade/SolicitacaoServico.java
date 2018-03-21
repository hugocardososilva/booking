package com.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	public static final String sql ="Select s from SolicitacaoServico s";
	
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
	
	@Temporal(TemporalType.DATE)
	private Date dataSolicitacao;
	
	private String OS;
	
	@Temporal(TemporalType.DATE)
	private Date dataInicioOS;
	
	@Temporal(TemporalType.DATE)
	private Date dataTerminoOS;
	
	@Column(columnDefinition = "int default 0")
	private boolean encaixe;
	
	
	public SolicitacaoServico() {
		// TODO Auto-generated constructor stub
	}
	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}
	

	public String getOS() {
		return OS;
	}
	public void setOS(String oS) {
		OS = oS;
	}
	public String getOutrosServicos() {
		return outrosServicos;
	}


	public void setOutrosServicos(String outrosServicos) {
		this.outrosServicos = outrosServicos;
	}
	

	public Date getDataInicioOS() {
		return dataInicioOS;
	}
	public void setDataInicioOS(Date dataInicioOS) {
		this.dataInicioOS = dataInicioOS;
	}
	public Date getDataTerminoOS() {
		return dataTerminoOS;
	}
	public void setDataTerminoOS(Date dataTerminoOS) {
		this.dataTerminoOS = dataTerminoOS;
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

	
	public boolean isEncaixe() {
		return encaixe;
	}
	public void setEncaixe(boolean encaixe) {
		this.encaixe = encaixe;
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
