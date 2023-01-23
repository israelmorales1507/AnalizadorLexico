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
public class ERAFN {
    
    String ExprRetular;
    public AFN result;
    public AnalizadorLexico L;

    public String getExprRetular() {
        return ExprRetular;
    }

    public void setExprRetular(String ExprRetular) {
        this.ExprRetular = ExprRetular;
    }

    public AFN getResult() {
        return result;
    }

    public void setResult(AFN result) {
        this.result = result;
    }

    public AnalizadorLexico getL() {
        return L;
    }

    public void setL(AnalizadorLexico L) {
        this.L = L;
    }

    public ERAFN(String sigma, AFD AutFD) {
        System.out.println("Ya detro 2: "+ ExprRetular);
        L = new AnalizadorLexico(sigma, AutFD );
    }
    
    public void SetExpresion(String sigma){
        this.ExprRetular = sigma;
        L.SetSigma(sigma);
    }
    
    public boolean InitConversion(){
        int token;
        ParametrosER parametro = new ParametrosER();
        if (E(parametro)) {
            token = L.yylex();
            if (token == 0) {
                this.result = parametro.getAfn();
                return true;
            }
        }
        return false;
    }

    public boolean E(ParametrosER parametro) {
        if (T(parametro)) {
            if (Ep(parametro)) {
                return true;
            }
        }
        return false;
    }

    public boolean Ep(ParametrosER parametro) {
        int token;
        ParametrosER parametro2 = new ParametrosER();
        token = L.yylex();
        if (token == 10) {
            if (T(parametro2)) {
                parametro.afn.UnirAFNS(parametro2.getAfn());
                if(Ep(parametro)){
                    return true;
                }
            }
            return false;
        }
        L.UndoToken();
        return true;
    }

    public boolean T(ParametrosER parametro) {
        if (C(parametro)) {
            if (Tp(parametro)) {
                return true;
            }
        }
        return false;
    }

    public boolean Tp(ParametrosER parametro) {
        int token;
        ParametrosER parametro2 = new ParametrosER();
        token = L.yylex();
        if (token == 20) {
            if (C(parametro2)) {
                parametro.afn.ConcatenacionAFN(parametro2.getAfn());
                if (Tp(parametro)) {
                    return true;
                }
            }
            return false;
        }
        L.UndoToken();
        return true;
    }

    public boolean C(ParametrosER parametro) {
        if (F(parametro)) {
            if (Cp(parametro)) {
                return true;
            }
        }
        return false;
    }

    public boolean Cp(ParametrosER parametro) {
        int token;
        token = L.yylex();
        switch (token) {
            case 30:
                parametro.afn.CerraduraPos();
                if (Cp(parametro)) {
                    return true;
                }
                return false;
            case 40:
                parametro.afn.CerraduraKleen();
                if (Cp(parametro)) {
                    return true;
                }
                return false;
            case 50:
                parametro.afn.Opcional();
                if (Cp(parametro)) {
                    return true;
                }
                return false;
        }
        L.UndoToken();
        return true;
    }

    public boolean F(ParametrosER parametro) {
        int token;
        char simbolo, simbolo2;
        token = L.yylex();
        switch (token) {
            case 60:
                if (E(parametro)) {
                    token = L.yylex();
                    if (token == 70) {
                        return true;
                    }
                }
                return false;
            case 80:
                token = L.yylex();
                if (token == 110) {
                    simbolo = L.Lexema.charAt(0) == '\\' ? L.Lexema.charAt(1) : L.Lexema.charAt(0);
                    token = L.yylex();
                    if (token == 100) {
                        token = L.yylex();
                        if (token == 110) {
                            simbolo2 = L.Lexema.charAt(0) == '\\' ? L.Lexema.charAt(1) : L.Lexema.charAt(0);
                            token = L.yylex();
                            if (token == 90) {
                                parametro.afn.CrearAFNBasico(simbolo, simbolo2);
                                return true;
                            }
                        }
                    }
                }
                return false;
            case 110:
                System.out.println("Lexama: "+L.Lexema);
                simbolo = (L.Lexema.charAt(0) == '\\') ? L.Lexema.charAt(1) : L.Lexema.charAt(0);
                parametro.afn.CrearAFNBasico(simbolo);
                return true;
            default:
                return false;
        }
    }
}
