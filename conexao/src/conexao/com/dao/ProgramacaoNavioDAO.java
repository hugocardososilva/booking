package conexao.com.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seguranca.com.entidade.ProgramacaoNavio;

public class ProgramacaoNavioDAO extends GenericDAO<ProgramacaoNavio> {

	private static final long serialVersionUID = 1L;

	public ProgramacaoNavioDAO() {
		super(ProgramacaoNavio.class);
	}

	public void delete(ProgramacaoNavio entidade) {
		super.delete(entidade.getId(), ProgramacaoNavio.class);
	}
	
	public List<ProgramacaoNavio> pesquisaAutoComplete(String pesquisa) {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -7);
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(ProgramacaoNavio.CAMPO_NAVIO, "%"+pesquisa+"%");     
		parameters.put(ProgramacaoNavio.CAMPO_DATAETA, calendar.getTime());     
		
		return super.retornaResultadoListaObjeto(ProgramacaoNavio.sql_pesquisa_autocomplete, parameters, false);
	}

	public ProgramacaoNavio pesquisaNavio(String navio, String viagem) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put(ProgramacaoNavio.CAMPO_NAVIO, navio.trim());     
		parameters.put(ProgramacaoNavio.CAMPO_NAVIOVIAGEM, viagem.trim());     
		
		return super.retornaResultadoObjeto(ProgramacaoNavio.sql_pesquisa_navio, parameters);
	}
}
