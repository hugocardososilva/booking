package com.dao;

import java.io.Serializable;

import com.dao.AuditEntityDAO;
import com.entidade.AuditEntity;

public class AudityEntityRN implements Serializable {

	private static final long serialVersionUID = 1L;

	private AuditEntityDAO dao;
	
	public AuditEntityDAO getDAO() {
		if (dao == null) {
			dao = new AuditEntityDAO();
		}
		return dao;
	}
	
	public AuditEntity localizar(int itemId) throws Exception {
		getDAO().beginTransaction();
		AuditEntity item = null;
		try {
			item = getDAO().localizarPorID(itemId);
		} catch (Exception e) {
			getDAO().rollback();
			throw new Exception(e.getMessage());
		}
		getDAO().closeTransaction();
		return item;
	}

}
