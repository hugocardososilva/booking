package com.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.joda.time.DateTime;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleModel;

import com.entidade.AnexosSolicitacao;
import com.entidade.Historico;
import com.entidade.JanelaAtendimento;
import com.entidade.Mensagem;
import com.entidade.Servico;
import com.entidade.ServicoJanelaAtendimento;
import com.entidade.Solicitacao;
import com.entidade.SolicitacaoServico;
import com.enums.StatusServicos;
import com.enums.StatusSolicitacao;
import com.lazy.SolicitacaoLazyDataModel;
import com.rn.SolicitacaoRN;
import com.util.JSFMessageUtil;

import conexao.com.bean.AbstractMB;
import conexao.com.util.IGenericBean;

import seguranca.com.entidade.CadastroBLContanier;
import seguranca.com.entidade.User;

@ViewScoped
@ManagedBean
public class SolicitacaoBean extends AbstractMB implements Serializable {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	private LazyDataModel<Solicitacao> lazyModel;
	
	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;
	
	private boolean controlarFormListar;
	private boolean controlarFormCadastrar;
	private boolean controlarFormEditar;
	
	private String descricaoOutros;
	
	private List<CadastroBLContanier> containers;
	private List<Servico> servicos;
	
	private Servico servico;
	private Solicitacao solicitacao;
	private SolicitacaoServico solicitacaoServico;
	private AnexosSolicitacao anexosSolicitacao;
	private Historico historico;
	private Mensagem mensagem;
	private Date dataSolicitacao;
	
	private User cliente;
	
	private List<User> clientes;
	
	 private ScheduleModel lazyEventServicoJanela;
	 private List<ServicoJanelaAtendimento> servicoJanelaAtendimentos;
	 
	 
	 private SolicitacaoRN rn;
	 
	 //filtros
	 private Date dataCadastroInicio;
	 private Date dataCadastroFim;
	 private Date dataSolicitacaoInicio;
	 private Date dataSolicitacaoFim;
	 
	 private Map<String, Object> filtros;
	 
	 private List<Integer> filtroStatusSolicitacao;
	 private List<Integer> filtroStatusServicos;
	 
	 private StatusSolicitacao statusSolicitacao;
	 private StatusServicos statusServicos;
	 
	 
	
	
	
	
	
