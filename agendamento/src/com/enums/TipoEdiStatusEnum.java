package com.enums;

public enum TipoEdiStatusEnum {
	STATUS_PENDENTE(0,"1-Pendente"),
	STATUS_NAO_INSPECIONADO(1,"2-Não inspecionado"),
	STATUS_SELECIONADO(2,"3-Selecionado"),
	STATUS_PRE_AGENDADO(3,"4-Pró-agendado"),
	STATUS_POSICIONADO(4,"5-Posicionado"),
	STATUS_EM_INSPECAO(5,"6-Em inspeção"),
	STATUS_NAO_FISCALIZADO(6,"7-Não fiscalizado"),
	STATUS_CONFORME(7,"8-Conforme"),
	STATUS_NAO_CONFORME(8,"9-Não conforme"),
	STATUS_NAO_SELECIONADO(9,"10-Não selecionado"),
	STATUS_PRE_SELECIONADO(10,"11-Pre selecionado"),
	STATUS_PRE_LISTA(11,"12-Pre lista");
	
	private int codigo;
	private String descricao;
	
	private TipoEdiStatusEnum(int codigo, String descricao) {
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
