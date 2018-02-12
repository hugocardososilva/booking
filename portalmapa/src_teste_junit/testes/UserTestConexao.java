package testes;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.mail.EmailException;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import conexao.com.rn.UserFacade;
import conexao.com.util.JSFUtil;
import seguranca.com.entidade.User;
import seguranca.com.enums.Role;
import seguranca.com.enums.TipoCryptografiaEnum;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserTestConexao {
	private static User userCadastrado = null;

	@Test
	public void testeAincluirUsuario() throws Exception {
		UserFacade userFacade = new UserFacade();
		User user2 = new User();
		user2.setEmail("murilo.nadalin@gmail.com1");
		user2.setName("murilo_deletar");
		user2.setCpf("029.250.999-00");
		user2.setPassword("@Aqwertyu4");
		user2.setRole(Role.ADMIN);

		userCadastrado = userFacade.incluir(user2);

		Assert.assertNotNull(userCadastrado);
	}

	@Test
	public void testeBvalidarLoginString() {
		UserFacade userFacade = new UserFacade();
		userCadastrado = userFacade.validarLogin("029.250.999-55", "1234");
		Assert.assertNotNull(userCadastrado);
	}

	@Test
	public void testeCAlterarSenhausuario() throws Exception {
		UserFacade userFacade = new UserFacade();

		userCadastrado.setNovaSenha("@Aqwertyu5");
		userCadastrado.setPassword(userCadastrado.getNovaSenha());
		userCadastrado = userFacade.alterarSenha(userCadastrado);
		Assert.assertTrue(true);

	}

	@Test
	public void testeCListaUsuarios() throws Exception {
		UserFacade userFacade = new UserFacade();
		List<User> listaTodos = userFacade.listaTodos();
		Assert.assertNotNull(listaTodos);
	}

	@Test
	public void testeDValidarUsuario() throws Exception {
		UserFacade userFacade = new UserFacade();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(userCadastrado.getDataCadastro());
		calendar.add(Calendar.DAY_OF_MONTH, -30);

		userCadastrado.setDataCadastro(calendar.getTime());
		userCadastrado = userFacade.alterar(userCadastrado);

		Assert.assertFalse(userFacade.validarUsuarioCpfString("029.250.999-00"));
	}

	@Test
	public void testeZdeletarUsuario() {
		UserFacade userFacade = new UserFacade();
		userFacade.deletar(userCadastrado);
		Assert.assertTrue(true);
	}

	@Test
	public void testarSistemaOperacional() {
		System.out.println(System.getProperty("os.name").substring(0, 3).toUpperCase());
	}

	@Test
	public void testarSenhaCryptografada() throws Exception {
		UserFacade userFacade = new UserFacade();
		User user2 = new User();
		user2.setEmail("murilo.nadalin@gmail.com6");
		user2.setName("murilo_deletar");
		user2.setCpf("029.250.999-00");
		user2.setPassword("@Aqwertyu4");
		user2.setRole(Role.ADMIN);
		String retorno = JSFUtil.getCriptografarSenha(user2.getPassword(), TipoCryptografiaEnum.CRIPTOGRAFAR);

		user2.setPassword(retorno.toString());

		userCadastrado = userFacade.incluir(user2);

		String retornoDescriptografia = JSFUtil.getCriptografarSenha(userCadastrado.getPassword(),
				TipoCryptografiaEnum.DESCRIPTOGRAFAR);

		System.out.println(retornoDescriptografia);

		userFacade.deletar(userCadastrado);

		Assert.assertNotNull(retornoDescriptografia);
	}

	@Test
	public void testeRecuperarSenha() throws EmailException, IOException {
		String cpf = "029.250.999-55";
		String email = "murilo.nadalin@gmail.com";
		
		UserFacade userFacade = new UserFacade();
		User user = userFacade.localizarUsuarioCpfEmail(cpf, email);
		
		String senhaCorreta = JSFUtil.getCriptografarSenha(user.getPassword(), TipoCryptografiaEnum.DESCRIPTOGRAFAR);
		
		JSFUtil jsfUtil = new JSFUtil();
		jsfUtil.envioEmailSimples(new StringTokenizer(user.getEmail(), ";"), "Envio de Senha Portal MAPA", "Sua senha é: " + senhaCorreta );
	}

}
