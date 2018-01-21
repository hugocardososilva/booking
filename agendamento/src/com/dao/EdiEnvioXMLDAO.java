package com.dao;

import com.entidade.EdiEnvioXML;

import conexao.com.dao.GenericDAO;

/**
 * Criado class DAO EdiEnvioXMLDAO
 * @author murilonadalin
 * @since 29-08-2017
 */
public class EdiEnvioXMLDAO extends GenericDAO<EdiEnvioXML> {

	private static final long serialVersionUID = 1L;

	public EdiEnvioXMLDAO() {
		super(EdiEnvioXML.class);
	}

	public void delete(EdiEnvioXML parametros) {
		super.delete(parametros.getId(), EdiEnvioXML.class);
	}
}
