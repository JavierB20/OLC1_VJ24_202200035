package VariablesGlobales;


import Excepciones.Errores;
import java.util.LinkedList;
import Simbolos.Simbolo;
import Simbolos.tablaSimbolos;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author msWas
 */
public class Variables {
    private static LinkedList<Errores> listaErrores = new LinkedList<>();
    private static LinkedList<Simbolo> listaSimbolo = new LinkedList<>();
    private static String strAst;

    public static String getStrAst() {
        return strAst;
    }

    public static void setStrAst(String strAst) {
        Variables.strAst = strAst;
    }

    
    
    // Método público para obtener la LinkedList
    public static LinkedList<Errores> getGlobalLinkedList() {
        return listaErrores;
    }

    // Método público para agregar un elemento a la LinkedList
    public static void addToGlobalLinkedList(Errores element) {
        listaErrores.add(element);
    }
    
    // Método público para limpiar la lista
    public static void clearGlobalLinkedList() {
        listaErrores.clear();
        return;
    }
    
    // Método público para obtener la LinkedList
    public static LinkedList<Simbolo> getGlobalLstSimbolo() {
        return listaSimbolo;
    }

    // Método público para agregar un elemento a la LinkedList
    public static void addGlobalLstSimbolo(Simbolo element) {
        listaSimbolo.add(element);
    }
    
    // Método público para limpiar la lista
    public static void clearGlobalLstSimbolo() {
        listaSimbolo.clear();
        return;
    }
}
