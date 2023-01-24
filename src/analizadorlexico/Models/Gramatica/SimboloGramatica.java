/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Models.Gramatica;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Israel Morales
 */
@Data
@NoArgsConstructor
public class SimboloGramatica {
    private String simbolo;
    private String expresionRegular;
    private boolean esTerminal;
    private int token;

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getExpresionRegular() {
        return expresionRegular;
    }

    public void setExpresionRegular(String expresionRegular) {
        this.expresionRegular = expresionRegular;
    }

    public boolean isEsTerminal() {
        return esTerminal;
    }

    public void setEsTerminal(boolean esTerminal) {
        this.esTerminal = esTerminal;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    
    public SimboloGramatica(String simbolo, String expresionRegular, boolean esTerminal, int token) {
        this.simbolo = simbolo;
        this.expresionRegular = expresionRegular;
        this.esTerminal = esTerminal;
        this.token = token;
    }

    
    public SimboloGramatica(String simbolo){
        this.simbolo = simbolo;
        token = -1;
    }
}
