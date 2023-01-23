/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Israel Morales
 */
public class Gramatica {
    
    private String cadenaGramatica;
    private ClaseNodo simboloGramaticaInicial;
    private HashSet<ClaseNodo> simbolosTerminales;
    private HashSet<ClaseNodo> simbolosNoTerminales;
    private ArrayList<List<ClaseNodo>> arregloReglas;
    private HashMap<String, ClaseNodo> conjuntoSimbolos;
    private AFD afdGramatica;
    public static ClaseNodo epsilon = new ClaseNodo("epsilon");
    public static ClaseNodo $ = new ClaseNodo("$");
    
    public String getCadenaGramatica() {
        return cadenaGramatica;
    }

    public void setCadenaGramatica(String cadenaGramatica) {
        this.cadenaGramatica = cadenaGramatica;
    }

    public ClaseNodo getSimboloGramaticaInicial() {
        return simboloGramaticaInicial;
    }

    public void setSimboloGramaticaInicial(ClaseNodo simboloGramaticaInicial) {
        this.simboloGramaticaInicial = simboloGramaticaInicial;
    }

    public HashSet<ClaseNodo> getSimbolosTerminales() {
        return simbolosTerminales;
    }

    public void setSimbolosTerminales(HashSet<ClaseNodo> simbolosTerminales) {
        this.simbolosTerminales = simbolosTerminales;
    }

    public HashSet<ClaseNodo> getSimbolosNoTerminales() {
        return simbolosNoTerminales;
    }

    public void setSimbolosNoTerminales(HashSet<ClaseNodo> simbolosNoTerminales) {
        this.simbolosNoTerminales = simbolosNoTerminales;
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

    public AFD getAfdGramatica() {
        return afdGramatica;
    }

    public void setAfdGramatica(AFD afdGramatica) {
        this.afdGramatica = afdGramatica;
    }

    public static ClaseNodo getEpsilon() {
        return epsilon;
    }

    public static void setEpsilon(ClaseNodo epsilon) {
        Gramatica.epsilon = epsilon;
    }

    public static ClaseNodo get$() {
        return $;
    }

    public static void set$(ClaseNodo $) {
        Gramatica.$ = $;
    }

    public boolean crearGramatica(String cadenaGramatica, AFD afd) {
        this.cadenaGramatica = cadenaGramatica;
        simbolosTerminales = new HashSet<>();
        simbolosNoTerminales = new HashSet<>();
        // Crear el analizador lexico de gramáticas        
        AFN afnGramatica = new AFN();
        AFD afdGramatica = afnGramatica.crearAFNGramaticaDeGramaticas().ConvertirAFNaAFD();
        DesRecGram_Gram descensoRecursivoGramatica = new DesRecGram_Gram(cadenaGramatica, afdGramatica);
        if (descensoRecursivoGramatica.analizarGramatica()) {
            arregloReglas = descensoRecursivoGramatica.getArregloReglas();
            conjuntoSimbolos = descensoRecursivoGramatica.getConjuntoSimbolos();
            simboloGramaticaInicial = arregloReglas.get(0).get(0);
            for (List<ClaseNodo> listaReglas : arregloReglas) {
                simbolosNoTerminales.add(listaReglas.get(0));
            }
            for (ClaseNodo simboloGramaticaTerminal : conjuntoSimbolos.values()) {
                if (simboloGramaticaTerminal.equals(epsilon)) // TODO revisar este epsilon                    
                {
                    continue;
                }
                if (!simbolosNoTerminales.contains(simboloGramaticaTerminal)) {
                    simboloGramaticaTerminal.setEsTerminal(true);
                    simbolosTerminales.add(simboloGramaticaTerminal);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public HashSet<ClaseNodo> First(List<ClaseNodo> listaSimbolosFirst) {
        HashSet<ClaseNodo> conjuntoResultadoFirst = new HashSet<>();
        ClaseNodo simboloGramatica = listaSimbolosFirst.get(0);
        if (simboloGramatica.isEsTerminal() || simboloGramatica.equals(epsilon)) {
            conjuntoResultadoFirst.add(simboloGramatica);
            return conjuntoResultadoFirst;
        }
        for (List<ClaseNodo> regla : arregloReglas) {
            if (regla.get(0).equals(simboloGramatica)) {
                List<ClaseNodo> ladosDerechos = new ArrayList<>(regla.subList(1, regla.size()));
                HashSet<ClaseNodo> conjuntoAuxiliar = this.First(ladosDerechos);
                if (!conjuntoAuxiliar.contains(epsilon) || listaSimbolosFirst.size() == 1) { // TODO revisar este epsilon                    
                    conjuntoResultadoFirst.addAll(conjuntoAuxiliar);
                    continue;
                }
                if (conjuntoResultadoFirst.contains(epsilon)) { // TODO revisar este epsilon                    
                    conjuntoResultadoFirst.remove(epsilon);
                    conjuntoResultadoFirst.addAll(this.First(listaSimbolosFirst.subList(1, listaSimbolosFirst.size())));
                }
            }
        }
        return conjuntoResultadoFirst;
    }

    public HashSet<ClaseNodo> Follow(ClaseNodo simboloGramaticaFollow) {
        HashSet<ClaseNodo> conjuntoSimbolosFollow = new HashSet<>();
        HashSet<ClaseNodo> conjuntoAuxiliarFirst = new HashSet<>();
        int indiceSimboloFollow; // TODO borrar este comentario        
        if (simboloGramaticaFollow.equals(simboloGramaticaInicial)) {
            conjuntoSimbolosFollow.add($);
        }
        for (List<ClaseNodo> regla : arregloReglas) {
            indiceSimboloFollow = regla.indexOf(simboloGramaticaFollow);
            if (indiceSimboloFollow == -1 || indiceSimboloFollow == 0) {
                continue;
            }
            ClaseNodo ladoIzquierdo = regla.get(0);
            if (indiceSimboloFollow + 1 == regla.size()) {
                if (simboloGramaticaFollow == ladoIzquierdo) {
                    continue;
                }
                conjuntoSimbolosFollow.addAll(this.Follow(ladoIzquierdo));
                continue;
            }
            conjuntoAuxiliarFirst = this.First(regla.subList(indiceSimboloFollow + 1, regla.size()));
            // Calcular Follow del lado izquierdo si el First tiene epsilon            
            if (conjuntoAuxiliarFirst.contains(epsilon)) {
                conjuntoAuxiliarFirst.remove(epsilon);
                if (simboloGramaticaFollow == ladoIzquierdo) {
                    continue;
                }
                conjuntoSimbolosFollow.addAll(this.Follow(ladoIzquierdo));
            }
            conjuntoSimbolosFollow.addAll(conjuntoAuxiliarFirst);
        }
        return conjuntoSimbolosFollow;
    }
}
