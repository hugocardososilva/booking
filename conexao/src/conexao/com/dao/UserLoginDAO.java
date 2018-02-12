package conexao.com.dao;

import seguranca.com.entidade.UserLoginLogaut;

public class UserLoginDAO extends GenericDAO<UserLoginLogaut> {

	private static final long serialVersionUID = 1L;

	public UserLoginDAO() {
		super(UserLoginLogaut.class);
	}

	public void delete(UserLoginLogaut entidade) {
		super.delete(entidade.getId(), UserLoginLogaut.class);
	}
	
}
