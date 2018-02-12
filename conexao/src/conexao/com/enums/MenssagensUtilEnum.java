package conexao.com.enums;

public enum MenssagensUtilEnum {
	
	OPERACAO_SUCESSO(0,"Operaçãoo realizada com sucesso!"),
	OPERACAO_ERRO(1,"ERRO"),
	OPERACAO_CANCELADA(2,"Operação cancelada!"),
	OPERACAO_EMAIL_SUCESSO(3,"E-mail enviado com Sucesso!"),
	OPERACAO_PROCESSO_SUCESSO(4,"Enviado processo com Sucesso!"),
	OPERACAO_UPLOAD(5,"Upload realizado com sucesso."),
	OPERACAO_SALVO_SUCESSO(6,"Salvo com sucesso!"),
	OPERACAO_VINCULADO_SUCESSO(7,"Vinculado com sucesso!");
	
	private int codigo;
	private String descricao;
	
	private MenssagensUtilEnum(int codigo, String descricao) {
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
