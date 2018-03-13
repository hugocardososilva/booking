package com.bean;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.commons.mail.EmailException;
import org.hibernate.sql.Select;
import org.joda.time.DateTime;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.StreamedContent;

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
import com.rn.JanelaAtendimentoRN;
import com.rn.SolicitacaoRN;

import com.util.JSFMessageUtil;

import conexao.com.bean.AbstractMB;
import conexao.com.enums.MenssagensUtilEnum;
import conexao.com.util.IGenericBean;
import conexao.com.util.JSFUtil;
import seguranca.com.entidade.CadastroBLAnexos;
import seguranca.com.entidade.CadastroBLContanier;
import seguranca.com.entidade.User;
import seguranca.com.enums.TipoAnexosEnum;

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
	
	private boolean outroServico;
	
	private String descricaoOutros;
	
	private CadastroBLContanier container;
	private List<CadastroBLContanier> containers;
	private List<CadastroBLContanier> containersSelecionados;
	private List<Servico> servicos;
	
	private JSFUtil jsfUtil;
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
	 private List<SolicitacaoServico> solicitacaoServicos;
	 
	 
	 private SolicitacaoRN rn;
	 private JanelaAtendimentoRN janelaAtendimentoRN;
	 
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
	 
	 public AnexosSolicitacao anexo;
	 private StreamedContent pdf;
	 
	 
	
	
	

	@PostConstruct
	public void init() {
		setControlarFormCadastrar(false);
		setControlarFormEditar(false);
		setControlarFormListar(true);
		filtros();
		carregarEventosServicoJanela();
		
		
	}
	public void editarEntidade() {
		
		try {
			setControlarFormCadastrar(true);
			setControlarFormEditar(true);
			setControlarFormListar(false);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro("Ouve um problema na operação!");
		}
	
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
	public void selecionarServico(SelectEvent event) {
		//TODO metodo que pesquisa se o servico tem ou não os requisitos para abrir a janela de atendimento outro serviço
		servico = (Servico)event.getObject();
		if(getServico().isJanelaCapacidade()) {
			carregarEventosServicoJanela();
		}
		if(getServico().getUnMedidaJanela() ==  "O".charAt(0)) {
			outroServico = true;
		}
		
	}
	
	public void carregarEventosServicoJanela() {
		try {
			
		
		lazyEventServicoJanela = new LazyScheduleModel() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public void loadEvents(Date start, Date end) {
				DateTime inicio = new DateTime(start);
				DateTime fim = new DateTime(end);
				for(DateTime data= inicio; data.isBefore(fim); data = data.plusDays(1)) {
					if(!data.isBefore(new DateTime()) && getServico().isJanelaCapacidade()) {
					addEvent(rn.getEventosCalendario(data,getServico()));
					}
				}
				
			}
		
		};
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro("Ouve um problema na operação!");
		}
	}
	public void novaSolicitacao() {
		try {
			//metodo que irá retornar a nova instancia do objeto solicitacao
			//atribuirCliente();
			getSolicitacao().setCliente(userMB.getUser());
			solicitacao = getRn().novaSolicitacao(getSolicitacao(), userMB.getUser());
			setControlarFormCadastrar(true);
			setControlarFormEditar(true);
			setControlarFormListar(false);
		} catch (Exception e) {	
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro("Ouve um problema na operação!");
		}
		
	}
	
	public void atribuirCliente() {
		if(userMB.getUser().isCliente()) {
			getSolicitacao().setCliente(userMB.getUser());
		}else if(cliente!= null){
			getSolicitacao().setCliente(cliente);
		}
		
	}
	public void adicionarSolicitacaoServico() {
		if(getContainersSelecionados().isEmpty()) {
			JSFMessageUtil.adicionarMensagemErro("Nenhum Container foi selecionado.");
		}else
			if(getServico().getId()==0) {
				JSFMessageUtil.adicionarMensagemErro("É necessário adicionar um serviço");
			}
			else {
				try {
					solicitacao = getRn().novaSolicitacaoServico(getSolicitacao(), getServico(), getContainersSelecionados());
					servico = new Servico();
					containersSelecionados = new ArrayList<>();
					JSFMessageUtil.adicionarMensagemSucesso("Solicitação de serviço relizada com sucesso!");
				} catch (Exception e) {
					e.printStackTrace();
					JSFMessageUtil.adicionarMensagemErro("Ouve um problema na operação!");
				}
			}
	}
	public void limparSolicitacaoServico() {
		servico = new Servico();
		dataSolicitacao = null;
	}
	public void removerSolicitacaoServico() {
		try {
			solicitacao.removeSolicitacaoServico(solicitacaoServico);
			solicitacaoServico.setSolicitacao(null);
			solicitacao = getRn().alterar(solicitacao, "Remoção de solicitação de servico", userMB.getUser());
			JSFMessageUtil.adicionarMensagemSucesso("Solicitação excluida com sucesso!");
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro("Erro ao remover a solicitação");
			e.printStackTrace();
		}
		
	}
	public void adicionarMensagem() {
		//TODO
		try {
			getMensagem().setSolicitacao(solicitacao);
			getMensagem().setUsuario(userMB.getUser());
			getMensagem().setData(new Date());
			solicitacao.addMensagem(getMensagem());
			solicitacao = getRn().alterar(solicitacao);
		} catch (Exception e) {
			// TODO: handle exception
			JSFMessageUtil.adicionarMensagemErro("Erro ao adicionar a mensagem");
			e.printStackTrace();
		}
	}
	public void removeAnexo() {
		//TODO 
	}
	public void download(ActionEvent evento) {
		try {
			anexo = (AnexosSolicitacao) evento.getComponent().getAttributes().get("anexoSelecionado");

			FileInputStream stream = null;

			stream = new FileInputStream(
					getJsfUtil().getCaminhoAnexos(TipoAnexosEnum.SOLICITACAO) + anexo.getCaminhoAnexo());

			setPdf(new DefaultStreamedContent(stream, "application/pdf", anexo.getCaminhoAnexo()));
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void uploadAnexo(FileUploadEvent event) throws SQLException, EmailException{
		
		//TODO:
		try {
			AnexosSolicitacao anexo = new AnexosSolicitacao();
			Path arquivoTemp = Files.createTempFile(null, null);
			Files.copy(event.getFile().getInputstream(), arquivoTemp, StandardCopyOption.REPLACE_EXISTING);
			anexo.setCaminhoAnexo(event.getFile().getFileName().replace(".pdf", "")+ "_"
					+getSolicitacao().getCodigoBL()+"_"+ new Date().getTime() + ".pdf");
			anexo.setSolicitacao(solicitacao);
			solicitacao.addAnexo(anexo);
			Path origem= Paths.get(arquivoTemp.toString());
			String destinoCaminho= getJsfUtil().getCaminhoAnexos(TipoAnexosEnum.SOLICITACAO);
			destinoCaminho = destinoCaminho + anexo.getCaminhoAnexo();
			Path destino = Paths.get(destinoCaminho);
			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
			
			solicitacao = getRn().alterar(solicitacao, "Anexo adicionado", getUserMB().getUser());
			JSFMessageUtil.adicionarMensagemSucesso(MenssagensUtilEnum.OPERACAO_UPLOAD.getDescricao());
		} catch (IOException e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
			e.printStackTrace();
		}
	}
	public void adicionarTodosOsContainers() {
		for(CadastroBLContanier container : getContainers()) {
			if(!getContainersSelecionados().contains(container)) {
				getContainersSelecionados().add(container);
				getContainers().remove(container);
			}
		}
	}
	public void adicionarContainer(SelectEvent event) {
		container = (CadastroBLContanier) event.getObject();
		getContainersSelecionados().add(container);
		getContainers().remove(container);
		
	}
	public void removerContainer() {
		getContainersSelecionados().remove(container);
		getContainers().add(container);
	}
	public List<Servico> pesquisaServico(String pesquisa) {
		List<Servico> servicos = new ArrayList<Servico>();
		try {
			
			servicos= getJanelaAtendimentoRN().getServicos(pesquisa);
			
		
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
		return servicos;
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
	public void carregarContainers() {
		// TODO método que irá buscar os containers do cliente
		containers = getRn().getContainersDoUsuario(userMB.getUser());
		
	}
	public List<CadastroBLContanier> pesquisaContainers(String pesquisa){
		List<CadastroBLContanier> containersFiltrados = new ArrayList<>();
		try {
			for(CadastroBLContanier container : getContainers()) {
				if(container.getNumeroContanier().toUpperCase().contains(pesquisa.toUpperCase())) {
					if(!getContainersSelecionados().contains(container)) {
						containersFiltrados.add(container);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
		return null;
	}
	
	
	public JSFUtil getJsfUtil() {
		if(jsfUtil == null) jsfUtil= new JSFUtil();
		return jsfUtil;
	}
	public void setJsfUtil(JSFUtil jsfUtil) {
		this.jsfUtil = jsfUtil;
	}
	public Servico getServico() {
		if(servico== null) servico = new Servico();
		return servico;
	}
	
	public ScheduleModel getLazyEventServicoJanela() {
		if(lazyEventServicoJanela == null) carregarEventosServicoJanela();
		return lazyEventServicoJanela;
	}
	public void setLazyEventServicoJanela(ScheduleModel lazyEventServicoJanela) {
		this.lazyEventServicoJanela = lazyEventServicoJanela;
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
	
	public List<SolicitacaoServico> getSolicitacaoServicos() {
		if(solicitacaoServicos == null) solicitacaoServicos = new ArrayList<SolicitacaoServico>();
		return solicitacaoServicos;
	}
	public void setSolicitacaoServicos(List<SolicitacaoServico> solicitacaoServicos) {
		this.solicitacaoServicos = solicitacaoServicos;
	}
	
	public CadastroBLContanier getContainer() {
		return container;
	}
	public void setContainer(CadastroBLContanier container) {
		this.container = container;
	}
	public List<CadastroBLContanier> getContainersSelecionados() {
		if(containersSelecionados == null) containersSelecionados = new ArrayList<CadastroBLContanier>();
		return containersSelecionados;
	}
	public void setContainersSelecionados(List<CadastroBLContanier> containersSelecionados) {
		this.containersSelecionados = containersSelecionados;
	}
	public UserMB getUserMB() {
		return userMB;
	}
	
	
	public JanelaAtendimentoRN getJanelaAtendimentoRN() {
		if(janelaAtendimentoRN == null) janelaAtendimentoRN = new JanelaAtendimentoRN();
		return janelaAtendimentoRN;
	}
	public void setJanelaAtendimentoRN(JanelaAtendimentoRN janelaAtendimentoRN) {
		this.janelaAtendimentoRN = janelaAtendimentoRN;
	}
	public void setUserMB(UserMB userMB) {
		this.userMB = userMB;
	}
	public void setServico(Servico servico) {
		this.servico = servico;
	}
	public boolean isOutroServico() {
		return outroServico;
	}
	public void setOutroServico(boolean outroServico) {
		this.outroServico = outroServico;
	}
	public AnexosSolicitacao getAnexo() {
		if(anexo==null) anexo = new AnexosSolicitacao();
		return anexo;
	}
	public void setAnexo(AnexosSolicitacao anexo) {
		this.anexo = anexo;
	}
	public StreamedContent getPdf() {
		return pdf;
	}
	public void setPdf(StreamedContent pdf) {
		this.pdf = pdf;
	}

	
	
}
