package com.servicos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import conexao.com.rn.LeituraArquivoRN;
import pentahoatual.com.tuil.ServicoPentahoPDI;

public class ExecutarTarefas implements Runnable {
	private int sleepTime = 0;
	private String threadName = "";
	private static Random generator = new Random();

	public ExecutarTarefas(String name) {
		threadName = name;
		sleepTime = generator.nextInt(10000);
	}

	@Override
	public void run() {
		try {
			Thread.sleep(40000);

			int minutos_horas = 60;
			int segundos = 60;
			int TIMEOUT = minutos_horas * segundos * 1000;

			int minutos_proce = 5;
			int segundos_proce = 60;
			int TIMEOUT_proce = minutos_proce * segundos_proce * 1000;

			while (true) {
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");

				System.out.println("Aguardando execução em... eventosAtualizaProcessosBloqueados....." + df.format(new Date()));
				Thread.sleep(TIMEOUT_proce);
				
//				ExecutarServicos.eventosAtualizaProcessosBloqueados();
				ExecutarServicos.eventosCotacaoDolar();
				System.err.println("Atualziado execução em... eventosAtualizaProcessosBloqueados...." );

				System.err.println("Aguardando execução PROGRAMACAO NAVIO em... " + df.format(new Date()));
				LeituraArquivoRN lerArquivo = new LeituraArquivoRN();
				lerArquivo.downloadProgramacaoNavio();
				
				System.err.println("Finalizado atualização, da programação de Navios");
				Thread.sleep(TIMEOUT);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}