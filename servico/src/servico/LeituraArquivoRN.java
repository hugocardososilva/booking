package servico;

import java.io.File;
import java.util.Date;

import jxl.Cell;
import jxl.Sheet;

public class LeituraArquivoRN {

	private ProgramacaoNavioRN programacaoNavioRN;

	@SuppressWarnings("deprecation")
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

	// public void arquivoExcelProgramacaoNavioTeste(String arquivo) throws
	// IOException {
	// String destino = "C:/services/arquivo/lista_programacao_navios -
	// Copia.xls";
	//
	// FileInputStream arquivoStream = new FileInputStream(destino);
	// HSSFWorkbook workbook = new HSSFWorkbook(arquivoStream);
	//
	// HSSFSheet sheetAlunos = workbook.getSheetAt(0);
	//
	// Iterator<Row> rowIterator = sheetAlunos.iterator();
	//
	// while (rowIterator.hasNext()) {
	// Row row = rowIterator.next();
	// Iterator<Cell> cellIterator = row.cellIterator();
	//
	// ProgramacaoNavio navio =
	// getProgramacaoNavioRN().pesquisaNavioViagem(null, null);
	// if (navio == null) {
	// navio = new ProgramacaoNavio();
	// }
	// while (cellIterator.hasNext()) {
	// Cell cell = cellIterator.next();
	//
	//// cell.getColumnIndex()
	////
	//// sheet.getCell(1, i);
	//
	// switch (cell.getColumnIndex()) {
	//
	// case 1:
	// navio.setNavio(cell.getStringCellValue());
	// break;
	// case 2:
	// navio.setNavioViagem(String.valueOf(cell.getStringCellValue()));
	// break;
	// case 8:
	// navio.setDataETA(new Date(cell.getStringCellValue()));
	// break;
	// case 9:
	// navio.setDataATA(new Date(cell.getStringCellValue()));
	// break;
	// case 12:
	// navio.setDataETS(new Date(cell.getStringCellValue()));
	// break;
	// }
	// }
	// }
	// arquivoStream.close();
	// }

	private ProgramacaoNavioRN getProgramacaoNavioRN() {
		if (programacaoNavioRN == null) {
			programacaoNavioRN = new ProgramacaoNavioRN();
		}

		return programacaoNavioRN;
	}

}
