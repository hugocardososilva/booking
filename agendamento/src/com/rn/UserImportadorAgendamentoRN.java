package com.rn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.UserImportadorAgendamentoDAO;
import com.entidade.UserImportadorAgendamento;

import seguranca.com.entidade.CadastroComissario;
import seguranca.com.entidade.User;

/**
 * Class para controlar a regra de negocio 
 * @author murilonadalin
 * @since 13-09-2017
 */
public class UserImportadorAgendamentoRN {

	private UserImportadorAgendamentoDAO DAO;

	public UserImportadorAgendamentoDAO getDAO() {
		if (DAO == null) {
			DAO = new UserImportadorAgendamentoDAO();
		}
		return DAO;
	}

	public UserImportadorAgendamento localizarUsuarioString(String usuario) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", usuario);

		getDAO().beginTransaction();
		UserImportadorAgendamento entidade = getDAO().retornaResultadoObjeto(User.FILTRO_NAME, parameters);
		getDAO().closeTransaction();
		return entidade;
	}

	public UserImportadorAgendamento incluir(UserImportadorAgendamento item) throws Exception {
		getDAO().beginTransaction();
		try {
			getDAO().save(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();

		return localizar(item.getId());
	}

	public UserImportadorAgendamento alterar(UserImportadorAgendamento item) throws Exception {
		if (item.getUsuario() == null) {
			throw new Exception("Obrigatório selecionar um item!");
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

	public UserImportadorAgendamento localizar(int itemId) {
		getDAO().beginTransaction();
		UserImportadorAgendamento item = getDAO().localizarPorID(itemId);
		getDAO().closeTransaction();
		return item;
	}

	public void deletar(UserImportadorAgendamento id) {
		getDAO().beginTransaction();
		getDAO().deletaRegistrosObjeto(UserImportadorAgendamento.FILTRO_DELETE_REGISTRO, UserImportadorAgendamento.USERS_COMISSARIO_ID,
				id.getId().toString());
		getDAO().commitAndCloseTransaction();
	}

	public List<UserImportadorAgendamento> listaTodos() {
		getDAO().beginTransaction();
		List<UserImportadorAgendamento> result = getDAO().listaTodosRegistros();
		getDAO().closeTransaction();
		return result;
	}

	public List<UserImportadorAgendamento> listaTodosAutoComplete(User usr) {
		getDAO().beginTransaction();
		List<UserImportadorAgendamento> result = getDAO().pesquisaAutoComplete(Integer.toString(usr.getId()));
		getDAO().closeTransaction();
		return result;
	}

	public List<UserImportadorAgendamento> listaTodosUsuariosComissarias(CadastroComissario entidade) {
		getDAO().beginTransaction();
		List<UserImportadorAgendamento> result = getDAO().pesquisaUsersImportadores(entidade);
		getDAO().closeTransaction();
		return result;
	}

	/**
	 * Retorna a lista dos importadores que estão liberados para incluir Usuarios / Comissária
	 * @param entidade ( User )
	 * @return List<UserImportadorAgendamento>
	 * @serialData 13-09-2017
	 * @author murilonadalin 
	 */
	public List<UserImportadorAgendamento> listaPorUsuario(User entidade) {
		getDAO().beginTransaction();
		List<UserImportadorAgendamento> retorno = getDAO().listaPorUsuarios(entidade);
		getDAO().closeTransaction();
		return retorno;
	}

	/**
	 * Retorna a entidade do importador
	 * @param entidade ( User )
	 * @return UserImportadorAgendamento
	 * @serialData 13-09-2017
	 * @author murilonadalin 
	 */
	public UserImportadorAgendamento retornaImportadorUsuarioMaster(User entidade) {
		getDAO().beginTransaction();
		UserImportadorAgendamento retorno = getDAO().retornaImportadorUsuarioMaster(entidade);
		getDAO().closeTransaction();
		return retorno;
	}

}