/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Expresiones;

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
ID EXP
ID EXP EXP
*/
public class AccesoVec extends Instruccion{
    private String id;
    private Instruccion dimension1;
    private Instruccion dimension2;

    public AccesoVec(String id, Instruccion dimension1, Instruccion dimension2, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.dimension1 = dimension1;
        this.dimension2 = dimension2;
    }

    public AccesoVec(String id, Instruccion dimension1, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.id = id;
        this.dimension1 = dimension1;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var vector = tabla.getVariable(id);
        if (vector == null) {
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Vector no exitente", this.linea, this.col));
            return new Errores("SEMANTICO", "Vector no exitente",this.linea, this.col);
        }
        
        var intDimension1 = this.dimension1.interpretar(arbol, tabla);
        if (intDimension1 instanceof Error) {
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Dimension ingresada no valida", this.linea, this.col));
            return new Errores("SEMANTICA", "Dimension ingresada no valida",this.linea, this.col);
        }
        
        var valorVector = (LinkedList) vector.getValor();
        this.tipo.setTipo(vector.getTipo().getTipo());
        
        int indiceAcceder1 = (int) intDimension1;
        
        if(!(indiceAcceder1 >= 0 && indiceAcceder1 < valorVector.size())) {
                        Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Fuera de rango en el vector", this.linea, this.col));
            return new Errores("SEMANTICA", "Fuera de rango en el vector",this.linea, this.col);
        }
        
        return valorVector.get(indiceAcceder1);

    }

    @Override
    public String generarast(Arbol arbol, String anterior) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
