package conexao.com.dao;

import java.util.Map;

import seguranca.com.entidade.CadastroBL;

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
	
}
