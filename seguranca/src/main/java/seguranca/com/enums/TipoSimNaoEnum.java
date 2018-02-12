package seguranca.com.enums;

public enum TipoSimNaoEnum {
	NAO(0,"Não"),
	SIM(1,"Sim");
	
	private int codigo;
	private String descricao;
	
	private TipoSimNaoEnum(int codigo, String descricao) {
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
