package conexao.com.rn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conexao.com.dao.CadastroComissarioDAO;
import net.sf.jasperreports.engine.type.SortOrderEnum;
import seguranca.com.entidade.CadastroComissario;

public class CadastroComissarioRN {
	
	private CadastroComissarioDAO userComissarioDAO;
	
	public CadastroComissarioDAO getCadastroComissarioDAO() {
		if (userComissarioDAO == null) {
			userComissarioDAO = new CadastroComissarioDAO();
		}
		return userComissarioDAO;
	}

	public CadastroComissario localizarEntidadeString(String campoPesquisa) throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nome", campoPesquisa);
		
		CadastroComissario entidade = null;
		getCadastroComissarioDAO().beginTransaction();
		try {
			entidade = getCadastroComissarioDAO().retornaResultadoObjeto(CadastroComissario.FILTRO_NAME, parameters);
		} catch (Exception e) {
			getCadastroComissarioDAO().rollback();
			throw new Exception(e.getMessage());
		}
		getCadastroComissarioDAO().closeTransaction();
		return entidade;
	}
	
	public CadastroComissario incluir(CadastroComissario item) throws Exception  {
		if (item.getNome().isEmpty()) {
			throw new Exception("Obrigatório informar Razão social!");
		}

		getCadastroComissarioDAO().beginTransaction();
		item.setNome(item.getNome().toUpperCase());
		getCadastroComissarioDAO().save(item);
		getCadastroComissarioDAO().commitAndCloseTransaction();
		
		return localizar(item.getId());
	}

	public CadastroComissario alterar(CadastroComissario item) throws Exception {
		if (item.getNome().isEmpty()) {
			throw new Exception("Obrigatório informar Razão social!");
		}

		getCadastroComissarioDAO().beginTransaction();
		item.setNome(item.getNome().toUpperCase());
		item = getCadastroComissarioDAO().alterar(item);
		getCadastroComissarioDAO().commitAndCloseTransaction();
		
		return item;
	}

	public CadastroComissario localizar(int itemId) throws Exception {
		CadastroComissario item = null;
		
		getCadastroComissarioDAO().beginTransaction();
		try {
			item = getCadastroComissarioDAO().localizarPorID(itemId);
		} catch (Exception e) {
			getCadastroComissarioDAO().rollback();
			throw new Exception(e.getMessage());
		}
		getCadastroComissarioDAO().closeTransaction();
		return item;
	}

	public void deletar(CadastroComissario item) {
		getCadastroComissarioDAO().beginTransaction();
		getCadastroComissarioDAO().delete(item);
		getCadastroComissarioDAO().commitAndCloseTransaction();
	}
	
	public List<CadastroComissario> listaTodos() throws Exception {
		List<CadastroComissario> result = null;
		
		getCadastroComissarioDAO().beginTransaction();
		try {
			Map<String, SortOrderEnum> parameters = new HashMap<String, SortOrderEnum>();
			parameters.put(CadastroComissario.COMISSARIO_NOME, SortOrderEnum.ASCENDING);     
			
			result = getCadastroComissarioDAO().listaTodosRegistrosOrdenacao(parameters);
		} catch (Exception e) {
			getCadastroComissarioDAO().rollback();
			throw new Exception(e.getMessage());
		}
		getCadastroComissarioDAO().closeTransaction();
		return result;
	}
	
	public List<CadastroComissario> listaTodosAutoComplete(String pesquisa) {
		getCadastroComissarioDAO().beginTransaction();
		List<CadastroComissario> result = getCadastroComissarioDAO().pesquisaAutoComplete(pesquisa);
		getCadastroComissarioDAO().closeTransaction();
		return result;
	}

	/**
	 * Efetua pesquisa por CNPJ
	 * @param pesquisa
	 * @return CadastroImportador 
	 */
	public CadastroComissario pesquisaPorCNPJ(String pesquisa) {
		getCadastroComissarioDAO().beginTransaction();
		CadastroComissario result = getCadastroComissarioDAO().pesquisaPorCNPJ(pesquisa.trim());
		getCadastroComissarioDAO().closeTransaction();
		return result;
	}
	
}