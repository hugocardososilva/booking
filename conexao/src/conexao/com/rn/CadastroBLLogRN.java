package conexao.com.rn;

import java.util.Date;
import java.util.List;

import conexao.com.dao.CadastroBLLogDAO;
import seguranca.com.entidade.CadastroBL;
import seguranca.com.entidade.CadastroBLAnexos;
import seguranca.com.entidade.CadastroBlLogAlteracao;
import seguranca.com.entidade.User;

public class CadastroBLLogRN {
	
	private CadastroBLLogDAO logDAO;
	
	public CadastroBLLogDAO getDAO() {
		if (logDAO == null) {
			logDAO = new CadastroBLLogDAO();
		}
		return logDAO;
	}

	public CadastroBlLogAlteracao incluir(CadastroBlLogAlteracao item) throws Exception {
		getDAO().beginTransaction();
		try {
			getDAO().save(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();
		
		return localizar(item.getId());
	}

	public CadastroBlLogAlteracao alterar(CadastroBlLogAlteracao item) throws Exception {
		getDAO().beginTransaction();
		try {
			item = getDAO().alterar(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commit();
		
		return item;
	}
	
	public void fecharConexaoLog() {
		getDAO().closeTransaction();
	}

	public CadastroBlLogAlteracao localizar(int itemId) throws Exception {
		CadastroBlLogAlteracao item = null;
		getDAO().beginTransaction();
		try {
			item = getDAO().localizarPorID(itemId);
		} catch (Exception e) {
			getDAO().rollback();
			throw new Exception(e.getMessage());
		}
		getDAO().closeTransaction();
		return item;
	}

	public void deletar(CadastroBlLogAlteracao item) {
		getDAO().beginTransaction();
		getDAO().delete(item);
		getDAO().commitAndCloseTransaction();
	}
	
	public List<CadastroBlLogAlteracao> listaTodos() throws Exception {
		List<CadastroBlLogAlteracao> result = null;
		getDAO().beginTransaction();
		try {
			result = getDAO().listaTodosRegistros();
		} catch (Exception e) {
			getDAO().rollback();
			throw new Exception(e.getMessage());
		}
		getDAO().closeTransaction();
		return result;
	}
	
	public CadastroBlLogAlteracao getInstanciarLog(CadastroBLAnexos itemBlAnexo, CadastroBL entBL, User user) {
		CadastroBlLogAlteracao log = new CadastroBlLogAlteracao();
		if (itemBlAnexo != null) {
			log.setCadastroBL(itemBlAnexo.getCadastroBL());
		}
		if (entBL != null) {
			log.setCadastroBL(entBL);
		}
		log.setDataAlteracao(new Date());
		if (user != null) {
			log.setUser(user);
		}
		
		return log;
	}
	
	
}