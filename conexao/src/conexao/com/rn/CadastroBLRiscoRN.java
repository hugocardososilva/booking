package conexao.com.rn;

import java.util.Date;
import java.util.List;

import conexao.com.dao.CadastroBLRiscoDAO;
import seguranca.com.entidade.CadastroBLRiscoFitossanitario;

public class CadastroBLRiscoRN {
	
	private CadastroBLRiscoDAO riscoDAO;
	
	public CadastroBLRiscoDAO getDAO() {
		if (riscoDAO == null) {
			riscoDAO = new CadastroBLRiscoDAO();
		}
		return riscoDAO;
	}

	public void incluirMesmaTransacao(CadastroBLRiscoFitossanitario item) throws Exception {
		try {
			getDAO().beginTransaction();
			getDAO().save(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();
	}
	
	public CadastroBLRiscoFitossanitario incluir(CadastroBLRiscoFitossanitario item) throws Exception {
		if (item.getRiscoFitossanitarioEnum() == null) {
			throw new Exception("Obrigatório selecionar um Risco Fitossanitário! ");
		}

		item.setDataCadastro(new Date());
		getDAO().beginTransaction();
		try {
			getDAO().save(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commit();
		
		return localizar(item.getId());
	}

	public CadastroBLRiscoFitossanitario alterar(CadastroBLRiscoFitossanitario item) throws Exception {
		getDAO().beginTransaction();
		try {
			item = getDAO().alterar(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();
		
		return item;
	}

	public CadastroBLRiscoFitossanitario localizar(int itemId) {
		getDAO().beginTransaction();
		CadastroBLRiscoFitossanitario item = getDAO().localizarPorID(itemId);
		getDAO().closeTransaction();
		return item;
	}

	public void deletar(CadastroBLRiscoFitossanitario item) throws Exception {
		getDAO().beginTransaction();
		try {
			getDAO().delete(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();
	}
	
	public List<CadastroBLRiscoFitossanitario> listaTodos() {
		getDAO().beginTransaction();
		List<CadastroBLRiscoFitossanitario> result = getDAO().listaTodosRegistros();
		getDAO().closeTransaction();
		return result;
	}
	
}