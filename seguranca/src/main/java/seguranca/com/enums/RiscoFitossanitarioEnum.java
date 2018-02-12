package seguranca.com.enums;

public enum RiscoFitossanitarioEnum  {
	AZUL(0,"Azul","blue"),
	VERDE(1,"Verde", "Green"),
	AMARELO(2,"Amarelo", "Yellow"),
	LARANJA(3,"Laranja", "DarkOrange"),
	VERMELHO(4,"Vermelho", "Red");
	
	private int codigo;
	private String descricao;
	private String cor;
	
	private RiscoFitossanitarioEnum(int codigo, String descricao, String cor) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.cor = cor;
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

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}
	
}
