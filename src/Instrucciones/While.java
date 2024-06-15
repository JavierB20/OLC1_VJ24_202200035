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
    private Instruccion instruccion;
    private boolean erroresEncontrados;
    private Errores error;
    
    public While(Instruccion condicion, Instruccion instruccion, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.condicion = condicion;
        this.instruccion = instruccion;
    }
    
        @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var cond = this.condicion.interpretar(arbol, tabla);
        if (cond instanceof Errores) {
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

        var newTabla = new tablaSimbolos(tabla);
                
        while((boolean) cond) {
                if(instruccion == null){
                    Variables.addToGlobalLinkedList(new Errores("SINTACTICO", "Error dentro de While no reconocible", this.linea, this.col));
                    return new Errores("SINTACTICO", "Error dentro de While no reconocible (Falta o hay un caracter de mas)", this.linea, this.col);
                }
                
                if (instruccion instanceof Break) {
                    return instruccion;
                }
                
                var resultado = instruccion.interpretar(arbol, newTabla);
                
                if (resultado instanceof Break) {
                    Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Error 'Break' fuera de ciclo", this.linea, this.col));
                    return new Errores("SEMANTICO", "Error 'Break' fuera de ciclo", this.linea, this.col);
                }
                
                //Manejo de errores en While
                if (resultado instanceof Errores){
                    erroresEncontrados = true;
                    error = (Errores) resultado;
                    break;
                }
                
                cond = this.condicion.interpretar(arbol, tabla);
        }
        

        if(erroresEncontrados && error != null){
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Error encotrado dentro de sentencia ELSE", this.linea, this.col));
            return new Errores("SEMANTICO", "Error encotrado dentro de sentencia ELSE", this.linea, this.col);
        }
        
        return null;
    }
    
}
