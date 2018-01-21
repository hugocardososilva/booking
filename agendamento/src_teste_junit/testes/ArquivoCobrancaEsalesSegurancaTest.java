package testes;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import conexao.com.rn.ArquivosCobrancaEsalesRN;
import seguranca.com.entidade.ArquivosCobrancaEsalesSeguranca;

public class ArquivoCobrancaEsalesSegurancaTest {

	private static ArquivosCobrancaEsalesRN arqRN;

	@BeforeClass
	public static void iniciar() {
		arqRN = new ArquivosCobrancaEsalesRN();
	}

	@Test
	public void testeAIncluirArquivo() throws Exception {
		ArquivosCobrancaEsalesSeguranca arq = new ArquivosCobrancaEsalesSeguranca();
		arq.setDataLeituraArquivo(new Date());
		arq.setDescricao("descricao");
		arq.setPassword("1234");
		arq = arqRN.incluir(arq);

		Assert.assertNotNull(arq);
	}

	@Test
	public void testeBEfetuarPesquisa() {
		ArquivosCobrancaEsalesSeguranca arq = arqRN.pesquisarEntidadeParamateros("1234", "descricao");
		Assert.assertNotNull(arq);
	}

	@Test
	public void testeCListaTodos() {
		List<ArquivosCobrancaEsalesSeguranca> arq = arqRN.listaTodos();
		Assert.assertNotNull(arq);
	}
}
