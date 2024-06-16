/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Instrucciones;

import VariablesGlobales.Variables;
import Abstracto.Instruccion;
import Excepciones.Errores;
import Simbolos.Arbol;
import Simbolos.Tipo;
import Simbolos.tablaSimbolos;
import Simbolos.tipoDato;

/**
 *
 * @author msWas
 */
public class Decremento extends Instruccion {
    private String id;

    public Decremento(String id, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var variable = tabla.getVariable(id);

        if (variable == null) {
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Variable no existente", this.linea, this.col));
            return new Errores("SEMANTICO", "Variable no existente", this.linea, this.col);
        }

        var tipo1 = variable.getTipo().getTipo();

        switch (tipo1) {
            case tipoDato.ENTERO -> {
                this.tipo.setTipo(tipoDato.ENTERO);
                int valorActual = (int) variable.getValor();
                variable.setValor(valorActual - 1);
                return null;
            }
            case tipoDato.DECIMAL -> {
                this.tipo.setTipo(tipoDato.DECIMAL);
                double valorActual = (double) variable.getValor();
                variable.setValor(valorActual - 1);
                return null;
            }
            default -> {
                Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Solo se puede decrementar enteros y decimales", this.linea, this.col));
                return new Errores("SEMANTICO", "Solo se puede decrementar enteros y decimales", this.linea, this.col);
            }
        }
    }
}

