/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Instrucciones;

import Abstracto.Instruccion;
import Simbolos.Arbol;
import Simbolos.Tipo;
import Simbolos.tablaSimbolos;
import Simbolos.tipoDato;

/**
 *
 * @author msWas
 */
public class Return extends Instruccion{
    public Instruccion expresion;
    public Object resultado;

    public Return(Instruccion expresion, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.expresion = expresion;
    }
    
    
    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        if (this.expresion == null) {
            this.resultado = null;
            return this;
        }
        var valor = this.expresion.interpretar(arbol, tabla);
        
        if (valor instanceof Error){
            return valor;
        }
        
        this.tipo.setTipo(this.expresion.tipo.getTipo());
        this.resultado = valor;
        return this;
    }

    @Override
    public String generarast(Arbol arbol, String anterior) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
