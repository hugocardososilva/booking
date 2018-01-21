package com.dao;

import java.util.HashMap;
import java.util.Map;

import com.entidade.CadastroDTCDTA;

import conexao.com.dao.GenericDAO;

public class CadastroDTCDTADAO extends GenericDAO<CadastroDTCDTA> {

	private static final long serialVersionUID = 1L;

	public CadastroDTCDTADAO() {
		super(CadastroDTCDTA.class);
	}

	public void delete(CadastroDTCDTA parametros) {
		super.delete(parametros.getId(), CadastroDTCDTA.class);
	}

	public CadastroDTCDTA localizarDTCDTA(int valor) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroDTCDTA.CAMPO_CADASTROBL, valor);

		return super.retornaResultadoObjeto(CadastroDTCDTA.LOCALIZAR_CADASTRO_DTC_DTA , parameters);
	}

	/**
	 * Efetua um select para efetuar pesquisa encontrando dados existentes com ( IMPORTADOR / NCM / PAIS ORIGEM )
	 * @param entidade
	 * @return CadastroDTCDTA 
	 */
	public int localizarRegistroFitosanitarioDAO(CadastroDTCDTA entidade) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroDTCDTA.CAMPO_PAISORIGEM_FILTRO, entidade.getPaisOrigem().getId());
		parameters.put(CadastroDTCDTA.CAMPO_NCM_FILTRO, entidade.getNcm().getId());
		parameters.put(CadastroDTCDTA.CAMPO_IMPORTADOR_FILTRO, entidade.getImportador().getId());
		parameters.put(CadastroDTCDTA.CAMPO_ID, entidade.getId());
		
		return super.executaSqlCountRegistros(CadastroDTCDTA.LOCALIZAR_REGISTRO_RISCO_FITOSANITARIO , parameters, false);
	}

	/**
	 * Retorna pesquisa
	 * 
	 * @return CadastroDTCDTA
	 */
	public boolean pesquisaDescricaoBL(String pesquisa) {
		boolean bloquear = false;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroDTCDTA.CADASTRO_BL_DESCRICAOBL, pesquisa.trim().toUpperCase());

		int qtdeRegistro = super.executaSqlCountRegistros(CadastroDTCDTA.FILTRO_CADASTRO_BL_DESCRICAOBL, parameters, false);

		if (qtdeRegistro >= 1) {
			bloquear = true;
		}

		return bloquear;
	}

	public void atualizaDadosProcessosBloqueadosUpdate(Map<String, Object> parametersCampoValor) {
		super.updateAtualizaDadosObjeto(CadastroDTCDTA.UPDATE_ATUALIZA_PROCESSOS_BLOQUEADOS, parametersCampoValor, false);
	}
	
}
