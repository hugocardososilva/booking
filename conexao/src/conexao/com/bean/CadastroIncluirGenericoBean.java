package conexao.com.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.primefaces.context.RequestContext;

import conexao.com.rn.CadastroComissarioRN;
import conexao.com.rn.CadastroImportadorRN;
import conexao.com.rn.CadastroResponsavelLoteRN;
import conexao.com.util.IGenericBean;
import conexao.com.util.JSFMessageUtil;
import seguranca.com.entidade.CadastroComissario;
import seguranca.com.entidade.CadastroImportador;
import seguranca.com.entidade.CadastroResponsavelLote;
import seguranca.com.enums.TipoPessoaFisicaJuridicaEnum;

public class CadastroIncluirGenericoBean extends AbstractMB implements IGenericBean {

	private static final String UPDATE_IMPORTADORES_USUARIOS = "frmPrincipalUsuarios:frmCadastrarUsuario:cadFormUsuario:importadores";
	private static final String UPDATE_COMISSARIA_USUARIO = "frmPrincipalUsuarios:frmCadastrarUsuario:cadFormUsuario:categoria";
	private String razaoSocial;
	private String cnpj;
	private String email;
	private String mascaraCnpjCpf;
	private boolean informarEmail = false;
	private boolean mostrarEmail = true;

	private CadastroImportador entidadeImportador;
	private CadastroComissario entidadeCamissaria;
	private CadastroResponsavelLote entidadeResponsavelLote;

	private TipoPessoaFisicaJuridicaEnum selecionadoPessoaFisicaJuridicaEnum;
	private CadastroImportadorRN importadorRN;
	private CadastroComissarioRN cadastroComissarioRN;

	private List<TipoPessoaFisicaJuridicaEnum> todasTipoPessoaFisicaJuridicaEnum;
	private List<CadastroComissario> listaTodosComissarios;
	private List<CadastroImportador> listaTodosImportadores;
	private CadastroResponsavelLoteRN responsavelLoteRN;

	@Override
	public void filtroTabela() {
	}

	@Override
	public String getFiltroGeral() {
		return null;
	}

	@Override
	public void setFiltroGeral(String filtroGeral) {
	}

	public List<CadastroComissario> getListaTodosComissarios() throws Exception {
		if (listaTodosComissarios == null) {
			listaTodosComissarios = getCadastroComissarioRN().listaTodos();
		}
		return listaTodosComissarios;
	}
	
	public List<CadastroImportador> getListaTodosImportadores() {
		if (listaTodosImportadores == null) {
			listaTodosImportadores = getImportadorRN().listaTodos();
		}
		return listaTodosImportadores;
	}
	
	
	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TipoPessoaFisicaJuridicaEnum getSelecionadoPessoaFisicaJuridicaEnum() {
		return selecionadoPessoaFisicaJuridicaEnum;
	}

	public void setSelecionadoPessoaFisicaJuridicaEnum(
			TipoPessoaFisicaJuridicaEnum selecionadoPessoaFisicaJuridicaEnum) {
		this.selecionadoPessoaFisicaJuridicaEnum = selecionadoPessoaFisicaJuridicaEnum;
	}

	public void alterarMasCara() {
		if (getSelecionadoPessoaFisicaJuridicaEnum() == null
				|| getSelecionadoPessoaFisicaJuridicaEnum() == TipoPessoaFisicaJuridicaEnum.PESSOA_FISICA) {
			setMascaraCnpjCpf("999.999.999-99");
		} else {
			setMascaraCnpjCpf("99.999.999/9999-99");
		}
	}

	public String getMascaraCnpjCpf() {
		return mascaraCnpjCpf;
	}

	public void setMascaraCnpjCpf(String mascaraCnpjCpf) {
		this.mascaraCnpjCpf = mascaraCnpjCpf;
	}

