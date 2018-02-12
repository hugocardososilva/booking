package conexao.com.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactorySara {
	private static Connection con;
	private static String url;
	private static String usuario;
	private static String senha;
	private JSFUtil jsfUtil;

	public ConexaoFactorySara() throws IOException, SQLException {
		super();

		con = null;
		url = getJsfUtil().getPropertyIniConfiguracao("prop.connection.sara.url");
		usuario = getJsfUtil().getPropertyIniConfiguracao("prop.connection.sara.user");
		senha = getJsfUtil().getPropertyIniConfiguracao("prop.connection.sara.password");
	}

	private JSFUtil getJsfUtil() {
		if (jsfUtil == null) {
			jsfUtil = new JSFUtil();
		}
		return jsfUtil;
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		ConexaoFactorySara.con = con;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		ConexaoFactorySara.url = url;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		ConexaoFactorySara.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		ConexaoFactorySara.senha = senha;
	}

	public Connection getConnection() throws SQLException, IOException {
		if (con == null) {
			DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
			con = DriverManager.getConnection(url, usuario, senha);
		}

		return con;
	}

}