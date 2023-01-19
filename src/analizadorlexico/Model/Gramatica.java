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

    public boolean crearGramatica(String cadenaGramatica) {
        this.cadenaGramatica = cadenaGramatica;
        simbolosTerminales = new HashSet<>();
        simbolosNoTerminales = new HashSet<>();
        // Crear el analizador lexico de gramáticas        
        AFN afnGramatica = new AFN();
        AFD afdGramatica = afnGramatica.crearAFNGramaticaDeGramaticas().toAFD();
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
        HashSet<Simbolo> conjuntoAuxiliarFirst = new HashSet<>();
        int indiceSimboloFollow; // TODO borrar este comentario        
        if (simboloGramaticaFollow.equals(simboloGramaticaInicial)) {
            conjuntoSimbolosFollow.add($);
        }
        // Buscar simboloFollow en los lados derechos        
        for (List<ClaseNodo> regla : arregloReglas) {
            // Ver si el simboloFollow existe en esa regla y obtener su índice            
            int indiceSimboloFollow = regla.indexOf(simboloGramaticaFollow);
            // Si el simboloFollow no aparece en el lado derecho de la regla, revisar la siguiente            
            if (indiceSimboloFollow == -1 || indiceSimboloFollow == 0) {
                continue;
            }
            ClaseNodo ladoIzquierdo = regla.get(0);
            // Calcular Follow del lado izquierdo si simboloFollow está en la última posición            
            if (indiceSimboloFollow + 1 == regla.size()) {
                // Verificar que simboloFollow no sea igual al lado izquierdo para evitar quedar ciclado                
                if (simboloGramaticaFollow == ladoIzquierdo) {
                    continue;
                }
                conjuntoSimbolosFollow.addAll(this.Follow(ladoIzquierdo));
                continue;
            }
            // Calcular el First de lo que sucede a simboloFollow            
            HashSet<ClaseNodo> conjuntoAuxiliarFirst = this.First(regla.subList(indiceSimboloFollow + 1, regla.size()));
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
