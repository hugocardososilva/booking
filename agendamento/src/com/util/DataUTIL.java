package com.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Class com metodos de Datas
 * 
 * @author murilonadalin
 *
 */
public class DataUTIL {
	/**
	 * 1 => Domingo
	 * 2 => Segunda
	 * 3 => Terça
	 * 4 => Quarta
	 * 5 => Quinta
	 * 6 => Sexta
	 * t => Sabado
	 * @param date
	 * @return int => dia da semana
	 */
	public static int verificaFinalDeSemana(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(Calendar.DAY_OF_WEEK);
	}

}
