/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Models.AFN;

import java.util.HashSet;

/**
 *
 * @author Israel Morales
 */
public class Estado {
    public static int contadorIdEstado;
    private int id;
    private boolean esAceptacion;
    private int token;
    private HashSet<Transicion> transiciones;

    public static int getContadorIdEstado() {
        return contadorIdEstado;
    }

    public static void setContadorIdEstado(int contadorIdEstado) {
        Estado.contadorIdEstado = contadorIdEstado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEsAceptacion() {
        return esAceptacion;
    }

    public void setEsAceptacion(boolean esAceptacion) {
        this.esAceptacion = esAceptacion;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public HashSet<Transicion> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(HashSet<Transicion> transiciones) {
        this.transiciones = transiciones;
    }

    
    public Estado(){
        this.transiciones = new HashSet<>();
        this.token = -1;
        this.id = contadorIdEstado++;
    }

    public Estado(HashSet<Transicion> transiciones){
        this.transiciones = transiciones;
        this.token = -1;
        this.id = contadorIdEstado++;
    }
}
