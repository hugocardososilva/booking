package com.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.LazyDataModel;

import com.rn.AuditoriaLogRN;
import com.util.JSFMessageUtil;

import conexao.com.bean.AbstractLazyDataModel;
import conexao.com.bean.AbstractMB;
import conexao.com.rn.CadastroBLRN;
import conexao.com.util.FiltroTabelaBean;
import conexao.com.util.IGenericBean;
import seguranca.com.entidade.CadastroBL;
import seguranca.com.entidade.CadastroBLContanier;
import seguranca.com.entidade.CadastroBLDescricaoMercadoria;
import seguranca.com.entidade.CadastroBLRiscoFitossanitario;
import seguranca.com.entidade.CadastroBlLogAlteracao;
import seguranca.com.entidade.LogInspecaoLiberadoMapa;

@ViewScoped
@ManagedBean
public class CadastroBLLogAlteracaoBean extends AbstractMB implements IGenericBean {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	
	private boolean mostrarListaExpandir = false;

	private CadastroBLContanier entidade;
	
	private CadastroBLRN cadBLRN;
	
	private LazyDataModel<CadastroBL> lazyModel;

	private AuditoriaLogRN auditoriaLogRN;

	private List<LogInspecaoLiberadoMapa> listaContainers;

	public List<CadastroBlLogAlteracao> listaBLFiscal(CadastroBL item) {
		List<CadastroBlLogAlteracao> listaContainers = null;
		if (item != null) {
			listaContainers = item.getListaLogAlteracao();
		}
		return listaContainers;
	}
	
	private AuditoriaLogRN getAuditoriaLogRN() {
		if (auditoriaLogRN == null) {
			auditoriaLogRN = new AuditoriaLogRN();
		}
		return auditoriaLogRN;
	}

	public List<LogInspecaoLiberadoMapa> listaLogAuditoriaContainer(CadastroBL item) {
		try {
			if (listaContainers == null && item != null && isMostrarListaExpandir() ) {
				listaContainers = getAuditoriaLogRN().getLogRegistrosContainer(item);
				
				RequestContext.getCurrentInstance().update("frmListarPrincipal:tabItensConsultaBLLog:tbListarEntidade:0:tabLogReg:j_idt42:tbListaTodosContainers");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
		return listaContainers;
	}
	
	public void onRowToggle(ToggleEvent event) {
		if (event.getVisibility().compareTo(org.primefaces.model.Visibility.VISIBLE) == 0) {
			setMostrarListaExpandir(true);
		} else {
			setMostrarListaExpandir(false);
		}
		listaContainers = null;
	}

	public List<CadastroBlLogAlteracao> listaLogAnexoDescMercadoria(CadastroBL item) {
		List<CadastroBlLogAlteracao> listaContainers = null;
		if (item != null) {
			listaContainers = item.getListaLogAlteracao();
		}
		return listaContainers;
	}

	public List<CadastroBLRiscoFitossanitario> listaRisco(CadastroBL item) {
		List<CadastroBLRiscoFitossanitario> listaRisco = null;
		if (item != null) {
			listaRisco = item.getListaBLRisco();
		}
		return listaRisco;
	}

	public List<CadastroBLDescricaoMercadoria> listaDescricaoMercadoria(CadastroBL item) {
		List<CadastroBLDescricaoMercadoria> listaDescricao = null;
		if (item != null) {
			listaDescricao = item.getListaBLDescricaoMercadoria();
		}
		return listaDescricao;
	}
	
	public CadastroBLRN getCadastroBLRN() {
		if (cadBLRN == null) {
			cadBLRN = new CadastroBLRN();
		}
		return cadBLRN;
	}

	public CadastroBLContanier getEntidade() {
		return entidade;
	}

	public void setEntidade(CadastroBLContanier entidade) {
		this.entidade = entidade;
	}

	public LazyDataModel<CadastroBL> getLazyModel() {
		if (lazyModel == null) {
//			setWhereSQL(FiltroTabelaBean.montaCondicaoSqlWhereAND(null, CadastroBL.CAMPO_ENVIARPARAMAPA,
//					TipoFiltroEnum.IGUAL_COM_WHERE, 1));

			lazyModel = new AbstractLazyDataModel<CadastroBL>(CadastroBL.sqlMapa, CadastroBL.sqlMapaCount, getWhereSQL(), null);
		}

		return lazyModel;
	}

	@Override
	public void filtroTabela() {
		try {
			lazyModel = null;
			setWhereSQL(null);

			if (!getFiltroGeral().isEmpty()) {

				getFiltrosParametros().add(CadastroBL.CADASTRO_BL_DESCRICAOBL);

				setWhereSQL(FiltroTabelaBean.montaCondicaoSqlSemWhereOR(getFiltroGeral(), getFiltrosParametros()));
//
//				setWhereSQL(FiltroTabelaBean.montaCondicaoSqlWhereAND(getWhereSQL(), CadastroBL.CAMPO_ENVIARPARAMAPA,
//						TipoFiltroEnum.IGUAL, 1));
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

	public boolean isMostrarListaExpandir() {
		return mostrarListaExpandir;
	}

	public void setMostrarListaExpandir(boolean mostrarListaExpandir) {
		this.mostrarListaExpandir = mostrarListaExpandir;
	}
}