	@PostConstruct
	public void init() {
		setControlarFormCadastrar(false);
		setControlarFormEditar(false);
		setControlarFormListar(true);
		filtros();
		
	}
	public void editarEntidade() {
		setControlarFormCadastrar(true);
		setControlarFormEditar(true);
		setControlarFormListar(false);
	}
	public void filtros() {
		try {
			
		
		if(dataCadastroInicio!=null || dataCadastroFim != null ) {
			if(dataCadastroInicio.after(dataCadastroFim)) {
				JSFMessageUtil.adicionarMensagemErro(" A data 'De:' não pode ser mais que a data 'Até:' ");
				return;
			}else {
				Map<String, Object> filtrosDataCadastro = new HashMap<>();
				filtrosDataCadastro.put("dataCadastroInicio", dataCadastroInicio);
				filtrosDataCadastro.put("dataCadastroFim", dataCadastroFim);
				getFiltros().put("filtrosDataCadastro", filtrosDataCadastro);
			}
		}	
			if(dataSolicitacaoInicio != null || dataSolicitacaoFim != null) {
				if(dataSolicitacaoInicio.after(dataSolicitacaoFim)) {
					JSFMessageUtil.adicionarMensagemErro(" A data 'De:' não pode ser mais que a data 'Até:' ");
					return;
				}else {
					Map<String, Object> filtrosDataSolicitacao = new HashMap<>();
					filtrosDataSolicitacao.put("dataSolicitacaoInicio", dataSolicitacaoInicio);
					filtrosDataSolicitacao.put("dataSolicitacaoFim", dataSolicitacaoFim);
					getFiltros().put("filtrosDataSolicitacao", filtrosDataSolicitacao);
				}
				
			}
			
			if(!getFiltroStatusServicos().isEmpty()) {
				getFiltros().put("filtroStatusServicos", getFiltroStatusServicos());
			}
			if(!getFiltroStatusSolicitacao().isEmpty()) {
				getFiltros().put("filtroStatusSolicitacao", getFiltroStatusSolicitacao());
			}
			if(cliente != null) {
				getFiltros().put("cliente", cliente.getId());
			}
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//método que insere o cliente no filtro
	public void filtrarCliente(SelectEvent event) {
		cliente = (User)event.getObject();
		
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
	public void novaSolicitacao() {
		
	}
	
	public List<User> pesquisaCliente(String pesquisa){
		List<User> list = new ArrayList<>();
		try {
			list = getRn().pesquisarUsuarioAutoCompletePorCliente(pesquisa);
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro("Aconteceu um problema ao carregar a lista de clientes.");
		}
		return list;
	}
	
	
	
	public Servico getServico() {
		if(servico== null) servico = new Servico();
		return servico;
	}
	public LazyDataModel<Solicitacao> getLazyModel() {
		if(lazyModel == null) lazyModel = new SolicitacaoLazyDataModel(getFiltros());
		return lazyModel;
	}
	public void setLazyModel(LazyDataModel<Solicitacao> lazyModel) {
		this.lazyModel = lazyModel;
	}
	public boolean isControlarFormListar() {
		return controlarFormListar;
	}
	public void setControlarFormListar(boolean controlarFormListar) {
		this.controlarFormListar = controlarFormListar;
	}
	public boolean isControlarFormCadastrar() {
		return controlarFormCadastrar;
	}
	public void setControlarFormCadastrar(boolean controlarFormCadastrar) {
		this.controlarFormCadastrar = controlarFormCadastrar;
	}
	public boolean isControlarFormEditar() {
		return controlarFormEditar;
	}
	public void setControlarFormEditar(boolean controlarFormEditar) {
		this.controlarFormEditar = controlarFormEditar;
	}
	public String getDescricaoOutros() {
		return descricaoOutros;
	}
	public void setDescricaoOutros(String descricaoOutros) {
		this.descricaoOutros = descricaoOutros;
	}
	public List<CadastroBLContanier> getContainers() {
		return containers;
	}
	public void setContainers(List<CadastroBLContanier> containers) {
		this.containers = containers;
	}
	public List<Servico> getServicos() {
		if(servicos == null) servicos = new ArrayList<>();
		return servicos;
	}
	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}
	public Solicitacao getSolicitacao() {
		if(solicitacao == null) solicitacao = new Solicitacao();
		return solicitacao;
	}
	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}
	public SolicitacaoServico getSolicitacaoServico() {
		if(solicitacaoServico == null) solicitacaoServico = new SolicitacaoServico();
		return solicitacaoServico;
	}
	public void setSolicitacaoServico(SolicitacaoServico solicitacaoServico) {
		this.solicitacaoServico = solicitacaoServico;
	}
	public AnexosSolicitacao getAnexosSolicitacao() {
		if(anexosSolicitacao == null) anexosSolicitacao = new AnexosSolicitacao();
		return anexosSolicitacao;
	}
	public void setAnexosSolicitacao(AnexosSolicitacao anexosSolicitacao) {
		this.anexosSolicitacao = anexosSolicitacao;
	}
	public Historico getHistorico() {
		if(historico == null) historico = new Historico();
		return historico;
	}
	public void setHistorico(Historico historico) {
		this.historico = historico;
	}
	public Mensagem getMensagem() {
		if(mensagem == null) mensagem = new Mensagem();
		return mensagem;
	}
	public void setMensagem(Mensagem mensagem) {
		this.mensagem = mensagem;
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
	public List<User> getClientes() {
		return clientes;
	}
	public void setClientes(List<User> clientes) {
		this.clientes = clientes;
	}
	
	public List<ServicoJanelaAtendimento> getServicoJanelaAtendimentos() {
		return servicoJanelaAtendimentos;
	}
	public void setServicoJanelaAtendimentos(List<ServicoJanelaAtendimento> servicoJanelaAtendimentos) {
		this.servicoJanelaAtendimentos = servicoJanelaAtendimentos;
	}
	public SolicitacaoRN getRn() {
		if(rn==null) rn = new SolicitacaoRN();
		return rn;
	}
	public void setRn(SolicitacaoRN rn) {
		this.rn = rn;
	}
	public Date getDataCadastroInicio() {
		return dataCadastroInicio;
	}
	public void setDataCadastroInicio(Date dataCadastroInicio) {
		this.dataCadastroInicio = dataCadastroInicio;
	}
	public Date getDataCadastroFim() {
		return dataCadastroFim;
	}
	public void setDataCadastroFim(Date dataCadastroFim) {
		this.dataCadastroFim = dataCadastroFim;
	}
	public Date getDataSolicitacaoInicio() {
		return dataSolicitacaoInicio;
	}
	public void setDataSolicitacaoInicio(Date dataSolicitacaoInicio) {
		this.dataSolicitacaoInicio = dataSolicitacaoInicio;
	}
	public Date getDataSolicitacaoFim() {
		return dataSolicitacaoFim;
	}
	public void setDataSolicitacaoFim(Date dataSolicitacaoFim) {
		this.dataSolicitacaoFim = dataSolicitacaoFim;
	}
	public Map<String, Object> getFiltros() {
		if(filtros == null) filtros = new HashMap<String, Object>();
		return filtros;
	}
	public void setFiltros(Map<String, Object> filtros) {
		this.filtros = filtros;
	}
	public List<Integer> getFiltroStatusSolicitacao() {
		if(filtroStatusSolicitacao == null) filtroStatusSolicitacao = new ArrayList<>();
		return filtroStatusSolicitacao;
	}
	public void setFiltroStatusSolicitacao(List<Integer> filtroStatusSolicitacao) {
		this.filtroStatusSolicitacao = filtroStatusSolicitacao;
	}
	public List<Integer> getFiltroStatusServicos() {
		if(filtroStatusServicos == null) filtroStatusServicos = new ArrayList<>();
		return filtroStatusServicos;
	}
	public void setFiltroStatusServicos(List<Integer> filtroStatusServicos) {
		this.filtroStatusServicos = filtroStatusServicos;
	}
	public StatusSolicitacao[] getStatusSolicitacao() {
		return statusSolicitacao.values();
	}
	public void setStatusSolicitacao(StatusSolicitacao statusSolicitacao) {
		this.statusSolicitacao = statusSolicitacao;
	}
	public StatusServicos[] getStatusServicos() {
		return statusServicos.values();
	}
	public void setStatusServicos(StatusServicos statusServicos) {
		this.statusServicos = statusServicos;
	}
	public UserMB getUserMB() {
		return userMB;
	}
	
	public void setUserMB(UserMB userMB) {
		this.userMB = userMB;
	}
	public void setServico(Servico servico) {
		this.servico = servico;
	}

	
}
