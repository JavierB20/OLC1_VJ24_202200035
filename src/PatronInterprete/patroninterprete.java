package Patroninterprete;


import VariablesGlobales.Variables;
import Abstracto.Instruccion;
import analisis.parser;
import analisis.scanner;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.LinkedList;
import Simbolos.Arbol;
import Simbolos.tablaSimbolos;
import Excepciones.Errores;


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

    public String Ejecutar(String args) {
        try {
            Variables.clearGlobalLstSimbolo();
            scanner s = new scanner(new BufferedReader(new StringReader(args)));
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
            String errores = "";
            Variables.clearGlobalLinkedList();
            for (var i : lista) {
                errores += ("\t>> Error de Tipo: "+ i.getTipo() + 
                        " Descripcion: " +i.getDesc() +
                        " Linea: " + i.getLinea() + 
                        " Columna: " + i.getColumna() + "\n");
            }
            
            return ast.getConsola() + "\n" + errores;

        } catch (Exception ex) {
            //Temporal
            System.out.println("LLamen a Dios y pregunten porque me abandono");
            System.out.println(ex);
            return "LLamen a Dios y pregunten porque me abandono\n " + ex;
        }
    }
}