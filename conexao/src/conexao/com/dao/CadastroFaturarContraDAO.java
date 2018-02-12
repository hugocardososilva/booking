package conexao.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seguranca.com.entidade.CadastroFaturarContra;

public class CadastroFaturarContraDAO extends GenericDAO<CadastroFaturarContra> {

	private static final long serialVersionUID = 1L;

	public CadastroFaturarContraDAO() {
		super(CadastroFaturarContra.class);
	}

	public void delete(CadastroFaturarContra entidade) {
		super.delete(entidade.getId(), CadastroFaturarContra.class);
	}
	
	public List<CadastroFaturarContra> pesquisaAutoComplete(String pesquisa) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroFaturarContra.CAMPO_RAZAOSOCIAL, "%"+pesquisa+"%");     
		
		return super.retornaResultadoListaObjeto(CadastroFaturarContra.sql_pesquisa_autocomplete, parameters, false);
	}
}
