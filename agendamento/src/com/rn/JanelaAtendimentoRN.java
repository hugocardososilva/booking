package com.rn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.Period;

import com.dao.JanelaAtendimentoDAO;
import com.dao.ServicoDAO;
import com.dao.ServicoJanelaAtendimentoDAO;
import com.entidade.JanelaAtendimento;
import com.entidade.Servico;
import com.entidade.ServicoJanelaAtendimento;


public class JanelaAtendimentoRN implements Serializable {
	private JanelaAtendimentoDAO DAO;
	private ServicoDAO servicoDAO;
	private ServicoJanelaAtendimentoDAO servicoJanelaAtendimentoDAO;
	
	public ServicoJanelaAtendimentoDAO getServicoJanelaAtendimentoDAO() {
		if(servicoJanelaAtendimentoDAO==null) servicoJanelaAtendimentoDAO = new ServicoJanelaAtendimentoDAO();
		return servicoJanelaAtendimentoDAO;
	}
	
	public JanelaAtendimentoDAO getDAO() {
		if(DAO==null) DAO =new JanelaAtendimentoDAO();
		return DAO;
	}
	
	public ServicoDAO getServicoDAO() {
		if(servicoDAO == null) servicoDAO = new ServicoDAO();
		return servicoDAO;
	}
	public List<Servico> getTodosServicos(String pesquisa) throws Exception{
		getServicoDAO().beginTransaction();
		List<Servico> lista = new ArrayList<Servico>();
		try {
			lista= getServicoDAO().pesquisaAutoCompleteTodos(pesquisa);
			
			
		} catch (Exception e) {
			getServicoDAO().rollback();
			throw new Exception(e.getMessage());
		}
		getServicoDAO().closeTransaction();
		return lista;
	}
	public List<Servico> getServicos(String pesquisa) throws Exception{
		getServicoDAO().beginTransaction();
		List<Servico> lista = new ArrayList<Servico>();
		try {
			lista= getServicoDAO().pesquisaAutoComplete(pesquisa);
			
			
		} catch (Exception e) {
			getServicoDAO().rollback();
			throw new Exception(e.getMessage());
		}
		getServicoDAO().closeTransaction();
		return lista;
	}
	public JanelaAtendimento incluir(JanelaAtendimento janelaAtendimento) throws Exception {
		//verifica se a data já possui registro
		if(verificarQntRegistrosDataJanela(janelaAtendimento.getData())!= 0) {
			throw new Exception("Já existe um registro com a mesma data");
		}else if(janelaAtendimento.getData().compareTo(new Date()) <= 0) {
			throw new Exception("Não é possivel adicionar para datas que já passaram.");
		}
		else {
		getDAO().beginTransaction();
		getDAO().save(janelaAtendimento);
		getDAO().commitAndCloseTransaction();
		return localizar(janelaAtendimento.getId());
		}
	}
	public JanelaAtendimento alterar(JanelaAtendimento janelaAtendimento) throws Exception {
		getDAO().beginTransaction();
		janelaAtendimento = getDAO().alterar(janelaAtendimento);
		getDAO().commitAndCloseTransaction();
		return janelaAtendimento;
	}
	public int verificarQntRegistrosDataJanela(Date data) throws Exception {
		getDAO().beginTransaction();
		int qnt = getDAO().retornaQtdeRegistrosParametros(JanelaAtendimento.sqlCountFiltroData, JanelaAtendimento.DATA_CAMPO, data);
		getDAO().closeTransaction();
		return qnt;
	}
	public JanelaAtendimento localizar(int id) {
		getDAO().beginTransaction();
		JanelaAtendimento ja = getDAO().localizarPorID(id);
		getDAO().closeTransaction();
		return ja;
	}

