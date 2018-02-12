package servico;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConexaoManagerFactoryGeneric {

	private static JSFUtil jsfUtil;

	public static EntityManagerFactory getEntityManager() {
		EntityManagerFactory entityManagerFactory = null;
		try {
			Properties prop = new Properties(); 
			prop.setProperty("javax.persistence.jdbc.user", getJsfUtil().getPropertyIniConfiguracao("prop.connection.user"));
			prop.setProperty("javax.persistence.jdbc.password", getJsfUtil().getPropertyIniConfiguracao("prop.connection.password"));
			prop.setProperty("javax.persistence.jdbc.driver", getJsfUtil().getPropertyIniConfiguracao("prop.connection.driver"));
			prop.setProperty("javax.persistence.jdbc.url", getJsfUtil().getPropertyIniConfiguracao("prop.connection.url"));
			prop.setProperty("hibernate.dialect", getJsfUtil().getPropertyIniConfiguracao("prop.connection.dialect"));
			
			entityManagerFactory = Persistence.createEntityManagerFactory(getJsfUtil().getPropertyIniConfiguracao("prop.connection.bancodados"), prop); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    return entityManagerFactory;
	}
	
	private static JSFUtil getJsfUtil() {
		if (jsfUtil == null) {
			jsfUtil = new JSFUtil();
		}

		return jsfUtil;
	}	

}

