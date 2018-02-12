package conexao.com.rn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conexao.com.dao.UserImportadorDAO;
import seguranca.com.entidade.CadastroComissario;
import seguranca.com.entidade.User;
import seguranca.com.entidade.UserImportador;

public class UserImportadorRN {
	
	private UserImportadorDAO DAO;
	
	public UserImportadorDAO getDAO() {
		if (DAO == null) {
			DAO = new UserImportadorDAO();
		}
		return DAO;
	}

	public UserImportador localizarUsuarioString(String usuario) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", usuario);
		
		getDAO().beginTransaction();
		UserImportador entidade = getDAO().retornaResultadoObjeto(User.FILTRO_NAME, parameters);
		getDAO().closeTransaction();
		return entidade;
	}
	
	public UserImportador incluir(UserImportador item) throws Exception {
		getDAO().beginTransaction();
		try {
			getDAO().save(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();
		
		return localizar(item.getId());
	}

	public UserImportador alterar(UserImportador item) throws Exception {
		if (item.getImportador() == null) {
			throw new Exception("Obrigatório selecionar um item!");
		}

		getDAO().beginTransaction();
		try {
			item = getDAO().alterar(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();
		return item;
	}

	public UserImportador localizar(int itemId) {
		getDAO().beginTransaction();
		UserImportador item = getDAO().localizarPorID(itemId);
		getDAO().closeTransaction();
		return item;
	}
	
	public void deletar(UserImportador id) {
		getDAO().beginTransaction();
		getDAO().deletaRegistrosObjeto(UserImportador.FILTRO_DELETE_REGISTRO, UserImportador.USERS_COMISSARIO_ID, id.getId().toString());
		getDAO().commitAndCloseTransaction();
	}

	public List<UserImportador> listaTodos() {
		getDAO().beginTransaction();
		List<UserImportador> result = getDAO().listaTodosRegistros();
		getDAO().closeTransaction();
		return result;
	}

	public List<UserImportador> listaTodosAutoComplete(User usr) {
		getDAO().beginTransaction();
		List<UserImportador> result = getDAO().pesquisaAutoComplete(Integer.toString(usr.getId()) );
		getDAO().closeTransaction();
		return result;
	}

	public List<UserImportador> listaTodosUsuariosComissarias(CadastroComissario entidade) {
		getDAO().beginTransaction();
		List<UserImportador> result = getDAO().pesquisaUsersImportadores(entidade);
		getDAO().closeTransaction();
		return result;
	}
	
	public String importadorPorUsuarios(User user) {
		String valor = null;
		int seq = 0;
		for (UserImportador item : listaComissariasPorUsuario(user) ) {
			seq += 1;

			if (seq == 1) {
				valor = "( " + item.getImportador().getId().toString();
			} else {
				valor = valor + "," + item.getImportador().getId().toString();
			}
		}
		valor = valor + " ) ";
		return valor;
	}
	
	public List<UserImportador> listaComissariasPorUsuario(User entidade){
		getDAO().beginTransaction();
		List<UserImportador> retorno = getDAO().listaComissariasPorUsuarios(entidade);
		getDAO().closeTransaction();
		return retorno;
	}
	
	
}