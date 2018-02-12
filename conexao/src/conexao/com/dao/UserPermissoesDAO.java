package conexao.com.dao;

import conexao.com.dao.GenericDAO;
import seguranca.com.entidade.UserPermissoes;;

public class UserPermissoesDAO extends GenericDAO<UserPermissoes> {

	private static final long serialVersionUID = 1L;

	public UserPermissoesDAO() {
		super(UserPermissoes.class);
	}

	public void delete(UserPermissoes entidade) {
		super.delete(entidade.getId(), UserPermissoes.class);
	}

}
