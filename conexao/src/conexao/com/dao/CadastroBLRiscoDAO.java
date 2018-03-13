package conexao.com.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import seguranca.com.entidade.CadastroBL;
import seguranca.com.entidade.CadastroBLRiscoFitossanitario;

public class CadastroBLRiscoDAO extends GenericDAO<CadastroBLRiscoFitossanitario> {

	private static final long serialVersionUID = 1L;

	public CadastroBLRiscoDAO() {
		super(CadastroBLRiscoFitossanitario.class);
	}

	public void delete(CadastroBLRiscoFitossanitario entidade) {
		super.delete(entidade.getId(), CadastroBLRiscoFitossanitario.class);
	}
	
	public CadastroBLRiscoFitossanitario getUltimoRiscoPorNcm(CadastroBL bl) {
		TypedQuery<CadastroBLRiscoFitossanitario> q = em.createQuery("Select r from CadastroBLRiscoFitossanitario r where  r.cadastroBL.importador.id = :idImportador and r.cadastroBL.ncm.id = :idNcm and r.cadastroBL.paisOrigem.id = :idPaisOrigem order by r.id desc", CadastroBLRiscoFitossanitario.class);
		q.setParameter("idImportador", bl.getImportador().getId());
		q.setParameter("idNcm",bl.getNcm().getId());
		q.setParameter("idPaisOrigem", bl.getPaisOrigem().getId() );
		try {
			List<CadastroBLRiscoFitossanitario> lista = (ArrayList<CadastroBLRiscoFitossanitario>) q.setMaxResults(1).getResultList();
			CadastroBLRiscoFitossanitario risco = lista.get(0);
			return risco;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

}
