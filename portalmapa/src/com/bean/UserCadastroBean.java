package com.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import conexao.com.bean.AbstractLazyDataModel;
import conexao.com.bean.UserCadastroComumBean;
import conexao.com.util.FiltroTabelaBean;
import seguranca.com.entidade.User;

@ViewScoped
@ManagedBean
public class UserCadastroBean extends UserCadastroComumBean {

	private LazyDataModel<User> lazyModel;

	@Override
	public LazyDataModel<User> getLazyModel() {
		if (lazyModel == null) {
			lazyModel = new AbstractLazyDataModel<User>(User.sql_portalmapa, User.sqlCount_portalmapa, getWhereSQL(), null);
		}

		return lazyModel;
	}

	@Override
	public void filtroTabela() {
		try {
			setLazyModel(null);
			setWhereSQL(null);

			if (!getFiltroGeral().isEmpty()) {

				getFiltrosParametros().add(User.NAME);
				getFiltrosParametros().add(User.CAMPO_EMAIL);
				getFiltrosParametros().add(User.CAMPO_CPF);
				getFiltrosParametros().add(User.CAMPO_ID);

				setWhereSQL(FiltroTabelaBean.montaCondicaoSqlSemWhereOR(getFiltroGeral(), getFiltrosParametros()));
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

	public void setLazyModel(LazyDataModel<User> lazyModel) {
		this.lazyModel = lazyModel;
	}
	
	
}