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
public class Llamada extends Instruccion{
    public String id;
    public LinkedList<Instruccion> parametros;

    public Llamada(String id, LinkedList<Instruccion> parametros, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.parametros = parametros;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var busquedaFun = arbol.getFuncion(this.id);
        if(busquedaFun == null){
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Funcion no existente", this.linea, this.col));
            return new Errores("SEMANTICO", "Funcion no existente", this.linea, this.col);
        }
        
        if(busquedaFun instanceof Metodo metodo) { 
            var newTable = new tablaSimbolos(arbol.getTablaGlobal()); //Posible cambio (tabla)
            newTable.setNombre("LLamado Metodo " + this.id);
            
            if(metodo.parametros.size() != this.parametros.size()){
                Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "No se esta recibiendo la misma cantidad de parametros", this.linea, this.col));
                return new Errores("SEMANTICO", "No se esta recibiendo la misma cantidad de parametros", this.linea, this.col);
            }
            
            //Declarando parametros de startWith
            for (int i = 0; i < this.parametros.size(); i++) {
                var identificador = (String) metodo.parametros.get(i).get("id");
                var valor = this.parametros.get(i);
                var tipo2 = (Tipo) metodo.parametros.get(i).get("tipo");
                
                var declaracionParametro = new Declaracion(identificador, valor, "VAR" , tipo2, this.linea ,this.col);
                
                var resultadoDeclaracion = declaracionParametro.interpretar(arbol, newTable);
                
                if (resultadoDeclaracion instanceof Errores) {
                    return resultadoDeclaracion;
                }
                
                var valorInterpretado = valor.interpretar(arbol, tabla);
                
                if(valorInterpretado instanceof Errores) {
                    return valorInterpretado;
                }
                
                var variable = newTable.getVariable(identificador);
                if(variable == null){
                    Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Error en declaracion de parametro", this.linea, this.col));
                    return new Errores("SEMANTICO", "Error en declaracion de parametro", this.linea, this.col);
                }
                
                if(variable.getTipo().getTipo() != valor.tipo.getTipo()){
                    Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Error en tipo de parametro", this.linea, this.col));
                    return new Errores("SEMANTICO", "Error en tipo de parametro", this.linea, this.col);
                }
                
                variable.setValor(valorInterpretado);
            }
            
            var resultadoFuncion = metodo.interpretar(arbol, newTable);
            if(resultadoFuncion instanceof Errores){
                return resultadoFuncion;
            }
            
            if(resultadoFuncion instanceof Return){
                this.tipo.setTipo(metodo.tipo.getTipo());
                return ((Return) resultadoFuncion).resultado;
            }
            
        }
        return null;
    }

    @Override
    public String generarast(Arbol arbol, String anterior) {
return "";    }
    
    
}
