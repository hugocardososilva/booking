package seguranca.com.enums;

public enum StatusBLEnum {
	PENDENTE_ANEXO(0,"Pendente Anexo / Descrição Mercadoria", "Pendente Envio Mapa"),
	AGUARDANDO_LIBERACAO_PROCESSOS_QUE_ANEXARAM(1,"Aguardando Liberação","Aguardando Liberação"),
	LIBERADO_SEM_VISTORIA(2,"Liberado S/ Vistoria","Liberado S/ Vistoria"),
	SEPARADO_PARA_VISTORIA(3,"Separado P/ Vistoria","Separado P/ Vistoria"),
	LIBERADO_E_VISTORIADO(4,"Liberado e Vistoriado","Liberado e Vistoriado"),
	TODOS(5,"Todos","Todos");
	
	private int codigo;
	private String descricao;
	private String descricaoAgendamento;
	
	private StatusBLEnum(int codigo, String descricao, String descricaoAgendamento) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.descricaoAgendamento = descricaoAgendamento;
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

	public String getDescricaoAgendamento() {
		return descricaoAgendamento;
	}

	public void setDescricaoAgendamento(String descricaoAgendamento) {
		this.descricaoAgendamento = descricaoAgendamento;
	}
}
