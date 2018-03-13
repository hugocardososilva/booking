package com.proxy;

import seguranca.com.entidade.TipoContainer;

public class ContainerProxy {
	
	private int id;
	private String numeroContainer;
	private TipoContainer tipoContainer;
	
	
	public ContainerProxy() {
		// TODO Auto-generated constructor stub
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNumeroContainer() {
		return numeroContainer;
	}


	public void setNumeroContainer(String numeroContainer) {
		this.numeroContainer = numeroContainer;
	}


	public TipoContainer getTipoContainer() {
		return tipoContainer;
	}


	public void setTipoContainer(TipoContainer tipoContainer) {
		this.tipoContainer = tipoContainer;
	}
	
	
	
}
