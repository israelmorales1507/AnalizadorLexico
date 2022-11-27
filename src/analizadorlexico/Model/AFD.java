/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Model;

import java.util.ArrayList;

/**
 *
 * @author Israel Morales
 */
public class AFD {

    private int IdAFD;
    private ArrayList<Estado> estadosAceptacion = new ArrayList<Estado>();
    private ArrayList<Character> alfabeto = new ArrayList<Character>();
    private int[][] tabular;

    public int getIdAFD() {
        return IdAFD;
    }

    public void setIdAFD(int IdAFD) {
        this.IdAFD = IdAFD;
    }

    public ArrayList<Estado> getEstadosAceptacion() {
        return estadosAceptacion;
    }

    public void setEstadosAceptacion(ArrayList<Estado> estadosAceptacion) {
        this.estadosAceptacion = estadosAceptacion;
    }

    public ArrayList<Character> getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(ArrayList<Character> alfabeto) {
        this.alfabeto = alfabeto;
    }

    public int[][] getTabular() {
        return tabular;
    }

    public void setTabular(int[][] tabular) {
        this.tabular = tabular;
    }    

    public AFD(int IdAFD, int[][] tabular, ArrayList<Estado> estadosAceptacion, ArrayList<Character> alfabeto) {
        this.IdAFD = IdAFD;
        this.tabular = tabular;
        this.estadosAceptacion = estadosAceptacion;
        this.alfabeto = alfabeto;
    }

    public AFD() {
    }
    
}
