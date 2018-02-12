package seguranca.com.enums;

public enum StatusAtiLclFclEnum {
	PENDENTE(0,"Pendente"),
	EM_ANALISE(1,"Em an�lise"),
	SEGREDADO(2,"Segrega��o solicitada "),
	AGUARDANDO_TRANSPORTE(3,"Ag. Transporte"),
	PRESENCA_TERMINAL(4,"Presen�a Terminal"),
	CANCELADO(5,"Cancelado"),
	SEGREGACAO_NAO_ACEITA(6,"Segrega��o n�o aceita ");
	
	private int codigo;
	private String descricao;
	
	private StatusAtiLclFclEnum(int codigo, String descricao) {
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
		StatusAtiLclFclEnum encontrado = null;
		for (StatusAtiLclFclEnum item : values()) {
			if (item.getDescricao().toUpperCase().trim().equals(valor.toUpperCase().trim() )) {
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
