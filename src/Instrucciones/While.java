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
//expresion instuccion
//si condicion ejecuto instruccion -> valido otra vez expresion (condicion)
public class While extends Instruccion{
    private Instruccion condicion;
    private LinkedList<Instruccion> instrucciones;
    private boolean erroresEncontrados;
    private Errores error;
    
    public While(Instruccion condicion, LinkedList<Instruccion> instrucciones, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.condicion = condicion;
        this.instrucciones = instrucciones;
    }
    
        @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        //creamos un nuevo entorno
        var newTabla = new tablaSimbolos(tabla);
        newTabla.setNombre(tabla.getNombre() + "WHILE");

        //validar la condicion -> Booleano
        var cond = this.condicion.interpretar(arbol, tabla);
        if (cond instanceof Errores) {
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Error en condicional del WHILE", this.linea, this.col));
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

        while ((boolean) this.condicion.interpretar(arbol, newTabla)) {
            //nuevo entorno
            var newTabla2 = new tablaSimbolos(newTabla);
            newTabla.setNombre(tabla.getNombre() + "WHILE-INTERNO");

            //ejecutar instrucciones
            for (var i : this.instrucciones) {
                if (i instanceof Break) {
                    return null;
                }
                
                if (i instanceof Continue) {
                    break;
                }
                
                var resIns = i.interpretar(arbol, newTabla2);
                if (resIns instanceof Break) {
                    return null;
                }
                
                if (resIns instanceof Continue) {
                    break;
                }
            }

            //actualizar la variable
            var act = this.condicion.interpretar(arbol, newTabla);
            if (act instanceof Errores) {
                return act;
            }
        }

        if(erroresEncontrados && error != null){
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Error encotrado dentro de sentencia ELSE", this.linea, this.col));
            return new Errores("SEMANTICO", "Error encotrado dentro de sentencia ELSE", this.linea, this.col);
        }
        
        return null;
    }
    
}
