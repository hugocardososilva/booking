package conexao.com.bean;

import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

import conexao.com.rn.CadastroImportadorRN;
import conexao.com.util.FiltroTabelaBean;
import conexao.com.util.IGenericBean;
import conexao.com.util.JSFMessageUtil;
import seguranca.com.entidade.CadastroImportador;

public class CadastroImportadorComumBean extends AbstractMB implements IGenericBean {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	private boolean controlarFormListar = true;
	private boolean controlarFormCadastrar = false;

	private CadastroImportador entidade;
	private CadastroImportadorRN cadImportadorRN;

	private LazyDataModel<CadastroImportador> lazyModel;

	public CadastroImportadorRN getImportadorRN() {
		if (cadImportadorRN == null) {
			cadImportadorRN = new CadastroImportadorRN();
		}
		return cadImportadorRN;
	}

	public void redirecionarCadastro() {
		try {
			setControlarFormCadastrar(true);
			setControlarFormListar(false);
			setEntidade(new CadastroImportador());
			atualizarFormCadastroUsuario();
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	private void atualizarFormCadastroUsuario() {
		RequestContext.getCurrentInstance().update(PG_FORM_CADASTRAR_ENTIDADE);
	}

	public void editarEntidade(CadastroImportador entidade) {
		try {
			setControlarFormCadastrar(true);
			setControlarFormListar(false);
			setEntidade(entidade);
			atualizarFormCadastroUsuario();
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void salvarEntidade() {
		try {
			setControlarFormCadastrar(false);
			setControlarFormListar(true);
			getImportadorRN().alterar(getEntidade());
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

	public void remover(CadastroImportador entidade) {
		try {
			getImportadorRN().deletar(entidade);
			atualizarListaUsuario();
		} catch (Exception e) {
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public CadastroImportador getEntidade() {
		return entidade;
	}

	public void setEntidade(CadastroImportador entidade) {
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

	public LazyDataModel<CadastroImportador> getLazyModel() {
		if (lazyModel == null) {
			lazyModel = new AbstractLazyDataModel<CadastroImportador>(CadastroImportador.sql,
					CadastroImportador.sqlCount, getWhereSQL(), null);
		}

		return lazyModel;
	}

	@Override
	public void filtroTabela() {
		try {
			lazyModel = null;
			setWhereSQL(null);

			if (!getFiltroGeral().isEmpty()) {

				getFiltrosParametros().add(CadastroImportador.CAMPO_CNPJ);
				getFiltrosParametros().add(CadastroImportador.CAMPO_RAZAOSOCIAL);
				getFiltrosParametros().add(CadastroImportador.IMPORTADOR_ID);

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
}