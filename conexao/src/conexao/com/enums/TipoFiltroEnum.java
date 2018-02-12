package conexao.com.enums;

public enum TipoFiltroEnum {
	TODOS(0,"Todos > 0"),
	IGUAL(1,"Igual = 0"),
	IN(2,"IN ->> in (2,3) "),
	IN_SEM_WHERE(3,"IN ->> in (2,3) "),
	IGUAL_COM_WHERE(4,"Igual = 0"),
	IN_SEM_WHERE_OR(5,"IN ->> in (2,3) OR ( IN =>> (2,3) )");
	
	
	private int codigo;
	private String descricao;
	
	private TipoFiltroEnum(int codigo, String descricao) {
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
