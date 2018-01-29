package com.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import com.entidade.Servico;
import com.rn.ServicoRN;
import com.util.JSFMessageUtil;

import conexao.com.bean.AbstractLazyDataModel;
import conexao.com.bean.AbstractMB;
import conexao.com.util.FiltroTabelaBean;
import conexao.com.util.IGenericBean;
import seguranca.com.entidade.User;

@ViewScoped
@ManagedBean
public class ServicoBean extends AbstractMB implements IGenericBean {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	private LazyDataModel<Servico> lazyModel;
	
	@ManagedProperty(value= UserMB.INJECTION_NAME)
	private UserMB userMB;
	
	private boolean controlarFormListar;
	private boolean controlarFormCadastrar;
	private boolean controlarFormEditar;
	
	private Servico entidadeServico;
	private ServicoRN servicoRN;
	
	@PostConstruct
	public void init() {
		controlarFormListar = true;
		controlarFormCadastrar= false;
		controlarFormEditar = false;
		setWhereSQL(null);
		
	}
	public LazyDataModel<Servico> getlazyModel() {
		if(lazyModel == null) lazyModel = new AbstractLazyDataModel<Servico>(Servico.sql, Servico.sqlCount , getWhereSQL(), null);
		return lazyModel;
	}
	
	public void editarEntidade(Servico servico) {
		// TO-do rotina para editar entidade
	}
	public void novaEntidade() {
		//TO-do rotina para uma nova entidade
		try {
			entidadeServico= new Servico();
			setControlarFormCadastrar(true);
			setControlarFormEditar(false);
			setControlarFormListar(false);
			JSFMessageUtil.adicionarMensagemSucesso("Nova entidade");
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
		
	}
	public void incluirEntidade() {
		try {
			getServicoRN().incluir(entidadeServico);
			
			
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro("Não foi possível adicionar o serviço");
		}finally {
			JSFMessageUtil.adicionarMensagemSucesso("Serviço Cadastrado com sucesso.");
			setControlarFormCadastrar(false);
			setControlarFormEditar(false);
			setControlarFormListar(true);
		}
	}
	
	public UserMB getUserMB() {
		return userMB;
	}

	public void setUserMB(UserMB userMB) {
		this.userMB = userMB;
	}
	
	

	public Servico getEntidadeServico() {
		return entidadeServico;
	}
	public void setEntidadeServico(Servico entidadeServico) {
		this.entidadeServico = entidadeServico;
	}
	public boolean isControlarFormListar() {
		return controlarFormListar;
	}
	public void setControlarFormListar(boolean controlarFormListar) {
		this.controlarFormListar = controlarFormListar;
	}
	public boolean isControlarFormCadastrar() {
		return controlarFormCadastrar;
	}
	public void setControlarFormCadastrar(boolean controlarFormCadastrar) {
		this.controlarFormCadastrar = controlarFormCadastrar;
	}
	public boolean isControlarFormEditar() {
		return controlarFormEditar;
	}
	public void setControlarFormEditar(boolean controlarFormEditar) {
		this.controlarFormEditar = controlarFormEditar;
	}
	public void ativarUnJanelaCapacidade() {
		if(entidadeServico.isJanelaCapacidade()) {
			entidadeServico.setJanelaCapacidade(true);
		}else {
			entidadeServico.setJanelaCapacidade(false);
		}
	}
	
	@Override
	public void filtroTabela() {
		try {
			setWhereSQL(null);
			lazyModel = null;

			if (!getFiltroGeral().isEmpty()) {

				getFiltrosParametros().add("id");
				getFiltrosParametros().add("nome");
				getFiltrosParametros().add("descricao");
				getFiltrosParametros().add("codigoNCM");
				getFiltrosParametros().add("validarDemurrage");
				getFiltrosParametros().add("janelaAtendimento");

				setWhereSQL(FiltroTabelaBean.montaCondicaoSqlSemWhereOR(getFiltroGeral(), getFiltrosParametros()));
			}
		} catch (Exception e) {
			setFiltrosParametros(null);
		}
	}


	@Override
	public String getFiltroGeral() {
		// TODO Auto-generated method stub
		return filtroGeral;
	}


	@Override
	public void setFiltroGeral(String filtroGeral) {
		this.filtroGeral= filtroGeral;
		
	}
	public ServicoRN getServicoRN() {
		if(servicoRN == null) servicoRN = new ServicoRN();
		return servicoRN;
	}
	
	
	
	
}
