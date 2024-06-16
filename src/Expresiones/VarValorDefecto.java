/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Expresiones;

import Abstracto.Instruccion;
import Simbolos.Arbol;
import Simbolos.Tipo;
import Simbolos.tablaSimbolos;
import Simbolos.tipoDato;

/**
 *
 * @author msWas
 */
public class VarValorDefecto<T> extends Instruccion {
    private T valor;
    private tipoDato tipo;

    public VarValorDefecto(T valor, tipoDato tipo, int linea, int col) {
        super(new Tipo(tipo), linea, col); // Define el tipo como ENTERO
        this.valor = valor;
        this.tipo = tipo;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        return valor;
    }
    
}
