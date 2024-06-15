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
                            var num1 : int = (int) 50.58;
                            println(num1 + 20);
                            if(num1 == 51) {
                                println("PENE1");
                                if(!!!false) {
                                    println("PENE2");
                                }
                                println("PENE3");
                            }
                            else if (true) {
                                println("PENEELSEIF1");
                                if(true){
                                    println("PREPUCIO");
                                }
                            }
                           else {
                                println("ELSE");
                           }
                           println("SALIDA");
                           var j : int = 11;
                           match j {
                                   1 => { println("j es 1"); }
                                   2 => { println("j es 2"); }
                                   3 => { println("j es 3"); }
                                   4 => { println("j es 4"); }
                                   5 => { println("j es 5"); }
                                   6 => { println("j es 6"); }
                                   7 => { println("j es 7"); }
                                   8 => { println("j es 8"); }
                                   9 => { println("j es 9"); }
                                   10 => { println("j es 10"); }
                           _ => { println("j es otro valor"); }
                               }
                            println("SALIDA3");
                           
                           """;
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
//            RptErrores rptErrores = new RptErrores();

             for (var i : lista) {
                System.out.println("\t>> Error de Tipo: "+ i.getTipo() + 
                        " Descripcion: " +i.getDesc() +
                        " Linea: " + i.getLinea() + 
                        " Columna: " + i.getColumna());
                vars.listaErrores.add(new Errores(i.getTipo(), i.getDesc(), i.getLinea(), i.getColumna()));
            }
//            rptErrores.generarReporte(vars.listaErrores);

        } catch (Exception ex) {
            //Temporal
            System.out.println("LLamen a Dios y pregunten porque me abandono");
            System.out.println(ex);
        }
    }
}