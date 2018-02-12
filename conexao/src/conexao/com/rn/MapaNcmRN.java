package conexao.com.rn;

import java.util.List;

import conexao.com.dao.MapaNcmDAO;
import seguranca.com.entidade.MapaNcm;

public class MapaNcmRN {
	
	private MapaNcmDAO cadNCMDAO;
	
	public MapaNcmDAO getNCMDAO() {
		if (cadNCMDAO == null) {
			cadNCMDAO = new MapaNcmDAO();
		}
		return cadNCMDAO;
	}

	public MapaNcm incluir(MapaNcm item) throws Exception {
		getNCMDAO().beginTransaction();
		getNCMDAO().save(item);
		getNCMDAO().commitAndCloseTransaction();
		
		return localizar(item.getId());
	}

	public MapaNcm alterar(MapaNcm item) throws Exception {
		getNCMDAO().beginTransaction();
		item = getNCMDAO().alterar(item);
		getNCMDAO().commitAndCloseTransaction();
		
		return item;
	}

	public MapaNcm localizar(int itemId) {
		getNCMDAO().beginTransaction();
		MapaNcm item = getNCMDAO().localizarPorID(itemId);
		getNCMDAO().closeTransaction();
		return item;
	}

	public void deletar(MapaNcm item) {
		getNCMDAO().beginTransaction();
		getNCMDAO().delete(item);
		getNCMDAO().commitAndCloseTransaction();
	}
	
	public List<MapaNcm> listaTodos() {
		getNCMDAO().beginTransaction();
		List<MapaNcm> result = getNCMDAO().listaTodosRegistros();
		getNCMDAO().closeTransaction();
		return result;
	}
}