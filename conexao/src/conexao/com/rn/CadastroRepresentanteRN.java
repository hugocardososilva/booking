package conexao.com.rn;

import java.util.List;

import conexao.com.dao.CadastroRepresentanteDAO;
import seguranca.com.entidade.CadastroRepresentante;

public class CadastroRepresentanteRN {
	
	private CadastroRepresentanteDAO DAO;
	
	public CadastroRepresentanteDAO getDAO() {
		if (DAO == null) {
			DAO = new CadastroRepresentanteDAO();
		}
		return DAO;
	}

	public CadastroRepresentante incluir(CadastroRepresentante item) throws Exception {
		if (item.getRazaoSocial().isEmpty()) {
			throw new Exception("Obrigatório informar Razão social!");
		}

		getDAO().beginTransaction();
		item.setRazaoSocial(item.getRazaoSocial().toUpperCase());
		try {
			getDAO().save(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();
		
		return localizar(item.getId());
	}

	public CadastroRepresentante alterar(CadastroRepresentante item) throws Exception {
		if (item.getRazaoSocial().isEmpty()) {
			throw new Exception("Obrigatório informar Razão social!");
		}

		getDAO().beginTransaction();
		item.setRazaoSocial(item.getRazaoSocial().toUpperCase());
		try {
			item = getDAO().alterar(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();
		
		return item;
	}

	public CadastroRepresentante localizar(int itemId) {
		getDAO().beginTransaction();
		CadastroRepresentante item = getDAO().localizarPorID(itemId);
		getDAO().closeTransaction();
		return item;
	}

	public void deletar(CadastroRepresentante item) {
		getDAO().beginTransaction();
		getDAO().delete(item);
		getDAO().commitAndCloseTransaction();
	}
	
	public List<CadastroRepresentante> listaTodos() {
		getDAO().beginTransaction();
		List<CadastroRepresentante> result = getDAO().listaTodosRegistros();
		getDAO().closeTransaction();
		return result;
	}
	
	public List<CadastroRepresentante> listaTodosAutoComplete(String pesquisa) {
		getDAO().beginTransaction();
		List<CadastroRepresentante> result = getDAO().pesquisaAutoComplete(pesquisa);
		getDAO().closeTransaction();
		return result;
	}

	
}