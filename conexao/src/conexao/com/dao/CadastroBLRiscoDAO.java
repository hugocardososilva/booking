package conexao.com.dao;

import seguranca.com.entidade.CadastroBLRiscoFitossanitario;

public class CadastroBLRiscoDAO extends GenericDAO<CadastroBLRiscoFitossanitario> {

	private static final long serialVersionUID = 1L;

	public CadastroBLRiscoDAO() {
		super(CadastroBLRiscoFitossanitario.class);
	}

	public void delete(CadastroBLRiscoFitossanitario entidade) {
		super.delete(entidade.getId(), CadastroBLRiscoFitossanitario.class);
	}

}
