package seguranca.com.enums;

public enum TipoPessoaFisicaJuridicaEnum {
	PESSOA_FISICA(0,"Pessoa Física"),
	PESSOA_JURIDICA(1,"Pessoa Jurídica");
	
	private int codigo;
	private String descricao;
	
	private TipoPessoaFisicaJuridicaEnum(int codigo, String descricao) {
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
