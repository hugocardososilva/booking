package com.entidade;

import java.io.Serializable;
import java.util.Date;

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


@Entity
@Table(name="SERVICO_JANELA_ATENDIMENTO")
@Audited
public class ServicoJanelaAtendimento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@SequenceGenerator(name = "SEQ_SJATENDIMENTO_ID", sequenceName = "SEQ_SJATENDIMENTO_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_SJATENDIMENTO_ID")
	private Integer id;
	
	private Integer capacidadeDia;
	
	@Temporal(TemporalType.TIME)
	private Date capacidadeHora;
	
	@Temporal(TemporalType.TIME)
	private Date inicio;
	
	@Temporal(TemporalType.TIME)
	private Date fim;
	
	private Integer capacidadePeriodo;
	
	@ManyToOne
	@JoinColumn(name="JANELAATENDIMENDO_ID", insertable = true, updatable= true, nullable = true)
	@ForeignKey(name="FK_JANELAATENDIMENTO_ID")
	private JanelaAtendimento janelaAtendimento;
	
	@ManyToOne
	@JoinColumn(name="SERVICO_ID", insertable= true, updatable = true, nullable = true)
	@ForeignKey(name="FK_SERVICO_ID")
	private Servico servico;
	
	
	public ServicoJanelaAtendimento() {
		// TODO Auto-generated constructor stub
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getCapacidadeDia() {
		return capacidadeDia;
	}


	public void setCapacidadeDia(Integer capacidadeDia) {
		this.capacidadeDia = capacidadeDia;
	}


	public Date getCapacidadeHora() {
		return capacidadeHora;
	}


	public void setCapacidadeHora(Date capacidadeHora) {
		this.capacidadeHora = capacidadeHora;
	}


	public Date getInicio() {
		return inicio;
	}


	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}


	public Date getFim() {
		return fim;
	}


	public void setFim(Date fim) {
		this.fim = fim;
	}


	public Integer getCapacidadePeriodo() {
		return capacidadePeriodo;
	}


	public void setCapacidadePeriodo(Integer capacidadePeriodo) {
		this.capacidadePeriodo = capacidadePeriodo;
	}


	public JanelaAtendimento getJanelaAtendimento() {
		return janelaAtendimento;
	}


	public void setJanelaAtendimento(JanelaAtendimento janelaAtendimento) {
		this.janelaAtendimento = janelaAtendimento;
	}


	public Servico getServico() {
		return servico;
	}


	public void setServico(Servico servico) {
		this.servico = servico;
	}
	
	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ServicoJanelaAtendimento) {
			ServicoJanelaAtendimento sja = (ServicoJanelaAtendimento) obj;
			return sja.getId() == id;
		}

		return false;
	}
	
	

}
