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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;




@Entity
@Audited
@Table(name="SERVICO")
public class Servico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static String sql= "select s from Servico s";
	public final static String CAMPO_NOME= "nome";
	public final static String sql_pesquisa_autocomplete= "select s from Servico s where s.nome like :nome";
	public final static String sqlCount= "select COUNT(s) from Servico s";
	
	@Id
	@SequenceGenerator(name = "SEQ_SERVICOS_ID", sequenceName = "SEQ_SERVICOS_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_SERVICOS_ID")
	private Integer id;
	
	@Column(name="NOME", length = 150)
	private String nome;
	
	private String descricao;
	
	@Column(name="VALIDAR_NCM" , columnDefinition = "int default 0")
	private boolean validarNCM;
	
	@Column(name="VALIDAR_DEMURRAGE" , columnDefinition = "int default 0")
	private boolean validarDemurrage;
	
	@Column(name="CODIGO_NCM", nullable= true)
	private Integer codigoNCM;
	
	@Column(name="JANELA_CAPACIDADE" , columnDefinition = "int default 0")
	private boolean janelaCapacidade;
	
	private char unMedidaJanela;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "servico", cascade = CascadeType.ALL, orphanRemoval = true)
	
	private List<ServicoJanelaAtendimento> servicoJanelaAtendimentos;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "servico", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SolicitacaoServico> solicitacaoServicos;
	
	public Servico() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isValidarNCM() {
		return validarNCM;
	}

	public void setValidarNCM(boolean validarNCM) {
		this.validarNCM = validarNCM;
	}
	
	public boolean isValidarDemurrage() {
		return validarDemurrage;
	}

	public void setValidarDemurrage(boolean validarDemurrage) {
		this.validarDemurrage = validarDemurrage;
	}

	public boolean isJanelaCapacidade() {
		return janelaCapacidade;
	}

	public void setJanelaCapacidade(boolean janelaCapacidade) {
		this.janelaCapacidade = janelaCapacidade;
	}

	public char getUnMedidaJanela() {
		return unMedidaJanela;
	}

	public void setUnMedidaJanela(char unMedidaJanela) {
		this.unMedidaJanela = unMedidaJanela;
	}

	public List<ServicoJanelaAtendimento> getServicoJanelaAtendimentos() {
		if(servicoJanelaAtendimentos == null) {
			servicoJanelaAtendimentos = new ArrayList<ServicoJanelaAtendimento>();
		}
		return servicoJanelaAtendimentos;
	}

	public void setServicoJanelaAtendimentos(List<ServicoJanelaAtendimento> servicoJanelaAtendimentos) {
		this.servicoJanelaAtendimentos = servicoJanelaAtendimentos;
	}

	public List<SolicitacaoServico> getSolicitacaoServicos() {
		if(solicitacaoServicos == null) {
			solicitacaoServicos = new ArrayList<SolicitacaoServico>();
		}
		return solicitacaoServicos;
	}

	public void setSolicitacaoServicos(List<SolicitacaoServico> solicitacaoServicos) {
		this.solicitacaoServicos = solicitacaoServicos;
	}
	
	public Integer getCodigoNCM() {
		return codigoNCM;
	}

	public void setCodigoNCM(Integer codigoNCM) {
		this.codigoNCM = codigoNCM;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Servico) {
			Servico serv = (Servico) obj;
			return serv.getId() == id;
		}

		return false;
	}

	

}
