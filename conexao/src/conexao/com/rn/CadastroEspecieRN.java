package conexao.com.rn;

import java.util.List;

import conexao.com.dao.CadastroEspecieDAO;
import seguranca.com.entidade.CadastroEspecie;

public class CadastroEspecieRN {
	
	private CadastroEspecieDAO cadEspecieDAO;
	
	public CadastroEspecieDAO getEspecieDAO() {
		if (cadEspecieDAO == null) {
			cadEspecieDAO = new CadastroEspecieDAO();
		}
		return cadEspecieDAO;
	}

	public CadastroEspecie incluir(CadastroEspecie item) throws Exception {
		getEspecieDAO().beginTransaction();
		getEspecieDAO().save(item);
		getEspecieDAO().commitAndCloseTransaction();
		
		return localizar(item.getId());
	}

	public CadastroEspecie alterar(CadastroEspecie item) throws Exception {
		getEspecieDAO().beginTransaction();
		item = getEspecieDAO().alterar(item);
		getEspecieDAO().commitAndCloseTransaction();
		
		return item;
	}

	public CadastroEspecie localizar(int itemId) {
		getEspecieDAO().beginTransaction();
		CadastroEspecie item = getEspecieDAO().localizarPorID(itemId);
		getEspecieDAO().closeTransaction();
		return item;
	}

	public void deletar(CadastroEspecie item) {
		getEspecieDAO().beginTransaction();
		getEspecieDAO().delete(item);
		getEspecieDAO().commitAndCloseTransaction();
	}
	
	public List<CadastroEspecie> listaTodos() {
		getEspecieDAO().beginTransaction();
		List<CadastroEspecie> result = getEspecieDAO().listaTodosRegistros();
		getEspecieDAO().closeTransaction();
		return result;
	}
	
	public List<CadastroEspecie> listaTodosAutoComplete(String pesquisa) {
		getEspecieDAO().beginTransaction();
		List<CadastroEspecie> result = getEspecieDAO().pesquisaAutoComplete(pesquisa);
		getEspecieDAO().closeTransaction();
		return result;
	}
	
}