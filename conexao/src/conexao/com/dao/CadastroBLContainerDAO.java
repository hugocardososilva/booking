package conexao.com.dao;

import java.util.Map;

import seguranca.com.entidade.CadastroBLContanier;

public class CadastroBLContainerDAO extends GenericDAO<CadastroBLContanier> {

	private static final long serialVersionUID = 1L;

	public CadastroBLContainerDAO() {
		super(CadastroBLContanier.class);
	}

	public void delete(CadastroBLContanier entidade) {
		
		super.delete(entidade.getId(), CadastroBLContanier.class);
	}
	
	public void atualizaDadosUpdate(Map<String, Object> parametersCampoValor) {
		super.updateAtualizaDadosObjeto(CadastroBLContanier.UPDATE_ATUALIZA_DADOS, parametersCampoValor, false);
	}

	public void atualizaDadosUpdateLiberadoSemVistoria(Map<String, Object> parametersCampoValor) {
		super.updateAtualizaDadosObjeto(CadastroBLContanier.UPDATE_ATUALIZA_STATUS_LIBERADO_SEM_VISTORIA, parametersCampoValor, false);
	}

	public void atualizaDadosUpdateLCL(Map<String, Object> parametersCampoValor) {
		super.updateAtualizaDadosObjeto(CadastroBLContanier.UPDATE_ATUALIZA_DADOS_LCL, parametersCampoValor, false);
	}

	public int executaQtdeRegistros(String sql, Map<String, Object> parametersCampoValor) {
		return super.executaSqlCountRegistros(sql, parametersCampoValor, false);
	}

	public int executaQtdeRegistrosInspecao(String sql, Map<String, Object> parametersCampoValor) {
		return super.executaSqlCountRegistros(sql, parametersCampoValor, false);
	}

	public int executaQtdeRegistrosLiberado(String sql, Map<String, Object> parametersCampoValor) {
		return super.executaSqlCountRegistros(sql, parametersCampoValor, false);
	}
}
