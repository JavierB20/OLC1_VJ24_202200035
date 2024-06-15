/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simbolo;


public class Simbolo {
    private Tipo tipo;
    private String id;
    private Object valor;
    private boolean mutabilidad;
    
    private String tipoDato;
    private String entorno;
    private int linea;
    private int columna;
    private String tipos;

    public Simbolo(Tipo tipo, String id) {
        this.tipo = tipo;
        this.id = id;
    }

    public Simbolo(Tipo tipo, String id, Object valor) {
        this.tipo = tipo;
        this.id = id;
        this.valor = valor;
    }

    public Simbolo(Tipo tipo, String id, Object valor, boolean mutabilidad) {
        this.tipo = tipo;
        this.id = id;
        this.valor = valor;
        this.mutabilidad = mutabilidad;
    }
    
    public Simbolo(String id, String tipos, String tipoDato, String entorno, Object valor, int linea, int columna) {
        this.id = id;
        this.tipos = tipos;
        this.tipoDato = tipoDato;
        this.entorno = entorno;
        this.valor = valor;
        this.linea = linea;
        this.columna = columna;
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

    public String getTipos() {
        return tipos;
    }

    public void setTipos(String tipos) {
        this.tipos = tipos;
    }
    
    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getValor() {
        return valor;
    }

    public boolean setValor(Object valor) {
        if (!mutabilidad) {
            this.valor = valor;
            return true;
        } else {
            return false;
        }
    }
    
    public boolean esConstante() {
        return mutabilidad;
    }
    
    
}