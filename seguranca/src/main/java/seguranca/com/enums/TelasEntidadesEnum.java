package seguranca.com.enums;

public enum TelasEntidadesEnum {

	
	CADASTRO_COMISSARIO(0,"Cadastro comissaria", " ", " ", false),
	CADASTRO_BL(1,"Cadastro BL", " ", " ", false),
	CONSULTAR_BL(2,"Consultar BL", " ", " ", false),
	CADASTRO_IMPORTADOR(3,"Cadastro importador", " ", " ", false),
	CONSULTAR_LOG(4,"Consultar LOG", " ", " ", false),
	CONSULTAR_CLIF_DEFERIDO(5,"Consultar CLIF", " ", " ", false),
	CADASTRO_USUARIOS(6,"Cadastro Usuários - CLIF", "/pages/privado/usuario/listarUsuarios.xhtml", "add-user.png", false),
	CADASTRO_ATI_FCL(7,"Solicitação DTC/DTA-FCL", "/pages/publico/cadastros/cadastroDtcDta.xhtml", "crane.png", true),
	CADASTRO_ATI_LCL(8,"Solicitação DTC/DTA-LCL", "/pages/publico/cadastros/cadastroLCL.xhtml", "truck-container_lcl.png", true),
	CONSULTA_PROCESSOS(9,"Consulta Processos", "/pages/publico/consultas/consultarProcessos.xhtml", "search_consulta.png", true),
	PROGRAMACAO_NAVIO(10,"Programacao Navio", " ", " ", false),
	IMPORTADOR_NOTIFY_DESPACHANTE(11,"Cadastro Usuários - Importador", " ", " ", false),
	DESPACHANTE_USUARIOS(12,"Cadastro Usuários - Comissária", " ", " ", false),
	GERADOR_ETIQUETAS(13,"Gerador - Etiquetas", "/pages/publico/cadastros/gerarEtiqueta.xhtml", "truck-container_lcl.png", false),
	DESCARGA_ETIQUETAS(15,"Descarga - Etiquetas", "/pages/publico/cadastros/descargaEtiqueta.xhtml", "truck-container_lcl.png", false),
	CARGA_ETIQUETAS(14,"Carga - Etiquetas", "/pages/publico/cadastros/cargaEtiqueta.xhtml", "truck-container_lcl.png", false),
	ETIQUETAS_GERADAS(15,"Etiquetas - Gerdas", "/pages/publico/cadastros/etiquetasGeradas.xhtml", "truck-container_lcl.png", false),
	SERVICOS(16,"Serviços ","/pages/publico/cadastros/cadastroServico.xhtml","",true),
	SOLICITACOES(17,"Solicitações","/pages/publico/cadastros/cadastroSOlicitacaoServicos.xhtml","",true),
	JANELA_ATENDIMENTO(18,"Janela de Atedimento","/pages/publico/cadastros/cadastroJanelaAteimento.xhtml","",true);

	private int codigo;
	private String descricao;
	private String caminhoTela;
	private String imagem;
	private boolean agendamento;
	
	private TelasEntidadesEnum(int codigo, String descricao, String caminhoTela, String imagem, boolean agendamento) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.caminhoTela = caminhoTela;
		this.imagem = imagem;
		this.agendamento = agendamento;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCaminhoTela() {
		return caminhoTela;
	}

	public void setCaminhoTela(String caminhoTela) {
		this.caminhoTela = caminhoTela;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public boolean isAgendamento() {
		return agendamento;
	}

	public void setAgendamento(boolean agendamento) {
		this.agendamento = agendamento;
	}
	
}
