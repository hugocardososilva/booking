package com.rn;

import com.dao.EdiEnvioXMLDAO;
import com.entidade.EdiEnvioXML;

/**
 * Criado class RN ( EdiEnvioXMLRN ), para controlar os metodos
 * @author murilonadalin
 * @since 29-08-2017
 */
public class EdiEnvioXMLRN {
	
	private EdiEnvioXMLDAO DAO;
	
	public EdiEnvioXMLDAO getDAO() {
		if (DAO == null) {
			DAO = new EdiEnvioXMLDAO();
		}
		return DAO;
	}

	/**
	 * Metodo incluir
	 * @serialData 29-08-2017 
	 * @param item ( EdiEnvioXML ) 
	 * @return EdiEnvioXML
	 * @author murilonadalin
	 * @throws Exception
	 * 
	 */
	public EdiEnvioXML incluir(EdiEnvioXML item) throws Exception  {
		getDAO().beginTransaction();
		getDAO().save(item);
		getDAO().commitAndCloseTransaction();
		
		return localizar(item.getId());
	}

	/**
	 * Metodo alterar
	 * @author murilonadalin
	 * @serialData 29-08-2017 
	 * @param item ( EdiEnvioXML ) 
	 * @return EdiEnvioXML
	 * @throws Exception
	 * 
	 */
	public EdiEnvioXML alterar(EdiEnvioXML item) throws Exception {
		getDAO().beginTransaction();
		item = getDAO().alterar(item);
		getDAO().commitAndCloseTransaction();
		
		return item;
	}

	/**
	 * Metodo localizar
	 * @author murilonadalin
	 * @serialData 29-08-2017 
	 * @param itemId ( int itemId ) 
	 * @return EdiEnvioXML
	 * @throws Exception
	 * 
	 */
	public EdiEnvioXML localizar(int itemId) throws Exception {
		EdiEnvioXML item = null;
		
		getDAO().beginTransaction();
		try {
			item = getDAO().localizarPorID(itemId);
		} catch (Exception e) {
			getDAO().rollback();
			throw new Exception(e.getMessage());
		}
		getDAO().closeTransaction();
		return item;
	}

	/**
	 * Metodo deletar
	 * @author murilonadalin
	 * @serialData 29-08-2017 
	 * @param EdiEnvioXML ( item ) 
	 * @return EdiEnvioXML
	 * @throws Exception
	 * 
	 */
	public void deletar(EdiEnvioXML item) {
		getDAO().beginTransaction();
		getDAO().delete(item);
		getDAO().commitAndCloseTransaction();
	}
	
}