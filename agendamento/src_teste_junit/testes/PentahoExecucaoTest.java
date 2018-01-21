package testes;

import org.junit.Test;
import org.pentaho.di.core.exception.KettleException;

import pentahoatual.com.tuil.ServicoPentahoPDI;

public class PentahoExecucaoTest {

	@Test
	public void testeExecutar() throws KettleException, InterruptedException {
		while (true) {
			int minutos_proce = 1;
			int segundos_proce = 60;
			int TIMEOUT_proce = minutos_proce * segundos_proce * 1000;
			Thread.sleep(TIMEOUT_proce);

			ServicoPentahoPDI pdi = new ServicoPentahoPDI();
			pdi.pentahoPdiExecutarJOB("C:/services/arquivo/pentaho/job_correcao_agendamento.kjb", null, null, null,
					null, null);

			System.out.println("teste...");
		}

	}
}
