package testes;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import conexao.com.rn.CadastroComissarioRN;
import conexao.com.rn.UserComissarioRN;
import conexao.com.rn.UserFacade;
import seguranca.com.entidade.CadastroComissario;
import seguranca.com.entidade.User;
import seguranca.com.entidade.UserComissario;
import seguranca.com.enums.Role;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserComissarioTest {
	
	private static UserComissarioRN entidadeRN;
	private static UserFacade userRN;
	private static User user;
	private static UserComissario userComissario;
	private static CadastroComissario cadComissario;
	private static CadastroComissarioRN cadComissarioRN;
	
	@BeforeClass
	public static void iniciar() throws Exception {
		UserComissario userComissario2 = new UserComissario();
		CadastroComissario cadastroComissario = new CadastroComissario();
		
		entidadeRN = new UserComissarioRN();
		userRN = new UserFacade();
		cadComissarioRN = new CadastroComissarioRN();

		criaUsuario();
		criaComissario();
		
		cadComissario = cadComissarioRN.alterar(cadComissario);
	}

	private static void criaComissario() {
		cadComissario = new CadastroComissario();
		cadComissario.setNome("murilo_deletar");
		cadComissario.setCpf("00000000055");
	}

	private static void criaUsuario() throws Exception {
		user = new User();
		user.setEmail("murilo.nadalin@gmail.com1");
		user.setName("murilo_teste_vinculado");
		user.setPassword("@Acdc1909");
		user.setRole(Role.ADMIN);
		user = userRN.alterar(user);
	}
	
	@Test
	public void testeAincluir() throws Exception{
		userComissario = new UserComissario();
		userComissario.setCadComissario(cadComissario);
		userComissario.setUsuario(user);
		
		userComissario = entidadeRN.incluir(userComissario);
		
		
//		deletarDados();

		Assert.assertNotNull(userComissario);
	}
	
	@Test
	public void testeBListaTodosUsuariosComissaria() {
		List<UserComissario> listaTodosUsersComissarias = entidadeRN.listaTodosUsuariosComissarias(cadComissario);
		Assert.assertNotNull(listaTodosUsersComissarias);
	}
	
	@Test
	public void testeZdeletarUsuario(){
		entidadeRN.deletar(userComissario);
		userRN.deletar(user);
		cadComissarioRN.deletar(cadComissario);
		Assert.assertTrue(true);
	}

	private void deletarDados(){
		entidadeRN.deletar(userComissario);
		userRN.deletar(user);
		cadComissarioRN.deletar(cadComissario);
		Assert.assertTrue(true);
	}
	
}
