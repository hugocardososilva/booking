package conexao.com.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import seguranca.com.entidade.CadastroBL;
import seguranca.com.entidade.CadastroImportador;

public class CadastroBLDAO extends GenericDAO<CadastroBL> {

	private static final long serialVersionUID = 1L;

	public CadastroBLDAO() {
		super(CadastroBL.class);
	}

	public void delete(CadastroBL entidade) {
		super.delete(entidade.getId(), CadastroBL.class);
	}

	public void atualizaDadosUpdate(Map<String, Object> parametersCampoValor) {
		super.updateAtualizaDadosObjeto(CadastroBL.UPDATE_ATUALIZA_DADOS, parametersCampoValor, false);
	}
	
	public float executaQtdeRegistros(String sql, Map<String, Object> parametersCampoValor) {
		return super.executaSqlCountRegistros(sql, parametersCampoValor, false);
	}
	
	public CadastroBL getUltimoBlDoImportador(CadastroImportador importador) {
		TypedQuery<CadastroBL> q = em.createQuery(CadastroBL.sql+" where p.importador.id = :id order by p.id desc", CadastroBL.class);
		q.setParameter("id", importador.getId());
		List<CadastroBL> lista = q.setMaxResults(1).getResultList();
		return lista.get(0);
	}
	
}
