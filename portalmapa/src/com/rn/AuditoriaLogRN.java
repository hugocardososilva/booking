package com.rn;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.envers.AuditReader;

import com.dao.AuditEntityDAO;
import com.dao.AudityEntityRN;

import seguranca.com.entidade.CadastroBL;
import seguranca.com.entidade.CadastroBLContanier;
import seguranca.com.entidade.LogInspecaoLiberadoMapa;

/**
 * Class criada para controle dos metodos RN
 * 
 * @author murilonadalin
 * @since 30-08-2017
 */
public class AuditoriaLogRN {

	private AudityEntityRN audityEntityRN;
	private AuditEntityDAO auditEntityDAO;

	private AuditEntityDAO getDAO() {
		if (auditEntityDAO == null) {
			auditEntityDAO = new AuditEntityDAO();
		}
		return auditEntityDAO;
	}

	private AudityEntityRN getAudityEntityRN() {
		if (audityEntityRN == null) {
			audityEntityRN = new AudityEntityRN();
		}
		return audityEntityRN;
	}

	/**
	 * Metodo criado, para mostrar os Logs da auditoria referente a modificação
	 * realizada da Entidade ( CadastroBLContanier )
	 * 
	 * @param entidade
	 *            ( CadastroBL )
	 * @return List<CadastroBLContanier>
	 * @throws Exception
	 * @author murilonadalin
	 * @serialData 30-08-2017
	 */
	public List<LogInspecaoLiberadoMapa> getLogRegistrosContainer(CadastroBL entidade) throws Exception {
		List<LogInspecaoLiberadoMapa> lista = new ArrayList<LogInspecaoLiberadoMapa>();

		AuditReader reader = getDAO().getAuditoriaReader();
		if (entidade.getListaCadastroBLContanier().size() > 0) {

			for (CadastroBLContanier itens : entidade.getListaCadastroBLContanier()) {
				List<Number> revisoes = reader.getRevisions(CadastroBLContanier.class, itens.getId());
				for (Number rev : revisoes) {
					CadastroBLContanier entidadeAuditoria = reader.find(CadastroBLContanier.class, itens.getId(), rev);

					if (entidadeAuditoria.isInspecao() || entidadeAuditoria.isLiberado()) {
						LogInspecaoLiberadoMapa log = new LogInspecaoLiberadoMapa();

						com.entidade.AuditEntity auditoriaUsuario = getAudityEntityRN().localizar(rev.intValue());

						entidadeAuditoria.setLogNomeUsuario(auditoriaUsuario.getUsuario());
						entidadeAuditoria.setLogDataAlteracao(auditoriaUsuario.getDataRevisao());

						log.setInspecao(entidadeAuditoria.isInspecao());
						log.setLiberado(entidadeAuditoria.isLiberado());
						log.setLogDataAlteracao(auditoriaUsuario.getDataRevisao());
						log.setLogNomeUsuario(auditoriaUsuario.getUsuario());
						log.setNumeroContainer(entidadeAuditoria.getNumeroContanier());
						log.setDataEnvioMapa(entidade.getDataEnvioMapa());

						lista.add(log);
					}
				}
			}
		} else {
			List<Number> revisoesBL = reader.getRevisions(CadastroBL.class, entidade.getId());
			for (Number rev : revisoesBL) {
				CadastroBL entidadeAuditoria = reader.find(CadastroBL.class, entidade.getId(), rev);

				if (entidadeAuditoria.isInspecao() || entidadeAuditoria.isLiberado()) {
					LogInspecaoLiberadoMapa log = new LogInspecaoLiberadoMapa();

					com.entidade.AuditEntity auditoriaUsuario = getAudityEntityRN().localizar(rev.intValue());

					log.setInspecao(entidadeAuditoria.isInspecao());
					log.setLiberado(entidadeAuditoria.isLiberado());
					log.setLogDataAlteracao(auditoriaUsuario.getDataRevisao());
					log.setLogNomeUsuario(auditoriaUsuario.getUsuario());
					log.setDataEnvioMapa(entidade.getDataEnvioMapa());

					lista.add(log);
				}
			}

		}
		return lista;
	}

	public String getLogRegistrosBLData(CadastroBL entidade) throws Exception {
		String nome = "";
		
		AuditReader reader = getDAO().getAuditoriaReader();
		List<Number> revisoesBL = reader.getRevisions(CadastroBL.class, entidade.getId());
		for (Number rev : revisoesBL) {
			CadastroBL entidadeAuditoria = reader.find(CadastroBL.class, entidade.getId(), rev);
			
			if (entidadeAuditoria.isInspecao() || entidadeAuditoria.isLiberado()) {
				LogInspecaoLiberadoMapa log = new LogInspecaoLiberadoMapa();
				
				com.entidade.AuditEntity auditoriaUsuario = getAudityEntityRN().localizar(rev.intValue());
				
				log.setInspecao(entidadeAuditoria.isInspecao());
				log.setLiberado(entidadeAuditoria.isLiberado());
				log.setLogDataAlteracao(auditoriaUsuario.getDataRevisao());
				log.setLogNomeUsuario(auditoriaUsuario.getUsuario());
				
				nome = auditoriaUsuario.getUsuario();
			}
		}
		
		return nome;
	}

}
