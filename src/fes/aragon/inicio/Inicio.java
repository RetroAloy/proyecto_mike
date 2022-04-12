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
        Inicio ap2 = new Inicio();
        
        
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
            System.out.println(ap.cola);

        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    
    private void insertarCola 				
    () {
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
    

    private void errorSintactico() {
        this.error = false;
        //descartar todo hasta encontrar ;            
        do {
            System.out.println(tokens.toString());
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
                throw new IOException("Fin Archivo");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
