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