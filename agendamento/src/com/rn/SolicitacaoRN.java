package com.rn;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.primefaces.model.DefaultScheduleEvent;

import com.dao.AnexoSolicitacaoDAO;
import com.dao.HistoricoDAO;
import com.dao.MensagemDAO;
import com.dao.ServicoJanelaAtendimentoDAO;
import com.dao.SolicitacaoDAO;
import com.dao.SolicitacaoServicoDAO;
import com.dao.UserDAO;
import com.entidade.Servico;
import com.entidade.ServicoJanelaAtendimento;

import seguranca.com.entidade.User;
import seguranca.com.enums.Role;

public class SolicitacaoRN implements Serializable {
	private SolicitacaoDAO DAO;
	private SolicitacaoServicoDAO solicitacaoServicoDAO;
	private MensagemDAO mensagemDAO;
	private AnexoSolicitacaoDAO anexoSolicitacaoDAO;
	private HistoricoDAO historicoDAO;
	private ServicoJanelaAtendimentoDAO servicoJanelaAtendimentoDAO;
	private UserDAO userDAO;
	public SolicitacaoDAO getDAO() {
		return DAO;
	}
	
	
	public DefaultScheduleEvent getEventosCalendario(Date data, Servico servico) {
		// to-do
		//adiciona ao calendario a janela do dia e todos os servicosJanela
		//pega a capacidade do servico no dia e compara com a soma das solicitacoes que ja estao cadastradas
		//imprime na tela em cor vermelha se for encaixe e na cor verde para agendar a solicitacao
		
		
		return null;
	}
	
	public List<User> pesquisarUsuarioAutoCompletePorCliente(String pesquisa){
		
			getUserDAO().beginTransaction();
			List<User> list =getUserDAO().pesquisaAutoCompletePorRole(pesquisa, Role.CLIENTE);
			getUserDAO().closeTransaction();
			
			return list;
		
		
		
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
	
	
	

}
