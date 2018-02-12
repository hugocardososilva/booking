package conexao.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seguranca.com.entidade.CadastroComissario;
import seguranca.com.entidade.User;
import seguranca.com.entidade.UserImportador;

public class UserImportadorDAO extends GenericDAO<UserImportador> {

	private static final long serialVersionUID = 1L;

	public UserImportadorDAO() {
		super(UserImportador.class);
	}

	public void delete(UserImportador entidade) {
		super.delete(entidade.getId(), UserImportador.class);
	}
	
	public List<UserImportador> pesquisaAutoComplete(String pesquisa){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserImportador.CAMPO_USUARIO, pesquisa);     
		
		return super.retornaResultadoListaObjeto(UserImportador.sql_pesquisa_autocomplete, parameters, false);
	}	

	public List<UserImportador> pesquisaUsersImportadores(CadastroComissario pesquisa){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserImportador.CAMPO_IMPORTADOR, pesquisa.getId());     
		
		return super.retornaResultadoListaObjeto(UserImportador.sql_pesquisa_users_comissarios, parameters, false);
	}	
	
	public List<UserImportador> listaComissariasPorUsuarios(User entidade){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserImportador.CAMPO_USUARIO, entidade.getId());     
		
		return super.retornaResultadoListaObjeto(UserImportador.SQL_LISTA_USUARIOS, parameters, false);
	}
	
}
