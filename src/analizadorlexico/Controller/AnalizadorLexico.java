/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Controller;

import analizadorlexico.Model.AFD;
import analizadorlexico.Model.AFN;
import analizadorlexico.Model.CaracteresEspeciales;
import analizadorlexico.Model.EstadoAnalizadorLexico;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Stack;

/**
 *
 * @author Israel Morales
 */
public class AnalizadorLexico {

    int token, EdoActual, EdoTransicion;
    public String CadenaSigma;
    public String Lexema; //yytext
    boolean PasoPorEdoAcept;
    int InitLexema, FinLexama, IndiceCaracterActual;
    char CaracterActual;
    Stack<Integer> stack = new Stack<Integer>();
    AFD automatAFd;
    ArrayList<String> tablaLexico = new ArrayList<String>();
    
    public AnalizadorLexico(String sigma, AFD automaAFD) {
        CadenaSigma = sigma;
        PasoPorEdoAcept = false;
        InitLexema = 0;
        FinLexama = -1;
        IndiceCaracterActual = 0;
        token = -1;
        stack.clear();
        this.automatAFd = automaAFD;
    }
    
    public EstadoAnalizadorLexico getEdoAnalizadorLexico() {
        EstadoAnalizadorLexico edoActual = new EstadoAnalizadorLexico();
        edoActual.setCaracterActual(CaracterActual);
        edoActual.setEdoActual(EdoActual);
        edoActual.setEdoTransicion(EdoTransicion);
        edoActual.setFinLexama(FinLexama);
        edoActual.setIndiceCaracterActual(IndiceCaracterActual);
        edoActual.setInitLexema(InitLexema);
        edoActual.setLexema(Lexema);
        edoActual.setPasoPorEdoAcept(PasoPorEdoAcept);
        edoActual.setToken(token);
        edoActual.setStack(stack);
        return edoActual;
    }

    public boolean SetEdoAnalizadorLexico(EstadoAnalizadorLexico e) {
        this.CaracterActual = e.getCaracterActual();
        this.EdoActual = e.getEdoActual();
        this.EdoTransicion = e.getEdoTransicion();
        this.FinLexama = e.getFinLexama();
        this.IndiceCaracterActual = e.getIndiceCaracterActual();
        this.InitLexema = e.getInitLexema();
        this.Lexema = e.getLexema();
        this.PasoPorEdoAcept = e.isPasoPorEdoAcept();
        this.token = e.getToken();
        this.stack = e.getStack();
        return true;
    }

    public void SetSigma(String sigma) {
        CadenaSigma = sigma;
        PasoPorEdoAcept = false;
        InitLexema = 0;
        FinLexama = -1;
        IndiceCaracterActual = 0;
        token = -1;
        stack.clear();
    }

    public String CadenaXanalizar() {
        return CadenaSigma.substring(IndiceCaracterActual, CadenaSigma.length() - IndiceCaracterActual);
    }

    public int yylex() {
        while (true) {
            stack.push(IndiceCaracterActual);
            if (IndiceCaracterActual >= CadenaSigma.length()) {
                Lexema = "";
                return CaracteresEspeciales.FIN;
            }
            InitLexema = IndiceCaracterActual;
            EdoActual = 0;
            FinLexama = -1;
            token = -1;

            while (IndiceCaracterActual < CadenaSigma.length()) {
                CaracterActual = CadenaSigma.charAt(IndiceCaracterActual);
                EdoTransicion = automatAFd.tabular[EdoActual][CaracterActual];
                if (EdoTransicion != -1) {
                    if (automatAFd.tabular[EdoTransicion][CaracteresEspeciales.ARREGLO - 1] != -1) {
                        PasoPorEdoAcept = true;
                        token = automatAFd.tabular[EdoTransicion][CaracteresEspeciales.ARREGLO - 1];
                        FinLexama = IndiceCaracterActual;
                    }
                    IndiceCaracterActual++;
                    EdoActual = EdoTransicion;
                    continue;
                }
                break;
            }

            if (!PasoPorEdoAcept) {
                IndiceCaracterActual = InitLexema + 1;
                Lexema = CadenaSigma.substring(InitLexema, 1);
                token = CaracteresEspeciales.ERROR;
                return token;
            }

            Lexema = CadenaSigma.substring(InitLexema, FinLexama + 1);
            IndiceCaracterActual = FinLexama + 1;
            if (token == CaracteresEspeciales.OMITIR) {
                continue;
            } else {
                return token;
            }
        }
    }

    public boolean UndoToken() {
        if (stack.isEmpty()) {
            return false;
        }
        IndiceCaracterActual = stack.pop();
        return true;
    }

    public ArrayList<String> analizarcadena() {
        int first = this.yylex();
        tablaLexico.add("" + first + "," + this.Lexema);
        while (first != CaracteresEspeciales.FIN) {
            first = this.yylex();
            tablaLexico.add("" + first + "," + this.Lexema);
        }
        return tablaLexico;
    }
}
