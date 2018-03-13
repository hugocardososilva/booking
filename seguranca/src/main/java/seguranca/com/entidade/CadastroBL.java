package seguranca.com.entidade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;

import seguranca.com.enums.ModalidadeBLEnum;
import seguranca.com.enums.StatusAtiLclFclEnum;
import seguranca.com.enums.StatusBLEnum;
import seguranca.com.enums.TipoAnexosEnum;
import seguranca.com.enums.TipoContainerEnum;
import seguranca.com.enums.TipoSimNaoEnum;
import seguranca.com.enums.TipomodalEnum;

@Entity
@Table(name = "MAPA_CAD_BL")
@Audited
@NamedQuery(name = "CadastroBL.FIND_BY_ENTIDADE", query = "select u from CadastroBL u where u.descricaoBL = :descricaoBL")

public class CadastroBL implements ICamposComunsBLDtcDta {

	public final static String sql = "select p from CadastroBL p ";
	public final static String sqlCount = "select COUNT(p) from CadastroBL p ";

	public final static String sqlMapa = "select p from CadastroBL p where p.enviarParaMapa = 1 and statusBLEnum <> 0 ";
	public final static String sqlMapaCount = "select COUNT(p) from CadastroBL p where p.enviarParaMapa = 1 and statusBLEnum <> 0 ";

	public final static String UPDATE_ATUALIZA_DADOS = "update CadastroBL p "
			+ "set statusBLEnum = :statusBLEnum where p.id = :id";

	public final static String SQL_COUNT_BLS = "select COUNT(p) from CadastroBL p where p.containerLCL.id = :containerLCL ";
	public final static String SQL_SOMA_VOLUME = "select sum(quantidade) from CadastroBL p where p.containerLCL.id = :containerLCL ";
	public final static String SQL_SOMA_MERCADORIA = "select sum(cifMercadoria) from CadastroBL p where p.containerLCL.id = :containerLCL ";

	public final static String CADASTRO_BL_DESCRICAOBL = "descricaoBL";
	public final static String CAMPO_CONTAINERLCL = "containerLCL";
	public final static String CADASTRO_BL_ID = "id";
	public final static String CADASTRO_CONTAINER = "container";
	public final static String CADASTRO_PAIS_ORIGEM_DESCRICAO = "paisOrigem.descricaoPais";
	public final static String CADASTRO_PAIS_PROCEDENCIA_DESCRICAO = "paisProcedencia.descricaoPais";
	public final static String CADASTRO_ESPECIE_DESCRICAO = "tipoEmbalagemEspecie.descricao";
	public final static String CADASTRO_NCM_DESCRICAO = "ncm.descricaoNcm";
	public final static String CADASTRO_DESCRICAOMERCADORIA = "descricaoMercadoria";
	public final static String CADASTRO_STATUS = "statusBLEnum";
	public final static String CAMPO_TIPO_MODAL = "tipoModalEnum";
	public final static String CAMPO_COMISSARIO = "cadComissario";
	public final static String CAMPO_ENVIARPARAMAPA = "enviarParaMapa";
	public final static String CAMPO_IMPORTADOR = "importador";

	public static final String FIND_BY_ENTIDADE = "CadastroBL.descricaoBL";
	public static final String FILTRO_CADASTRO_BL_DESCRICAOBL = "select u from CadastroBL u where u.descricaoBL = :descricaoBL";

	@Id
	@SequenceGenerator(name = "SEQ_CADASTRO_BL_ID", sequenceName = "SEQ_CADASTRO_BL_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CADASTRO_BL_ID")
	@Column(name = "CADASTROBL_ID")
	private Integer id;

	@Column(name = "DATA_CADASTRO")
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;

