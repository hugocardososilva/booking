package conexao.com.rn;

import java.util.List;

import conexao.com.dao.CadastroFaturarContraDAO;
import seguranca.com.entidade.CadastroFaturarContra;

public class CadastroFaturarContraRN {
	
	private CadastroFaturarContraDAO DAO;
	
	public CadastroFaturarContraDAO getDAO() {
		if (DAO == null) {
			DAO = new CadastroFaturarContraDAO();
		}
		return DAO;
	}

	public CadastroFaturarContra incluir(CadastroFaturarContra item) throws Exception {
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

	public CadastroFaturarContra alterar(CadastroFaturarContra item) throws Exception {
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

	public CadastroFaturarContra salvarSemFecharTransacao(CadastroFaturarContra item) throws Exception {
		getDAO().beginTransaction();
		item.setRazaoSocial(item.getRazaoSocial().toUpperCase());
		try {
			item = getDAO().alterar(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commit();
		
		return item;
	}

	public CadastroFaturarContra localizar(int itemId) {
		getDAO().beginTransaction();
		CadastroFaturarContra item = getDAO().localizarPorID(itemId);
		getDAO().closeTransaction();
		return item;
	}

	public void deletar(CadastroFaturarContra item) {
		getDAO().beginTransaction();
		getDAO().delete(item);
		getDAO().commitAndCloseTransaction();
	}
	
	public List<CadastroFaturarContra> listaTodos() {
		getDAO().beginTransaction();
		List<CadastroFaturarContra> result = getDAO().listaTodosRegistros();
		getDAO().closeTransaction();
		return result;
	}
	
	public List<CadastroFaturarContra> listaTodosAutoComplete(String pesquisa) throws Exception {
		List<CadastroFaturarContra> result = null;
		
		getDAO().beginTransaction();
		try {
			result = getDAO().pesquisaAutoComplete(pesquisa);
		} catch (Exception e) {
			getDAO().rollback();
			throw new Exception(e.getMessage());
		}
		getDAO().closeTransaction();
		return result;
	}

	
}