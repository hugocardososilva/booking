package com.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.primefaces.context.RequestContext;

import com.entidade.CadastroDTCDTA;
import com.entidade.UserComissariaAgendamento;
import com.entidade.UserComissariaUsuarios;
import com.entidade.UserImportadorAgendamento;
import com.entidade.UserImportadorComissaria;
import com.entidade.UserImportadorUsuarios;
import com.rn.UserComissariaUsuariosRN;
import com.rn.UserImportadorAgendamentoRN;
import com.rn.UserImportadorComissariaRN;
import com.rn.UserImportadorUsuariosRN;
import com.util.JSFMessageUtil;

import conexao.com.bean.AbstractMB;
import conexao.com.rn.CadastroBLAnexosRN;
import conexao.com.rn.CadastroBLRN;
import conexao.com.rn.CadastroCodigoPaisRN;
import conexao.com.rn.CadastroComissarioRN;
import conexao.com.rn.CadastroEspecieRN;
import conexao.com.rn.CadastroFaturarContraRN;
import conexao.com.rn.CadastroImportadorRN;
import conexao.com.rn.CadastroNcmRN;
import conexao.com.rn.CadastroPortosRN;
import conexao.com.rn.CadastroRepresentanteRN;
import conexao.com.rn.CadastroResponsavelLoteRN;
import conexao.com.rn.LeituraArquivoRN;
import conexao.com.rn.MapaNcmRN;
import conexao.com.rn.ProgramacaoNavioRN;
import conexao.com.rn.UserComissarioRN;
import conexao.com.rn.UserImportadorRN;
import conexao.com.rn.UserResponsavelLoteRN;
import seguranca.com.entidade.CadastroBL;
import seguranca.com.entidade.CadastroBLAnexos;
import seguranca.com.entidade.CadastroCodigoPais;
import seguranca.com.entidade.CadastroComissario;
import seguranca.com.entidade.CadastroEspecie;
import seguranca.com.entidade.CadastroFaturarContra;
import seguranca.com.entidade.CadastroImportador;
import seguranca.com.entidade.CadastroNcm;
import seguranca.com.entidade.CadastroPortos;
import seguranca.com.entidade.CadastroRepresentante;
import seguranca.com.entidade.CadastroResponsavelLote;
import seguranca.com.entidade.MapaNcm;
import seguranca.com.entidade.ProgramacaoNavio;
import seguranca.com.entidade.User;
import seguranca.com.entidade.UserComissario;
import seguranca.com.entidade.UserImportador;
import seguranca.com.enums.ModalidadeBLEnum;
import seguranca.com.enums.TipoAnexosEnum;
import seguranca.com.enums.TipoContainerEnum;
import seguranca.com.enums.TipoPessoaFisicaJuridicaEnum;
import seguranca.com.enums.TipoSimNaoEnum;
import seguranca.com.enums.TipomodalEnum;

public class CadastroDtcDtaComumBean extends AbstractMB {

	private boolean controlarFormListar = true;
	private boolean controlarFormCadastrar = false;
	private boolean controlarAnexos = false;
	private boolean informarEmail = false;
	private boolean bloquearNavio = false;
	private boolean mostrarEmail = true;

	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;

	private String razaoSocial;
	private String cnpj;
	private String email;

	private String retornoBL;
	private String retornoCE;
	private String retornoDTC_DTA;
	private String retornoINVOICE;
	private String retornoPACKING;
	private String mascaraCnpjCpf;

	private String retornoCorBL;
	private String retornoCorCE;
	private String retornoCorDTC_DTA;
	private String retornoCorINVOICE;
	private String retornoCorPACKING;
	private String textoMaoLivreEmail;

	private CadastroBL entidadeBL;
	private UserComissario userComissario;
	private CadastroDTCDTA entidadeDTCDTA;
	private CadastroImportador importadorSelecionado;
	private CadastroComissario comissarioSelecionado;
	private CadastroImportador entidadeImportador;
	private CadastroComissario entidadeCamissaria;
	private CadastroRepresentante entidadeRepresentante;
	private CadastroResponsavelLote entidadeResponsavelLote;
	private CadastroFaturarContra entidadeFaturarContra;
	private ProgramacaoNavio entidadeProgramacaoNavio;
	private CadastroCodigoPais paisSelecionado;
	private ProgramacaoNavio programacaoNavioSelecionado;
	private CadastroCodigoPais paisProcedenciaSelecionado;
	private CadastroPortos portoSelecionado;
	private CadastroRepresentante representanteSelecionado;
	private CadastroResponsavelLote responsavelLoteSelecionado;
	private CadastroFaturarContra faturarContraSelecionado;
	private CadastroEspecie embalagemEncontradaSelecionado;
	private CadastroEspecie especieSelecionado;
	private TipoAnexosEnum selecionadoTipoAnexos;
	// private TipoAnexosEnum selecionadoTipoStatusAnexos;

	private List<TipoSimNaoEnum> todosTipoSimNao;
	private List<TipomodalEnum> todasTipoModalEnum;
	private List<ModalidadeBLEnum> todasModalidadeBLEnum;
	private List<TipoContainerEnum> todasTipoContainerEnum;
	private List<TipoAnexosEnum> todasTipoAnexosEnum;
	private List<CadastroBLAnexos> listaAnexosPorTipo;
	private List<MapaNcm> listaMapaNCM;
	private TipoSimNaoEnum selecionadoCargaImo;

	private List<CadastroComissario> listaUserComissario;

	private MapaNcmRN mapaNcmRN;
	private UserComissarioRN userComissarioRN;
	private CadastroEspecieRN cadEspecieRN;
	private CadastroImportadorRN importadorRN;
	private CadastroNcm ncmSelecionado;
	private CadastroNcmRN cadNcmRN;
	private CadastroCodigoPaisRN cadCodigoPaisRN;
	private CadastroPortosRN portosRN;
	private CadastroRepresentanteRN representanteRN;
	private CadastroComissarioRN cadastroComissarioRN;
	private CadastroBLAnexosRN cadBLAnexosRN;

	private List<CadastroImportador> listaImportador;
	private List<CadastroEspecie> listaEmbalagem;
	private List<CadastroCodigoPais> listaPais;
	private List<CadastroRepresentante> listaRepresentante;
	private List<CadastroPortos> listaPorto;
	private List<TipoPessoaFisicaJuridicaEnum> todasTipoPessoaFisicaJuridicaEnum;
	private TipoPessoaFisicaJuridicaEnum selecionadoPessoaFisicaJuridicaEnum;
	private CadastroResponsavelLoteRN responsavelLoteRN;
	private CadastroFaturarContraRN faturarContraRN;
	private ProgramacaoNavioRN programacaoNavioRN;
	private UserResponsavelLoteRN userResponsavelLoteRN;
	private UserImportadorRN userImportadorRN;
	private UserImportadorComissariaRN userImportadorComissariaRN;
	private UserImportadorAgendamentoRN userImportadorAgendamentoRN;
	private UserImportadorUsuariosRN importadorUsuariosRN;
	private UserComissariaUsuariosRN userComissariaUsuariosRN;
	private List<CadastroComissario> listaComissarias;
	private CadastroBLRN cadastroBLRN;

	public List<CadastroPortos> getListaPorto() {
		if (listaPorto == null) {
			listaPorto = getPortosRN().listaTodos();
		}
		return listaPorto;
	}

