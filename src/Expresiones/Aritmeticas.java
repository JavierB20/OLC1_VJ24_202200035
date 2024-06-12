
package expresiones;

import abstracto.Instruccion;
import excepciones.Errores;
import simbolo.*;
import VariablesGlobales.Variables;


public class Aritmeticas extends Instruccion {

    private Instruccion operando1;
    private Instruccion operando2;
    private OperadoresAritmeticos operacion;
    private Instruccion operandoUnico;
    Variables vars = new Variables();

    //negacion 
    public Aritmeticas(Instruccion operandoUnico, OperadoresAritmeticos operacion, int linea, int col) {
        super(new Tipo(tipoDato.ENTERO), linea, col);
        this.operacion = operacion;
        this.operandoUnico = operandoUnico;
    }

    //cualquier operacion menos negacion
    public Aritmeticas(Instruccion operando1, Instruccion operando2, OperadoresAritmeticos operacion, int linea, int col) {
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
            case SUMA ->
                this.suma(opIzq, opDer);
            case RESTA ->
                this.resta(opIzq, opDer);
            case MULTIPLICACION ->
                this.multiplicacion(opIzq, opDer);
            case DIVISION ->
                this.division(opIzq, opDer);
            case POTENCIA ->
                this.potencia(opIzq, opDer);
            case MODULO ->
                this.modulo(opIzq, opDer);
            case NEGACION ->
                this.negacion(Unico);
            default -> {
               vars.listaErrores.add(new Errores("SEMANTICO", "Operador invalido", this.linea, this.col));
               yield null; // O un valor por defecto adecuado, si null no es apropiado
            }
        };
    }

    public Object suma(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();

        switch (tipo1) {
            case tipoDato.ENTERO -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1 + (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int) op1 + (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (int) op1 + (int) op2.toString().charAt(0);
                    }
                    case tipoDato.CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        vars.listaErrores.add(new Errores("SEMANTICO", "Suma erronea de entero, se esperaba que el sumando fuera un entero, decimal, caracter o cadena", this.linea, this.col));
                        return new Errores("SEMANTICO", "Suma erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.DECIMAL -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (double) op1 + (double) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 + (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (double) op1 + (double) op2.toString().charAt(0);
                    }
                    case tipoDato.CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        vars.listaErrores.add(new Errores("SEMANTICO", "Suma erronea de decimal, se esperaba que el sumando fuera un entero, decimal, caracter o cadena", this.linea, this.col));
                        return new Errores("SEMANTICO", "Suma erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.BOOLEANO -> {
                switch (tipo2) {
                    case tipoDato.CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        vars.listaErrores.add(new Errores("SEMANTICO", "Suma erronea de booleano, se esperaba que el sumando fuera una cadena", this.linea, this.col));
                        return new Errores("SEMANTICO", "Suma erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.CARACTER -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1.toString().charAt(0) + (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1.toString().charAt(0) + (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return op1.toString() + op2.toString();
                    }
                    case tipoDato.CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        vars.listaErrores.add(new Errores("SEMANTICO", "Suma erronea de caracter, se esperaba que el sumando fuera un entero, decimal, caracter o cadena", this.linea, this.col));
                        return new Errores("SEMANTICO", "Suma erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.CADENA -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return op1.toString() + op2.toString();
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return op1.toString() + op2.toString();
                    }
                    case tipoDato.BOOLEANO -> {
                        this.tipo.setTipo(tipoDato.BOOLEANO);
                        return op1.toString() + op2.toString();
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return op1.toString() + op2.toString();
                    }
                    case tipoDato.CADENA -> {
                        this.tipo.setTipo(tipoDato.CADENA);
                        return op1.toString() + op2.toString();
                    }
                    default -> {
                        vars.listaErrores.add(new Errores("SEMANTICO", "Suma erronea de caracter, se esperaba que el sumando fuera un entero, decimal, caracter, cadena o booleano", this.linea, this.col));
                        return new Errores("SEMANTICO", "Suma erronea", this.linea, this.col);
                    }
                }
            }
            default -> {
                vars.listaErrores.add(new Errores("SEMANTICO", "Suma erronea, se esperaba que el sumando fuera un entero, decimal, caracter, cadena o booleano", this.linea, this.col));
                return new Errores("SEMANTICO", "Suma erronea", this.linea, this.col);
            }
        }
    }
    
    public Object resta(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            case tipoDato.ENTERO -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1 - (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int) op1 - (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (int) op1 - (int) op2.toString().charAt(0);
                    }
                    default -> {
                        vars.listaErrores.add(new Errores("SEMANTICO", "Resta erronea de entero, se esperaba que el sustrayendo fuera un entero, decimal o caracter", this.linea, this.col));
                        return new Errores("SEMANTICO", "Resta erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.DECIMAL -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (double) op1 - (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 - (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (double) op1 - (double) op2.toString().charAt(0);
                    }
                    default -> {
                        vars.listaErrores.add(new Errores("SEMANTICO", "Resta erronea de decimal, se esperaba que el sustrayendo fuera un entero, decimal o caracter", this.linea, this.col));
                        return new Errores("SEMANTICO", "Resta erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.CARACTER -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1.toString().charAt(0) - (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1.toString().charAt(0) - (double) op2;
                    }
                    default -> {
                        vars.listaErrores.add(new Errores("SEMANTICO", "Resta erronea de caracter, se esperaba que el sustrayendo fuera un entero o decimal", this.linea, this.col));
                        return new Errores("SEMANTICO", "Resta erronea", this.linea, this.col);
                    }
                }
            }
            default -> {
                vars.listaErrores.add(new Errores("SEMANTICO", "Resta erronea, se esperaba que el minuendo fuera un entero, decimal o caracter", this.linea, this.col));
                return new Errores("SEMANTICO", "Resta erronea", this.linea, this.col);
            }
        }
    }
    
    public Object multiplicacion(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            case tipoDato.ENTERO -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1 * (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int) op1 * (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (int) op1 * (int) op2.toString().charAt(0);
                    }
                    default -> {
                        vars.listaErrores.add(new Errores("SEMANTICO", "Multiplicacion erronea de entero, se esperaba que el multiplicador fuera un entero, decimal o caracter", this.linea, this.col));
                        return new Errores("SEMANTICO", "Multiplicacion erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.DECIMAL -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (double) op1 * (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 * (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (double) op1 * (double) op2.toString().charAt(0);
                    }
                    default -> {
                        vars.listaErrores.add(new Errores("SEMANTICO", "Multiplicacion erronea de decimal, se esperaba que el multiplicador fuera un entero, decimal o caracter", this.linea, this.col));
                        return new Errores("SEMANTICO", "Multiplicacion erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.CARACTER -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) op1.toString().charAt(0) * (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1.toString().charAt(0) * (double) op2;
                    }
                    default -> {
                        vars.listaErrores.add(new Errores("SEMANTICO", "Multiplicacion erronea de caracter, se esperaba que el multiplicador fuera un entero o decimal", this.linea, this.col));
                        return new Errores("SEMANTICO", "Multiplicacion erronea", this.linea, this.col);
                    }
                }
            }
            default -> {
                vars.listaErrores.add(new Errores("SEMANTICO", "Multiplicacion erronea, se esperaba que el multiplicando fuera un entero, decimal o caracter", this.linea, this.col));
                return new Errores("SEMANTICO", "Multiplicacion erronea", this.linea, this.col);
            }
        }
    }
    
    public Object division(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        if (op2 instanceof Integer) {
            if((int) op2 == 0) {
                vars.listaErrores.add(new Errores("SEMANTICO", "No es valida la division dentro de 0", this.linea, this.col));
                return new Errores("SEMANTICO", "No es valida la division dentro de 0", this.linea, this.col);
            }
        } else if (op2 instanceof Double) {
            if((double) op2 == 0.0) {
                vars.listaErrores.add(new Errores("SEMANTICO", "No es valida la division dentro de 0", this.linea, this.col));
                return new Errores("SEMANTICO", "No es valida la division dentro de 0", this.linea, this.col);
            }
        } else if (op2 instanceof Character) {
            if((int) op2.toString().charAt(0) == 0) {
                vars.listaErrores.add(new Errores("SEMANTICO", "No es valida la division dentro de 0", this.linea, this.col));
                return new Errores("SEMANTICO", "No es valida la division dentro de 0", this.linea, this.col);
            }
        } 
        
        
        switch (tipo1) {
            case tipoDato.ENTERO -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        int intVal1 = (int) op1;
                        int intVal2 = (int) op2;
                        return (double) intVal1 / (double) intVal2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        int intVal1 = (int) op1;
                        return (double) intVal1 / (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        System.out.println("HOLA");
                        int intVal1 = (int) op1;
                        return (double) intVal1 / (double) op2.toString().charAt(0);
                    }
                    default -> {
                        vars.listaErrores.add(new Errores("SEMANTICO", "Division erronea de caracter, se esperaba que el divisor fuera un entero, decimal o caracter", this.linea, this.col));
                        return new Errores("SEMANTICO", "Division erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.DECIMAL -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (double) op1 / (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 / (double) op2;
                    }
                    case tipoDato.CARACTER -> {
                        this.tipo.setTipo(tipoDato.CARACTER);
                        return (double) op1 / (double) op2.toString().charAt(0);
                    }
                    default -> {
                        vars.listaErrores.add(new Errores("SEMANTICO", "Division erronea de caracter, se esperaba que el divisor fuera un entero, decimal o caracter", this.linea, this.col));
                        return new Errores("SEMANTICO", "Division erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.CARACTER -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (double) op1.toString().charAt(0) / (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1.toString().charAt(0) / (double) op2;
                    }
                    default -> {
                        vars.listaErrores.add(new Errores("SEMANTICO", "Division erronea de caracter, se esperaba que el divisor fuera un entero o decimal", this.linea, this.col));
                        return new Errores("SEMANTICO", "Division erronea", this.linea, this.col);
                    }
                }
            }
            default -> {
                vars.listaErrores.add(new Errores("SEMANTICO", "Division erronea, se esperaba que el dividendo fuera entero, decimal o caracter", this.linea, this.col));
                return new Errores("SEMANTICO", "Division erronea", this.linea, this.col);
            }
        }
    }
    
    public Object potencia(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
              
        switch (tipo1) {
            case tipoDato.ENTERO -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (int) Math.pow((int) op1,(int) op2);
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return Math.pow((int) op1,(double) op2);
                    }
                    default -> {
                        vars.listaErrores.add(new Errores("SEMANTICO", "Potencia erronea de entero, se esperaba que el exponente fuera un entero o decimal", this.linea, this.col));
                        return new Errores("SEMANTICO", "Potencia erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.DECIMAL -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return Math.pow((double) op1,(int) op2);
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return Math.pow((double) op1,(double) op2);
                    }
                    default -> {
                        vars.listaErrores.add(new Errores("SEMANTICO", "Potencia erronea de decimal, se esperaba que el exponente fuera un entero o decimal", this.linea, this.col));
                        return new Errores("SEMANTICO", "Potencia erronea", this.linea, this.col);
                    }
                }
            }
            default -> {
                vars.listaErrores.add(new Errores("SEMANTICO", "Potencia erronea, se esperaba que la base fuera entero o decimal", this.linea, this.col));
                return new Errores("SEMANTICO", "Potencia erronea", this.linea, this.col);
            }
        }
    }

    public Object modulo(Object op1, Object op2) {
        var tipo1 = this.operando1.tipo.getTipo();
        var tipo2 = this.operando2.tipo.getTipo();
        
        switch (tipo1) {
            case tipoDato.ENTERO -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        int intVal1 = (int) op1;
                        int intVal2 = (int) op2;
                        return (double) intVal1 % (double) intVal2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (int) op1 % (double) op2;
                    }
                    default -> {
                        vars.listaErrores.add(new Errores("SEMANTICO", "Modulo erronea de entero, se esperaba que el divisor fuera un entero o decimal", this.linea, this.col));
                        return new Errores("SEMANTICO", "Modulo erronea", this.linea, this.col);
                    }
                }
            }
            case tipoDato.DECIMAL -> {
                switch (tipo2) {
                    case tipoDato.ENTERO -> {
                        this.tipo.setTipo(tipoDato.ENTERO);
                        return (double) op1 % (int) op2;
                    }
                    case tipoDato.DECIMAL -> {
                        this.tipo.setTipo(tipoDato.DECIMAL);
                        return (double) op1 % (double) op2;
                    }
                    default -> {
                        vars.listaErrores.add(new Errores("SEMANTICO", "Modulo erronea de decimal, se esperaba que el divisor fuera un entero o decimal", this.linea, this.col));
                        return new Errores("SEMANTICO", "Modulo erronea", this.linea, this.col);
                    }
                }
            }
            default -> {
                vars.listaErrores.add(new Errores("SEMANTICO", "Modulo erronea, se esperaba que el dividendo fuera un entero o decimal", this.linea, this.col));
                return new Errores("SEMANTICO", "Modulo erronea", this.linea, this.col);
            }
        }
    }
    
    public Object negacion(Object op1) {
        var opU = this.operandoUnico.tipo.getTipo();
        switch (opU) {
            case tipoDato.ENTERO -> {
                this.tipo.setTipo(tipoDato.ENTERO);
                return (int) op1 * -1;
            }
            case tipoDato.DECIMAL -> {
                this.tipo.setTipo(tipoDato.DECIMAL);
                return (double) op1 * -1;
            }
            default -> {
                vars.listaErrores.add(new Errores("SEMANTICO", "Negacion erronea, se esperaba que el dato fuera un entero o decimal", this.linea, this.col));
                return new Errores("SEMANTICO", "Negacion erronea", this.linea, this.col);
            }
        }
    }
}