package com.enums;

public enum UnidadesEnum {

	PC(0,"Peça"),
	UN(1,"Unidade"),
	LT(2,"Litro"),
	M(3,"Metro"),
	KG(4,"Quilograma");
	
	private int codigo;
	private String descricao;
	
	private UnidadesEnum(int codigo, String descricao) {
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
