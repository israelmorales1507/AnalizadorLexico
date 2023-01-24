/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Models.analizador.lexico;

import analizadorlexico.Models.AFD.AFD;
import analizadorlexico.Models.enums.SimbolosEspeciales;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Israel Morales
 */
public class AnalizadorLexico {
    
    private AFD afd;
    private String cadenaSigma;
    private String lexema;
    private int token;
    private int idEstadoActual;
    private int idEstadoTransicion;
    private int posicionInicialLexema;
    private int posicionFinalLexema;
    private int posicionCaracterActual;
    private Character caracterActual;
    private boolean esLexema;
    private Stack<Integer> pilaPosicionesCaracteres;

    public AFD getAfd() {
        return afd;
    }

    public void setAfd(AFD afd) {
        this.afd = afd;
    }

    public String getCadenaSigma() {
        return cadenaSigma;
    }

    public void setCadenaSigma(String cadenaSigma) {
        this.cadenaSigma = cadenaSigma;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public int getIdEstadoActual() {
        return idEstadoActual;
    }

    public void setIdEstadoActual(int idEstadoActual) {
        this.idEstadoActual = idEstadoActual;
    }

    public int getIdEstadoTransicion() {
        return idEstadoTransicion;
    }

    public void setIdEstadoTransicion(int idEstadoTransicion) {
        this.idEstadoTransicion = idEstadoTransicion;
    }

    public int getPosicionInicialLexema() {
        return posicionInicialLexema;
    }

    public void setPosicionInicialLexema(int posicionInicialLexema) {
        this.posicionInicialLexema = posicionInicialLexema;
    }

    public int getPosicionFinalLexema() {
        return posicionFinalLexema;
    }

    public void setPosicionFinalLexema(int posicionFinalLexema) {
        this.posicionFinalLexema = posicionFinalLexema;
    }

    public int getPosicionCaracterActual() {
        return posicionCaracterActual;
    }

    public void setPosicionCaracterActual(int posicionCaracterActual) {
        this.posicionCaracterActual = posicionCaracterActual;
    }

    public Character getCaracterActual() {
        return caracterActual;
    }

    public void setCaracterActual(Character caracterActual) {
        this.caracterActual = caracterActual;
    }

    public boolean isEsLexema() {
        return esLexema;
    }

    public void setEsLexema(boolean esLexema) {
        this.esLexema = esLexema;
    }

    public Stack<Integer> getPilaPosicionesCaracteres() {
        return pilaPosicionesCaracteres;
    }

    public void setPilaPosicionesCaracteres(Stack<Integer> pilaPosicionesCaracteres) {
        this.pilaPosicionesCaracteres = pilaPosicionesCaracteres;
    }    
    
    public AnalizadorLexico(AFD afd, String cadenaSigma, String lexema, int token, int idEstadoActual, int idEstadoTransicion, int posicionInicialLexema, int posicionFinalLexema, int posicionCaracterActual, Character caracterActual, boolean esLexema, Stack<Integer> pilaPosicionesCaracteres) {
        this.afd = afd;
        this.cadenaSigma = cadenaSigma;
        this.lexema = lexema;
        this.token = token;
        this.idEstadoActual = idEstadoActual;
        this.idEstadoTransicion = idEstadoTransicion;
        this.posicionInicialLexema = posicionInicialLexema;
        this.posicionFinalLexema = posicionFinalLexema;
        this.posicionCaracterActual = posicionCaracterActual;
        this.caracterActual = caracterActual;
        this.esLexema = esLexema;
        this.pilaPosicionesCaracteres = pilaPosicionesCaracteres;
    }
    
    

    public AnalizadorLexico() {
        cadenaSigma = "";
        posicionInicialLexema = -1;
        posicionFinalLexema = -1;
        token = -1;
        pilaPosicionesCaracteres = new Stack<>();
    }

    public AnalizadorLexico(String sigma, String nombreArchivo, int IdAFD) throws IOException, ClassNotFoundException {
        afd = new AFD(nombreArchivo, IdAFD);
        cadenaSigma = sigma;
        esLexema = false;
        posicionInicialLexema = 0;
        posicionFinalLexema = -1;
        posicionCaracterActual = 0;
        token = -1;
        pilaPosicionesCaracteres = new Stack<>();
    }

    public AnalizadorLexico(String sigma, String nombreArchivo) throws IOException, ClassNotFoundException {
        AFD afd = new AFD(nombreArchivo);
        this.afd = afd.recuperarObjetoAFD();
        
        cadenaSigma = sigma;
        esLexema = false;
        posicionInicialLexema = 0;
        posicionFinalLexema = -1;
        posicionCaracterActual = 0;
        token = -1;
        pilaPosicionesCaracteres = new Stack<>();
    }

    public AnalizadorLexico(String nombreArchivo, int idAFD) throws IOException, ClassNotFoundException {
        afd = new AFD(nombreArchivo, idAFD);
        cadenaSigma = "";
        esLexema = false;
        posicionInicialLexema = 0;
        posicionFinalLexema = -1;
        posicionCaracterActual = 0;
        token = -1;
    }

    public AnalizadorLexico(String sigma, AFD afd) {
        cadenaSigma = sigma;
        esLexema = false;
        posicionInicialLexema = 0;
        posicionFinalLexema = -1;
        posicionCaracterActual = 0;
        token = -1;
        pilaPosicionesCaracteres = new Stack<>();
        this.afd = afd;
    }

    public int yylex() {
        while(true){
            pilaPosicionesCaracteres.push(posicionCaracterActual);
            //Validar que no sea el fin de la cadena
            if(posicionCaracterActual >= cadenaSigma.length()){
                lexema = "";
                token = SimbolosEspeciales.FIN.getSimbolo();
                return token;
            }

            posicionInicialLexema = posicionCaracterActual;
            idEstadoActual = 0;
            esLexema = false;
            posicionFinalLexema = -1;
            token = -1;

            while(posicionCaracterActual < cadenaSigma.length()){
                caracterActual = cadenaSigma.charAt(posicionCaracterActual);
                // Validar que caracterActual sea parte del alfabeto
                if(afd.getTabla().containsColumn(caracterActual.toString()))
                    idEstadoTransicion = afd.getTabla().get(idEstadoActual,caracterActual.toString());
                else break;
                // Validar que haya transicion
                if(idEstadoTransicion != -1){
                    // Recuperar token asociado al estadoTransicion
                    if(afd.getEstadosAceptacion().containsKey(idEstadoTransicion)
                    &&(afd.getEstadosAceptacion().get(idEstadoTransicion) != -1)){
                        esLexema = true;
                        token = afd.getEstadosAceptacion().get(idEstadoTransicion);
                        posicionFinalLexema = posicionCaracterActual;
                    }
                    posicionCaracterActual++;
                    idEstadoActual = idEstadoTransicion;
                    continue;
                }
                break;
            }
            if(!esLexema){
                posicionCaracterActual = posicionInicialLexema + 1;
                lexema = cadenaSigma.substring(posicionInicialLexema,posicionInicialLexema+1);
                token = BanderasAnalizadorLexico.NO_ES_LEXEMA.getValorBandera();
                return token;
            }
            lexema = cadenaSigma.substring(posicionInicialLexema,posicionFinalLexema+1);
            posicionCaracterActual = posicionFinalLexema + 1;
            if(token == BanderasAnalizadorLexico.OMITIR_AFN.getValorBandera())
                continue;
            else
                return token;
        }
    }
    public boolean undoToken(){
        if(pilaPosicionesCaracteres.isEmpty())
            return false;
        posicionCaracterActual = pilaPosicionesCaracteres.pop();
        return true;
    }

    public List<AnalizadorLexicoDTO> analizarCadena(){
        List<AnalizadorLexicoDTO> listaAnalizadorLexicoDTO = new ArrayList<>();

        while(token != SimbolosEspeciales.FIN.getSimbolo()){
            AnalizadorLexicoDTO analizadorLexicoAuxiliar = new AnalizadorLexicoDTO();
            analizadorLexicoAuxiliar.setToken(this.yylex());
            analizadorLexicoAuxiliar.setLexema(this.getLexema());
            listaAnalizadorLexicoDTO.add(analizadorLexicoAuxiliar);
        }
        return listaAnalizadorLexicoDTO;
    }
}
