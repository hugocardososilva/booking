package testes;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.pentaho.di.trans.steps.getxmldata.GetXMLDataMetaInjection;

import com.dao.CadastroDTCDTADAO;
import com.entidade.CadastroDTCDTA;
import com.rn.CadastroDTCDTARN;
import com.rn.EdiEnvioGerarXMLRN;

import conexao.com.rn.CadastroBLRN;
import conexao.com.rn.ProgramacaoNavioRN;
import seguranca.com.entidade.CadastroBL;
import seguranca.com.entidade.ProgramacaoNavio;

public class CadastroDtcDtaTest {

	
	private static ProgramacaoNavioRN rn;
	private static CadastroBLRN rnBL;
	private static CadastroDTCDTARN rnDtcDta;
	private CadastroDTCDTADAO dao;
	private CadastroBL bl;
	private CadastroDTCDTA dtcdta;
	private static EdiEnvioGerarXMLRN rnSigVigXML;

	@BeforeClass
	public static void iniciar() {
		rn = new ProgramacaoNavioRN();
		rnBL = new CadastroBLRN();
		rnDtcDta = new CadastroDTCDTARN();
		rnSigVigXML = new EdiEnvioGerarXMLRN();
	}
	
	@Test
	public void verificarPrazoDtc() throws ParseException {
		ProgramacaoNavio entidade = rn.localizar(215);
		
		Assert.assertTrue( !rn.prazoSegregacao24AntesEta(entidade) );
	}

	@Test
	public void validarDuplicacao() {
		String texto = "AAAAAA - AAA";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("descricaoBL", texto);
		
		dao = new CadastroDTCDTADAO();

		dao.beginTransaction();
		
		int executaSqlCountRegistros = dao.executaSqlCountRegistros(CadastroDTCDTA.FILTRO_CADASTRO_BL_DESCRICAOBL, parameters, false);
		
		System.out.println(executaSqlCountRegistros);
		
		dao.closeTransaction();
	}
	
	@Test
	public void localizarPorReferencia() throws Exception {
		bl = rnBL.localizar(119);
		
		dtcdta = rnDtcDta.localizarPorReferencia(bl.getId());
		
		Assert.assertNotNull(dtcdta);
	}

	@Test
	public void gerarXMLUnidadesCarga() throws Exception {
		dtcdta = rnDtcDta.localizar(109);
		
		StringBuffer xmlUnidadesCarga = rnSigVigXML.gerarXMLUnidadesCarga(dtcdta);
		
		
		Assert.assertNotNull(xmlUnidadesCarga);
	}

	@Test
	public void gerarXMLDeclaracaoImportacao() throws Exception {
		dtcdta = rnDtcDta.localizar(109);
		
		StringBuffer xmlUnidadesCarga = rnSigVigXML.gerarXMLDeclaracaoImportacao(dtcdta);
		
		
		Assert.assertNotNull(xmlUnidadesCarga);
	}
	
	@Test
	public void retirarCaracteresEspeciais() {
		String texto = "@#$A-DDDD;DFFDFDFDF�";
		
		texto = texto.replaceAll("[^0-9a-z0-9A-Z]", "");
		
		System.out.println(texto);
	}
	
	@Test
	public void retornaUltimaATIRiscoFitosanitario() throws Exception {
		rnDtcDta.localizarRegistroFitosanitario(rnDtcDta.localizar(131));
	}
	
	/**
	 * teste efetuado na base de produção SQL SERVER
	 */
	@Test
	public void testeEnvioEmailPrimeiroCadastroDTC() throws Exception {
		CadastroDTCDTA entidade = rnDtcDta.localizar(13);
		CadastroDTCDTA novo = new CadastroDTCDTA();
		novo = entidade;
		novo.setId(null);
		novo.setDescricaoBL("TESTE DTC MURILO....");
		novo.setNumeroATI(null);
		
		rnDtcDta.alterar(novo, null);
	}
	
}
