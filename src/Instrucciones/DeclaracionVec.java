/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Instrucciones;

import Abstracto.Instruccion;
import Excepciones.Errores;
import Simbolos.Arbol;
import Simbolos.Simbolo;
import Simbolos.Tipo;
import Simbolos.tablaSimbolos;
import VariablesGlobales.Variables;
import java.util.LinkedList;

/**
 *
 * @author msWas
 */

/*
MUTABILIDAD ID : TIPO []   = LSTVALORES -> LSTEXPRESIONES
MUTABILIDAD ID : TIPO [][] = LSTVALORES -> LSTEXPRESIONES
*/
public class DeclaracionVec extends Instruccion {

    public String mutabilidad;
    public boolean boolMutabilidad;
    public String id;
    public int dimension1;
    public int dimension2;
    public LinkedList<Instruccion> lstValoes;

    public DeclaracionVec(String mutabilidad, String id, int dimension1, int dimension2, LinkedList<Instruccion> lstValoes, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.mutabilidad = mutabilidad;
        this.id = id;
        this.dimension1 = dimension1;
        this.dimension2 = dimension2;
        this.lstValoes = lstValoes;
    }

    public DeclaracionVec(String mutabilidad, String id, int dimension1, LinkedList<Instruccion> lstValoes, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.mutabilidad = mutabilidad;
        this.id = id;
        this.dimension1 = dimension1;
        this.lstValoes = lstValoes;
    }

    

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        for (var i : this.lstValoes){
            var resIns = i.interpretar(arbol, tabla);
            
            if (resIns instanceof Errores) {
                    Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Error dentro de la sentencia For", this.linea, this.col));
                    return resIns;
            }
            
            //validamos los tipo
            if (i.tipo.getTipo() != this.tipo.getTipo()) {
                Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Tipos erroneos", this.linea, this.col));
                return new Errores("SEMANTICO", "Tipos erroneos", this.linea, this.col);
            }
            
            this.boolMutabilidad = this.mutabilidad.equalsIgnoreCase("const");
            Simbolo s = new Simbolo(this.tipo, this.id, resIns, this.boolMutabilidad);

            Simbolo simbolo = new Simbolo(
                id, 
                "Variable", 
                tipo.getTipo().toString(), 
                tabla.getNombre(), 
                resIns, 
                this.linea, 
                this.col
            );

            boolean creacion = tabla.setVariable(s);
            if (!creacion) {
                Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Variable ya exitente", this.linea, this.col));
                return new Errores("SEMANTICO", "Variable ya existente", this.linea, this.col);
            }
            Variables.addGlobalLstSimbolo(simbolo);
        }
        return null;
    }

    @Override
    public String generarast(Arbol arbol, String anterior) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
