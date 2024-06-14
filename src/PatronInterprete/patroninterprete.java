package patroninterprete;


import RptHTML.RptErrores;
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
    static Variables vars = new Variables();

    public static void main(String[] args) {
        try {
            String texto = """
                           println("\tPrueba de secuencias de escape" );
                           println(5+2*(8));
                           println(((int) 18.6) == 18);
                           println(((int) 18.6) + 18);
                           println("------");
                           var numero : bool;
                           println(numero);
                           """;
//                           numero = 10;
//                           println(numero);
//                           println("------");
//                           double numDoble = ((double) numero) + 22.56;
//                           println(numDoble);
            //97 a
            scanner s = new scanner(new BufferedReader(new StringReader(texto)));
            parser p = new parser(s);
            var resultado = p.parse();
            var ast = new Arbol((LinkedList<Instruccion>) resultado.value);
            var tabla = new tablaSimbolos();
            tabla.setNombre("GLOBAL");
            ast.setConsola("");
            LinkedList<Errores> lista = new LinkedList<>();
            lista.addAll(s.listaErrores);
            lista.addAll(p.listaErrores);
            for (var a : ast.getInstrucciones()) {
                if (a == null) {
                    continue;
                }

                var res = a.interpretar(ast, tabla);
                if (res instanceof Errores) {
                    lista.add((Errores) res);
                    
                }
            }
            
            System.out.println(ast.getConsola());
            RptErrores rptErrores = new RptErrores();

             for (var i : lista) {
                System.out.println("\t>> Error de Tipo: "+ i.getTipo() + 
                        " Descripcion: " +i.getDesc() +
                        " Linea: " + i.getLinea() + 
                        " Columna: " + i.getColumna());
                vars.listaErrores.add(new Errores(i.getTipo(), i.getDesc(), i.getLinea(), i.getColumna()));
            }
            //rptErrores.generarReporte(vars.listaErrores);

        } catch (Exception ex) {
            //Temporal
            System.out.println("LLamen a Dios y pregunten porque me abandono");
            System.out.println(ex);
        }
    }
}