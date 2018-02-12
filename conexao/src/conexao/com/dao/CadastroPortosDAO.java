package conexao.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seguranca.com.entidade.CadastroPortos;

public class CadastroPortosDAO extends GenericDAO<CadastroPortos> {

	private static final long serialVersionUID = 1L;

	public CadastroPortosDAO() {
		super(CadastroPortos.class);
	}

	public void delete(CadastroPortos entidade) {
		super.delete(entidade.getId(), CadastroPortos.class);
	}
	
	public List<CadastroPortos> pesquisaAutoComplete(String pesquisa) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroPortos.CAMPO_NOME, "%"+pesquisa+"%");     
		
		return super.retornaResultadoListaObjeto(CadastroPortos.sql_pesquisa_autocomplete, parameters, false);
	}
}
