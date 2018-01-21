package com.rn;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.entidade.CadastroDTCDTA;

/**
 * Class criada, para controlar os metodos de geração dos XMLs
 * @author murilonadalin
 * @since 29-08-2017
 */
public class EdiEnvioGerarXMLRN {

	/**
	 * Metodo para gerar XML ( XMLUnidadesCarga )
	 * @param entidade
	 * @author murilonadalin
	 * @serialData 29-08-2017
	 * @return StringBuffer ( XML )
	 */
	public StringBuffer gerarXMLUnidadesCarga(CadastroDTCDTA entidade) {
		String data = null;
		DateFormat dfData = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dfHora = new SimpleDateFormat("HH:mm:ss");

		Date dataHoje = new Date();
		data = dfData.format(dataHoje) + "T" + dfHora.format(dataHoje);

		StringBuffer xmlEnvio = new StringBuffer();
		xmlEnvio.append("<unidadesCarga>");
			xmlEnvio.append("<idContatoRepresentanteImportador>" + 10607 + "</idContatoRepresentanteImportador>");
			xmlEnvio.append("<nrDeclaracaoImportacao>" + 36 / 2015 + "</nrDeclaracaoImportacao>");
			xmlEnvio.append("<idRecintoVigiagro>" + 159 + "</idRecintoVigiagro>");
			xmlEnvio.append("<cdConhecimentoCarga>" + "NRCONH34" + "</cdConhecimentoCarga>");
				xmlEnvio.append("<listaUnidadesCarga>");
					xmlEnvio.append("<objetoUnidadeCarga>");
						xmlEnvio.append("<cdUnidadeCarga>" + "UNICARG98" + "</cdUnidadeCarga>");
						xmlEnvio.append("<data>" + data + "</data>");
						xmlEnvio.append("<local>" + "Pátio 3" + "</local>");
					xmlEnvio.append("</objetoUnidadeCarga>");
				xmlEnvio.append("</listaUnidadesCarga>");
		xmlEnvio.append("</unidadesCarga>");
		
		return xmlEnvio; 
	}

	/**
	 * Metodo para gerar XML ( XMLDeclaracaoImportacao )
	 * @param entidade
	 * @author murilonadalin
	 * @serialData 29-08-2017
	 * @return StringBuffer ( XML )
	 */
	public StringBuffer gerarXMLDeclaracaoImportacao(CadastroDTCDTA entidade) {
		StringBuffer xmlEnvio = new StringBuffer();
		xmlEnvio.append("<declaracaoImportacao>");
			xmlEnvio.append("<qtConhecimento>" + 1 + "</qtConhecimento>");
			xmlEnvio.append("<idRecintoVigiagro>" + 2 + "</idRecintoVigiagro>");
			xmlEnvio.append("<listaConhecimentos>");
				xmlEnvio.append("<objetoConhecimento>");
					xmlEnvio.append("<qtUnidadeCarga>" + 2 + "</qtUnidadeCarga>");
					xmlEnvio.append("<sgPaisOrigem>" + "ARG" + "</sgPaisOrigem>");
					xmlEnvio.append("<sgPaisProcedencia>" + "BRA" + "</sgPaisProcedencia>");
					xmlEnvio.append("<idEmpresaImportadora>" + 2 + "</idEmpresaImportadora>");
					xmlEnvio.append("<idEmpresaExportadora>" + 2 + "</idEmpresaExportadora>");
					xmlEnvio.append("<nrConhecimento>" + "NRConhTeste01" + "</nrConhecimento>");
					
					xmlEnvio.append("<listaUnidadesCarga>");
						xmlEnvio.append("<objetoUnidadeCarga>");
							xmlEnvio.append("<idNCM>" + 2 + "</idNCM>");
//							xmlEnvio.append("<pesoLiquidoMercadora>" + 2 + "</pesoLiquidoMercadora>");
							xmlEnvio.append("<cdUnidadeCarga>" + "UNICARGTEST01" + "</cdUnidadeCarga>");
//							xmlEnvio.append("<idUnidadeMedidaPesoLiquido>" + 2 + "</idUnidadeMedidaPesoLiquido>");
//							xmlEnvio.append("<pesoBrutoMercadoria>" + 2 + "</pesoBrutoMercadoria>");
//							xmlEnvio.append("<idUnidadeMedidaPesoBruto>" + 2 + "</idUnidadeMedidaPesoBruto>");
							xmlEnvio.append("<tipoEmbalagem>" + 2 + "</tipoEmbalagem>");
							xmlEnvio.append("<csScanner>" + 2 + "</csScanner>");
							xmlEnvio.append("<csInspecao>" + 2 + "</csInspecao>");
							xmlEnvio.append("<dsMercadoria>" + "PEÇAS DE RETALHO" + "</dsMercadoria>");
							xmlEnvio.append("<dsLocalidadeDespacho>" + "Porto de Santos" + "</dsLocalidadeDespacho>");
						xmlEnvio.append("</objetoUnidadeCarga>");
					xmlEnvio.append("</listaUnidadesCarga>");
				xmlEnvio.append("</objetoConhecimento>");
			xmlEnvio.append("</listaConhecimentos>");
		xmlEnvio.append("</declaracaoImportacao>");
		
		return xmlEnvio; 
	}

}
