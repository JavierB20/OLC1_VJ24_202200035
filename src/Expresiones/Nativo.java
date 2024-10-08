
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

    @Override
    public String generarast(Arbol arbol, String anterior) {
        String nodoNativo = "n" + arbol.getContador();//n1
        String nodoValor = "n" + arbol.getContador();//n2

        String resultado = anterior + " -> " + nodoNativo+";\n";

        resultado += nodoNativo + "[label=\"NATIVO\"];\n";
        resultado += nodoValor + "[label=\""
                + this.valor.toString() + "\"];\n";

        resultado += nodoNativo + " -> " + nodoValor+";\n";
        return resultado;
    }
}