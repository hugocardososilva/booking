package conexao.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seguranca.com.entidade.CadastroComissario;
import seguranca.com.entidade.User;
import seguranca.com.entidade.UserComissario;

public class UserComissarioDAO extends GenericDAO<UserComissario> {

	private static final long serialVersionUID = 1L;

	public UserComissarioDAO() {
		super(UserComissario.class);
	}

	public void delete(UserComissario entidade) {
		super.delete(entidade.getId(), UserComissario.class);
	}
	
	public List<UserComissario> pesquisaAutoComplete(String pesquisa){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserComissario.CAMPO_USUARIO, pesquisa);     
		
		return super.retornaResultadoListaObjeto(UserComissario.sql_pesquisa_autocomplete, parameters, false);
	}	

	public List<UserComissario> pesquisaUsersComissarias(CadastroComissario pesquisa){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserComissario.CAMPO_CADCOMISSARIO, pesquisa.getId());     
		
		return super.retornaResultadoListaObjeto(UserComissario.sql_pesquisa_users_comissarios, parameters, false);
	}	
	
	public List<UserComissario> listaComissariasPorUsuarios(User entidade){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(UserComissario.CAMPO_USUARIO, entidade.getId());     
		
		return super.retornaResultadoListaObjeto(UserComissario.SQL_LISTA_USUARIOS, parameters, false);
	}
	
}
