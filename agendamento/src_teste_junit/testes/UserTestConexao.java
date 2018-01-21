package testes;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

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
		user2.setEmail("murilo.nadalin@gmail.com");
		user2.setName("murilo_deletar");
		user2.setCpf("029.250.999-55");
		user2.setPassword("@Clif2017");
		user2.setRole(Role.ADMIN);

		userCadastrado = userFacade.incluir(user2);

		Assert.assertNotNull(userCadastrado);
		
	}
	
	@Test
	public void enviarEmailUserCadastrado() throws EmailException, IOException {
		UserFacade userRN = new UserFacade();
		User user = userRN.localizar(1);
		
		userRN.enviarEmailUsuarioCadastrado(user);
	}
	
	@Test
	public void testeCListaUsuarios() throws Exception {
		UserFacade userFacade = new UserFacade();
		List<User> listaTodos = userFacade.listaTodos();
		Assert.assertNotNull(listaTodos);
	}

	@Test
	public void testeAincluirUsuarioSeguranca() throws Exception {
		UserFacade userFacade = new UserFacade();
		User user2 = new User();
		user2.setEmail("murilo.nadalin@gmail.com1");
		user2.setName("murilo_deletar");
		user2.setCpf("029.250.999-00");
		user2.setPassword("@Clif2017");
		user2.setRole(Role.ADMIN);
		
		userCadastrado = (User) userFacade.incluir(user2);
		
		Assert.assertNotNull(userCadastrado);
		
	}

	@Test
	public void testeBvalidarLoginString() {
		UserFacade userFacade = new UserFacade();
		userCadastrado = (User) userFacade.validarLoginTeste("029.250.999-00", "@Clif2017");
		Assert.assertNotNull(userCadastrado);
	}

	@Test
	public void testeCAlterarSenhausuario() throws Exception {
		UserFacade userFacade = new UserFacade();

		userCadastrado.setNovaSenha("@Clif2017");
		userCadastrado.setPassword(userCadastrado.getNovaSenha());
		userCadastrado = userFacade.alterarSenha(userCadastrado);
		Assert.assertTrue(true);

	}
	
	@Test
	public void testeDValidarUsuario() throws Exception {
		UserFacade userFacade = new UserFacade();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime( userCadastrado.getDataCadastro() );
		calendar.add( Calendar.DAY_OF_MONTH , -30 );
		
		userCadastrado.setDataCadastro(calendar.getTime());
		userCadastrado = userFacade.alterar(userCadastrado);
		
		Assert.assertFalse( userFacade.validarUsuarioCpfString("029.250.999-55") );
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
		user2.setPassword("@Clif2017");
		user2.setRole(Role.ADMIN);
		String retorno = JSFUtil.getCriptografarSenha(user2.getPassword(), TipoCryptografiaEnum.CRIPTOGRAFAR);
		
		user2.setPassword(retorno.toString());

		userCadastrado = userFacade.incluir(user2);
		
		String retornoDescriptografia = JSFUtil.getCriptografarSenha(userCadastrado.getPassword(), TipoCryptografiaEnum.DESCRIPTOGRAFAR);

		System.out.println( retornoDescriptografia );
		
		userFacade.deletar(userCadastrado);
		
		Assert.assertNotNull(retornoDescriptografia);
	}

	@Test
	public void testarSenhaDesCryptografada() throws Exception {
		String retornoDescriptografia = JSFUtil.getCriptografarSenha("QEFjZGMxOTA5", TipoCryptografiaEnum.DESCRIPTOGRAFAR);
		
		System.out.println( retornoDescriptografia );
		
		
		Assert.assertNotNull(retornoDescriptografia);
	}
	
	@Test
	public void testarLocalizarUserPerfil() {
		UserFacade userFacade = new UserFacade();
		List<User> localizarUsuarioPorPerfil = userFacade.localizarUsuarioPorPerfil(Role.CLIF);
		
		Assert.assertNotNull(localizarUsuarioPorPerfil);
	}
	

}
