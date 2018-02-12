package conexao.com.bean;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import conexao.com.util.ConexaoManagerFactoryGeneric;

public class AbstractLazyDataModel<T> extends LazyDataModel<T> {

	private static final long serialVersionUID = 1L;
	private static final EntityManagerFactory emf = ConexaoManagerFactoryGeneric.getEntityManager();
	private EntityManager em;

	private List<T> list;
	private String sqlParametro;
	private String sqlParametroCount;
	private String sqlWhere;
	private String orderBy;

	public AbstractLazyDataModel(String sqlParametro, String sqlParametroCount, String sqlWhere, String orderBy) {
		super();
		em = emf.createEntityManager();
		this.sqlParametro = sqlParametro;
		this.sqlParametroCount = sqlParametroCount;
		this.sqlWhere = sqlWhere;
		this.orderBy = orderBy;
	}

	/**
	 * Envia os dados para efetuar a carga dos registros do lazy data model
	 */
	// public List<T> load(int startingAt, int maxPerPage, String sortField,
	// SortOrder sortOrder, Map<String, String> filters) {
	// try {
	// try {
	// em.getTransaction().begin();
	//
	// list = (List<T>) loadListLazyDataModel(startingAt, maxPerPage, sortField,
	// sortOrder, filters, sqlParametro, sqlWhere);
	//
	// } finally {
	// em.getTransaction().commit();
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// if (getRowCount() <= 0) {
	// setRowCount(countRegistrosTotal(sqlParametroCount));
	// }
	//
	// setPageSize(maxPerPage);
	//
	// return list;
	// }

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

		if (getRowCount() <= 0) {
			if (sqlWhere != null && sqlParametroCount != null) {
				if (sqlParametroCount.indexOf("where") == -1) {
					sqlParametroCount = sqlParametroCount+" "+sqlWhere;
				}
			}
			setRowCount(countRegistrosTotal(sqlParametroCount));
		}

		setPageSize(pageSize);

		return list;
	}

	/**
	 * Executa a carga da lista do lazy data model em cima dos parametros
	 * recebidos
	 * 
	 * @param startingAt
	 * @param maxPerPage
	 * @param sortField
	 * @param sortOrder
	 * @param filters
	 * @param sqlParametro
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> loadListLazyDataModel(int startingAt, int maxPerPage, String sortField, SortOrder sortOrder,
			Map<String, Object> filters, String sqlParametro, String sqlWhere, String orderBy) {
		Query query = null;
//		String operacaoSimbolo = null;
//		String operacaoSimboloPorcento = null;
//		String upper = null;
//		String upper_final = null;

		String sql = sqlParametro;

		if (orderBy != null) {
			sql = sql + " " + orderBy;
		}

		// regular query that will search for players in the db
		query = em.createQuery(sql);

		int qtdeReg = 0;
		for (Map.Entry<String, Object> entry : filters.entrySet()) {
			qtdeReg = qtdeReg + 1;

			System.out.println(entry.getValue());

			// boolean numeric = verificaSeNumeroOuTexto(entry.getValue());
			// if (numeric) {
			// operacaoSimbolo = " = ";
			// operacaoSimboloPorcento = " ";
			// upper = "";
			// upper_final = "";
			// } else {
			// operacaoSimbolo = " like '%";
			// operacaoSimboloPorcento = "%' ";
			// upper = "upper(";
			// upper_final = ")";
			// }
			//
			// if (qtdeReg == 1) {
			// sql = sql + " where " + upper + entry.getKey() + upper_final +
			// operacaoSimbolo + entry.getValue().toUpperCase() +
			// operacaoSimboloPorcento;
			//
			// } else {
			// sql = sql + " or " + upper + entry.getKey() + upper_final
			// +operacaoSimbolo + entry.getValue().toUpperCase() +
			// operacaoSimboloPorcento;
			// }
		}

		if (sqlWhere != null) {
			sql = sqlParametro + sqlWhere;
			if (orderBy != null) {
				sql = sql + " " + orderBy;
			}
			query = em.createQuery(sql);
		}

		if (sortField != null && !sortField.isEmpty()) {
			if (sortOrder.equals(SortOrder.ASCENDING)) {
				query = em.createQuery(sql + " order by " + sortField);
			} else if (sortOrder.equals(SortOrder.DESCENDING)) {
				query = em.createQuery(sql + " order by " + sortField + " desc");
			}
		}

		query.setFirstResult(startingAt);
		query.setMaxResults(maxPerPage);
		return query.getResultList();
	}

	/**
	 * Executa a conta dos registros em cima do parametro recebidos
	 * 
	 * @param sqlParametro
	 * @return
	 */
	public int countRegistrosTotal(String sqlParametro) {
		Query query = em.createQuery(sqlParametro);

		Number result = (Number) query.getSingleResult();

		return result.intValue();
	}

	/**
	 * Verifica se é inteiro ( numerico ) ou STRING ( TEXTO )
	 * 
	 * @param s
	 * @return
	 */
	public static boolean verificaSeNumeroOuTexto(String s) {
		try {
			Long.parseLong(s);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

}
