package com.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.dao.ServicoDAO;
import com.entidade.Servico;
import com.rn.ServicoRN;

@ApplicationScoped
@ManagedBean
public class AplicationBean implements Serializable {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	@PostConstruct
	public void init() {
		initOutrosServicos();
	}
	
	public void initOutrosServicos() {
		try {
			ServicoRN rn = new ServicoRN();
			
			if(!rn.verificarOutroServico()) {
				Servico s = new Servico();
				s.setNome("Outros");
				s.setDescricao("Outros Serviços");
				s.setUnMedidaJanela("O".charAt(0));
				rn.incluir(s);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}

}
