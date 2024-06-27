/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Instrucciones;

import Expresiones.VarValorDefecto;
import VariablesGlobales.Variables;
import Abstracto.Instruccion;
import Excepciones.Errores;
import Simbolos.Arbol;
import Simbolos.Simbolo;
import Simbolos.Tipo;
import Simbolos.tablaSimbolos;
import Simbolos.tipoDato;

/**
 *
 * @author msWas
 */
public class Declaracion extends Instruccion {
 
    public String identificador;
    public Instruccion valor;
    public String strMutabilidad;
    public boolean boolMutabilidad;
    
    public Declaracion(String identificador, String strMutabilidad, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.identificador = identificador;
        this.strMutabilidad = strMutabilidad;
        this.valor = null;
    }
    
    public Declaracion(String identificador, Instruccion valor, String strMutabilidad, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.identificador = identificador;
        this.valor = valor;
        this.strMutabilidad = strMutabilidad;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        //Validamos si es una declaracion sin datos
        if(this.valor == null) {
            switch (this.tipo.getTipo()) {
                case tipoDato.ENTERO ->
                    this.valor =  new VarValorDefecto(0, tipoDato.ENTERO,this.linea, this.col);
                case tipoDato.DECIMAL ->
                    this.valor =  new VarValorDefecto(0.0, tipoDato.DECIMAL,this.linea, this.col);
                case tipoDato.CARACTER ->
                    this.valor =  new VarValorDefecto(' ', tipoDato.CARACTER,this.linea, this.col);
                case tipoDato.CADENA ->
                    this.valor =  new VarValorDefecto(" ", tipoDato.CADENA,this.linea, this.col);
                case tipoDato.BOOLEANO ->
                    this.valor =  new VarValorDefecto(true, tipoDato.BOOLEANO,this.linea, this.col);
                default -> {
                    Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Error al crear variable", this.linea, this.col));
                }
            }
        }

        // interpretado la expresion
        var valorInterpretado = this.valor.interpretar(arbol, tabla);

        //validamos si es error
        if (valorInterpretado instanceof Errores) {
            return valorInterpretado;
        }
        
        //validamos los tipo
        if (this.valor.tipo.getTipo() != this.tipo.getTipo()) {
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Tipos erroneos", this.linea, this.col));
            return new Errores("SEMANTICO", "Tipos erroneos", this.linea, this.col);
        }

        this.boolMutabilidad = this.strMutabilidad.equalsIgnoreCase("const");
        Simbolo s = new Simbolo(this.tipo, this.identificador, valorInterpretado, this.boolMutabilidad);

        Simbolo simbolo = new Simbolo(
            identificador, 
            "Variable", 
            tipo.getTipo().toString(), 
            tabla.getNombre(), 
            valorInterpretado, 
            this.linea, 
            this.col
        );
        
        boolean creacion = tabla.setVariable(s);
        if (!creacion) {
            Variables.addToGlobalLinkedList(new Errores("SEMANTICO", "Variable ya exitente", this.linea, this.col));
            return new Errores("SEMANTICO", "Variable ya existente", this.linea, this.col);
        }
        Variables.addGlobalLstSimbolo(simbolo);
        return null;
    }

    @Override
    public String generarast(Arbol arbol, String anterior) {
return "";    }
}
