/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Instrucciones;

import VariablesGlobales.Variables;
import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

/**
 *
 * @author msWas
 */
public class For extends Instruccion {

    private Instruccion asignacion;
    private Instruccion condicion;
    private Instruccion actualizacion;
    private LinkedList<Instruccion> instrucciones;

    public For(Instruccion asignacion, Instruccion condicion, Instruccion actualizacion, LinkedList<Instruccion> instrucciones, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.asignacion = asignacion;
        this.condicion = condicion;
        this.actualizacion = actualizacion;
        this.instrucciones = instrucciones;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        //creamos un nuevo entorno
        var newTabla = new tablaSimbolos(tabla);
        newTabla.setNombre(tabla.getNombre() + "FOR");

        // asignacion/declaracion
        var res1 = this.asignacion.interpretar(arbol, newTabla);
        if (res1 instanceof Errores) {
                        Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Error en condicional del WHILE", this.linea, this.col));
            return res1;
        }

        //validar la condicion -> Booleano
        var cond = this.condicion.interpretar(arbol, newTabla);
        if (cond instanceof Errores) {
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Error en la declaracion del FOR", this.linea, this.col));
            return cond;
        }
        
        // ver que cond sea booleano
        if (this.condicion.tipo.getTipo() != tipoDato.BOOLEANO) {
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Expresion invalida", this.linea, this.col));
            return new Errores("SEMANTICO", "Expresion invalida",
                    this.linea, this.col);
        }
        if(cond instanceof String){
            cond = (cond.toString().toLowerCase()).equals("true") ? true : false;
        }
        
        if(cond instanceof String){
            cond = (cond.toString().toLowerCase()).equals("true") ? true : false;
        }

        while ((boolean) this.condicion.interpretar(arbol, newTabla)) {
            //nuevo entorno
            var newTabla2 = new tablaSimbolos(newTabla);
            newTabla.setNombre(tabla.getNombre() + "FOR-INTERNO");

            //ejecutar instrucciones
            for (var i : this.instrucciones) {
                if (i instanceof Break) {
                    return null;
                }
                var resIns = i.interpretar(arbol, newTabla2);
                if (resIns instanceof Break) {
                    return null;
                }
            }

            //actualizar la variable
            var act = this.actualizacion.interpretar(arbol, newTabla);
            if (act instanceof Errores) {
                return act;
            }
        }
        return null;
    }
    
}
