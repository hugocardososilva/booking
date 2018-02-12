package conexao.com.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import seguranca.com.entidade.UserDadosConexao;

public class ConexaoFactoryJasper {
	private static Connection con;
	private static String url;
	private static String usuario;
	private static String senha;

	public ConexaoFactoryJasper() throws IOException, SQLException {
		super();

//		ConexaoFactoryJasper.con = null;
//		ConexaoFactoryJasper.url = getJsfUtil().getPropertyIniConfiguracao("prop.connection.url");
//		ConexaoFactoryJasper.usuario = getJsfUtil().getPropertyIniConfiguracao("prop.connection.user");
//		ConexaoFactoryJasper.senha = getJsfUtil().getPropertyIniConfiguracao("prop.connection.password");
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		ConexaoFactoryJasper.con = con;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		ConexaoFactoryJasper.url = url;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		ConexaoFactoryJasper.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		ConexaoFactoryJasper.senha = senha;
	}

	public static Connection getConnection() throws SQLException, IOException {
		if (con == null) {
			LoginController login = new LoginController();
			UserDadosConexao conexao = login.getRetornaUsuarioLogado().getUserDadosConexao();
			
			url = conexao.getConnectionUrl();
			usuario = conexao.getConnectionUser();
			senha = conexao.getConnectionPassword();

			if (conexao.getConnectionJasper().equals("MYSQL")) {
				DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			}
			if (conexao.getConnectionJasper().equals("SQLSERVER")) {
				DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
			}
			con = DriverManager.getConnection(url, usuario, senha);
		}

		return con;
	}
	
}