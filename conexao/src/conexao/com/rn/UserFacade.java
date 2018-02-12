package conexao.com.rn;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.mail.EmailException;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;

import conexao.com.dao.UserDAO;
import conexao.com.util.JSFUtil;
import conexao.com.util.LoginController;
import seguranca.com.entidade.User;
import seguranca.com.entidade.UserDadosConexao;
import seguranca.com.entidade.UserPermissoes;
import seguranca.com.enums.Role;
import seguranca.com.enums.TelasEntidadesEnum;
import seguranca.com.enums.TipoCryptografiaEnum;

public class UserFacade {

	private UserDAO userDAO;
	private PasswordValidator validator;
	private JSFUtil jSFUtil;
	private UserDadosConexaoRN userDadosConexaoRN;

	public UserDAO getUserDAO() {
		if (userDAO == null) {
			userDAO = new UserDAO();
		}
		return userDAO;
	}

	public User isValidLogin(String email, String password) {
		getUserDAO().beginTransaction();
		User user = getUserDAO().findUserByEmail(email);

		if (user == null || !user.getPassword().equals(password)) {
			return null;
		}

		return user;
	}

	/**
	 * Efetuao a validacao do login, utilizando o metodo generico
	 * 
	 * @param usuario
	 * @param password
	 * @return User
	 */
	public User validarLogin(String usuario, String password) {
		getUserDAO().beginTransaction();

		password = JSFUtil.getCriptografarSenha(password, TipoCryptografiaEnum.CRIPTOGRAFAR);

		User user = getUserDAO().validarLogin(usuario, password);

		if (user == null || !user.getPassword().equals(password)) {
			return null;
		}

		getUserDAO().closeTransaction();
		return user;
	}

	public User validarLoginTeste(String usuario, String password) {
		getUserDAO().beginTransaction();

		password = JSFUtil.getCriptografarSenha(password, TipoCryptografiaEnum.CRIPTOGRAFAR);

		User user = (User) getUserDAO().validarLogin(usuario, password);

		if (user == null || !user.getPassword().equals(password)) {
			return null;
		}

		getUserDAO().closeTransaction();
		return user;
	}

	public User localizarUsuarioString(String usuario) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", usuario);

