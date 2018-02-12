package conexao.com.rn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conexao.com.dao.CadastroImportadorDAO;
import net.sf.jasperreports.engine.type.SortOrderEnum;
import seguranca.com.entidade.CadastroImportador;

public class CadastroImportadorRN {
	
	private CadastroImportadorDAO cadImportadorDAO;
	
	public CadastroImportadorDAO getDAO() {
		if (cadImportadorDAO == null) {
			cadImportadorDAO = new CadastroImportadorDAO();
		}
		return cadImportadorDAO;
	}

	public CadastroImportador incluir(CadastroImportador item) throws Exception {
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

	public CadastroImportador alterar(CadastroImportador item) throws Exception {
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

	public CadastroImportador localizar(int itemId) {
		getDAO().beginTransaction();
		CadastroImportador item = getDAO().localizarPorID(itemId);
		getDAO().closeTransaction();
		return item;
	}

	public void deletar(CadastroImportador item) {
		getDAO().beginTransaction();
		getDAO().delete(item);
		getDAO().commitAndCloseTransaction();
	}
	
	public List<CadastroImportador> listaTodos() {
		Map<String, SortOrderEnum> parameters = new HashMap<String, SortOrderEnum>();
		parameters.put(CadastroImportador.CAMPO_RAZAOSOCIAL, SortOrderEnum.ASCENDING);     

		getDAO().beginTransaction();
		List<CadastroImportador> result = getDAO().listaTodosRegistrosOrdenacao(parameters);
		getDAO().closeTransaction();
		return result;
	}
	
	public List<CadastroImportador> listaTodosAutoComplete(String pesquisa) {
		getDAO().beginTransaction();
		List<CadastroImportador> result = getDAO().pesquisaAutoComplete(pesquisa);
		getDAO().closeTransaction();
		return result;
	}

	/**
	 * Efetua pesquisa por CNPJ
	 * @param pesquisa
	 * @return CadastroImportador 
	 */
	public CadastroImportador pesquisaPorCNPJ(String pesquisa) {
		getDAO().beginTransaction();
		CadastroImportador result = getDAO().pesquisaPorCNPJ(pesquisa.trim());
		getDAO().closeTransaction();
		return result;
	}

	
}