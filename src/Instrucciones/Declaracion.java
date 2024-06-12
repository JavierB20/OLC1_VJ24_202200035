/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Instrucciones;

import VariablesGlobales.Variables;
import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.Arbol;
import simbolo.Simbolo;
import simbolo.Tipo;
import simbolo.tablaSimbolos;

/**
 *
 * @author msWas
 */
public class Declaracion extends Instruccion {
 
    public String identificador;
    public Instruccion valor;
    Variables vars = new Variables();
    
    public Declaracion(String identificador, Instruccion valor, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.identificador = identificador;
        this.valor = valor;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        // interpretado la expresion
        var valorInterpretado = this.valor.interpretar(arbol, tabla);

        //validamos si es error
        if (valorInterpretado instanceof Errores) {
            return valorInterpretado;
        }

        //validamos los tipo
        if (this.valor.tipo.getTipo() != this.tipo.getTipo()) {
            vars.listaErrores.add(new Errores("SEMANTICO", "Tipos erroneos", this.linea, this.col));
            return new Errores("SEMANTICO", "Tipos erroneos", this.linea, this.col);
        }

        Simbolo s = new Simbolo(this.tipo, this.identificador, valorInterpretado);

        boolean creacion = tabla.setVariable(s);
        if (!creacion) {
            vars.listaErrores.add(new Errores("SEMANTICO", "Variable ya exitente", this.linea, this.col));
            return new Errores("SEMANTICO", "Variable ya existente", this.linea, this.col);
        }

        return null;
    }
}
