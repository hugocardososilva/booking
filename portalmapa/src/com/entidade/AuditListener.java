package com.entidade;

import org.hibernate.envers.RevisionListener;

import conexao.com.util.LoginController;

public class AuditListener implements RevisionListener {
	private LoginController loginController;

	@Override
	public void newRevision(Object revisionEntity) {
		if (loginController == null) {
			loginController = new LoginController();
		}
		
		String nomeUsuario = null;
		String ipUsuario = null;
		if (loginController.getRetornaUsuarioLogado() == null) {
			nomeUsuario = "usuario_teste_junit";
		} else {
			nomeUsuario = loginController.getRetornaUsuarioLogado().getName();
			ipUsuario = loginController.getRetornaIPUsuarioLogado();
		}
		
		AuditEntity revEntity = (AuditEntity) revisionEntity;
		revEntity.setUsuario(nomeUsuario );
		revEntity.setIpUsuario(ipUsuario);
	}
}
