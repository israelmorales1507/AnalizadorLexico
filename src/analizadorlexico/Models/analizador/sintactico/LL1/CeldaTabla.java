/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Models.analizador.sintactico.LL1;

import analizadorlexico.Models.Gramatica.SimboloGramatica;
import java.util.List;

/**
 *
 * @author Israel Morales
 */
public class CeldaTabla {
    private Integer indiceRegla;
    private List<SimboloGramatica> listaSimbolosGramatica;

    public CeldaTabla() {
    }

    public CeldaTabla(Integer indiceRegla, List<SimboloGramatica> listaSimbolosGramatica) {
        this.indiceRegla = indiceRegla;
        this.listaSimbolosGramatica = listaSimbolosGramatica;
    }

    public Integer getIndiceRegla() {
        return indiceRegla;
    }

    public void setIndiceRegla(Integer indiceRegla) {
        this.indiceRegla = indiceRegla;
    }

    public List<SimboloGramatica> getListaSimbolosGramatica() {
        return listaSimbolosGramatica;
    }

    public void setListaSimbolosGramatica(List<SimboloGramatica> listaSimbolosGramatica) {
        this.listaSimbolosGramatica = listaSimbolosGramatica;
    }
    
    
}
