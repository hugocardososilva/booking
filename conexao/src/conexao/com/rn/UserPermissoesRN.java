package conexao.com.rn;

import java.util.List;

import conexao.com.dao.UserPermissoesDAO;
import seguranca.com.entidade.UserPermissoes;

public class UserPermissoesRN {
	
	private UserPermissoesDAO userPermissoesDAO;
	
	public UserPermissoesDAO getUserPermissoesDAO() {
		if (userPermissoesDAO == null) {
			userPermissoesDAO = new UserPermissoesDAO();
		}
		return userPermissoesDAO;
	}

	public UserPermissoes incluir(UserPermissoes item) throws Exception {
		getUserPermissoesDAO().beginTransaction();
		try {
			item = getUserPermissoesDAO().alterar(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getUserPermissoesDAO().commitAndCloseTransaction();
		
		return item;
	}

	public UserPermissoes alterar(UserPermissoes item) throws Exception {
		getUserPermissoesDAO().beginTransaction();
		try {
			item = getUserPermissoesDAO().alterar(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getUserPermissoesDAO().commitAndCloseTransaction();
		
		return item;
	}

	public UserPermissoes localizar(int itemId) {
		getUserPermissoesDAO().beginTransaction();
		UserPermissoes item = getUserPermissoesDAO().localizarPorID(itemId);
		getUserPermissoesDAO().closeTransaction();
		return item;
	}

	public void deletar(UserPermissoes item) {
		getUserPermissoesDAO().beginTransaction();
		getUserPermissoesDAO().delete(item);
		getUserPermissoesDAO().commitAndCloseTransaction();
	}
	
	public List<UserPermissoes> listaTodos() {
		getUserPermissoesDAO().beginTransaction();
		List<UserPermissoes> result = getUserPermissoesDAO().listaTodosRegistros();
		getUserPermissoesDAO().closeTransaction();
		return result;
	}
}