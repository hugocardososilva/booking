package conexao.com.rn;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import conexao.com.dao.ProgramacaoNavioDAO;
import seguranca.com.entidade.ProgramacaoNavio;

public class ProgramacaoNavioRN {
	
	private ProgramacaoNavioDAO DAO;
	
	public ProgramacaoNavioDAO getDAO() {
		if (DAO == null) {
			DAO = new ProgramacaoNavioDAO();
		}
		return DAO;
	}

	public ProgramacaoNavio incluir(ProgramacaoNavio item) throws Exception {
		getDAO().beginTransaction();
		getDAO().save(item);
		getDAO().commitAndCloseTransaction();
		
		return localizar(item.getId());
	}

	public ProgramacaoNavio alterar(ProgramacaoNavio item) throws Exception {
		getDAO().beginTransaction();
		item = getDAO().alterar(item);
		getDAO().commitAndCloseTransaction();
		
		return item;
	}
	
	public ProgramacaoNavio localizar(int itemId) {
		getDAO().beginTransaction();
		ProgramacaoNavio item = getDAO().localizarPorID(itemId);
		getDAO().closeTransaction();
		return item;
	}

	public void deletar(ProgramacaoNavio item) {
		getDAO().beginTransaction();
		getDAO().delete(item);
		getDAO().commitAndCloseTransaction();
	}
	
	public List<ProgramacaoNavio> listaTodos() {
		getDAO().beginTransaction();
		List<ProgramacaoNavio> result = getDAO().listaTodosRegistros();
		getDAO().closeTransaction();
		return result;
	}
	
	public List<ProgramacaoNavio> listaTodosAutoComplete(String pesquisa) {
		getDAO().beginTransaction();
		List<ProgramacaoNavio> result = getDAO().pesquisaAutoComplete(pesquisa);
		getDAO().closeTransaction();
		return result;
	}

	public ProgramacaoNavio pesquisaNavioViagem(String navio, String viagem) {
		getDAO().beginTransaction();
		ProgramacaoNavio result = getDAO().pesquisaNavio(navio, viagem);
		getDAO().closeTransaction();
		return result;
	}
	
	public boolean prazoSegregacao24AntesEta(ProgramacaoNavio entidade) throws ParseException {
		Date dataHoje = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMdd hh:mm");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(entidade.getDataETA());
		calendar.add(Calendar.HOUR_OF_DAY, -48);

		Date dateEtaMenos48Horas = df.parse(df.format(calendar.getTime()));
		
		dataHoje = df.parse(df.format(dataHoje));
		
		return dataHoje.after(dateEtaMenos48Horas);
	}

	
}