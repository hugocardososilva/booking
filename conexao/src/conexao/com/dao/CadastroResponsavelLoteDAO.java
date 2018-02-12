package conexao.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seguranca.com.entidade.CadastroResponsavelLote;

public class CadastroResponsavelLoteDAO extends GenericDAO<CadastroResponsavelLote> {

	private static final long serialVersionUID = 1L;

	public CadastroResponsavelLoteDAO() {
		super(CadastroResponsavelLote.class);
	}

	public void delete(CadastroResponsavelLote entidade) {
		super.delete(entidade.getId(), CadastroResponsavelLote.class);
	}
	
	public List<CadastroResponsavelLote> pesquisaAutoComplete(String pesquisa) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroResponsavelLote.CAMPO_RAZAOSOCIAL, "%"+pesquisa+"%");     
		
		return super.retornaResultadoListaObjeto(CadastroResponsavelLote.sql_pesquisa_autocomplete, parameters, false);
	}
	
	/**
	 * Efetua pesquisa por CNPJ
	 * @param pesquisa
	 * @return CadastroImportador 
	 */
	public CadastroResponsavelLote pesquisaPorCNPJ(String pesquisa) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroResponsavelLote.CAMPO_CNPJ, pesquisa);     
		
		return super.retornaResultadoObjeto(CadastroResponsavelLote.FILTRO_CNPJ, parameters);
	}
	
}
