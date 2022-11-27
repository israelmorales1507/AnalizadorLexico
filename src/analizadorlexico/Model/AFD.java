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

    public static ArrayList<AFD> conjutnoAFD = new ArrayList<AFD>();
    
    private int IdAFD;
    private ArrayList<Character> alfabeto = new ArrayList<Character>();
    private ArrayList<EstadoIj> EstadosAFD = new ArrayList<>();
    public int[][] tabular;
    

    public int getIdAFD() {
        return IdAFD;
    }

    public void setIdAFD(int IdAFD) {
        this.IdAFD = IdAFD;
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

    public AFD(int IdAFD, int[][] tabular, ArrayList<EstadoIj> EstadosAFD, ArrayList<Character> alfabeto) {
        this.IdAFD = IdAFD;
        this.tabular = tabular;
        this.EstadosAFD = EstadosAFD;
        this.alfabeto = alfabeto;
    }
    
    public AFD(int IdAFD, ArrayList<EstadoIj> EstadosAFD, ArrayList<Character> alfabeto) {
        this.IdAFD = IdAFD;
        this.alfabeto = alfabeto;
        this.EstadosAFD = EstadosAFD;
        this.tabular = new int[EstadosAFD.size()][CaracteresEspeciales.ARREGLO];
        construirTable();
    }

    public AFD() {
    }
    
    private void construirTable(){
        int l = EstadosAFD.size();
        for (int i = 0; i < l; i++) {
            tabular[i] = EstadosAFD.get(i).getTrascionesAFD();
        }
    }
}
