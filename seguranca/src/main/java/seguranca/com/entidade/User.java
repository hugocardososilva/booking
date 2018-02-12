package seguranca.com.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import seguranca.com.enums.Role;

@Entity
@Table(name = "USERS", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "cpf" }, name = "UQ_USERS_CPF") } )
@Audited
@AuditTable(value="USERS_AUD") 
@NamedQuery(name = "User.findUserByEmail", query = "select u from User u where u.email = :email")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_EMAIL = "User.findUserByEmail";
	public static final String FILTRO_NAME = "select u from User u where u.name = :name";
	public static final String FILTRO_USUARIO_SENHA = "select u from User u where u.cpf = :cpf and u.password = :password";
	public static final String VALIDAR_USUARIO_CPF = "select u from User u where u.cpf = :cpf";

	public static final String LOCALIZAR_USUARIOS_PERFIL = "select u from User u where u.role = :role and u.receberEmail = 1 and u.userDesativado = 0";

	public static final String LOCALIZAR_USUARIO_EMAIL_CPF = "select u from User u where u.cpf = :cpf and u.email = :email";
	
	public static final String NAME = "name";
	public static final String CAMPO_EMAIL = "email";
	public static final String CAMPO_CPF = "cpf";
	public static final String CAMPO_ROLE = "role";
	public static final String CAMPO_ID = "id";
	public static final String CAMPO_ACESSOMAPA = "acessoMapa";
	public static final String CAMPO_ADM = "role.descricao";
	public final static String sql = "select p from User p ";
	public final static String sqlCount = "select COUNT(p) from User p ";

	public final static String sql_portalmapa = "select p from User p where p.acessoMapa = 1";
	public final static String sqlCount_portalmapa = "select COUNT(p) from User p where p.acessoMapa = 1";
	public final static String sql_agendamento = "select p from User p where p.acessoMapa = 0";
	public final static String sqlCount_agendamento = "select COUNT(p) from User p where p.acessoMapa = 0";

	@Id
	@SequenceGenerator(name = "SEQ_USERS_ID", sequenceName = "SEQ_USERS_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_USERS_ID")
	private int id;

	@Column(unique = false)
	private String email;
	
	@Column(name="DATA_CADASTRO")
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;

	private String password;
	
	@Transient
	private String novaSenha;

	private String name;

	private String cpf;

	@Lob
    @Column(name = "IMAGEM")
    private byte[] imagem;
	
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(name="RECEBER_EMAIL" , columnDefinition = "int default 0")
	private boolean receberEmail;

	@Column(name="UTILIZARNOTIFY" , columnDefinition = "int default 0")
	private boolean utilizarNotify;

	@Column(name="ENVIAR_MAPA" , columnDefinition = "int default 0")
	private boolean enviarMapa;

	@Column(name="ACESSO_MAPA" , columnDefinition = "int default 0")
	private boolean acessoMapa;

	@Column(name="ACESSO_MAPA_CONSULTA" , columnDefinition = "int default 0")
	private boolean acessoMapaConsulta;

	@Column(name="USER_DESATIVADO" , columnDefinition = "int default 0")
	private boolean userDesativado;

	@Column(name="FILTRO_LCL" , columnDefinition = "int default 0")
	private boolean utilizarFiltroLCL;

	@ManyToOne
	@JoinColumn(name = "DADOSCONEXAO_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_DADOSCONEXAO_ID")
	private UserDadosConexao userDadosConexao;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioVinculado", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserPermissoes> listaUserPermissoes;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("cadRespLote DESC")
	private List<UserResponsavelLote> listaUserResponsavelLote;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserComissario> listaUserComissaria;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("importador DESC")
	private List<UserImportador> listaUserImportadores;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isAdmin() {
		return Role.ADMIN.equals(role);
	}

	public boolean isCliente() {
		return Role.CLIENTE.equals(role);
	}

	public boolean isImportadorMaster() {
		return Role.IMPORTADOR_MASTER.equals(role);
	}

	public boolean isComissariaMaster() {
		return Role.COMISSARIA_MASTER.equals(role);
	}

	public boolean isFiscal() {
		return Role.USER.equals(role);
	}

	public boolean isDespachante() {
		return Role.DESPACHANTE.equals(role);
	}

	public boolean isClif() {
		return Role.CLIF.equals(role);
	}

	public boolean isUser() {
		return Role.USER.equals(role);
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User user = (User) obj;
			return user.getId() == id;
		}

		return false;
	}

	public List<UserPermissoes> getListaUserPermissoes() {
		if (listaUserPermissoes == null) {
			listaUserPermissoes = new ArrayList<UserPermissoes>();
		}
		return listaUserPermissoes;
	}

	public void setListaUserPermissoes(List<UserPermissoes> listaUserPermissoes) {
		this.listaUserPermissoes = listaUserPermissoes;
	}
	
	public List<UserComissario> getListaUserComissaria() {
		if (listaUserComissaria == null) {
			listaUserComissaria = new ArrayList<UserComissario>();
		}
		return listaUserComissaria;
	}
	
	public void setListaUserComissaria(List<UserComissario> listaUserComissaria) {
		this.listaUserComissaria = listaUserComissaria;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public boolean isReceberEmail() {
		return receberEmail;
	}

	public void setReceberEmail(boolean receberEmail) {
		this.receberEmail = receberEmail;
	}

	public List<UserImportador> getListaUserImportadores() {
		if (listaUserImportadores == null) {
			listaUserImportadores = new ArrayList<UserImportador>();
		}
		return listaUserImportadores;
	}

	public void setListaUserImportadores(List<UserImportador> listaUserImportadores) {
		this.listaUserImportadores = listaUserImportadores;
	}

	public UserDadosConexao getUserDadosConexao() {
		return userDadosConexao;
	}

	public void setUserDadosConexao(UserDadosConexao userDadosConexao) {
		this.userDadosConexao = userDadosConexao;
	}

	public List<UserResponsavelLote> getListaUserResponsavelLote() {
		if (listaUserResponsavelLote == null) {
			listaUserResponsavelLote = new ArrayList<UserResponsavelLote>();
		}
		return listaUserResponsavelLote;
	}

	public void setListaUserResponsavelLote(List<UserResponsavelLote> listaUserResponsavelLote) {
		this.listaUserResponsavelLote = listaUserResponsavelLote;
	}

	public boolean isUtilizarNotify() {
		return utilizarNotify;
	}

	public void setUtilizarNotify(boolean utilizarNotify) {
		this.utilizarNotify = utilizarNotify;
	}

	public boolean isEnviarMapa() {
		return enviarMapa;
	}

	public void setEnviarMapa(boolean enviarMapa) {
		this.enviarMapa = enviarMapa;
	}

	public boolean isAcessoMapa() {
		return acessoMapa;
	}

	public void setAcessoMapa(boolean acessoMapa) {
		this.acessoMapa = acessoMapa;
	}

	public boolean isAcessoMapaConsulta() {
		return acessoMapaConsulta;
	}

	public void setAcessoMapaConsulta(boolean acessoMapaConsulta) {
		this.acessoMapaConsulta = acessoMapaConsulta;
	}

	public boolean isUserDesativado() {
		return userDesativado;
	}

	public void setUserDesativado(boolean userDesativado) {
		this.userDesativado = userDesativado;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public boolean isUtilizarFiltroLCL() {
		return utilizarFiltroLCL;
	}

	public void setUtilizarFiltroLCL(boolean utilizarFiltroLCL) {
		this.utilizarFiltroLCL = utilizarFiltroLCL;
	}
	


}