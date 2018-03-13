package conexao.com.util;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

import conexao.com.enums.TipoExtensaoArquivoEnum;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import seguranca.com.entidade.User;
import seguranca.com.enums.TipoAnexosEnum;
import seguranca.com.enums.TipoCryptografiaEnum;
import seguranca.com.enums.TipoRelatoriosEnum;

public class JSFUtil {
	private static Properties properties;
	private static Properties propertiesGenerico;
	private static final Charset UTF_8 = StandardCharsets.UTF_8;

	public void envioEmailSimples(StringTokenizer destinatario, String assunto, String corpo)
			throws EmailException, IOException {

		StringTokenizer stPara = destinatario;

		SimpleEmail email = new SimpleEmail();

		email.setSSLOnConnect(false);
		// email.setDebug(true);
//		email.setSmtpPort(Integer.parseInt(getPropertyIniConfiguracao("prop.email.smtp_port_user")));
		email.setSmtpPort(Integer.parseInt(getPropertyIniConfiguracao("prop.email.smtp_port")));
		email.setHostName(getPropertyIniConfiguracao("prop.email.smtp"));
		email.setAuthentication(getPropertyIniConfiguracao("prop.email.smtp_user"),
				getPropertyIniConfiguracao("prop.email.smtp_password"));
		email.setFrom(getPropertyIniConfiguracao("prop.email.smtp_user"));

		while (stPara.hasMoreTokens()) {
			email.addTo(stPara.nextToken().trim());
		}
		email.setSubject(assunto);
		email.setMsg(corpo);
		email.send();
	}

