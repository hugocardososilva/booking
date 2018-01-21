package com.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.envers.Audited;

import com.enums.TipoEdiEnum;
import com.enums.TipoEdiStatusEnum;

/**
 * Class criada para efetuar controle dos Envios de arquivos WEB Service SIGVIG
 * @author murilonadalin
 * @since 29-08-2017
 */

@Entity
@Table(name = "SYS_EDI")
@Audited
public class EdiEnvioXML implements Serializable {

	private static final long serialVersionUID = 1L;
	public final static String sql = "select p from EdiEnvioXML p ";
	public final static String sqlCount = "select COUNT(p) from EdiEnvioXML p ";
	
	public final static String CAMPO_ID = "id";
	
	@Id
	@SequenceGenerator(name = "SEQ_EDI_ID", sequenceName = "SEQ_EDI_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_EDI_ID")
	@Column(name="EDI_ID")
	private Integer id;
	
	@Column(name = "DATA_ENVIO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEnvio;

	@Column(name = "DATA_RETORNO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataRetorno;
	
	@Column(name = "TIPO_EDI")
	private TipoEdiEnum tipoEdiEnum;

	@Column(name = "TIPO_EDI_STATUS")
	private TipoEdiStatusEnum tipoEdiStatusEnum;

	@Column(name = "NOME_ARQUIVO", length = 350)
	private String nomeArquivo;
	
	@ManyToOne
	@JoinColumn(name = "DTCDTA_ID", nullable = true, insertable = true, updatable = true)
	@ForeignKey(name = "FK_EDI_DTCDTA_ID")
	private CadastroDTCDTA cadastroDtcDta;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public Date getDataRetorno() {
		return dataRetorno;
	}

	public void setDataRetorno(Date dataRetorno) {
		this.dataRetorno = dataRetorno;
	}

	public TipoEdiEnum getTipoEdiEnum() {
		return tipoEdiEnum;
	}

	public void setTipoEdiEnum(TipoEdiEnum tipoEdiEnum) {
		this.tipoEdiEnum = tipoEdiEnum;
	}

	public TipoEdiStatusEnum getTipoEdiStatusEnum() {
		return tipoEdiStatusEnum;
	}

	public void setTipoEdiStatusEnum(TipoEdiStatusEnum tipoEdiStatusEnum) {
		this.tipoEdiStatusEnum = tipoEdiStatusEnum;
	}

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public CadastroDTCDTA getCadastroDtcDta() {
		return cadastroDtcDta;
	}

	public void setCadastroDtcDta(CadastroDTCDTA cadastroDtcDta) {
		this.cadastroDtcDta = cadastroDtcDta;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EdiEnvioXML) {
			EdiEnvioXML item = (EdiEnvioXML) obj;
			return item.getId() == id;
		}

		return false;
	}
}
