package testes;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

public class CotacaoDolarTest {

	@Test
	public void cotacaoDolarBaixar() throws IOException {
		URL url = new URL("http://api.promasters.net.br/cotacao/v1/valores?moedas=USD&alt=json");

		String destino = "C:/services/arquivo/txDolar.xml";

		InputStream is = url.openStream();
		
		FileOutputStream fos = new FileOutputStream(destino);

		int bytes = 0;

		while ((bytes = is.read()) != -1) {
			fos.write(bytes);
		}

		is.close();

		fos.close();
		
		BufferedReader br = new BufferedReader(new FileReader(destino));
		String linha = null;
		while(br.ready()){
			linha = br.readLine();
		}
		br.close();

		linha = linha.replace("\"", "");
		int posInicial = linha.indexOf("valor:") + 6;
		int posFinal = linha.indexOf(",ultima_consulta:");
		float valorDolar = Float.parseFloat(linha.substring(posInicial, posFinal));
		
		Assert.assertNotNull(valorDolar);
	}

	@Test
	public void cotacaoDolarPegarValor() throws IOException {
		String destino = "C:/services/arquivo/txDolar.xml";
		
		BufferedReader br = new BufferedReader(new FileReader(destino));
		String linha = null;
		while(br.ready()){
			linha = br.readLine();
			System.out.println(linha);
		}
		br.close();
		
		linha = linha.replace("\"", "");
		
		int posInicial = linha.indexOf("valor:") + 6;
		int posFinal = linha.indexOf(",ultima_consulta:");
		
		float valorDolar = Float.parseFloat(linha.substring(posInicial, posFinal));
		
		System.out.println(destino + "  "+linha + "  "+valorDolar + "  "+posFinal);
	}
	
	
}
