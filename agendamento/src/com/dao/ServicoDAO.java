package com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	
	
	

}
