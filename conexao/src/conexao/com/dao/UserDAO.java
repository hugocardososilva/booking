package conexao.com.dao;
 
import java.util.HashMap;
import java.util.Map;

import conexao.com.dao.GenericDAO;
import seguranca.com.entidade.User;
 
public class UserDAO extends GenericDAO<User> {
 
	private static final long serialVersionUID = 1L;

	public UserDAO() {
        super(User.class);
    }
 
	public void delete(User item) {
		super.delete(item.getId(), User.class);
	}

	public User findUserByEmail(String email){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("email", email);     
 
        return super.retornaResultadoObjeto(User.FIND_BY_EMAIL, parameters);
    }

	public User validarUsuarioCpf(String valor){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cpf", valor);     
		
		return super.retornaResultadoObjeto(User.VALIDAR_USUARIO_CPF, parameters);
	}

	/**
	 * Efetua validacao do login (usuario / senha)
	 * @param usuario
	 * @param senha
	 * @return User
	 */
	public User validarLogin(String usuario, String senha){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cpf", usuario);     
		parameters.put("password", senha);     
		
		return super.retornaResultadoObjeto(User.FILTRO_USUARIO_SENHA, parameters);
	}

	public User validarLoginTESTE(String usuario, String senha){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cpf", usuario);     
		parameters.put("password", senha);     
		
		return (User) super.retornaResultadoObjeto(User.FILTRO_USUARIO_SENHA, parameters);
	}
}