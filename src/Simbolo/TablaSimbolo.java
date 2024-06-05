/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Simbolo;

/**
 *
 * @author msWas
 */
public class TablaSimbolo {
    
    private String id;
    private String tipo;
    private String tipoDato;
    private String entorno;
    private String valor;
    private int linea;
    private int columna;

    public TablaSimbolo(String id, String tipo, String tipoDato, String entorno, String valor, int linea, int columna) {
        this.id = id;
        this.tipo = tipo;
        this.tipoDato = tipoDato;
        this.entorno = entorno;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public String getEntorno() {
        return entorno;
    }

    public void setEntorno(String entorno) {
        this.entorno = entorno;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
    
}
