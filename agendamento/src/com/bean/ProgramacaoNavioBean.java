package com.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

import com.util.JSFMessageUtil;

import conexao.com.bean.AbstractLazyDataModel;
import conexao.com.bean.AbstractMB;
import conexao.com.rn.ProgramacaoNavioRN;
import conexao.com.util.FiltroTabelaBean;
import conexao.com.util.IGenericBean;
import seguranca.com.entidade.CadastroImportador;
import seguranca.com.entidade.ProgramacaoNavio;

@ViewScoped
@ManagedBean
public class ProgramacaoNavioBean extends AbstractMB implements IGenericBean {

	private boolean controlarFormListar = true;
	private boolean controlarFormCadastrar = false;
	
	private ProgramacaoNavio entidade;
	
	private LazyDataModel<ProgramacaoNavio> lazyModel;
	private ProgramacaoNavioRN programacaoNavioRN;

	public LazyDataModel<ProgramacaoNavio> getLazyModel() {
		if (lazyModel == null) {
			lazyModel = new AbstractLazyDataModel<ProgramacaoNavio>(ProgramacaoNavio.sql,
					ProgramacaoNavio.sqlCount, getWhereSQL(), null);
		}

		return lazyModel;
	}

	@Override
	public void filtroTabela() {
		try {
			lazyModel = null;
			setWhereSQL(null);

			if (!getFiltroGeral().isEmpty()) {

				getFiltrosParametros().add(ProgramacaoNavio.CAMPO_NAVIO);
				getFiltrosParametros().add(ProgramacaoNavio.CAMPO_NAVIOVIAGEM);

				setWhereSQL(FiltroTabelaBean.montaCondicaoSqlWhereOR(getFiltroGeral(), getFiltrosParametros()));
			}
		} catch (Exception e) {
			setFiltrosParametros(null);
		}
	}

	@Override
	public String getFiltroGeral() {
		return filtroGeral;
	}

	@Override
	public void setFiltroGeral(String filtroGeral) {
		this.filtroGeral = filtroGeral;
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
	
	public void redirecionarCadastro() {
		try {
			setControlarFormCadastrar(true);
			setControlarFormListar(false);
			setEntidade(new ProgramacaoNavio());
			atualizarFormCadastroUsuario();
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void editarEntidade(ProgramacaoNavio entidade) {
		try {
			setControlarFormCadastrar(true);
			setControlarFormListar(false);
			setEntidade(entidade);
			atualizarFormCadastroUsuario();
			
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	private void atualizarFormCadastroUsuario() {
		RequestContext.getCurrentInstance().update(PG_FORM_CADASTRAR_ENTIDADE);
	}

	public ProgramacaoNavio getEntidade() {
		return entidade;
	}

	public void setEntidade(ProgramacaoNavio entidade) {
		this.entidade = entidade;
	}
	
	private ProgramacaoNavioRN getProgramacaoNavioRN() {
		if (programacaoNavioRN == null) {
			programacaoNavioRN = new ProgramacaoNavioRN();
		}
		
		return programacaoNavioRN;
	}

	public void salvarEntidade() {
		try {
			setControlarFormCadastrar(false);
			setControlarFormListar(true);
			getProgramacaoNavioRN().alterar(getEntidade());
			atualizarFormCadastroUsuario();
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void voltarPgPrincipal() {
		setControlarFormCadastrar(false);
		setControlarFormListar(true);
		atualizarFormCadastroUsuario();
	}
	
	
}
