package com.dao;

import com.entidade.ParametrosSistema;

import conexao.com.dao.GenericDAO;

public class ParametrosSistemaDAO extends GenericDAO<ParametrosSistema> {

	private static final long serialVersionUID = 1L;

	public ParametrosSistemaDAO() {
		super(ParametrosSistema.class);
	}

	public void delete(ParametrosSistema parametros) {
		super.delete(parametros.getId(), ParametrosSistema.class);
	}

}
