/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Instrucciones;

import VariablesGlobales.Variables;
import abstracto.Instruccion;
import excepciones.Errores;
import java.util.LinkedList;
import simbolo.Arbol;
import simbolo.tablaSimbolos;

/**
 *
 * @author msWas
 */
public class Match extends Instruccion {
    private Instruccion expresion;
    private LinkedList<Instruccion> casos;
    private Instruccion defualt;


    public Match(Instruccion expresion, LinkedList<Instruccion> casos, Instruccion defualt,int linea, int col) {
        super(null, linea, col);
        this.expresion = expresion;
        this.casos = casos;
        this.defualt = defualt;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Object valorExpresion = this.expresion.interpretar(arbol, tabla);

        // Validamos si la interpretación resultó en un error
        if (valorExpresion instanceof Errores) {
            return valorExpresion;
        }

        if (this.casos != null) {
            for (Instruccion caso : casos) {
                Object valorCaso = ((Caso) caso).getExpresion().interpretar(arbol, tabla);
                if (valorCaso instanceof Errores) {
                    Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Error en Caso", this.linea, this.col));
                    return valorCaso;
                }

                if (valorExpresion.equals(valorCaso)) {
                    return caso.interpretar(arbol, tabla);
                }
            }
        }

        
        if(this.defualt != null){
            Object casoDefault = this.defualt.interpretar(arbol, tabla);
            if(casoDefault instanceof Errores) {
                Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Error en caso Defualt", this.linea, this.col));
                return casoDefault;
            }
            else {
                return casoDefault;
            } 
        }
        return null;

    }
}
