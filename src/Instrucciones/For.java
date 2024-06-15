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
        // Crear un nuevo entorno
        var newTabla = new tablaSimbolos(tabla);
        newTabla.setNombre(tabla.getNombre() + "FOR");

        // Asignación/declaración
        var res1 = this.asignacion.interpretar(arbol, newTabla);
        if (res1 instanceof Errores) {
            Variables.addToGlobalLinkedList((Errores) res1);
            return res1;
        }

        // Validar la condición -> Booleano
        var cond = this.condicion.interpretar(arbol, newTabla);
        if (cond instanceof Errores) {
            Variables.addToGlobalLinkedList((Errores) cond);
            return cond;
        }

        // Verificar que la condición sea booleana
        if (this.condicion.tipo.getTipo() != tipoDato.BOOLEANO) {
            Errores error = new Errores("SEMANTICO", "Expresión inválida", this.linea, this.col);
            Variables.addToGlobalLinkedList(error);
            return error;
        }

        if (cond instanceof String) {
            cond = ((String) cond).equalsIgnoreCase("true");
        }

        while ((boolean) this.condicion.interpretar(arbol, newTabla)) {
            // Nuevo entorno
            var newTabla2 = new tablaSimbolos(newTabla);
            newTabla2.setNombre(tabla.getNombre() + "FOR-INTERNO");

            // Ejecutar instrucciones
            for (var i : this.instrucciones) {
                var resIns = i.interpretar(arbol, newTabla2);
                if (resIns instanceof Break) {
                    return null;
                }
                
                if (i instanceof Continue) {
                    break;
                }
                
                if (resIns instanceof Errores) {
                    Variables.addToGlobalLinkedList((Errores) resIns);
                    return resIns;
                }
            }

            // Actualizar la variable
            var act = this.actualizacion.interpretar(arbol, newTabla);
            if (act instanceof Errores) {
                Variables.addToGlobalLinkedList((Errores) act);
                return act;
            }
        }
        return null;
    }
}

