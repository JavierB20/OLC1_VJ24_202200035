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
TIPO ID 
*/
public class DeclaracionListaDinamica extends Instruccion{
    
    public boolean boolMutabilidad;
    private final String strMutabilidad;
    private String id;

    public DeclaracionListaDinamica(String id, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.strMutabilidad = "VAR";
        this.id = id;
    }



    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        LinkedList<Object> lstValores = new LinkedList<>(); // Inicializa la lista aquí
        
        this.boolMutabilidad = this.strMutabilidad.equalsIgnoreCase("const");
        
        Simbolo s = new Simbolo(this.tipo, this.id, lstValores, this.boolMutabilidad);
    
        if (!tabla.setVariable(s)) {
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Variable ya definida", this.linea, this.col));
            return new Errores("SEMANTICO", "Variable ya definida", this.linea, this.col);
        }

        return null;
    }

    @Override
    public String generarast(Arbol arbol, String anterior) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
