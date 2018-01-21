package com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.entidade.UserImportadorAgendamento;
import com.entidade.UserImportadorComissaria;

import conexao.com.dao.GenericDAO;
import seguranca.com.entidade.CadastroComissario;
import seguranca.com.entidade.User;

public class UserImportadorComissariaDAO extends GenericDAO<UserImportadorComissaria> {

	private static final long serialVersionUID = 1L;

	public UserImportadorComissariaDAO() {
		super(UserImportadorComissaria.class);
	}

	public void delete(UserImportadorComissaria entidade) {
		super.delete(entidade.getId(), UserImportadorComissaria.class);
	}
	
	public List<UserImportadorComissaria> pesquisaAutoComplete(String pesquisa){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserImportadorComissaria.CAMPO_USUARIO, pesquisa);     
		
		return super.retornaResultadoListaObjeto(UserImportadorComissaria.sql_pesquisa_autocomplete, parameters, false);
	}	

	public List<UserImportadorComissaria> pesquisaUsersImportadores(CadastroComissario pesquisa){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserImportadorComissaria.CAMPO_IMPORTADOR, pesquisa.getId());     
		
		return super.retornaResultadoListaObjeto(UserImportadorComissaria.sql_pesquisa_users_comissarios, parameters, false);
	}	
	
	/**
	 * Retorna a lista dos importadores que estão liberados para incluir Usuarios / Comissária
	 * @param entidade ( User )
	 * @return List<UserImportadorAgendamento>
	 * @serialData 13-09-2017
	 * @author murilonadalin 
	 */
	public List<UserImportadorComissaria> listaPorUsuarios(User entidade){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserImportadorComissaria.CAMPO_USUARIO, entidade.getId());     
		
		return super.retornaResultadoListaObjeto(UserImportadorComissaria.SQL_LISTA_USUARIOS, parameters, false);
	}

	/**
	 * Retorna a lista dos importadores comissarias que estão liberados para incluir Usuarios / Comissária
	 * @param entidade ( UserImportadorAgendamento )
	 * @return List<UserImportadorAgendamento>
	 * @serialData 13-09-2017
	 * @author murilonadalin 
	 */
	public List<UserImportadorComissaria> listaPorImportadorComiisarias(UserImportadorAgendamento entidade){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserImportadorComissaria.CAMPO_IMPORTADORCOMISSARIA, entidade.getId());     
		
		return super.retornaResultadoListaObjeto(UserImportadorComissaria.SQL_LISTA_COMISSARIAS, parameters, false);
	}

	/**
	 * Retorna a lista dos importadores comissarias que estão liberados para incluir Usuarios / Comissária
	 * @param entidade ( UserImportadorAgendamento )
	 * @return List<UserImportadorAgendamento>
	 * @serialData 13-09-2017
	 * @author murilonadalin 
	 */
	public List<UserImportadorComissaria> retornaComisariaDAO(CadastroComissario entidade){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserImportadorComissaria.CAMPO_COMISSARIA, entidade.getId());     
		
		return super.retornaResultadoListaObjeto(UserImportadorComissaria.SQL_LISTA_POR_COMISSARIAS, parameters, false);
	}
	
}
