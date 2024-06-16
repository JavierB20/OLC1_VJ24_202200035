/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Expresiones;

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
public class AccesoVar extends Instruccion {
    private String id;
    Variables vars = new Variables();

    public AccesoVar(String id, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var valor = tabla.getVariable(this.id);
        if (valor == null) {
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Variable no existente", this.linea, this.col));
            return new Errores("SEMANTICA", "Variable no existente",
                    this.linea, this.col);
        }
        this.tipo.setTipo(valor.getTipo().getTipo());
        return valor.getValor();
    }
}
