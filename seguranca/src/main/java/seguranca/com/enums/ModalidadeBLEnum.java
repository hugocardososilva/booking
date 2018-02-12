package seguranca.com.enums;

public enum ModalidadeBLEnum {
	FCL(0,"FCL"),
	LCL(1,"LCL");
	
	private int codigo;
	private String descricao;
	
	private ModalidadeBLEnum(int codigo, String descricao) {
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
	
	public static int getPesquisaTabela(String valor) {
		int valorRetorno = -1;
		ModalidadeBLEnum encontrado = null;
		for (ModalidadeBLEnum item : values()) {
			if (item.getDescricao().equals(valor.toUpperCase())) {
				encontrado = item;
				break;
			}
		}
		if (encontrado != null) {
			valorRetorno = encontrado.getCodigo();
		}
		
		return valorRetorno;
	}	
}
