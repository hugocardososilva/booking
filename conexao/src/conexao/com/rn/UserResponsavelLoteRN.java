package conexao.com.rn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conexao.com.dao.UserResponsavelLoteDAO;
import seguranca.com.entidade.CadastroComissario;
import seguranca.com.entidade.User;
import seguranca.com.entidade.UserResponsavelLote;

public class UserResponsavelLoteRN {
	
	private UserResponsavelLoteDAO dao;
	
	public UserResponsavelLoteDAO getUserComissarioDAO() {
		if (dao == null) {
			dao = new UserResponsavelLoteDAO();
		}
		return dao;
	}

	public UserResponsavelLote localizarUsuarioString(String usuario) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", usuario);
		
		getUserComissarioDAO().beginTransaction();
		UserResponsavelLote entidade = getUserComissarioDAO().retornaResultadoObjeto(User.FILTRO_NAME, parameters);
		getUserComissarioDAO().closeTransaction();
		return entidade;
	}
	
	public UserResponsavelLote incluir(UserResponsavelLote item) throws Exception {
		getUserComissarioDAO().beginTransaction();
		try {
			getUserComissarioDAO().save(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getUserComissarioDAO().commitAndCloseTransaction();
		
		return localizar(item.getId());
	}

	public UserResponsavelLote alterar(UserResponsavelLote item) throws Exception {
		if (item.getCadRespLote() == null) {
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

	public UserResponsavelLote localizar(int itemId) {
		getUserComissarioDAO().beginTransaction();
		UserResponsavelLote item = getUserComissarioDAO().localizarPorID(itemId);
		getUserComissarioDAO().closeTransaction();
		return item;
	}
	
	public void deletar(UserResponsavelLote id) {
		getUserComissarioDAO().beginTransaction();
		getUserComissarioDAO().deletaRegistrosObjeto(UserResponsavelLote.FILTRO_DELETE_REGISTRO, UserResponsavelLote.USERS_COMISSARIO_ID, id.getId().toString());
		getUserComissarioDAO().commitAndCloseTransaction();
	}

	public List<UserResponsavelLote> listaTodos() {
		getUserComissarioDAO().beginTransaction();
		List<UserResponsavelLote> result = getUserComissarioDAO().listaTodosRegistros();
		getUserComissarioDAO().closeTransaction();
		return result;
	}

	public List<UserResponsavelLote> listaTodosAutoComplete(User usr) {
		getUserComissarioDAO().beginTransaction();
		List<UserResponsavelLote> result = getUserComissarioDAO().pesquisaAutoComplete(Integer.toString(usr.getId()) );
		getUserComissarioDAO().closeTransaction();
		return result;
	}

	public List<UserResponsavelLote> listaTodosUsuariosComissarias(CadastroComissario entidade) {
		getUserComissarioDAO().beginTransaction();
		List<UserResponsavelLote> result = getUserComissarioDAO().pesquisaUsersComissarias(entidade);
		getUserComissarioDAO().closeTransaction();
		return result;
	}
	
	public String responsavelLotePorUsuarios(User user) {
		String valor = null;
		int seq = 0;
		for (UserResponsavelLote item : listaRespLotePorUsuario(user) ) {
			seq += 1;

			if (seq == 1) {
				valor = "( " + item.getCadRespLote().getId().toString();
			} else {
				valor = valor + "," + item.getCadRespLote().getId().toString();
			}
		}
		valor = valor + " ) ";
		return valor;
	}
	
	public List<UserResponsavelLote> listaRespLotePorUsuario(User entidade){
		getUserComissarioDAO().beginTransaction();
		List<UserResponsavelLote> retorno = getUserComissarioDAO().listaRespLotePorUsuarios(entidade);
		getUserComissarioDAO().closeTransaction();
		return retorno;
	}
	
	
}