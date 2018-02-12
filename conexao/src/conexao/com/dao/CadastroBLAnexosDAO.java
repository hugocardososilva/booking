package conexao.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seguranca.com.entidade.CadastroBL;
import seguranca.com.entidade.CadastroBLAnexos;
import seguranca.com.enums.TipoAnexosEnum;

public class CadastroBLAnexosDAO extends GenericDAO<CadastroBLAnexos> {

	private static final long serialVersionUID = 1L;

	public CadastroBLAnexosDAO() {
		super(CadastroBLAnexos.class);
	}

	public void delete(CadastroBLAnexos entidade) {
		super.delete(entidade.getId(), CadastroBLAnexos.class);
	}

	public List<CadastroBLAnexos> listaAnexosPorTipo(CadastroBL bl, TipoAnexosEnum tipoAnexo){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroBLAnexos.CAMPO_CADASTROBL, bl.getId());     
		parameters.put(CadastroBLAnexos.CAMPO_TIPOANEXOSENUM, tipoAnexo);     
		
		return super.retornaResultadoListaObjeto(CadastroBLAnexos.SQL_LISTA_POR_TIPO_DE_ANEXOS, parameters, false);
	}

	public List<CadastroBLAnexos> listaAnexosMapaPorTipo(CadastroBL bl){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroBLAnexos.CAMPO_CADASTROBL, bl.getId());     
		
		return super.retornaResultadoListaObjeto(CadastroBLAnexos.SQL_LISTA_POR_TIPO_DE_ANEXOS_MAPA, parameters, false);
	}

	public List<CadastroBLAnexos> listaAnexosPorTipoEmailDAO(CadastroBL bl){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroBLAnexos.CAMPO_CADASTROBL, bl.getId());     
		
		return super.retornaResultadoListaObjeto(CadastroBLAnexos.SQL_LISTA_TIPO_EMAIL, parameters, false);
	}

	public float executarQtdeRegistros(CadastroBL bl, TipoAnexosEnum tipoAnexo){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroBLAnexos.CAMPO_CADASTROBL, bl.getId());     
		parameters.put(CadastroBLAnexos.CAMPO_TIPOANEXOSENUM, tipoAnexo);     
		
		return super.executaSqlCountRegistros(CadastroBLAnexos.SQL_QTDE_POR_TIPO_DE_ANEXOS, parameters, false);
	}
	
}
