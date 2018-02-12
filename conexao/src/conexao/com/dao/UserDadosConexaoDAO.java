package conexao.com.dao;

import seguranca.com.entidade.UserDadosConexao;

public class UserDadosConexaoDAO extends GenericDAO<UserDadosConexao> {

	private static final long serialVersionUID = 1L;

	public UserDadosConexaoDAO() {
		super(UserDadosConexao.class);
	}

	public void delete(UserDadosConexao entidade) {
		super.delete(entidade.getId(), UserDadosConexao.class);
	}
}
