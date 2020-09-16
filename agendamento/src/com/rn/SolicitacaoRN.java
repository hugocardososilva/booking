package com.rn;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.util.SoftLimitMRUCache;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.primefaces.model.DefaultScheduleEvent;

import com.dao.AnexoSolicitacaoDAO;
import com.dao.HistoricoDAO;
import com.dao.MensagemDAO;
import com.dao.ServicoJanelaAtendimentoDAO;
import com.dao.SolicitacaoDAO;
import com.dao.SolicitacaoServicoDAO;
import com.dao.UserDAO;
import com.entidade.AnexosSolicitacao;
import com.entidade.Historico;
import com.entidade.Mensagem;
import com.entidade.Servico;
import com.entidade.ServicoJanelaAtendimento;
import com.entidade.Solicitacao;
import com.entidade.SolicitacaoServico;
import com.enums.StatusServicos;
import com.enums.StatusSolicitacao;
import com.proxy.ContainerProxy;

import conexao.com.dao.ContainerFlcDAO;
import seguranca.com.entidade.CadastroBL;
import seguranca.com.entidade.CadastroBLContanier;
import seguranca.com.entidade.CadastroImportador;
import seguranca.com.entidade.User;
import seguranca.com.entidade.UserComissario;
import seguranca.com.entidade.UserImportador;
import seguranca.com.enums.Role;

public class SolicitacaoRN implements Serializable {
	private SolicitacaoDAO DAO;
	private SolicitacaoServicoDAO solicitacaoServicoDAO;
	private MensagemDAO mensagemDAO;
	private AnexoSolicitacaoDAO anexoSolicitacaoDAO;
	private HistoricoDAO historicoDAO;
	private ServicoJanelaAtendimentoDAO servicoJanelaAtendimentoDAO;
	private UserDAO userDAO;
	private ContainerFlcDAO flcDAO;
	
	public SolicitacaoDAO getDAO() {
		if(DAO== null) DAO = new SolicitacaoDAO();
		return DAO;
	}
	public Solicitacao localizar(int id) {
		getDAO().beginTransaction();
		Solicitacao s = getDAO().localizarPorIDJoinFetch(id);
		getDAO().closeTransaction();
		return s;
	}
	public void fecharConexao() {
		getDAO().closeTransaction();
	}
	public Solicitacao alterar(Solicitacao solicitacao) throws Exception{
		
		getDAO().beginTransaction();
		
		solicitacao = getDAO().alterar(solicitacao);

		getDAO().commit();
		return solicitacao;
	}
	public Solicitacao alterar(Solicitacao solicitacao, String descricao, User user) throws Exception{
		solicitacao.setUltResponsavel(user);
		solicitacao = adicionarHistorico(solicitacao, descricao, user);
		getDAO().beginTransaction();
		solicitacao = getDAO().alterar(solicitacao);
		getDAO().commit();
		return solicitacao;
	}
	
