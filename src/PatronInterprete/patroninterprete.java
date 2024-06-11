package patroninterprete;


import VariablesGlobales.Variables;
import abstracto.Instruccion;
import analisis.parser;
import analisis.scanner;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.LinkedList;
import simbolo.Arbol;
import simbolo.tablaSimbolos;
import excepciones.Errores;
import java.util.ArrayList;
import java.util.List;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author fabian
 */
public class patroninterprete {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String texto = "println(\"\\tMi terner \\nmienbro\" );"
                    + "//Comentario \n" 
                    + "/*Mi nombre es"
                    + "HOla mundo */"
                    + "println(5+2*(8));"
                    + "println(((int) 'a') + 25.6);";
            //97 a
            scanner s = new scanner(new BufferedReader(new StringReader(texto)));
            parser p = new parser(s);
            var resultado = p.parse();
            var ast = new Arbol((LinkedList<Instruccion>) resultado.value);
            var tabla = new tablaSimbolos();
            tabla.setNombre("GLOBAL");
            ast.setConsola("");
            for (var a : ast.getInstrucciones()) {
                var res = a.interpretar(ast, tabla);
            }
            System.out.println(ast.getConsola());
        } catch (Exception ex) {
            Variables vars = new Variables();
            List<Errores> listaErrores = vars.listaErrores;
            for (var token : listaErrores) {
                System.out.println(token.getTipo() + " : " + token.getDesc() + " en la linea " + token.getLinea() + " y columna " + token.getColumna());
            }
            //Temporal
            System.out.println(ex);
        }
    }
}