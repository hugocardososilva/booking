package conexao.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seguranca.com.entidade.CadastroEspecie;

public class CadastroEspecieDAO extends GenericDAO<CadastroEspecie> {

	private static final long serialVersionUID = 1L;

	public CadastroEspecieDAO() {
		super(CadastroEspecie.class);
	}

	public void delete(CadastroEspecie entidade) {
		super.delete(entidade.getId(), CadastroEspecie.class);
	}

	public List<CadastroEspecie> pesquisaAutoComplete(String pesquisa) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroEspecie.ESPECIE_ABREVIACAO, "%"+pesquisa+"%");     
		parameters.put(CadastroEspecie.ESPECIE_DESCRICAO, "%"+pesquisa+"%");     
		
		return super.retornaResultadoListaObjeto(CadastroEspecie.sql_pesquisa_autocomplete, parameters, false);
	}
}
