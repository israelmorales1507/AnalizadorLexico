/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Model;

import java.util.Stack;

/**
 *
 * @author Israel Morales
 */
public class EstadoAnalizadorLexico {
    
    int token, EdoActual, EdoTransicion;
    String CadenaSigma;
    String Lexema; //yytext
    boolean PasoPorEdoAcept;
    int InitLexema, FinLexama, IndiceCaracterActual;
    char CaracterActual;
    Stack<Integer> stack = new Stack<Integer>();
    AFD automataFd;

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public int getEdoActual() {
        return EdoActual;
    }

    public void setEdoActual(int EdoActual) {
        this.EdoActual = EdoActual;
    }

    public int getEdoTransicion() {
        return EdoTransicion;
    }

    public void setEdoTransicion(int EdoTransicion) {
        this.EdoTransicion = EdoTransicion;
    }

    public String getCadenaSigma() {
        return CadenaSigma;
    }

    public void setCadenaSigma(String CadenaSigma) {
        this.CadenaSigma = CadenaSigma;
    }

    public String getLexema() {
        return Lexema;
    }

    public void setLexema(String Lexema) {
        this.Lexema = Lexema;
    }

    public boolean isPasoPorEdoAcept() {
        return PasoPorEdoAcept;
    }

    public void setPasoPorEdoAcept(boolean PasoPorEdoAcept) {
        this.PasoPorEdoAcept = PasoPorEdoAcept;
    }

    public int getInitLexema() {
        return InitLexema;
    }

    public void setInitLexema(int InitLexema) {
        this.InitLexema = InitLexema;
    }

    public int getFinLexama() {
        return FinLexama;
    }

    public void setFinLexama(int FinLexama) {
        this.FinLexama = FinLexama;
    }

    public int getIndiceCaracterActual() {
        return IndiceCaracterActual;
    }

    public void setIndiceCaracterActual(int IndiceCaracterActual) {
        this.IndiceCaracterActual = IndiceCaracterActual;
    }

    public char getCaracterActual() {
        return CaracterActual;
    }

    public void setCaracterActual(char CaracterActual) {
        this.CaracterActual = CaracterActual;
    }

    public Stack<Integer> getStack() {
        return stack;
    }

    public void setStack(Stack<Integer> stack) {
        this.stack = stack;
    }

    public AFD getAutomataFd() {
        return automataFd;
    }

    public void setAutomataFd(AFD automataFd) {
        this.automataFd = automataFd;
    }
    
    public EstadoAnalizadorLexico() {
    }
    
    
}
