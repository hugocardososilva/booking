package conexao.com.rn;

import java.util.List;

import conexao.com.dao.ArquivosCobrancaEsalesDAO;
import seguranca.com.entidade.ArquivosCobrancaEsalesSeguranca;

public class ArquivosCobrancaEsalesRN {

	private ArquivosCobrancaEsalesDAO dao;

	public ArquivosCobrancaEsalesDAO getDAO() {
		if (dao == null) {
			dao = new ArquivosCobrancaEsalesDAO();
		}
		return dao;
	}

	public ArquivosCobrancaEsalesSeguranca incluir(ArquivosCobrancaEsalesSeguranca item) throws Exception {
		getDAO().beginTransaction();
		getDAO().save(item);
		getDAO().commitAndCloseTransaction();
		
		return item;
	}

	public ArquivosCobrancaEsalesSeguranca alterar(ArquivosCobrancaEsalesSeguranca item) throws Exception {
		getDAO().beginTransaction();
		item = getDAO().alterar(item);
		getDAO().commitAndCloseTransaction();
		
		return item;
	}

	public ArquivosCobrancaEsalesSeguranca localizar(int itemId) {
		getDAO().beginTransaction();
		ArquivosCobrancaEsalesSeguranca item = getDAO().localizarPorID(itemId);
		getDAO().closeTransaction();
		return item;
	}

	public void deletar(ArquivosCobrancaEsalesSeguranca item) {
		getDAO().beginTransaction();
		getDAO().delete(item);
		getDAO().commitAndCloseTransaction();
	}
	
	public List<ArquivosCobrancaEsalesSeguranca> listaTodos() {
		getDAO().beginTransaction();
		List<ArquivosCobrancaEsalesSeguranca> result = getDAO().listaTodosRegistros();
		getDAO().closeTransaction();
		return result;
	}
	
	public ArquivosCobrancaEsalesSeguranca pesquisarEntidadeParamateros(String password, String descricao) {
		getDAO().beginTransaction();
		
		ArquivosCobrancaEsalesSeguranca user = getDAO().validarLoginTESTE(password, descricao);
		
		getDAO().closeTransaction();
		return user;
	}
	
}