	public void salvarImportadorComissaria() {
		try {
			salvarComissaria();

			salvarImportador();

			// salvarRepresentante();

			salvarResponsavelLote();

			// salvarFaturamentoContra();

			RequestContext.getCurrentInstance().execute("PF('dlgBLIncluir').hide()");
			
			JSFMessageUtil.adicionarMensagemSucesso("Operação realizada com Sucesso !");
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	private void salvarResponsavelLote() throws Exception {
		if (getEntidadeResponsavelLote() != null) {
			getEntidadeResponsavelLote().setCnpj(getCnpj());
			getEntidadeResponsavelLote().setRazaoSocial(getRazaoSocial());
			getEntidadeResponsavelLote().setEmail(getEmail());

			getResponsavelLoteRN().alterar(getEntidadeResponsavelLote());
			setEntidadeResponsavelLote(null);
//			listaRepresentante = null;
		}
	}

	private CadastroResponsavelLoteRN getResponsavelLoteRN() {
		if (responsavelLoteRN == null) {
			responsavelLoteRN = new CadastroResponsavelLoteRN();
		}
		return responsavelLoteRN;
	}

	private void salvarImportador() throws Exception {
		if (getEntidadeImportador() != null) {
			getEntidadeImportador().setCnpj(getCnpj());
			getEntidadeImportador().setRazaoSocial(getRazaoSocial());
			getEntidadeImportador().setEmail(getEmail());

			getImportadorRN().alterar(getEntidadeImportador());
			
			listaTodosImportadores = null;
			getListaTodosImportadores();
			
			RequestContext.getCurrentInstance().update(UPDATE_IMPORTADORES_USUARIOS);
		}
	}

	private CadastroImportadorRN getImportadorRN() {
		if (importadorRN == null) {
			importadorRN = new CadastroImportadorRN();
		}
		return importadorRN;
	}

	public CadastroImportador getEntidadeImportador() {
		return entidadeImportador;
	}

	public void setEntidadeImportador(CadastroImportador entidadeImportador) {
		this.entidadeImportador = entidadeImportador;
	}

	private void salvarComissaria() throws Exception {
		if (getEntidadeCamissaria() != null) {
			getEntidadeCamissaria().setCpf(getCnpj());
			getEntidadeCamissaria().setNome(getRazaoSocial());
			getEntidadeCamissaria().setTipoPessoaEnum(TipoPessoaFisicaJuridicaEnum.PESSOA_JURIDICA);
			getEntidadeCamissaria().setEmail(getEmail());

			getCadastroComissarioRN().alterar(getEntidadeCamissaria());

			listaTodosComissarios = null;
			getListaTodosComissarios();
			
			RequestContext.getCurrentInstance().update(UPDATE_COMISSARIA_USUARIO);
		}
	}
	
	private CadastroComissarioRN getCadastroComissarioRN() {
		if (cadastroComissarioRN == null){
			cadastroComissarioRN = new CadastroComissarioRN();
		}
		return cadastroComissarioRN;
	}

	public CadastroComissario getEntidadeCamissaria() {
		return entidadeCamissaria;
	}

	public void setEntidadeCamissaria(CadastroComissario entidadeCamissaria) {
		this.entidadeCamissaria = entidadeCamissaria;
	}
	
	public List<TipoPessoaFisicaJuridicaEnum> getTodasTipoPessoaFisicaJuridicaEnum() {
		if (todasTipoPessoaFisicaJuridicaEnum == null) {
			todasTipoPessoaFisicaJuridicaEnum = new ArrayList<TipoPessoaFisicaJuridicaEnum>();
			todasTipoPessoaFisicaJuridicaEnum = new ArrayList<TipoPessoaFisicaJuridicaEnum>(
					Arrays.asList(TipoPessoaFisicaJuridicaEnum.values()));
		}

		return todasTipoPessoaFisicaJuridicaEnum;
	}
	
	/**
	 * Tipo ->> 1 IMPORTADOR Tipo ->> 2 COMISSARIA Tipo ->> 3 REPRESENTANTE
	 * COMISSARIA Tipo ->> 4 RESPONSAVEL LOTE Tipo ->> 5 FATURAR CONTRA
	 * @param entidade
	 */
	public void instanciarImportadorComissaria(int entidade) {
		setarInformacaoEditarInstanciar();
		setMostrarEmail(true);

		setSelecionadoPessoaFisicaJuridicaEnum(TipoPessoaFisicaJuridicaEnum.PESSOA_JURIDICA);
		setMascaraCnpjCpf("99.999.999/9999-99");
		setInformarEmail(false);
		if (entidade == 1) {
			setEntidadeImportador(new CadastroImportador());
		}
		if (entidade == 2) {
			setEntidadeCamissaria(new CadastroComissario());
		}
//		if (entidade == 3) {
//			setEntidadeRepresentante(new CadastroRepresentante());
//		}
		if (entidade == 4) {
			setEntidadeResponsavelLote(new CadastroResponsavelLote());
		}
		if (entidade == 5) {
			setMostrarEmail(false);
//			setInformarEmail(true);
//			setEntidadeFaturarContra(new CadastroFaturarContra());
		}
	}

	private void setarInformacaoEditarInstanciar() {
		setRazaoSocial(null);
		setCnpj(null);
		setEmail(null);
		setEntidadeImportador(null);
		setEntidadeCamissaria(null);
//		setEntidadeRepresentante(null);
		setEntidadeResponsavelLote(null);
//		setEntidadeFaturarContra(null);
	}

	public boolean isInformarEmail() {
		return informarEmail;
	}

	public void setInformarEmail(boolean informarEmail) {
		this.informarEmail = informarEmail;
	}

	public CadastroResponsavelLote getEntidadeResponsavelLote() {
		return entidadeResponsavelLote;
	}

	public void setEntidadeResponsavelLote(CadastroResponsavelLote entidadeResponsavelLote) {
		this.entidadeResponsavelLote = entidadeResponsavelLote;
	}

	public boolean isMostrarEmail() {
		return mostrarEmail;
	}

	public void setMostrarEmail(boolean mostrarEmail) {
		this.mostrarEmail = mostrarEmail;
	}
	
}
