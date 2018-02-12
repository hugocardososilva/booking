package com.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import conexao.com.bean.EnviarSenhaComumBean;

@ManagedBean(name = "MBEnviarSenha")
@ViewScoped
public class EnviarSenhaBean extends EnviarSenhaComumBean  {
	public static final String PAGINA_LOGIN = "/portalmapa/";
}
