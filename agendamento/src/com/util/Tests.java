package com.util;

import com.entidade.Servico;
import com.rn.ServicoRN;

public class Tests {
	
	public static void main(String[] args) {
		Servico s = new Servico();
		s.setNome("Teste");
		s.setDescricao("teste de inclusao");
		s.setJanelaCapacidade(false);
		
		ServicoRN srn= new ServicoRN();
		try {
			Servico servi = srn.incluir(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
