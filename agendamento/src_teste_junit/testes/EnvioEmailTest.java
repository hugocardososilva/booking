package testes;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.mail.EmailException;
import org.junit.BeforeClass;
import org.junit.Test;

import conexao.com.enums.TipoExtensaoArquivoEnum;
import conexao.com.rn.UserFacade;
import conexao.com.util.EnvioEmailParametrosUTIL;
import conexao.com.util.JSFUtil;

public class EnvioEmailTest {

	private static UserFacade rn;

	@BeforeClass
	public static void iniciar() {
		rn = new UserFacade();
	}

	@Test
	public void envioEmail() throws EmailException, IOException {
		String corpoEmail = " Segue confirmacao, de Cadastro do Portal CLIF. " + " \n\n";
		corpoEmail = corpoEmail + " Login =>  000.000.000-00 " + "\n\n";
		corpoEmail = corpoEmail + " Senha => @Clif2017" + " \n\n";
		corpoEmail = corpoEmail + " Segue link para acesso." + " \n\n";
		corpoEmail = corpoEmail + " http://sistemas.oclif.com.br/agendamento" + " \n\n";

		JSFUtil jsfUtil = new JSFUtil();
		jsfUtil.envioEmailSimples(new StringTokenizer("murilonadalin@oclif.com.br", ";"), "teste..", corpoEmail);
	}

	@Test
	public void envioEmailComAnexo() throws EmailException, IOException {
		String corpoEmail = " Segue confirmacao, de Cadastro do Portal CLIF. " + " \n\n";
		corpoEmail = corpoEmail + " Login =>  000.000.000-00 " + "\n\n";
		corpoEmail = corpoEmail + " Senha => @Clif2017" + " \n\n";
		corpoEmail = corpoEmail + " Segue link para acesso." + " \n\n";
		corpoEmail = corpoEmail + " http://sistemas.oclif.com.br/agendamento" + " \n\n";

		JSFUtil jsfUtil = new JSFUtil();

		try {
			EnvioEmailParametrosUTIL paramEmail = new EnvioEmailParametrosUTIL();
			paramEmail.setAssunto("DTC - 1703769373 MONTE VERDE YAHA / MAGNUM 137/17 ATI 672");
			paramEmail.setConteudoEmail("Favor incluir os processos em anexo em segregacao para remocao via DTC." + " \n\n");
			paramEmail.setNomeArquivo("SEGREGACAO - 1703769373");
//			paramEmail.setComCopia("origemsistema@gmail.com");

			String caminhoLogo = "C:/Desenvolvimento/clif/agendamento/WebContent/resources/documentos/Manual de Utilizacao Agendamento.pdf";
			jsfUtil.envioEmailComAnexo(caminhoLogo, rn.localizar(1), "murilo.nadalin@gmail.com", paramEmail, TipoExtensaoArquivoEnum.EXECEL_XLS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
