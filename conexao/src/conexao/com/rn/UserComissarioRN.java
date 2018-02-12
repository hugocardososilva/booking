package conexao.com.rn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conexao.com.dao.UserComissarioDAO;
import seguranca.com.entidade.CadastroComissario;
import seguranca.com.entidade.User;
import seguranca.com.entidade.UserComissario;

public class UserComissarioRN {
	
	private UserComissarioDAO userComissarioDAO;
	
	public UserComissarioDAO getUserComissarioDAO() {
		if (userComissarioDAO == null) {
			userComissarioDAO = new UserComissarioDAO();
		}
		return userComissarioDAO;
	}

	public UserComissario localizarUsuarioString(String usuario) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", usuario);
		
		getUserComissarioDAO().beginTransaction();
		UserComissario entidade = getUserComissarioDAO().retornaResultadoObjeto(User.FILTRO_NAME, parameters);
		getUserComissarioDAO().closeTransaction();
		return entidade;
	}
	
	public UserComissario incluir(UserComissario item) throws Exception {
		getUserComissarioDAO().beginTransaction();
		try {
			getUserComissarioDAO().save(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getUserComissarioDAO().commitAndCloseTransaction();
		
		return localizar(item.getId());
	}

	public UserComissario alterar(UserComissario item) throws Exception {
		if (item.getCadComissario() == null) {
			throw new Exception("Obrigatório selecionar um item!");
		}

		getUserComissarioDAO().beginTransaction();
		try {
			item = getUserComissarioDAO().alterar(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getUserComissarioDAO().commitAndCloseTransaction();
		return item;
	}

	public UserComissario localizar(int itemId) {
		getUserComissarioDAO().beginTransaction();
		UserComissario item = getUserComissarioDAO().localizarPorID(itemId);
		getUserComissarioDAO().closeTransaction();
		return item;
	}
	
	public void deletar(UserComissario id) {
		getUserComissarioDAO().beginTransaction();
		getUserComissarioDAO().deletaRegistrosObjeto(UserComissario.FILTRO_DELETE_REGISTRO, UserComissario.USERS_COMISSARIO_ID, id.getId().toString());
		getUserComissarioDAO().commitAndCloseTransaction();
	}

	public List<UserComissario> listaTodos() {
		getUserComissarioDAO().beginTransaction();
		List<UserComissario> result = getUserComissarioDAO().listaTodosRegistros();
		getUserComissarioDAO().closeTransaction();
		return result;
	}

	public List<UserComissario> listaTodosAutoComplete(User usr) {
		getUserComissarioDAO().beginTransaction();
		List<UserComissario> result = getUserComissarioDAO().pesquisaAutoComplete(Integer.toString(usr.getId()) );
		getUserComissarioDAO().closeTransaction();
		return result;
	}

	public List<UserComissario> listaTodosUsuariosComissarias(CadastroComissario entidade) {
		getUserComissarioDAO().beginTransaction();
		List<UserComissario> result = getUserComissarioDAO().pesquisaUsersComissarias(entidade);
		getUserComissarioDAO().closeTransaction();
		return result;
	}
	
	public String comissariaPorUsuarios(User user) {
		String valor = null;
		int seq = 0;
		for (UserComissario item : listaComissariasPorUsuario(user) ) {
			seq += 1;

			if (seq == 1) {
				valor = "( " + item.getCadComissario().getId().toString();
			} else {
				valor = valor + "," + item.getCadComissario().getId().toString();
			}
		}
		valor = valor + " ) ";
		return valor;
	}
	
	/**
	 * Retorna uma lista das comissarias que esta vinculada ao usuario ( Despachante / Comissaria )
	 * @param entidade ( User )
	 * @return List<UserComissario>  
	 * @author murilonadalin
	 * @serialData 21-09-2017
	 */
	public List<UserComissario> listaComissariasPorUsuario(User entidade){
		getUserComissarioDAO().beginTransaction();
		List<UserComissario> retorno = getUserComissarioDAO().listaComissariasPorUsuarios(entidade);
		getUserComissarioDAO().closeTransaction();
		return retorno;
	}

	/**
	 * Retorna a comissaria que esta vinculada ao usuario( Despachante / Comissaria )
	 * @param entidade ( User )
	 * @return CadastroComissario 
	 * @author murilonadalin
	 * @serialData 21-09-2017
	 */
	public CadastroComissario retornaComissariasUsuarioMaster(User entidade){
		CadastroComissario retornoEntidade = null;
		
		getUserComissarioDAO().beginTransaction();
		List<UserComissario> retorno = getUserComissarioDAO().listaComissariasPorUsuarios(entidade);
		
		for (UserComissario item : retorno) {
			retornoEntidade = item.getCadComissario();
			break;
		}
		
		getUserComissarioDAO().closeTransaction();
		return retornoEntidade;
	}
	
	
}