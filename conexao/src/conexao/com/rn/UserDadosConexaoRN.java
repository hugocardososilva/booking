package conexao.com.rn;

import java.util.List;

import conexao.com.dao.UserDadosConexaoDAO;
import seguranca.com.entidade.UserDadosConexao;

public class UserDadosConexaoRN {
	
	private UserDadosConexaoDAO dao;
	
	public UserDadosConexaoDAO getDAO() {
		if (dao == null) {
			dao = new UserDadosConexaoDAO();
		}
		return dao;
	}

	public UserDadosConexao incluir(UserDadosConexao item) throws Exception {
		getDAO().beginTransaction();
		try {
			getDAO().save(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();
		
		return localizar(item.getId());
	}

	public UserDadosConexao alterar(UserDadosConexao item) throws Exception {
		getDAO().beginTransaction();
		try {
			item = getDAO().alterar(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();
		return item;
	}

	public UserDadosConexao localizar(int itemId) {
		getDAO().beginTransaction();
		UserDadosConexao item = getDAO().localizarPorID(itemId);
		getDAO().closeTransaction();
		return item;
	}
	
	public List<UserDadosConexao> listaTodos() {
		getDAO().beginTransaction();
		List<UserDadosConexao> result = getDAO().listaTodosRegistros();
		getDAO().closeTransaction();
		return result;
	}
}