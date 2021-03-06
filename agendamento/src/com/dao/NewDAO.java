package com.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.exception.ConstraintViolationException;

import conexao.com.util.ConexaoManagerFactoryGeneric;
import net.sf.jasperreports.engine.type.SortOrderEnum;

public abstract class NewDAO<T> implements Serializable {

	@PersistenceContext
	private static EntityManagerFactory emf;
	@PersistenceContext
	protected EntityManager em;
	
	private Class<T> entityClass;
	
	static {
		if(emf == null) {
			emf = ConexaoManagerFactoryGeneric.getEntityManager();
		}
	}
	public void beginTransaction() {
		if(em == null) {
			em = emf.createEntityManager();
		}
		em.getTransaction().begin();
	}
	public void commit() {
		em.getTransaction().commit();
	}
	
	public AuditReader getAuditoriaReader() {
		return AuditReaderFactory.get(emf.createEntityManager());
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

	public NewDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public void save(T entity) throws Exception {
		try {
			em.persist(entity);
		} catch (Exception e) {
			rollback();
			if (e.getCause() instanceof ConstraintViolationException) {
				throw new Exception("Registro já existente ! ");
			} else {
				throw new Exception(e.getMessage());
			}
		}
	}

	public void delete(Object id, Class<T> classe) {
		try {
			T entityToBeRemoved = em.getReference(classe, id);
			
			em.remove(entityToBeRemoved);
		} catch (Exception e) {
			rollback();
			e.printStackTrace();
		}
	}

	public T alterar(T entity) throws Exception {
		try {
			return em.merge(entity);
		} catch (Exception e) {
			rollback();
			if (e.getCause() instanceof ConstraintViolationException) {
				throw new Exception("Registro já existente ! ");
			} else {
				throw new Exception(e.getMessage());
			}
		}
	}

	public void remover(T entity) {
		try {
			em.remove(entity);
		} catch (Exception e) {
			rollback();
			e.printStackTrace();
		}
	}

	public T localizarPorID(int entityID) {
		return em.find(entityClass, entityID);
	}

	public T localizarPorString(String entityString) {
		return em.find(entityClass, entityString);
	}

	public T localizaPorReferencias(int entityID) {
		try {
			return em.getReference(entityClass, entityID);
		} catch (Exception e) {
			rollback();
			e.printStackTrace();
		}
		return null;
	}

	public T localizaPorReferenciasLong(Long entityID) {
		try {
			return em.getReference(entityClass, entityID);
		} catch (Exception e) {
			rollback();
			e.printStackTrace();
		}
		return null;
	}

	// Using the unchecked because JPA does not have a
	// em.getCriteriaBuilder().createQuery()<T> method
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> listaTodosRegistros() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		
		return em.createQuery(cq).getResultList();
	}

	public List<T> listaTodosRegistrosOrdenacao(Map<String, SortOrderEnum> paramOrdenacao) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(entityClass);
		
		Root<T> from = query.from(entityClass);
		
		CriteriaQuery<T> select = query.select(from);

		if (paramOrdenacao != null && !paramOrdenacao.isEmpty()) {
			List<Order> listOrder = new ArrayList<>();
			for (Entry<String, SortOrderEnum> entry : paramOrdenacao.entrySet()) {
				if (entry.getValue()==SortOrderEnum.ASCENDING) {
					listOrder.add(builder.asc(from.get(entry.getKey())));
				} else {
					listOrder.add(builder.desc(from.get(entry.getKey())));
				}
			}
			query.orderBy(listOrder);
		}

		return em.createQuery(select).getResultList();
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
			rollback();
			System.out.println("No result found for named query: " + sql);
		} catch (Exception e) {
			rollback();
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
			rollback();
			System.out.println("No result found for named query: " + sql);
		} catch (Exception e) {
			rollback();
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
			rollback();
			System.out.println("No result found for named query: " + sql);
		} catch (Exception e) {
			rollback();
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
			rollback();
			System.out.println("No result found for named query: " + sql);
		} catch (Exception e) {
			rollback();
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
			rollback();
			System.out.println("No result found for named query: " + sql);
		} catch (Exception e) {
			rollback();
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
	 * Verifica se Ã© inteiro ( numerico ) ou STRING ( TEXTO )
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

	public int retornaQtdeRegistrosParametros(String sqlParametro, String parametroCampoTabela, Date valor) {
		Query query = em.createQuery(sqlParametro);

		if (valor != null) {
			
				
				query.setParameter(parametroCampoTabela, valor);
			
		}

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
			rollback();
			System.out.println("No result found for named query: " + sql);
		} catch (Exception e) {
			rollback();
			System.out.println("Error while running query: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
