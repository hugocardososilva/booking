package com.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.joda.time.DateTime;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import com.entidade.JanelaAtendimento;
import com.entidade.Servico;
import com.entidade.ServicoJanelaAtendimento;
import com.rn.JanelaAtendimentoRN;
import com.util.JSFMessageUtil;

import conexao.com.bean.AbstractLazyDataModel;
import conexao.com.bean.AbstractMB;
import conexao.com.util.FiltroTabelaBean;
import conexao.com.util.IGenericBean;

@ViewScoped
@ManagedBean
public class JanelaAtendimentoBean extends AbstractMB implements IGenericBean,Serializable {
	
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	private LazyDataModel<JanelaAtendimento> lazyModel;
	
	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;
	
	private boolean controlarFormListar;
	private boolean controlarFormCadastrar;
	private boolean controlarFormEditar;
	private Date dataFiltroInicio;
	private Date dataFiltroFim;
	private Date dataNovoCadastro;
	
	private JanelaAtendimento entidadeJanelaAtendimento;
	
	private List<JanelaAtendimento> janelas;
	
	private ServicoJanelaAtendimento servicoJanelaAtendimento;
	
	private List<ServicoJanelaAtendimento> servicoJanelaAtendimentos;
	
	private JanelaAtendimentoRN janelaAtendimentoRN;
	
	private Servico servico;
	
	
	@PostConstruct
	public void init() {
		iniciarDataFiltro();
		controlarFormListar = true;
		controlarFormCadastrar = false;
		controlarFormEditar = false;
		setWhereSQL(FiltroTabelaBean.montarCondicaoSqlWhereBetween(dataFiltroInicio, dataFiltroFim, JanelaAtendimento.DATA_CAMPO));
	
	}
	//metodo que inicia a view mostrando os proximos 30 dias7
	public void iniciarDataFiltro() {
		
		dataFiltroInicio = new Date();
		DateTime dt= new DateTime(dataFiltroInicio);
		//dataFiltroFim = new Date();
		dataFiltroFim =dt.plusDays(30).toDate();
	}
	public void filtrarDatas() {
		this.janelas = getJanelaAtendimentoRN().getJanelasAtendimentoPorData(dataFiltroInicio, dataFiltroFim);
				
	}
	public void resetList() {
		setJanelas(null);
		getJanelas();
	}
	
	public LazyDataModel<JanelaAtendimento> getLazyModel(){
		if(lazyModel== null) lazyModel = new AbstractLazyDataModel<JanelaAtendimento>(JanelaAtendimento.sql, JanelaAtendimento.sqlCount,
				null, " order by data ASC");
		return lazyModel;
	}
	public void novaEntidade() {
		//metodo que cria uma nova entidade quando o usuário escolher a data de adicao
		System.out.println("nova entidade com data : " + dataNovoCadastro);
		entidadeJanelaAtendimento = new JanelaAtendimento();
		entidadeJanelaAtendimento.setData(dataNovoCadastro);
		
		incluirEntidade();
		
		
	}
	//método para edição dos serviços na janela de atendimento
	public void editarEntidade() {
		setServicoJanelaAtendimentos(entidadeJanelaAtendimento.getServicoJanelaAtendimentos());
		controlarFormListar = false;
		controlarFormCadastrar = true;
		controlarFormEditar = false;
	}
	public void incluirServicoJanelaAtendimento() {
		try {
			janelaAtendimentoRN.incluirServicoJanelaAtendimento(entidadeJanelaAtendimento, servico,servicoJanelaAtendimento);
			JSFMessageUtil.adicionarMensagemSucesso("Serviço adicionado com sucesso!");
			servico = new Servico();
			servicoJanelaAtendimento = new ServicoJanelaAtendimento();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro("Ouve um problema na operação!");
		}
	}
	
