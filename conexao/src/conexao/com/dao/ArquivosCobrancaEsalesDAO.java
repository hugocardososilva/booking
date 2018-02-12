package conexao.com.dao;

import java.util.HashMap;
import java.util.Map;

import seguranca.com.entidade.ArquivosCobrancaEsalesSeguranca;

public class ArquivosCobrancaEsalesDAO extends GenericDAO<ArquivosCobrancaEsalesSeguranca> {
 
	private static final long serialVersionUID = 1L;

	public ArquivosCobrancaEsalesDAO() {
        super(ArquivosCobrancaEsalesSeguranca.class);
    }
 
	public void delete(ArquivosCobrancaEsalesSeguranca item) {
		super.delete(item.getIdent(), ArquivosCobrancaEsalesSeguranca.class);
	}

	public ArquivosCobrancaEsalesSeguranca validarLoginTESTE(String password, String descricao){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("password", password);     
		parameters.put("descricao", descricao);     
		
		return super.retornaResultadoObjeto(ArquivosCobrancaEsalesSeguranca.FILTRO_USUARIO_SENHA, parameters);
	}
	
}