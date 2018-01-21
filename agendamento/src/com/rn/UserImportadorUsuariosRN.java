package com.rn;

import java.util.List;

import com.dao.UserImportadorUsuariosDAO;
import com.entidade.UserImportadorUsuarios;

import seguranca.com.entidade.User;

/**
 * Class para controlar a regra de negocio 
 * @author murilonadalin
 * @since 13-09-2017
 */
public class UserImportadorUsuariosRN {

	private UserImportadorUsuariosDAO DAO;

	public UserImportadorUsuariosDAO getDAO() {
		if (DAO == null) {
			DAO = new UserImportadorUsuariosDAO();
		}
		return DAO;
	}

	public UserImportadorUsuarios incluir(UserImportadorUsuarios item) throws Exception {
		getDAO().beginTransaction();
		try {
			getDAO().save(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();

		return localizar(item.getId());
	}

	public UserImportadorUsuarios alterar(UserImportadorUsuarios item) throws Exception {
		if (item.getUser() == null) {
			throw new Exception("Obrigatorio selecionar um item!");
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

	public UserImportadorUsuarios localizar(int itemId) {
		getDAO().beginTransaction();
		UserImportadorUsuarios item = getDAO().localizarPorID(itemId);
		getDAO().closeTransaction();
		return item;
	}

	public void deletar(UserImportadorUsuarios id) {
		getDAO().beginTransaction();
		getDAO().deletaRegistrosObjeto(UserImportadorUsuarios.FILTRO_DELETE_REGISTRO, UserImportadorUsuarios.USERS_COMISSARIO_ID,
				id.getId().toString());
		getDAO().commitAndCloseTransaction();
	}

	public List<UserImportadorUsuarios> listaTodos() {
		getDAO().beginTransaction();
		List<UserImportadorUsuarios> result = getDAO().listaTodosRegistros();
		getDAO().closeTransaction();
		return result;
	}

	/**
	 * Retorna a entidade do importador master
	 * @param entidade ( User )
	 * @return UserImportadorAgendamento
	 * @serialData 13-09-2017
	 * @author murilonadalin 
	 */
	public UserImportadorUsuarios retornaImportadorUsuarioMaster(User entidade) {
		getDAO().beginTransaction();
		UserImportadorUsuarios retorno = getDAO().retornaImportadorUsuarioMaster(entidade);
		getDAO().closeTransaction();
		return retorno;
	}
	

}