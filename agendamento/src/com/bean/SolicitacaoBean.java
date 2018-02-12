package com.bean;


import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.joda.time.DateTime;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleModel;

import com.entidade.AnexosSolicitacao;
import com.entidade.Historico;
import com.entidade.JanelaAtendimento;
import com.entidade.Mensagem;
import com.entidade.Servico;
import com.entidade.ServicoJanelaAtendimento;
import com.rn.SolicitacaoRN;
import com.util.JSFMessageUtil;

import seguranca.com.entidade.CadastroBLContanier;
import seguranca.com.entidade.User;

@ViewScoped
@ManagedBean
public class SolicitacaoBean {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	private LazyDataModel<JanelaAtendimento> lazyModel;
	
	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;
	
	private boolean controlarFormListar;
	private boolean controlarFormCadastrar;
	private boolean controlarFormEditar;
	
	private String descricaoOutros;
	
	private List<CadastroBLContanier> containers;
	private List<Servico> servicos;
	
	private Servico servico;
	private AnexosSolicitacao anexosSolicitacao;
	private Historico historico;
	private Mensagem mensagem;
	private Date dataSolicitacao;
	
	private User cliente;
	
	private List<User> clientes;
	
	 private ScheduleModel lazyEventServicoJanela;
	 private List<ServicoJanelaAtendimento> servicoJanelaAtendimentos;
	 
	 private SolicitacaoRN rn;
	
	
	
	
	
	@PostConstruct
	public void init() {
		
	}
	
	public void carregarEventosServicoJanela() {
		try {
			
		
		lazyEventServicoJanela = new LazyScheduleModel() {
			
			@Override
            public void loadEvents(Date start, Date end) {
				DateTime inicio = new DateTime(start);
				DateTime fim = new DateTime(end);
				for(DateTime data= inicio; data.isBefore(fim); data = data.plusDays(1)) {
					addEvent(rn.getEventosCalendario(data.toDate(),getServico()));
				}
				
			}
		
		};
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro("Ouve um problema na operação!");
		}
	}
	
	
	
	public Servico getServico() {
		if(servico== null) servico = new Servico();
		return servico;
	}
	
	
}
