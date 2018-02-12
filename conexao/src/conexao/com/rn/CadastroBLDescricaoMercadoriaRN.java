package conexao.com.rn;

import java.util.Date;
import java.util.List;

import conexao.com.dao.CadastroBLDescMercadoriaDAO;
import seguranca.com.entidade.CadastroBLDescricaoMercadoria;

public class CadastroBLDescricaoMercadoriaRN {
	
	private CadastroBLDescMercadoriaDAO descMercadoriaDAO;
	
	public CadastroBLDescMercadoriaDAO getDAO() {
		if (descMercadoriaDAO == null) {
			descMercadoriaDAO = new CadastroBLDescMercadoriaDAO();
		}
		return descMercadoriaDAO;
	}

	public CadastroBLDescricaoMercadoria incluir(CadastroBLDescricaoMercadoria item) throws Exception {
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

	public CadastroBLDescricaoMercadoria alterar(CadastroBLDescricaoMercadoria item) throws Exception {
		getDAO().beginTransaction();
		try {
			item = getDAO().alterar(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();
		
		return item;
	}

	public CadastroBLDescricaoMercadoria localizar(int itemId) {
		getDAO().beginTransaction();
		CadastroBLDescricaoMercadoria item = getDAO().localizarPorID(itemId);
		getDAO().closeTransaction();
		return item;
	}

	public void deletar(CadastroBLDescricaoMercadoria item) throws Exception {
		getDAO().beginTransaction();
		try {
			getDAO().delete(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();
	}
	
	public List<CadastroBLDescricaoMercadoria> listaTodos() {
		getDAO().beginTransaction();
		List<CadastroBLDescricaoMercadoria> result = getDAO().listaTodosRegistros();
		getDAO().closeTransaction();
		return result;
	}
	
}