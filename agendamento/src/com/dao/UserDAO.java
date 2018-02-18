package com.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import conexao.com.dao.GenericDAO;
import seguranca.com.entidade.User;
import seguranca.com.enums.Role;



/**
 * Classe utilizada para complementar nova versão do sistema
 */
public class UserDAO extends GenericDAO<User> {

	public UserDAO() {
		super(User.class);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public List<User> pesquisaAutoCompletePorRole(String pesquisa, Role role){
		TypedQuery<User> q = em.createQuery("Select p from User p where name like :pesquisa or cpf like :pesquisa and role = :role",User.class);
		q.setParameter("pesquisa", "%"+pesquisa.toUpperCase()+"%");
		q.setParameter("role", role);
		try {
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

}
