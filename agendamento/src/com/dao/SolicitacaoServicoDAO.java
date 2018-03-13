package com.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import com.entidade.Servico;
import com.entidade.SolicitacaoServico;

import conexao.com.dao.GenericDAO;

public class SolicitacaoServicoDAO extends GenericDAO<SolicitacaoServico> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SolicitacaoServicoDAO() {
		super(SolicitacaoServico.class);
		// TODO Auto-generated constructor stub
	}
	
public List<SolicitacaoServico> getSolicitacoesPorDataeServico(Date data, Servico servico  ){
	TypedQuery<SolicitacaoServico> q = em.createQuery(SolicitacaoServico.sql+" where s.dataSolicitacao = :data and s.servico.id = :idServico", SolicitacaoServico.class);	
	q.setParameter("idServico", servico.getId());
	q.setParameter("data", data);
	try {
		List<SolicitacaoServico> lista = (ArrayList<SolicitacaoServico>)q.getResultList();
		return lista;
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
	}
	
	
	

}
