package conexao.com.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seguranca.com.entidade.CadastroBLContanierLCL;

public class CadastroBLContainerLCLDAO extends GenericDAO<CadastroBLContanierLCL> {

	private static final long serialVersionUID = 1L;

	public CadastroBLContainerLCLDAO() {
		super(CadastroBLContanierLCL.class);
	}

	public void delete(CadastroBLContanierLCL entidade) {
		
		super.delete(entidade.getId(), CadastroBLContanierLCL.class);
	}
	
	public List<CadastroBLContanierLCL> pesquisaAutoComplete(String pesquisa) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(CadastroBLContanierLCL.CAMPO_NUMEROCONTANIER, "%"+pesquisa+"%");     
		
		return super.retornaResultadoListaObjeto(CadastroBLContanierLCL.sql_pesquisa_autocomplete, parameters, false);
	}

	public List<CadastroBLContanierLCL> listaBlMasterLCLDAO() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		return super.retornaResultadoListaObjeto(CadastroBLContanierLCL.LISTA_BL_MASTER, parameters, false);
	}
}