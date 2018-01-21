package com.dao;

import com.entidade.AuditEntity;

import conexao.com.dao.GenericDAO;

public class AuditEntityDAO extends GenericDAO<AuditEntity> {

	private static final long serialVersionUID = 1L;

	public AuditEntityDAO() {
		super(AuditEntity.class);
	}
}
