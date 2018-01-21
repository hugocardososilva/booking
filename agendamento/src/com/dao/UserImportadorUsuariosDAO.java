package com.dao;

import java.util.HashMap;
import java.util.Map;

import com.entidade.UserImportadorUsuarios;

import conexao.com.dao.GenericDAO;
import seguranca.com.entidade.User;

public class UserImportadorUsuariosDAO extends GenericDAO<UserImportadorUsuarios> {

	private static final long serialVersionUID = 1L;

	public UserImportadorUsuariosDAO() {
		super(UserImportadorUsuarios.class);
	}

	public void delete(UserImportadorUsuarios entidade) {
		super.delete(entidade.getId(), UserImportadorUsuarios.class);
	}
	
	/**
	 * Retorna a entidade dos importadores 
	 * @param entidade ( User )
	 * @return UserImportadorAgendamento
	 * @serialData 13-09-2017
	 * @author murilonadalin 
	 */
	public UserImportadorUsuarios retornaImportadorUsuarioMaster(User entidade){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserImportadorUsuarios.CAMPO_USUARIO, entidade.getId());     
		
		return super.retornaResultadoObjeto(UserImportadorUsuarios.SQL_LISTA_USUARIOS, parameters);
	}
	
}
