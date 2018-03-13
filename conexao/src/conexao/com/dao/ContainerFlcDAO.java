package conexao.com.dao;

import java.util.ArrayList;
import java.util.List;

import javax.management.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.reflect.Typed;

import seguranca.com.entidade.CadastroBLContanier;
import seguranca.com.entidade.CadastroComissario;
import seguranca.com.entidade.CadastroImportador;
import seguranca.com.enums.StatusAtiLclFclEnum;

public class ContainerFlcDAO extends GenericDAO<CadastroBLContanier> {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ContainerFlcDAO() {
		super(CadastroBLContanier.class);		
	}
	
	public List<CadastroBLContanier> findByCliente(int id){
		TypedQuery<CadastroBLContanier> q = em.createQuery(" Select c from CadastroBLContainer c where c.cadastroBL.userCadastroDtc.id = :id ",CadastroBLContanier.class);
		q.setParameter("id", id);
		
		try {
			List<CadastroBLContanier> list = (ArrayList<CadastroBLContanier>)q.getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public List<CadastroBLContanier> findByComissarioSemImportador(CadastroComissario comissario){
		TypedQuery<CadastroBLContanier> q = em.createQuery("Select c from CadastroBLContainer c where c.cadastroBL.statusAtiLclFlclEnum = :status and c.cadastroBL.cadComissario = :idComissario",CadastroBLContanier.class);
		q.setParameter("status", StatusAtiLclFclEnum.PRESENCA_TERMINAL);
		q.setParameter("idComissario", comissario.getId());
		
		try {
			List<CadastroBLContanier> list = (ArrayList<CadastroBLContanier>)q.getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<CadastroBLContanier> findByImportador(CadastroImportador importador){
		TypedQuery<CadastroBLContanier> q = em.createQuery("Select c from CadastroBLContainer c where c.cadastroBL.statusAtiLclFlcEnum = :status and c.cadastroBL.importador.id = :idImportador", CadastroBLContanier.class);
		q.setParameter("status", StatusAtiLclFclEnum.PRESENCA_TERMINAL);
		q.setParameter("idImportador", importador.getId());
		
		try {
			List<CadastroBLContanier> list = (ArrayList<CadastroBLContanier>)q.getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<CadastroBLContanier> findByImportadorEComissario(CadastroComissario comissario, CadastroImportador importador){
		TypedQuery<CadastroBLContanier> q = em.createQuery("Select c from CadastroBLContainer c where c.cadastroBL.statusAtiLclFlcEnum = :status and c.cadastroBL.importador.id = :idImportador", CadastroBLContanier.class);
		q.setParameter("status", StatusAtiLclFclEnum.PRESENCA_TERMINAL);
		q.setParameter("idImportador", importador.getId());
		q.setParameter("idComissario", comissario.getId());
		
		try {
			List<CadastroBLContanier> list = (ArrayList<CadastroBLContanier>)q.getResultList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
