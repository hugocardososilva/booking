package testes;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import conexao.com.rn.CadastroBLAnexosRN;
import conexao.com.rn.CadastroBLContanierLCLRN;
import conexao.com.rn.CadastroBLContanierRN;
import conexao.com.rn.CadastroBLRN;
import seguranca.com.entidade.CadastroBL;
import seguranca.com.entidade.CadastroBLAnexos;
import seguranca.com.entidade.CadastroBLContanierLCL;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CadastroBLAnexosTest {

	private static CadastroBLRN blRN;
	private static CadastroBLAnexosRN blAnexosRN;
	
	@BeforeClass
	public static void iniciar() {
		blRN = new CadastroBLRN();
		blAnexosRN = new CadastroBLAnexosRN();
	}
	
	@Test
	public void testeRetornoLista() {
		CadastroBL bl = blRN.localizar(110);
		
		List<CadastroBLAnexos> list = blAnexosRN.listaTodosPorTipoAnexosMapa(bl);
		
		Assert.assertNotNull(list);
	}
	

}
