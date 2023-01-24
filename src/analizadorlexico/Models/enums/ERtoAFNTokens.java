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
    FIN_CADENA(0,"\0",null),
    NO_EXISTE(-1,null,null);
     

    private final int token;
    private final String expresionRegular;
    private final String claseLexica;

    public static ERtoAFNTokens getOR() {
        return OR;
    }

    public static ERtoAFNTokens getCONCATENAR() {
        return CONCATENAR;
    }

    public static ERtoAFNTokens getCERRADURA_POSITIVA() {
        return CERRADURA_POSITIVA;
    }

    public static ERtoAFNTokens getCERRADURA_KLEEN() {
        return CERRADURA_KLEEN;
    }

    public static ERtoAFNTokens getOPCIONAL() {
        return OPCIONAL;
    }

    public static ERtoAFNTokens getPARENTESIS_IZQUIERDO() {
        return PARENTESIS_IZQUIERDO;
    }

    public static ERtoAFNTokens getPARENTESIS_DERECHO() {
        return PARENTESIS_DERECHO;
    }

    public static ERtoAFNTokens getCORCHETE_IZQUIERDO() {
        return CORCHETE_IZQUIERDO;
    }

    public static ERtoAFNTokens getCORCHETE_DERECHO() {
        return CORCHETE_DERECHO;
    }

    public static ERtoAFNTokens getGUION() {
        return GUION;
    }

    public static ERtoAFNTokens getSIMBOLO() {
        return SIMBOLO;
    }

    public static ERtoAFNTokens getFIN_CADENA() {
        return FIN_CADENA;
    }

    public static ERtoAFNTokens getNO_EXISTE() {
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

    private ERtoAFNTokens(int token, String expresionRegular, String claseLexica) {
        this.token = token;
        this.expresionRegular = expresionRegular;
        this.claseLexica = claseLexica;
    }

    public static ERtoAFNTokens getEnumTokensByToken(int tokenBuscado) {
        return Arrays.stream(values())
        .filter(token -> token.getToken() == tokenBuscado)
        .findFirst().orElse(NO_EXISTE);
    }
}