	public JanelaAtendimento incluirServicoJanelaAtendimento(JanelaAtendimento janelaAtendimento, Servico servico ,ServicoJanelaAtendimento servicoJanelaAtendimento) throws Exception {
		
		DateTime datain= new DateTime(servicoJanelaAtendimento.getInicio());
		DateTime datafm= new DateTime(servicoJanelaAtendimento.getFim());
		Period periodo = new Period(datain,datafm);
		if(servico.getUnMedidaJanela() == "H".charAt(0)) {
			int dif = Minutes.minutesBetween(datain, datafm).getMinutes();
			
			DateTime capHora = new DateTime(servicoJanelaAtendimento.getCapacidadeHora());
			DateTime horainicial = capHora;
			horainicial = horainicial.hourOfDay().setCopy(0);
			horainicial = horainicial.minuteOfDay().setCopy(0);
			horainicial = horainicial.secondOfDay().setCopy(0);
			
			int capacidade = Minutes.minutesBetween(horainicial, capHora).getMinutes();
			
//			System.out.println("diferenca entre os minutos de do inicio pro fim : " +dif);
//			System.out.println("hora inicial : " +horainicial);
			int result= dif/capacidade;
//			System.out.println("RESULTADO DA CAPACIDADE : " +result);
			servicoJanelaAtendimento.setCapacidadePeriodo(result);
		}else
			if(servico.getUnMedidaJanela() == "D".charAt(0)) {
				servicoJanelaAtendimento.setCapacidadePeriodo(servicoJanelaAtendimento.getCapacidadeDia());
			}
		
		servicoJanelaAtendimento.setServico(servico);
		servicoJanelaAtendimento.setJanelaAtendimento(janelaAtendimento);
		janelaAtendimento.addServicoJanelaAtendimento(servicoJanelaAtendimento);
		servico.addServicoJanelaAtendimento(servicoJanelaAtendimento);
		
		try {
			getServicoJanelaAtendimentoDAO().beginTransaction();
			getServicoJanelaAtendimentoDAO().save(servicoJanelaAtendimento);
			getServicoJanelaAtendimentoDAO().commitAndCloseTransaction();
			janelaAtendimento =alterar(janelaAtendimento);
			
			//rotina para atualizar os horarios da janela
			
			janelaAtendimento = localizar(janelaAtendimento.getId());
			 for (ServicoJanelaAtendimento sja : janelaAtendimento.getServicoJanelaAtendimentos()) {
				//se a hora de inicio for menor que a hora do servico
					if(janelaAtendimento.getInicioDia()== null) {
						janelaAtendimento.setInicioDia(sja.getInicio());
					}else
						if(sja.getInicio().compareTo(janelaAtendimento.getInicioDia()) < 0) {
						janelaAtendimento.setInicioDia(sja.getInicio());
					}
					//se a hora do fim do maior que a hora do servico
					if(janelaAtendimento.getTerminoDia()== null ) {
						janelaAtendimento.setTerminoDia(sja.getFim());
					} else
					if(sja.getFim().compareTo(janelaAtendimento.getTerminoDia()) > 0) {
						janelaAtendimento.setTerminoDia(sja.getFim());
					}
				
			}
			alterar(janelaAtendimento);
		} catch (Exception e) {
			e.printStackTrace();
			getServicoJanelaAtendimentoDAO().rollback();
			throw new Exception("Não foi possível salvar.");
			
		}
		
		return janelaAtendimento;
		
		
		
	}
	public JanelaAtendimento clonarJanelaAtendimento(JanelaAtendimento janelaAtendimento, Date data) throws Exception{
		
			try {
				JanelaAtendimento ja = new JanelaAtendimento();
				
				ja.setData(data);
					
				ja = incluir(ja);
				
				List<ServicoJanelaAtendimento> lista = new ArrayList<ServicoJanelaAtendimento>(); 
						lista = janelaAtendimento.getServicoJanelaAtendimentos();
				
				for(ServicoJanelaAtendimento servicoJA : lista) {
					ServicoJanelaAtendimento sja = new ServicoJanelaAtendimento();
					Servico servico = servicoJA.getServico();
					sja.setFim(servicoJA.getFim());
					sja.setInicio(servicoJA.getInicio());
					if(servicoJA.getServico().getUnMedidaJanela() == "H".charAt(0)) {
						sja.setCapacidadeHora(servicoJA.getCapacidadeHora());
					}else if(servicoJA.getServico().getUnMedidaJanela() == "D".charAt(0)) {
						sja.setCapacidadeDia(servicoJA.getCapacidadeDia());
					}
					
					 ja = incluirServicoJanelaAtendimento(ja, servico , sja);
				}
				
				return ja;
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
			
			
		
		
	}
	public List<JanelaAtendimento> getJanelasAtendimentoPorData(Date dataInicio, Date dataFim){
		getDAO().beginTransaction();
		List<JanelaAtendimento> lista = getDAO().getJanelasAtendimentoPorData(dataInicio, dataFim);
		getDAO().closeTransaction();
		return lista;
	}
	
	public void excluirServicoJanelaAtendimento(ServicoJanelaAtendimento servicoJanelaAtendimento){
		try {
			
		
		servicoJanelaAtendimento.getServico().removeServicoJanelaAtendimento(servicoJanelaAtendimento);
		servicoJanelaAtendimento.getJanelaAtendimento().removeServicoJanelaAtendimento(servicoJanelaAtendimento);
		servicoJanelaAtendimento.setServico(null);
		servicoJanelaAtendimento.setJanelaAtendimento(null);
		getServicoJanelaAtendimentoDAO().beginTransaction();
		getServicoJanelaAtendimentoDAO().delete(servicoJanelaAtendimento, ServicoJanelaAtendimento.class);
		getServicoJanelaAtendimentoDAO().commitAndCloseTransaction();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void excluir(JanelaAtendimento janelaAtendimento) {
		//remover a referencia de servico da lista de servicosJanelaAgendamento
		try {
			
			for(ServicoJanelaAtendimento item : janelaAtendimento.getServicoJanelaAtendimentos()) {
				item.setServico(null);
			}
		
			getDAO().beginTransaction();
			getDAO().delete(janelaAtendimento.getId(), JanelaAtendimento.class);
			getDAO().commitAndCloseTransaction();
		} catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	public void duplicarServicoJanelaAtendimento(ServicoJanelaAtendimento servicoJanelaAtendimento) {
		//novo servico
		try {
			ServicoJanelaAtendimento s = new ServicoJanelaAtendimento();
			s.setCapacidadeDia(servicoJanelaAtendimento.getCapacidadeDia());
			if(servicoJanelaAtendimento.getCapacidadeHora()!=null) s.setCapacidadeHora(servicoJanelaAtendimento.getCapacidadeHora());
			s.setCapacidadePeriodo(servicoJanelaAtendimento.getCapacidadePeriodo());
			s.setFim(servicoJanelaAtendimento.getFim());
			s.setInicio(servicoJanelaAtendimento.getInicio());
			s.setJanelaAtendimento(servicoJanelaAtendimento.getJanelaAtendimento());
			s.setServico(servicoJanelaAtendimento.getServico());
			s.getServico().addServicoJanelaAtendimento(s);
			s.getJanelaAtendimento().addServicoJanelaAtendimento(s);
			
			getServicoJanelaAtendimentoDAO().beginTransaction();
			getServicoJanelaAtendimentoDAO().save(s);
			getServicoJanelaAtendimentoDAO().commitAndCloseTransaction();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		
	}
	
	
	
}
