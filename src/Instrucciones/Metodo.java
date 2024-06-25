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
import VariablesGlobales.Variables;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author msWas
 */
public class Metodo extends Instruccion{

    public String id;
    public LinkedList<Instruccion> Instrucciones;
    public LinkedList<HashMap> parametros;

    public Metodo(String id,LinkedList<HashMap> parametros, LinkedList<Instruccion> Instrucciones, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.id = id;
        this.parametros = parametros;
        this.Instrucciones = Instrucciones;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        for (var i:this.Instrucciones){
            var resultado = i.interpretar(arbol, tabla);
            //Validar cuando venga return
            
            if (resultado instanceof Errores){
                Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Error dentro del metodo", this.linea, this.col));
                return new Errores("SEMANTICO", "Error dentro del metodo", this.linea, this.col);
            }
            
            if (resultado instanceof Break || resultado instanceof Continue){
                Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Error expresion de transeferencia invalida (Break/Continue)", this.linea, this.col));
                return new Errores("SEMANTICO", "Error expresion de transeferencia invalida (Break/Continue)", this.linea, this.col);
            }
            
        } 
        return null;
    }
    
    
}
