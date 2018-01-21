package com.dao;

import java.util.HashMap;
import java.util.Map;

import com.entidade.UserComissariaAgendamento;
import com.entidade.UserComissariaUsuarios;

import conexao.com.dao.GenericDAO;
import seguranca.com.entidade.User;

public class UserComissariaUsuariosDAO extends GenericDAO<UserComissariaUsuarios> {

	private static final long serialVersionUID = 1L;

	public UserComissariaUsuariosDAO() {
		super(UserComissariaUsuarios.class);
	}

	public void delete(UserComissariaUsuarios entidade) {
		super.delete(entidade.getId(), UserComissariaUsuarios.class);
	}
	
	/**
	 * Retorna a entidade "UserComissariaUsuarios ", que estão vinculados a comissária
	 * @param entidade ( User )
	 * @return UserComissariaUsuario
	 * @author murilonadalin
	 * @serialData 19-09-2017
	 */
	public UserComissariaUsuarios retornaUsuarioComissariaDAO(User entidade){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserComissariaUsuarios.CAMPO_USUARIO, entidade.getId());     
		
		return super.retornaResultadoObjeto(UserComissariaUsuarios.SQL_LISTA_USUARIOS, parameters);
	}

	public UserComissariaUsuarios retornaUsuarioComissariaMasterDAO(UserComissariaAgendamento entidade){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserComissariaUsuarios.CAMPO_COMISSARIAMASTER, entidade.getId());     
		
		return super.retornaResultadoObjeto(UserComissariaUsuarios.sql_pesquisa_users_comissarios, parameters);
	}
	
}
