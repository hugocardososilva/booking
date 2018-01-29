package com.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;


@Entity
@Audited
@Table(name="JANELA_ATENDIMENTO")
public class JanelaAtendimento implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "SEQ_JANELA_ATENDIMENTO_ID", sequenceName = "SEQ_JANELA_ATENDIMENTO_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_JANELA_ATENDIMENTO_ID")
	private Integer id;
	
	@Temporal(TemporalType.TIME)
	private Date inicioDia;
	
	@Temporal(TemporalType.TIME)
	private Date terminoDia;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	
	@OneToMany(fetch= FetchType.LAZY, mappedBy="janelaAtendimento", cascade= CascadeType.ALL, orphanRemoval = true)
	private List<ServicoJanelaAtendimento> servicoJanelaAtendimentos;
	
	public JanelaAtendimento() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInicioDia() {
		return inicioDia;
	}

	public void setInicioDia(Date inicioDia) {
		this.inicioDia = inicioDia;
	}

	public Date getTerminoDia() {
		return terminoDia;
	}

	public void setTerminoDia(Date terminoDia) {
		this.terminoDia = terminoDia;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public List<ServicoJanelaAtendimento> getServicoJanelaAtendimentos() {
		if(servicoJanelaAtendimentos == null) {
			servicoJanelaAtendimentos= new ArrayList<ServicoJanelaAtendimento>();
		}
		return servicoJanelaAtendimentos;
	}

	public void setServicoJanelaAtendimentos(List<ServicoJanelaAtendimento> servicoJanelaAtendimentos) {
		this.servicoJanelaAtendimentos = servicoJanelaAtendimentos;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof JanelaAtendimento) {
			JanelaAtendimento ja = (JanelaAtendimento) obj;
			return ja.getId() == id;
		}

		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return id;
	}
	
	
	

}
