package testes;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.mail.EmailException;
import org.junit.Test;

import conexao.com.util.JSFUtil;

public class EnvioEmailTest {
	
	@Test
	public void envioEmail() throws EmailException, IOException {
		JSFUtil jsfUtil = new JSFUtil();
		jsfUtil.envioEmailSimples(new StringTokenizer("murilonadalin@oclif.com.br", ";"), "Portal MAPA CLIF - BL: BL...KADU...123",
				"BL: ");
	}
}
