/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Expresiones;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.*;

/**
 *
 * @author msWas
 */
public class Relacionales extends Instruccion {

    private Instruccion operando1;
    private Instruccion operando2;
    private OperadoresRelacionales operacion;

    public Relacionales(Instruccion operando1, Instruccion operando2, OperadoresRelacionales operacion, int linea, int col) {
        super(new Tipo(tipoDato.ENTERO), linea, col);
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.operacion = operacion;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        Object opIzq = null, opDer = null, Unico = null;
        
        opIzq = this.operando1.interpretar(arbol, tabla);
        if (opIzq instanceof Errores) {
            return opIzq;
        }
        opDer = this.operando2.interpretar(arbol, tabla);
        if (opDer instanceof Errores) {
            return opDer;
        }

        return switch (operacion) {
            case IGUALACION ->
                this.igualacion(opIzq, opDer);
            case DIFERENCIA ->
                this.diferencia(opIzq, opDer);
            case MENORQUE ->
                this.menorQue(opIzq, opDer);
            case MENOROIGUAL ->
                this.menorIgual(opIzq, opDer);
            case MAYORQUE ->
                this.mayorQue(opIzq, opDer);
            case MAYOROIGUAL ->
                this.mayorIgual(opIzq, opDer);
            
            default ->
                new Errores("SEMANTICO", "Operador invalido", this.linea, this.col);
        };
    }