	@Column(name = "DATA_ENVIOMAPA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEnvioMapa;

	@Column(name = "DATA_ATRACACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAtracacao;

	@Column(name = "DESCRICAO_BL", length = 350)
	private String descricaoBL;

	@Column(name = "NUMERO_ATI", length = 150)
	private String numeroATI;

	@Column(name = "TEMPERATURA")
	private Integer temperatura;

	@Column(name="TEMPERATURA_STRING", length = 15)
	private String temperaturaString;

	@Column(name = "FREE_TIME")
	private Integer freeTime;

	@Column(name = "REFERENCIA_CLIENTE", length = 350)
	private String referenciaCliente;

	@Column(name = "DESCRICAOMERCADORIA", length = 350)
	private String descricaoMercadoria;

	@Column(name = "QUANTIDADE")
	private Float quantidade;

	@Column(name = "CIF_MERCADORIA")
	private Float cifMercadoria;

	@Column(name = "ARMADOR", length = 350)
	private String armador;

	@Column(name = "NAVIOVIAGEM", length = 350)
	private String navioViagem;

	@Column(name="VIAGEM", length = 350)
	private String viagem;

	@Column(name = "STATUS_BL")
	private StatusBLEnum statusBLEnum;

	@Column(name="STATUS_ANEXO", columnDefinition = "int default 0")
	private TipoAnexosEnum tipoAnexosStatusEnum;

	@Column(name="STATUS_ATI", columnDefinition = "int default 0" )
	private StatusAtiLclFclEnum statusAtiLclFclEnum;

	@Column(name = "MODALIDADE_BL")
	private ModalidadeBLEnum modalidadeBLEnum;

	@Column(name = "INSPECAO", columnDefinition = "int default 0")
	private boolean inspecao;

	@Column(name = "DEFERIDO", columnDefinition = "int default 0")
	private boolean deferido;

	@Column(name = "LIBERADO", columnDefinition = "int default 0")
	private boolean liberado;

	@Column(name = "ANUENCIA_MAPA", columnDefinition = "int default 0")
	private boolean anuenciaMapa;

	@Transient
	private boolean inspecaoControle;
	
	@Transient
	private boolean liberadoControle;

	@Column(name = "REUNITIZAR", columnDefinition = "int default 0")
	private boolean reunitizar;

	@Column(name = "DESUNITIZACAO", columnDefinition = "int default 0")
	private boolean desunitizacao;

	@Column(name="ENVIAR_PARA_MAPA" , columnDefinition = "int default 0")
	private boolean enviarParaMapa;

	@Column(name = "TIPO_CONTAINER")
	private TipoContainerEnum tipoContainerEnum;

	@Column(name = "TIPO_MODAL")
	private TipomodalEnum tipoModalEnum;

	@Transient
	private boolean alteracaoDescricaoMercadoria;

	@Transient
	private boolean alteradoInspecao;

	@Transient
	private boolean alteradoLiberado;

	@ManyToOne
	@JoinColumn(name = "PAISORIGEM_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_PAISORIGEM_ID")
	private CadastroCodigoPais paisOrigem;

	@ManyToOne
	@JoinColumn(name = "PROGRAMACAONAVIO_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_BL_PROGRAMACAONAVIO_ID")
	private ProgramacaoNavio programacaoNavio;

	@ManyToOne
	@JoinColumn(name = "USER_CADASTRO_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_BL_USER")
	private User userCadastroDtc;

	@ManyToOne
	@JoinColumn(name = "PAISPROCEDENCIA_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_PAISPROCEDENCIA_ID")
	private CadastroCodigoPais paisProcedencia;

	@ManyToOne
	@JoinColumn(name = "USERS_COMISSARIO_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_BL_USERCOMISSARIO")
	private UserComissario comissario;

	@ManyToOne
	@JoinColumn(name = "COMISSARIO_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_BL_CAD_COMISSARIO")
	private CadastroComissario cadComissario;

	@ManyToOne
	@JoinColumn(name = "ESPECIE_EMBALAGEM_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_BL_ESPECIE_ID")
	private CadastroEspecie tipoEmbalagemEspecie;

	@ManyToOne
	@JoinColumn(name = "PORTOS_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_BL_PORTOS_ID")
	@Index(name = "I_PORTOS")
	private CadastroPortos portos;

	@ManyToOne
	@JoinColumn(name = "REPRESENTANTE_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_BL_REPRESENTANTE_ID")
	@Index(name = "I_REPRESENTANTE")
	private CadastroRepresentante representante;

	@ManyToOne
	@JoinColumn(name = "EMBALAGEM_ENCONTRADA_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_BL_EMB_ENCONTRADA_ID")
	private CadastroEspecie tipoEmbalagemEncontrada;

	@ManyToOne
	@JoinColumn(name = "NCM_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_BL_NCM_ID")
	@Index(name = "I_NCM")
	private CadastroNcm ncm;

	@ManyToOne
	@JoinColumn(name = "LCL_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_BL_LCL_ID")
	private CadastroBLContanierLCL containerLCL;

	@ManyToOne
	@JoinColumn(name = "IMPORTADOR_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_BL_IMP_ID")
	private CadastroImportador importador;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cadastroBL", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CadastroBLAnexos> listaCadastroBLAnexos;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "cadastroBL", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CadastroBLContanier> listaCadastroBLContanier;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cadastroBL", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CadastroBLRiscoFitossanitario> listaBLRisco;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cadastroBL", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CadastroBLDescricaoMercadoria> listaBLDescricaoMercadoria;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cadastroBL", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CadastroBlLogAlteracao> listaLogAlteracao;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cadastroBL", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MapaNcm> listaMapaNcm;

	@Column(name = "CARGA_IMO")
	private TipoSimNaoEnum cargaImo;

	@Column(name = "CARGA_REEFER")
	private TipoSimNaoEnum cargaReefer;

	@Column(name = "CARGA_MADEIRA")
	private TipoSimNaoEnum cargaComMadeira;

	@Column(name = "ENTREPOSTO")
	private TipoSimNaoEnum entrePosto;

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
	public int hashCode() {
		return id;
	}
	
	@Override
	public String toString() {
		return descricaoBL;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CadastroBL) {
			CadastroBL item = (CadastroBL) obj;
			return item.getId() == id;
		}

		return false;
	}

	public List<CadastroBLAnexos> getListaCadastroBLAnexos() {
		if (listaCadastroBLAnexos == null) {
			listaCadastroBLAnexos = new ArrayList<CadastroBLAnexos>();
		}
		return listaCadastroBLAnexos;
	}

	public void setListaCadastroBLAnexos(List<CadastroBLAnexos> listaCadastroBLAnexos) {
		this.listaCadastroBLAnexos = listaCadastroBLAnexos;
	}
	@Override
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public UserComissario getComissario() {
		return comissario;
	}

	public void setComissario(UserComissario comissario) {
		this.comissario = comissario;
	}

	public List<CadastroBLContanier> getListaCadastroBLContanier() {
		if (listaCadastroBLContanier == null) {
			listaCadastroBLContanier = new ArrayList<CadastroBLContanier>();
		}
		return listaCadastroBLContanier;
	}

	public void setListaCadastroBLContanier(List<CadastroBLContanier> listaCadastroBLContanier) {
		this.listaCadastroBLContanier = listaCadastroBLContanier;
	}
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

	public List<CadastroBLRiscoFitossanitario> getListaBLRisco() {
		if (listaBLRisco == null) {
			listaBLRisco = new ArrayList<CadastroBLRiscoFitossanitario>();
		}
		return listaBLRisco;
	}

	public void setListaBLRisco(List<CadastroBLRiscoFitossanitario> listaBLRisco) {
		this.listaBLRisco = listaBLRisco;
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

	public List<CadastroBLDescricaoMercadoria> getListaBLDescricaoMercadoria() {
		if (listaBLDescricaoMercadoria == null) {
			listaBLDescricaoMercadoria = new ArrayList<CadastroBLDescricaoMercadoria>();
		}
		return listaBLDescricaoMercadoria;
	}

	public void setListaBLDescricaoMercadoria(List<CadastroBLDescricaoMercadoria> listaBLDescricaoMercadoria) {
		this.listaBLDescricaoMercadoria = listaBLDescricaoMercadoria;
	}

	public List<CadastroBlLogAlteracao> getListaLogAlteracao() {
		return listaLogAlteracao;
	}

	public void setListaLogAlteracao(List<CadastroBlLogAlteracao> listaLogAlteracao) {
		this.listaLogAlteracao = listaLogAlteracao;
	}

	public boolean isAlteracaoDescricaoMercadoria() {
		return alteracaoDescricaoMercadoria;
	}

	public void setAlteracaoDescricaoMercadoria(boolean alteracaoDescricaoMercadoria) {
		this.alteracaoDescricaoMercadoria = alteracaoDescricaoMercadoria;
	}

	public boolean isAlteradoInspecao() {
		return alteradoInspecao;
	}

	public void setAlteradoInspecao(boolean alteradoInspecao) {
		this.alteradoInspecao = alteradoInspecao;
	}

	public boolean isAlteradoLiberado() {
		return alteradoLiberado;
	}

	public void setAlteradoLiberado(boolean alteradoLiberado) {
		this.alteradoLiberado = alteradoLiberado;
	}
	@Override
	public TipoSimNaoEnum getCargaImo() {
		return cargaImo;
	}
	@Override
	public TipoSimNaoEnum getCargaReefer() {
		return cargaReefer;
	}
	@Override
	public TipoSimNaoEnum getCargaComMadeira() {
		return cargaComMadeira;
	}
	@Override
	public TipoSimNaoEnum getEntrePosto() {
		return entrePosto;
	}

	public void setCargaImo(TipoSimNaoEnum cargaImo) {
		this.cargaImo = cargaImo;
	}

	public void setCargaReefer(TipoSimNaoEnum cargaReefer) {
		this.cargaReefer = cargaReefer;
	}

	public void setCargaComMadeira(TipoSimNaoEnum cargaComMadeira) {
		this.cargaComMadeira = cargaComMadeira;
	}

	public void setEntrePosto(TipoSimNaoEnum entrePosto) {
		this.entrePosto = entrePosto;
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

	public String getTemperaturaString() {
		return temperaturaString;
	}

	public void setTemperaturaString(String temperaturaString) {
		this.temperaturaString = temperaturaString;
	}

	public boolean isInspecaoControle() {
		if (!inspecaoControle) {
			inspecaoControle = inspecao;
		}
		return inspecaoControle;
	}

	public void setInspecaoControle(boolean inspecaoControle) {
		this.inspecaoControle = inspecaoControle;
	}

	public boolean isLiberadoControle() {
		if (!liberadoControle) {
			liberadoControle = liberado;
		}
		return liberadoControle;
	}

	public void setLiberadoControle(boolean liberadoControle) {
		this.liberadoControle = liberadoControle;
	}

	public ProgramacaoNavio getProgramacaoNavio() {
		return programacaoNavio;
	}

	public void setProgramacaoNavio(ProgramacaoNavio programacaoNavio) {
		this.programacaoNavio = programacaoNavio;
	}
	@Override
	public Date getDataEnvioMapa() {
		return dataEnvioMapa;
	}
	
	public void setDataEnvioMapa(Date dataEnvioMapa) {
		this.dataEnvioMapa = dataEnvioMapa;
	}

	public List<MapaNcm> getListaMapaNcm() {
		if (listaMapaNcm == null) {
			listaMapaNcm = new ArrayList<MapaNcm>();
		}
		return listaMapaNcm;
	}

	public void setListaMapaNcm(List<MapaNcm> listaMapaNcm) {
		this.listaMapaNcm = listaMapaNcm;
	}

	public boolean isAnuenciaMapa() {
		return anuenciaMapa;
	}

	public void setAnuenciaMapa(boolean anuenciaMapa) {
		this.anuenciaMapa = anuenciaMapa;
	}

	public User getUserCadastroDtc() {
		return userCadastroDtc;
	}

	public void setUserCadastroDtc(User userCadastroDtc) {
		this.userCadastroDtc = userCadastroDtc;
	}

	public void addRisco(CadastroBLRiscoFitossanitario risco) {
		this.getListaBLRisco().add(risco);
	}
}
