package conexao.com.enums;

public enum TipoExtensaoArquivoEnum {
	EXECEL_XLS(0,".xls"),
	PDF(1,".pdf");
	
	private int codigo;
	private String descricao;
	
	private TipoExtensaoArquivoEnum(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
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
}
