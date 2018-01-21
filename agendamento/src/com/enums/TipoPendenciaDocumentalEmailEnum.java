package com.enums;

public enum TipoPendenciaDocumentalEmailEnum {

	PENDENCIA_DE_MBL_1(0,1,"1- Pendencia de MBL"),
	PENDENCIA_DE_CE_MERCANTE_2(1,2,"2- Pendencia de CE Mercante"),
	PENDENCIA_DE_CE_MERCANTE_E_MBL_3(2,3,"3 - Pendencia de CE Mercante e MBL");
	
	private int codigo;
	private int seq;
	private String descricao;
	
	private TipoPendenciaDocumentalEmailEnum(int codigo, int seq, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.seq = seq;
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

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
	
}
