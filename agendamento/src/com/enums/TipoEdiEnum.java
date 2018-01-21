package com.enums;

public enum TipoEdiEnum {
	EDI_SIGVIG(0,"EDI SIGVIG");
	
	private int codigo;
	private String descricao;
	
	private TipoEdiEnum(int codigo, String descricao) {
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
