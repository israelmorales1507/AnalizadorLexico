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
public enum GramaticaDeGramaticasTokens {
    SIMBOLO(10,"(([!-,]|.|[0-:]|[<-{]|[}-¿])|(\\\\&(\\-|\\||;| |\t|\n|/)))+","SIMBOLO"),// ((\&( |\-|\||;|\t|\n))|[!-,]|[.-:]|[<-{]|[}-¿])+
    FLECHA(20,"\\-&>","FLECHA"),
    OR(30,"\\|","OR"),
    FIN_LISTA_REGLAS(40, ";","FIN_LISTA_REGLAS"),
    //El token -4 indica a yylex que debe omitir esa case léxica y seguir buscando
    ESPACIO(-4,"( |\n|\t)+","ESPACIO"),
    COMENTARIO(-4,"/&\\*&([ -¿])*&\\*&/","COMENTARIO"),
    FIN_CADENA(0,"\0",null),
    NO_EXISTE(-1,null,null);


    private final int token;
    private final String expresionRegular;
    private final String claseLexica;

    public static GramaticaDeGramaticasTokens getSIMBOLO() {
        return SIMBOLO;
    }

    public static GramaticaDeGramaticasTokens getFLECHA() {
        return FLECHA;
    }

    public static GramaticaDeGramaticasTokens getOR() {
        return OR;
    }

    public static GramaticaDeGramaticasTokens getFIN_LISTA_REGLAS() {
        return FIN_LISTA_REGLAS;
    }

    public static GramaticaDeGramaticasTokens getESPACIO() {
        return ESPACIO;
    }

    public static GramaticaDeGramaticasTokens getCOMENTARIO() {
        return COMENTARIO;
    }

    public static GramaticaDeGramaticasTokens getFIN_CADENA() {
        return FIN_CADENA;
    }

    public static GramaticaDeGramaticasTokens getNO_EXISTE() {
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

    
    private GramaticaDeGramaticasTokens(int token, String expresionRegular, String claseLexica) {
        this.token = token;
        this.expresionRegular = expresionRegular;
        this.claseLexica = claseLexica;
    }
    

    public static GramaticaDeGramaticasTokens getEnumTokensByToken(int tokenBuscado) {
        return Arrays.stream(values())
                .filter(token -> token.getToken() == tokenBuscado)
                .findFirst().orElse(NO_EXISTE);
    }
}
