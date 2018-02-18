package com.lazy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.entidade.Solicitacao;

import conexao.com.util.ConexaoManagerFactoryGeneric;

public class SolicitacaoLazyDataModel extends LazyDataModel<Solicitacao> {
	protected static final EntityManagerFactory emf = ConexaoManagerFactoryGeneric.getEntityManager();
	private EntityManager em;
	private List<Solicitacao> list;
	private Map<String, Object> filtros;
	private StringBuilder consulta;
	private StringBuilder consultaCount;
	private StringBuilder consultaDataCadastro;
	private StringBuilder consultaDataSolicitacao;
	private StringBuilder consultaStatusServico;
	private StringBuilder consultaStatusSolicitacao;
	private StringBuilder consultaCliente;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SolicitacaoLazyDataModel(Map<String, Object> filtros) {
		super();
		this.em = emf.createEntityManager();
		this.filtros = filtros;
		this.consulta = new StringBuilder();
		this.consultaCount = new StringBuilder();
		
	}
	
	@Override
	public List<Solicitacao> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		try {
			try {
				em.getTransaction().begin();

				list = (List<Solicitacao>) loadListLazyDataModel(first, pageSize, sortField, sortOrder, filtros);

			} finally {
				em.getTransaction().commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}



		setRowCount(countRegistrosTotal());


		setPageSize(pageSize);

		return list;
	}
	@SuppressWarnings("unchecked")
	public List<Solicitacao> loadListLazyDataModel(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters){
		try {
			Query query = null;
			consulta = new StringBuilder();
			consultaCount = new StringBuilder();
			consulta.append(Solicitacao.sql);
			consultaCount.append(Solicitacao.sqlCount);
			
			
			if(!filtros.isEmpty()) {
				
				for(Map.Entry<String, Object> entry : filtros.entrySet()) {
					if(entry.getKey().equals("filtrosDataCadastro")) {
						
						Map<String, Object> filtrosDataCadastro = (Map<String, Object>) entry.getValue();
						inserirConsultaFiltroDateCadastro(filtrosDataCadastro);
					}
					if(entry.getKey().equals("filtrosDataSolicitacao")) {
						Map<String, Object> filtrosDataSolicitacao = (Map<String, Object>)entry.getValue();
						inserirConsultaFiltroDateSolicitacao(filtrosDataSolicitacao);
					}
					if(entry.getKey().equals("filtroStatusServicos")) {
						List<Integer> filtroStatusServicos= (List<Integer>)entry.getValue();
						inserirConsultaFiltroStatusServicos(filtroStatusServicos);
					}
					if(entry.getKey().equals("filtroStatusSolicitacao")) {
						List<Integer> filtroStatusSolicitacao = (List<Integer>)entry.getValue();
						inserirConsultaFiltroStatusSolicitacao(filtroStatusSolicitacao);
					}
					if(entry.getKey().equals("cliente")) {
						int id = (int) entry.getValue();
						inserirConsultaFiltroCliente(id);
						
					}
				}
				if(consultaStatusServico == null) {
					consulta.append(" where ");
					consultaCount.append(" where ");
				}else{	
					consulta.append(consultaStatusServico.toString());				
					consultaCount.append(consultaStatusServico.toString());
				}
				if(consultaDataCadastro != null ) {
					consulta.append(" and ");
					consulta.append(consultaDataCadastro.toString());
					consultaCount.append(" and ");
					consultaCount.append(consultaDataCadastro.toString());
				}
				if(consultaDataSolicitacao != null) {
					consulta.append(" and ");
					consulta.append(consultaDataSolicitacao.toString());
					consultaCount.append(" and ");
					consultaCount.append(consultaDataSolicitacao.toString());				
					
				}
				
				
				
				if(consultaCliente != null) {
					consulta.append(" and ");
					consulta.append(consultaCliente.toString());
					consultaCount.append(" and ");
					consultaCount.append(consultaCliente.toString());
				}
			}
			if(consultaStatusSolicitacao != null) {
				consulta.append(" and ");
				consulta.append(consultaStatusSolicitacao.toString());
				consultaCount.append(" and ");
				consultaCount.append(consultaStatusSolicitacao.toString());
			}
			if(sortField != null && !sortField.isEmpty()) {
				consulta.append(" order by ");
				consulta.append(sortField);
			
				if(sortOrder.equals(SortOrder.ASCENDING)) {
				
				}else 
					if(sortOrder.equals(SortOrder.DESCENDING)) {
						consulta.append(" desc ");
						
					}
			}
			query = em.createQuery(consulta.toString());
			query.setFirstResult(first);
			query.setMaxResults(pageSize);
			return query.getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public int countRegistrosTotal() {
		Query query = em.createQuery(consultaCount.toString());

		Number result = (Number) query.getSingleResult();

		return result.intValue();
	}
	
	public void inserirConsultaFiltroDateCadastro(Map<String, Object> entry) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Date dataInicio = (Date)entry.get("dataCadastroInicio");
		Date dataFim = (Date)entry.get("dataCadastroFim");
		consultaDataCadastro = new StringBuilder();
		consultaDataCadastro.append(" (");
		consultaDataCadastro.append("s.dataCadastro");
		consultaDataCadastro.append(" ");
		consultaDataCadastro.append("between");
		consultaDataCadastro.append(" ");
		consultaDataCadastro.append("'");
		consultaDataCadastro.append(sd.format(dataInicio));
		consultaDataCadastro.append("' ");
		consultaDataCadastro.append(" and ");
		consultaDataCadastro.append("'");
		consultaDataCadastro.append(sd.format(dataFim));
		consultaDataCadastro.append("' ");
		consultaDataCadastro.append(") ");

		
	}
	public void inserirConsultaFiltroDateSolicitacao(Map<String, Object> entry) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Date dataInicio = (Date)entry.get("dataSolicitacaoInicio");
		Date dataFim = (Date)entry.get("dataSolicitacaoFim");
		consultaDataSolicitacao = new StringBuilder();
		consultaDataSolicitacao.append(" (");
		consultaDataSolicitacao.append("s.dataCadastro");
		consultaDataSolicitacao.append(" ");
		consultaDataSolicitacao.append("between");
		consultaDataSolicitacao.append(" ");
		consultaDataSolicitacao.append("'");
		consultaDataSolicitacao.append(sd.format(dataInicio));
		consultaDataSolicitacao.append("' ");
		consultaDataSolicitacao.append(" and ");
		consultaDataSolicitacao.append("'");
		consultaDataSolicitacao.append(sd.format(dataFim));
		consultaDataSolicitacao.append("' ");
		consultaDataSolicitacao.append(") ");

		
	}
	public void inserirConsultaFiltroStatusServicos(List<Integer> entry) {
		consultaStatusServico = new StringBuilder();
		consultaStatusServico.append(" join ");
		consultaStatusServico.append("s.solicitacaoServicos as ss ");
		consultaStatusServico.append(" where ");
		consultaStatusServico.append(" ss.statusServicos ");
		consultaStatusServico.append(" in(");
		Iterator it = entry.iterator();
		
		while(it.hasNext()) {
			String item = (String)it.next();
			consultaStatusServico.append(item);
				if(it.hasNext()) consultaStatusServico.append(",");
		}
		consultaStatusServico.append(") ");
		
	}
	public void inserirConsultaFiltroStatusSolicitacao(List<Integer> entry) {
		consultaStatusSolicitacao = new StringBuilder();
		consultaStatusSolicitacao.append(" (");
		
		consultaStatusSolicitacao.append("s.statusSolicitacao");
		consultaStatusSolicitacao.append(" ");
		consultaStatusSolicitacao.append(" in ");
		consultaStatusSolicitacao.append(" (");
		Iterator it = entry.iterator();
		
		
		while(it.hasNext()) {
			String item = (String)it.next();
			consultaStatusSolicitacao.append(item.toString());
				if(it.hasNext()) consultaStatusSolicitacao.append(",");
		}
		consultaStatusSolicitacao.append(") ");
		consultaStatusSolicitacao.append(") ");
		
		
	}
	public void inserirConsultaFiltroCliente(int id) {
		consultaCliente = new StringBuilder();
		consultaCliente.append(" (");
		consultaCliente.append("s.cliente.id");
		consultaCliente.append(" ");
		consultaCliente.append(" = ");
		consultaCliente.append(id);
		consultaCliente.append(" ");
		consultaCliente.append(") ");
	}

}
