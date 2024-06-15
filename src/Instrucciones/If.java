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
public class If extends Instruccion{
    private Instruccion condicion;
    private LinkedList<Instruccion> instrucciones;
    private LinkedList<Instruccion> instruccionesELSE;
    private Instruccion instruccionesELSEIF;
    Variables vars = new Variables();
    private boolean erroresEncontrados;
    private Errores error;


    //Para IF normal
    public If(Instruccion condicion, LinkedList<Instruccion> instrucciones, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.condicion = condicion;
        this.instrucciones = instrucciones;
    }
    
    //Para ELSE IF
    public If(Instruccion condicion, LinkedList<Instruccion> instrucciones, Instruccion instruccionesELSEIF, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.condicion = condicion;
        this.instrucciones = instrucciones;
        this.instruccionesELSEIF = instruccionesELSEIF;
    }
    
    //Para ELSE
    public If(Instruccion condicion, LinkedList<Instruccion> instrucciones, LinkedList<Instruccion> instruccionesELSE, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.condicion = condicion;
        this.instrucciones = instrucciones;
        this.instruccionesELSE = instruccionesELSE;
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
        newTabla.setNombre(tabla.getNombre() + "IF");

                
        if ((boolean) cond) {
            for (var i : this.instrucciones) {
                
                if(i == null){
                    Variables.addToGlobalLinkedList(new Errores("SINTACTICO", "Error dentro de if no reconocible", this.linea, this.col));
                    return new Errores("SINTACTICO", "Error dentro de if no reconocible (Falta o hay un caracter de mas)", this.linea, this.col);
                }
                
                if (i instanceof Break) {
                    return i;
                }
                
                if (i instanceof Continue) {
                    return i;
                }
                
                var resultado = i.interpretar(arbol, newTabla);
                
                if (resultado instanceof Break) {
                    return resultado;
                }

                if (resultado instanceof Continue) {
                    return resultado;
                }
                //Manejo de errores en if
                
                if (resultado instanceof Errores){
                    erroresEncontrados = true;
                    error = (Errores) resultado;
                    break;
                }
            }
        }
        else {
            if(this.instruccionesELSE != null) {
                if(!this.instruccionesELSE.isEmpty()){
                    for (var i : this.instruccionesELSE) {

                        if(i == null){
                            Variables.addToGlobalLinkedList(new Errores("SINTACTICO", "Error dentro de else no reconocible (Falta o hay un caracter de mas)", this.linea, this.col));
                            return new Errores("SINTACTICO", "Error dentro de else no reconocible (Falta o hay un caracter de mas)", this.linea, this.col);
                        }

                        if (i instanceof Break) {
                            return i;
                        }
                        
                        if (i instanceof Continue) {
                            return i;
                        }

                        var resultado = i.interpretar(arbol, newTabla);

                        if (resultado instanceof Break) {
                            return resultado;
                        }
                        
                        if (resultado instanceof Continue) {
                            return resultado;
                        }
                        //Manejo de errores en else

                        if (resultado instanceof Errores){
                            erroresEncontrados = true;
                            error = (Errores) resultado;
                            break;
                        }
                    }
                }
            }
            if(this.instruccionesELSEIF != null){
                var resultado = this.instruccionesELSEIF.interpretar(arbol, tabla);
                if(resultado instanceof Errores){
                    Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Error dentro de if no reconocible", this.linea, this.col));
                    return new Errores("SEMANTICO", "Error dentro de if no reconocible", this.linea, this.col);
                }
            }
        }
       
        if(erroresEncontrados && error != null){
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Error encotrado dentro de sentencia ELSE", this.linea, this.col));
            return new Errores("SEMANTICO", "Error encotrado dentro de sentencia ELSE", this.linea, this.col);
        }
        
        return null;
    }
    
}
