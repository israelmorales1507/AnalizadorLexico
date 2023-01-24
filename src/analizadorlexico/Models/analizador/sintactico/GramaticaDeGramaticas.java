/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Models.analizador.sintactico;

import analizadorlexico.Models.AFD.AFD;
import analizadorlexico.Models.Gramatica.SimboloGramatica;
import analizadorlexico.Models.analizador.lexico.AnalizadorLexico;
import analizadorlexico.Models.enums.GramaticaDeGramaticasTokens;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Israel Morales
 */
public class GramaticaDeGramaticas {

    private AnalizadorLexico analizadorLexico;
    private ArrayList<List<SimboloGramatica>> arregloReglas;
    private HashMap<String, SimboloGramatica> conjuntoSimbolos;

    public AnalizadorLexico getAnalizadorLexico() {
        return analizadorLexico;
    }

    public void setAnalizadorLexico(AnalizadorLexico analizadorLexico) {
        this.analizadorLexico = analizadorLexico;
    }

    public ArrayList<List<SimboloGramatica>> getArregloReglas() {
        return arregloReglas;
    }

    public void setArregloReglas(ArrayList<List<SimboloGramatica>> arregloReglas) {
        this.arregloReglas = arregloReglas;
    }

    public HashMap<String, SimboloGramatica> getConjuntoSimbolos() {
        return conjuntoSimbolos;
    }

    public void setConjuntoSimbolos(HashMap<String, SimboloGramatica> conjuntoSimbolos) {
        this.conjuntoSimbolos = conjuntoSimbolos;
    }

    public GramaticaDeGramaticas(AnalizadorLexico analizadorLexico, ArrayList<List<SimboloGramatica>> arregloReglas, HashMap<String, SimboloGramatica> conjuntoSimbolos) {
        this.analizadorLexico = analizadorLexico;
        this.arregloReglas = arregloReglas;
        this.conjuntoSimbolos = conjuntoSimbolos;
    }

    public GramaticaDeGramaticas() {
    }
    
    public GramaticaDeGramaticas(String cadenaGramatica, AFD afdGG) {
        this.analizadorLexico = new AnalizadorLexico(cadenaGramatica, afdGG);
        this.arregloReglas = new ArrayList<>();
        this.conjuntoSimbolos = new HashMap<>();
    }

    public boolean analizarGramatica() {
        GramaticaDeGramaticasTokens token;
        if (Gramatica()) {
            //Verificar que la gramática termine con ; para evitar errores
            analizadorLexico.undoToken();
            token = GramaticaDeGramaticasTokens.getEnumTokensByToken(analizadorLexico.yylex());
            if (token != GramaticaDeGramaticasTokens.FIN_LISTA_REGLAS) {
                return false;
            }
            //Verificar que se haya analizado toda la cadena
            token = GramaticaDeGramaticasTokens.getEnumTokensByToken(analizadorLexico.yylex());
            if (token == GramaticaDeGramaticasTokens.FIN_CADENA) {
                return true;
            }
        }
        return false;
    }

    public boolean Gramatica() {
        return ConjuntoDeReglas();
    }

    public boolean ConjuntoDeReglas() {
        if (ListaDeReglas()) {
            if (ConjuntoDeReglasP()) {
                return true;
            }
        }
        return false;
    }

    public boolean ConjuntoDeReglasP() {
        if (ListaDeReglas()) {
            if (ConjuntoDeReglasP()) {
                return true;
            }
            return false;
        }
        analizadorLexico.undoToken();
        return true;
    }

    public boolean ListaDeReglas() {
        GramaticaDeGramaticasTokens token;
        token = GramaticaDeGramaticasTokens.getEnumTokensByToken(analizadorLexico.yylex());
        if (token == GramaticaDeGramaticasTokens.SIMBOLO) {

            // Recuperar simbolo o crear nuevo
            SimboloGramatica ladoIzquierdo = creaSimbolo(analizadorLexico.getLexema());

            token = GramaticaDeGramaticasTokens.getEnumTokensByToken(analizadorLexico.yylex());
            if (token == GramaticaDeGramaticasTokens.FLECHA) {
                if (ListaDeLadosDerechos(ladoIzquierdo)) {
                    token = GramaticaDeGramaticasTokens.getEnumTokensByToken(analizadorLexico.yylex());
                    if (token == GramaticaDeGramaticasTokens.FIN_LISTA_REGLAS) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    public boolean ListaDeLadosDerechos(SimboloGramatica ladoIzquierdo) {
        if (LadoDerecho(ladoIzquierdo)) {
            if (ListaDeLadosDerechosP(ladoIzquierdo)) {
                return true;
            }
        }
        return false;
    }

    public boolean ListaDeLadosDerechosP(SimboloGramatica ladoIzquierdo) {
        GramaticaDeGramaticasTokens token;
        token = GramaticaDeGramaticasTokens.getEnumTokensByToken(analizadorLexico.yylex());
        if (token == GramaticaDeGramaticasTokens.OR) {
            if (LadoDerecho(ladoIzquierdo)) {
                if (ListaDeLadosDerechosP(ladoIzquierdo)) {
                    return true;
                }
            }
            return false;
        }
        analizadorLexico.undoToken();
        return true;
    }

    public boolean LadoDerecho(SimboloGramatica ladoIzquierdo) {
        GramaticaDeGramaticasTokens token;
        token = GramaticaDeGramaticasTokens.getEnumTokensByToken(analizadorLexico.yylex());
        List<SimboloGramatica> listaReglas = new ArrayList<>();
        if (token == GramaticaDeGramaticasTokens.SIMBOLO) {
            SimboloGramatica ladoDerecho = creaSimbolo(analizadorLexico.getLexema());
            if (LadoDerechoP(listaReglas)) {
                listaReglas.add(ladoDerecho);
                listaReglas.add(ladoIzquierdo);
                Collections.reverse(listaReglas);
                arregloReglas.add(listaReglas);
                return true;
            }
        }
        return false;
    }

    public boolean LadoDerechoP(List<SimboloGramatica> listaReglas) {
        GramaticaDeGramaticasTokens token;
        token = GramaticaDeGramaticasTokens.getEnumTokensByToken(analizadorLexico.yylex());
        if (token == GramaticaDeGramaticasTokens.SIMBOLO) {
            SimboloGramatica ladoDerecho = creaSimbolo(analizadorLexico.getLexema());
            if (LadoDerechoP(listaReglas)) {
                listaReglas.add(ladoDerecho);
                return true;
            }
            return false;
        }
        analizadorLexico.undoToken();
        return true;
    }

    public SimboloGramatica creaSimbolo(String nombreSimbolo) {
        SimboloGramatica simboloGramatica;
        if (conjuntoSimbolos.containsKey(nombreSimbolo)) {
            simboloGramatica = conjuntoSimbolos.get(nombreSimbolo);
        } else {
            simboloGramatica = new SimboloGramatica(analizadorLexico.getLexema());
            conjuntoSimbolos.put(nombreSimbolo, simboloGramatica);
        }
        return simboloGramatica;
    }
}
