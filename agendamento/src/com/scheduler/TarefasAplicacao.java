package com.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dao.SolicitacaoServicoDAO;
import com.rn.SolicitacaoRN;

public class TarefasAplicacao implements Job{

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("Iniciando verificação dos serviços");
		
		SolicitacaoRN rn = new SolicitacaoRN();
		
		rn.atualizarStatusSolicitacaoServicoSara();
		
		System.out.println("Finalizando verificação dos serviços");
		
	}

}
