package patroninterprete;


import abstracto.Instruccion;
//import Analizador.parser;
//import Analizador.scanner;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.LinkedList;
import simbolo.Arbol;
import simbolo.TablaSimbolo;

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
        // TODO code application logic here
//        try {
//            String texto = "imprimir(\"Mi cadena\");"
//                    + "imprimir(2); imprimir(3.33);";
//            scanner s = new scanner(new BufferedReader(new StringReader(texto)));
//            parser p = new parser(s);
//            var resultado = p.parse();
//            var ast = new Arbol((LinkedList<Instruccion>) resultado.value);
//            var tabla = new tablaSimbolos();
//            tabla.setNombre("GLOBAL");
//            ast.setConsola("");
//            for (var a : ast.getInstrucciones()) {
//                var res = a.interpretar(ast, tabla);
//            }
//            System.out.println(ast.getConsola());
//        } catch (Exception ex) {
//            System.out.println("Algo salio mal");
//            System.out.println(ex);
//        }
    }

}