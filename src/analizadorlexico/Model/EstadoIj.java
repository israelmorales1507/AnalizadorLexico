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
public class EstadoIj {

    public int idj;
    public ArrayList<Estado> ConjuntoIj;
    public int[] trascionesAFD;
    

    public int[] getTrascionesAFD() {
        return trascionesAFD;
    }

    public void setTrascionesAFD(int[] trascionesAFD) {
        this.trascionesAFD = trascionesAFD;
    }
            
    public int[] gettrascionesAFD() {
        return trascionesAFD;
    }

    public void settrascionesAFD(int[] trascionesAFD) {
        this.trascionesAFD = trascionesAFD;
    }

    public int getIdj() {
        return idj;
    }

    public void setIdj(int idj) {
        this.idj = idj;
    }

    public ArrayList<Estado> getConjuntoIj() {
        return ConjuntoIj;
    }

    public void setConjuntoIj(ArrayList<Estado> conjuntoIj) {
        ConjuntoIj = conjuntoIj;
    }

    public EstadoIj() {
        this.trascionesAFD = new int[CaracteresEspeciales.ARREGLO];
        for (int i = 0; i < CaracteresEspeciales.ARREGLO; i++) {
            this.trascionesAFD[i] = -1;
        }
    }
}