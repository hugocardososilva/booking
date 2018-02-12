package seguranca.com.enums;

public enum TipoAnexosEnum {
	BL(0,"BL", 1),
	CE(1,"CE", 2),
	INVOICE(2,"Invoice", 3),
	PACKING(3,"Packing", 4),
	DTC_DTA(4,"DTC - DTA", 5),
	STATUS_ANEXO(5,"Anexos Status", 0),
	CLIF_ANEXO(6,"Anexos Clif", 6),
	MAPA_ANEXO(7,"Anexos Mapa", 7),
	COMPLEMENTARES_ANEXO(8,"Anexos Complementares", 8),
	ANEXO_LI(9,"LI", 9),
	FISPQ(10,"FISPQ - Segurança Produtos Químicos", 10);
	
	private int codigo;
	private String descricao;
	private int posicaoAbaTela;
	
	private TipoAnexosEnum(int codigo, String descricao, int posicaoAbaTela) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.posicaoAbaTela = posicaoAbaTela;
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

	public int getPosicaoAbaTela() {
		return posicaoAbaTela;
	}

	public void setPosicaoAbaTela(int posicaoAbaTela) {
		this.posicaoAbaTela = posicaoAbaTela;
	}
}
