/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Expresiones;

import VariablesGlobales.Variables;
import Abstracto.Instruccion;
import Excepciones.Errores;
import Simbolos.Arbol;
import Simbolos.Tipo;
import Simbolos.tablaSimbolos;
import Simbolos.tipoDato;

/**
 *
 * @author msWas
 */
public class Casteos extends Instruccion {
    
    private Instruccion operando1;
    Variables vars = new Variables();
    
    public Casteos(Tipo tipoCasteo, Instruccion operando1, int linea, int col) {
        super(tipoCasteo, linea, col);
        this.operando1 = operando1;
    }
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Object operador = null;

        operador = this.operando1.interpretar(arbol, tabla);
        if (operador instanceof Errores) {
            return operador;
        }

        return switch (this.tipo.getTipo()) {
            case ENTERO ->
                this.castInt(operador);
            case DECIMAL ->
                this.castDouble(operador);
            case CARACTER ->
                this.castChar(operador);
            default -> {
                Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Casteo incorrecto", this.linea, this.col));
                yield null;
            }
        };
    }
    
    public Object castInt(Object op1) {
        var tipo1 = this.operando1.tipo.getTipo();

        switch (tipo1) {
            case tipoDato.DECIMAL -> {
                this.tipo.setTipo(tipoDato.ENTERO);
                Double doubleVar = Math.floor((Double) op1);
                Integer integerValue = doubleVar.intValue(); 
                return integerValue;
            }
            case tipoDato.CARACTER -> {
                this.tipo.setTipo(tipoDato.ENTERO);
                return (int) op1.toString().charAt(0);
            }
            default -> {
                Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "El casteo a entero solo puedo ser de decimal o caracter", this.linea, this.col));
                return new Errores("SEMANTICO", "El casteo a entero solo puedo ser de decimal o caracter", this.linea, this.col);
            }
        }
    }

    public Object castDouble(Object op1) {
        var tipo1 = this.operando1.tipo.getTipo();

        switch (tipo1) {
            case tipoDato.ENTERO -> {
                this.tipo.setTipo(tipoDato.DECIMAL);
                Double valorDouble = ((Integer) op1).doubleValue(); // Conversión explícita
                return valorDouble;
            }
            case tipoDato.CARACTER -> {
                this.tipo.setTipo(tipoDato.DECIMAL);
                return (double) op1.toString().charAt(0);
            }
            default -> {
                Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "El casteo a decimal solo puedo ser de entero o caracter", this.linea, this.col));
                return new Errores("SEMANTICO", "Casteo a double erroneo", this.linea, this.col);
            }
        }
    }

    public Object castChar(Object op1) {
        var tipo1 = this.operando1.tipo.getTipo();

        if (op1 instanceof Integer) {
            if((int) op1 < 0 && (int) op1 > 255) {
                Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Esta fuera del rango del codigo ASCII", this.linea, this.col));
                return new Errores("SEMANTICO", "Esta fuera del rango del codigo ASCII", this.linea, this.col);
            }
        }
        
        switch (tipo1) {
            case tipoDato.ENTERO -> {
                this.tipo.setTipo(tipoDato.CARACTER);
                return (char) ((Integer) op1).intValue();
            }
            default -> {
                Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "El casteo a char solo puedo ser de entero", this.linea, this.col));
                return new Errores("SEMANTICO", "Casteo a double erroneo", this.linea, this.col);
            }
        }
    }
    
}
