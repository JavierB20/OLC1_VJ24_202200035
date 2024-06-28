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
import Simbolos.tipoDato;
import VariablesGlobales.Variables;
import java.util.LinkedList;

/**
 *
 * @author msWas
 */
/*
ID EXP -> Valor a ingresar
*/
public class Append extends Instruccion{
    private String id;
    private Instruccion expresion;

    public Append(String id, Instruccion expresion, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var lstDinamica = tabla.getVariable(id);
        
        var valorInterpretado = this.expresion.interpretar(arbol,tabla);
        if(valorInterpretado instanceof Errores){
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Error al agregar valor", this.linea, this.col));
            return new Errores("SEMANTICO", "Error al agregar valor",this.linea, this.col);
        }
        
        if(this.expresion.tipo.getTipo() != lstDinamica.getTipo().getTipo()){
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Valor a asignado a lstDinamica no coincide con el tipo", this.linea, this.col));
            return new Errores("SEMANTICO", "Valor a asignado a lstDinamica no coincide con el tipo",this.linea, this.col);
        }
        
        var valorLstDinamica = (LinkedList) lstDinamica.getValor();
        valorLstDinamica.add(valorInterpretado);
        
        return null;
    }

    @Override
    public String generarast(Arbol arbol, String anterior) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
