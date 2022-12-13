/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Controller;

import analizadorlexico.Model.AFD;
import analizadorlexico.Model.Parametro;

/**
 *
 * @author Israel Morales
 */
public class EvaludorExpresion {

    String expresion;
    float result;
    String expresionPostFijo;
    AnalizadorLexico lexico;

    public EvaludorExpresion(String expresion, AFD automataAFD) {
        this.expresion = expresion;
        this.lexico = new AnalizadorLexico(expresion, automataAFD);
    }

    public void SetExpresion(String expresion) {
        this.expresion = expresion;
        this.lexico.SetSigma(expresion);
    }

    public boolean InitEvaluacion() {
        int token;
        float v;
//        String postFijo = "";
//        v = (float) 0;
        Parametro parametros = new Parametro();
        parametros.setPostFijo("");
        parametros.setV((float) 0.0);

        if (E(parametros)) {
            token = lexico.yylex();
            if (token == 0) {
                result = parametros.getV();
                expresionPostFijo = parametros.getPostFijo();
                return true;
            }
        }
        return false;
    }

    public boolean E(Parametro parametros) {
        if (T(parametros)) {
            if (Ep(parametros)) {
                return true;
            }
        }
        return false;
    }

    public boolean Ep(Parametro parametros) {
        int token;
        float v2 = 0;
        String postFijo2 = "";
        token = lexico.yylex();
        if (token  == 10 || token == 20) {
            if (T(parametros)) {
                parametros.setV(parametros.getV() + (token  == 20 ? v2 : -v2));
                parametros.setPostFijo(parametros.getPostFijo() + " " + postFijo2 + " " + (token == 10 ? "+" : "-"));
                if (Ep(parametros)) {
                    return true;
                }
            }
            return false;
        }
        lexico.UndoToken();
        return true;
    }
    
    public boolean T(Parametro parametros) {
        if (F(parametros)) {
            if (Tp(parametros)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean Tp(Parametro parametros){
        int token;
        float v2 = 0;
        String postFijo2 = "";
        token = lexico.yylex();
        if (token  == 30 || token == 40) {
            if (F(parametros)) {
                parametros.setV(parametros.getV() * (token  == 30 ? v2 : (1/v2)));
                parametros.setPostFijo(parametros.getPostFijo() + " " + postFijo2 + " " + (token == 30 ? "*" : "/"));
                if (Tp(parametros)) {
                    return true;
                }
            }
            return false;
        }
        lexico.UndoToken();
        return true;
    }

    public boolean F(Parametro parametros){
        int token;
        token = lexico.yylex();
        switch (token) {
            case 50:
                if (E(parametros)) {
                    token = lexico.yylex();
                    if (token == 60) {
                        return true;
                    }
                }
                return false;
            case 70:
                parametros.setV(Float.parseFloat(lexico.Lexema));
                parametros.setPostFijo(lexico.Lexema);
                return true;
        }
        return false;
    }
}
