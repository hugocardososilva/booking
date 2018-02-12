package conexao.com.bean;

import java.util.StringTokenizer;

import conexao.com.rn.UserFacade;
import conexao.com.util.JSFMessageUtil;
import conexao.com.util.JSFUtil;
import seguranca.com.entidade.User;
import seguranca.com.enums.TipoCryptografiaEnum;

public class EnviarSenhaComumBean extends AbstractMB  {
	public static final String PAGINA_LOGIN_PORTAL_MAPA = "/portalmapa/";
	public static final String PAGINA_LOGIN_AGENDAMENTO = "/agendamento/";

	private String email;
	private String cpf;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void enviarEmail(int pgInicial) {
		try {
			UserFacade userFacade = new UserFacade();
			User user = userFacade.localizarUsuarioCpfEmail(getCpf(), getEmail());
			
			if (user != null) {
				String senhaCorreta = JSFUtil.getCriptografarSenha(user.getPassword(), TipoCryptografiaEnum.DESCRIPTOGRAFAR);
				JSFUtil jsfUtil = new JSFUtil();
				jsfUtil.envioEmailSimples(new StringTokenizer(user.getEmail(), ";"), "Envio de Senha Portal MAPA", "Sua senha e: " + senhaCorreta );
				
				JSFMessageUtil.adicionarMensagemSucesso("Senha enviada com sucesso para o e-mail: " + this.email);
				if (pgInicial == 1) {
					redirecionarPaginas(PAGINA_LOGIN_PORTAL_MAPA);
				}
				if (pgInicial == 2) {
					redirecionarPaginas(PAGINA_LOGIN_AGENDAMENTO);
				}
			} else {
				JSFMessageUtil.adicionarMensagemErro("Usuario nao localizado!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}
