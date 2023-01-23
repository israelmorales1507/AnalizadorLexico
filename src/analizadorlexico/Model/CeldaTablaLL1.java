/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Model;

import java.util.List;

/**
 *
 * @author Israel Morales
 */
public class CeldaTablaLL1 {
    private Integer indiceRegla;
    private List<ClaseNodo> listaSimbolosGramatica;

    public Integer getIndiceRegla() {
        return indiceRegla;
    }

    public void setIndiceRegla(Integer indiceRegla) {
        this.indiceRegla = indiceRegla;
    }

    public List<ClaseNodo> getListaSimbolosGramatica() {
        return listaSimbolosGramatica;
    }

    public void setListaSimbolosGramatica(List<ClaseNodo> listaSimbolosGramatica) {
        this.listaSimbolosGramatica = listaSimbolosGramatica;
    }

    public CeldaTablaLL1(Integer indiceRegla, List<ClaseNodo> listaSimbolosGramatica) {
        this.indiceRegla = indiceRegla;
        this.listaSimbolosGramatica = listaSimbolosGramatica;
    }
    
    
}
