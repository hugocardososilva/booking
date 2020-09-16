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
	
	private StringBuilder consultaFiltroNumeroATI;
	private StringBuilder consultaFiltroCodigoBL;
	private StringBuilder consultaFriltroCodigoContainer;
	private StringBuilder consultaFiltroCodigoSolicitacao;
	
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
					String sessao = (String)filtros.get("sessao");
				for(Map.Entry<String, Object> entry : filtros.entrySet()) {
					if(sessao.equals("admin")) {
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
						}else if(sessao.equals("cliente")) {
							if(entry.getKey().equals("filtroNumeroATI")) {
								String ati = (String) entry.getValue();
								inserirConsultaFiltroNumeroATI(ati);
							}
							if(entry.getKey().equals("filtroCodigoBL")) {
								String codigoBL = (String) entry.getValue();
								inserirConsultaFiltroCodigoBL(codigoBL);
							}
							if(entry.getKey().equals("filtroCodigoContainer")) {
								String container = (String) entry.getValue();
								inserirConsultaFiltroCodigoContainer(container);
							}
							if(entry.getKey().equals("filtroCodigoSolicitacao")) {
								String s = (String) entry.getValue();
								inserirConsultaFiltroCodigoSolicitacao(s);
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
				}
				

				if(sessao.equals("admin")) {	
		
						if(consultaStatusServico == null) {
							//consulta.append(" where ");
							//consultaCount.append(" where ");
						}else{	
							consulta.append(consultaStatusServico.toString());				
							consultaCount.append(consultaStatusServico.toString());
						}
						if(consultaDataCadastro != null ) {
							if(consultaStatusServico!= null) {
								consulta.append(" and ");
								consultaCount.append(" and ");
							}else if(consultaStatusServico == null)  {
								consulta.append(" where ");
								consultaCount.append(" where ");
							}
							
							consulta.append(consultaDataCadastro.toString());					
							consultaCount.append(consultaDataCadastro.toString());
						}
						if(consultaDataSolicitacao != null) {
							if(consultaStatusServico != null || consultaDataCadastro != null) {
								consulta.append(" and ");
								consultaCount.append(" and ");
							}else  if(consultaStatusServico == null && consultaDataCadastro == null) {
								consulta.append(" where ");
								consultaCount.append(" where ");
							}
							
							consulta.append(consultaDataSolicitacao.toString());					
							consultaCount.append(consultaDataSolicitacao.toString());				
							
						}
						
						
						
						if(consultaCliente != null) {
							if(consultaStatusServico != null || consultaDataCadastro != null || consultaDataSolicitacao != null) {
								consulta.append(" and ");
								consultaCount.append(" and ");
							}else if(consultaStatusServico == null && consultaDataCadastro == null && consultaDataSolicitacao == null){
								consulta.append(" where ");
								consultaCount.append(" where ");
							}
						
							consulta.append(consultaCliente.toString());
							
							consultaCount.append(consultaCliente.toString());
						}
					
					if(consultaStatusSolicitacao != null) {
						if(consultaStatusServico != null || consultaDataCadastro != null || consultaDataSolicitacao != null || consultaCliente != null ) {
							consulta.append(" and ");
							consultaCount.append(" and ");
							
						}else if(consultaStatusServico == null && consultaDataCadastro == null && consultaDataSolicitacao == null && consultaCliente == null ){
							consulta.append(" where ");
							consultaCount.append(" where ");
						}
						
						consulta.append(consultaStatusSolicitacao.toString());
						
						consultaCount.append(consultaStatusSolicitacao.toString());
					}
				}else 
					if(sessao.equals("cliente")){
						
						if(consultaFriltroCodigoContainer!= null) {
							consulta.append(consultaFriltroCodigoContainer.toString());
							consultaCount.append(consultaFriltroCodigoContainer.toString());
						}
						if(consultaFiltroNumeroATI != null) {
							if(consultaFriltroCodigoContainer!= null) {
								consulta.append(" and ");
								consultaCount.append(" and ");
							}else if(consultaFriltroCodigoContainer== null) {
								consulta.append(" where ");
								consultaCount.append(" where ");
							}
							 consulta.append(consultaFiltroNumeroATI.toString());
							 consultaCount.append(consultaFiltroNumeroATI.toString());
						}
						if(consultaFiltroCodigoBL != null) {
							if(consultaFriltroCodigoContainer!= null || consultaFiltroNumeroATI != null) {
								consulta.append(" and ");
								consultaCount.append(" and ");
							}else if(consultaFriltroCodigoContainer== null && consultaFiltroNumeroATI == null){
								consulta.append(" where ");
								consultaCount.append(" where ");
							}
							 consulta.append(consultaFiltroCodigoBL.toString());
							 consultaCount.append(consultaFiltroCodigoBL.toString());
						}
						if(consultaFiltroCodigoSolicitacao != null) {
							if(consultaFriltroCodigoContainer!= null || consultaFiltroNumeroATI != null || consultaFiltroCodigoBL != null) {
								consulta.append(" and ");
								consultaCount.append(" and ");
							}else if(consultaFriltroCodigoContainer== null && consultaFiltroNumeroATI == null && consultaFiltroCodigoBL == null) {
								consulta.append(" where ");
								consultaCount.append(" where ");
							}
								consulta.append(consultaFiltroCodigoSolicitacao.toString());
							 consultaCount.append(consultaFiltroCodigoSolicitacao.toString());
						}
						if(consultaStatusSolicitacao != null) {
							if(consultaFriltroCodigoContainer!= null || consultaFiltroNumeroATI != null || consultaFiltroCodigoBL != null || consultaFiltroCodigoSolicitacao != null) {
								consulta.append(" and ");
								consultaCount.append(" and ");
								
							}else if(consultaFriltroCodigoContainer== null && consultaFiltroNumeroATI == null && consultaFiltroCodigoBL == null && consultaFiltroCodigoSolicitacao == null){
								consulta.append(" where ");
								consultaCount.append(" where ");
							}
							
							consulta.append(consultaStatusSolicitacao.toString());
							
							consultaCount.append(consultaStatusSolicitacao.toString());
						}
						if(consultaCliente != null) {
							if(consultaFriltroCodigoContainer!= null || consultaFiltroNumeroATI != null || consultaFiltroCodigoBL != null || consultaFiltroCodigoSolicitacao != null || consultaStatusSolicitacao != null) {
								consulta.append(" and ");
								consultaCount.append(" and ");
							}else if(consultaFriltroCodigoContainer == null && consultaFiltroNumeroATI == null && consultaFiltroCodigoBL == null && consultaFiltroCodigoSolicitacao == null && consultaStatusSolicitacao == null){
								consulta.append(" where ");
								consultaCount.append(" where ");
							}
						
							consulta.append(consultaCliente.toString());
							
							consultaCount.append(consultaCliente.toString());
						}
					
				}
					
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
	public void inserirConsultaFiltroNumeroATI(String ati) {
		consultaFiltroNumeroATI = new StringBuilder();
		consultaFiltroNumeroATI.append(" (");
		consultaFiltroNumeroATI.append("s.numeroATI");
		consultaFiltroNumeroATI.append(" ");
		consultaFiltroNumeroATI.append(" like ");
		consultaFiltroNumeroATI.append("'%");
		consultaFiltroNumeroATI.append(ati);
		consultaFiltroNumeroATI.append("%'");
		consultaFiltroNumeroATI.append(") ");
	}
	public void inserirConsultaFiltroCodigoBL(String bl) {
		consultaFiltroCodigoBL = new StringBuilder();
		consultaFiltroCodigoBL.append(" (");
		consultaFiltroCodigoBL.append("s.codigoBL");
		consultaFiltroCodigoBL.append(" ");
		consultaFiltroCodigoBL.append(" like ");
		consultaFiltroCodigoBL.append("'%");
		consultaFiltroCodigoBL.append(bl);
		consultaFiltroCodigoBL.append("%'");
		consultaFiltroCodigoBL.append(") ");
	}
	public void inserirConsultaFiltroCodigoContainer(String container) {
		consultaFriltroCodigoContainer = new StringBuilder();
		consultaFriltroCodigoContainer.append(" left join s.solicitacaoServicos as ss");
		consultaFriltroCodigoContainer.append(" where ss.container.numeroContanier");
		consultaFriltroCodigoContainer.append(" ");
		consultaFriltroCodigoContainer.append(" like ");
		consultaFriltroCodigoContainer.append("'%");
		consultaFriltroCodigoContainer.append(container);
		consultaFriltroCodigoContainer.append("%'");
		consultaFriltroCodigoContainer.append(" ");
	}
	public void inserirConsultaFiltroCodigoSolicitacao(String s) {
		consultaFiltroCodigoSolicitacao = new StringBuilder();
		consultaFiltroCodigoSolicitacao.append(" (");
		consultaFiltroCodigoSolicitacao.append("s.id");
		consultaFiltroCodigoSolicitacao.append(" ");
		consultaFiltroCodigoSolicitacao.append(" = ");
		consultaFiltroCodigoSolicitacao.append("");
		consultaFiltroCodigoSolicitacao.append(s);
		consultaFiltroCodigoSolicitacao.append(" ");
		consultaFiltroCodigoSolicitacao.append(") ");
	}

}
