/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fes.aragon.inicio;

import fes.aragon.codigo.ErrorSintactico;
import fes.aragon.codigo.Lexico;
import fes.aragon.codigo.Sym;
import fes.aragon.codigo.Tokens;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author MASH
 */
public class Inicio {
	private boolean error = true;
	private Tokens tokens = null;
	private Lexico analizador = null;
	private static String[][] reglas = new String[4][4];
	private static Stack<String> pila_de_simbolos = new Stack<>();
	private static LinkedList<String> cola = new LinkedList<>();
	private static String ultimo_cola = new String();
	private static String primero_pila = new String();

	public static void main(String[] args) throws ErrorSintactico  {
		Inicio ap = new Inicio();

		BufferedReader buf;
		try {
			buf = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/archivo.txt"));

			ap.analizador = new Lexico(buf);
			ap.siguienteToken();
			
			do {
				try {
					ap.insertarCola();
					ap.pilaDeSimbolos();
				}catch (ErrorSintactico e) {
					ap.cola.clear();
					ap.pila_de_simbolos.clear();
					System.out.println(e.getMessage());
					ap.siguienteToken();
				}
			} while (ap.tokens.getLexema() != Sym.EOF);
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	private void pilaDeSimbolos() throws ErrorSintactico {
		pila_de_simbolos.add(";");
		pila_de_simbolos.add("S");
		ultimo_cola = cola.peek();
		primero_pila = pila_de_simbolos.peek();

		while (tokens.getLexema() != Sym.EOF) {
			while (ultimo_cola != ";" && primero_pila != ";") {
				if (primero_pila == "S" && ultimo_cola == "a") {
					reglaS();
				} else if (primero_pila == "S" && ultimo_cola == "b") {
					reglaS();
				} else if (primero_pila == "S" && ultimo_cola == "c") {
					throw new ErrorSintactico("Error sintactico" + " Linea: " + (tokens.getLinea() + 1));
				} else if (primero_pila == "S" && ultimo_cola == "d") {
					throw new ErrorSintactico("Error sintactico" + " Linea: " + (tokens.getLinea() + 1));
				} else if (primero_pila == "S" && ultimo_cola == ";") {
					throw new ErrorSintactico("Error sintactico" + " Linea: " + (tokens.getLinea() + 1));
				} 

				if (primero_pila == "A" && ultimo_cola == "a") {
					reglaA();
				} else if (primero_pila == "A" && ultimo_cola == "b") {
					lambda();
				} else if (primero_pila == "A" && ultimo_cola == "c") {
					throw new ErrorSintactico("Error sintactico" + " Linea: " + (tokens.getLinea() + 1));
				} else if (primero_pila == "A" && ultimo_cola == "d") {
					throw new ErrorSintactico("Error sintactico" + " Linea: " + (tokens.getLinea() + 1));
				} else if (primero_pila == "A" && ultimo_cola == ";") {
					throw new ErrorSintactico("Error sintactico" + " Linea: " + (tokens.getLinea() + 1));
				}

				if (primero_pila == ultimo_cola) {
					consumir();
				}

				if (primero_pila == "B" && ultimo_cola == "a") {
					throw new ErrorSintactico("Error sintactico" + " Linea: " + (tokens.getLinea() + 1));
				} else if (primero_pila == "B" && ultimo_cola == "b") {
					reglaB();
				} else if (primero_pila == "B" && ultimo_cola == "c") {
					throw new ErrorSintactico("Error sintactico" + " Linea: " + (tokens.getLinea() + 1));
				} else if (primero_pila == "B" && ultimo_cola == "d") {
					throw new ErrorSintactico("Error sintactico" + " Linea: " + (tokens.getLinea() + 1));
				} else if (primero_pila == "B" && ultimo_cola == ";") {
					throw new ErrorSintactico("Error sintactico" + " Linea: " + (tokens.getLinea() + 1));
				}

				if (primero_pila == "C" && ultimo_cola == "a") {
					throw new ErrorSintactico("Error sintactico" + " Linea: " + (tokens.getLinea() + 1));
				} else if (primero_pila == "C" && ultimo_cola == "b") {
					throw new ErrorSintactico("Error sintactico" + " Linea: " + (tokens.getLinea() + 1));
				} else if (primero_pila == "C" && ultimo_cola == "c") {
					reglaC();
				} else if (primero_pila == "C" && ultimo_cola == "d") {
					lambda();
				} else if (primero_pila == "C" && ultimo_cola == ";") {
					throw new ErrorSintactico("Error sintactico" + " Linea: " + (tokens.getLinea() + 1));
				}
				
				
			}

			System.out.println("linea correcta " + (tokens.getLinea() + 1));
			
			
			
			//Si el lexema es ;
			if (tokens.getLexema() == Sym.PUNTOCOMA) {
				//Ir al siguiente token
				siguienteToken();
				
				//Limpio info
				cola.clear();
				pila_de_simbolos.clear();
				
				//Rellenamos la informacion de la siguiente linea en la cola
				insertarCola();
				
				//Rellenamos la pila
				pila_de_simbolos.add(";");
				pila_de_simbolos.add("S");
				
			}
			
			// Leemos el ultimo de la cola y el primero de la pila
			ultimo_cola = cola.peek();
			primero_pila = pila_de_simbolos.peek();

		}
	}

	private void reglaS() {
		System.out.println(pila_de_simbolos);
		// Eliminamos primer elemento de la pila de simbolos
		pila_de_simbolos.pop();

		/*
		 * De acuerdo a la regla de producción de nuestra tabla la sustituimos
		 */
		pila_de_simbolos.add("B");
		pila_de_simbolos.add("A");

		// Leemos el ultimo de la cola y el primero de la pila
		ultimo_cola = cola.peek();
		primero_pila = pila_de_simbolos.peek();

		// Imprimimos el cambio
		System.out.println(pila_de_simbolos);
	}

	private void reglaA() {
		// Eliminamos primer elemento de la pila de simbolos
		pila_de_simbolos.pop();

		/*
		 * De acuerdo a la regla de producción de nuestra tabla la sustituimos
		 */
		pila_de_simbolos.add("a");

		// Leemos el ultimo de la cola y el primero de la pila
		ultimo_cola = cola.peek();
		primero_pila = pila_de_simbolos.peek();

		System.out.println(pila_de_simbolos);
	}

	private void reglaB() {
		// Eliminamos primer elemento de la pila de simbolos
		pila_de_simbolos.pop();

		/*
		 * De acuerdo a la regla de producción de nuestra tabla la sustituimos
		 */
		pila_de_simbolos.add("d");
		pila_de_simbolos.add("C");
		pila_de_simbolos.add("b");

		// Leemos el ultimo de la cola y el primero de la pila
		ultimo_cola = cola.peek();
		primero_pila = pila_de_simbolos.peek();

		System.out.println(pila_de_simbolos);
	}

	private void reglaC() {
		// Eliminamos primer elemento de la pila de simbolos
		pila_de_simbolos.pop();

		/*
		 * De acuerdo a la regla de producción de nuestra tabla la sustituimos
		 */
		pila_de_simbolos.add("c");

		// Leemos el ultimo de la cola y el primero de la pila
		ultimo_cola = cola.peek();
		primero_pila = pila_de_simbolos.peek();

		System.out.println(pila_de_simbolos);
	}

	private void lambda() {
		// Eliminamos primer elemento de la pila de simbolos
		pila_de_simbolos.pop();

		/*
		 * De acuerdo a la regla de producción de nuestra tabla la sustituimos
		 */
		// No agregamos nada por lambda

		// Leemos el ultimo de la cola y el primero de la pila
		ultimo_cola = cola.peek();
		primero_pila = pila_de_simbolos.peek();

		System.out.println(pila_de_simbolos);

		// Regresamos
		return;
	}

	private void consumir() {
		// Removemos el simbolo en común tanto en cola como en pila
		pila_de_simbolos.pop();
		cola.remove();

		// Leemos el ultimo de la cola y el primero de la pila
		ultimo_cola = cola.peek();
		primero_pila = pila_de_simbolos.peek();

		System.out.println(pila_de_simbolos);

		return;
	}

	private void insertarCola() throws ErrorSintactico {
		while (tokens.getLexema() != Sym.PUNTOCOMA) {
			if (tokens.getLexema() == Sym.A) {
				cola.add("a");
				siguienteToken();
			}
			if (tokens.getLexema() == Sym.B) {
				cola.add("b");
				siguienteToken();
			}
			if (tokens.getLexema() == Sym.C) {
				cola.add("c");
				siguienteToken();
			}
			if (tokens.getLexema() == Sym.D) {
				cola.add("d");
				siguienteToken();
			}
			if (tokens.getLexema() == Sym.PUNTOCOMA) {
				cola.add(";");
			}
		}
	}
	
	private void errorSintactico() {
        this.error = false;
        //descartar todo hasta encontrar ;            
        do {
            System.out.println("Error sintactico linea " + (tokens.getLinea() + 1 ));
            if (tokens.getLexema() != Sym.PUNTOCOMA) {
                siguienteToken();
            }
        } while (tokens.getLexema() != Sym.PUNTOCOMA && tokens.getLexema() != Sym.EOF);

    }

	private void siguienteToken() {
		try {
			tokens = analizador.yylex();
			if (tokens == null) {
				tokens = new Tokens("EOF", Sym.EOF, 0, 0);
				throw new IOException("Fin de archivo");
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

	}

}
