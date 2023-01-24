/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Model;

/**
 *
 * @author Israel Morales
 */
public class ItemLR0 {
    private Integer indiceRegla;
    private Integer posicionPunto;

    public Integer getIndiceRegla() {
        return indiceRegla;
    }

    public void setIndiceRegla(Integer indiceRegla) {
        this.indiceRegla = indiceRegla;
    }

    public Integer getPosicionPunto() {
        return posicionPunto;
    }

    public void setPosicionPunto(Integer posicionPunto) {
        this.posicionPunto = posicionPunto;
    }

    public ItemLR0(Integer indiceRegla, Integer posicionPunto) {
        this.indiceRegla = indiceRegla;
        this.posicionPunto = posicionPunto;
    }
    
}
