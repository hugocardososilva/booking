package conexao.com.rn;

import java.util.List;

import conexao.com.dao.CadastroBLAnexosDAO;
import seguranca.com.entidade.CadastroBL;
import seguranca.com.entidade.CadastroBLAnexos;
import seguranca.com.entidade.CadastroBlLogAlteracao;
import seguranca.com.entidade.User;
import seguranca.com.enums.TipoAnexosEnum;

public class CadastroBLAnexosRN {

	private CadastroBLAnexosDAO cadBLAnexosDAO;
	private CadastroBLLogRN logRN;

	public CadastroBLAnexosDAO getCadastroBLAnexosDAO() {
		if (cadBLAnexosDAO == null) {
			cadBLAnexosDAO = new CadastroBLAnexosDAO();
		}
		return cadBLAnexosDAO;
	}

	public CadastroBLAnexos incluir(CadastroBLAnexos item) throws Exception {
		getCadastroBLAnexosDAO().beginTransaction();
		try {
			getCadastroBLAnexosDAO().save(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getCadastroBLAnexosDAO().commitAndCloseTransaction();

		return localizar(item.getId());
	}

	public CadastroBLAnexos alterar(CadastroBLAnexos item, User user) throws Throwable {
		getCadastroBLAnexosDAO().beginTransaction();
		try {
			item = getCadastroBLAnexosDAO().alterar(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getCadastroBLAnexosDAO().commit();

		CadastroBlLogAlteracao log = getLogRN().getInstanciarLog(item, null, user);
		log.setAnexo(true);
		log.setCadastroBL(item.getCadastroBL());
		try {
			getLogRN().alterar(log);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

//		CadastroBL cadastroBL = getCadastroBLRN().localizarNaMesmaTransacao(item.getCadastroBL().getId());
//		cadastroBL.getListaCadastroBLAnexos().add(item);
		
		getCadastroBLAnexosDAO().closeTransaction();
		return item;
	}

	public CadastroBLAnexos salvar(CadastroBLAnexos item) throws Throwable {
		getCadastroBLAnexosDAO().beginTransaction();
		try {
			item = getCadastroBLAnexosDAO().alterar(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getCadastroBLAnexosDAO().commitAndCloseTransaction();
		return item;
	}
	
	private CadastroBLLogRN getLogRN() {
		if (logRN == null) {
			logRN = new CadastroBLLogRN();
		}
		return logRN;
	}

	public CadastroBLAnexos localizar(int itemId) {
		getCadastroBLAnexosDAO().beginTransaction();
		CadastroBLAnexos item = getCadastroBLAnexosDAO().localizarPorID(itemId);
		getCadastroBLAnexosDAO().closeTransaction();
		return item;
	}

	public void deletar(CadastroBLAnexos item) throws Exception {
		getCadastroBLAnexosDAO().beginTransaction();
		try {
			getCadastroBLAnexosDAO().delete(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getCadastroBLAnexosDAO().commitAndCloseTransaction();
	}

	public List<CadastroBLAnexos> listaTodos() {
		getCadastroBLAnexosDAO().beginTransaction();
		List<CadastroBLAnexos> result = getCadastroBLAnexosDAO().listaTodosRegistros();
		getCadastroBLAnexosDAO().closeTransaction();
		return result;
	}

	public List<CadastroBLAnexos> listaTodosPorTipoAnexos(CadastroBL bl, TipoAnexosEnum tipoAnexo) {
		getCadastroBLAnexosDAO().beginTransaction();
		List<CadastroBLAnexos> result = getCadastroBLAnexosDAO().listaAnexosPorTipo(bl, tipoAnexo);
		getCadastroBLAnexosDAO().closeTransaction();
		return result;
	}

	public List<CadastroBLAnexos> listaAnexosPorTipoEmail(CadastroBL bl) {
		getCadastroBLAnexosDAO().beginTransaction();
		List<CadastroBLAnexos> result = getCadastroBLAnexosDAO().listaAnexosPorTipoEmailDAO(bl);
		getCadastroBLAnexosDAO().closeTransaction();
		return result;
	}

	public List<CadastroBLAnexos> listaTodosPorTipoAnexosMapa(CadastroBL bl) {
		getCadastroBLAnexosDAO().beginTransaction();
		List<CadastroBLAnexos> result = getCadastroBLAnexosDAO().listaAnexosMapaPorTipo(bl);
		getCadastroBLAnexosDAO().closeTransaction();
		return result;
	}
	
	public String executarQtdeRegistros(CadastroBL bl, TipoAnexosEnum tipoAnexo) throws Exception {
		String retorno = null;
		float result = 0;
		
		getCadastroBLAnexosDAO().beginTransaction();
		try {
			result = getCadastroBLAnexosDAO().executarQtdeRegistros(bl, tipoAnexo);
		} catch (Exception e) {
			getCadastroBLAnexosDAO().rollback();
			throw new Exception(e.getMessage());
		}
		getCadastroBLAnexosDAO().closeTransaction();
		
		if (result == 0) {
			retorno = tipoAnexo.getDescricao() + " -->> Pendente de Anexo";
		} else {
			retorno = tipoAnexo.getDescricao() + " -->> Documento Anexado";
		}
		
		return retorno;
	}

	public float executarQtdeRegistrosValor(CadastroBL bl, TipoAnexosEnum tipoAnexo) throws Exception {
		float result = 0;
		
		getCadastroBLAnexosDAO().beginTransaction();
		try {
			result = getCadastroBLAnexosDAO().executarQtdeRegistros(bl, tipoAnexo);
		} catch (Exception e) {
			getCadastroBLAnexosDAO().rollback();
			throw new Exception(e.getMessage());
		}
		getCadastroBLAnexosDAO().closeTransaction();
		
		return result;
	}

}