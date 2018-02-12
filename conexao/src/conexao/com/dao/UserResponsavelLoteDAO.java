package conexao.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seguranca.com.entidade.CadastroComissario;
import seguranca.com.entidade.User;
import seguranca.com.entidade.UserResponsavelLote;

public class UserResponsavelLoteDAO extends GenericDAO<UserResponsavelLote> {

	private static final long serialVersionUID = 1L;

	public UserResponsavelLoteDAO() {
		super(UserResponsavelLote.class);
	}

	public void delete(UserResponsavelLote entidade) {
		super.delete(entidade.getId(), UserResponsavelLote.class);
	}
	
	public List<UserResponsavelLote> pesquisaAutoComplete(String pesquisa){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserResponsavelLote.CAMPO_USUARIO, pesquisa);     
		
		return super.retornaResultadoListaObjeto(UserResponsavelLote.sql_pesquisa_autocomplete, parameters, false);
	}	

	public List<UserResponsavelLote> pesquisaUsersComissarias(CadastroComissario pesquisa){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserResponsavelLote.CAMPO_CADCOMISSARIO, pesquisa.getId());     
		
		return super.retornaResultadoListaObjeto(UserResponsavelLote.sql_pesquisa_users_respLote, parameters, false);
	}	
	
	public List<UserResponsavelLote> listaRespLotePorUsuarios(User entidade){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserResponsavelLote.CAMPO_USUARIO, entidade.getId());     
		
		return super.retornaResultadoListaObjeto(UserResponsavelLote.SQL_LISTA_USUARIOS, parameters, false);
	}
	
}
