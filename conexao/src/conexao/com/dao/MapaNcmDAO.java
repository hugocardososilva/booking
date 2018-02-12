package conexao.com.dao;

import seguranca.com.entidade.MapaNcm;

public class MapaNcmDAO extends GenericDAO<MapaNcm> {

	private static final long serialVersionUID = 1L;

	public MapaNcmDAO() {
		super(MapaNcm.class);
	}

	public void delete(MapaNcm entidade) {
		super.delete(entidade.getId(), MapaNcm.class);
	}
}
