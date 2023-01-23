/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Model;

import analizadorlexico.Controller.AnalizadorLexico;

/**
 *
 * @author Israel Morales
 */
public class ExpresionRegularToAFN {

    private String expresionRegular;
    private AFN afnResultante;
    private AnalizadorLexico analizadorLexico;
    
    public String getExpresionRegular() {
        return expresionRegular;
    }

    public void setExpresionRegular(String expresionRegular) {
        this.expresionRegular = expresionRegular;
    }

    public AFN getAfnResultante() {
        return afnResultante;
    }

    public void setAfnResultante(AFN afnResultante) {
        this.afnResultante = afnResultante;
    }

    public AnalizadorLexico getAnalizadorLexico() {
        return analizadorLexico;
    }

    public void setAnalizadorLexico(AnalizadorLexico analizadorLexico) {
        this.analizadorLexico = analizadorLexico;
    }
    
    public ExpresionRegularToAFN(AnalizadorLexico analizadorLexico){
        this.analizadorLexico = analizadorLexico;
    }

    public ExpresionRegularToAFN(String expresionRegular, AFN afnResultante, AnalizadorLexico analizadorLexico) {
        this.expresionRegular = expresionRegular;
        this.afnResultante = afnResultante;
        this.analizadorLexico = analizadorLexico;
    }
    
    
    //Escribir constructores que puedan crear analizador lexico
    public ExpresionRegularToAFN(String cadenaSigma, AFD afd){
        AnalizadorLexico analizadorLexico = new AnalizadorLexico(cadenaSigma,afd);
        this.analizadorLexico = analizadorLexico;
    }
    public boolean iniciarConversionExpresionRegularToAFN(){
        ERtoAFNTokens token;
        AFN afnResultado = new AFN();
        if(E(afnResultado)){
            token = ERtoAFNTokens.getEnumTokensByToken(analizadorLexico.yylex());
            if(token == ERtoAFNTokens.FIN_CADENA){
                this.afnResultante = afnResultado;
                return true;
            }
        }
        return false;
    }
    private boolean E(AFN afnPorReferencia) {
        if(T(afnPorReferencia)){
            if(Ep(afnPorReferencia)){
                return true;
            }
        }
        return false;
    }
    private boolean Ep(AFN afnPorReferencia) {
        ERtoAFNTokens token;
        AFN nuevoAfn = new AFN();
        token = ERtoAFNTokens.getEnumTokensByToken(analizadorLexico.yylex());
        if(token==ERtoAFNTokens.OR){
            if(T(nuevoAfn)){
                afnPorReferencia.UnirAFNS(nuevoAfn);
                if(Ep(afnPorReferencia))
                    return true;
            }
            return false;
        }
        analizadorLexico.UndoToken();
        return true;
    }
    private boolean T(AFN afnPorReferencia) {
        if(C(afnPorReferencia))
            if(Tp(afnPorReferencia))
                return true;
        return false;
    }
    private boolean Tp(AFN afnPorReferencia) {
        ERtoAFNTokens token;
        AFN nuevoAfn = new AFN();
        token = ERtoAFNTokens.getEnumTokensByToken(analizadorLexico.yylex());
        if(token == ERtoAFNTokens.CONCATENAR){
            if(C(nuevoAfn)){
                afnPorReferencia.ConcatenacionAFN(nuevoAfn);
                if(Tp(afnPorReferencia)){
                    return true;
                }
                return false;
            }
        }
        analizadorLexico.UndoToken();
        return true;
    }
    private boolean C(AFN afnPorReferencia) {
        if(F(afnPorReferencia))
            if(Cp(afnPorReferencia))
                return true;
        return false;
    }
    private boolean Cp(AFN afnPorReferencia) {
        ERtoAFNTokens token;
        token = ERtoAFNTokens.getEnumTokensByToken(analizadorLexico.yylex());
        switch(token){
            case CERRADURA_POSITIVA:
                afnPorReferencia.CerraduraPos();
                if(Cp(afnPorReferencia))
                    return true;
                return false;
            case CERRADURA_KLEEN:
                afnPorReferencia.CerraduraKleen();
                if(Cp(afnPorReferencia))
                    return true;
                return false;
            case OPCIONAL:
                afnPorReferencia.Opcional();
                if(Cp(afnPorReferencia))
                    return true;
                return false;
            default:
                break;
        }
        analizadorLexico.UndoToken();
        return true;
    }
    private boolean F(AFN afnPorReferencia) {
        ERtoAFNTokens token;
        char simbolo1,simbolo2;
        token = ERtoAFNTokens.getEnumTokensByToken(analizadorLexico.yylex());
        switch (token) {
            case PARENTESIS_IZQUIERDO:
                if(E(afnPorReferencia)){
                    token = ERtoAFNTokens.getEnumTokensByToken(analizadorLexico.yylex());
                    if(token == ERtoAFNTokens.PARENTESIS_DERECHO)
                        return true;
                }
                return false;
            case CORCHETE_IZQUIERDO:
                token = ERtoAFNTokens.getEnumTokensByToken(analizadorLexico.yylex());
                if(token == ERtoAFNTokens.SIMBOLO){
                    simbolo1 = (analizadorLexico.getLexema().charAt(0)=='\\')?analizadorLexico.getLexema().charAt(1) : analizadorLexico.getLexema().charAt(0);
                    token = ERtoAFNTokens.getEnumTokensByToken(analizadorLexico.yylex());
                    if(token == ERtoAFNTokens.GUION){
                        token = ERtoAFNTokens.getEnumTokensByToken(analizadorLexico.yylex());
                        if(token ==ERtoAFNTokens.SIMBOLO){
                            simbolo2 = (analizadorLexico.getLexema().charAt(0)=='\\')?analizadorLexico.getLexema().charAt(1) : analizadorLexico.getLexema().charAt(0);
                            token = ERtoAFNTokens.getEnumTokensByToken(analizadorLexico.yylex());
                            if(token == ERtoAFNTokens.CORCHETE_DERECHO){
                                afnPorReferencia.CrearAFNBasico(simbolo1,simbolo2);
                                return true;
                            }
                        }
                    }
                }
                return false;
            case SIMBOLO:
                simbolo1 = (analizadorLexico.getLexema().charAt(0)=='\\')?analizadorLexico.getLexema().charAt(1) : analizadorLexico.getLexema().charAt(0); 
                afnPorReferencia.CrearAFNBasico(simbolo1);
                return true;
            default:
                break;
        }
        return false;
    }
}
