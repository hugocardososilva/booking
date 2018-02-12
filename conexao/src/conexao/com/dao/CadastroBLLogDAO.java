package conexao.com.dao;

import seguranca.com.entidade.CadastroBlLogAlteracao;

public class CadastroBLLogDAO extends GenericDAO<CadastroBlLogAlteracao> {

	private static final long serialVersionUID = 1L;

	public CadastroBLLogDAO() {
		super(CadastroBlLogAlteracao.class);
	}

	public void delete(CadastroBlLogAlteracao entidade) {
		super.delete(entidade.getId(), CadastroBlLogAlteracao.class);
	}

}
