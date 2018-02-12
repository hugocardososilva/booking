package seguranca.com.enums;

public enum Role {
	ADMIN(0,"ADMIN"),
	USER(1, "FISCAL"),
	CLIENTE(2, "CLIENTE"),
	CLIF(3, "CLIF"),
	DESPACHANTE(4, "DESPACHANTE"),
	IMPORTADOR_MASTER(5, "IMPORTADOR USER MASTER"),
	COMISSARIA_MASTER(6, "COMISSARIA USER MASTER");
	
	private int codigo;
	private String descricao;

	private Role(int codigo, String descricao) {
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
