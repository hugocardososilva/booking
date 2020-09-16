package com.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import com.entidade.Solicitacao;

import conexao.com.dao.GenericDAO;
import seguranca.com.entidade.CadastroBL;

public class SolicitacaoDAO extends GenericDAO<Solicitacao> {

	public SolicitacaoDAO() {
		super(Solicitacao.class);
		// TODO Auto-generated constructor stub
	}
	public Solicitacao localizarPorIDJoinFetch(int id) {
		TypedQuery<Solicitacao> q = em.createQuery("select s from Solicitacao s fetch all properties where s.id = :id", Solicitacao.class);
		q.setParameter("id", id);
		
		try {
			List<Solicitacao> lista = q.setMaxResults(1).getResultList();
			return lista.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
