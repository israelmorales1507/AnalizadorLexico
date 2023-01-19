/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Model;

import analizadorlexico.Controller.AnalizadorLexico;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Israel Morales
 */
public class DesRecGram_Gram {

    private AnalizadorLexico analizadorLexico;
    private ArrayList<List<ClaseNodo>> arregloReglas;
    private HashMap<String, ClaseNodo> conjuntoSimbolos;

    public AnalizadorLexico getAnalizadorLexico() {
        return analizadorLexico;
    }

    public void setAnalizadorLexico(AnalizadorLexico analizadorLexico) {
        this.analizadorLexico = analizadorLexico;
    }

    public ArrayList<List<ClaseNodo>> getArregloReglas() {
        return arregloReglas;
    }

    public void setArregloReglas(ArrayList<List<ClaseNodo>> arregloReglas) {
        this.arregloReglas = arregloReglas;
    }

    public HashMap<String, ClaseNodo> getConjuntoSimbolos() {
        return conjuntoSimbolos;
    }

    public void setConjuntoSimbolos(HashMap<String, ClaseNodo> conjuntoSimbolos) {
        this.conjuntoSimbolos = conjuntoSimbolos;
    }

    public DesRecGram_Gram(String cadenaGramatica, AFD afdGG) {
        this.analizadorLexico = new AnalizadorLexico(cadenaGramatica, afdGG);
        this.arregloReglas = new ArrayList<>();
        this.conjuntoSimbolos = new HashMap<>();
    }

    public boolean analizarGramatica() {
        GramaticaDeGramaticasTokens token;
        if (Gramatica()) {
            token = GramaticaDeGramaticasTokens.getEnumTokensByToken(analizadorLexico.yylex());
            if (token != GramaticaDeGramaticasTokens.FIN_LISTA_REGLAS) {
                return false;
            }
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
        analizadorLexico.UndoToken();
        return true;
    }

    public boolean ListaDeReglas() {
        GramaticaDeGramaticasTokens token;
        token = GramaticaDeGramaticasTokens.getEnumTokensByToken(analizadorLexico.yylex());
        if (token == GramaticaDeGramaticasTokens.SIMBOLO) {
            ClaseNodo ladoIzquierdo = creaSimbolo(analizadorLexico.Lexema);
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

    public boolean ListaDeLadosDerechos(ClaseNodo ladoIzquierdo) {
        if (LadoDerecho(ladoIzquierdo)) {
            if (ListaDeLadosDerechosP(ladoIzquierdo)) {
                return true;
            }
        }
        return false;
    }

    public boolean ListaDeLadosDerechosP(ClaseNodo ladoIzquierdo) {
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
        analizadorLexico.UndoToken();
        return true;
    }

    public boolean LadoDerecho(ClaseNodo ladoIzquierdo) {
        GramaticaDeGramaticasTokens token;
        token = GramaticaDeGramaticasTokens.getEnumTokensByToken(analizadorLexico.yylex());
        List<ClaseNodo> listaReglas = new ArrayList<>();
        if (token == GramaticaDeGramaticasTokens.SIMBOLO) {
            ClaseNodo ladoDerecho = creaSimbolo(analizadorLexico.Lexema);
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

    public boolean LadoDerechoP(List<ClaseNodo> listaReglas) {
        GramaticaDeGramaticasTokens token;
        token = GramaticaDeGramaticasTokens.getEnumTokensByToken(analizadorLexico.yylex());
        if (token == GramaticaDeGramaticasTokens.SIMBOLO) {
            ClaseNodo ladoDerecho = creaSimbolo(analizadorLexico.Lexema);
            if (LadoDerechoP(listaReglas)) {
                listaReglas.add(ladoDerecho);
                return true;
            }
            return false;
        }
        analizadorLexico.UndoToken();
        return true;
    }

    public ClaseNodo creaSimbolo(String nombreSimbolo) {
        ClaseNodo simboloGramatica;
        if (conjuntoSimbolos.containsKey(nombreSimbolo)) {
            simboloGramatica = conjuntoSimbolos.get(nombreSimbolo);
        } else {
            simboloGramatica = new ClaseNodo(analizadorLexico.Lexema);
            conjuntoSimbolos.put(nombreSimbolo, simboloGramatica);
        }
        return simboloGramatica;
    }
}
