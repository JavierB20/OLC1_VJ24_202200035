
package Abstracto;

import Simbolos.Arbol;
import Simbolos.Tipo;
import Simbolos.tablaSimbolos;

public abstract class Instruccion {

    public Tipo tipo;
    public int linea;
    public int col;

    public Instruccion(Tipo tipo, int linea, int col) {
        this.tipo = tipo;
        this.linea = linea;
        this.col = col;
    }

    public abstract Object interpretar(Arbol arbol, tablaSimbolos tabla);

    public abstract String generarast(Arbol arbol, String anterior);
    
    public void add(Instruccion a) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}