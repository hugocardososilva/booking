package servico;

import java.util.List;

public class ProgramacaoNavioRN {
	
	private ProgramacaoNavioDAO DAO;
	
	public ProgramacaoNavioDAO getDAO() {
		if (DAO == null) {
			DAO = new ProgramacaoNavioDAO();
		}
		return DAO;
	}

	public ProgramacaoNavio incluir(ProgramacaoNavio item) {
		getDAO().beginTransaction();
		getDAO().save(item);
		getDAO().commitAndCloseTransaction();
		
		return localizar(item.getId());
	}

	public ProgramacaoNavio alterar(ProgramacaoNavio item) {
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

	
}