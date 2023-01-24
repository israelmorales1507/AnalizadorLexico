/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package analizadorlexico.Models.enums;

import java.util.Arrays;

/**
 *
 * @author Israel Morales
 */
public enum ExpresionesAritmeticasTokens {
    SUMA(10,"+","SUMA"),
    RESTA(20,"-","RESTA"),
    MULTIPLICACION(30,"*","MULTIPLICACION"),
    DIVISION(40,"/","DIVISION"),
    PARENTESIS_IZQUIERDO(50,"(","PARENTESIS_IZQUIERDO"),
    PARENTESIS_DERECHO(60,")","PARENTESIS_DERECHO"),
    NUMERO(70,"[0-9]*&(.[0-9]+)?","NUMERO"),
    
    FIN_CADENA(0,"\0",null),
    NO_EXISTE(-1,null,null);
     

    private final int token;
    private final String expresionRegular;
    private final String claseLexica;

    public static ExpresionesAritmeticasTokens getSUMA() {
        return SUMA;
    }

    public static ExpresionesAritmeticasTokens getRESTA() {
        return RESTA;
    }

    public static ExpresionesAritmeticasTokens getMULTIPLICACION() {
        return MULTIPLICACION;
    }

    public static ExpresionesAritmeticasTokens getDIVISION() {
        return DIVISION;
    }

    public static ExpresionesAritmeticasTokens getPARENTESIS_IZQUIERDO() {
        return PARENTESIS_IZQUIERDO;
    }

    public static ExpresionesAritmeticasTokens getPARENTESIS_DERECHO() {
        return PARENTESIS_DERECHO;
    }

    public static ExpresionesAritmeticasTokens getNUMERO() {
        return NUMERO;
    }

    public static ExpresionesAritmeticasTokens getFIN_CADENA() {
        return FIN_CADENA;
    }

    public static ExpresionesAritmeticasTokens getNO_EXISTE() {
        return NO_EXISTE;
    }

    public int getToken() {
        return token;
    }

    public String getExpresionRegular() {
        return expresionRegular;
    }

    public String getClaseLexica() {
        return claseLexica;
    }

    private ExpresionesAritmeticasTokens(int token, String expresionRegular, String claseLexica) {
        this.token = token;
        this.expresionRegular = expresionRegular;
        this.claseLexica = claseLexica;
    }

    
    public static ExpresionesAritmeticasTokens getEnumTokensByToken(int tokenBuscado) {
        return Arrays.stream(values())
        .filter(token -> token.getToken() == tokenBuscado)
        .findFirst().orElse(NO_EXISTE);
    }
}
