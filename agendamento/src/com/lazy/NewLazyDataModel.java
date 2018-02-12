package com.lazy;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import java.util.List;
import java.util.Map;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.joda.time.DateTime;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import conexao.com.util.ConexaoManagerFactoryGeneric;


public class NewLazyDataModel<T> extends LazyDataModel<T> {

	protected static final long serialVersionUID = 1L;
	protected static final EntityManagerFactory emf = ConexaoManagerFactoryGeneric.getEntityManager();
	protected EntityManager em;

	protected List<T> list;
	protected String sqlParametro;
	protected String sqlParametroCount;
	protected String sqlWhere;
	protected String orderBy;
	protected StringBuilder consulta;
	protected StringBuilder consultaCount;
	protected Map<String, String> camposTabela;


	public NewLazyDataModel(Map<String,String> camposTabela, String sqlParametro, String sqlParametroCount, String sqlWhere, String orderBy) {
		super();

		this.camposTabela = camposTabela;
		em = emf.createEntityManager();
		this.sqlParametro = sqlParametro;
		this.sqlParametroCount = sqlParametroCount;
		this.sqlWhere = sqlWhere;
		this.orderBy = orderBy;
		this.consulta = new StringBuilder();
		this.consultaCount = new StringBuilder();
	}
	




	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		try {
			try {
				em.getTransaction().begin();

				list = (List<T>) loadListLazyDataModel(first, pageSize, sortField, sortOrder, filters, sqlParametro,
						sqlWhere, orderBy);

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

	/**
	 * Executa a carga da lista do lazy data model em cima dos parametros
	 * recebidos
		 */
	@SuppressWarnings("unchecked")
	public List<T> loadListLazyDataModel(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder,
			Map<String, Object> filters, String sqlParametro, String sqlWhere, String orderBy) {
		try {
			Query query = null;			
			consulta = new StringBuilder();
			consultaCount = new StringBuilder();
			consulta.append(sqlParametro);
			consultaCount.append(sqlParametroCount);

			//se não existir consultas off table
			if (sqlWhere != null ) {
				consulta.append(sqlWhere);
				consultaCount.append(sqlWhere);
			}
			int qtdeReg = 0;

			for (Map.Entry<String, Object> entry : filters.entrySet()) {
				qtdeReg = qtdeReg + 1;
				if (qtdeReg == 1 ) {

					if(sqlWhere != null) {
						consulta.append(" and ");
						consultaCount.append(" and ");
					}
					else {
						consulta.append(" where ");
						consultaCount.append(" where ");


					}
				}else {
					consulta.append(" and ");
					consultaCount.append(" and ");
				}
				System.out.println(entry.getValue());
				for(Map.Entry<String, String> camposEntidade: camposTabela.entrySet()) {
					if(entry.getKey().equals(camposEntidade.getKey())) {
						if(camposEntidade.getValue().equals("String")) {

							inserirConsultaFiltroString(entry);


						}else
							if(camposEntidade.getValue().equals("int")) {

								inserirConsultaFiltroInt(entry);


							}else
								if(camposEntidade.getValue().equals("Date")) {

									inserirConsultaFiltroDate(entry);	

								}	
					}				
				}



			}

			if(sortField != null && !sortField.isEmpty()) {
				consulta.append(" order by ");
				consulta.append(sortField);

				//consultaCount.append(" order by ");
				//consultaCount.append(sortField);
				if(sortOrder.equals(SortOrder.ASCENDING)) {
					//consulta.append(" asc ");
					//consultaCount.append(" asc ");
				}else 
					if(sortOrder.equals(SortOrder.DESCENDING)) {
						consulta.append(" desc ");
						//consultaCount.append(" desc ");
					}
			}
			query = em.createQuery(consulta.toString());



			query.setFirstResult(startingAt);
			query.setMaxResults(maxPerPage);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Executa a conta dos registros em cima do parametro recebidos
	 * 
	 * @param sqlParametro
	 * @return
	 */
	public int countRegistrosTotal() {
		Query query = em.createQuery(consultaCount.toString());

		Number result = (Number) query.getSingleResult();

		return result.intValue();
	}

	/**
	 * Método utilizado para preencher informações na query da consulta
	 * 
	 * @param Map<String,Object>
	 * @return 
	 */

	public void inserirConsultaFiltroString(Map.Entry<String, Object> entry) {
		consulta.append("upper(");
		consulta.append(entry.getKey());
		consulta.append(")");
		consulta.append(" like '%");
		consulta.append(entry.getValue().toString().toUpperCase());
		consulta.append("%' ");

		consultaCount.append("upper(");
		consultaCount.append(entry.getKey());
		consultaCount.append(")");
		consultaCount.append(" like '%");
		consultaCount.append(entry.getValue().toString().toUpperCase());
		consultaCount.append("%' ");
	}
	/**
	 * Método utilizado para preencher informações na query da consulta
	 * 
	 * @param Map<String,Object>
	 * @return 
	 */
	public void inserirConsultaFiltroInt(Map.Entry<String, Object> entry) {
		consulta.append(" ");
		consulta.append(entry.getKey());
		consulta.append(" ");
		consulta.append(" = ");
		consulta.append(entry.getValue().toString().toUpperCase());
		consulta.append(" ");

		consultaCount.append(" ");
		consultaCount.append(entry.getKey());
		consultaCount.append(" ");
		consultaCount.append(" = ");
		consultaCount.append(entry.getValue().toString().toUpperCase());
		consultaCount.append(" ");
	}
	/**
	 * Método utilizado para preencher informações na query da consulta
	 * 
	 * @param Map<String,Object>
	 * @return 
	 */
	public void inserirConsultaFiltroDate(Map.Entry<String, Object> entry) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:S");
		DateTime dt = new DateTime(entry.getValue());

		consulta.append(" ");
		consulta.append(entry.getKey());
		consulta.append(" ");
		consulta.append("between");
		consulta.append(" ");
		consulta.append("'");
		consulta.append(sd.format(dt.toDate()));
		consulta.append("' ");
		consulta.append(" and ");
		consulta.append("'");
		consulta.append(sd.format(dt.plusDays(1).toDate()));
		consulta.append("' ");
		consulta.append(" ");

		consultaCount.append(" ");
		consultaCount.append(entry.getKey());
		consultaCount.append(" ");
		consultaCount.append("between");
		consultaCount.append(" ");
		consultaCount.append("'");
		consultaCount.append(sd.format(dt.toDate()));
		consultaCount.append("' ");
		consultaCount.append(" and ");
		consultaCount.append("'");
		consultaCount.append(sd.format(dt.plusDays(1).toDate()));
		consultaCount.append("' ");
		consultaCount.append(" ");

	}


}