		getUserDAO().beginTransaction();
		User user = getUserDAO().retornaResultadoObjeto(User.FILTRO_NAME, parameters);
		getUserDAO().closeTransaction();
		return user;
	}

	public User localizarUsuarioCpf(String usuario) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cpf", usuario);

		getUserDAO().beginTransaction();
		User user = getUserDAO().retornaResultadoObjeto(User.VALIDAR_USUARIO_CPF, parameters);
		getUserDAO().closeTransaction();
		return user;
	}

	public List<User> localizarUsuarioPorPerfil(Role perfil) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("role", perfil);

		getUserDAO().beginTransaction();
		List<User> user = getUserDAO().retornaResultadoListaObjeto(User.LOCALIZAR_USUARIOS_PERFIL, parameters, false);
		getUserDAO().closeTransaction();
		return user;
	}

	public User localizarUsuarioCpfEmail(String usuario, String email) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cpf", usuario.trim());
		parameters.put("email", email.trim());

		getUserDAO().beginTransaction();
		User user = getUserDAO().retornaResultadoObjeto(User.LOCALIZAR_USUARIO_EMAIL_CPF, parameters);
		getUserDAO().closeTransaction();
		return user;
	}

	public boolean validarUsuarioCpfString(String cpf) throws Exception {
		boolean validacao = true;

		getUserDAO().beginTransaction();
		User user = getUserDAO().validarUsuarioCpf(cpf);
		getUserDAO().closeTransaction();

		if (user == null) {
			throw new Exception("Usuário não localizado !");
		}

		if (user.isCliente() || user.isDespachante()) {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");

			Date dataHoje = new Date();

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(user.getDataCadastro());
			calendar.add(Calendar.DAY_OF_MONTH, 30);

			// Converte para Date
			Date dateA = df.parse(df.format(calendar.getTime()));
			Date dateB = df.parse(df.format(dataHoje));

			if (dateB.after(dateA)) {
				validacao = false;
				return validacao;
			}
		}

		return validacao;
	}

	private PasswordValidator getValidarSenha() {
		if (validator == null) {
			validator = new PasswordValidator(Arrays.asList(
					// comprimento entre 8 e 16 caracteres
					new LengthRule(8, 16),

					// pelo menos um caractere de um dígito
					new CharacterRule(EnglishCharacterData.Digit, 1),

					// pelo menos um caractere de um dígito
					new CharacterRule(EnglishCharacterData.Special, 1),

					// pelo menos um personagem em maiúscula
					new CharacterRule(EnglishCharacterData.UpperCase, 1)));
		}
		return validator;
	}

	private RuleResult validarSenha(User item) {
		String senha = item.getNovaSenha();
		if (senha == null) {
			senha = item.getPassword();
		}

		return getValidarSenha().validate(new PasswordData(new String(senha)));
	}

	public User incluir(User item) throws Exception {
		RuleResult result = validarSenha(item);

		if (result.isValid()) {
			getUserDAO().beginTransaction();
			item.setDataCadastro(new Date());
			item.setName(item.getName().toUpperCase());

			item.setPassword(JSFUtil.getCriptografarSenha(item.getPassword(), TipoCryptografiaEnum.CRIPTOGRAFAR));

			try {
				getUserDAO().save(item);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
			getUserDAO().commitAndCloseTransaction();
		} else {
			@SuppressWarnings("unused")
			String erro = null;
			for (String msg : getValidarSenha().getMessages(result)) {
				erro = msg;
			}

			throw new Exception("Senha inválida ! "
					+ "A senha deve conter pelo menos um caracter especial(@) e letra maiuscula(A), maximo(8 caracteres)");
		}

		return localizar(item.getId());
	}

	public User alterar(User item) throws Exception {
		RuleResult result = validarSenha(item);
		if (result.isValid()) {
			getUserDAO().beginTransaction();

			if (item.getName() != null) {
				item.setName(item.getName().toUpperCase());
			}

			if (item.getId() == 0) {
				item.setDataCadastro(new Date());
			}

			item.setPassword(JSFUtil.getCriptografarSenha(item.getPassword(), TipoCryptografiaEnum.CRIPTOGRAFAR));

			LoginController login = new LoginController();
			UserDadosConexao dadosConexao = login.getRetornaUsuarioLogado().getUserDadosConexao();
			item.setUserDadosConexao(dadosConexao);

			try {
				item = getUserDAO().alterar(item);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
			getUserDAO().commitAndCloseTransaction();
		} else {
			@SuppressWarnings("unused")
			String erro = null;
			for (String msg : getValidarSenha().getMessages(result)) {
				erro = msg;
			}

			throw new Exception("Senha inválida ! "
					+ "A senha deve conter pelo menos um caracter especial(@) e letra maiuscula(A), maximo(8 caracteres) ");
		}

		return item;
	}

	public User localizar(int itemId) {
		getUserDAO().beginTransaction();
		User item = getUserDAO().localizarPorID(itemId);
		getUserDAO().closeTransaction();
		return item;
	}

	public void deletar(User item) {
		getUserDAO().beginTransaction();
		User persistedemp = getUserDAO().localizaPorReferencias(item.getId());
		getUserDAO().delete(persistedemp);
		getUserDAO().commitAndCloseTransaction();
	}

	public List<User> listaTodos() {
		getUserDAO().beginTransaction();
		List<User> result = getUserDAO().listaTodosRegistros();
		getUserDAO().closeTransaction();
		return result;
	}

	/**
	 * Alterar senha do usuario
	 * 
	 * @param usuario
	 * @return User
	 * @author murilo
	 */
	public User alterarSenha(User usuario) throws Exception {
		RuleResult result = validarSenha(usuario);
		if (result.isValid()) {
			getUserDAO().beginTransaction();
			usuario.setPassword(usuario.getNovaSenha());
			if (usuario.getNovaSenha() != null) {
				usuario.setDataCadastro(new Date());
			}

			usuario.setPassword(JSFUtil.getCriptografarSenha(usuario.getPassword(), TipoCryptografiaEnum.CRIPTOGRAFAR));

			try {
				getUserDAO().alterar(usuario);
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
			getUserDAO().commitAndCloseTransaction();
		} else {
			String erro = null;
			for (String msg : getValidarSenha().getMessages(result)) {
				erro = msg;
			}

			throw new Exception("Senha inválida ! " + erro);
		}

		return localizar(usuario.getId());
	}

	private UserDadosConexaoRN getUserDadosConexaoRN() {
		if (userDadosConexaoRN == null) {
			userDadosConexaoRN = new UserDadosConexaoRN();
		}

		return userDadosConexaoRN;
	}

	public User salvar(User entidade) throws Exception {
		getUserDAO().beginTransaction();
		entidade = getUserDAO().alterar(entidade);
		getUserDAO().commitAndCloseTransaction();

		return entidade;
	}

	public User incluirUsuarioDespachante(User entidade, Role perfil) throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -40);

		entidade.setPassword(JSFUtil.getCriptografarSenha("@Clif2017", TipoCryptografiaEnum.CRIPTOGRAFAR));
		entidade.setRole(perfil);
		entidade.setReceberEmail(true);
		entidade.setDataCadastro(calendar.getTime());
		entidade.setUserDadosConexao(getUserDadosConexaoRN().localizar(1));

		UserPermissoes perA = new UserPermissoes();
		perA.setUsuarioVinculado(entidade);
		perA.setTelasEntidadesEnum(TelasEntidadesEnum.CADASTRO_ATI_FCL);

		UserPermissoes perB = new UserPermissoes();
		perB.setUsuarioVinculado(entidade);
		perB.setTelasEntidadesEnum(TelasEntidadesEnum.CADASTRO_ATI_LCL);

		UserPermissoes perC = new UserPermissoes();
		perC.setUsuarioVinculado(entidade);
		perC.setTelasEntidadesEnum(TelasEntidadesEnum.CONSULTA_PROCESSOS);

		entidade.getListaUserPermissoes().add(perA);
		entidade.getListaUserPermissoes().add(perB);
		entidade.getListaUserPermissoes().add(perC);

		getUserDAO().beginTransaction();
		entidade = getUserDAO().alterar(entidade);
		getUserDAO().commitAndCloseTransaction();

		try {
			enviarEmailUsuarioCadastrado(entidade);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return entidade;
	}

	public void enviarEmailUsuarioCadastrado(User user) throws EmailException, IOException {
		String corpoEmail = " Segue confirmação, de Cadastro do Portal CLIF. " + " \n\n";
		corpoEmail = corpoEmail + " Login => " + user.getCpf() + " \n\n";
		corpoEmail = corpoEmail + " Senha => @Clif2017" + " \n\n";
		corpoEmail = corpoEmail + " Segue link para acesso." + " \n\n";
		corpoEmail = corpoEmail + " http://sistemas.oclif.com.br/agendamento" + " \n\n";

		getJSFUtil().envioEmailSimples(new StringTokenizer(user.getEmail(), ";"), "Portal CLIF - Usuario cadastrado",
				corpoEmail);
	}

	private JSFUtil getJSFUtil() {
		if (jSFUtil == null) {
			jSFUtil = new JSFUtil();
		}
		return jSFUtil;
	}

	public User ativarDesativarUser(User entidade) throws Exception {
		User user = entidade;

		if (!user.isUserDesativado()) {
			user.setUserDesativado(true);
		} else {
			user.setUserDesativado(false);
		}

		return salvar(user);
	}

}