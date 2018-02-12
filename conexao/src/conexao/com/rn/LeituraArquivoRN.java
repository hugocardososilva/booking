package conexao.com.rn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import conexao.com.util.ConexaoFactorySara;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import seguranca.com.entidade.ProgramacaoNavio;

public class LeituraArquivoRN {

	private ProgramacaoNavioRN programacaoNavioRN;
	private ConexaoFactorySara factory;
	
	private ConexaoFactorySara getFactory() throws IOException, Throwable {
		if (factory == null) {
			factory = new ConexaoFactorySara();
		}
		return factory;
	}

	@SuppressWarnings("deprecation")
	private void arquivoExcelProgramacaoNavio(File arquivo) throws Throwable {
//		Connection conexao = getFactory().getConnection();

		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(arquivo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
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

			if (i > 0) {
				if (!colunaBNavio.isEmpty()) {
					rotinaNavioPortalClif(df, colunaBNavio, colunaCViagem, colunaIETA, colunaJATA, colunaMETS);
					
//					String sql = "SELECT * FROM TAB_NAVIOS WHERE NAV_N2 = ?" ;
//				    PreparedStatement prest = conexao.prepareStatement(sql);
//				    prest.setString(1, colunaBNavio);
//				    ResultSet rs = prest.executeQuery();	
//					
//				    System.out.println("teste..." +rs);
				}
			}
		}
		workbook.close();
	}

	private void rotinaNavioPortalClif(DateFormat df, String colunaBNavio, String colunaCViagem, String colunaIETA,
			String colunaJATA, String colunaMETS) throws ParseException, Exception {
		ProgramacaoNavio navio = getProgramacaoNavioRN().pesquisaNavioViagem(colunaBNavio, colunaCViagem);
		if (navio == null) {
			navio = new ProgramacaoNavio();
		}
		
//					if (i==74 || i==75) {
//					}
		
		navio.setNavio(colunaBNavio);
		navio.setNavioViagem(colunaCViagem);
		if (!colunaIETA.isEmpty()) {
			navio.setDataETA(df.parse(colunaIETA));
		}
		if (!colunaJATA.isEmpty()) {
			navio.setDataATA(df.parse(colunaJATA));
		}
		if (!colunaMETS.isEmpty()) {
			navio.setDataETS(df.parse(colunaMETS));
		}
		getProgramacaoNavioRN().alterar(navio);
	}

	public void downloadProgramacaoNavio() throws Throwable {
		try {
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

			arquivoExcelProgramacaoNavio(new File(destino));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private ProgramacaoNavioRN getProgramacaoNavioRN() {
		if (programacaoNavioRN == null) {
			programacaoNavioRN = new ProgramacaoNavioRN();
		}

		return programacaoNavioRN;
	}

}
