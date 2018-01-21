package testes;

import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Row;
import org.junit.BeforeClass;
import org.junit.Test;

import com.rn.CadastroDTCDTARN;

import conexao.com.enums.TipoExtensaoArquivoEnum;
import conexao.com.rn.UserFacade;
import conexao.com.util.EnvioEmailParametrosUTIL;
import conexao.com.util.JSFUtil;

public class ExcelGerarArquivoTest {

	private static CadastroDTCDTARN rnDTC;
	private static JSFUtil jsfUtil;
	private static UserFacade rnUser;

	@BeforeClass
	public static void iniciar() {
		rnDTC = new CadastroDTCDTARN();
		jsfUtil = new JSFUtil();
		rnUser = new UserFacade();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void criaExcel() {
		String caminho = null;
		caminho = "C:/services/arquivo/" + "teste.xls";

		try {
			// Cria um Arquivo Excel
			HSSFWorkbook  wb = new HSSFWorkbook();

			// Cria uma planilha Excel
			HSSFSheet  sheet = wb.createSheet("Segregacao");

			// Cria uma linha na Planilha.
			Row linha1 = sheet.createRow((short) 0);
			Row linha2 = sheet.createRow((short) 1);
			Row linha3 = sheet.createRow((short) 2);
			Row linha4 = sheet.createRow((short) 3);
			Row linha5 = sheet.createRow((short) 4);
			Row linha6 = sheet.createRow((short) 5);
			Row linha7 = sheet.createRow((short) 6);
			Row linha11 = sheet.createRow((short) 10);
			Row linha12 = sheet.createRow((short) 11);
			Row linha13 = sheet.createRow((short) 12);
			Row linha14 = sheet.createRow((short) 13);
			Row linha15 = sheet.createRow((short) 14);
			Row linha16 = sheet.createRow((short) 15);

			// Cria as células na linha
			linha1.createCell(0).setCellValue("porto de itapoa");
			linha1.createCell(1).setCellValue("Itapoá Terminais Portuários S.A.");
			sheet.addMergedRegion(new CellRangeAddress(0,0,1,8));
			linha1.createCell(9).setCellValue("SGI");
			sheet.addMergedRegion(new CellRangeAddress(0,0,9,10));

			linha2.createCell(0).setCellValue("Código F.OPR.IEX.38");
			linha2.createCell(1).setCellValue("Formulário REQUISIÇÃO DE SEGREGAÇÃO DE DTC");
			sheet.addMergedRegion(new CellRangeAddress(1,1,1,8));
			linha2.createCell(9).setCellValue("Página 1/1");
			sheet.addMergedRegion(new CellRangeAddress(1,1,9,10));

			DateFormat df = new SimpleDateFormat("dd/MMM");
			linha3.createCell(0).setCellValue("NAVIO/VG:");
			linha3.createCell(1).setCellValue("NOME NAVIO");
			sheet.addMergedRegion(new CellRangeAddress(2,2,1,3));
			linha3.createCell(4).setCellValue("ATRACAÇÃO:");
			linha3.createCell(5).setCellValue(df.format(new Date()));
			sheet.addMergedRegion(new CellRangeAddress(2,2,5,10));

			linha4.createCell(0).setCellValue("CNPJ NVOCC/AGENTE:");
			linha4.createCell(4).setCellValue("CE MASTER:");
			linha4.createCell(5).setCellValue("171705197185707");
			sheet.addMergedRegion(new CellRangeAddress(3,3,5,10));
			
			linha5.createCell(4).setCellValue("CE HOUSE:");
			linha5.createCell(5).setCellValue("");
			sheet.addMergedRegion(new CellRangeAddress(4,4,5,10));
			
			linha6.createCell(0).setCellValue("Solicitação");
			sheet.addMergedRegion(new CellRangeAddress(5,5,0,3));
			linha6.createCell(4).setCellValue("CONTAINER");
			sheet.addMergedRegion(new CellRangeAddress(5,5,4,10));

			linha7.createCell(0).setCellValue("(X) Segregação (_____) Cancelamento de segregação");
			sheet.addMergedRegion(new CellRangeAddress(6,6,0,3));
			linha7.createCell(4).setCellValue("SUDU7506999");
			sheet.addMergedRegion(new CellRangeAddress(6,6,4,10));

			linha11.createCell(0).setCellValue("REQUISITOS PARA SEGREGAÇÃO");
			sheet.addMergedRegion(new CellRangeAddress(10,10,0,10));

			linha12.createCell(0).setCellValue("Lote completo do CE Master");
			sheet.addMergedRegion(new CellRangeAddress(11,11,0,10));

			linha13.createCell(0).setCellValue("CNPJ NVOCC/AGENTE informado deve ser o constante do CE House quando houver");
			sheet.addMergedRegion(new CellRangeAddress(12,12,0,10));

			linha14.createCell(0).setCellValue("Prazo para segregação: 24h antes do ETA do Navio");
			sheet.addMergedRegion(new CellRangeAddress(13,13,0,10));

			linha15.createCell(0).setCellValue("Solicitamos a segregação e custos serão direcionados para:");
			sheet.addMergedRegion(new CellRangeAddress(14,14,0,3));
			linha15.createCell(4).setCellValue("CNPJ:");
			sheet.addMergedRegion(new CellRangeAddress(14,14,4,10));
			
			linha16.createCell(0).setCellValue("O formulário deverá ser preenchido e enviado para o endereço eletrônico: opr-dtc@portoitapoa.com.br");
			sheet.addMergedRegion(new CellRangeAddress(15,15,0,10));
			
			try (FileOutputStream fileOut = new FileOutputStream(caminho)) {
				wb.write(fileOut);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	@Test
	public void gerarSegregacao() throws Exception {
		EnvioEmailParametrosUTIL param = new EnvioEmailParametrosUTIL();
		param.setAssunto("TESTE ARQ. SEGREGAÇÃO");
		param.setConteudoEmail("TESTE CONTEUDO EMAIL");
		param.setNomeArquivo("SEGREGACAO");
		param.setComCopia("adilsoferreira@oclif.com.br;elisangelahnatiak@oclif.com.br;origemsistema@gmail.com;fabioormerod@oclif.com.br");
		
		String caminhoArquivo = rnDTC.gerarSegregacaoExcel(rnDTC.localizar(122));
		
		jsfUtil.envioEmailComAnexo(caminhoArquivo, rnUser.localizar(1), "murilo.nadalin@gmail.com", param, TipoExtensaoArquivoEnum.EXECEL_XLS);
	}
}
