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
public class AsignacionVar extends Instruccion{
    
    private String id;
    private Instruccion exp;

    public AsignacionVar(Instruccion exp, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.exp = exp;
    }
    
    public AsignacionVar(String id, Instruccion exp, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.exp = exp;
    }
    

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        //variable exista
        var variable = tabla.getVariable(id);
        if (variable == null) {
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Variable no exitente", this.linea, this.col));
            return new Errores("SEMANTICO", "Variable no exitente",
                    this.linea, this.col);
        }

        // interpretar el nuevo valor a asignar
        var newValor = this.exp.interpretar(arbol, tabla);
        if (newValor instanceof Errores) {
            return newValor;
        }

        //validar tipos
        if (variable.getTipo().getTipo() != this.exp.tipo.getTipo()) {
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Tipos erroneos en asignacion", this.linea, this.col));
            return new Errores("SEMANTICO", "Tipos erroneos en asignacion",
                    this.linea, this.col);
        }
        this.tipo.setTipo(variable.getTipo().getTipo());
        boolean asignacion = (boolean)variable.setValor(newValor);
        if (!asignacion) {
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "No se puede cambiar el valor de una variable constante", this.linea, this.col));
            return new Errores("SEMANTICO", "No se puede cambiar el valor de una variable constante", this.linea, this.col);
        }
        return null;
    }

    @Override
    public String generarast(Arbol arbol, String anterior) {
return "";    }
}
