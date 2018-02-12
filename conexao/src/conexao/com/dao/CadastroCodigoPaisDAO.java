package conexao.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seguranca.com.entidade.CadastroCodigoPais;

public class CadastroCodigoPaisDAO extends GenericDAO<CadastroCodigoPais> {

	private static final long serialVersionUID = 1L;

	public CadastroCodigoPaisDAO() {
		super(CadastroCodigoPais.class);
	}

	public void delete(CadastroCodigoPais entidade) {
		super.delete(entidade.getId(), CadastroCodigoPais.class);
	}
	
	/**
	 * Retorna a lista de consulta 
	 * @param pesquisa
	 * @param descricao
	 * @return List<CadastroCodigoPais>
	 */
	public List<CadastroCodigoPais> pesquisaAutoComplete(String pesquisa){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroCodigoPais.CODIGOPAIS_DESCRICAO_PAIS, "%"+pesquisa+"%");     
		parameters.put(CadastroCodigoPais.CODIGOPAIS_ABREVIACAOPAIS, "%"+pesquisa+"%");     
		
		return super.retornaResultadoListaObjeto(CadastroCodigoPais.sql_pesquisa_autocomplete, parameters, true);
	}

}
