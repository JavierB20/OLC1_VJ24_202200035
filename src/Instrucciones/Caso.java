/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Instrucciones;

import VariablesGlobales.Variables;
import Abstracto.Instruccion;
import Excepciones.Errores;
import java.util.LinkedList;
import Simbolos.Arbol;
import Simbolos.tablaSimbolos;

/**
 *
 * @author msWas
 */
public class Caso extends Instruccion {
    private Instruccion expresion;
    private LinkedList<Instruccion> instrucciones;
    Variables vars = new Variables();

    public Caso(LinkedList<Instruccion> instrucciones, int linea, int col) {
        super(null, linea, col);
        this.instrucciones = instrucciones;
    }
    
    public Caso(Instruccion expresion, LinkedList<Instruccion> instrucciones, int linea, int col) {
        super(null, linea, col);
        this.expresion = expresion;
        this.instrucciones = instrucciones;
    }

    public Instruccion getExpresion() {
        return expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        for (Instruccion instruccion : instrucciones) {
            Object resultado = instruccion.interpretar(arbol, tabla);
            if (resultado instanceof Instruccion) {
                if (resultado instanceof Break) {
                    Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Error 'Break' fuera de ciclo", this.linea, this.col));
                    return new Errores("SEMANTICO", "Error 'Break' fuera de ciclo", this.linea, this.col);
                }
                if (resultado instanceof Errores) {
                    Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Expresion invalida en CASE", this.linea, this.col));
                    return new Errores("SEMANTICO", "Expresion invalida en CASE",this.linea, this.col);
                }
                return resultado;
            }
        }
        return null;
    }

    @Override
    public String generarast(Arbol arbol, String anterior) {
return "";    }
}
