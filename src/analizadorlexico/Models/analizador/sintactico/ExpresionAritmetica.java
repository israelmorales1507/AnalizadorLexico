/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Models.analizador.sintactico;

import analizadorlexico.Models.analizador.lexico.AnalizadorLexico;
import analizadorlexico.Models.enums.ExpresionesAritmeticasTokens;
import static analizadorlexico.Models.enums.ExpresionesAritmeticasTokens.DIVISION;
import static analizadorlexico.Models.enums.ExpresionesAritmeticasTokens.FIN_CADENA;
import static analizadorlexico.Models.enums.ExpresionesAritmeticasTokens.MULTIPLICACION;
import static analizadorlexico.Models.enums.ExpresionesAritmeticasTokens.PARENTESIS_DERECHO;
import static analizadorlexico.Models.enums.ExpresionesAritmeticasTokens.RESTA;
import static analizadorlexico.Models.enums.ExpresionesAritmeticasTokens.SUMA;

/**
 *
 * @author Israel Morales
 */
public class ExpresionAritmetica {
    private ExpresionAritmeticaValores expresionAritmeticaResultado;
    private String expresionAritmetica;
    private AnalizadorLexico analizadorLexico;

    public ExpresionAritmeticaValores getExpresionAritmeticaResultado() {
        return expresionAritmeticaResultado;
    }

    public void setExpresionAritmeticaResultado(ExpresionAritmeticaValores expresionAritmeticaResultado) {
        this.expresionAritmeticaResultado = expresionAritmeticaResultado;
    }

    public String getExpresionAritmetica() {
        return expresionAritmetica;
    }

    public void setExpresionAritmetica(String expresionAritmetica) {
        this.expresionAritmetica = expresionAritmetica;
    }

    public AnalizadorLexico getAnalizadorLexico() {
        return analizadorLexico;
    }

    public void setAnalizadorLexico(AnalizadorLexico analizadorLexico) {
        this.analizadorLexico = analizadorLexico;
    }

    
    public ExpresionAritmetica(ExpresionAritmeticaValores expresionAritmeticaResultado, String expresionAritmetica, AnalizadorLexico analizadorLexico) {
        this.expresionAritmeticaResultado = expresionAritmeticaResultado;
        this.expresionAritmetica = expresionAritmetica;
        this.analizadorLexico = analizadorLexico;
    }

    public ExpresionAritmetica() {
    }
    
    public ExpresionAritmetica(AnalizadorLexico analizadorLexico){
        this.analizadorLexico = analizadorLexico;
        this.expresionAritmeticaResultado = new ExpresionAritmeticaValores();
    }

    public boolean iniciarEvaluacionArtimetica(){
        ExpresionesAritmeticasTokens token;
        ExpresionAritmeticaValores expresionAritmeticaValores = new ExpresionAritmeticaValores();
        if(E(expresionAritmeticaValores)){
            token = ExpresionesAritmeticasTokens.getEnumTokensByToken(analizadorLexico.yylex());
            if(token==FIN_CADENA){
                this.expresionAritmeticaResultado = expresionAritmeticaValores;
                return true;
            }
        }
        return false;
    }

    private boolean E(ExpresionAritmeticaValores expresionAritmeticaPorReferencia) {
        if(T(expresionAritmeticaPorReferencia))
            if(Ep(expresionAritmeticaPorReferencia))
                return true;
        return false;
    }

    private boolean Ep(ExpresionAritmeticaValores expresionAritmeticaPorReferencia) {
        ExpresionesAritmeticasTokens token;
        ExpresionAritmeticaValores expresionAritmeticaValores = new ExpresionAritmeticaValores();

        token = ExpresionesAritmeticasTokens.getEnumTokensByToken(analizadorLexico.yylex());

        if(token==SUMA ||token==RESTA){
            if(T(expresionAritmeticaValores)){

                expresionAritmeticaPorReferencia.valorNumerico = expresionAritmeticaPorReferencia.valorNumerico + (token==SUMA ? expresionAritmeticaValores.valorNumerico: (-1)*expresionAritmeticaValores.valorNumerico);

                expresionAritmeticaPorReferencia.expresionPostFija =String.format("%s %s %s",expresionAritmeticaPorReferencia.expresionPostFija,expresionAritmeticaValores.expresionPostFija,(token==SUMA ? "+" : "-"));

                if(Ep(expresionAritmeticaPorReferencia))
                    return true;
            }
            return false;
        }
        analizadorLexico.undoToken();
        return true;
    }

    private boolean T(ExpresionAritmeticaValores expresionAritmeticaPorReferencia) {
        if(F(expresionAritmeticaPorReferencia))
            if(Tp(expresionAritmeticaPorReferencia))
                return true;
        return false;
    }

    private boolean Tp(ExpresionAritmeticaValores expresionAritmeticaPorReferencia) {
        ExpresionesAritmeticasTokens token;
        ExpresionAritmeticaValores expresionAritmeticaValores = new ExpresionAritmeticaValores();

        token = ExpresionesAritmeticasTokens.getEnumTokensByToken(analizadorLexico.yylex());

        if(token==MULTIPLICACION ||token==DIVISION){
            if(F(expresionAritmeticaValores)){

                expresionAritmeticaPorReferencia.valorNumerico = expresionAritmeticaPorReferencia.valorNumerico * (token==MULTIPLICACION ? expresionAritmeticaValores.valorNumerico: 1/expresionAritmeticaValores.valorNumerico);

                expresionAritmeticaPorReferencia.expresionPostFija =String.format("%s %s %s",expresionAritmeticaPorReferencia.expresionPostFija,expresionAritmeticaValores.expresionPostFija,(token==MULTIPLICACION ? "*" : "/"));

                if(Tp(expresionAritmeticaPorReferencia))
                    return true;
            }
            return false;
        }
        analizadorLexico.undoToken();
        return true;
    }

    private boolean F(ExpresionAritmeticaValores expresionAritmeticaPorReferencia) {
        ExpresionesAritmeticasTokens token;
        token = ExpresionesAritmeticasTokens.getEnumTokensByToken(analizadorLexico.yylex());
        switch (token) {
            case PARENTESIS_IZQUIERDO:
                if(E(expresionAritmeticaPorReferencia)){
                    token = ExpresionesAritmeticasTokens.getEnumTokensByToken(analizadorLexico.yylex());
                    if(token==PARENTESIS_DERECHO)
                        return true;
                }
                return false;
            case NUMERO:
                expresionAritmeticaPorReferencia.valorNumerico = Float.parseFloat(analizadorLexico.getLexema());
                expresionAritmeticaPorReferencia.expresionPostFija =analizadorLexico.getLexema();
                return true;
        
            default:
                break;
        }
        return false;
    }
}
