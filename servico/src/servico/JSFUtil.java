package servico;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class JSFUtil {
	private static Properties properties;
	private static final Charset UTF_8 = StandardCharsets.UTF_8;

	public void envioEmailSimples(String destinatario, String assunto, String corpo)
			throws EmailException, IOException {

		StringTokenizer stPara = new StringTokenizer(destinatario, ";");

		while (stPara.hasMoreTokens()) {
			SimpleEmail email = new SimpleEmail();

			email.setSSLOnConnect(false);
			// email.setDebug(true);
			email.setSmtpPort(Integer.parseInt(getPropertyIniConfiguracao("prop.email.smtp_port")));
			email.setHostName(getPropertyIniConfiguracao("prop.email.smtp"));
			email.setAuthentication(getPropertyIniConfiguracao("prop.email.smtp_user"),
					getPropertyIniConfiguracao("prop.email.smtp_password"));
			email.setFrom(getPropertyIniConfiguracao("prop.email.smtp_user"));

			email.addTo(stPara.nextToken().trim());
			email.setSubject(assunto);
			email.setMsg(corpo);
			email.send();
		}
	}

	public Properties getIniConfiguracao() throws IOException {
		if (properties == null) {
			properties = new Properties();

			InputStream file = getClass().getClassLoader().getResourceAsStream("META-INF/properties.conexao/dados.properties");

			properties.load(file);
		}

		return properties;
	}

	public String getPropertyIniConfiguracao(String property) throws IOException {
		Properties properties = getIniConfiguracao();

		return properties.getProperty(property);
	}

	public static String getVerificaSistemaOperacional() {
		return System.getProperty("os.name").substring(0, 3).toUpperCase();
	}

	
	
}
