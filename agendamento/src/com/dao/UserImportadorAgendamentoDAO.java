package com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.entidade.UserImportadorAgendamento;

import conexao.com.dao.GenericDAO;
import seguranca.com.entidade.CadastroComissario;
import seguranca.com.entidade.User;

public class UserImportadorAgendamentoDAO extends GenericDAO<UserImportadorAgendamento> {

	private static final long serialVersionUID = 1L;

	public UserImportadorAgendamentoDAO() {
		super(UserImportadorAgendamento.class);
	}

	public void delete(UserImportadorAgendamento entidade) {
		super.delete(entidade.getId(), UserImportadorAgendamento.class);
	}
	
	public List<UserImportadorAgendamento> pesquisaAutoComplete(String pesquisa){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserImportadorAgendamento.CAMPO_USUARIO, pesquisa);     
		
		return super.retornaResultadoListaObjeto(UserImportadorAgendamento.sql_pesquisa_autocomplete, parameters, false);
	}	

	public List<UserImportadorAgendamento> pesquisaUsersImportadores(CadastroComissario pesquisa){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserImportadorAgendamento.CAMPO_IMPORTADOR, pesquisa.getId());     
		
		return super.retornaResultadoListaObjeto(UserImportadorAgendamento.sql_pesquisa_users_comissarios, parameters, false);
	}	
	
	/**
	 * Retorna a lista dos importadores que estão liberados para incluir Usuarios / Comissária
	 * @param entidade ( User )
	 * @return List<UserImportadorAgendamento>
	 * @serialData 13-09-2017
	 * @author murilonadalin 
	 */
	public List<UserImportadorAgendamento> listaPorUsuarios(User entidade){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserImportadorAgendamento.CAMPO_USUARIO, entidade.getId());     
		
		return super.retornaResultadoListaObjeto(UserImportadorAgendamento.SQL_LISTA_USUARIOS, parameters, false);
	}

	/**
	 * Retorna a entidade dos importadores 
	 * @param entidade ( User )
	 * @return UserImportadorAgendamento
	 * @serialData 13-09-2017
	 * @author murilonadalin 
	 */
	public UserImportadorAgendamento retornaImportadorUsuarioMaster(User entidade){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserImportadorAgendamento.CAMPO_USUARIO, entidade.getId());     
		
		return super.retornaResultadoObjeto(UserImportadorAgendamento.SQL_LISTA_USUARIOS, parameters);
	}
	
}
