package conexao.com.rn;

import java.util.List;

import conexao.com.dao.CadastroBLContainerLCLDAO;
import seguranca.com.entidade.CadastroBLContanierLCL;

public class CadastroBLContanierLCLRN {

	private CadastroBLContainerLCLDAO DAO;
	private CadastroBLContanierRN containerRN;
	private CadastroBLRN blRN;

	public CadastroBLContainerLCLDAO getDAO() {
		if (DAO == null) {
			DAO = new CadastroBLContainerLCLDAO();
		}
		return DAO;
	}
	
	private CadastroBLContanierRN getContainerRN() {
		if (containerRN == null) {
			containerRN = new CadastroBLContanierRN();
		}
		return containerRN;
	}
	
	private CadastroBLRN getBlRN() {
		if (blRN == null) {
			blRN = new CadastroBLRN();
		}
		return blRN;
	}

	public CadastroBLContanierLCL incluir(CadastroBLContanierLCL item) throws Exception {
		getDAO().beginTransaction();
		if (item.getNumeroContanier() != null) {
			item.setNumeroContanier(item.getNumeroContanier().toUpperCase());
		}

		if (item.getNumeroLacre() != null) {
			item.setNumeroLacre(item.getNumeroLacre().toUpperCase());
		}
		try {
			getDAO().save(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		getDAO().commitAndCloseTransaction();

		return localizar(item.getId());
	}

	public List<CadastroBLContanierLCL> listaTodosAutoComplete(String pesquisa) {
		getDAO().beginTransaction();
		List<CadastroBLContanierLCL> result = getDAO().pesquisaAutoComplete(pesquisa);
		getDAO().closeTransaction();
		return result;
	}
	
	public CadastroBLContanierLCL alterar(CadastroBLContanierLCL item) throws Exception {
		getDAO().beginTransaction();

		try {
			item = getDAO().alterar(item);
			getContainerRN().updateAtualizaDadosLCL(item);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		getDAO().commitAndCloseTransaction();
		
		return item;
	}

	public CadastroBLContanierLCL localizar(int itemId) {
		getDAO().beginTransaction();
		CadastroBLContanierLCL item = getDAO().localizarPorID(itemId);
		getDAO().closeTransaction();
		return item;
	}

	public void deletar(CadastroBLContanierLCL item) {
		getDAO().beginTransaction();
		getDAO().deletaRegistrosObjeto(CadastroBLContanierLCL.FILTRO_DELETE_REGISTRO, CadastroBLContanierLCL.CAMPO_ID,
				item.getId().toString());
		getDAO().commitAndCloseTransaction();
	}

	public List<CadastroBLContanierLCL> listaTodos() {
		getDAO().beginTransaction();
		List<CadastroBLContanierLCL> result = getDAO().listaTodosRegistros();
		getDAO().closeTransaction();
		return result;
	}
	
	public void validarQuantidadesVolumes(CadastroBLContanierLCL entidade) throws Exception {
		
		float qtde_bls = getBlRN().retornaQtdeRegistros(entidade, 1);
		float soma_volumes = getBlRN().retornaQtdeRegistros(entidade, 2);
		float soma_mercadoria = getBlRN().retornaQtdeRegistros(entidade, 3);
		
		if (entidade.getQuantidadeBL() != qtde_bls || entidade.getQuantidadeVolume() != soma_mercadoria || entidade.getQuantidadeMercadoria() != soma_volumes) {
			throw new Exception("Quantidades de BLs ou volumes, não estão batendo com os valores informados! ");
		}
	}

	public List<CadastroBLContanierLCL> listaBlMasterLCL() {
		getDAO().beginTransaction();
		List<CadastroBLContanierLCL> result = getDAO().listaBlMasterLCLDAO();
		getDAO().closeTransaction();
		return result;
	}
	

}