package testes;

import org.junit.Test;

import clif.chat.app.frame.ClienteFrame;
import clif.chat.app.service.ServidorService;

public class ChatServidorTest {

	@Test
	public void testeServidorChat() {
		new ServidorService();
	}

	@Test
	public void testeClienteChat() {
		new ClienteFrame().setVisible(true);
		System.out.println("teste");
	}
	
}
