package servico;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

public abstract class GenericDAO<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final EntityManagerFactory emf = ConexaoManagerFactoryGeneric.getEntityManager();

	private EntityManager em;

	private Class<T> entityClass;

	public void beginTransaction() {
	    em = emf.createEntityManager();

		em.getTransaction().begin();
	}

	public void commit() {
		em.getTransaction().commit();
	}

	public void rollback() {
		em.getTransaction().rollback();
	}

	public void closeTransaction() {
		em.close();
	}

	public void commitAndCloseTransaction() {
		commit();
		closeTransaction();
	}

	public void flush() {
		em.flush();
	}

	public void joinTransaction() {
		em = emf.createEntityManager();
		em.joinTransaction();
	}

	public GenericDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public void save(T entity) {
		em.persist(entity);
	}

	public void delete(Object id, Class<T> classe) {
		T entityToBeRemoved = em.getReference(classe, id);

		em.remove(entityToBeRemoved);
	}

	public T alterar(T entity) {
		return em.merge(entity);
	}

	public void remover(T entity) {
		em.remove(entity);
	}

	public T localizarPorID(int entityID) {
		return em.find(entityClass, entityID);
	}

	public T localizarPorString(String entityString) {
		return em.find(entityClass, entityString);
	}

	public T localizaPorReferencias(int entityID) {
		return em.getReference(entityClass, entityID);
	}

	public T localizaPorReferenciasLong(Long entityID) {
		return em.getReference(entityClass, entityID);
	}

	// Using the unchecked because JPA does not have a
	// em.getCriteriaBuilder().createQuery()<T> method
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> listaTodosRegistros() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return em.createQuery(cq).getResultList();
	}

	@SuppressWarnings("unchecked")
	protected T findOneResulte(String sql, Map<String, Object> parametersCampoValor) {
		T result = null;

		try {
			Query query = em.createQuery(sql);

			if (parametersCampoValor != null && !parametersCampoValor.isEmpty()) {
				populateQueryParameters(query, parametersCampoValor);
			}

			result = (T) query.getSingleResult();

		} catch (NoResultException e) {
			System.out.println("No result found for named query: " + sql);
		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public T retornaResultadoObjeto(String sql, Map<String, Object> parametersCampoValor) {
		T result = null;

		try {
			Query query = em.createQuery(sql);

			if (parametersCampoValor != null && !parametersCampoValor.isEmpty()) {
				populateQueryParameters(query, parametersCampoValor);
			}

			result = (T) query.getSingleResult();

		} catch (NoResultException e) {
			System.out.println("No result found for named query: " + sql);
		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public List<T> retornaResultadoListaObjeto(String sql, Map<String, Object> parametersCampoValor,
			boolean utilizarVerificacaoStrinInteiro) {
		List<T> result = null;

		try {
			Query query = em.createQuery(sql);

			if (parametersCampoValor != null && !parametersCampoValor.isEmpty()) {
				if (utilizarVerificacaoStrinInteiro) {
					populateQueryParametersInteiroString(query, parametersCampoValor);
				} else {
					populateQueryParameters(query, parametersCampoValor);
				}
			}

			result = (List<T>) query.getResultList();
		} catch (NoResultException e) {
			System.out.println("No result found for named query: " + sql);
		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	private void populateQueryParameters(Query query, Map<String, Object> parameters) {
		for (Entry<String, Object> entry : parameters.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}

	public void updateAtualizaDadosObjeto(String sql, Map<String, Object> parametersCampoValor,
			boolean utilizarVerificacaoStrinInteiro) {
		try {
			Query query = em.createQuery(sql);

			if (parametersCampoValor != null && !parametersCampoValor.isEmpty()) {
				if (utilizarVerificacaoStrinInteiro) {
					populateQueryParametersInteiroString(query, parametersCampoValor);
				} else {
					populateQueryParameters(query, parametersCampoValor);
				}
			}

			query.executeUpdate();
		} catch (NoResultException e) {
			System.out.println("No result found for named query: " + sql);
		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public int executaSqlCountRegistros(String sql, Map<String, Object> parametersCampoValor,
			boolean utilizarVerificacaoStrinInteiro) {
		try {
			Query query = em.createQuery(sql);

			if (parametersCampoValor != null && !parametersCampoValor.isEmpty()) {
				if (utilizarVerificacaoStrinInteiro) {
					populateQueryParametersInteiroString(query, parametersCampoValor);
				} else {
					populateQueryParameters(query, parametersCampoValor);
				}
			}

			Number result = (Number) query.getSingleResult();

			return result.intValue();
		} catch (NoResultException e) {
			System.out.println("No result found for named query: " + sql);
		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}
		return 0;
	}

	private void populateQueryParametersInteiroString(Query query, Map<String, Object> parameters) {
		for (Entry<String, Object> entry : parameters.entrySet()) {

			if (entry.getValue() != null) {
				if (verificaSeNumeroOuTexto(entry.getValue().toString())) {
					Integer valorInteiro = Integer.valueOf(entry.getValue().toString());
					query.setParameter(entry.getKey(), valorInteiro);
				} else {
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
		}
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

	/**
	 * Retorna a qtde de registros via SQL
	 * 
	 * @param sqlParametro
	 * @return
	 */
	public int retornaQtdeRegistros(String sqlParametro) {
		Query query = em.createQuery(sqlParametro);

		Number result = (Number) query.getSingleResult();

		return result.intValue();
	}

	public int retornaQtdeRegistrosParametros(String sqlParametro, String parametroCampoTabela, String valor) {
		Query query = em.createQuery(sqlParametro);

		if (valor != null) {
			if (verificaSeNumeroOuTexto(valor)) {
				Integer valorInteiro = Integer.valueOf(valor);
				query.setParameter(parametroCampoTabela, valorInteiro);
			} else {
				query.setParameter(parametroCampoTabela, valor);
			}
		}

		Number result = (Number) query.getSingleResult();

		return result.intValue();
	}

	/**
	 * Retorna um BigDecimal de registros via SQL
	 * 
	 * @param sqlParametro
	 * @return result ( Big Decimal )
	 */
	public BigDecimal retornaBigDecimalRegistros(String sqlParametro) {
		Query query = em.createQuery(sqlParametro);

		BigDecimal result = (BigDecimal) query.getSingleResult();

		return result;
	}

	/**
	 * Retorna a lista dos itens via SQL
	 * 
	 * @param sql
	 * @param parametro
	 * @param valor
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> retornaListaSQL(String sql, String parametro, String valor) {
		Query query = em.createQuery(sql);

		if (valor != null) {

			if (verificaSeNumeroOuTexto(valor)) {
				Integer valorInteiro = Integer.valueOf(valor);
				query.setParameter(parametro, valorInteiro);
			} else {
				query.setParameter(parametro, valor);
			}

		}

		@SuppressWarnings("rawtypes")
		List result = query.getResultList();

		return result;
	}

	@SuppressWarnings("unchecked")
	/**
	 * Efetua pesquisa valores Strings ou integer generico
	 * 
	 * @param sql
	 * @param parametroCampoTabela
	 * @param valor
	 * @return T ->> Objeto
	 */
	public T pesquisaObjetoCampos(String sql, String parametroCampoTabela, String valor) {
		Query query = em.createQuery(sql);

		if (valor != null) {
			if (verificaSeNumeroOuTexto(valor)) {
				Integer valorInteiro = Integer.valueOf(valor);
				query.setParameter(parametroCampoTabela, valorInteiro);
			} else {
				query.setParameter(parametroCampoTabela, valor);
			}
		}

		Object singleResult = query.getSingleResult();

		return (T) singleResult;
	}

	public void deletaRegistrosObjeto(String sql, String parametroCampoTabela, String valor) {
		try {
			Query query = em.createQuery(sql);

			if (valor != null) {
				if (verificaSeNumeroOuTexto(valor)) {
					Integer valorInteiro = Integer.valueOf(valor);
					query.setParameter(parametroCampoTabela, valorInteiro);
				} else {
					query.setParameter(parametroCampoTabela, valor);
				}
			}

			query.executeUpdate();
		} catch (NoResultException e) {
			System.out.println("No result found for named query: " + sql);
		} catch (Exception e) {
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
