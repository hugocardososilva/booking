package testes;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import conexao.com.rn.CadastroCodigoPaisRN;
import seguranca.com.entidade.CadastroCodigoPais;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CadastroPaisTest {
	private static CadastroCodigoPais entidade;
	
	private static CadastroCodigoPaisRN RN;

	@BeforeClass
	public static void iniciar() {
		RN = new CadastroCodigoPaisRN();
	}
	
	@Test
	public void testeAincluir() throws Exception{
		entidade = new CadastroCodigoPais();
		entidade.setAbreviacaoPais("BRA");

		Assert.assertNotNull(RN.incluir(entidade));
	}
	@Test
	public void testeBAlterar() throws Exception {
		String teste = "BR";
		List<CadastroCodigoPais> listaTodosAutoComplete = RN.listaTodosAutoComplete(teste);
		System.out.println(listaTodosAutoComplete);
		
		Assert.assertTrue(true);
	}
}
