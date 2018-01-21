package com.rn;

import java.io.Serializable;
import java.util.List;

import com.dao.ParametrosSistemaDAO;
import com.entidade.ParametrosSistema;
import com.enums.TiposParametrosEnum;

import seguranca.com.enums.TelasEntidadesEnum;

public class ParametrosSistemasRN implements Serializable {

	private static final long serialVersionUID = 1L;

	private ParametrosSistemaDAO dao = new ParametrosSistemaDAO();

	private ParametrosSistemasRN rnParametros;

	public void incluir(ParametrosSistema item) throws Exception {
		dao.beginTransaction();
		dao.save(item);
		dao.commitAndCloseTransaction();
	}

	public void alterar(ParametrosSistema item) throws Exception {
		dao.beginTransaction();

		dao.alterar(item);
		dao.commitAndCloseTransaction();
	}

	public ParametrosSistema localizar(int itemId) {
		dao.beginTransaction();
		ParametrosSistema item = dao.localizarPorID(itemId);
		dao.closeTransaction();
		return item;
	}

	public void deletar(ParametrosSistema item) {
		dao.beginTransaction();
		ParametrosSistema persistedemp = dao.localizaPorReferencias(item.getId());
		dao.delete(persistedemp);
		dao.commitAndCloseTransaction();
	}

	public List<ParametrosSistema> listaTodos() {
		dao.beginTransaction();
		List<ParametrosSistema> result = dao.listaTodosRegistros();
		dao.closeTransaction();
		return result;
	}

	/**
	 * Retorna o valor do parametro referente a tela e ao tipo informado
	 * 
	 * @param tela
	 * @param pipoParametro
	 * @return String
	 */
	public String retornaValorParametro(TelasEntidadesEnum tela, TiposParametrosEnum pipoParametro) {

		for (ParametrosSistema item : listaTodos()) {
			if (item.getTelasEntidades().getCodigo() == tela.getCodigo()) {
				return item.getValor();
			}
		}

		return null;
	}

	/**
	 * Retorna o valor do parametro referente a tela e ao tipo informado
	 * 
	 * @param tela
	 * @param pipoParametro
	 * @return Integer
	 */
	public Integer retornaValorParametroInteiro(TelasEntidadesEnum tela, TiposParametrosEnum pipoParametro) {

		for (ParametrosSistema item : listaTodos()) {
			if (item.getTelasEntidades().getCodigo() == tela.getCodigo()) {
				return Integer.valueOf(item.getValor());
			}
		}

		return null;
	}

	public Integer retornaQtdeMessas() {
		Integer parametro = 0;
		if (parametro == 0) {
//			parametro = getRnParametros().retornaValorParametroInteiro(TelasEntidadesEnum.VENDA_COMANDA_MESSAS, TiposParametrosEnum.VENDA_COMANDA_MESSAS);
		}
		return parametro;
	}

	public ParametrosSistemasRN getRnParametros() {
		if (rnParametros == null) {
			rnParametros = new ParametrosSistemasRN();
		}
		return rnParametros;
	}
}
