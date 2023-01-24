/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Model;

import java.util.HashSet;

/**
 *
 * @author Israel Morales
 */
public class EstadoLR0 {
    
    private HashSet<ItemLR0> conjuntoItems;

    public HashSet<ItemLR0> getConjuntoItems() {
        return conjuntoItems;
    }

    public void setConjuntoItems(HashSet<ItemLR0> conjuntoItems) {
        this.conjuntoItems = conjuntoItems;
    } 
    
    public EstadoLR0(HashSet<ItemLR0> conjuntoItems){
        this.conjuntoItems = conjuntoItems;
    }
    public EstadoLR0(ItemLR0 itemLR0){
        this.conjuntoItems = new HashSet<>();
        conjuntoItems.add(itemLR0);
    }
}
