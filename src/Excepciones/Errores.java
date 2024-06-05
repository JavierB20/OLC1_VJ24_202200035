/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *
 * @author msWas
 */
public class Errores {
    private String descripcion;
    private String tipo;
    private int linea;
    private int columna;

    public Errores(String descripcion, String tipo, int linea, int columna) {
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.linea = linea;
        this.columna = columna;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public int getLinea() {
        return linea;
    }

    public int getColumna() {
        return columna;
    }
}
