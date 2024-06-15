/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Instrucciones;

import VariablesGlobales.Variables;
import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

/**
 *
 * @author msWas
 */
public class Incremento extends Instruccion {
    private String id;

    public Incremento(String id, int linea, int col) {
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
                variable.setValor(valorActual + 1);
                return null;
            }
            case tipoDato.DECIMAL -> {
                this.tipo.setTipo(tipoDato.DECIMAL);
                double valorActual = (double) variable.getValor();
                variable.setValor(valorActual + 1);
                return null;
            }
            default -> {
                Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Solo se puede incrementar enteros y decimales", this.linea, this.col));
                return new Errores("SEMANTICO", "Solo se puede incrementar enteros y decimales", this.linea, this.col);
            }
        }
    }
}
