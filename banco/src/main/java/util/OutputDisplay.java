package util;

public class OutputDisplay {

	public void exibeTexto(String algumTexto) {
		long hora = System.currentTimeMillis();
		
		System.out.println("hora = " + hora + " - " + algumTexto);
	}
}
