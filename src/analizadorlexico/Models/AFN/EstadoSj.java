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
public class EstadoSj {
    private int id;
    private HashSet<Estado> estados;

    public EstadoSj() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public HashSet<Estado> getEstados() {
        return estados;
    }

    public void setEstados(HashSet<Estado> estados) {
        this.estados = estados;
    }

    public EstadoSj(int id, HashSet<Estado> estados) {
        this.id = id;
        this.estados = estados;
    }
    

    public EstadoSj(HashSet<Estado> estados){
        this.estados = estados;
    }
}
