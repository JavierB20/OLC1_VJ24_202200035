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
 * @author msWas
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

    @Override
    public String generarast(Arbol arbol, String anterior) {
        
        String nodoPrint = "n" + arbol.getContador();
        String nodoPAR1 = "n" + arbol.getContador();
        String nodoEXP = "n" + arbol.getContador();
        String nodoPAR2 = "n" + arbol.getContador();

        String resultado = anterior + "->" + nodoPrint + ";\n";
        resultado += anterior + "->" + nodoPAR1 + ";\n";
        resultado += anterior + "->" + nodoEXP + ";\n";
        resultado += anterior + "->" + nodoPAR2 + ";\n";
        
        resultado += nodoPrint+"lable=[\"PRINTLN\"];\n";
        resultado += nodoPAR1+"lable=[\"(\"];\n";
        resultado += nodoEXP+"lable=[\"EXP\"];\n";
        resultado += nodoPAR2+"lable=[\")\"];\n";

        resultado += this.expresion.generarast(arbol, nodoEXP);
        
        return resultado;    
    }

}