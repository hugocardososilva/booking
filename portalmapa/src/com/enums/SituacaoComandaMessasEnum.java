package com.enums;

public enum SituacaoComandaMessasEnum {
	FECHADA(0 , "Fechada"),
	ABERTA(1 , "Aberta");

	private int codigo;
	private String descricao;
	
	private SituacaoComandaMessasEnum(int codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	@Override
	public String toString() {
		return descricao;
	}
	
	
}
