package conexao.com.rn;

import java.util.List;

import conexao.com.dao.CadastroPortosDAO;
import seguranca.com.entidade.CadastroPortos;

public class CadastroPortosRN {
	
	private CadastroPortosDAO DAO;
	
	public CadastroPortosDAO getDAO() {
		if (DAO == null) {
			DAO = new CadastroPortosDAO();
		}
		return DAO;
	}

	public CadastroPortos incluir(CadastroPortos item) throws Exception {
		getDAO().beginTransaction();
		getDAO().save(item);
		getDAO().commitAndCloseTransaction();
		
		return localizar(item.getId());
	}

	public CadastroPortos alterar(CadastroPortos item) throws Exception {
		getDAO().beginTransaction();
		item = getDAO().alterar(item);
		getDAO().commitAndCloseTransaction();
		
		return item;
	}

	public CadastroPortos localizar(int itemId) {
		getDAO().beginTransaction();
		CadastroPortos item = getDAO().localizarPorID(itemId);
		getDAO().closeTransaction();
		return item;
	}

	public void deletar(CadastroPortos item) {
		getDAO().beginTransaction();
		getDAO().delete(item);
		getDAO().commitAndCloseTransaction();
	}
	
	public List<CadastroPortos> listaTodos() {
		getDAO().beginTransaction();
		List<CadastroPortos> result = getDAO().listaTodosRegistros();
		getDAO().closeTransaction();
		return result;
	}
	
	public List<CadastroPortos> listaTodosAutoComplete(String pesquisa) {
		getDAO().beginTransaction();
		List<CadastroPortos> result = getDAO().pesquisaAutoComplete(pesquisa);
		getDAO().closeTransaction();
		return result;
	}

	
}