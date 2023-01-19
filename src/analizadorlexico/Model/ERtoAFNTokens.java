/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package analizadorlexico.Model;

import java.util.Arrays;

/**
 *
 * @author Israel Morales
 */
public enum ERtoAFNTokens {
    OR(10,"|","OR"),
    CONCATENAR(20,"&","CONCATENAR"),
    CERRADURA_POSITIVA(30,"+","CERRADURA_POSITIVA"),
    CERRADURA_KLEEN(40,"*","CERRADURA_KLEEN"),
    OPCIONAL(50,"?","OPCIONAL"),
    PARENTESIS_IZQUIERDO(60,"(","PARENTESIS_IZQUIERDO"),
    PARENTESIS_DERECHO(70,")","PARENTESIS_DERECHO"),
    CORCHETE_IZQUIERDO(80,"[","CORCHETE_IZQUIERDO"),
    CORCHETE_DERECHO(90,"]","CORCHETE_DERECHO"),
    GUION(100,"-","GUION"),
    SIMBOLO(110,"[a-z]|[A-Z]|[0-9]|(\\&(||&|+|*|?|(|)|[|]|-))","SIMBOLO"),
    FIN_CADENA(CaracteresEspeciales.FIN,"\0",null),
    NO_EXISTE(CaracteresEspeciales.ERROR,null,null);
    private final int token;
    private final String expresionRegular;
    private final String claseLexica;

    private ERtoAFNTokens(int token, String expresionRegular, String claseLexica) {
        this.token = token;
        this.expresionRegular = expresionRegular;
        this.claseLexica = claseLexica;
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
    
    
    public static ERtoAFNTokens getEnumTokensByToken(int tokenBuscado) {
        return Arrays.stream(values())
        .filter(token -> token.getToken() == tokenBuscado)
        .findFirst().orElse(NO_EXISTE);
    }
}
