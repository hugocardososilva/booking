package com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.entidade.UserComissariaAgendamento;

import conexao.com.dao.GenericDAO;
import seguranca.com.entidade.CadastroComissario;
import seguranca.com.entidade.User;

public class UserComissariaAgendamentoDAO extends GenericDAO<UserComissariaAgendamento> {

	private static final long serialVersionUID = 1L;

	public UserComissariaAgendamentoDAO() {
		super(UserComissariaAgendamento.class);
	}

	public void delete(UserComissariaAgendamento entidade) {
		super.delete(entidade.getId(), UserComissariaAgendamento.class);
	}
	
	public List<UserComissariaAgendamento> pesquisaAutoComplete(String pesquisa){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserComissariaAgendamento.CAMPO_USUARIO, pesquisa);     
		
		return super.retornaResultadoListaObjeto(UserComissariaAgendamento.sql_pesquisa_autocomplete, parameters, false);
	}	

	public List<UserComissariaAgendamento> pesquisaUsersImportadores(CadastroComissario pesquisa){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserComissariaAgendamento.CAMPO_IMPORTADOR, pesquisa.getId());     
		
		return super.retornaResultadoListaObjeto(UserComissariaAgendamento.sql_pesquisa_users_comissarios, parameters, false);
	}	
	
	/**
	 * Retorna a lista dos importadores que estão liberados para incluir Usuarios / Comissária
	 * @param entidade ( User )
	 * @return List<UserImportadorAgendamento>
	 * @serialData 13-09-2017
	 * @author murilonadalin 
	 */
	public List<UserComissariaAgendamento> listaPorUsuarios(User entidade){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserComissariaAgendamento.CAMPO_USUARIO, entidade.getId());     
		
		return super.retornaResultadoListaObjeto(UserComissariaAgendamento.SQL_LISTA_USUARIOS, parameters, false);
	}

	public UserComissariaAgendamento retornaUsuariosDAO(User entidade){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserComissariaAgendamento.CAMPO_USUARIO, entidade.getId());     
		
		return super.retornaResultadoObjeto(UserComissariaAgendamento.SQL_LISTA_USUARIOS, parameters);
	}
	
}
