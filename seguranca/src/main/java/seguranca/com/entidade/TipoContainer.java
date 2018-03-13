package seguranca.com.entidade;

public enum TipoContainer {
	FLC(0,"FLC"),
	LCL(1,"LCL");
	
	private int codigo;
	private String descricao;
	
	private TipoContainer(int codigo, String descricao) {
		this.codigo= codigo;
		this.descricao= descricao;
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
