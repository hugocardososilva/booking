package conexao.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seguranca.com.entidade.CadastroRepresentante;

public class CadastroRepresentanteDAO extends GenericDAO<CadastroRepresentante> {

	private static final long serialVersionUID = 1L;

	public CadastroRepresentanteDAO() {
		super(CadastroRepresentante.class);
	}

	public void delete(CadastroRepresentante entidade) {
		super.delete(entidade.getId(), CadastroRepresentante.class);
	}
	
	public List<CadastroRepresentante> pesquisaAutoComplete(String pesquisa) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroRepresentante.CAMPO_RAZAOSOCIAL, "%"+pesquisa+"%");     
		
		return super.retornaResultadoListaObjeto(CadastroRepresentante.sql_pesquisa_autocomplete, parameters, false);
	}
}
