package com.enums;

public enum StatusSolicitacao {
	
	PENDENTE(0,"Pendente"),
	ANALISE(1, "Analise"),
	AGENDADO(2, "Agendado"),
	FINALIZADO(3, "Finalizado");
	
	private int codigo;
	private String descricao;
	
	private StatusSolicitacao(int codigo, String descricao) {
		this.codigo= codigo;
		this.descricao= descricao;
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
