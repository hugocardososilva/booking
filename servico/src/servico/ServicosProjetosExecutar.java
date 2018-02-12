package servico;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServicosProjetosExecutar {
	public static void main(String[] args) throws InterruptedException {
		PrintTask task1 = new PrintTask("Servi�o programa��o navios ");
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		System.out.println("Servi�o iniciado em..." + df.format(new Date()));

		int minutos = 1;
		int segundos = 60;
		int TIMEOUT = minutos * segundos * 1000;
		// while (true) {
		// Thread.sleep(TIMEOUT);
		// ExecutorService threadExecutor = Executors.newFixedThreadPool(1);
		// threadExecutor.execute(task1);
		// System.out.println("Servi�o executado em... " + df.format(new
		// Date()));
		// threadExecutor.shutdown();
		//
		// System.out.println("Aguardando execu��o em... " + df.format(new
		// Date()));
		// Thread.sleep(TIMEOUT);
		// String destino = "C:/services/arquivo/lista_programacao_navios.xls";
		// LeituraArquivoRN lerArquivo = new LeituraArquivoRN();
		// lerArquivo.arquivoExcelProgramacaoNavio(new File(destino));
		//
		// System.out.println("Finalizado atualiza��o, da programa��o de
		// Navios");
		// Thread.sleep(TIMEOUT);
		// }
		ExecutorService threadExecutor = Executors.newFixedThreadPool(1);
		threadExecutor.execute(task1);
		System.out.println("Servi�o executado em... " + df.format(new Date()));
		threadExecutor.shutdown();
		
	}
}