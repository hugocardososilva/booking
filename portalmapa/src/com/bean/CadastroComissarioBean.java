package com.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

import com.util.JSFMessageUtil;

import conexao.com.bean.AbstractLazyDataModel;
import conexao.com.bean.AbstractMB;
import conexao.com.rn.CadastroComissarioRN;
import conexao.com.util.FiltroTabelaBean;
import conexao.com.util.IGenericBean;
import seguranca.com.entidade.CadastroComissario;
import seguranca.com.enums.TipoPessoaFisicaJuridicaEnum;

@ViewScoped
@ManagedBean
public class CadastroComissarioBean extends AbstractMB implements IGenericBean {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	private boolean controlarFormListar = true;
	private boolean controlarFormCadastrar = false;
	private String mascaraCnpjCpf;

	private CadastroComissario entidade;
	private CadastroComissarioRN cadComissarioRN;

	private List<TipoPessoaFisicaJuridicaEnum> todasTipoPessoaFisicaJuridicaEnum;

	private LazyDataModel<CadastroComissario> lazyModel;

	public CadastroComissarioRN getCadastroComissarioRN() {
		if (cadComissarioRN == null) {
			cadComissarioRN = new CadastroComissarioRN();
		}
		return cadComissarioRN;
	}

	public List<TipoPessoaFisicaJuridicaEnum> getTodasTipoPessoaFisicaJuridicaEnum() {
		if (todasTipoPessoaFisicaJuridicaEnum == null && isControlarFormCadastrar()) {
			todasTipoPessoaFisicaJuridicaEnum = new ArrayList<TipoPessoaFisicaJuridicaEnum>();
			todasTipoPessoaFisicaJuridicaEnum = new ArrayList<TipoPessoaFisicaJuridicaEnum>(Arrays.asList(TipoPessoaFisicaJuridicaEnum.values()));
		}
		
		return todasTipoPessoaFisicaJuridicaEnum;
	}
	
	public void redirecionarCadastro() {
		try {
			setControlarFormCadastrar(true);
			setControlarFormListar(false);
			setEntidade(new CadastroComissario());
			atualizarFormCadastroUsuario();
			
			alterarMasCara();
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	private void atualizarFormCadastroUsuario() {
		RequestContext.getCurrentInstance().update(PG_FORM_CADASTRAR_ENTIDADE);
	}

	public void editarEntidade(CadastroComissario entidade) {
		try {
			setControlarFormCadastrar(true);
			setControlarFormListar(false);
			setEntidade(entidade);
			atualizarFormCadastroUsuario();
			
			alterarMasCara();
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void salvarEntidade() {
		try {
			setControlarFormCadastrar(false);
			setControlarFormListar(true);
			getCadastroComissarioRN().alterar(getEntidade());
			atualizarListaUsuario();
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}
	
	public void voltarPgPrincipal() {
		setControlarFormCadastrar(false);
		setControlarFormListar(true);
		atualizarListaUsuario();
	}

	private void atualizarListaUsuario() {
		RequestContext.getCurrentInstance().update(PG_FORM_PRINCIPAL);
	}

	public void remover(CadastroComissario entidade) {
		try {
			getCadastroComissarioRN().deletar(entidade);
			atualizarListaUsuario();
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public CadastroComissario getEntidade() {
		return entidade;
	}

	public void setEntidade(CadastroComissario entidade) {
		this.entidade = entidade;
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

	public LazyDataModel<CadastroComissario> getLazyModel() {
		if (lazyModel == null) {
			lazyModel = new AbstractLazyDataModel<CadastroComissario>(CadastroComissario.sql,
					CadastroComissario.sqlCount, getWhereSQL(), null);
		}

		return lazyModel;
	}

	@Override
	public void filtroTabela() {
		try {
			lazyModel = null;
			setWhereSQL(null);

			if (!getFiltroGeral().isEmpty()) {

				getFiltrosParametros().add(CadastroComissario.COMISSARIO_NOME);
				getFiltrosParametros().add(CadastroComissario.COMISSARIO_CPF);
				getFiltrosParametros().add(CadastroComissario.COMISSARIO_ID);

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
	
	public void alterarMasCara() {
		if (getEntidade().getTipoPessoaEnum() == null || getEntidade().getTipoPessoaEnum()==TipoPessoaFisicaJuridicaEnum.PESSOA_FISICA) {
			setMascaraCnpjCpf("999.999.999-99");
		} else {
			setMascaraCnpjCpf("99.999.999/9999-99");
		}
		getEntidade().setCpf(getEntidade().getCpf());
	}

	public String getMascaraCnpjCpf() {
		return mascaraCnpjCpf;
	}

	public void setMascaraCnpjCpf(String mascaraCnpjCpf) {
		this.mascaraCnpjCpf = mascaraCnpjCpf;
	}
}