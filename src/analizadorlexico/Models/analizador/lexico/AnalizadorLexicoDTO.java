/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Models.analizador.lexico;

/**
 *
 * @author Israel Morales
 */
public class AnalizadorLexicoDTO {
    private int token;
    private String claseLexica;
    private String lexema;

    public AnalizadorLexicoDTO() {
    }

    public AnalizadorLexicoDTO(int token, String claseLexica, String lexema) {
        this.token = token;
        this.claseLexica = claseLexica;
        this.lexema = lexema;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public String getClaseLexica() {
        return claseLexica;
    }

    public void setClaseLexica(String claseLexica) {
        this.claseLexica = claseLexica;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }
    
}
