/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Instrucciones;

import Simbolos.tablaSimbolos;
import Simbolos.tipoDato;
import Simbolos.Arbol;
import Simbolos.Tipo;
import Abstracto.Instruccion;
import Excepciones.Errores;

/**
 *
 * @author fabian
 */
public class Print extends Instruccion {

    private Instruccion expresion;

    public Print(Instruccion expresion, int linea, int col) {
        super(new Tipo(tipoDato.VOID), linea, col);
        this.expresion = expresion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        var resultado = this.expresion.interpretar(arbol, tabla);
        if (resultado instanceof Errores) {
            return resultado;
        }
        arbol.Print(resultado.toString());
        return null;
    }

}