	public void incluirEntidade() {
		//to-do logica para incluir a entidade
		try {
			entidadeJanelaAtendimento = getJanelaAtendimentoRN().incluir(entidadeJanelaAtendimento);
			entidadeJanelaAtendimento = new JanelaAtendimento();
			controlarFormListar = false;
			controlarFormCadastrar = true;
			controlarFormEditar = false;
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	public void clonarEntidade() {
		try {
			entidadeJanelaAtendimento = getJanelaAtendimentoRN().clonarJanelaAtendimento(entidadeJanelaAtendimento, dataNovoCadastro);
			
			JSFMessageUtil.adicionarMensagemSucesso("Janela de Atendimento clonada com sucesso!");
			controlarFormListar = true;
			controlarFormCadastrar = false;
			controlarFormEditar = false;
			resetList();
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	public void excluirEntidade() {
		try {
			
			getJanelaAtendimentoRN().excluir(entidadeJanelaAtendimento);
			JSFMessageUtil.adicionarMensagemSucesso("Operação de exclusão foi realizada com sucesso!");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	public void filtrarPorData() {
		setWhereSQL(FiltroTabelaBean.montarCondicaoSqlWhereBetween(dataFiltroInicio, dataFiltroFim, JanelaAtendimento.DATA_CAMPO));
	}
	public void duplicarServicoJanelaAtendimento() {
		try {
			
			getJanelaAtendimentoRN().duplicarServicoJanelaAtendimento(servicoJanelaAtendimento);
			JSFMessageUtil.adicionarMensagemSucesso("Serviço duplicado com sucesso.");
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro("Ocorreu um erro ao duplicar.");
		}
	}
	public void excluirServicoJanelaAtendimento() {
		try {
			janelaAtendimentoRN.excluirServicoJanelaAtendimento(servicoJanelaAtendimento);
			JSFMessageUtil.adicionarMensagemSucesso("Serviço excluído com sucesso.");
			resetList();
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	public void voltarParaListagem() {
		controlarFormListar = true;
		controlarFormCadastrar = false;
		controlarFormEditar = false;
		resetList();
		
	}
	public List<Servico> pesquisaServico(String pesquisa) {
		List<Servico> servicos = new ArrayList<Servico>();
		try {
			
			servicos= janelaAtendimentoRN.getServicos(pesquisa);
			
		
			
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
		return servicos;
	}
	public void changeSelectServicos(SelectEvent event) {
		servico = (Servico)event.getObject();
		
	}
	
	@Override
	public void filtroTabela() {
		
		try {
			setWhereSQL(null);
			lazyModel= null;
			if(!getFiltroGeral().isEmpty()) {
				getFiltrosParametros().add("id");
				getFiltrosParametros().add("inicioDia");
				getFiltrosParametros().add("terminoDia");
				getFiltrosParametros().add("data");
				
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
		// TODO Auto-generated method stub
		
	}
	public JanelaAtendimentoRN getJanelaAtendimentoRN() {
		if(janelaAtendimentoRN == null) janelaAtendimentoRN = new JanelaAtendimentoRN();
		return janelaAtendimentoRN;
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

	public JanelaAtendimento getEntidadeJanelaAtendimento() {
		return entidadeJanelaAtendimento;
	}

	public void setEntidadeJanelaAtendimento(JanelaAtendimento entidadeJanelaAtendimento) {
		this.entidadeJanelaAtendimento = entidadeJanelaAtendimento;
	}

	public ServicoJanelaAtendimento getServicoJanelaAtendimento() {
		if(servicoJanelaAtendimento == null) servicoJanelaAtendimento = new ServicoJanelaAtendimento();
		return servicoJanelaAtendimento;
	}

	public void setServicoJanelaAtendimento(ServicoJanelaAtendimento servicoJanelaAtendimento) {
		this.servicoJanelaAtendimento = servicoJanelaAtendimento;
	}

	public List<ServicoJanelaAtendimento> getServicoJanelaAtendimentos() {
		if(servicoJanelaAtendimentos == null) servicoJanelaAtendimentos = new ArrayList<ServicoJanelaAtendimento>();
		return servicoJanelaAtendimentos;
	}

	public void setServicoJanelaAtendimentos(List<ServicoJanelaAtendimento> servicoJanelaAtendimentos) {
		this.servicoJanelaAtendimentos = servicoJanelaAtendimentos;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public UserMB getUserMB() {
		return userMB;
	}
	public void setUserMB(UserMB userMB) {
		this.userMB = userMB;
	}
	public Date getDataFiltroInicio() {
		return dataFiltroInicio;
	}
	public void setDataFiltroInicio(Date dataFiltroInicio) {
		this.dataFiltroInicio = dataFiltroInicio;
	}
	public Date getDataFiltroFim() {
		return dataFiltroFim;
	}
	public void setDataFiltroFim(Date dataFiltroFim) {
		this.dataFiltroFim = dataFiltroFim;
	}
	public Date getDataNovoCadastro() {
		return dataNovoCadastro;
	}
	public void setDataNovoCadastro(Date dataNovoCadastro) {
		this.dataNovoCadastro = dataNovoCadastro;
	}
	public List<JanelaAtendimento> getJanelas() {
		if(janelas == null) janelas = getJanelaAtendimentoRN().getJanelasAtendimentoPorData(dataFiltroInicio, dataFiltroFim);
		return janelas;
	}
	public void setJanelas(List<JanelaAtendimento> janelas) {
		this.janelas = janelas;
	}
	
	
	
	
	
	

}