    public Object igualacion(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();

        switch (tipo1) {
            case tipoDato.ENTERO -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1 == (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int) op1 == (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (int) op1 == (int) op2.toString().charAt(0);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Igualacion erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.DECIMAL -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (double) op1 == (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 == (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (double) op1 == (double) op2.toString().charAt(0);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Igualacion erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.BOOLEANO -> {
                switch (tipo2) {
                    case tipoDato.BOOLEANO -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return Boolean.parseBoolean(op1.toString()) == Boolean.parseBoolean(op2.toString());
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Igualacion erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.CARACTER -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1.toString().charAt(0) == (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1.toString().charAt(0) == (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (int) op1.toString().charAt(0) == (int) op2.toString().charAt(0);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Igualacion erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.CADENA -> {
                switch (tipo2) {
                    case tipoDato.CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString().equals(op2.toString());
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Igualacion erronea", this.linea, this.col);
                    }
                }
            }
            default -> {
                return new Errores("SEMANTICO", "Igualacion erronea", this.linea, this.col);
            }
        }
    }
 
    public Object diferencia(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();

        switch (tipo1) {
            case tipoDato.ENTERO -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1 != (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int) op1 != (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (int) op1 != (int) op2.toString().charAt(0);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "diferencia erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.DECIMAL -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (double) op1 != (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 != (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (double) op1 != (double) op2.toString().charAt(0);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "diferencia erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.BOOLEANO -> {
                switch (tipo2) {
                    case tipoDato.BOOLEANO -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return Boolean.parseBoolean(op1.toString()) != Boolean.parseBoolean(op2.toString());
                    }
                    default -> {
                        return new Errores("SEMANTICO", "diferencia erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.CARACTER -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1.toString().charAt(0) != (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1.toString().charAt(0) != (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (int) op1.toString().charAt(0) != (int) op2.toString().charAt(0);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "diferencia erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.CADENA -> {
                switch (tipo2) {
                    case tipoDato.CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString().equals(op2.toString());
                    }
                    default -> {
                        return new Errores("SEMANTICO", "diferencia erronea", this.linea, this.col);
                    }
                }
            }
            default -> {
                return new Errores("SEMANTICO", "diferencia erronea", this.linea, this.col);
            }
        }
    }

    public Object menorQue(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();

        switch (tipo1) {
            case tipoDato.ENTERO -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1 < (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int) op1 < (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (int) op1 < (int) op2.toString().charAt(0);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Menor que  erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.DECIMAL -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (double) op1 < (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 < (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (double) op1 < (double) op2.toString().charAt(0);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Menor que  erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.BOOLEANO -> {
                switch (tipo2) {
                    case tipoDato.BOOLEANO -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        int aux1 = Boolean.parseBoolean(op1.toString()) ? 1 : 0;
                        int aux2 = Boolean.parseBoolean(op1.toString()) ? 1 : 0;
                        return aux1 < aux2;
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Menor que  erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.CARACTER -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1.toString().charAt(0) < (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1.toString().charAt(0) < (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (int) op1.toString().charAt(0) < (int) op2.toString().charAt(0);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Menor que  erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.CADENA -> {
                switch (tipo2) {
                    case tipoDato.CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString().equals(op2.toString());
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Menor que  erronea", this.linea, this.col);
                    }
                }
            }
            default -> {
                return new Errores("SEMANTICO", "Menor que  erronea", this.linea, this.col);
            }
        }
    }
        
    public Object menorIgual(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();

        switch (tipo1) {
            case tipoDato.ENTERO -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1 <= (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int) op1 <= (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (int) op1 <= (int) op2.toString().charAt(0);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Menor igual erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.DECIMAL -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (double) op1 <= (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 <= (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (double) op1 <= (double) op2.toString().charAt(0);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Menor igual erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.BOOLEANO -> {
                switch (tipo2) {
                    case tipoDato.BOOLEANO -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        int aux1 = Boolean.parseBoolean(op1.toString()) ? 1 : 0;
                        int aux2 = Boolean.parseBoolean(op1.toString()) ? 1 : 0;
                        return aux1 <=  aux2;
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Menor igual erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.CARACTER -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1.toString().charAt(0) <= (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1.toString().charAt(0) <= (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (int) op1.toString().charAt(0) <= (int) op2.toString().charAt(0);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Menor igual erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.CADENA -> {
                switch (tipo2) {
                    case tipoDato.CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString().equals(op2.toString());
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Menor igual erronea", this.linea, this.col);
                    }
                }
            }
            default -> {
                return new Errores("SEMANTICO", "Menor igual erronea", this.linea, this.col);
            }
        }
    }

    public Object mayorQue(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();

        switch (tipo1) {
            case tipoDato.ENTERO -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1 > (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int) op1 > (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (int) op1 > (int) op2.toString().charAt(0);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Mayor que erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.DECIMAL -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (double) op1 > (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 > (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (double) op1 > (double) op2.toString().charAt(0);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Mayor que erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.BOOLEANO -> {
                switch (tipo2) {
                    case tipoDato.BOOLEANO -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        int aux1 = Boolean.parseBoolean(op1.toString()) ? 1 : 0;
                        int aux2 = Boolean.parseBoolean(op1.toString()) ? 1 : 0;
                        return aux1 >  aux2;
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Mayor que erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.CARACTER -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1.toString().charAt(0) > (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1.toString().charAt(0) > (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (int) op1.toString().charAt(0) > (int) op2.toString().charAt(0);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Mayor que erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.CADENA -> {
                switch (tipo2) {
                    case tipoDato.CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString().equals(op2.toString());
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Mayor que erronea", this.linea, this.col);
                    }
                }
            }
            default -> {
                return new Errores("SEMANTICO", "Mayor que erronea", this.linea, this.col);
            }
        }
    }
        
    public Object mayorIgual(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();

        switch (tipo1) {
            case tipoDato.ENTERO -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1 >= (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int) op1 >= (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (int) op1 >= (int) op2.toString().charAt(0);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Mayor igual erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.DECIMAL -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (double) op1 >= (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 >= (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (double) op1 >= (double) op2.toString().charAt(0);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Mayor igual erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.BOOLEANO -> {
                switch (tipo2) {
                    case tipoDato.BOOLEANO -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        int aux1 = Boolean.parseBoolean(op1.toString()) ? 1 : 0;
                        int aux2 = Boolean.parseBoolean(op1.toString()) ? 1 : 0;
                        return aux1 >=  aux2;
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Mayor igual erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.CARACTER -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1.toString().charAt(0) >= (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1.toString().charAt(0) >= (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (int) op1.toString().charAt(0) >= (int) op2.toString().charAt(0);
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Mayor igual erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.CADENA -> {
                switch (tipo2) {
                    case tipoDato.CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString().equals(op2.toString());
                    }
                    default -> {
                        return new Errores("SEMANTICO", "Mayor igual erronea", this.linea, this.col);
                    }
                }
            }
            default -> {
                return new Errores("SEMANTICO", "Mayor igual erronea", this.linea, this.col);
            }
        }
    }

}
