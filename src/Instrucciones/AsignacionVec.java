/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Instrucciones;

import Abstracto.Instruccion;
import Excepciones.Errores;
import Simbolos.Arbol;
import Simbolos.Tipo;
import Simbolos.tablaSimbolos;
import Simbolos.tipoDato;
import VariablesGlobales.Variables;
import java.util.LinkedList;

/**
 *
 * @author msWas
 */

/*
ID [EXP]      EXP
ID [EXP][EXP] EXP
*/
public class AsignacionVec extends Instruccion{
    private String id;
    private Instruccion Dimension1;
    private Instruccion Dimension2;
    private Instruccion expresion;

    public AsignacionVec(String id, Instruccion Dimension1, Instruccion Dimension2, Instruccion expresion, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.Dimension1 = Dimension1;
        this.Dimension2 = Dimension2;
        this.expresion = expresion;
    }

    public AsignacionVec(String id, Instruccion Dimension1, Instruccion expresion, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.Dimension1 = Dimension1;
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
       
        //Validar mutabilidad
        boolean mutabilidad = (boolean) tabla.getMutabilidad(id);
        if (mutabilidad) {
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "No se puede cambiar el valor de una variable constante", this.linea, this.col));
            return new Errores("SEMANTICO", "No se puede cambiar el valor de una variable constante", this.linea, this.col);
        }
        
        //variable exista 
        var vector = tabla.getVariable(id);
        if (vector == null) {
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Vector no exitente", this.linea, this.col));
            return new Errores("SEMANTICO", "Vector no exitente",this.linea, this.col);
        }
        
        var dime1 = this.Dimension1.interpretar(arbol,tabla);
        if(dime1 instanceof Errores){
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Expresion en dimension erronea", this.linea, this.col));
            return new Errores("SEMANTICO", "Expresion en dimension erronea",this.linea, this.col);
        }
        //Siempre entero - COMPI2
        
        var nuevoValor = this.expresion.interpretar(arbol, tabla);
        if(nuevoValor instanceof Errores){
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Valor a asignado a vector erroneo", this.linea, this.col));
            return new Errores("SEMANTICO", "Valor a asignado a vector erroneo",this.linea, this.col);
        }
        
        var valorVector = (LinkedList) vector.getValor();
        valorVector.set((int) dime1, nuevoValor); //Validar la mutabilidad antes de todo
        
        return null;
    }

    @Override
    public String generarast(Arbol arbol, String anterior) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
