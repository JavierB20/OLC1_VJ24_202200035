
package Expresiones;

/**
 *
 * @author msWas
 */
import Abstracto.Instruccion;
import Simbolos.Arbol;
import Simbolos.Tipo;
import Simbolos.tablaSimbolos;


public class Nativo extends Instruccion{
    public Object valor;

    public Nativo(Object valor, Tipo tipo, int linea, int col) {
        super(tipo, linea, col);
        this.valor = valor;
    }

    @Override
    public Object interpretar(Arbol arbol, tablaSimbolos tabla) {
        return this.valor;
    }
    
}