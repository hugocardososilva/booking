package conexao.com.dao;

import seguranca.com.entidade.CadastroBLDescricaoMercadoria;

public class CadastroBLDescMercadoriaDAO extends GenericDAO<CadastroBLDescricaoMercadoria> {

	private static final long serialVersionUID = 1L;

	public CadastroBLDescMercadoriaDAO() {
		super(CadastroBLDescricaoMercadoria.class);
	}

	public void delete(CadastroBLDescricaoMercadoria entidade) {
		super.delete(entidade.getId(), CadastroBLDescricaoMercadoria.class);
	}

}