	private MapaNcmRN getMapaNcmRN() {
		if (mapaNcmRN == null) {
			mapaNcmRN = new MapaNcmRN();
		}
		return mapaNcmRN;
	}

	public List<CadastroEspecie> getListaEmbalagem() {
		if (listaEmbalagem == null) {
			listaEmbalagem = getCadEspecieRN().listaTodos();
		}
		return listaEmbalagem;
	}

	public List<CadastroImportador> getListaImportador() {
		if (listaImportador == null) {
			listaImportador = getImportadorRN().listaTodos();
		}
		return listaImportador;
	}

	public List<CadastroComissario> getListaComissarias() {
		if (listaComissarias == null) {
			try {
				listaComissarias = getCadastroComissarioRN().listaTodos();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return listaComissarias;
	}

	public List<CadastroCodigoPais> getListaPais() {
		if (listaPais == null) {
			listaPais = getCadCodigoPaisRN().listaTodos();
		}
		return listaPais;
	}

	public List<CadastroRepresentante> getListaRepresentante() {
		if (listaRepresentante == null) {
			listaRepresentante = getRepresentanteRN().listaTodos();
		}
		return listaRepresentante;
	}

	public List<ProgramacaoNavio> listaProgramacaoNavio(String pesquisa) {
		List<ProgramacaoNavio> lista = new ArrayList<ProgramacaoNavio>();
		if (getEntidadeBL() != null || getEntidadeDTCDTA() != null) {
			lista = getProgramacaoNavioRN().listaTodosAutoComplete(pesquisa);
		}
		return lista;
	}

	private ProgramacaoNavioRN getProgramacaoNavioRN() {
		if (programacaoNavioRN == null) {
			programacaoNavioRN = new ProgramacaoNavioRN();
		}

		return programacaoNavioRN;
	}

	public List<CadastroFaturarContra> listaFaturarContra(String pesquisa) {
		List<CadastroFaturarContra> lista = new ArrayList<CadastroFaturarContra>();
		try {
			if (getEntidadeBL() != null || getEntidadeDTCDTA() != null) {
				lista = getFaturarContraRN().listaTodosAutoComplete(pesquisa);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
		return lista;
	}

	private CadastroFaturarContraRN getFaturarContraRN() {
		if (faturarContraRN == null) {
			faturarContraRN = new CadastroFaturarContraRN();
		}
		return faturarContraRN;
	}

	private CadastroResponsavelLoteRN getResponsavelLoteRN() {
		if (responsavelLoteRN == null) {
			responsavelLoteRN = new CadastroResponsavelLoteRN();
		}
		return responsavelLoteRN;
	}

	protected UserComissarioRN getUserComissarioRN() {
		if (userComissarioRN == null) {
			userComissarioRN = new UserComissarioRN();
		}
		return userComissarioRN;
	}

	public void carregarDadosComissarias() {
		listaUserComissario = null;
	}

	public List<TipomodalEnum> getTodasTipoModalEnum() {
		if (todasTipoModalEnum == null && isControlarFormCadastrar()) {
			todasTipoModalEnum = new ArrayList<TipomodalEnum>();
			todasTipoModalEnum = new ArrayList<TipomodalEnum>(Arrays.asList(TipomodalEnum.values()));
		}
		return todasTipoModalEnum;
	}

	public List<TipoAnexosEnum> getTodasTipoAnexosEnum() {
		if (todasTipoAnexosEnum == null && isControlarFormCadastrar()) {
			todasTipoAnexosEnum = new ArrayList<TipoAnexosEnum>();
			todasTipoAnexosEnum.add(TipoAnexosEnum.STATUS_ANEXO);
			todasTipoAnexosEnum.add(TipoAnexosEnum.BL);
			todasTipoAnexosEnum.add(TipoAnexosEnum.CE);
			todasTipoAnexosEnum.add(TipoAnexosEnum.INVOICE);
			todasTipoAnexosEnum.add(TipoAnexosEnum.PACKING);
			todasTipoAnexosEnum.add(TipoAnexosEnum.DTC_DTA);
			todasTipoAnexosEnum.add(TipoAnexosEnum.CLIF_ANEXO);
			todasTipoAnexosEnum.add(TipoAnexosEnum.MAPA_ANEXO);
			todasTipoAnexosEnum.add(TipoAnexosEnum.COMPLEMENTARES_ANEXO);
			todasTipoAnexosEnum.add(TipoAnexosEnum.ANEXO_LI);
			todasTipoAnexosEnum.add(TipoAnexosEnum.FISPQ);
		}
		return todasTipoAnexosEnum;
	}

	public boolean visualizarAnexosUpload(TipoAnexosEnum item) {
		boolean valor = true;
		if (item.getCodigo() == 5 && isControlarAnexos()) {
			valor = false;
		}
		return valor;
	}

	public boolean visualizarAnexosStatus(TipoAnexosEnum item) {
		boolean valor = false;
		if (item.getCodigo() == 5 && isControlarAnexos()) {
			valor = true;
		}
		return valor;
	}

	public void instanciarAnexos(TipoAnexosEnum item) {
		try {

			if (item != null) {
				setSelecionadoTipoAnexos(item);
				setControlarAnexos(true);
				setListaAnexosPorTipo(listaBLTodosAnexos(item));

				RequestContext.getCurrentInstance().update(
						"anexosGenerico:abas:" + item.getPosicaoAbaTela() + ":frmAnexosImportacao:tblAnexosImportacao");
			} else {
				CadastroBL bl = getEntidadeDTCDTA().getCadastroBL();

				// setRetornoBL(getCadBLAnexosRN().executarQtdeRegistros(bl,
				// TipoAnexosEnum.BL));
				// setRetornoCE(getCadBLAnexosRN().executarQtdeRegistros(bl,
				// TipoAnexosEnum.CE));
				// setRetornoDTC_DTA(getCadBLAnexosRN().executarQtdeRegistros(bl,
				// TipoAnexosEnum.DTC_DTA));
				// setRetornoINVOICE(getCadBLAnexosRN().executarQtdeRegistros(bl,
				// TipoAnexosEnum.INVOICE));
				// setRetornoPACKING(getCadBLAnexosRN().executarQtdeRegistros(bl,
				// TipoAnexosEnum.PACKING));

				if (getCadBLAnexosRN().executarQtdeRegistrosValor(bl, TipoAnexosEnum.BL) == 0) {
					setRetornoBL(TipoAnexosEnum.BL + " -->> Pendente de Anexo");
					setRetornoCorBL("color: red");
				} else {
					setRetornoBL(TipoAnexosEnum.BL + " -->> Documento Anexado");
					setRetornoCorBL("color: black");
				}

				if (getCadBLAnexosRN().executarQtdeRegistrosValor(bl, TipoAnexosEnum.CE) == 0) {
					setRetornoCE(TipoAnexosEnum.CE + " -->> Pendente de Anexo");
					setRetornoCorCE("color: red");
				} else {
					setRetornoCE(TipoAnexosEnum.CE + " -->> Documento Anexado");
					setRetornoCorCE("color: black");
				}

				if (getCadBLAnexosRN().executarQtdeRegistrosValor(bl, TipoAnexosEnum.DTC_DTA) == 0) {
					setRetornoDTC_DTA(TipoAnexosEnum.DTC_DTA + " -->> Pendente de Anexo");
					setRetornoCorDTC_DTA("color: red");
				} else {
					setRetornoDTC_DTA(TipoAnexosEnum.DTC_DTA + " -->> Documento Anexado");
					setRetornoCorDTC_DTA("color: black");
				}

				if (getCadBLAnexosRN().executarQtdeRegistrosValor(bl, TipoAnexosEnum.INVOICE) == 0) {
					setRetornoINVOICE(TipoAnexosEnum.INVOICE + " -->> Pendente de Anexo");
					setRetornoCorINVOICE("color: red");
				} else {
					setRetornoINVOICE(TipoAnexosEnum.INVOICE + " -->> Documento Anexado");
					setRetornoCorINVOICE("color: black");
				}

				if (getCadBLAnexosRN().executarQtdeRegistrosValor(bl, TipoAnexosEnum.PACKING) == 0) {
					setRetornoPACKING(TipoAnexosEnum.PACKING + " -->> Pendente de Anexo");
					setRetornoCorPACKING("color: red");
				} else {
					setRetornoPACKING(TipoAnexosEnum.PACKING + " -->> Documento Anexado");
					setRetornoCorPACKING("color: black");
				}

				setSelecionadoTipoAnexos(TipoAnexosEnum.STATUS_ANEXO);

				RequestContext.getCurrentInstance().update("anexosGenerico:abas:0:frmAnexosStatus");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public List<CadastroBLAnexos> listaBLTodosAnexos(TipoAnexosEnum tipo) {
		List<CadastroBLAnexos> listaBLTodosAnexos = null;
		if (isControlarAnexos()) {
			CadastroBL bl = null;
			if (getEntidadeDTCDTA() != null) {
				bl = getEntidadeDTCDTA().getCadastroBL();
			}

			if (getEntidadeBL() != null) {
				bl = getEntidadeBL();

				if (bl.getId() == null) {
					bl = getEntidadeDTCDTA().getCadastroBL();
				}
			}

			if (bl != null) {
				listaBLTodosAnexos = getCadBLAnexosRN().listaTodosPorTipoAnexos(bl, tipo);
			}
		}
		return listaBLTodosAnexos;
	}

	private CadastroBLAnexosRN getCadBLAnexosRN() {
		if (cadBLAnexosRN == null) {
			cadBLAnexosRN = new CadastroBLAnexosRN();
		}

		return cadBLAnexosRN;
	}

	public List<TipoSimNaoEnum> getTodosTipoSimNao() {
		if (todosTipoSimNao == null && isControlarFormCadastrar()) {
			todosTipoSimNao = new ArrayList<TipoSimNaoEnum>();
			todosTipoSimNao = new ArrayList<TipoSimNaoEnum>(Arrays.asList(TipoSimNaoEnum.values()));
		}
		return todosTipoSimNao;
	}

	public CadastroEspecie getEspecieSelecionado() {
		return especieSelecionado;
	}

	public void setEspecieSelecionado(CadastroEspecie especieSelecionado) {
		this.especieSelecionado = especieSelecionado;
	}

	public CadastroImportador getEntidadeImportador() {
		return entidadeImportador;
	}

	public void setEntidadeImportador(CadastroImportador entidadeImportador) {
		this.entidadeImportador = entidadeImportador;
	}

	public CadastroComissario getEntidadeCamissaria() {
		return entidadeCamissaria;
	}

	public void setEntidadeCamissaria(CadastroComissario entidadeCamissaria) {
		this.entidadeCamissaria = entidadeCamissaria;
	}

	public CadastroRepresentante getEntidadeRepresentante() {
		return entidadeRepresentante;
	}

	public void setEntidadeRepresentante(CadastroRepresentante entidadeRepresentante) {
		this.entidadeRepresentante = entidadeRepresentante;
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

	public List<TipoPessoaFisicaJuridicaEnum> getTodasTipoPessoaFisicaJuridicaEnum() {
		if (todasTipoPessoaFisicaJuridicaEnum == null && isControlarFormCadastrar()) {
			todasTipoPessoaFisicaJuridicaEnum = new ArrayList<TipoPessoaFisicaJuridicaEnum>();
			todasTipoPessoaFisicaJuridicaEnum = new ArrayList<TipoPessoaFisicaJuridicaEnum>(
					Arrays.asList(TipoPessoaFisicaJuridicaEnum.values()));
		}

		return todasTipoPessoaFisicaJuridicaEnum;
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

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	/**
	 * Tipo ->> 1 IMPORTADOR Tipo ->> 2 COMISSARIA Tipo ->> 3 REPRESENTANTE
	 * COMISSARIA Tipo ->> 4 RESPONSAVEL LOTE Tipo ->> 5 FATURAR CONTRA
	 * 
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
		if (entidade == 3) {
			setEntidadeRepresentante(new CadastroRepresentante());
		}
		if (entidade == 4) {
			setEntidadeResponsavelLote(new CadastroResponsavelLote());
		}
		if (entidade == 5) {
			setInformarEmail(true);
			setMostrarEmail(false);
			setEntidadeFaturarContra(new CadastroFaturarContra());
		}
	}

	/**
	 * Tipo ->> 1 IMPORTADOR Tipo ->> 2 COMISSARIA Tipo ->> 3 REPRESENTANTE
	 * Editar dados
	 * 
	 * @param entidade
	 */
	public void editarAlterarDadosImportadorComissaria(int entidade, CadastroDTCDTA entidadeDtcDta) {
		setarInformacaoEditarInstanciar();
		setMostrarEmail(true);

		setSelecionadoPessoaFisicaJuridicaEnum(TipoPessoaFisicaJuridicaEnum.PESSOA_JURIDICA);
		setMascaraCnpjCpf("99.999.999/9999-99");
		setInformarEmail(false);
		if (entidade == 1) {
			setEntidadeImportador(entidadeDtcDta.getImportador());
			setRazaoSocial(getEntidadeImportador().getRazaoSocial());
			setCnpj(getEntidadeImportador().getCnpj());
			setEmail(getEntidadeImportador().getEmail());
		}
		if (entidade == 2) {
			setEntidadeCamissaria(entidadeDtcDta.getCadComissario());
			setRazaoSocial(getEntidadeCamissaria().getNome());
			setSelecionadoPessoaFisicaJuridicaEnum(getEntidadeCamissaria().getTipoPessoaEnum());
			setCnpj(getEntidadeCamissaria().getCpf());
			setEmail(getEntidadeCamissaria().getEmail());
		}
		if (entidade == 3) {
			setEntidadeRepresentante(entidadeDtcDta.getRepresentante());
			setRazaoSocial(getEntidadeRepresentante().getRazaoSocial());
			setCnpj(getEntidadeRepresentante().getCnpj());
			setEmail(getEntidadeRepresentante().getEmail());
		}
		if (entidade == 4) {
			setEntidadeResponsavelLote(entidadeDtcDta.getResponsavelLote());
			setRazaoSocial(getEntidadeResponsavelLote().getRazaoSocial());
			setCnpj(getEntidadeResponsavelLote().getCnpj());
			setEmail(getEntidadeResponsavelLote().getEmail());
		}
		if (entidade == 5) {
			setInformarEmail(true);
			setEntidadeFaturarContra(entidadeDtcDta.getFaturarContra());
			setRazaoSocial(getEntidadeFaturarContra().getRazaoSocial());
			setCnpj(getEntidadeFaturarContra().getCnpj());
			setEmail(getEntidadeFaturarContra().getEmail());
			setMostrarEmail(false);
		}
	}

	private void setarInformacaoEditarInstanciar() {
		setRazaoSocial(null);
		setCnpj(null);
		setEmail(null);
		setEntidadeImportador(null);
		setEntidadeCamissaria(null);
		setEntidadeRepresentante(null);
		setEntidadeResponsavelLote(null);
		setEntidadeFaturarContra(null);
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

	public CadastroComissarioRN getCadastroComissarioRN() {
		if (cadastroComissarioRN == null) {
			cadastroComissarioRN = new CadastroComissarioRN();
		}
		return cadastroComissarioRN;
	}

	private UserImportadorComissariaRN getUserImportadorComissariaRN() {
		if (userImportadorComissariaRN == null) {
			userImportadorComissariaRN = new UserImportadorComissariaRN();
		}

		return userImportadorComissariaRN;
	}

	private UserImportadorAgendamentoRN getUserImportadorAgendamentoRN() {
		if (userImportadorAgendamentoRN == null) {
			userImportadorAgendamentoRN = new UserImportadorAgendamentoRN();
		}

		return userImportadorAgendamentoRN;
	}

	private List<CadastroComissario> executaPermissaoComissariaPerfilDespachante() throws Exception {
		UserComissariaUsuarios userComissaria = getUserComissariaUsuariosRN()
				.retornaUsuarioComissaria(getUserMB().getUser());

		if (userComissaria == null) {
			listaUserComissario = getCadastroComissarioRN().listaTodos();
		} else {
			UserComissariaAgendamento userMaster = userComissaria.getComissariaMaster();

			listaUserComissario = retornaListaComissaria(userMaster.getUsuario());
		}

		return listaUserComissario;
	}

	private List<CadastroComissario> executaPermissaoComissariaPerfilDespachanteNovo(String pesquisa) throws Exception {
		UserComissariaUsuarios userComissaria = getUserComissariaUsuariosRN()
				.retornaUsuarioComissaria(getUserMB().getUser());

		if (userComissaria == null) {
			listaUserComissario = getCadastroComissarioRN().listaTodosAutoComplete(pesquisa);
		} else {
			UserComissariaAgendamento userMaster = userComissaria.getComissariaMaster();

			listaUserComissario = retornaListaComissaria(userMaster.getUsuario());
		}

		return listaUserComissario;
	}

	private List<CadastroComissario> retornaListaComissaria(User usuario) {
		List<UserComissario> list = getUserComissarioRN().listaComissariasPorUsuario(usuario);
		for (UserComissario item : list) {
			// List<UserImportadorComissaria> listaComissarias =
			// getUserImportadorComissariaRN()
			// .retornaComisarias(item.getCadComissario());
			//
			// for (UserImportadorComissaria itens : listaComissarias) {
			// listaUserComissario.add(itens.getComissaria());
			// }
			listaUserComissario.add(item.getCadComissario());
		}

		return listaUserComissario;
	}

	private List<CadastroComissario> retornaListaComissariaPerfilCliente() throws Exception {
		UserImportadorUsuarios userMaster = getImportadorUsuariosRN()
				.retornaImportadorUsuarioMaster(getUserMB().getUser());

		if (userMaster == null) {
			listaUserComissario = getCadastroComissarioRN().listaTodos();
		} else {
			listaUserComissario = retornaListaComissaria(userMaster.getImportadorComissaria());
		}
		return listaUserComissario;
	}

	private List<CadastroComissario> retornaListaComissariaPerfilClienteNovo(String pesquisa) throws Exception {
		UserImportadorUsuarios userMaster = getImportadorUsuariosRN()
				.retornaImportadorUsuarioMaster(getUserMB().getUser());

		if (userMaster == null) {
			listaUserComissario = getCadastroComissarioRN().listaTodosAutoComplete(pesquisa);
		} else {
			listaUserComissario = retornaListaComissaria(userMaster.getImportadorComissaria());
		}
		return listaUserComissario;
	}

	private List<CadastroComissario> retornaListaComissariaPerfilImportadorMaster() {
		UserImportadorAgendamento userMaster = getUserImportadorAgendamentoRN()
				.retornaImportadorUsuarioMaster(getUserMB().getUser());

		return listaUserComissario = retornaListaComissaria(userMaster);
	}

	private List<CadastroComissario> retornaListaComissaria(UserImportadorAgendamento userMaster) {
		List<UserImportadorComissaria> list = getUserImportadorComissariaRN().listaPorImportadorComiisarias(userMaster);

		for (UserImportadorComissaria item : list) {
			listaUserComissario.add(item.getComissaria());
		}

		return listaUserComissario;
	}

	public UserResponsavelLoteRN getUserResponsavelLoteRN() {
		if (userResponsavelLoteRN == null) {
			userResponsavelLoteRN = new UserResponsavelLoteRN();
		}
		return userResponsavelLoteRN;
	}

	public UserImportadorRN getUserImportadorRN() {
		if (userImportadorRN == null) {
			userImportadorRN = new UserImportadorRN();
		}

		return userImportadorRN;
	}

	private UserImportadorUsuariosRN getImportadorUsuariosRN() {
		if (importadorUsuariosRN == null) {
			importadorUsuariosRN = new UserImportadorUsuariosRN();
		}
		return importadorUsuariosRN;
	}

	public List<CadastroImportador> listaImportador(String pesquisa) {
		List<CadastroImportador> listaCadastroCodigoPais = new ArrayList<CadastroImportador>();
		if (getEntidadeBL() != null || getEntidadeDTCDTA() != null) {
			boolean verificaPermissao = true;

			if (getUserMB().getUser().isAdmin() || getUserMB().getUser().isClif()) {
				listaCadastroCodigoPais = getImportadorRN().listaTodosAutoComplete(pesquisa);
				verificaPermissao = false;
			}

			if (verificaPermissao) {
				if (getUserMB().getUser().isImportadorMaster()) {
					retornaListaPerfilImportadorMaster(listaCadastroCodigoPais);
				}
				if (getUserMB().getUser().isCliente()) {
					listaCadastroCodigoPais = retornaListaImportadorPerfilCliente(pesquisa, listaCadastroCodigoPais);
				}
				if (getUserMB().getUser().isComissariaMaster()) {
					listaCadastroCodigoPais = retornaListaImportadorPerfilComissaria(listaCadastroCodigoPais,
							getUserMB().getUser());
				}
				if (getUserMB().getUser().isDespachante()) {
					listaCadastroCodigoPais = criaListaImportadorPerfilDespachante(pesquisa, listaCadastroCodigoPais);
				}
			}
		}
		return listaCadastroCodigoPais;
	}

	public List<CadastroComissario> getListaUserComissario() throws Exception {
		if (listaUserComissario == null) {
			boolean verificaPermissao = true;

			if (getUserMB().getUser().isAdmin() || getUserMB().getUser().isClif()) {
				listaUserComissario = getCadastroComissarioRN().listaTodos();
				verificaPermissao = false;
			}

			if (verificaPermissao) {
				listaUserComissario = new ArrayList<CadastroComissario>();
				if (getUserMB().getUser().isImportadorMaster()) {
					retornaListaComissariaPerfilImportadorMaster();
				}
				if (getUserMB().getUser().isCliente()) {
					retornaListaComissariaPerfilCliente();
				}
				if (getUserMB().getUser().isComissariaMaster()) {
					listaUserComissario = retornaListaComissaria(getUserMB().getUser());
				}
				if (getUserMB().getUser().isDespachante()) {
					executaPermissaoComissariaPerfilDespachante();
				}
			}

		}
		return listaUserComissario;
	}

	public List<CadastroComissario> listaComissario(String pesquisa) throws Exception {
		List<CadastroComissario> listaComissario = new ArrayList<CadastroComissario>();
		if (getEntidadeBL() != null || getEntidadeDTCDTA() != null) {
			boolean verificaPermissao = true;

			if (getUserMB().getUser().isAdmin() || getUserMB().getUser().isClif()) {
				listaComissario = getCadastroComissarioRN().listaTodosAutoComplete(pesquisa);
				verificaPermissao = false;
			}

			if (verificaPermissao) {
				listaComissario = new ArrayList<CadastroComissario>();
				if (getUserMB().getUser().isImportadorMaster()) {
					listaComissario = retornaListaComissariaPerfilImportadorMaster();
				}
				if (getUserMB().getUser().isCliente()) {
					listaComissario = retornaListaComissariaPerfilClienteNovo(pesquisa);
				}
				if (getUserMB().getUser().isComissariaMaster()) {
					listaComissario = retornaListaComissaria(getUserMB().getUser());
				}
				if (getUserMB().getUser().isDespachante()) {
					listaComissario = executaPermissaoComissariaPerfilDespachanteNovo(pesquisa);
				}
			}

		}
		return listaComissario;
	}

	private List<CadastroImportador> criaListaImportadorPerfilDespachante(String pesquisa,
			List<CadastroImportador> listaCadastroCodigoPais) {
		UserComissariaUsuarios userComissaria = getUserComissariaUsuariosRN()
				.retornaUsuarioComissaria(getUserMB().getUser());

		if (userComissaria == null) {
			listaCadastroCodigoPais = getImportadorRN().listaTodosAutoComplete(pesquisa);
		} else {
			UserComissariaAgendamento userMaster = userComissaria.getComissariaMaster();

			listaCadastroCodigoPais = retornaListaImportadorPerfilComissaria(listaCadastroCodigoPais,
					userMaster.getUsuario());
		}
		return listaCadastroCodigoPais;
	}

	private void retornaListaPerfilImportadorMaster(List<CadastroImportador> listaCadastroCodigoPais) {
		List<UserImportador> userImportador = getUserImportadorRN().listaComissariasPorUsuario(getUserMB().getUser());

		for (UserImportador item : userImportador) {
			listaCadastroCodigoPais.add(item.getImportador());
		}
	}

	private List<CadastroImportador> retornaListaImportadorPerfilCliente(String pesquisa,
			List<CadastroImportador> listaCadastroCodigoPais) {
		UserImportadorUsuarios userMaster = getImportadorUsuariosRN()
				.retornaImportadorUsuarioMaster(getUserMB().getUser());

		if (userMaster == null) {
			listaCadastroCodigoPais = getImportadorRN().listaTodosAutoComplete(pesquisa);
		} else {
			List<UserImportador> userImportador = getUserImportadorRN()
					.listaComissariasPorUsuario(userMaster.getImportadorComissaria().getUsuario());

			for (UserImportador item : userImportador) {
				listaCadastroCodigoPais.add(item.getImportador());
			}
		}
		return listaCadastroCodigoPais;
	}

	private List<CadastroImportador> retornaListaImportadorPerfilComissaria(
			List<CadastroImportador> listaCadastroCodigoPais, User usuario) {
		List<UserComissario> list = getUserComissarioRN().listaComissariasPorUsuario(usuario);

		CadastroImportador carregarLista = null;
		for (UserComissario item : list) {
			List<UserImportadorComissaria> listaComissarias = getUserImportadorComissariaRN()
					.retornaComisarias(item.getCadComissario());

			for (UserImportadorComissaria itens : listaComissarias) {
				if (carregarLista == null || !carregarLista.equals(itens.getImportador())) {
					listaCadastroCodigoPais.add(itens.getImportador());
				}
				carregarLista = itens.getImportador();
			}
		}

		return listaCadastroCodigoPais;
	}

	public List<CadastroResponsavelLote> listaResponsavelLote(String pesquisa) {
		List<CadastroResponsavelLote> lista = new ArrayList<CadastroResponsavelLote>();
		if (getEntidadeBL() != null || getEntidadeDTCDTA() != null) {
			boolean verificaPermissao = true;

			if (getUserMB().getUser().isAdmin() || getUserMB().getUser().isClif()) {
				lista = getResponsavelLoteRN().listaTodosAutoComplete(pesquisa);
				verificaPermissao = false;
			}

			if (verificaPermissao) {
				boolean todosNotify = true;
				if (getUserMB().getUser().isImportadorMaster()) {
					lista = criaListaNotifyPerfilImportadorMaster(pesquisa, lista, todosNotify);
				}
				if (getUserMB().getUser().isCliente()) {
					lista = criaListaNotifyImportadorPerfilCliente(pesquisa, lista, todosNotify);
				}
				if (getUserMB().getUser().isComissariaMaster()) {
					lista = retornaListaNotifyComissaria(lista, todosNotify, pesquisa, getUserMB().getUser());
				}
				if (getUserMB().getUser().isDespachante()) {
					lista = criaListaNotifPerfilDespachante(pesquisa, lista, todosNotify);
				}
			}
		}
		return lista;
	}

	private List<CadastroResponsavelLote> criaListaNotifPerfilDespachante(String pesquisa,
			List<CadastroResponsavelLote> lista, boolean todosNotify) {
		UserComissariaUsuarios userComissaria = getUserComissariaUsuariosRN()
				.retornaUsuarioComissaria(getUserMB().getUser());
		if (userComissaria == null) {
			lista = getResponsavelLoteRN().listaTodosAutoComplete(pesquisa);
		} else {
			UserComissariaAgendamento userMaster = userComissaria.getComissariaMaster();

			lista = retornaListaNotifyComissaria(lista, todosNotify, pesquisa, userMaster.getUsuario());
		}
		return lista;
	}

	private List<CadastroResponsavelLote> retornaListaNotifyComissaria(List<CadastroResponsavelLote> lista,
			boolean todosNotify, String pesquisa, User usuario) {

		List<UserComissario> list = getUserComissarioRN().listaComissariasPorUsuario(usuario);
		CadastroResponsavelLote addListaNotify = null;
		for (UserComissario item : list) {
			List<UserImportadorComissaria> listaComissarias = getUserImportadorComissariaRN()
					.retornaComisarias(item.getCadComissario());

			for (UserImportadorComissaria itens : listaComissarias) {
				if (itens.getNotifyRespLote() != null) {
					todosNotify = false;
					if (addListaNotify == null || !addListaNotify.equals(itens.getNotifyRespLote())) {
						lista.add(itens.getNotifyRespLote());
					}
					addListaNotify = itens.getNotifyRespLote();
				}
			}
		}
		if (todosNotify) {
			lista = getResponsavelLoteRN().listaTodosAutoComplete(pesquisa);
		}

		return lista;
	}

	private List<CadastroResponsavelLote> criaListaNotifyImportadorPerfilCliente(String pesquisa,
			List<CadastroResponsavelLote> lista, boolean todosNotify) {
		UserImportadorUsuarios userMaster = getImportadorUsuariosRN()
				.retornaImportadorUsuarioMaster(getUserMB().getUser());

		UserImportadorAgendamento userImpAgen = null;
		if (userMaster != null) {
			userImpAgen = userMaster.getImportadorComissaria();
		}

		lista = retornaListaNotifyImportador(pesquisa, lista, todosNotify, userImpAgen);
		return lista;
	}

	private List<CadastroResponsavelLote> criaListaNotifyPerfilImportadorMaster(String pesquisa,
			List<CadastroResponsavelLote> lista, boolean todosNotify) {
		UserImportadorAgendamento userMaster = getUserImportadorAgendamentoRN()
				.retornaImportadorUsuarioMaster(getUserMB().getUser());

		lista = retornaListaNotifyImportador(pesquisa, lista, todosNotify, userMaster);
		return lista;
	}

	private UserComissariaUsuariosRN getUserComissariaUsuariosRN() {
		if (userComissariaUsuariosRN == null) {
			userComissariaUsuariosRN = new UserComissariaUsuariosRN();
		}

		return userComissariaUsuariosRN;
	}

	private List<CadastroResponsavelLote> retornaListaNotifyImportador(String pesquisa,
			List<CadastroResponsavelLote> lista, boolean todosNotify, UserImportadorAgendamento userMaster) {

		List<UserImportadorComissaria> list = null;
		if (userMaster != null) {
			list = getUserImportadorComissariaRN().listaPorImportadorComiisarias(userMaster);

			for (UserImportadorComissaria item : list) {
				if (item.getNotifyRespLote() != null) {
					todosNotify = false;
					lista.add(item.getNotifyRespLote());
				}
			}
		}
		if (todosNotify) {
			lista = getResponsavelLoteRN().listaTodosAutoComplete(pesquisa);
		}
		return lista;
	}

	public CadastroEspecieRN getCadEspecieRN() {
		if (cadEspecieRN == null) {
			cadEspecieRN = new CadastroEspecieRN();
		}
		return cadEspecieRN;
	}

	public List<CadastroEspecie> listaCadastroEspecie(String pesquisa) {
		List<CadastroEspecie> listaCadastroEspecie = new ArrayList<CadastroEspecie>();
		if (getEntidadeBL() != null || getEntidadeDTCDTA() != null) {
			listaCadastroEspecie = getCadEspecieRN().listaTodosAutoComplete(pesquisa);
		}
		return listaCadastroEspecie;
	}

	public CadastroBL getEntidadeBL() {
		return entidadeBL;
	}

	public void setEntidade(CadastroBL entidade) {
		this.entidadeBL = entidade;
	}

	public UserMB getUserMB() {
		return userMB;
	}

	public void setUserMB(UserMB userMB) {
		this.userMB = userMB;
	}

	public TipoSimNaoEnum getSelecionadoCargaImo() {
		return selecionadoCargaImo;
	}

	public void setSelecionadoCargaImo(TipoSimNaoEnum selecionadoCargaImo) {
		this.selecionadoCargaImo = selecionadoCargaImo;
	}

	public CadastroImportador getImportadorSelecionado() {
		return importadorSelecionado;
	}

	public void setImportadorSelecionado(CadastroImportador importadorSelecionado) {
		this.importadorSelecionado = importadorSelecionado;
	}

	public CadastroDTCDTA getEntidadeDTCDTA() {
		return entidadeDTCDTA;
	}

	public void setEntidadeDTCDTA(CadastroDTCDTA entidadeDTCDTA) {
		this.entidadeDTCDTA = entidadeDTCDTA;
	}

	public CadastroImportadorRN getImportadorRN() {
		if (importadorRN == null) {
			importadorRN = new CadastroImportadorRN();
		}
		return importadorRN;
	}

	public CadastroNcm getNcmSelecionado() {
		return ncmSelecionado;
	}

	public void setNcmSelecionado(CadastroNcm ncmSelecionado) {
		this.ncmSelecionado = ncmSelecionado;
	}

	public List<CadastroNcm> listaCadastroNCM(String pesquisa) {
		List<CadastroNcm> listaCadastroNCM = new ArrayList<CadastroNcm>();
		if (getEntidadeBL() != null || getEntidadeDTCDTA() != null) {
			listaCadastroNCM = getCadNcmRN().listaTodosAutoComplete(pesquisa);
		}
		return listaCadastroNCM;
	}

	public CadastroNcmRN getCadNcmRN() {
		if (cadNcmRN == null) {
			cadNcmRN = new CadastroNcmRN();
		}
		return cadNcmRN;
	}

	public CadastroCodigoPais getPaisSelecionado() {
		return paisSelecionado;
	}

	public void setPaisSelecionado(CadastroCodigoPais paisSelecionado) {
		this.paisSelecionado = paisSelecionado;
	}

	public CadastroCodigoPais getPaisProcedenciaSelecionado() {
		return paisProcedenciaSelecionado;
	}

	public void setPaisProcedenciaSelecionado(CadastroCodigoPais paisProcedenciaSelecionado) {
		this.paisProcedenciaSelecionado = paisProcedenciaSelecionado;
	}

	public CadastroCodigoPaisRN getCadCodigoPaisRN() {
		if (cadCodigoPaisRN == null) {
			cadCodigoPaisRN = new CadastroCodigoPaisRN();
		}
		return cadCodigoPaisRN;
	}

	public List<CadastroCodigoPais> listaCodigoPais(String pesquisa) {
		List<CadastroCodigoPais> listaCadastroCodigoPais = new ArrayList<CadastroCodigoPais>();
		if (getEntidadeBL() != null || getEntidadeDTCDTA() != null) {
			listaCadastroCodigoPais = getCadCodigoPaisRN().listaTodosAutoComplete(pesquisa);
		}
		return listaCadastroCodigoPais;
	}

	public List<ModalidadeBLEnum> getTodasModalidadeBLEnum() {
		if (todasModalidadeBLEnum == null && isControlarFormCadastrar()) {
			todasModalidadeBLEnum = new ArrayList<ModalidadeBLEnum>();
			todasModalidadeBLEnum = new ArrayList<ModalidadeBLEnum>(Arrays.asList(ModalidadeBLEnum.values()));
		}
		return todasModalidadeBLEnum;
	}

	public List<TipoContainerEnum> getTodosTiposContainerEnum() {
		if (todasTipoContainerEnum == null && isControlarFormCadastrar()) {
			todasTipoContainerEnum = new ArrayList<TipoContainerEnum>();
			todasTipoContainerEnum = new ArrayList<TipoContainerEnum>(Arrays.asList(TipoContainerEnum.values()));
		}
		return todasTipoContainerEnum;
	}

	public CadastroPortos getPortoSelecionado() {
		return portoSelecionado;
	}

	public void setPortoSelecionado(CadastroPortos portoSelecionado) {
		this.portoSelecionado = portoSelecionado;
	}

	public CadastroPortosRN getPortosRN() {
		if (portosRN == null) {
			portosRN = new CadastroPortosRN();
		}
		return portosRN;
	}

	public List<CadastroPortos> listaPortos(String pesquisa) {
		List<CadastroPortos> listaCadastroCodigoPais = new ArrayList<CadastroPortos>();
		if (getEntidadeBL() != null || getEntidadeDTCDTA() != null) {
			listaCadastroCodigoPais = getPortosRN().listaTodosAutoComplete(pesquisa);
		}
		return listaCadastroCodigoPais;
	}

	public boolean mostrarInformacaoTemperatura() {
		boolean valor = true;

		if (getEntidadeDTCDTA() != null) {
			if (getEntidadeDTCDTA().getCargaReefer() == TipoSimNaoEnum.SIM) {
				valor = false;
			}
		}

		return valor;
	}

	public void atualizaProgramacaoNavio() throws Throwable {
		LeituraArquivoRN leituraArquivoRN = new LeituraArquivoRN();
		leituraArquivoRN.downloadProgramacaoNavio();
	}

	public CadastroRepresentante getRepresentanteSelecionado() {
		return representanteSelecionado;
	}

	public void setRepresentanteSelecionado(CadastroRepresentante representanteSelecionado) {
		this.representanteSelecionado = representanteSelecionado;
	}

	public CadastroRepresentanteRN getRepresentanteRN() {
		if (representanteRN == null) {
			representanteRN = new CadastroRepresentanteRN();
		}
		return representanteRN;
	}

	public List<CadastroRepresentante> listaRepresentante(String pesquisa) {
		List<CadastroRepresentante> listaCadastroCodigoPais = new ArrayList<CadastroRepresentante>();
		if (getEntidadeBL() != null || getEntidadeDTCDTA() != null) {
			listaCadastroCodigoPais = getRepresentanteRN().listaTodosAutoComplete(pesquisa);
		}
		return listaCadastroCodigoPais;
	}

	public void salvarImportadorComissaria() {
		try {
			salvarComissaria();

			salvarRepresentante();

			salvarResponsavelLote();

			salvarImportador();

			salvarFaturamentoContra();

			RequestContext.getCurrentInstance().execute("PF('dlgBLIncluir').hide()");
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	private void salvarImportador() throws Exception {
		if (getEntidadeImportador() != null) {
			getEntidadeImportador().setCnpj(getCnpj());
			getEntidadeImportador().setRazaoSocial(getRazaoSocial());
			getEntidadeImportador().setEmail(getEmail());

			getImportadorRN().alterar(getEntidadeImportador());
			setEntidadeImportador(null);
			listaImportador = null;
		}
	}

	private void salvarFaturamentoContra() throws Exception {
		if (getEntidadeFaturarContra() != null) {
			getEntidadeFaturarContra().setCnpj(getCnpj());
			getEntidadeFaturarContra().setRazaoSocial(getRazaoSocial());
			getEntidadeFaturarContra().setEmail(getEmail());

			getFaturarContraRN().alterar(getEntidadeFaturarContra());
			setEntidadeFaturarContra(null);
		}
	}

	private void salvarResponsavelLote() throws Exception {
		if (getEntidadeResponsavelLote() != null) {
			getEntidadeResponsavelLote().setCnpj(getCnpj());
			getEntidadeResponsavelLote().setRazaoSocial(getRazaoSocial());
			getEntidadeResponsavelLote().setEmail(getEmail());

			getResponsavelLoteRN().alterar(getEntidadeResponsavelLote());
			setEntidadeResponsavelLote(null);
			listaRepresentante = null;
		}
	}

	private void salvarRepresentante() throws Exception {
		if (getEntidadeRepresentante() != null) {
			getEntidadeRepresentante().setCnpj(getCnpj());
			getEntidadeRepresentante().setRazaoSocial(getRazaoSocial());
			getEntidadeRepresentante().setEmail(getEmail());

			getRepresentanteRN().alterar(getEntidadeRepresentante());
			setEntidadeRepresentante(null);
			listaRepresentante = null;
		}
	}

	private void salvarComissaria() {
		try {

			if (getEntidadeCamissaria() != null) {
				getEntidadeCamissaria().setCpf(getCnpj());
				getEntidadeCamissaria().setNome(getRazaoSocial());
				getEntidadeCamissaria().setTipoPessoaEnum(TipoPessoaFisicaJuridicaEnum.PESSOA_JURIDICA);
				getEntidadeCamissaria().setEmail(getEmail());

				CadastroComissario novoComissaria = getCadastroComissarioRN().alterar(getEntidadeCamissaria());

				setUserComissario(new UserComissario());
				getUserComissario().setUsuario(getUserMB().getUser());
				getUserComissario().setCadComissario(novoComissaria);
				getUserComissarioRN().alterar(getUserComissario());

				listaUserComissario = null;
				setEntidadeCamissaria(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public UserComissario getUserComissario() {
		return userComissario;
	}

	public void setUserComissario(UserComissario userComissario) {
		this.userComissario = userComissario;
	}

	public CadastroEspecie getEmbalagemEncontradaSelecionado() {
		return embalagemEncontradaSelecionado;
	}

	public void setEmbalagemEncontradaSelecionado(CadastroEspecie embalagemEncontradaSelecionado) {
		this.embalagemEncontradaSelecionado = embalagemEncontradaSelecionado;
	}

	public boolean isControlarAnexos() {
		return controlarAnexos;
	}

	public void setControlarAnexos(boolean controlarAnexos) {
		this.controlarAnexos = controlarAnexos;
	}

	public TipoAnexosEnum getSelecionadoTipoAnexos() {
		return selecionadoTipoAnexos;
	}

	public void setSelecionadoTipoAnexos(TipoAnexosEnum selecionadoTipoAnexos) {
		this.selecionadoTipoAnexos = selecionadoTipoAnexos;
	}

	public void fecharAnexo() {
		setControlarAnexos(false);
	}

	public String getRetornoBL() {
		return retornoBL;
	}

	public void setRetornoBL(String retornoBL) {
		this.retornoBL = retornoBL;
	}

	public String getRetornoCE() {
		return retornoCE;
	}

	public void setRetornoCE(String retornoCE) {
		this.retornoCE = retornoCE;
	}

	public String getRetornoDTC_DTA() {
		return retornoDTC_DTA;
	}

	public void setRetornoDTC_DTA(String retornoDTC_DTA) {
		this.retornoDTC_DTA = retornoDTC_DTA;
	}

	public String getRetornoINVOICE() {
		return retornoINVOICE;
	}

	public void setRetornoINVOICE(String retornoINVOICE) {
		this.retornoINVOICE = retornoINVOICE;
	}

	public String getRetornoPACKING() {
		return retornoPACKING;
	}

	public void setRetornoPACKING(String retornoPACKING) {
		this.retornoPACKING = retornoPACKING;
	}

	public String getMascaraCnpjCpf() {
		return mascaraCnpjCpf;
	}

	public void setMascaraCnpjCpf(String mascaraCnpjCpf) {
		this.mascaraCnpjCpf = mascaraCnpjCpf;
	}

	public CadastroResponsavelLote getResponsavelLoteSelecionado() {
		return responsavelLoteSelecionado;
	}

	public void setResponsavelLoteSelecionado(CadastroResponsavelLote responsavelLoteSelecionado) {
		this.responsavelLoteSelecionado = responsavelLoteSelecionado;
	}

	public CadastroFaturarContra getFaturarContraSelecionado() {
		return faturarContraSelecionado;
	}

	public void setFaturarContraSelecionado(CadastroFaturarContra faturarContraSelecionado) {
		this.faturarContraSelecionado = faturarContraSelecionado;
	}

	public CadastroResponsavelLote getEntidadeResponsavelLote() {
		return entidadeResponsavelLote;
	}

	public void setEntidadeResponsavelLote(CadastroResponsavelLote entidadeResponsavelLote) {
		this.entidadeResponsavelLote = entidadeResponsavelLote;
	}

	public CadastroFaturarContra getEntidadeFaturarContra() {
		return entidadeFaturarContra;
	}

	public void setEntidadeFaturarContra(CadastroFaturarContra entidadeFaturarContra) {
		this.entidadeFaturarContra = entidadeFaturarContra;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isInformarEmail() {
		return informarEmail;
	}

	public void setInformarEmail(boolean informarEmail) {
		this.informarEmail = informarEmail;
	}

	public ProgramacaoNavio getEntidadeProgramacaoNavio() {
		return entidadeProgramacaoNavio;
	}

	public void setEntidadeProgramacaoNavio(ProgramacaoNavio entidadeProgramacaoNavio) {
		this.entidadeProgramacaoNavio = entidadeProgramacaoNavio;
	}

	public ProgramacaoNavio getProgramacaoNavioSelecionado() {
		return programacaoNavioSelecionado;
	}

	public void setProgramacaoNavioSelecionado(ProgramacaoNavio programacaoNavioSelecionado) {
		this.programacaoNavioSelecionado = programacaoNavioSelecionado;
	}

	public boolean validarNavioViagemAtracacao() {
		boolean valor = false;
		setBloquearNavio(false);
		if (getEntidadeDTCDTA() != null) {
			if (getEntidadeDTCDTA().getTipoModalEnum() != TipomodalEnum.DTC) {
				setBloquearNavio(true);
			}
			if (getEntidadeDTCDTA().getTipoModalEnum() == TipomodalEnum.DTA_AEREO
					|| getEntidadeDTCDTA().getTipoModalEnum() == TipomodalEnum.DTA_MIC) {
				valor = true;
			}
		}

		return valor;
	}

	public boolean validarTipoContainer() {
		boolean valor = false;
		if (getEntidadeDTCDTA() != null) {
			if (getEntidadeDTCDTA().getTipoModalEnum() != TipomodalEnum.DTC) {
				valor = true;
			}

		}

		return valor;
	}

	public CadastroComissario getComissarioSelecionado() {
		return comissarioSelecionado;
	}

	public void setComissarioSelecionado(CadastroComissario comissarioSelecionado) {
		this.comissarioSelecionado = comissarioSelecionado;
	}

	public List<CadastroBLAnexos> getListaAnexosPorTipo() {
		return listaAnexosPorTipo;
	}

	public void setListaAnexosPorTipo(List<CadastroBLAnexos> listaAnexosPorTipo) {
		this.listaAnexosPorTipo = listaAnexosPorTipo;
	}

	public String getRetornoCorBL() {
		return retornoCorBL;
	}

	public void setRetornoCorBL(String retornoCorBL) {
		this.retornoCorBL = retornoCorBL;
	}

	public String getRetornoCorCE() {
		return retornoCorCE;
	}

	public void setRetornoCorCE(String retornoCorCE) {
		this.retornoCorCE = retornoCorCE;
	}

	public String getRetornoCorDTC_DTA() {
		return retornoCorDTC_DTA;
	}

	public void setRetornoCorDTC_DTA(String retornoCorDTC_DTA) {
		this.retornoCorDTC_DTA = retornoCorDTC_DTA;
	}

	public String getRetornoCorINVOICE() {
		return retornoCorINVOICE;
	}

	public void setRetornoCorINVOICE(String retornoCorINVOICE) {
		this.retornoCorINVOICE = retornoCorINVOICE;
	}

	public String getRetornoCorPACKING() {
		return retornoCorPACKING;
	}

	public void setRetornoCorPACKING(String retornoCorPACKING) {
		this.retornoCorPACKING = retornoCorPACKING;
	}

	public boolean isMostrarEmail() {
		return mostrarEmail;
	}

	public void setMostrarEmail(boolean mostrarEmail) {
		this.mostrarEmail = mostrarEmail;
	}

	public boolean isBloquearNavio() {
		return bloquearNavio;
	}

	public void setBloquearNavio(boolean bloquearNavio) {
		this.bloquearNavio = bloquearNavio;
	}

	public String anuenciaMapaSimNao(CadastroDTCDTA item) {
		String valor = "Não";

		CadastroBL bl = item.getCadastroBL();

		if (bl != null) {
			if (bl.isAnuenciaMapa()) {
				valor = "Sim";
			}
			if (bl != null && bl.getNcm() != null) {
				if (bl.getNcm().isProdutoAnuencia()) {
					valor = "Sim";
				}
			}
		}
		return valor;
	}

	public void excluirNCM(MapaNcm entidade) {
		try {
			if (entidade.getId() != null) {
				getMapaNcmRN().deletar(entidade);
				getEntidadeDTCDTA().getCadastroBL().getListaMapaNcm().remove(entidade);
				listaMapaNCM = null;
			} else {
				getListaMapaNCM().remove(entidade);
			}

			getListaMapaNCM();
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public void incluirNCM() {
		try {
			if (getNcmSelecionado() != null) {
				MapaNcm mapaNcm = new MapaNcm();
				mapaNcm.setNcm(getNcmSelecionado());

				if (getEntidadeDTCDTA().getId() != null) {
					CadastroBL bl = getEntidadeDTCDTA().getCadastroBL();

					mapaNcm.setCadastroBL(bl);
					mapaNcm = getMapaNcmRN().alterar(mapaNcm);

					getEntidadeDTCDTA().getCadastroBL().getListaMapaNcm().add(mapaNcm);

					listaMapaNCM = null;
					getListaMapaNCM();

					// bl.setAnuenciaMapa(getNcmSelecionado().isProdutoAnuencia());
					// getCadastroBLRN().salvar(bl);
				} else {
					getListaMapaNCM().add(mapaNcm);
				}

				JSFMessageUtil.adicionarMensagemSucesso("NCM Vinculado!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	private CadastroBLRN getCadastroBLRN() {
		if (cadastroBLRN == null) {
			cadastroBLRN = new CadastroBLRN();
		}
		return cadastroBLRN;
	}

	public void carregarNCM() {
		if (getEntidadeDTCDTA() != null) {
			if (getEntidadeDTCDTA().getId() != null) {
				listaMapaNCM = null;
			}
		}
		getListaMapaNCM();
	}

	public List<MapaNcm> getListaMapaNCM() {
		if (listaMapaNCM == null) {
			if (getEntidadeDTCDTA() != null) {
				if (getEntidadeDTCDTA().getId() != null) {
					listaMapaNCM = getEntidadeDTCDTA().getCadastroBL().getListaMapaNcm();
				} else {
					listaMapaNCM = new ArrayList<MapaNcm>();
				}
			}
		}
		return listaMapaNCM;
	}

	public void setListaMapaNCM(List<MapaNcm> listaMapaNCM) {
		this.listaMapaNCM = listaMapaNCM;
	}

}
