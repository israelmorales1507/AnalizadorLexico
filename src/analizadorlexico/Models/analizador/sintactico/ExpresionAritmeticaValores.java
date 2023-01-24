/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Models.analizador.sintactico;

/**
 *
 * @author Israel Morales
 */
public class ExpresionAritmeticaValores {

    public String expresionPostFija;
    public float valorNumerico;

    public ExpresionAritmeticaValores() {
    }
    
    public ExpresionAritmeticaValores(String expresionPostFija, float valorNumerico) {
        this.expresionPostFija = expresionPostFija;
        this.valorNumerico = valorNumerico;
    }

    public String getExpresionPostFija() {
        return expresionPostFija;
    }

    public void setExpresionPostFija(String expresionPostFija) {
        this.expresionPostFija = expresionPostFija;
    }

    public float getValorNumerico() {
        return valorNumerico;
    }

    public void setValorNumerico(float valorNumerico) {
        this.valorNumerico = valorNumerico;
    }
    
    
}
