package Instrucciones;

import VariablesGlobales.Variables;
import Abstracto.Instruccion;
import Excepciones.Errores;
import java.util.LinkedList;
import Simbolos.Arbol;
import Simbolos.Tipo;
import Simbolos.tablaSimbolos;
import Simbolos.tipoDato;

public class DoWhile extends Instruccion {
    private Instruccion condicion;
    private LinkedList<Instruccion> instrucciones;

    public DoWhile(Instruccion condicion, LinkedList<Instruccion> instrucciones, int linea, int col) {
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

        do {
            //nuevo entorno
            var newTabla2 = new tablaSimbolos(newTabla);
            newTabla2.setNombre(tabla.getNombre() + "WHILE-INTERNO");

            //ejecutar instrucciones
            for (var i : this.instrucciones) {
                if (i instanceof Break) {
                    return null;
                }
                
                if (i instanceof Continue) {
                    break;
                }

                var resIns = i.interpretar(arbol, newTabla2);
                if(resIns instanceof Error){
                    Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Error encotrado dentro de sentencia DoWhile", this.linea, this.col));
                    return new Errores("SEMANTICO", "Error encotrado dentro de sentencia DoWhile", this.linea, this.col);
                }
                
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
                Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Error al actualizar variable", this.linea, this.col));
                return act;
            }

        } while ((boolean) this.condicion.interpretar(arbol, newTabla));

        return null;
    }

    @Override
    public String generarast(Arbol arbol, String anterior) {
return "";    }
}
