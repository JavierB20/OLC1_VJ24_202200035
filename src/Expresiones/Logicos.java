/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Expresiones;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.Arbol;
import simbolo.Tipo;
import simbolo.tablaSimbolos;
import simbolo.tipoDato;

/**
 *
 * @author msWas
 */
public class Logicos extends Instruccion{

    private Instruccion operando1;
    private Instruccion operando2;
    private OperadoresLogicos operacion;
    private Instruccion operandoUnico;

        //negacion 
    public Logicos(Instruccion operandoUnico, OperadoresLogicos operacion, int linea, int col) {
        super(new Tipo(tipoDato.ENTERO), linea, col);
        this.operacion = operacion;
        this.operandoUnico = operandoUnico;
    }
    
    public Logicos(Instruccion operando1, Instruccion operando2, OperadoresLogicos operacion, int linea, int col) {
        super(new Tipo(tipoDato.ENTERO), linea, col);
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.operacion = operacion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Object opIzq = null, opDer = null, Unico = null;
        if (this.operandoUnico != null) {
            Unico = this.operandoUnico.interpretar(arbol, tabla);
            if (Unico instanceof Errores) {
                return Unico;
            }
        } else {
            opIzq = this.operando1.interpretar(arbol, tabla);
            if (opIzq instanceof Errores) {
                return opIzq;
            }
            opDer = this.operando2.interpretar(arbol, tabla);
            if (opDer instanceof Errores) {
                return opDer;
            }
        }

        return switch (operacion) {
            case OPOR ->
                this.opOr(opIzq, opDer);
            case OPAND ->
                this.opAnd(opIzq, opDer);
            case OPXOR ->
                this.opXor(opIzq, opDer);
            case OPNOT ->
                this.opNot(Unico);
            default ->
                new Errores("SEMANTICO", "Operador invalido", this.linea, this.col);
        };
    }

    public Object opOr(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();

        switch (tipo1) {
            case tipoDato.BOOLEANO -> {
                switch (tipo2) {
                    case tipoDato.BOOLEANO -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return Boolean.parseBoolean(op1.toString()) || Boolean.parseBoolean(op2.toString());
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Or erronea", this.linea, this.col);
                    }
                }
            }
            default -> {
                return new Errores("SEMANTICO", "Or erronea", this.linea, this.col);
            }
        }
    }
 
    public Object opAnd(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();

        switch (tipo1) {
            case tipoDato.BOOLEANO -> {
                switch (tipo2) {
                    case tipoDato.BOOLEANO -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return Boolean.parseBoolean(op1.toString()) && Boolean.parseBoolean(op2.toString());
                    }
                    default -> {
                        return new Errores("SEMANTICO", "And erronea", this.linea, this.col);
                    }
                }
            }
            default -> {
                return new Errores("SEMANTICO", "And erronea", this.linea, this.col);
            }
        }
    }

    public Object opXor(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();

        switch (tipo1) {
            case tipoDato.BOOLEANO -> {
                switch (tipo2) {
                    case tipoDato.BOOLEANO -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return Boolean.parseBoolean(op1.toString()) ^ Boolean.parseBoolean(op2.toString());
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Xor erronea", this.linea, this.col);
                    }
                }
            }
           default -> {
                return new Errores("SEMANTICO", "Xor erronea", this.linea, this.col);
            }
        }
    }
        
    public Object opNot(Object op1) {
        var opU = this.operandoUnico.tipo.getTipo();
        switch (opU) {
            case tipoDato.BOOLEANO -> {
                this.tipo.setTipo(tipoDato.BOOLEANO);
                return !Boolean.parseBoolean(op1.toString());
            }
            default -> {
                return new Errores("SEMANTICO", "Negacion erronea", this.linea, this.col);
            }
        }
    }

}
