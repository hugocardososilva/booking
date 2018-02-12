package conexao.com.rn;

import java.util.List;

import conexao.com.dao.CadastroCodigoPaisDAO;
import seguranca.com.entidade.CadastroCodigoPais;

public class CadastroCodigoPaisRN {
	
	private CadastroCodigoPaisDAO cadPaisDAO;
	
	public CadastroCodigoPaisDAO getPaisDAO() {
		if (cadPaisDAO == null) {
			cadPaisDAO = new CadastroCodigoPaisDAO();
		}
		return cadPaisDAO;
	}

	public CadastroCodigoPais incluir(CadastroCodigoPais item) throws Exception {
		getPaisDAO().beginTransaction();
		getPaisDAO().save(item);
		getPaisDAO().commitAndCloseTransaction();
		
		return localizar(item.getId());
	}

	public CadastroCodigoPais alterar(CadastroCodigoPais item) throws Exception {
		getPaisDAO().beginTransaction();
		item = getPaisDAO().alterar(item);
		getPaisDAO().commitAndCloseTransaction();
		
		return item;
	}

	public CadastroCodigoPais localizar(int itemId) {
		getPaisDAO().beginTransaction();
		CadastroCodigoPais item = getPaisDAO().localizarPorID(itemId);
		getPaisDAO().closeTransaction();
		return item;
	}

	public void deletar(CadastroCodigoPais item) {
		getPaisDAO().beginTransaction();
		getPaisDAO().delete(item);
		getPaisDAO().commitAndCloseTransaction();
	}
	
	public List<CadastroCodigoPais> listaTodos() {
		getPaisDAO().beginTransaction();
		List<CadastroCodigoPais> result = getPaisDAO().listaTodosRegistros();
		getPaisDAO().closeTransaction();
		return result;
	}

	public List<CadastroCodigoPais> listaTodosAutoComplete(String pesquisa) {
		getPaisDAO().beginTransaction();
		List<CadastroCodigoPais> result = getPaisDAO().pesquisaAutoComplete(pesquisa);
		getPaisDAO().closeTransaction();
		return result;
	}
}