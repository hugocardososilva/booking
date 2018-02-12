package conexao.com.rn;

import java.util.List;

import conexao.com.dao.UserLoginDAO;
import seguranca.com.entidade.User;
import seguranca.com.entidade.UserLoginLogaut;
import seguranca.com.enums.TipoLoginEnum;

public class UserLoginRN {
	
	private UserLoginDAO DAO;
	
	public UserLoginDAO getDAO() {
		if (DAO == null) {
			DAO = new UserLoginDAO();
		}
		return DAO;
	}

	public UserLoginLogaut incluir(UserLoginLogaut item) throws Exception {
		getDAO().beginTransaction();
		try {
			getDAO().save(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();
		
		return localizar(item.getId());
	}

	public UserLoginLogaut alterar(User item, TipoLoginEnum tipo) throws Exception {
		UserLoginLogaut usrLogin = new UserLoginLogaut();
		getDAO().beginTransaction();
		try {
			usrLogin.setUsuarioVinculado(item);
			usrLogin.setTipoLoginEnum(tipo);
			
			usrLogin = getDAO().alterar(usrLogin);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();
		return usrLogin;
	}

	public UserLoginLogaut localizar(int itemId) {
		getDAO().beginTransaction();
		UserLoginLogaut item = getDAO().localizarPorID(itemId);
		getDAO().closeTransaction();
		return item;
	}
	
	public List<UserLoginLogaut> listaTodos() {
		getDAO().beginTransaction();
		List<UserLoginLogaut> result = getDAO().listaTodosRegistros();
		getDAO().closeTransaction();
		return result;
	}
}