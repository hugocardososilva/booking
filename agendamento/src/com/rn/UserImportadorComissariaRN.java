package com.rn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.UserImportadorComissariaDAO;
import com.entidade.UserImportadorAgendamento;
import com.entidade.UserImportadorComissaria;

import seguranca.com.entidade.CadastroComissario;
import seguranca.com.entidade.User;

/**
 * Class para controlar a regra de negocio 
 * @author murilonadalin
 * @since 13-09-2017
 */
public class UserImportadorComissariaRN {

	private UserImportadorComissariaDAO DAO;

	public UserImportadorComissariaDAO getDAO() {
		if (DAO == null) {
			DAO = new UserImportadorComissariaDAO();
		}
		return DAO;
	}

	public UserImportadorComissaria localizarUsuarioString(String usuario) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", usuario);

		getDAO().beginTransaction();
		UserImportadorComissaria entidade = getDAO().retornaResultadoObjeto(User.FILTRO_NAME, parameters);
		getDAO().closeTransaction();
		return entidade;
	}

	public UserImportadorComissaria incluir(UserImportadorComissaria item) throws Exception {
		getDAO().beginTransaction();
		try {
			getDAO().save(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();

		return localizar(item.getId());
	}

	public UserImportadorComissaria alterar(UserImportadorComissaria item) throws Exception {

		if (item.getComissaria() == null && item.getNotifyRespLote() == null) {
			throw new Exception("Obrigatorio informar uma Comissaria ou Notify !");
		}
		
		getDAO().beginTransaction();
		try {
			item = getDAO().alterar(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();
		return item;
	}

	public UserImportadorComissaria localizar(int itemId) {
		getDAO().beginTransaction();
		UserImportadorComissaria item = getDAO().localizarPorID(itemId);
		getDAO().closeTransaction();
		return item;
	}

	public void deletar(UserImportadorComissaria id) {
		getDAO().beginTransaction();
		getDAO().deletaRegistrosObjeto(UserImportadorComissaria.FILTRO_DELETE_REGISTRO, UserImportadorComissaria.USERS_COMISSARIO_ID,
				id.getId().toString());
		getDAO().commitAndCloseTransaction();
	}

	public List<UserImportadorComissaria> listaTodos() {
		getDAO().beginTransaction();
		List<UserImportadorComissaria> result = getDAO().listaTodosRegistros();
		getDAO().closeTransaction();
		return result;
	}

	public List<UserImportadorComissaria> listaTodosAutoComplete(User usr) {
		getDAO().beginTransaction();
		List<UserImportadorComissaria> result = getDAO().pesquisaAutoComplete(Integer.toString(usr.getId()));
		getDAO().closeTransaction();
		return result;
	}

	public List<UserImportadorComissaria> listaTodosUsuariosComissarias(CadastroComissario entidade) {
		getDAO().beginTransaction();
		List<UserImportadorComissaria> result = getDAO().pesquisaUsersImportadores(entidade);
		getDAO().closeTransaction();
		return result;
	}

	public List<UserImportadorComissaria> listaPorImportadorComiisarias(UserImportadorAgendamento entidade) {
		getDAO().beginTransaction();
		List<UserImportadorComissaria> result = getDAO().listaPorImportadorComiisarias(entidade);
		getDAO().closeTransaction();
		return result;
	}

	public List<UserImportadorComissaria> retornaComisarias(CadastroComissario entidade){
		getDAO().beginTransaction();
		List<UserImportadorComissaria> result = getDAO().retornaComisariaDAO(entidade);
		getDAO().closeTransaction();
		return result;
	}


}