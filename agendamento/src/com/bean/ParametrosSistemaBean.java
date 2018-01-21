package com.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import com.entidade.ParametrosSistema;
import com.enums.TiposParametrosEnum;
import com.rn.ParametrosSistemasRN;

import conexao.com.bean.AbstractLazyDataModel;
import conexao.com.bean.AbstractMB;
import conexao.com.util.FiltroTabelaBean;
import conexao.com.util.IGenericBean;
import seguranca.com.enums.TelasEntidadesEnum;

@ViewScoped
@ManagedBean

public class ParametrosSistemaBean extends AbstractMB implements IGenericBean {

	private static String itemPesquisa;

	private ParametrosSistema entidade;
	private ParametrosSistemasRN rnParametros;

	private int modoEdicao = 0;
	private LazyDataModel<ParametrosSistema> lazyModel;
	private List<TelasEntidadesEnum> todosTelasEntidadesEnum;
	private List<TiposParametrosEnum> todosTiposParametrosEnum;

	public ParametrosSistemasRN getRnParametros() {
		if (rnParametros == null) {
			rnParametros = new ParametrosSistemasRN();
		}

		return rnParametros;
	}

	// INICIO GETS e SETS
	/**
	 * Retorna a lista utilizando o Lazy Data Model
	 * 
	 * @return
	 */
	public LazyDataModel<ParametrosSistema> getLazyModel() {
		if (lazyModel == null) {
			lazyModel = new AbstractLazyDataModel<ParametrosSistema>(ParametrosSistema.sql, ParametrosSistema.sqlCount, null, null);			
		}

		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<ParametrosSistema> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public String getNomeTela() {
		if (nomeTela == null) {
//			nomeTela = TelasEntidadesEnum.SISTEMA_PARAMETROS.getDescricao();
		}

		return nomeTela;
	}

	public void setNomeTela(String nomeTela) {
		this.nomeTela = nomeTela;
	}

	public int getModoEdicao() {
		return modoEdicao;
	}

	public void setModoEdicao(int modoEdicao) {
		this.modoEdicao = modoEdicao;
	}

	public ParametrosSistema getEntidade() {
		if (entidade == null) {
			entidade = new ParametrosSistema();
		}

		return entidade;
	}

	public void setEntidade(ParametrosSistema parametros) {
		this.entidade = parametros;
	}

	public static String getItemPesquisa() {
		return itemPesquisa;
	}

	public static void setItemPesquisa(String itemPesquisa) {
	}

	// FINAL GETS e SETS

	/**
	 * Metodo salvar pega os daos do objeto e salva os dados
	 */
	public void salvar() {
		try {
			getRnParametros().incluir(entidade);
			closeDialog();
			displayInfoMessageToUser("Salvo com Sucesso !");
			limparInstancia();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Nao foi possivel. Tente novamente mais tarde");
			e.printStackTrace();
		}
	}

	/**
	 * Metodo alterar pega os dados do objeto e realiza a alterações dos dados
	 */
	public void alterar() {
		try {
			getRnParametros().alterar(entidade);
			closeDialog();
			displayInfoMessageToUser("Alterado com Sucesso !");
			setLazyModel(null);
			limparInstancia();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Nao foi possivel. Tente novamente mais tarde");
			e.printStackTrace();
		}
	}

	/**
	 * Metodo remove excluir o registro que foi selecionado no parametro item
	 * 
	 * @param item
	 */
	public void remove(ParametrosSistema item) {
		try {
			getRnParametros().deletar(item);
			closeDialog();
			displayInfoMessageToUser("Excluido com Sucesso !");
			limparInstancia();
		} catch (Exception e) {
			keepDialogOpen();
			displayErrorMessageToUser("Nao foi possivel. Tente novamente mais tarde");
			e.printStackTrace();
		}
	}

	public void limparInstancia() {
		setEntidade(null);
	}

	public void instanciarIncluir() {
		setModoEdicao(0);
	}

	public void instanciarEditar(ParametrosSistema item) {
		ParametrosSistema retornaDadosAtuais = getRnParametros().localizar(item.getId());

		setModoEdicao(-1);
		setEntidade(retornaDadosAtuais);
	}

	/**
	 * Controla os campos lables para quando for incluir um novo registro não
	 * apareca o campo
	 * 
	 * @return
	 */
	public Boolean controlaCampo() {
		if (getModoEdicao() == -1) {
			return true;
		}
		return false;
	}

	/**
	 * Metodo controla botão quando estiver ediando nao apareca o botao de
	 * incluir
	 * 
	 * @return
	 */
	public Boolean controlaBoataoIncluir() {
		if (getModoEdicao() == -1) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo controla botão alterar quando estiver incluindo nao apareca o
	 * botao de alterar
	 * 
	 * @return
	 */
	public Boolean controlaBoataoAlterar() {

		if (getModoEdicao() == 0) {
			return false;
		}
		return true;
	}

	public String retornaPaginaInicial() {
		return "/pages/protected/index.xhtml";
	}
	

    /** Enum Telas */
	public List<TelasEntidadesEnum> getTodosTelasEntidadesEnum() {
		if (todosTelasEntidadesEnum == null) {
			todosTelasEntidadesEnum = new ArrayList<TelasEntidadesEnum>();
			todosTelasEntidadesEnum = new ArrayList<TelasEntidadesEnum>(Arrays.asList(TelasEntidadesEnum.values()));	
		}
		return todosTelasEntidadesEnum;
	}

	/** Enum Parametros */
	public List<TiposParametrosEnum> getTodosTiposParametrosEnum() {
		if (todosTiposParametrosEnum == null) {
			todosTiposParametrosEnum = new ArrayList<TiposParametrosEnum>();
			todosTiposParametrosEnum = new ArrayList<TiposParametrosEnum>(Arrays.asList(TiposParametrosEnum.values()));	
		}
		return todosTiposParametrosEnum;
	}
	
	public void filtroTa1bela() {
	}

	public FiltroTabelaBean getFiltroTabelaBean() {
		return null;
	}

	public void setFiltroTabelaBean(FiltroTabelaBean filtroTabelaBean) {
	}

	@Override
	public String getFiltroGeral() {
		return null;
	}

	@Override
	public void setFiltroGeral(String filtroGeral) {
	}

	@Override
	public void filtroTabela() {
		// TODO Auto-generated method stub
		
	}
}
