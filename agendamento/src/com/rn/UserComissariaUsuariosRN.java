package com.rn;

import java.util.List;

import com.dao.UserComissariaUsuariosDAO;
import com.entidade.UserComissariaAgendamento;
import com.entidade.UserComissariaUsuarios;

import seguranca.com.entidade.User;

/**
 * Class para controlar a regra de negocio 
 * @author murilonadalin
 * @since 13-09-2017
 */
public class UserComissariaUsuariosRN {

	private UserComissariaUsuariosDAO DAO;

	public UserComissariaUsuariosDAO getDAO() {
		if (DAO == null) {
			DAO = new UserComissariaUsuariosDAO();
		}
		return DAO;
	}

	public UserComissariaUsuarios incluir(UserComissariaUsuarios item) throws Exception {
		getDAO().beginTransaction();
		try {
			getDAO().save(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();

		return localizar(item.getId());
	}

	public UserComissariaUsuarios alterar(UserComissariaUsuarios item) throws Exception {
		if (item.getUser() == null) {
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

	public UserComissariaUsuarios localizar(int itemId) {
		getDAO().beginTransaction();
		UserComissariaUsuarios item = getDAO().localizarPorID(itemId);
		getDAO().closeTransaction();
		return item;
	}

	public void deletar(UserComissariaUsuarios id) {
		getDAO().beginTransaction();
		getDAO().deletaRegistrosObjeto(UserComissariaUsuarios.FILTRO_DELETE_REGISTRO, UserComissariaUsuarios.USERS_COMISSARIO_ID,
				id.getId().toString());
		getDAO().commitAndCloseTransaction();
	}

	public List<UserComissariaUsuarios> listaTodos() {
		getDAO().beginTransaction();
		List<UserComissariaUsuarios> result = getDAO().listaTodosRegistros();
		getDAO().closeTransaction();
		return result;
	}

	/**
	 * Retorna a entidade "UserComissariaUsuarios ", que estão vinculados a comissária
	 * @param entidade ( User )
	 * @return UserComissariaUsuario
	 * @author murilonadalin
	 * @serialData 19-09-2017
	 */
	public UserComissariaUsuarios retornaUsuarioComissaria(User entidade) {
		getDAO().beginTransaction();
		UserComissariaUsuarios retorno = getDAO().retornaUsuarioComissariaDAO(entidade);
		getDAO().closeTransaction();
		return retorno;
	}

	public UserComissariaUsuarios retornaUsuarioComissariaMaster(UserComissariaAgendamento entidade) {
		getDAO().beginTransaction();
		UserComissariaUsuarios retorno = getDAO().retornaUsuarioComissariaMasterDAO(entidade);
		getDAO().closeTransaction();
		return retorno;
	}
	

}