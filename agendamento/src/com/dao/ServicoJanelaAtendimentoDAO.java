package com.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import com.entidade.JanelaAtendimento;
import com.entidade.Servico;
import com.entidade.ServicoJanelaAtendimento;

import conexao.com.dao.GenericDAO;

public class ServicoJanelaAtendimentoDAO extends GenericDAO<ServicoJanelaAtendimento> {

	public ServicoJanelaAtendimentoDAO() {
		super(ServicoJanelaAtendimento.class);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<ServicoJanelaAtendimento> getServicoJanelaPorDataJanela(Date data, Servico servico){
		TypedQuery<ServicoJanelaAtendimento> q = em.createQuery(ServicoJanelaAtendimento.sql+" where s.atendimentoJanela.data = :dataJanela AND s.servico.id = :idServico", ServicoJanelaAtendimento.class);
		q.setParameter("dataJanela", data );
		q.setParameter("idServico", servico.getId());
		
		try {
			List<ServicoJanelaAtendimento> lista = (ArrayList<ServicoJanelaAtendimento>) q.getResultList();
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


}
