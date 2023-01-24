/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Model;

/**
 *
 * @author Israel Morales
 */
public class CeldaTablaLR0 {
    private Integer indiceRegla;
    private String accion;

    public Integer getIndiceRegla() {
        return indiceRegla;
    }

    public void setIndiceRegla(Integer indiceRegla) {
        this.indiceRegla = indiceRegla;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public CeldaTablaLR0(Integer indiceRegla, String accion) {
        this.indiceRegla = indiceRegla;
        this.accion = accion;
    }
    
    
}
