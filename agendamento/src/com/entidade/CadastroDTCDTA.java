package com.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;

import seguranca.com.entidade.CadastroBL;
import seguranca.com.entidade.CadastroBLContanierLCL;
import seguranca.com.entidade.CadastroCodigoPais;
import seguranca.com.entidade.CadastroComissario;
import seguranca.com.entidade.CadastroEspecie;
import seguranca.com.entidade.CadastroFaturarContra;
import seguranca.com.entidade.CadastroImportador;
import seguranca.com.entidade.CadastroNcm;
import seguranca.com.entidade.CadastroPortos;
import seguranca.com.entidade.CadastroRepresentante;
import seguranca.com.entidade.CadastroResponsavelLote;
import seguranca.com.entidade.ICamposComunsBLDtcDta;
import seguranca.com.entidade.ProgramacaoNavio;
import seguranca.com.entidade.User;
import seguranca.com.enums.ModalidadeBLEnum;
import seguranca.com.enums.StatusAtiLclFclEnum;
import seguranca.com.enums.StatusBLEnum;
import seguranca.com.enums.TipoAnexosEnum;
import seguranca.com.enums.TipoContainerEnum;
import seguranca.com.enums.TipoSimNaoEnum;
import seguranca.com.enums.TipomodalEnum;

@Entity
@Table(name = "AGEN_CAD_DTCDTA")
@Audited
public class CadastroDTCDTA implements ICamposComunsBLDtcDta {
	public final static String sql = "select p from CadastroDTCDTA p ";
	public final static String sqlCount = "select COUNT(p) from CadastroDTCDTA p ";
	public final static String UPDATE_ATUALIZA_DADOS = "update CadastroDTCDTA p "
			+ "set statusBLEnum = :statusBLEnum where p.id = :id";

	public final static String UPDATE_ATUALIZA_PROCESSOS_BLOQUEADOS = "update CadastroDTCDTA p "
			+ "set processoBloqueado = null where p.processoBloqueado is not null";

	public final static String CAMPO_ID = "id";
	public final static String CADASTRO_BL_DESCRICAOBL = "descricaoBL";
	public final static String CADASTRO_BL_ID = "id";
	public final static String CADASTRO_CONTAINER = "container";
	public final static String CADASTRO_COMISSARIO = "cadComissario";
	public final static String CADASTRO_PAIS_ORIGEM_DESCRICAO = "paisOrigem.descricaoPais";
	public final static String CADASTRO_PAIS_PROCEDENCIA_DESCRICAO = "paisProcedencia.descricaoPais";
	public final static String CADASTRO_ESPECIE_DESCRICAO = "tipoEmbalagemEspecie.descricao";
	public final static String CADASTRO_NCM_DESCRICAO = "ncm.descricaoNcm";
	public final static String CADASTRO_DESCRICAOMERCADORIA = "descricaoMercadoria";
	public final static String CADASTRO_STATUS = "statusBLEnum";
	public final static String CAMPO_MODALIDADEBLENUM = "modalidadeBLEnum";
	public final static String CAMPO_STATUSATILCLFCLENUM = "statusAtiLclFclEnum";
	public final static String CAMPO_NUMEROATI = "numeroATI";
	public final static String CAMPO_NAVIOVIAGEM = "programacaoNavio.navio";
	public final static String CAMPO_VIAGEM = "programacaoNavio.navioViagem";
	public final static String CAMPO_DATAATRACACAO = "dataAtracacao";
	public final static String CAMPO_IMPORTADOR = "importador.razaoSocial";
	public final static String CAMPO_IMPORTADOR_FILTRO = "importador";
	public final static String CAMPO_NCM_FILTRO = "ncm";
	public final static String CAMPO_PAISORIGEM_FILTRO = "paisOrigem";
	public final static String CAMPO_RESPONSAVELLOTE_FILTRO = "responsavelLote";
	public final static String CAMPO_RESPONSAVEL_LOTE = "responsavelLote.razaoSocial";
	public final static String CAMPO_FATURA_CONTRA = "faturarContra.razaoSocial";
	public final static String CAMPO_CADASTROBL = "cadastroBL";
	public final static String CAMPO_TIPO_MODAL = "tipoModalEnum";
	public final static String CAMPO_IMPORTADOR_LCL = "importadorLCL";
	public final static String CAMPO_BL_MASTER_FILTRO = "containerLCL.numeroBLMaster";
	public final static String CAMPO_LCL = "containerLCL";

