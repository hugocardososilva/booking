package com.enums;

public enum SituacaoPedidoEnum {

	PENDENTES(0 , "Pendente"),
	ENTREGUE(1 , "Entregue");

	private int codigo;
	private String descricao;
	
	private SituacaoPedidoEnum(int codigo, String descricao) {
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
