package com.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.envers.Audited;

import com.enums.StatusServicos;
import com.enums.StatusSolicitacao;

import seguranca.com.entidade.User;

@Entity
@Table(name= "SOLICITACAO")
@Audited

public class Solicitacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name= "SEQ_SOLICITACAO_ID", sequenceName= "SEQ_SOLICITACAO_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator= "SEQ_SOLICITACAO_ID")
	@Column(name="SOLICITACAO_ID")
	private int id;
	
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;
	
	@Temporal(TemporalType.DATE)
	private Date dataSolicitacao;
	
	@ManyToOne
	@JoinColumn(name="ID_USUARIO", nullable = false, insertable = true, updatable = true)
	@ForeignKey(name="FK_CLIENTE")
	private User cliente;
	
	@Column(name="ID_ULTIMO_RESPONSAVEL")
	private Integer ultResponsavel;
	
	@Enumerated(EnumType.STRING)
	private StatusServicos statusServicos;
	
	@Enumerated(EnumType.STRING)
	private StatusSolicitacao statusSolicitacao;
	
	@OneToMany(fetch= FetchType.LAZY, mappedBy= "solicitacao", cascade = CascadeType.ALL, orphanRemoval= true)
	private List<AnexosSolicitacao> anexos;
	
	@OneToMany(fetch= FetchType.LAZY, mappedBy= "solicitacao", cascade = CascadeType.ALL, orphanRemoval= true)
	@OrderBy("data ASC")
	private List<Mensagem> mensagems;
	
	@OneToMany(fetch= FetchType.LAZY, mappedBy= "solicitacao", cascade = CascadeType.ALL, orphanRemoval= true)
	@OrderBy("data ASC")
	private List<Historico> historico;
	
	public Solicitacao() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public User getCliente() {
		return cliente;
	}

	public void setCliente(User cliente) {
		this.cliente = cliente;
	}

	public Integer getUltResponsavel() {
		return ultResponsavel;
	}

	public void setUltResponsavel(Integer ultResponsavel) {
		this.ultResponsavel = ultResponsavel;
	}

	public StatusServicos getStatusServicos() {
		return statusServicos;
	}

	public void setStatusServicos(StatusServicos statusServicos) {
		this.statusServicos = statusServicos;
	}

	public StatusSolicitacao getStatusSolicitacao() {
		return statusSolicitacao;
	}

	public void setStatusSolicitacao(StatusSolicitacao statusSolicitacao) {
		this.statusSolicitacao = statusSolicitacao;
	}

	public List<AnexosSolicitacao> getAnexos() {
		if(anexos==null) anexos = new ArrayList<AnexosSolicitacao>();
		return anexos;
	}

	public void setAnexos(List<AnexosSolicitacao> anexos) {
		this.anexos = anexos;
	}

	public List<Mensagem> getMensagems() {
		if(mensagems == null) mensagems= new ArrayList<Mensagem>();
		return mensagems;
	}

	public void setMensagems(List<Mensagem> mensagems) {
		this.mensagems = mensagems;
	}

	public List<Historico> getHistorico() {
		if(historico == null) historico = new ArrayList<Historico>();
		return historico;
	}

	public void setHistorico(List<Historico> historico) {
		this.historico = historico;
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

	public void addHistorico(Historico historico) {
		this.historico.add(historico);
	}
	
	public void addAnexo(AnexosSolicitacao anexosSolicitacao) {
		this.anexos.add(anexosSolicitacao);
	}
	public void removeAnexo(AnexosSolicitacao anexosSolicitacao) {
		this.anexos.remove(anexosSolicitacao);
	}
	


}
