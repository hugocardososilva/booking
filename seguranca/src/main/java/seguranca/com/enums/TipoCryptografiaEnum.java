package seguranca.com.enums;

public enum TipoCryptografiaEnum {
	CRIPTOGRAFAR(0),
	DESCRIPTOGRAFAR(1);
	
	private int codigo;
	
	private TipoCryptografiaEnum(int codigo) {
		this.codigo = codigo;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
}
