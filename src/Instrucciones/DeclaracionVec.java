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
MUTABILIDAD ID : TIPO []   = LSTVALORES -> LSTEXPRESIONES
MUTABILIDAD ID : TIPO [][] = LSTVALORES -> LSTEXPRESIONES
*/
public class DeclaracionVec extends Instruccion {

    public String strMutabilidad;
    public boolean boolMutabilidad;
    public String id;
    public int dimension1;
    public int dimension2;
    public LinkedList<Instruccion> lstValores;
    public LinkedList<LinkedList<Instruccion>> lstValores2;

    public DeclaracionVec(String strMutabilidad, String id, int dimension1, int dimension2, LinkedList<LinkedList<Instruccion>> lstValores2, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.strMutabilidad = strMutabilidad;
        this.id = id;
        this.dimension1 = dimension1;
        this.dimension2 = dimension2;
        this.lstValores2 = lstValores2;
    }

    public DeclaracionVec(String strMutabilidad, String id, int dimension1, LinkedList<Instruccion> lstValores, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.strMutabilidad = strMutabilidad;
        this.id = id;
        this.dimension1 = dimension1;
        this.lstValores = lstValores;
    }

    

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        LinkedList<Object> lstValores = new LinkedList<>(); // Inicializa la lista aquí
        LinkedList<LinkedList<Object>> lstValores2D = new LinkedList<>(); // Inicializa la lista aquí
        LinkedList<Object> lstValoresInterna = new LinkedList<>(); // Inicializa la lista aquí

        //Para vector de dos dimensiones
        if (this.dimension2 == 1) {

            for (var i : this.lstValores2) {
                for (var dato : i ){
                // Interpretar dato
                var valorInterpretado = dato.interpretar(arbol, tabla);

                if (valorInterpretado instanceof Errores) {
                    return valorInterpretado;
                }

                // Validar el tipo con la declaración
                if (dato.tipo.getTipo() != this.tipo.getTipo()) {
                    Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Tipos erroneos en vector", this.linea, this.col));
                    return new Errores("SEMANTICO", "Tipos erroneos en vector", this.linea, this.col);
                }

                // Agregar el valor interpretado a la lista
                lstValoresInterna.add(valorInterpretado);
                }
                lstValores2D.add(lstValoresInterna);
            }
        }


        if (this.dimension1 == 1) {
            for (var dato : this.lstValores) {
                // Interpretar dato
                var valorInterpretado = dato.interpretar(arbol, tabla);

                if (valorInterpretado instanceof Errores) {
                    return valorInterpretado;
                }

                // Validar el tipo con la declaración
                if (dato.tipo.getTipo() != this.tipo.getTipo()) {
                    Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Tipos erroneos en vector", this.linea, this.col));
                    return new Errores("SEMANTICO", "Tipos erroneos en vector", this.linea, this.col);
                }

                // Agregar el valor interpretado a la lista
                lstValores.add(valorInterpretado);
            }
        }


        this.boolMutabilidad = this.strMutabilidad.equalsIgnoreCase("const");
        Simbolo s;

        if(this.dimension2 == 1) {
            s = new Simbolo(this.tipo, this.id, lstValores2D, this.boolMutabilidad);
        }
        else {
            s = new Simbolo(this.tipo, this.id, lstValores, this.boolMutabilidad);
        }

        // Guardar el símbolo en la tabla de símbolos
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
