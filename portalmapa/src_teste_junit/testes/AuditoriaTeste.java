package testes;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditQuery;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dao.AudityEntityRN;
import com.entidade.AuditEntity;
import com.rn.AuditoriaLogRN;

import conexao.com.rn.CadastroBLRN;
import conexao.com.rn.UserFacade;
import conexao.com.util.ConexaoManagerFactoryGeneric;
import seguranca.com.entidade.CadastroBL;
import seguranca.com.entidade.CadastroBLContanier;
import seguranca.com.entidade.LogInspecaoLiberadoMapa;
import seguranca.com.entidade.User;

public class AuditoriaTeste {
	private static final EntityManagerFactory emf = ConexaoManagerFactoryGeneric.getEntityManager();
	private static AudityEntityRN rnAuditoria;
	private static AuditoriaLogRN rnAuditoriaLog;
	private static CadastroBLRN rnBL;

	private EntityManager entityManager() {
		return emf.createEntityManager();
	}

	@BeforeClass
	public static void iniciar() {
		rnBL = new CadastroBLRN();
		rnAuditoria = new AudityEntityRN();
		rnAuditoriaLog = new AuditoriaLogRN();
	}

	@Test
	public void testeAuditoriaRetornaTodaAlteracaoTabela() {
		AuditReader reader = AuditReaderFactory.get(entityManager());

		AuditQuery query = reader.createQuery().forRevisionsOfEntity(CadastroBLContanier.class, false, true);
		List<?> result = query.getResultList();

		System.out.println(result);
	}

	@Test
	public void testeAuditoriaRetornaTodosRegistrosReferenteAoRegistro() throws Throwable {
		UserFacade userFacade = new UserFacade();
		User user = userFacade.validarLoginTeste("029.250.999-55", "@Acdc1909");

		AuditReader reader = AuditReaderFactory.get(entityManager());
		User user_rev1 = reader.find(User.class, user.getId(), 3);

		System.out.println(user_rev1);

		List<Number> revisionNumbers = reader.getRevisions(User.class, user.getId());
		for (Number rev : revisionNumbers) {
			User auditedBook = reader.find(User.class, user.getId(), rev);
			
			AuditEntity localizar = rnAuditoria.localizar(rev.intValue());

			System.out.println(auditedBook + "  " + localizar);
		}
	}

	@Test
	public void testeAuditoriaContainer() throws Exception {
		CadastroBL localizar = rnBL.localizar(71);
		
		List<LogInspecaoLiberadoMapa> list = rnAuditoriaLog.getLogRegistrosContainer(localizar);
		
		Assert.assertNotNull(list);
	}
}
