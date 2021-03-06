package seguranca.com.enums;

public enum TipoContainerEnum {
	TIPO_20GP(0,"20GP - GENERAL PURPOSE CONT."),
	TIPO_20HR(1,"20HR - ISOLADO CONTAINE REEFER"),
	TIPO_20PF(2,"20PF - FLAT (FIXED ENDS)"),
	TIPO_20TD(3,"20TD - TANK CONTAINER"),
	TIPO_20TG(4,"20TG -TANK CONTAINER"),
	TIPO_20TN(5,"20TN - TANK CONTAINER"),
	TIPO_22BU(6,"22BU - BULK CONTAINER"),
	TIPO_22GP(7,"22GP - GENERAL PURPOSE CONT."),
	TIPO_22HR(8,"22HR - INSULATED CONTAINER"),
	TIPO_22PC(9,"22PC - FLAT (COLLAPSIBLE)"),
	TIPO_22PF(10,"22PF - FLAT (FIXED ENDS)"),
	TIPO_22RC(11,"22RC - REEFER CONT.(NO FOOD)"),
	TIPO_22RS(12,"22RS - BUILT-IN GEN. F. POWER SPLY OF REEF"),
	TIPO_22RT(13,"22RT - REEFER CONTAINER"),
	TIPO_22SN(14,"22SN - NAMED CARGO CONTAINER"),
	TIPO_22TD(15,"22TD - TANK CONTAINER"),
	TIPO_22TG(16,"22TG - TANK CONTAINER"),
	TIPO_22TN(17,"22TN - TANK CONTAINER"),
	TIPO_22UP(18,"22UP - HARDTOP CONTAINER"),
	TIPO_22UT(19,"22UT - OPEN TOP CONTAINER"),
	TIPO_22VH(20,"22VH - VENTILATED CONTAINER"),
	TIPO_25GP(21,"25GP - GP-CONTAINER OVER-HEIGHT"),
	TIPO_26GP(22,"26GP - GP-CONTAINER OVER-HEIGHT"),
	TIPO_26HR(23,"26HR - INSULATED CONTAINER"),
	TIPO_28TG(24,"28TG - TANK FOR GAS"),
	TIPO_28UT(25,"28UT - OPEN TOP (HALF HEIGHT)"),
	TIPO_28VH(26,"28VH - VE-HALF-HEIGHT =1448 MM HEIGHT"),
	TIPO_29PL(27,"29PL - PLATFORM"),
	TIPO_2EGP(28,"2EGP - GEN. PURP. WITHOUT VENT WIDTH 2.5M"),
	TIPO_42GP(29,"42GP - GENERAL PURPOSE CONT."),
	TIPO_42HR(30,"42HR - INSULATED CONTAINER"),
	TIPO_42PC(31,"42PC - FLAT (COLLAPSIBLE)"),
	TIPO_42PF(32,"42PF - FLAT (FIXED ENDS)"),
	TIPO_42PS(33,"42PS - FLAT (SPACE SAVER)"),
	TIPO_42RC(34,"42RC - REEFER CONT.(NO FOOD)"),
	TIPO_42RS(35,"42RS - REEFER CONT.(DIESEL GEN.)"),
	TIPO_42RT(36,"42RT - REEFER CONTAINER"),
	TIPO_42SN(37,"42SN - NAMED CARGO CONTAINER"),
	TIPO_42TD(38,"42TD - TANK CONTAINER"),
	TIPO_42TG(39,"42TG - TANK CONTAINER"),
	TIPO_42TN(40,"42TN - TANK CONTAINER"),
	TIPO_42UP(41,"42UP - HARDTOP CONTAINER"),
	TIPO_42UT(42,"42UT - OPEN TOP CONTAINER"),
	TIPO_45BK(43,"45BK - BULK CONTAINER"),
	TIPO_45GP(44,"45GP - HIGH CUBE CONT."),
	TIPO_45PC(45,"45PC - FLAT (COLLAPSIBLE)"),
	TIPO_45RC(46,"45RC - REEFER CONT.(NO FOOD)"),
	TIPO_45RT(47,"45RT - REEFER HIGHCUBE CONTAINER"),
	TIPO_45UT(48,"45UT - OPEN TOP CONTAINER"),
	TIPO_45UP(49,"45UP - HIGH CUBE HARDTOP CONT."),
	TIPO_46HR(50,"46HR - INSULATED CONTAINER"),
	TIPO_48TG(51,"48TG - TANK FOR GAS"),
	TIPO_49PL(52,"49PL - PLATFORM"),
	TIPO_4CGP(53,"4CGP - GP CONTAINER"),
	TIPO_L0GP(54,"L0GP - HIGH CUBE CONT."),
	TIPO_L2GP(55,"L2GP - HIGH CUBE CONT."),
	TIPO_L5GP(56,"L5GP - HIGH CUBE CONT.");

	private int codigo;
	private String descricao;
	
	private TipoContainerEnum(int codigo, String descricao) {
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
