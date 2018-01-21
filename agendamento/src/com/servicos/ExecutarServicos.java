package com.servicos;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.rn.CadastroDTCDTARN;


public class ExecutarServicos {
	private static CadastroDTCDTARN dtcDtaRN;

	public static void eventosProgramacaoNavio() throws InterruptedException {
		ExecutarTarefas task1 = new ExecutarTarefas("Tarefas...");
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		System.out.println("Servico iniciado em..." + df.format(new Date()));

		ExecutorService threadExecutor = Executors.newFixedThreadPool(1);
		threadExecutor.execute(task1);
		System.out.println("Servico executado em... " + df.format(new Date()));
		threadExecutor.shutdown();
	}

	public static void eventosAtualizaProcessosBloqueados() throws InterruptedException {
		try {
			getDtcDtaRN().updateAtualizaDados();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static void eventosCotacaoDolar() throws InterruptedException {
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	private static CadastroDTCDTARN getDtcDtaRN() {
		if (dtcDtaRN == null) {
			dtcDtaRN = new CadastroDTCDTARN();
		}
		
		return dtcDtaRN;
	}
}