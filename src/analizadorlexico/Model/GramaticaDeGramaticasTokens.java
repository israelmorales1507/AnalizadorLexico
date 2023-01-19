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
public enum GramaticaDeGramaticasTokens {
    
    SIMBOLO(10,"(([!-,]|.|[0-:]|[<-{]|[}-¿])|(\\\\&(\\-|\\||;| |\t|\n|/)))+","SIMBOLO"),
    FLECHA(20,"\\-&>","FLECHA"),
    OR(30,"\\|","OR"),
    FIN_LISTA_REGLAS(40,";","FIN_LISTA_REGLAS"),
    COMENTARIO(CaracteresEspeciales.OMITIR,"/&\\*&([ -¿])*&\\*&/","COMENTARIO"),
    FIN_CADENA(CaracteresEspeciales.FIN,"\0",null),
    NO_EXISTE(CaracteresEspeciales.ERROR,null,null);
    
    private final int token;
    private final String expresionRegular;
    private final String claseLexica;

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
