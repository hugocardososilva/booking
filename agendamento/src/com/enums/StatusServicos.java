package com.enums;

public enum StatusServicos {
	CRIA_OS_SARA(0,"Gerar OS SARA"),
	OS_GERADA(1, "OS Gerada"),
	OS_INICIADA(2, "Os iniciada"),
	FINALIZADO(3, "Finalizado");
	
	private int codigo;
	private String descricao;
	
	private StatusServicos(int codigo, String descricao) {
		this.codigo=codigo;
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
