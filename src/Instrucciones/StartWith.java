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
public class StartWith extends Instruccion{
    public String id;
    public LinkedList<Instruccion> parametros;

    public StartWith(String id, LinkedList<Instruccion> parametros, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.parametros = parametros;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var busquedaFun = arbol.getFuncion(id);
        if(busquedaFun == null){
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Funcion no existente", this.linea, this.col));
            return new Errores("SEMANTICO", "Funcion no existente", this.linea, this.col);
        }
        
        if(busquedaFun instanceof Metodo metodo){
            var newTabla = new tablaSimbolos(arbol.getTablaGlobal());
            newTabla.setNombre("Start-With");
            
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
                
                var resultadoDeclaracion = declaracionParametro.interpretar(arbol, newTabla);
                
                if (resultadoDeclaracion instanceof Errores) {
                    return resultadoDeclaracion;
                }
            }
            
            var resultadoFuncion = metodo.interpretar(arbol, newTabla);
            if(resultadoFuncion instanceof Errores) {
                return resultadoFuncion;
            }
        }
        return null;
    }

    @Override
    public String generarast(Arbol arbol, String anterior) {
return "";    }
    
}
