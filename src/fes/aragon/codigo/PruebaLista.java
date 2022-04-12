package fes.aragon.codigo;

import java.util.LinkedList;

public class PruebaLista {
	public static void main(String[] args) {
		LinkedList<String> cola = new LinkedList<>();
		cola.add("a");
		cola.add("b");
		cola.add("c");
		cola.add("d");
		cola.add(";");
		
		for (int i = 0; i < cola.size(); i++) {
			System.out.println("Cola: " + cola);
			cola.remove();
		}
	}
}
