package com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.entidade.Servico;

import conexao.com.dao.GenericDAO;

public class ServicoDAO extends GenericDAO<Servico> {

	public ServicoDAO() {
		super(Servico.class);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<Servico> pesquisaAutoComplete(String pesquisa){
		Map<String, Object> param= new HashMap<String, Object>();
		param.put(Servico.CAMPO_NOME, "%"+pesquisa+"%");
		return super.retornaResultadoListaObjeto(Servico.sql_pesquisa_autocomplete, param, false);
	}
	public List<Servico> pesquisaAutoCompleteTodos(String pesquisa){
		Map<String, Object> param= new HashMap<String, Object>();
		param.put(Servico.CAMPO_NOME, "%"+pesquisa+"%");
		return super.retornaResultadoListaObjeto(Servico.sql_pesquisa_autocomplete_solicitacao, param, false);
	}
	
	public boolean verificarOutroServico() {
		TypedQuery<Servico> q = em.createQuery(Servico.sql+ " where s.unMedidaJanela eq :un", Servico.class);
		q.setParameter("un", "O".charAt(0));
		try {
			q.getFirstResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
	
	
	

}
