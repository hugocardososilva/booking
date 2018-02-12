package com.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.enums.TiposParametrosEnum;

import seguranca.com.enums.TelasEntidadesEnum;

@Entity
@Table(name="SYS_PARAMETROS")

public class ParametrosSistema {
	public final static String sql = "select p from ParametrosSistema p ";
	public final static String sqlCount = "select COUNT(p) from ParametrosSistema p ";

	@Id
	@SequenceGenerator(name = "SEQ_PARAMETROS_ID", sequenceName = "SEQ_PARAMETROS_ID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_PARAMETROS_ID")

	@Column(name="PARAMETROS_ID")
	private Integer id;
	
	private TiposParametrosEnum tiposParametros; 

	private TelasEntidadesEnum telasEntidades; 

	@Column(name="VALOR")
	private String valor;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TiposParametrosEnum getTiposParametros() {
		return tiposParametros;
	}

	public void setTiposParametros(TiposParametrosEnum tiposParametros) {
		this.tiposParametros = tiposParametros;
	}

	public TelasEntidadesEnum getTelasEntidades() {
		return telasEntidades;
	}

	public void setTelasEntidades(TelasEntidadesEnum telasEntidades) {
		this.telasEntidades = telasEntidades;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	
	@Override
	public int hashCode() {
		return id;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ParametrosSistema) {
			ParametrosSistema parametros = (ParametrosSistema) obj;
			return parametros.getId() == id;
		}

		return false;

	}
	
	
}
