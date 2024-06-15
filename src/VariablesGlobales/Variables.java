package VariablesGlobales;


import excepciones.Errores;
import java.util.LinkedList;

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
}
