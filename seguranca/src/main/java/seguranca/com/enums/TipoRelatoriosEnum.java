package seguranca.com.enums;

public enum TipoRelatoriosEnum {
	REL_RETIRADAIMPORTACAO(0, "retiradaImportacao.jrxml", "Itapoá Terminais Portuários S.A.", "Retirada_De_Importacao_ATI_"),
	REL_REQUISICAO_SEGREGACAO(1, "requisicaoSegregacao.jrxml", "Itapoá Terminais Portuários S.A.", "Requisicao_De_Segregacao_DTC_"),
	REL_ETIQUETAS(2, "etiquetas.jrxml", "ETIQUETAS", "ETIQUETAS_");

	private int codigo;
	private String nomeRelatorioJasper;
	private String tituloRelatorio;
	private String nomeArquivoRelatorioDownload;

	private TipoRelatoriosEnum(int codigo, String nomeRelatorioJasper, String tituloRelatorio,
			String nomeArquivoRelatorioDownload) {
		
		this.codigo = codigo;
		this.nomeRelatorioJasper = nomeRelatorioJasper;
		this.tituloRelatorio = tituloRelatorio;
		this.nomeArquivoRelatorioDownload = nomeArquivoRelatorioDownload;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getTituloRelatorio() {
		return tituloRelatorio;
	}

	public void setTituloRelatorio(String tituloRelatorio) {
		this.tituloRelatorio = tituloRelatorio;
	}

	public String getNomeArquivoRelatorioDownload() {
		return nomeArquivoRelatorioDownload;
	}

	public void setNomeArquivoRelatorioDownload(String nomeArquivoRelatorioDownload) {
		this.nomeArquivoRelatorioDownload = nomeArquivoRelatorioDownload;
	}

	public String getNomeRelatorioJasper() {
		return nomeRelatorioJasper;
	}

	public void setNomeRelatorioJasper(String nomeRelatorioJasper) {
		this.nomeRelatorioJasper = nomeRelatorioJasper;
	}
}
