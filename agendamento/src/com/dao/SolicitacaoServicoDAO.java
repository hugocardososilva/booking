package com.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import javax.persistence.TypedQuery;

import com.entidade.Servico;
import com.entidade.SolicitacaoServico;
import com.enums.StatusSolicitacao;

import conexao.com.dao.GenericDAO;

public class SolicitacaoServicoDAO extends GenericDAO<SolicitacaoServico> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SolicitacaoServicoDAO() {
		super(SolicitacaoServico.class);
		// TODO Auto-generated constructor stub
	}
	
public List<SolicitacaoServico> getSolicitacoesPorDataeServico(Date data, Servico servico  ){
	TypedQuery<SolicitacaoServico> q = em.createQuery(SolicitacaoServico.sql+" where s.dataSolicitacao = :data and s.servico.id = :idServico", SolicitacaoServico.class);	
	q.setParameter("idServico", servico.getId());
	q.setParameter("data", data);
	try {
		List<SolicitacaoServico> lista = (ArrayList<SolicitacaoServico>)q.getResultList();
		return lista;
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
	}
public List<SolicitacaoServico> getSolicitacaoServicoNaoFinalizadas(){
	TypedQuery<SolicitacaoServico> q = em.createQuery(SolicitacaoServico.sql+" where s.statusSolicitacao != :status", SolicitacaoServico.class);
	q.setParameter("status", StatusSolicitacao.FINALIZADO);
	try {
		List<SolicitacaoServico> lista = (ArrayList<SolicitacaoServico>)q.getResultList();
		return lista;
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
}
	
public String getStatusServerSara(String ati, String os) {
	
	Query q = em.createNamedQuery("proc_clif_pesq_status_os");
	q.setParameter("w_ati", ati);
	q.setParameter("os_id", os);
	
	List<String> lista = q.setMaxResults(1).getResultList();
	return lista.get(0);
	
}
public Map<String, Date> getDataInicioeFimSara(String ati, String os){
	Query q = em.createNamedQuery("proc_clif_pesq_status_os");
	q.setParameter("w_ati", ati);
	q.setParameter("os_id", os);
	
	List<Map<String, Date>> lista = q.setMaxResults(1).getResultList();
	return lista.get(0);
}
//proc_clif_pesq_dt_ini_fim_os
//@w_ati
//@os_id

//dbo.proc_clif_pesq_status_os 
//@w_ati varchar(150),
//@os_id varchar (10)
}
