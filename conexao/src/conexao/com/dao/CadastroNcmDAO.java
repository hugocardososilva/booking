package conexao.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seguranca.com.entidade.CadastroNcm;

public class CadastroNcmDAO extends GenericDAO<CadastroNcm> {

	private static final long serialVersionUID = 1L;

	public CadastroNcmDAO() {
		super(CadastroNcm.class);
	}

	public void delete(CadastroNcm entidade) {
		super.delete(entidade.getId(), CadastroNcm.class);
	}

	/**
	 * Retorna a lista de consulta 
	 * @param pais
	 * @param descricao
	 * @param pesquisa
	 * @return List<CadastroCodigoPais>
	 */
	public List<CadastroNcm> pesquisaAutoComplete(String pesquisa) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroNcm.NCM_ABREVIACAO, "%"+pesquisa+"%");     
		
		return super.retornaResultadoListaObjeto(CadastroNcm.sql_pesquisa_autocomplete, parameters, false);
	}
	
}
