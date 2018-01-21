package com.rn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.UserComissariaAgendamentoDAO;
import com.entidade.UserComissariaAgendamento;

import seguranca.com.entidade.CadastroComissario;
import seguranca.com.entidade.User;

/**
 * Class para controlar a regra de negocio 
 * @author murilonadalin
 * @since 13-09-2017
 */
public class UserComissariaAgendamentoRN {

	private UserComissariaAgendamentoDAO DAO;

	public UserComissariaAgendamentoDAO getDAO() {
		if (DAO == null) {
			DAO = new UserComissariaAgendamentoDAO();
		}
		return DAO;
	}

	public UserComissariaAgendamento localizarUsuarioString(String usuario) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", usuario);

		getDAO().beginTransaction();
		UserComissariaAgendamento entidade = getDAO().retornaResultadoObjeto(User.FILTRO_NAME, parameters);
		getDAO().closeTransaction();
		return entidade;
	}

	public UserComissariaAgendamento incluir(UserComissariaAgendamento item) throws Exception {
		getDAO().beginTransaction();
		try {
			getDAO().save(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();

		return localizar(item.getId());
	}

	public UserComissariaAgendamento alterar(UserComissariaAgendamento item) throws Exception {
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

	public UserComissariaAgendamento localizar(int itemId) {
		getDAO().beginTransaction();
		UserComissariaAgendamento item = getDAO().localizarPorID(itemId);
		getDAO().closeTransaction();
		return item;
	}

	public void deletar(UserComissariaAgendamento id) {
		getDAO().beginTransaction();
		getDAO().deletaRegistrosObjeto(UserComissariaAgendamento.FILTRO_DELETE_REGISTRO, UserComissariaAgendamento.USERS_COMISSARIO_ID,
				id.getId().toString());
		getDAO().commitAndCloseTransaction();
	}

	public List<UserComissariaAgendamento> listaTodos() {
		getDAO().beginTransaction();
		List<UserComissariaAgendamento> result = getDAO().listaTodosRegistros();
		getDAO().closeTransaction();
		return result;
	}

	public List<UserComissariaAgendamento> listaTodosAutoComplete(User usr) {
		getDAO().beginTransaction();
		List<UserComissariaAgendamento> result = getDAO().pesquisaAutoComplete(Integer.toString(usr.getId()));
		getDAO().closeTransaction();
		return result;
	}

	public List<UserComissariaAgendamento> listaTodosUsuariosComissarias(CadastroComissario entidade) {
		getDAO().beginTransaction();
		List<UserComissariaAgendamento> result = getDAO().pesquisaUsersImportadores(entidade);
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
	public List<UserComissariaAgendamento> listaPorUsuario(User entidade) {
		getDAO().beginTransaction();
		List<UserComissariaAgendamento> retorno = getDAO().listaPorUsuarios(entidade);
		getDAO().closeTransaction();
		return retorno;
	}

	public UserComissariaAgendamento retornaUsuarios(User entidade) {
		getDAO().beginTransaction();
		UserComissariaAgendamento retorno = getDAO().retornaUsuariosDAO(entidade);
		getDAO().closeTransaction();
		return retorno;
	}

}