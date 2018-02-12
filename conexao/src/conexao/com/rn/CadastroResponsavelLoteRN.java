package conexao.com.rn;

import java.util.List;

import conexao.com.dao.CadastroResponsavelLoteDAO;
import seguranca.com.entidade.CadastroResponsavelLote;

public class CadastroResponsavelLoteRN {
	
	private CadastroResponsavelLoteDAO DAO;
	
	public CadastroResponsavelLoteDAO getDAO() {
		if (DAO == null) {
			DAO = new CadastroResponsavelLoteDAO();
		}
		return DAO;
	}

	public CadastroResponsavelLote incluir(CadastroResponsavelLote item) throws Exception {
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

	public CadastroResponsavelLote alterar(CadastroResponsavelLote item) throws Exception {
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

	public CadastroResponsavelLote localizar(int itemId) {
		getDAO().beginTransaction();
		CadastroResponsavelLote item = getDAO().localizarPorID(itemId);
		getDAO().closeTransaction();
		return item;
	}

	public void deletar(CadastroResponsavelLote item) {
		getDAO().beginTransaction();
		getDAO().delete(item);
		getDAO().commitAndCloseTransaction();
	}
	
	public List<CadastroResponsavelLote> listaTodos() {
		getDAO().beginTransaction();
		List<CadastroResponsavelLote> result = getDAO().listaTodosRegistros();
		getDAO().closeTransaction();
		return result;
	}
	
	public List<CadastroResponsavelLote> listaTodosAutoComplete(String pesquisa) {
		getDAO().beginTransaction();
		List<CadastroResponsavelLote> result = getDAO().pesquisaAutoComplete(pesquisa);
		getDAO().closeTransaction();
		return result;
	}

	/**
	 * Efetua pesquisa por CNPJ
	 * @param pesquisa
	 * @return CadastroImportador 
	 */
	public CadastroResponsavelLote pesquisaPorCNPJ(String pesquisa) {
		getDAO().beginTransaction();
		CadastroResponsavelLote result = getDAO().pesquisaPorCNPJ(pesquisa.trim());
		getDAO().closeTransaction();
		return result;
	}
	
	
}