/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Model;

/**
 *
 * @author Israel Morales
 */
public class ClaseNodo {
    public String simbolo;
    public boolean esTerminal;
    public int token;

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
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
    
    public ClaseNodo(String simbolo) {
        this.simbolo = simbolo;
        token = -1;
    }
}
