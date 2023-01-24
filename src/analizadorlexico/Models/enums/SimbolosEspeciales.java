/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package analizadorlexico.Models.enums;

/**
 *
 * @author Israel Morales
 */
public enum SimbolosEspeciales {
    EPSILON('ε'),
    FIN((char)0);

    private char simbolo;

    public static SimbolosEspeciales getEPSILON() {
        return EPSILON;
    }

    public static SimbolosEspeciales getFIN() {
        return FIN;
    }

    public char getSimbolo() {
        return simbolo;
    }
    
    private SimbolosEspeciales(char simbolo){
        this.simbolo = simbolo;
    }
}
