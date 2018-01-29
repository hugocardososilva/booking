package com.rn;

import com.dao.ServicoDAO;
import com.entidade.Servico;

public class ServicoRN {
	
	private ServicoDAO DAO;

	public ServicoDAO getDAO() {
		if(DAO==null) DAO= new ServicoDAO();
		return DAO;
	}
	
	public Servico incluir(Servico serv) throws Exception {
		getDAO().beginTransaction();
		getDAO().save(serv);
		getDAO().commitAndCloseTransaction();
		return localizar(serv.getId());
	}
	
	public Servico alterar(Servico serv) throws Exception{
		getDAO().beginTransaction();
		serv= getDAO().alterar(serv);
		getDAO().commitAndCloseTransaction();
		return serv;
	}
	
	public Servico localizar(int id) {
		getDAO().beginTransaction();
		Servico servico= getDAO().localizarPorID(id);
		getDAO().closeTransaction();
		return servico;
	}
	
	

}
