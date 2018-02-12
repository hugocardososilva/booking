package seguranca.com.enums;

public enum TipoLoginEnum {
	LOGIN(0,"Login"),
	LOGAUT(1,"Logaut");
	
	private int codigo;
	private String descricao;
	
	private TipoLoginEnum(int codigo, String descricao) {
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
