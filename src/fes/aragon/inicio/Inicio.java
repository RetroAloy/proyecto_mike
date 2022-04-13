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
    
    public static void main(String[] args) {
        Inicio ap = new Inicio();
        
        
        //Reglas de producción
        reglas[0][0] = "S:=AB";
        reglas[0][1] = "S:=AB";
        reglas[0][2] = "error";
        reglas[0][3] = "error";
        reglas[1][0] = "A:=a";
        reglas[1][1] = "A:=l";
        reglas[1][2] = "error";
        reglas[1][3] = "error";
        reglas[2][0] = "eror";
        reglas[2][1] = "B:=bCd";
        reglas[2][2] = "error";
        reglas[2][3] = "error";
        reglas[3][0] = "error";
        reglas[3][1] = "error";
        reglas[3][2] = "C:=c";
        reglas[3][3] = "C:=l";
        
        
        BufferedReader buf;
        try {
            buf = new BufferedReader(
                    new FileReader(System.getProperty("user.dir")
                            + "/archivo.txt"));
            
            
            ap.analizador = new Lexico(buf);
            ap.siguienteToken();
            
            ap.insertarCola();
            ap.pilaDeSimbolos();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ErrorSintactico e) {
        	e.printStackTrace();
        }
    }
    
    private void pilaDeSimbolos() throws ErrorSintactico {
    	pila_de_simbolos.add(";");
    	pila_de_simbolos.add("S");
    	String ultimo_cola = cola.peek();
    	String primero_pila = pila_de_simbolos.peek();
    	
    	if (primero_pila == "S" && ultimo_cola == "a") {
    		reglaS(ultimo_cola, primero_pila);
    	} else if (primero_pila == "S" && ultimo_cola == "b") {
    		reglaS(ultimo_cola, primero_pila);
    	} else if (primero_pila == "S" && ultimo_cola == "c") {
    		throw new ErrorSintactico("Error sintactico"+ " Linea: " + (tokens.getLinea()+1));
    	} else if (primero_pila == "S" && ultimo_cola == "c") {
    		throw new ErrorSintactico("Error sintactico"+ " Linea: " +(tokens.getLinea()+1));
    	}
    	
    	System.out.println(pila_de_simbolos);
   }
    
    private void reglaS(String ultimo_cola , String primero_pila) {
    	//Eliminamos primer elemento de la pila de simbolos
		pila_de_simbolos.pop();
		
		/*
		 * De acuerdo a la regla de producción de nuestra tabla
		 * la sustituimos
		*/
		pila_de_simbolos.add("B");
		pila_de_simbolos.add("A");
		
		//Leemos el ultimo de la cola y el primero de la pila
		ultimo_cola = cola.peek();
		primero_pila = pila_de_simbolos.peek();
    }
    
    private void insertarCola() {
    	if(tokens.getLexema() == Sym.A) {
    		cola.add("a");
    		siguienteToken();
    	}
    	if(tokens.getLexema() == Sym.B) {
    		cola.add("b");
    		siguienteToken();
    	}
    	if(tokens.getLexema() == Sym.C) {
    		cola.add("c");
    		siguienteToken();
    	}
    	if(tokens.getLexema() == Sym.D) {
    		cola.add("d");
    		siguienteToken();
    	}
    	if(tokens.getLexema() == Sym.PUNTOCOMA) {
    		cola.add(";");
    	}
    }
    

    

    private void siguienteToken() {
        try {
            tokens = analizador.yylex();
            if (tokens == null) {
                tokens = new Tokens("EOF", Sym.EOF, 0, 0);
                throw new IOException("Fin Archivo");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
