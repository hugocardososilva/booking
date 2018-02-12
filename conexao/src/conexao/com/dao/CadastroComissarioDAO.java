package conexao.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seguranca.com.entidade.CadastroComissario;

public class CadastroComissarioDAO extends GenericDAO<CadastroComissario> {

	private static final long serialVersionUID = 1L;

	public CadastroComissarioDAO() {
		super(CadastroComissario.class);
	}

	public void delete(CadastroComissario entidade) {
		super.delete(entidade.getId(), CadastroComissario.class);
	}

	/**
	 * Efetua pesquisa por CNPJ
	 * @param pesquisa
	 * @return CadastroImportador 
	 */
	public CadastroComissario pesquisaPorCNPJ(String pesquisa) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroComissario.COMISSARIO_CPF, pesquisa);     
		
		return super.retornaResultadoObjeto(CadastroComissario.FILTRO_CNPJ, parameters);
	}
	
	public List<CadastroComissario> pesquisaAutoComplete(String pesquisa) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroComissario.COMISSARIO_NOME, "%"+pesquisa+"%");     
		
		return super.retornaResultadoListaObjeto(CadastroComissario.sql_pesquisa_autocomplete, parameters, false);
	}
	
}
