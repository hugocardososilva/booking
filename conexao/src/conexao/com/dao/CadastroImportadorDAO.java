package conexao.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seguranca.com.entidade.CadastroImportador;

public class CadastroImportadorDAO extends GenericDAO<CadastroImportador> {

	private static final long serialVersionUID = 1L;

	public CadastroImportadorDAO() {
		super(CadastroImportador.class);
	}

	public void delete(CadastroImportador entidade) {
		super.delete(entidade.getId(), CadastroImportador.class);
	}
	
	public List<CadastroImportador> pesquisaAutoComplete(String pesquisa) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroImportador.CAMPO_RAZAOSOCIAL, "%"+pesquisa+"%");     
		
		return super.retornaResultadoListaObjeto(CadastroImportador.sql_pesquisa_autocomplete, parameters, false);
	}

	/**
	 * Efetua pesquisa por CNPJ
	 * @param pesquisa
	 * @return CadastroImportador 
	 */
	public CadastroImportador pesquisaPorCNPJ(String pesquisa) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroImportador.CAMPO_CNPJ, pesquisa);     
		
		return super.retornaResultadoObjeto(CadastroImportador.FILTRO_CNPJ, parameters);
	}
}
