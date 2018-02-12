package conexao.com.util;

import java.text.Normalizer;

public class RemoverCaracterEspecialUTIL {
	
	public static String removerAcentuacao(String texto) {
		return texto = Normalizer.normalize(limparMascaras(texto), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toUpperCase();
	}
	
	private static String limparMascaras(String valor) {
		valor = valor.replace(".", "");
		valor = valor.replace("-", "");
		valor = valor.replace("/", "");
		return valor.trim();
	}
}
