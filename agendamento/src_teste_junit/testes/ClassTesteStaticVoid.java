package testes;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import conexao.com.util.JSFUtil;

public class ClassTesteStaticVoid {

	public static void main(String[] args) throws ParseException {

		
		System.out.println(getIp());
		
		System.out.println(JSFUtil.getCaminhoAplicacaoPath());
		
		String texto = new String();
		texto = texto.concat("teste").concat("novo");
		
		System.out.println(texto);
		
		Date dataHoje = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		
		int valor = Integer.valueOf(sdf.format(dataHoje));
		
		if (valor >=15 ) {
			System.out.println(sdf.format(dataHoje));
		}
		
		
		
	}
	
	private static String getIp() {
        String ipAddress = null;
        Enumeration<NetworkInterface> net = null;
        try {
            net = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        while (net.hasMoreElements()) {
            NetworkInterface element = net.nextElement();
            Enumeration<InetAddress> addresses = element.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress ip = addresses.nextElement();

                if (ip.isSiteLocalAddress()) {
                    ipAddress = ip.getHostAddress();
                }           
            }
        }
        return ipAddress;
    }
	
	private static String limparMascaras(String valor) {
		valor = valor.replace(".", "");
		valor = valor.replace("-", "");
		valor = valor.replace("/", "");
		valor = valor.replace("(", "");
		valor = valor.replace(")", "");
		return valor.trim();
	}
	

}
