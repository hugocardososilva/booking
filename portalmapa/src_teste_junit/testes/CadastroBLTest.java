package testes;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import conexao.com.rn.CadastroBLContanierLCLRN;
import conexao.com.rn.CadastroBLContanierRN;
import conexao.com.rn.CadastroBLRN;
import seguranca.com.entidade.CadastroBLContanierLCL;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CadastroBLTest {

	private static CadastroBLContanierRN containerRN;
	private static CadastroBLContanierLCLRN lclRN;
	private static CadastroBLRN blRN;
	
	@BeforeClass
	public static void iniciar() {
		containerRN = new CadastroBLContanierRN();
		blRN = new CadastroBLRN();
		lclRN = new CadastroBLContanierLCLRN();
	}
	
	@Test
	public void testeQtdeSoma() {
		CadastroBLContanierLCL lcl = lclRN.localizar(15);
		
		float qtde = blRN.retornaQtdeRegistros(lcl, 3);
		
		System.out.println(qtde);
		
		
	}
	
	@Test
	public void listaBlPorLCL() {
		CadastroBLContanierLCL lcl = lclRN.localizar(3);
		System.out.println(lcl.getListaCadastroBL() );
	}

	@Test
	public void validarNumeroContainerComResultadoIgual10() {
		String nroContainer = "SEGU 631121 0";
		
		Assert.assertTrue( containerRN.validarContanier(nroContainer) );
		
	}

	@Test
	public void validarNumeroContainerComResultadoDiferente10() {
		String nroContainer = "HOYU 751013 6";
		
		Assert.assertTrue( containerRN.validarContanier(nroContainer) );
		
	}

}
