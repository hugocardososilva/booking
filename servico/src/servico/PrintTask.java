package servico;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.sound.sampled.SourceDataLine;

import jxl.Cell;
import jxl.Sheet;

public class PrintTask implements Runnable {
	private int sleepTime = 0;
	private String threadName = "";
	private ProgramacaoNavioRN programacaoNavioRN;
	private static Random generator = new Random();

	public PrintTask(String name) {
		threadName = name;
		sleepTime = generator.nextInt(10000);
	}

	@Override
	public void run() {
		try {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");

			URL url = new URL("http://www.portoitapoa.com.br/servicos_programacao_navios/excel/");

			String destino = "C:/services/arquivo/lista_programacao_navios.xls";

			InputStream is = url.openStream();

			FileOutputStream fos = new FileOutputStream(destino);

			int bytes = 0;

			while ((bytes = is.read()) != -1) {
				fos.write(bytes);
			}

			is.close();

			fos.close();

			System.out.println(
					threadName + ". Dowload do arquivo em... " + df.format(new Date()) +"\n");

			Thread.sleep(sleepTime);
			
			arquivoExcelProgramacaoNavio(new File(destino));
			
//			LeituraArquivoRN lerArquivo = new LeituraArquivoRN();
//			lerArquivo.arquivoExcelProgramacaoNavio(new File(destino));
//			lerArquivo.arquivoExcelProgramacaoNavioTeste(destino);

			System.out.println("Finalizado atualização, da programação de Navios");
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void arquivoExcelProgramacaoNavio(File arquivo) {
		jxl.Workbook workbook = null;
			try {
				workbook = jxl.Workbook.getWorkbook(arquivo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		Sheet sheet = workbook.getSheet(0);

		int linhas = sheet.getRows();

		System.out.println("Iniciando a leitura da planilha XLS:");

		for (int i = 0; i < linhas; i++) {
			Cell coluna1B = sheet.getCell(1, i);
			Cell coluna2C = sheet.getCell(2, i);
			Cell coluna8I = sheet.getCell(8, i);
			Cell coluna9J = sheet.getCell(9, i);
			Cell coluna12M = sheet.getCell(12, i);

			String colunaBNavio = coluna1B.getContents();
			String colunaCViagem = coluna2C.getContents();
			String colunaIETA = coluna8I.getContents();
			String colunaJATA = coluna9J.getContents();
			String colunaMETS = coluna12M.getContents();

			// System.out.println("Coluna B2 NAVIO: " + colunaBNavio);
			// System.out.println("Coluna C3 VIAGEM: " + colunaCViagem);
			// System.out.println("Coluna I8 ETA: " + colunaIETA);
			// System.out.println("Coluna J9 ATA: " + colunaJATA);
			// System.out.println("Coluna M12 ETS: " + colunaMETS);

			if (i > 0) {
				if (!colunaBNavio.isEmpty()) {
					ProgramacaoNavio navio = getProgramacaoNavioRN().pesquisaNavioViagem(colunaBNavio, colunaCViagem);
					if (navio == null) {
						navio = new ProgramacaoNavio();
					}
					navio.setNavio(colunaBNavio);
					navio.setNavioViagem(colunaCViagem);
					if (!colunaIETA.isEmpty()) {
						navio.setDataETA(new Date(colunaIETA));
					}
					if (!colunaJATA.isEmpty()) {
						navio.setDataATA(new Date(colunaJATA));
					}
					if (!colunaMETS.isEmpty()) {
						navio.setDataETS(new Date(colunaMETS));
					}
					getProgramacaoNavioRN().alterar(navio);
				}
			}
		}
		workbook.close();
	}
	
	private ProgramacaoNavioRN getProgramacaoNavioRN() {
		if (programacaoNavioRN == null) {
			programacaoNavioRN = new ProgramacaoNavioRN();
		}

		return programacaoNavioRN;
	}
	
	private void verificarStatusServicos() {
		
	}
	
	
	
}