	public Solicitacao novaSolicitacao(Solicitacao solicitacao, User user) {
		
		
		try {
			getDAO().beginTransaction();
			solicitacao.setDataCadastro(new Date());
			solicitacao.setStatusSolicitacao(StatusSolicitacao.PENDENTE);
			//logica aplicada:
			if(user.isCliente()) {
				solicitacao.setCliente(user);
				
			}else
				if(user.isClif()||user.isAdmin()) {
					
					
				}
				else
					if(user.isDespachante()) {
						solicitacao.setCliente(user);
					}
			

			solicitacao.setUltResponsavel(user);
			getDAO().beginTransaction();			
			getDAO().save(solicitacao);

			
			//adicionando o numero da ATI
			String ano = String.valueOf(new DateTime().getYear());
			System.out.println(solicitacao.getId());
			solicitacao.setNumeroATI(solicitacao.getId()+"/"+ano);
			solicitacao = getDAO().alterar(solicitacao);
		
			getDAO().commitAndCloseTransaction();
			

			return localizar(solicitacao.getId());


		} catch (Exception e) {
			e.printStackTrace();
			getDAO().rollback();
			return null;
		}
		
		
			

		
	}
	public List<CadastroBLContanier> getContainersDoUsuario(User user){
		List<CadastroBLContanier> lista= new ArrayList<>();
		try {
			
			getFlcDAO().beginTransaction();	
			
	
		
		if(user.isCliente()) {
			for(UserImportador importador : user.getListaUserImportadores()) {
				
				List<CadastroBLContanier> containers = getFlcDAO().findByImportador(importador.getImportador());
				for(CadastroBLContanier c: containers) {
					if(!lista.contains(c)) lista.add(c);
				}
			}
		}
		if(user.isDespachante()) {
			if(user.getListaUserImportadores().isEmpty()|| user.getListaUserImportadores()==null) {
				for(UserComissario comissario : user.getListaUserComissaria()) {
					List<CadastroBLContanier> containers= getFlcDAO().findByComissarioSemImportador(comissario.getCadComissario());
					for(CadastroBLContanier c: containers) {
						if(!lista.contains(c)) lista.add(c);
					}
				}
			}else {
				for(UserComissario comissario : user.getListaUserComissaria()) {
					List<CadastroBLContanier> containersComissario = getFlcDAO().findByComissarioSemImportador(comissario.getCadComissario());
					for(CadastroBLContanier c : containersComissario) {
						if(!lista.contains(c)) lista.add(c);
						
					}
					
 				}
				for(UserImportador importador : user.getListaUserImportadores()) {
					List<CadastroBLContanier> containersImportador = getFlcDAO().findByImportador(importador.getImportador());
					for(CadastroBLContanier c: containersImportador) {
						if(!lista.contains(c)) lista.add(c);
					}
				}
			}
			
		}
		
	
	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		getFlcDAO().closeTransaction();
		return lista;
		
	}
	public Solicitacao editarSoliticacaoServico(SolicitacaoServico solicitacaoServico) throws Exception {
		
	
		
		if(solicitacaoServico.getOS() == null || solicitacaoServico.getOS().isEmpty()) {
			solicitacaoServico.setStatusServicos(StatusServicos.CRIA_OS_SARA);
		}else {
			if(solicitacaoServico.getDataInicioOS()!= null) {
			if(solicitacaoServico.getDataTerminoOS() == null) {
				solicitacaoServico.setStatusServicos(StatusServicos.OS_INICIADA);
			}else {
				solicitacaoServico.setStatusServicos(StatusServicos.FINALIZADO);
			}
		}
		}
		 	Solicitacao solicitacao = alterar(solicitacaoServico.getSolicitacao(), "SolicitaÃ§Ã£o de serviÃ§o - status alterado : "+ solicitacaoServico.getId() + "  "+ solicitacaoServico.getStatusServicos().getDescricao(), solicitacaoServico.getSolicitacao().getUltResponsavel());
		
		return solicitacao;
	}
	public Solicitacao notificarDemurrage(SolicitacaoServico solicitacaoServico, Solicitacao solicitacao) {
		int freetime= solicitacaoServico.getContainer().getCadastroBL().getFreeTime();
		
		DateTime hoje = new DateTime();
		DateTime data = new DateTime(solicitacaoServico.getContainer().getCadastroBL().getDataAtracacao());
		Days diasAteDTA = Days.daysBetween(hoje, data);
		int dta = diasAteDTA.getDays();
		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
	
		int total = dta+freetime;
		data.plusDays(freetime);
		
	
		Mensagem mensagem = new Mensagem();
		mensagem.setTitulo("Serviço - " + solicitacaoServico.getServico().getNome() + " - Observaços");
		mensagem.setConteudo("O prazo máximo de permanência no do container é de " + total + " dias. Com previsão para " + formatter.format(data.toDate()));
		
		solicitacao = addMensagem(solicitacaoServico.getSolicitacao(), solicitacaoServico.getSolicitacao().getUltResponsavel(), mensagem);
		return solicitacao;
	}
	public Solicitacao addMensagem(Solicitacao solicitacao, User user, Mensagem mensagem) {
		mensagem.setSolicitacao(solicitacao);
		mensagem.setUsuario(user);
		mensagem.setData(new Date());
		solicitacao.addMensagem(mensagem);
		try {
			solicitacao = alterar(solicitacao);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return solicitacao;
	}
	
	public Solicitacao novaSolicitacaoServico(Solicitacao solicitacao, Servico servico, List<CadastroBLContanier> containers) {
		try {	
		
			for(CadastroBLContanier container : containers) {
				SolicitacaoServico solicitacaoServico = new SolicitacaoServico();
				solicitacao.addSolicitacaoServico(solicitacaoServico);
				solicitacaoServico.setSolicitacao(solicitacao);
				solicitacaoServico.setServico(servico);
				solicitacaoServico.setContainer(container);
				solicitacaoServico.setDataSolicitacao(new Date());
				solicitacaoServico.setStatusServicos(StatusServicos.CRIA_OS_SARA);
				
				
				if(servico.isValidarDemurrage()) {
					solicitacao = notificarDemurrage(solicitacaoServico, solicitacao);
				}
			
			}
			solicitacao = alterar(solicitacao, "Nova SolicitaÃ§Ã£o de ServiÃ§os", solicitacao.getUltResponsavel());
			return solicitacao;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		
			return null;
		}
	
	}
	
	
	public DefaultScheduleEvent getEventosCalendario(DateTime data, Servico servico) throws NullPointerException {
		// to-do
		//adiciona ao calendario a janela do dia e todos os servicosJanela
		//pega a capacidade do servico no dia e compara com a soma das solicitacoes que ja estao cadastradas
		//imprime na tela em cor vermelha se for encaixe e na cor verde para agendar a solicitacao
		List<ServicoJanelaAtendimento> servicoJanelaAtendimentos = new ArrayList<>();
		List<SolicitacaoServico> solicitacaoServicos = new ArrayList<>();
		
		
		
		getServicoJanelaAtendimentoDAO().beginTransaction();
		servicoJanelaAtendimentos = getServicoJanelaAtendimentoDAO().getServicoJanelaPorDataJanela(data.toDate(), servico);
		getServicoJanelaAtendimentoDAO().closeTransaction();
		
		DefaultScheduleEvent defaultScheduleEvent= new DefaultScheduleEvent();
		defaultScheduleEvent.setAllDay(true);
		defaultScheduleEvent.setData(data.toDate());
		defaultScheduleEvent.setStartDate(data.toDate());
		defaultScheduleEvent.setEndDate(data.plusHours(20).toDate());
		
		
		if(!servicoJanelaAtendimentos.isEmpty()) {
			
			
			getSolicitacaoServicoDAO().beginTransaction();
			solicitacaoServicos = getSolicitacaoServicoDAO().getSolicitacoesPorDataeServico(data.toDate(), servico);
			getSolicitacaoServicoDAO().closeTransaction();
			
			int janelaServico = 0;
			int janelaSolicitacao = 0;
				for(ServicoJanelaAtendimento servicoJanelaAtendimento: servicoJanelaAtendimentos) {
					janelaServico = janelaServico + servicoJanelaAtendimento.getCapacidadePeriodo();
				}
				janelaSolicitacao = solicitacaoServicos.size();
			int disponivel = janelaServico-janelaSolicitacao;
			if(disponivel > 0) {
				defaultScheduleEvent.setTitle("DisponÃ­vel");
				defaultScheduleEvent.setStyleClass("green");
				
			}else {
				defaultScheduleEvent.setTitle("Encaixe");
				defaultScheduleEvent.setStyleClass("red");
				
			}
		}else {
		defaultScheduleEvent.setTitle("NÃ£o DisponÃ­vel");
		defaultScheduleEvent.setStyleClass("no-color");
		}
		
		return defaultScheduleEvent;
	}
	
	
	public Solicitacao adicionarHistorico(Solicitacao solicitacao, String descricao, User user) {
		Historico historico = new Historico();
		historico.setData(new Date());
		historico.setDescricao(descricao);
		historico.setSolicitacao(solicitacao);
		historico.setUsuario(user);
		solicitacao.addHistorico(historico);
		return solicitacao;
		
	}
	public List<User> pesquisarUsuarioAutoCompletePorCliente(String pesquisa){
		
			getUserDAO().beginTransaction();
			List<User> list =getUserDAO().pesquisaAutoCompletePorRole(pesquisa, Role.CLIENTE);
			getUserDAO().closeTransaction();
			
			return list;
		
		
		
	}
	public List<CadastroBLContanier> findContainers(User cliente) {
		getFlcDAO().beginTransaction();
		List<CadastroBLContanier> list = getFlcDAO().findByCliente(cliente.getId());
		getFlcDAO().closeTransaction();
		return list;
	}
	
	public void adicionarServicoContainer(Map<String,Object> entry, Servico servico, Solicitacao solicitacao) {
		
	}
	public void atualizarStatusSolicitacaoServicoSara() {
		//pega todos as solicitacoes que nao estao finalizadas
		//aplica o novo status e salva com uma mensagem do historico
		List<SolicitacaoServico> solicitacoes = new ArrayList<>();
		try {
		getSolicitacaoServicoDAO().beginTransaction();
		solicitacoes = getSolicitacaoServicoDAO().getSolicitacaoServicoNaoFinalizadas();
		getSolicitacaoServicoDAO().closeTransaction();
		
		for(SolicitacaoServico s : solicitacoes) {
			if(s.getStatusServicos() != StatusServicos.CRIA_OS_SARA) {
			String status = getSolicitacaoServicoDAO().getStatusServerSara(s.getSolicitacao().getNumeroATI(), s.getOS());
			if(status.equals("OS_GERADA")) {
				s.setStatusServicos(StatusServicos.OS_GERADA);
			}else 
				if(status.equals("OS_INICIADA")) {
					s.setStatusServicos(StatusServicos.OS_INICIADA);
				}
				else 
					if(status.equals("FINALIZADO")) {
						s.setStatusServicos(StatusServicos.FINALIZADO);
					}
			getDAO().beginTransaction();
			alterar(s.getSolicitacao(), "AlteraÃ§Ã£o de Status do serviÃ§o",s.getSolicitacao().getUltResponsavel());
			getDAO().commitAndCloseTransaction();
			}
		
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	
	
	public ServicoJanelaAtendimentoDAO getServicoJanelaAtendimentoDAO() {
		if(servicoJanelaAtendimentoDAO==null) servicoJanelaAtendimentoDAO = new ServicoJanelaAtendimentoDAO();
		return servicoJanelaAtendimentoDAO;
	}

	
	
	public ServicoJanelaAtendimentoDAO solicitacaoServicoDAO() {
		if(solicitacaoServicoDAO==null) solicitacaoServicoDAO = new SolicitacaoServicoDAO();
		return servicoJanelaAtendimentoDAO;
	}
	

	public SolicitacaoServicoDAO getSolicitacaoServicoDAO() {
		if(solicitacaoServicoDAO == null) solicitacaoServicoDAO = new SolicitacaoServicoDAO();
		return solicitacaoServicoDAO;
	}
	public MensagemDAO getMensagemDAO() {
		if(mensagemDAO== null) mensagemDAO = new MensagemDAO();
		return mensagemDAO;
	}
	public AnexoSolicitacaoDAO getAnexoSolicitacaoDAO() {
		if(anexoSolicitacaoDAO==null) anexoSolicitacaoDAO = new AnexoSolicitacaoDAO();
		return anexoSolicitacaoDAO;
	}
	public HistoricoDAO getHistoricoDAO() {
		if(historicoDAO == null) historicoDAO = new HistoricoDAO();
		return historicoDAO;
	}


	public UserDAO getUserDAO() {
		if(userDAO== null) userDAO= new UserDAO();
		return userDAO;
	}
	

	public ContainerFlcDAO getFlcDAO() {
		if(flcDAO == null) flcDAO = new ContainerFlcDAO();
		return flcDAO;
	}

	
	
	

}
