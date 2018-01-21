package testes;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;

import conexao.com.rn.LeituraArquivoRN;
import conexao.com.util.ConexaoFactorySara;

public class ProgramacaoNavioTest {

	private static LeituraArquivoRN rn;
	private static ConexaoFactorySara factory;

	@BeforeClass
	public static void iniciar() throws IOException, SQLException {
		rn = new LeituraArquivoRN();
		factory = new ConexaoFactorySara();
	}

	@Test
	public void baixarProgramacaoNavio() throws Throwable {
		rn.downloadProgramacaoNavio();
	}

	@Test
	public void comunicaoHttp() throws IOException {
		URL Url = new URL("http://homolog.agricultura.gov.br/sigvig/servicos/application.wadl");
		// URL Url = new URL("http://www.google.com/search?q=mkyong");
		HttpURLConnection connection = (HttpURLConnection) Url.openConnection();
		connection.setRequestProperty("Content-Type", "application/XML");
		connection.setRequestMethod("POST");
		connection.setDoInput(true);
		connection.connect();

		int responseCode = connection.getResponseCode();

		System.out.println(responseCode);
	}

	@Test
	public void testeSaraNavio() throws SQLException, IOException {
		try {

			String sql = "INSERT INTO SYS_PARAMETROS" + "(telasEntidades, tiposParametros, VALOR) VALUES" + "(?,?,?)";
			Connection conexao = factory.getConnection();
			// Connection conexao = ConexaoFactorySara.getConnection();

			PreparedStatement comando = conexao.prepareStatement(sql.toString());
			// comando.setInt(1, 11);
			comando.setString(1, "0");
			comando.setString(2, "0");
			comando.setString(3, "0");
			// comando.setTimestamp(4, getCurrentTimeStamp());
			comando.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
//	 String sql = "UPDATE equipamento SET nm_equipamento =?, "
//             + "num_patrimonio =?, cod_tipo_equip =?,"
//             + "modelo_equip =?"
//             + " WHERE cod_equipamento =?";
//try{
// PreparedStatement stm = con.prepareStatement(sql);
// stm.setString(1, equip.getNomeEquip());
// stm.setString(2, equip.getPatrimonio());
// stm.setInt(3, equip.getTipoId());
// stm.setString(4, equip.getModelo());
// stm.setInt(5, equip.getId());
// stm.execute();
// stm.close();
// JOptionPane.showMessageDialog(null, "Equipamento alterado com sucesso");
//}catch(SQLException ex){
// throw new RuntimeException();
//}	

//	String sql = "SELECT COUNT(*) FROM mytable" ;
//    PreparedStatement prest = con.prepareStatement(sql);
//    ResultSet rs = prest.executeQuery();	
	
}
