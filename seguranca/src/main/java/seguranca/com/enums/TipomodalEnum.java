package seguranca.com.enums;

public enum TipomodalEnum {
	DTC(0,"DTC", "Laranja"),
	DTA_MARITIMO(1,"DTA - Marítimo", "Verde"),
	DTA_AEREO(2,"DTA - Aéreo", "Amarelo"),
	DTA_MIC(3,"MIC - DTA", "Azul");
	
	private int codigo;
	private String descricao;
	private String cor;
	
	private TipomodalEnum(int codigo, String descricao, String cor) {
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
