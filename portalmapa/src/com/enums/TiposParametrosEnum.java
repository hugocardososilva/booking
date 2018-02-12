package com.enums;

public enum TiposParametrosEnum {

	OCULTAR_CAMPO_PLACA(0,"Ocultar campo placa"),
	CADASTRO_PROUTO_TABELA_PRECO(1,"Cadastro Produto Utilizar Tabela Preço"),
	VENDA_COMANDA_MESSAS(2,"Quantidade de messas comanda");
	
	private int codigo;
	private String descricao;
	
	private TiposParametrosEnum(int codigo, String descricao) {
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