	public void envioEmailSimplesComCopia(String destinatario, String comCopia, String assunto, String corpo,
			boolean envioCo) throws EmailException, IOException {

		StringTokenizer stPara = new StringTokenizer(destinatario, ";");
		StringTokenizer stCcopia = new StringTokenizer(comCopia, ";");

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

			if (envioCo) {
				email.addBcc(stCcopia.nextToken().trim());
			} else {
				email.addCc(stCcopia.nextToken().trim());
			}

			email.setSubject(assunto);
			email.setMsg(corpo);
			email.send();
		}
	}

	public void envioEmailComAnexo(String caminhoArquivo, User user, String destinatario,
			EnvioEmailParametrosUTIL paramEmail, TipoExtensaoArquivoEnum extensao) throws EmailException, IOException {

		if (user != null) {
			destinatario = destinatario + ";" + user.getEmail();
		}

		StringTokenizer stPara = new StringTokenizer(destinatario, ";");

		StringTokenizer stCcopia = null;
		if (paramEmail.getComCopia() != null) {
			stCcopia = new StringTokenizer(paramEmail.getComCopia(), ";");
		}

		// Cria a mensagem de e-mail.
		MultiPartEmail email = new MultiPartEmail();
		if (user == null) {
			email.setSmtpPort(Integer.parseInt(getPropertyIniConfiguracao("prop.email.smtp_port")));
			email.setHostName(getPropertyIniConfiguracao("prop.email.smtp"));
			email.setAuthentication(getPropertyIniConfiguracao("prop.email.smtp_user"),
					getPropertyIniConfiguracao("prop.email.smtp_password"));
			email.setFrom(getPropertyIniConfiguracao("prop.email.smtp_user"));
		} else {
			email.setHostName(getPropertyIniConfiguracao("prop.email.smtp"));
			email.setSmtpPort(Integer.parseInt(getPropertyIniConfiguracao("prop.email.smtp_port_user")));
			// remetente
			email.setFrom(user.getEmail());
		}

		// destinatario
		while (stPara.hasMoreTokens()) {
			email.addTo(stPara.nextToken().trim());
		}

		if (stCcopia != null) {
			while (stCcopia.hasMoreTokens()) {
				email.addCc(stCcopia.nextToken().trim());
			}
		}

		for (Entry<String, String> entry : paramEmail.getVariosAnexos().entrySet()) {
			if (entry.getValue() != null) {
				// cria o anexo.
				EmailAttachment attachment = new EmailAttachment();
				// caminho da imagem
				attachment.setPath(entry.getKey());
				attachment.setDisposition(EmailAttachment.ATTACHMENT);
				// attachment.setDescription("Picture of John");
				attachment.setName(entry.getValue() + extensao.getDescricao());
				// adiciona o anexo � mensagem
				email.attach(attachment);
			}
		}

		// Assunto
		email.setSubject(paramEmail.getAssunto());
		// conteudo do e-mail
		email.setMsg(paramEmail.getConteudoEmail());

		email.send();
	}

	public Properties getIniConfiguracao() throws IOException {
		if (properties == null) {
			properties = new Properties();

			InputStream file = getClass().getClassLoader()
					.getResourceAsStream("META-INF/properties.conexao/dados.properties");

			properties.load(file);
		}

		return properties;
	}

	private Properties getCarregaArquivoProperties(String caminhoArquivo) throws IOException {
		if (propertiesGenerico == null) {
			propertiesGenerico = new Properties();

			InputStream file = getClass().getClassLoader().getResourceAsStream(caminhoArquivo);

			propertiesGenerico.load(file);
		}

		return propertiesGenerico;
	}

	public String getPropertyRecuperaInformcao(String property, String caminhoArquivo) throws IOException {
		Properties properties = getCarregaArquivoProperties(caminhoArquivo);

		return properties.getProperty(property);
	}

	public String getPropertyIniConfiguracao(String property) throws IOException {
		Properties properties = getIniConfiguracao();

		return properties.getProperty(property);
	}

	public static String getVerificaSistemaOperacional() {
		return System.getProperty("os.name").substring(0, 3).toUpperCase();
	}

	public static String getCaminhoAplicacaoPath() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ServletContext request = (ServletContext) facesContext.getExternalContext().getContext();

		return request.getContextPath();
	}

	/**
	 * Metodo para criptografar senha...
	 * 
	 * @param senha
	 * @param tipo
	 * @return String
	 */
	public static String getCriptografarSenha(String senha, TipoCryptografiaEnum tipo) {
		String retorno = null;
		try {
			if (tipo == TipoCryptografiaEnum.CRIPTOGRAFAR) {
				byte[] senhaCriptografada = Base64.encodeBase64(senha.getBytes(UTF_8));
				retorno = new String(senhaCriptografada, UTF_8);
			}

			if (tipo == TipoCryptografiaEnum.DESCRIPTOGRAFAR) {
				byte[] senhaDescriptografada = Base64.decodeBase64(senha.getBytes());
				retorno = new String(senhaDescriptografada, UTF_8);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return retorno;
	}

	public String getCaminhoAnexos(TipoAnexosEnum tipo) throws IOException {
		String valor = getPropertyIniConfiguracao("prop.upload.anexos.status_anexo");
		if (tipo.equals(TipoAnexosEnum.BL)) {
			valor = getPropertyIniConfiguracao("prop.upload.anexos.bl");
		}
		if (tipo.equals(TipoAnexosEnum.CE)) {
			valor = getPropertyIniConfiguracao("prop.upload.anexos.ce");
		}
		if (tipo.equals(TipoAnexosEnum.INVOICE)) {
			valor = getPropertyIniConfiguracao("prop.upload.anexos.invoice");
		}
		if (tipo.equals(TipoAnexosEnum.PACKING)) {
			valor = getPropertyIniConfiguracao("prop.upload.anexos.packing");
		}
		if (tipo.equals(TipoAnexosEnum.STATUS_ANEXO)) {
			valor = getPropertyIniConfiguracao("prop.upload.anexos.status_anexo");
		}
		if (tipo.equals(TipoAnexosEnum.SOLICITACAO)) {
			valor = getPropertyIniConfiguracao("prop.upload.anexos.anexo_solicitacao");
		}

		return valor;
	}
	

	public BufferedImage getLogoImage(byte[] imagem) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(imagem);
		return ImageIO.read(bais);
	}

	/**
	 * Converte arquivo para byte
	 * 
	 * @param arquivo
	 * @return byte[]
	 */
	public byte[] converteArquivoToByte(String arquivo) {
		FileInputStream fileInputStream = null;
		byte[] bytesArray = null;

		try {
			File file = new File(arquivo);
			bytesArray = new byte[(int) file.length()];

			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bytesArray);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return bytesArray;
	}

	public File gerarRelatorio(TipoRelatoriosEnum tipoRelatorio, Map<String, Object> parametros, String valorTitulo,
			boolean logoPadrao, boolean envioEmail) throws SQLException, IOException, JRException {

		if (logoPadrao) {
			String caminhoLogo = FacesContext.getCurrentInstance().getExternalContext()
					.getRealPath("/resources/images/clif.png");

			byte[] arquivoByte = converteArquivoToByte(caminhoLogo);

			parametros.put("PARAM_LOGO", getLogoImage(arquivoByte));
		}

		String caminho = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/reports/" + tipoRelatorio.getNomeRelatorioJasper());

		Connection conexao = ConexaoFactoryJasper.getConnection();

		JasperReport report = JasperCompileManager.compileReport(caminho);

		JasperPrint print = JasperFillManager.fillReport(report, parametros, conexao);

		byte[] b = JasperExportManager.exportReportToPdf(print);

		if (envioEmail) {
			String arquivoTemp = caminhoTemporario();
			arquivoTemp = arquivoTemp + tipoRelatorio.getNomeArquivoRelatorioDownload() + valorTitulo + ".pdf";
			File file = new File(arquivoTemp); // Criamos um nome para o arquivo
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(b); // Gravamos os bytes l�
			bos.close();
			return file;
		} else {
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();

			response.setContentType("application/pdf");

			response.setHeader("Content-disposition",
					"attachment;filename=" + tipoRelatorio.getNomeArquivoRelatorioDownload() + valorTitulo + ".pdf");

			response.getOutputStream().write(b);
			response.getCharacterEncoding();

			FacesContext.getCurrentInstance().responseComplete();
		}
		return null;
	}

	public void baixarDocumentos(String nomeArquivo) throws SQLException, IOException, JRException {
		byte[] b = converteArquivoToByte(nomeArquivo);

		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();

		response.setContentType("application/pdf");

		response.setHeader("Content-disposition", "attachment;filename=" + "arquivo.pdf");

		response.getOutputStream().write(b);
		response.getCharacterEncoding();

		FacesContext.getCurrentInstance().responseComplete();
	}

	public String caminhoTemporario() {
		return System.getProperty("java.io.tmpdir").toString();
	}
	
	public File criarArquivoTemporario(byte[] b) throws IOException {
		String arquivoTemp = caminhoTemporario();
		arquivoTemp = arquivoTemp + "arquivo_temp" + ".pdf";
		File file = new File(arquivoTemp); // Criamos um nome para o arquivo
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
		bos.write(b); // Gravamos os bytes l�
		bos.close();
		return file;
	}
	
	public void deletarArquivoTemporario(File arq) {
		arq.delete();
	}
	
	public static String getIp() {
        String ipAddress = null;
        Enumeration<NetworkInterface> net = null;
        try {
            net = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        while (net.hasMoreElements()) {
            NetworkInterface element = net.nextElement();
            Enumeration<InetAddress> addresses = element.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress ip = addresses.nextElement();

                if (ip.isSiteLocalAddress()) {
                    ipAddress = ip.getHostAddress();
                }           
            }
        }
        return ipAddress;
    }	
}
