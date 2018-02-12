package conexao.com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import conexao.com.enums.TipoFiltroEnum;

public class FiltroTabelaBean {
	/**
	 * Monta a condi��o WHERE dos filtros, para efetuar a pesquisa na tabela principal
	 * @param valor
	 * @param listaFiltros
	 * @return String Exemplo : ( where (campo_1 like '%valor%' or campo_2 like '%valor%')  )
	 * @author murilonadalin
	 * @serialData 01-09-2017
	 */
	public static String montaCondicaoSqlWhereOR(String valor, List<String> listaFiltros) {
		String sql = null;
		int seq = 0;
		for (String itemString : listaFiltros) {
			seq += 1;

			if (seq == 1) {
				sql = "where (" + itemString.trim().toString() + " like '%" + valor.trim().toUpperCase() + "%'";
			} else {
				sql = sql + " or " + itemString.trim().toString() + " like '%" + valor.trim().toUpperCase() + "%'";
			}
		}
		sql = sql + " )";

		return sql;
	}

	/**
	 * Monta a condi��o WHERE dos filtros, para efetuar a pesquisa na tabela principal
	 * @param valor
	 * @param listaFiltros
	 * @return String Exemplo : ( and (campo_1 like '%valor%' or campo_2 like '%valor%')  )
	 * @author murilonadalin
	 * @serialData 01-09-2017
	 */
	public static String montaCondicaoSqlSemWhereOR(String valor, List<String> listaFiltros) {
		String sql = null;
		int seq = 0;
		for (String itemString : listaFiltros) {
			seq += 1;
			
			if (seq == 1) {
				sql = " and (" + itemString.trim().toString() + " like '%" + valor.trim().toUpperCase() + "%'";
			} else {
				sql = sql + " or " + itemString.trim().toString() + " like '%" + valor.trim().toUpperCase() + "%'";
			}
		}
		sql = sql + " )";
		
		return sql;
	}

	public static String montaCondicaoSqlWhereAND(String sqlWhereOR, String filtroCampo, TipoFiltroEnum tipoFiltro,
			Integer valor) {
		if (tipoFiltro.equals(TipoFiltroEnum.TODOS)) {
			sqlWhereOR = sqlWhereOR + " and ( " + filtroCampo + " >= 0) ";
		} else {
			sqlWhereOR = sqlWhereOR + " and ( " + filtroCampo + " = " + valor + " )";
		}
		if (tipoFiltro.equals(TipoFiltroEnum.IGUAL_COM_WHERE)) {
			sqlWhereOR = " where " + filtroCampo + " = " + valor;
		}
		return sqlWhereOR;
	}

	public static String montaCondicaoSqlWhereIN(String filtroCampo, String sqlWhereOR, TipoFiltroEnum tipoFiltro,
			String sqlWhereIN) {
		if (tipoFiltro.equals(TipoFiltroEnum.IN)) {
			sqlWhereOR = " where " + filtroCampo + " in " + sqlWhereOR;
		}
		if (tipoFiltro.equals(TipoFiltroEnum.IN_SEM_WHERE)) {
			sqlWhereOR = sqlWhereOR + " and ( " + filtroCampo + " in " + sqlWhereIN + " )";
		}
		if (tipoFiltro.equals(TipoFiltroEnum.IN_SEM_WHERE_OR)) {
			sqlWhereOR = sqlWhereOR + " or ( " + filtroCampo + " in ( " + sqlWhereIN + " ) )";
		}
		return sqlWhereOR;
	}
	
	public static String montarCondicaoSqlWhereBetween(Date dataInicio, Date dataFim, String dataCampo) {
		SimpleDateFormat dataFormatada = new SimpleDateFormat("yyyy-MM-dd");
		String dataInicioS = dataFormatada.format(dataInicio);
		String dataFimS = dataFormatada.format(dataFim);
		String sql = "";
		
			sql = " where " + dataCampo + " BETWEEN " + dataFormatada.format(dataInicio) + " AND " + dataFormatada.format(dataFim);
	
		return sql;
		
		
	}

}
