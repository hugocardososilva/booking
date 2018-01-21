package com.bean;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.mail.EmailException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;

import com.entidade.UserComissariaAgendamento;
import com.entidade.UserImportadorAgendamento;
import com.rn.UserComissariaAgendamentoRN;
import com.rn.UserImportadorAgendamentoRN;

import conexao.com.bean.AbstractLazyDataModel;
import conexao.com.bean.UserCadastroComumBean;
import conexao.com.util.FiltroTabelaBean;
import conexao.com.util.JSFMessageUtil;
import seguranca.com.entidade.User;

@ViewScoped
@ManagedBean
public class UserCadastroBean extends UserCadastroComumBean {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	private LazyDataModel<User> lazyModel;
	private byte[] bytesImagem;

	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;

	private UserImportadorAgendamentoRN userImportadorAgendamentoRN;

	private UserComissariaAgendamentoRN userComissariaAgendamento;

	public UserMB getUserMB() {
		return userMB;
	}

	public void setUserMB(UserMB userMB) {
		this.userMB = userMB;
	}

	@Override
	public LazyDataModel<User> getLazyModel() {
		if (lazyModel == null) {
			lazyModel = new AbstractLazyDataModel<User>(User.sql_agendamento, User.sqlCount_agendamento, getWhereSQL(),
					null);
		}

		return lazyModel;
	}

	@Override
	public void filtroTabela() {
		try {
			setWhereSQL(null);
			lazyModel = null;

			if (!getFiltroGeral().isEmpty()) {

				getFiltrosParametros().add(User.NAME);
				getFiltrosParametros().add(User.CAMPO_EMAIL);
				getFiltrosParametros().add(User.CAMPO_CPF);
				getFiltrosParametros().add(User.CAMPO_ID);

				setWhereSQL(FiltroTabelaBean.montaCondicaoSqlSemWhereOR(getFiltroGeral(), getFiltrosParametros()));
			}
		} catch (Exception e) {
			setFiltrosParametros(null);
		}
	}

	@Override
	public String getFiltroGeral() {
		return filtroGeral;
	}

	@Override
	public void setFiltroGeral(String filtroGeral) {
		this.filtroGeral = filtroGeral;
	}

	public void setLazyModel(LazyDataModel<User> lazyModel) {
		this.lazyModel = lazyModel;
	}

	@Override
	public boolean isSalvarImportadorMaster() {
		return salvarImportadorMaster;
	}

	@Override
	public void setSalvarImportadorMaster(boolean salvarImportadorMaster) {
		this.salvarImportadorMaster = salvarImportadorMaster;
		if (salvarImportadorMaster) {
			salvarImportadorMaster();
		}
	}

	public void salvarImportadorMaster() {
		try {
			UserImportadorAgendamento achou = getUserImportadorAgendamentoRN()
					.retornaImportadorUsuarioMaster(getUsuario());

			if (achou == null) {
				UserImportadorAgendamento item = new UserImportadorAgendamento();
				item.setUsuario(getUsuario());

				getUserImportadorAgendamentoRN().alterar(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	@Override
	public boolean isSalvarComissariaMaster() {
		return salvarComissariaMaster;
	}

	@Override
	public void setSalvarComissariaMaster(boolean salvarComissariaMaster) {
		this.salvarComissariaMaster = salvarComissariaMaster;
		if (salvarComissariaMaster) {
			salvarComissariaMaster();
		}
	}

	public void salvarComissariaMaster() {
		try {
			UserComissariaAgendamento achou = getUserComissariaAgendamentoRN().retornaUsuarios(getUsuario());
			if (achou== null) {
				UserComissariaAgendamento item = new UserComissariaAgendamento();
				item.setUsuario(getUsuario());
				
				getUserComissariaAgendamentoRN().alterar(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	private UserComissariaAgendamentoRN getUserComissariaAgendamentoRN() {
		if (userComissariaAgendamento == null) {
			userComissariaAgendamento = new UserComissariaAgendamentoRN();
		}
		return userComissariaAgendamento;
	}

	private UserImportadorAgendamentoRN getUserImportadorAgendamentoRN() {
		if (userImportadorAgendamentoRN == null) {
			userImportadorAgendamentoRN = new UserImportadorAgendamentoRN();
		}

		return userImportadorAgendamentoRN;
	}
	
	
	public void upload(FileUploadEvent evento) throws SQLException, EmailException {
		try {
			UploadedFile uploadedFile = evento.getFile();
			InputStream is = new BufferedInputStream(uploadedFile.getInputstream());
			byte[] bytes = new byte[(int) is.available()];

			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
			is.close();
			
			getUsuario().setImagem(bytes);
			
			setBytesImagem(bytes);
			
			getUserRN().salvar(getUsuario());

			JSFMessageUtil.adicionarMensagemSucesso("Upload realizado com sucesso.");
		} catch (Throwable e) {
			e.printStackTrace();
			JSFMessageUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	public byte[] getBytesImagem() {
		return bytesImagem;
	}

	public void setBytesImagem(byte[] bytesImagem) {
		this.bytesImagem = bytesImagem;
	}

	

}