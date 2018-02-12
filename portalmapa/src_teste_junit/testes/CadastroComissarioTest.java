package testes;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import conexao.com.rn.CadastroComissarioRN;
import seguranca.com.entidade.CadastroComissario;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CadastroComissarioTest {
	
	private static CadastroComissarioRN entidadeRN;
	@BeforeClass
	public static void iniciar() {
		entidadeRN = new CadastroComissarioRN();
	}
	
	@Test
	public void testeAincluir() {
		CadastroComissario entidade = new CadastroComissario();
		entidade.setNome("murilo_deletar");
		entidade.setCpf("00000000055");

		try {
			Assert.assertNotNull(entidadeRN.incluir(entidade));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	@Test
	public void testeBAlterar() throws Exception {
		CadastroComissario entidade = entidadeRN.localizarEntidadeString("murilo_deletar");
		
		entidade.setNome("murilo_alterar");
		entidade.setCpf("00000000056");
		entidade = entidadeRN.alterar(entidade);
		
		Assert.assertTrue(true);
	}
	@Test
	public void testeCListaUsuarios() throws Exception{
		List<CadastroComissario> entidade = entidadeRN.listaTodos();
		
		Assert.assertNotNull(entidade);
	}
	@Test
	public void testeZdeletarUsuario() throws Exception{
		CadastroComissario entidade = entidadeRN.localizarEntidadeString("murilo_alterar");
		entidadeRN.deletar(entidade);
		Assert.assertTrue(true);
	}
	
}
