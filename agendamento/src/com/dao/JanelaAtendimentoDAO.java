package com.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;



import com.entidade.JanelaAtendimento;

import conexao.com.dao.GenericDAO;

public class JanelaAtendimentoDAO extends GenericDAO<JanelaAtendimento>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JanelaAtendimentoDAO() {
		super(JanelaAtendimento.class);
		// TODO Auto-generated constructor stub
	}

	public List<JanelaAtendimento> getJanelasAtendimentoPorData(Date dataInicio, Date dataFim){
		TypedQuery<JanelaAtendimento> q = em.createQuery(JanelaAtendimento.sql + " where j.data BETWEEN :inicioDia AND :terminoDia order by j.data ASC", JanelaAtendimento.class);
		q.setParameter("inicioDia", dataInicio);
		q.setParameter("terminoDia", dataFim);
		
		try {
			List<JanelaAtendimento> lista = (ArrayList<JanelaAtendimento>) q.getResultList();
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			}
	}


}
