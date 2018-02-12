package conexao.com.rn;

import java.util.List;

import conexao.com.dao.CadastroNcmDAO;
import seguranca.com.entidade.CadastroNcm;

public class CadastroNcmRN {
	
	private CadastroNcmDAO cadNCMDAO;
	
	public CadastroNcmDAO getNCMDAO() {
		if (cadNCMDAO == null) {
			cadNCMDAO = new CadastroNcmDAO();
		}
		return cadNCMDAO;
	}

	public CadastroNcm incluir(CadastroNcm item) throws Exception {
		getNCMDAO().beginTransaction();
		getNCMDAO().save(item);
		getNCMDAO().commitAndCloseTransaction();
		
		return localizar(item.getId());
	}

	public CadastroNcm alterar(CadastroNcm item) throws Exception {
		getNCMDAO().beginTransaction();
		item = getNCMDAO().alterar(item);
		getNCMDAO().commitAndCloseTransaction();
		
		return item;
	}

	public CadastroNcm localizar(int itemId) {
		getNCMDAO().beginTransaction();
		CadastroNcm item = getNCMDAO().localizarPorID(itemId);
		getNCMDAO().closeTransaction();
		return item;
	}

	public void deletar(CadastroNcm item) {
		getNCMDAO().beginTransaction();
		getNCMDAO().delete(item);
		getNCMDAO().commitAndCloseTransaction();
	}
	
	public List<CadastroNcm> listaTodos() {
		getNCMDAO().beginTransaction();
		List<CadastroNcm> result = getNCMDAO().listaTodosRegistros();
		getNCMDAO().closeTransaction();
		return result;
	}
	
	public List<CadastroNcm> listaTodosAutoComplete(String pesquisa) {
		getNCMDAO().beginTransaction();
		List<CadastroNcm> result = getNCMDAO().pesquisaAutoComplete(pesquisa);
		getNCMDAO().closeTransaction();
		return result;
	}
	
}