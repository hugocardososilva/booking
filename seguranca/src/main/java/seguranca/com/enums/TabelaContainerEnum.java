package seguranca.com.enums;

public enum TabelaContainerEnum {
	
	TABELA_A("A",10),
	TABELA_B("B",12),
	TABELA_C("C",13),
	TABELA_D("D",14),
	TABELA_E("E",15),
	TABELA_F("F",16),
	TABELA_G("G",17),
	TABELA_H("H",18),
	TABELA_I("I",19),
	TABELA_J("J",20),
	TABELA_K("K",21),
	TABELA_L("L",23),
	TABELA_M("M",24),
	TABELA_N("N",25),
	TABELA_O("O",26),
	TABELA_P("P",27),
	TABELA_Q("Q",28),
	TABELA_R("R",29),
	TABELA_S("S",30),
	TABELA_T("T",31),
	TABELA_U("U",32),
	TABELA_V("V",34),
	TABELA_W("W",35),
	TABELA_X("X",36),
	TABELA_Y("Y",37),
	TABELA_Z("Z",38),
	TABELA_1("1",1),
	TABELA_2("2",2),
	TABELA_3("3",3),
	TABELA_4("4",4),
	TABELA_5("5",5),
	TABELA_6("6",6),
	TABELA_7("7",7),
	TABELA_8("8",8),
	TABELA_9("9",9);
	
	private int codigo;
	private String descricao;
	
	private TabelaContainerEnum(String descricao,int codigo) {
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
	
	public static TabelaContainerEnum getPesquisaTabela(String valor) {
		TabelaContainerEnum encontrado = null;
		for (TabelaContainerEnum item : values()) {
			if (item.getDescricao().equals(valor)) {
				encontrado = item;
				break;
			}
		}
		return encontrado;
	}
	
	
}