	public static final String FIND_BY_ENTIDADE = "CadastroDTCDTA.descricaoBL";
	public static final String FILTRO_CADASTRO_BL_DESCRICAOBL = "select count(u) from CadastroDTCDTA u where u.descricaoBL = :descricaoBL";
	public static final String LOCALIZAR_CADASTRO_DTC_DTA = "select u from CadastroDTCDTA u where u.cadastroBL.id = :cadastroBL";

	public static final String LOCALIZAR_REGISTRO_RISCO_FITOSANITARIO = "select max(id) from CadastroDTCDTA u where u.paisOrigem.id = :paisOrigem and u.ncm.id = :ncm and u.importador.id = :importador and u.id <> :id";

	@Id
	@SequenceGenerator(name = "SEQ_DTCDTA_ID", sequenceName = "SEQ_DTCDTA_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_DTCDTA_ID")
	@Column(name = "DTCDTA_ID")
	private Integer id;

	@Column(name = "DATA_CADASTRO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;

	@Column(name = "DATA_ENVIOMAPA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEnvioMapa;

	@Column(name = "DATA_ATRACACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAtracacao;

	@Column(name = "DESCRICAO_BL", length = 350)
	private String descricaoBL;

	@Column(name = "NUMERO_PROCESSO", length = 350)
	private String numeroProcesso;

	@Column(name = "OBSERVACAO", length = 350)
	private String observacao;

	@Column(name = "AGENTE_NOME", length = 350)
	private String agenteNome;

	@Column(name = "AGENTE_CNPJ", length = 350)
	private String agenteCnpj;

	@Column(name = "SEGREGACAO_CEMASTER", length = 350)
	private String segregacaoCeMaster;

	@Column(name = "NUMERO_BLMASTER", length = 350)
	private String numeroBlMaster;
	
	@Column(name = "SEGREGACAO_CEHOUSE", length = 350)
	private String segregacaoCeHouse;

	@Column(name = "ADQUIRENTE_NOME", length = 350)
	private String adquirenteNome;

	@Column(name = "OBSERVACAO_INVOICE", length = 350)
	private String retiradaObservacaoInvoice;

	@Column(name = "ADQUIRENTE_CNPJ", length = 350)
	private String adquirenteCNPJ;

	@Column(name = "ADQUIRENTE_BLHOUSE", length = 350)
	private String adquirenteBlHouse;

	@Column(name = "MOTIVO_CANCELAMENTO", length = 500)
	private String motivoCancelamento;

	@Column(name = "DESCRICAOMERCADORIA", length = 350)
	private String descricaoMercadoria;

	@Column(name = "ARMADOR", length = 350)
	private String armador;

	@Column(name = "NAVIOVIAGEM", length = 350)
	private String navioViagem;

	@Column(name = "VIAGEM", length = 350)
	private String viagem;

	@Column(name = "REFERENCIA_CLIENTE", length = 350)
	private String referenciaCliente;

	@Column(name = "NUMERO_ATI", length = 150)
	private String numeroATI;

	@Column(name = "QUANTIDADE")
	private Float quantidade;

	@Column(name = "CIF_MERCADORIA")
	private Float cifMercadoria;

	@Column(name = "FREE_TIME")
	private Integer freeTime;

	@Column(name = "TEMPERATURA")
	private Integer temperatura;

	@Column(name = "TEMPERATURA_STRING", length = 15)
	private String temperaturaString;

	@Column(name = "STATUS_BL")
	private StatusBLEnum statusBLEnum;

	@Column(name = "MODALIDADE_BL")
	private ModalidadeBLEnum modalidadeBLEnum;

	@Column(name = "STATUS_ATI", columnDefinition = "int default 0")
	private StatusAtiLclFclEnum statusAtiLclFclEnum;

	@Column(name = "STATUS_ANEXO", columnDefinition = "int default 0")
	private TipoAnexosEnum tipoAnexosStatusEnum;

	@Column(name = "INSPECAO", columnDefinition = "int default 0")
	private boolean inspecao;

	@Column(name = "REUNITIZAR", columnDefinition = "int default 0")
	private boolean reunitizar;

	@Column(name = "DESUNITIZACAO", columnDefinition = "int default 0")
	private boolean desunitizacao;

	@Column(name = "ENVIAR_PARA_MAPA", columnDefinition = "int default 0")
	private boolean enviarParaMapa;

	@Column(name = "DEFERIDO", columnDefinition = "int default 0")
	private boolean deferido;

	@Column(name = "LIBERADO", columnDefinition = "int default 0")
	private boolean liberado;

	@Column(name = "TIPO_CONTAINER")
	private TipoContainerEnum tipoContainerEnum;

	@Column(name = "CARGA_IMO")
	private TipoSimNaoEnum cargaImo;

	@Column(name = "CARGA_REEFER")
	private TipoSimNaoEnum cargaReefer;

	@Column(name = "CARGA_MADEIRA")
	private TipoSimNaoEnum cargaComMadeira;

	@Column(name = "ENTREPOSTO")
	private TipoSimNaoEnum entrePosto;

	@Column(name = "TIPO_MODAL")
	private TipomodalEnum tipoModalEnum;

	@ManyToOne
	@JoinColumn(name = "PAISORIGEM_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_DTC_DTA_PAISORIGEM_ID")
	private CadastroCodigoPais paisOrigem;

	@ManyToOne
	@JoinColumn(name = "PROGRAMACAONAVIO_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_DTC_PROGRAMACAONAVIO_ID")
	private ProgramacaoNavio programacaoNavio;

	@ManyToOne
	@JoinColumn(name = "FATURAR_CONTRA_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_DTC_FATURARCONTRA_ID")
	private CadastroFaturarContra faturarContra;

	@Column(name = "FATURAR_CONTRA_EMAIL", length = 850)
	private String faturarContraEmail;

	@ManyToOne
	@JoinColumn(name = "RESPONSAVEL_LOTE_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_DTC_RESPONSAVELLOTEID")
	private CadastroResponsavelLote responsavelLote;

	@ManyToOne
	@JoinColumn(name = "CADASTROBL_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_DTC_DTA_BL_ID")
	private CadastroBL cadastroBL;

	@ManyToOne
	@JoinColumn(name = "LCL_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_DTCDTA_LCL_ID")
	private CadastroBLContanierLCL containerLCL;

	@ManyToOne
	@JoinColumn(name = "PAISPROCEDENCIA_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_DTC_DTA_PAISPROCEDENCIA_ID")
	private CadastroCodigoPais paisProcedencia;

	// @ManyToOne
	// @JoinColumn(name = "USERS_COMISSARIO_ID", nullable = true, insertable =
	// true, updatable = true)
	// @ForeignKey(name = "FK_DTC_DTA__USERCOMISSARIO")
	// private UserComissario comissario;

	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_DTC_DTA__USER")
	private User processoBloqueado;

	@ManyToOne
	@JoinColumn(name = "USER_CADASTRO_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_DTCDTA_USER")
	private User userCadastroDtc;

	@ManyToOne
	@JoinColumn(name = "COMISSARIO_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_DTC_DTA_COMISSARIO")
	private CadastroComissario cadComissario;

	@ManyToOne
	@JoinColumn(name = "ESPECIE_EMBALAGEM_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_DTC_DTA_ESPECIE_ID")
	private CadastroEspecie tipoEmbalagemEspecie;

	@ManyToOne
	@JoinColumn(name = "EMBALAGEM_ENCONTRADA_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_DTC_DTA_ENCONTRADA_ID")
	private CadastroEspecie tipoEmbalagemEncontrada;

	@ManyToOne
	@JoinColumn(name = "NCM_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_DTC_DTA_NCM_ID")
	@Index(name = "I_NCM")
	private CadastroNcm ncm;

	@ManyToOne
	@JoinColumn(name = "PORTOS_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_DTC_PORTOS_ID")
	@Index(name = "I_PORTOS")
	private CadastroPortos portos;

	@ManyToOne
	@JoinColumn(name = "IMPORTADOR_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_DTC_DTA_IMP_ID")
	private CadastroImportador importador;

	@ManyToOne
	@JoinColumn(name = "IMPORTADOR_LCL_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_DTCDTA_IMP_LCL_ID")
	private CadastroImportador importadorLCL;

	@ManyToOne
	@JoinColumn(name = "REPRESENTANTE_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_DTC_REPRESENTANTE_ID")
	@Index(name = "I_REPRESENTANTE")
	private CadastroRepresentante representante;

	public String getDeferimentoSimNao() {
		String valor = "Não";

		if (deferido) {
			valor = "Sim";
		}

		return valor;
	}

	@Transient
	private boolean envioMapaValidar;

	@Transient
	private User userLogado;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String getDescricaoBL() {
		return descricaoBL;
	}

	public void setDescricaoBL(String descricaoBL) {
		this.descricaoBL = descricaoBL;
	}

	@Override
	public String getDescricaoMercadoria() {
		return descricaoMercadoria;
	}

	public void setDescricaoMercadoria(String descricaoMercadoria) {
		this.descricaoMercadoria = descricaoMercadoria;
	}

	@Override
	public TipoContainerEnum getTipoContainerEnum() {
		return tipoContainerEnum;
	}

	public void setTipoContainerEnum(TipoContainerEnum tipoContainerEnum) {
		this.tipoContainerEnum = tipoContainerEnum;
	}

	@Override
	public CadastroCodigoPais getPaisOrigem() {
		return paisOrigem;
	}

	public void setPaisOrigem(CadastroCodigoPais paisOrigem) {
		this.paisOrigem = paisOrigem;
	}

	@Override
	public CadastroCodigoPais getPaisProcedencia() {
		return paisProcedencia;
	}

	public void setPaisProcedencia(CadastroCodigoPais paisProcedencia) {
		this.paisProcedencia = paisProcedencia;
	}

	@Override
	public CadastroEspecie getTipoEmbalagemEspecie() {
		return tipoEmbalagemEspecie;
	}

	public void setTipoEmbalagemEspecie(CadastroEspecie tipoEmbalagemEspecie) {
		this.tipoEmbalagemEspecie = tipoEmbalagemEspecie;
	}

	@Override
	public CadastroNcm getNcm() {
		return ncm;
	}

	public void setNcm(CadastroNcm ncm) {
		this.ncm = ncm;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CadastroDTCDTA) {
			CadastroDTCDTA item = (CadastroDTCDTA) obj;
			return item.getId() == id;
		}

		return false;
	}

	@Override
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	// public UserComissario getComissario() {
	// return comissario;
	// }
	//
	// public void setComissario(UserComissario comissario) {
	// this.comissario = comissario;
	// }

	@Override
	public boolean isLiberado() {
		return liberado;
	}

	public void setLiberado(boolean liberado) {
		this.liberado = liberado;
	}

	@Override
	public boolean isInspecao() {
		return inspecao;
	}

	public void setInspecao(boolean inspecao) {
		this.inspecao = inspecao;
	}

	@Override
	public StatusBLEnum getStatusBLEnum() {
		return statusBLEnum;
	}

	public void setStatusBLEnum(StatusBLEnum statusBLEnum) {
		this.statusBLEnum = statusBLEnum;
	}

	@Override
	public CadastroEspecie getTipoEmbalagemEncontrada() {
		return tipoEmbalagemEncontrada;
	}

	public void setTipoEmbalagemEncontrada(CadastroEspecie tipoEmbalagemEncontrada) {
		this.tipoEmbalagemEncontrada = tipoEmbalagemEncontrada;
	}

	@Override
	public TipomodalEnum getTipoModalEnum() {
		return tipoModalEnum;
	}

	public void setTipoModalEnum(TipomodalEnum tipoModalEnum) {
		this.tipoModalEnum = tipoModalEnum;
	}

	@Override
	public CadastroImportador getImportador() {
		return importador;
	}

	public void setImportador(CadastroImportador importador) {
		this.importador = importador;
	}

	@Override
	public CadastroComissario getCadComissario() {
		return cadComissario;
	}

	public void setCadComissario(CadastroComissario cadComissario) {
		this.cadComissario = cadComissario;
	}

	@Override
	public ModalidadeBLEnum getModalidadeBLEnum() {
		return modalidadeBLEnum;
	}

	public void setModalidadeBLEnum(ModalidadeBLEnum modalidadeBLEnum) {
		this.modalidadeBLEnum = modalidadeBLEnum;
	}

	@Override
	public boolean isDeferido() {
		return deferido;
	}

	public void setDeferido(boolean deferido) {
		this.deferido = deferido;
	}

	@Override
	public TipoSimNaoEnum getCargaImo() {
		return cargaImo;
	}

	public void setCargaImo(TipoSimNaoEnum cargaImo) {
		this.cargaImo = cargaImo;
	}

	@Override
	public TipoSimNaoEnum getCargaReefer() {
		return cargaReefer;
	}

	public void setCargaReefer(TipoSimNaoEnum cargaReefer) {
		this.cargaReefer = cargaReefer;
	}

	@Override
	public TipoSimNaoEnum getCargaComMadeira() {
		return cargaComMadeira;
	}

	public void setCargaComMadeira(TipoSimNaoEnum cargaComMadeira) {
		this.cargaComMadeira = cargaComMadeira;
	}

	@Override
	public TipoSimNaoEnum getEntrePosto() {
		return entrePosto;
	}

	public void setEntrePosto(TipoSimNaoEnum entrePosto) {
		this.entrePosto = entrePosto;
	}

	public CadastroBL getCadastroBL() {
		return cadastroBL;
	}

	public void setCadastroBL(CadastroBL cadastroBL) {
		this.cadastroBL = cadastroBL;
	}

	@Override
	public String getArmador() {
		return armador;
	}

	public void setArmador(String armador) {
		this.armador = armador;
	}

	@Override
	public Date getDataAtracacao() {
		return dataAtracacao;
	}

	@Override
	public String getNavioViagem() {
		return navioViagem;
	}

	public void setDataAtracacao(Date dataAtracacao) {
		this.dataAtracacao = dataAtracacao;
	}

	public void setNavioViagem(String navioViagem) {
		this.navioViagem = navioViagem;
	}

	@Override
	public Float getQuantidade() {
		return quantidade;
	}

	@Override
	public Float getCifMercadoria() {
		return cifMercadoria;
	}

	public void setQuantidade(Float quantidade) {
		this.quantidade = quantidade;
	}

	public void setCifMercadoria(Float cifMercadoria) {
		this.cifMercadoria = cifMercadoria;
	}

	@Override
	public String getReferenciaCliente() {
		return referenciaCliente;
	}

	public void setReferenciaCliente(String referenciaCliente) {
		this.referenciaCliente = referenciaCliente;
	}

	@Override
	public Integer getFreeTime() {
		return freeTime;
	}

	public void setFreeTime(Integer freeTime) {
		this.freeTime = freeTime;
	}

	@Override
	public boolean isDesunitizacao() {
		return desunitizacao;
	}

	@Override
	public boolean isReunitizar() {
		return reunitizar;
	}

	public void setReunitizar(boolean reunitizar) {
		this.reunitizar = reunitizar;
	}

	public void setDesunitizacao(boolean desunitizacao) {
		this.desunitizacao = desunitizacao;
	}

	@Override
	public CadastroPortos getPortos() {
		return portos;
	}

	public void setPortos(CadastroPortos portos) {
		this.portos = portos;
	}

	@Override
	public CadastroRepresentante getRepresentante() {
		return representante;
	}

	public void setRepresentante(CadastroRepresentante representante) {
		this.representante = representante;
	}

	@Override
	public String getNumeroATI() {
		return numeroATI;
	}

	public void setNumeroATI(String numeroATI) {
		this.numeroATI = numeroATI;
	}

	@Override
	public Integer getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(Integer temperatura) {
		this.temperatura = temperatura;
	}

	public CadastroBLContanierLCL getContainerLCL() {
		return containerLCL;
	}

	public void setContainerLCL(CadastroBLContanierLCL containerLCL) {
		this.containerLCL = containerLCL;
	}

	@Override
	public StatusAtiLclFclEnum getStatusAtiLclFclEnum() {
		return statusAtiLclFclEnum;
	}

	public void setStatusAtiLclFclEnum(StatusAtiLclFclEnum statusAtiLclFclEnum) {
		this.statusAtiLclFclEnum = statusAtiLclFclEnum;
	}

	@Override
	public boolean isEnviarParaMapa() {
		return enviarParaMapa;
	}

	public void setEnviarParaMapa(boolean enviarParaMapa) {
		this.enviarParaMapa = enviarParaMapa;
	}

	public User getProcessoBloqueado() {
		return processoBloqueado;
	}

	public void setProcessoBloqueado(User processoBloqueado) {
		this.processoBloqueado = processoBloqueado;
	}

	public TipoAnexosEnum getTipoAnexosStatusEnum() {
		return tipoAnexosStatusEnum;
	}

	public void setTipoAnexosStatusEnum(TipoAnexosEnum tipoAnexosStatusEnum) {
		this.tipoAnexosStatusEnum = tipoAnexosStatusEnum;
	}

	@Override
	public String getViagem() {
		return viagem;
	}

	public void setViagem(String viagem) {
		this.viagem = viagem;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public String getTemperaturaString() {
		return temperaturaString;
	}

	public void setTemperaturaString(String temperaturaString) {
		this.temperaturaString = temperaturaString;
	}

	public ProgramacaoNavio getProgramacaoNavio() {
		return programacaoNavio;
	}

	public void setProgramacaoNavio(ProgramacaoNavio programacaoNavio) {
		this.programacaoNavio = programacaoNavio;
	}

	public CadastroFaturarContra getFaturarContra() {
		return faturarContra;
	}

	public void setFaturarContra(CadastroFaturarContra faturarContra) {
		this.faturarContra = faturarContra;
	}

	public CadastroResponsavelLote getResponsavelLote() {
		return responsavelLote;
	}

	public void setResponsavelLote(CadastroResponsavelLote responsavelLote) {
		this.responsavelLote = responsavelLote;
	}

	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public String getAdquirenteNome() {
		return adquirenteNome;
	}

	public void setAdquirenteNome(String adquirenteNome) {
		this.adquirenteNome = adquirenteNome;
	}

	public String getAdquirenteCNPJ() {
		return adquirenteCNPJ;
	}

	public void setAdquirenteCNPJ(String adquirenteCNPJ) {
		this.adquirenteCNPJ = adquirenteCNPJ;
	}

	public String getAdquirenteBlHouse() {
		return adquirenteBlHouse;
	}

	public void setAdquirenteBlHouse(String adquirenteBlHouse) {
		this.adquirenteBlHouse = adquirenteBlHouse;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getAgenteNome() {
		return agenteNome;
	}

	public void setAgenteNome(String agenteNome) {
		this.agenteNome = agenteNome;
	}

	public String getAgenteCnpj() {
		return agenteCnpj;
	}

	public void setAgenteCnpj(String agenteCnpj) {
		this.agenteCnpj = agenteCnpj;
	}

	public String getSegregacaoCeMaster() {
		return segregacaoCeMaster;
	}

	public void setSegregacaoCeMaster(String segregacaoCeMaster) {
		this.segregacaoCeMaster = segregacaoCeMaster;
	}

	public String getSegregacaoCeHouse() {
		return segregacaoCeHouse;
	}

	public void setSegregacaoCeHouse(String segregacaoCeHouse) {
		this.segregacaoCeHouse = segregacaoCeHouse;
	}

	public boolean isEnvioMapaValidar() {
		return envioMapaValidar;
	}

	public void setEnvioMapaValidar(boolean envioMapaValidar) {
		this.envioMapaValidar = envioMapaValidar;
	}

	public User getUserLogado() {
		return userLogado;
	}

	public void setUserLogado(User userLogado) {
		this.userLogado = userLogado;
	}

	public User getUserCadastroDtc() {
		return userCadastroDtc;
	}

	public void setUserCadastroDtc(User userCadastroDtc) {
		this.userCadastroDtc = userCadastroDtc;
	}

	@Override
	public Date getDataEnvioMapa() {
		return dataEnvioMapa;
	}

	public void setDataEnvioMapa(Date dataEnvioMapa) {
		this.dataEnvioMapa = dataEnvioMapa;
	}

	public String getFaturarContraEmail() {
		return faturarContraEmail;
	}

	public void setFaturarContraEmail(String faturarContraEmail) {
		this.faturarContraEmail = faturarContraEmail;
	}

	public String getRetiradaObservacaoInvoice() {
		return retiradaObservacaoInvoice;
	}

	public void setRetiradaObservacaoInvoice(String retiradaObservacaoInvoice) {
		this.retiradaObservacaoInvoice = retiradaObservacaoInvoice;
	}

	public CadastroImportador getImportadorLCL() {
		return importadorLCL;
	}

	public void setImportadorLCL(CadastroImportador importadorLCL) {
		this.importadorLCL = importadorLCL;
	}

	public String getNumeroBlMaster() {
		return numeroBlMaster;
	}

	public void setNumeroBlMaster(String numeroBlMaster) {
		this.numeroBlMaster = numeroBlMaster;
	}

}
