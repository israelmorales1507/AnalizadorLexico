/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Models.Gramatica;

import analizadorlexico.Models.AFD.AFD;
import analizadorlexico.Models.AFN.AFN;
import analizadorlexico.Models.analizador.sintactico.GramaticaDeGramaticas;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

/**
 *
 * @author Israel Morales
 */
public class Gramatica {

    private String cadenaGramatica;
    private SimboloGramatica simboloGramaticaInicial;
    private HashSet<SimboloGramatica> simbolosTerminales;
    private HashSet<SimboloGramatica> simbolosNoTerminales;
    private ArrayList<List<SimboloGramatica>> arregloReglas;
    private HashMap<String, SimboloGramatica> conjuntoSimbolos;
    private AFD afdGramatica;
    public static SimboloGramatica epsilon = new SimboloGramatica("epsilon");
    public static SimboloGramatica $ = new SimboloGramatica("$");

    public String getCadenaGramatica() {
        return cadenaGramatica;
    }

    public void setCadenaGramatica(String cadenaGramatica) {
        this.cadenaGramatica = cadenaGramatica;
    }

    public SimboloGramatica getSimboloGramaticaInicial() {
        return simboloGramaticaInicial;
    }

    public void setSimboloGramaticaInicial(SimboloGramatica simboloGramaticaInicial) {
        this.simboloGramaticaInicial = simboloGramaticaInicial;
    }

    public HashSet<SimboloGramatica> getSimbolosTerminales() {
        return simbolosTerminales;
    }

    public void setSimbolosTerminales(HashSet<SimboloGramatica> simbolosTerminales) {
        this.simbolosTerminales = simbolosTerminales;
    }

    public HashSet<SimboloGramatica> getSimbolosNoTerminales() {
        return simbolosNoTerminales;
    }

    public void setSimbolosNoTerminales(HashSet<SimboloGramatica> simbolosNoTerminales) {
        this.simbolosNoTerminales = simbolosNoTerminales;
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

    public AFD getAfdGramatica() {
        return afdGramatica;
    }

    public void setAfdGramatica(AFD afdGramatica) {
        this.afdGramatica = afdGramatica;
    }

    public static SimboloGramatica getEpsilon() {
        return epsilon;
    }

    public static void setEpsilon(SimboloGramatica epsilon) {
        Gramatica.epsilon = epsilon;
    }

    public static SimboloGramatica get$() {
        return $;
    }

    public static void set$(SimboloGramatica $) {
        Gramatica.$ = $;
    }

    public Gramatica() {
    }

    public Gramatica(String cadenaGramatica, SimboloGramatica simboloGramaticaInicial, HashSet<SimboloGramatica> simbolosTerminales, HashSet<SimboloGramatica> simbolosNoTerminales, ArrayList<List<SimboloGramatica>> arregloReglas, HashMap<String, SimboloGramatica> conjuntoSimbolos, AFD afdGramatica) {
        this.cadenaGramatica = cadenaGramatica;
        this.simboloGramaticaInicial = simboloGramaticaInicial;
        this.simbolosTerminales = simbolosTerminales;
        this.simbolosNoTerminales = simbolosNoTerminales;
        this.arregloReglas = arregloReglas;
        this.conjuntoSimbolos = conjuntoSimbolos;
        this.afdGramatica = afdGramatica;
    }

    public boolean crearGramatica(String cadenaGramatica) {
        this.cadenaGramatica = cadenaGramatica;
        simbolosTerminales = new HashSet<>();
        simbolosNoTerminales = new HashSet<>();

        // Crear el analizador lexico de gramáticas
        AFN afnGramatica = new AFN();
        AFD afdGramatica = afnGramatica.crearAFNGramaticaDeGramaticas().toAFD();
        
        GramaticaDeGramaticas descensoRecursivoGramatica = new GramaticaDeGramaticas(cadenaGramatica, afdGramatica);

        // Validar la gramática mediante descenso recursivo
        if (descensoRecursivoGramatica.analizarGramatica()) {
            arregloReglas = descensoRecursivoGramatica.getArregloReglas();
            conjuntoSimbolos = descensoRecursivoGramatica.getConjuntoSimbolos();
            simboloGramaticaInicial = arregloReglas.get(0).get(0);
            // Llenar el conjunto de los no terminales con el primer símbolo de cada lista
            for (List<SimboloGramatica> listaReglas : arregloReglas) {
                simbolosNoTerminales.add(listaReglas.get(0));
            }
            // Llenar el conjunto de los terminales con los símbolos que no están en el otro conjunto
            for (SimboloGramatica simboloGramaticaTerminal : conjuntoSimbolos.values()) {
                // Evitar que epsilon entre en el conjunto de los terminales
                if (simboloGramaticaTerminal.getSimbolo().equals(epsilon.getSimbolo())) // TODO revisar este epsilon
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

    public HashSet<SimboloGramatica> First(List<SimboloGramatica> listaSimbolosFirst) {
        HashSet<SimboloGramatica> conjuntoResultadoFirst = new HashSet<>();
        SimboloGramatica simboloGramatica = listaSimbolosFirst.get(0);

        if (simboloGramatica.isEsTerminal() || simboloGramatica.equals(epsilon)) { // TODO revisar este epsilon
            conjuntoResultadoFirst.add(simboloGramatica);
            return conjuntoResultadoFirst;
        }
        for (List<SimboloGramatica> regla : arregloReglas) {
            // Ver si el símbolo es igual al lado izquierdo de la regla
            if (regla.get(0).equals(simboloGramatica)) { // TODO Este compara todos los atributos del objeto, para verificar que los simbolos tengan el mismo nombre regla.get(0).getSimbolo() == simbolo.getSimbolo()
                // Calcular el First de los lados derechos de la regla
                List<SimboloGramatica> ladosDerechos = new ArrayList<>(regla.subList(1, regla.size()));
                HashSet<SimboloGramatica> conjuntoAuxiliar = this.First(ladosDerechos);

                // Si el First de los lados derechos no tiene a epsilon o es el último elemento, se termina el cálculo de este First
                if (!conjuntoAuxiliar.contains(epsilon) || listaSimbolosFirst.size() == 1) { // TODO revisar este epsilon
                    conjuntoResultadoFirst.addAll(conjuntoAuxiliar);
                    continue;
                }

                // Quitar epsilon del resultado y calcular el first de lo que está a la derecha del símbolo
                if (conjuntoResultadoFirst.contains(epsilon)) { // TODO revisar este epsilon
                    conjuntoResultadoFirst.remove(epsilon);
                    conjuntoResultadoFirst.addAll(this.First(listaSimbolosFirst.subList(1, listaSimbolosFirst.size())));
                }
            }
        }

        return conjuntoResultadoFirst;
    }

    public HashSet<SimboloGramatica> Follow(SimboloGramatica simboloGramaticaFollow) {
        HashSet<SimboloGramatica> conjuntoSimbolosFollow = new HashSet<>();
//        HashSet<Simbolo> conjuntoAuxiliarFirst = new HashSet<>();
//        int indiceSimboloFollow; // TODO borrar este comentario

        // Agregar fin de cadena si se está calculando el Follow del símbolo inicial
        if (simboloGramaticaFollow.equals(simboloGramaticaInicial)) {
            conjuntoSimbolosFollow.add($);
        }

        // Buscar simboloFollow en los lados derechos
        for (List<SimboloGramatica> regla : arregloReglas) {
            // Ver si el simboloFollow existe en esa regla y obtener su índice

            int[] indices = IntStream.range(0, regla.size())
                    .filter(i -> regla.get(i).equals(simboloGramaticaFollow))
                    .toArray();
            for (int indiceSimboloFollow : indices) {// Si el simboloFollow no aparece en el lado derecho de la regla, revisar la siguiente
                if (indiceSimboloFollow == -1 || indiceSimboloFollow == 0) {
                    continue;
                }

                SimboloGramatica ladoIzquierdo = regla.get(0);
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
                HashSet<SimboloGramatica> conjuntoAuxiliarFirst = this.First(regla.subList(indiceSimboloFollow + 1, regla.size()));

                // Calcular Follow del lado izquierdo si el First tiene epsilon
                if (conjuntoAuxiliarFirst.contains(epsilon)) { // TODO revisar este epsilon
                    conjuntoAuxiliarFirst.remove(epsilon);
                    if (simboloGramaticaFollow == ladoIzquierdo) {
                        continue;
                    }
                    conjuntoSimbolosFollow.addAll(this.Follow(ladoIzquierdo));
                }
                conjuntoSimbolosFollow.addAll(conjuntoAuxiliarFirst);
            }
        }

        return conjuntoSimbolosFollow;
    }

    public void crearAFD() { // Al invocar este método ya deben estar asignadas las ER y los tokens
        AFN afn = new AFN();
        HashSet<AFN> conjuntoAFN = new HashSet<>();
        for (SimboloGramatica simboloGramaticaTerminal : simbolosTerminales) {
//            System.out.println(simboloGramaticaTerminal);
            AFN afnTerminal = new AFN(simboloGramaticaTerminal.getExpresionRegular());
            afnTerminal.cambiarToken(simboloGramaticaTerminal.getToken());
            conjuntoAFN.add(afnTerminal);
        }
        AFN.conjuntoAFNsUnionLexico.addAll(conjuntoAFN);
        afdGramatica = afn.unionLexicoAFNs().toAFD();
    }

    public void imprimir() {
        System.out.println("Gramatica:");
        System.out.println(cadenaGramatica + "\n");
        System.out.println("Simbolo inicial:");
        System.out.println(simboloGramaticaInicial.getSimbolo() + "\n");
        System.out.println("Conjunto de reglas:");
        for (List<SimboloGramatica> listaReglas : arregloReglas) {
            for (SimboloGramatica simboloGramatica : listaReglas) {
                System.out.print(simboloGramatica.getSimbolo() + "\t");
            }
            System.out.println();
        }
        System.out.println("\nSimbolos no terminales:");
        for (SimboloGramatica simboloGramatica : simbolosNoTerminales) {
            System.out.print(simboloGramatica.getSimbolo() + " ");
        }
        System.out.println("\n\nSimbolos terminales:");
        for (SimboloGramatica simboloGramatica : simbolosTerminales) {
            System.out.print(simboloGramatica.getSimbolo() + " ");
        }
    }

    public void crearGramaticaGramaticas() {
        this.crearGramatica(""
                + "Gramatica -> ConjuntoReglas;\n" +
"                ConjuntoReglas -> ListaReglas ConjuntoReglasP;\n" +
"                ConjuntoReglasP -> ListaReglas ConjuntoReglasP | epsilon;\n" +
"                ListaReglas -> Simbolo Flecha ListaLadosDerechos PC;\n" +
"                ListaLadosDerechos -> LadoDerecho ListaLadosDerechosP;\n" +
"                ListaLadosDerechosP -> OR LadoDerecho ListaLadosDerechosP | epsilon;\n" +
"                LadoDerecho -> Simbolo LadoDerechoP;\n" +
"                LadoDerechoP -> Simbolo LadoDerechoP | epsilon;\n" +
"                ListaReglas -> ESPACIO Simbolo Flecha ListaLadosDerechos PC;");

        this.getConjuntoSimbolos().get("Simbolo").setExpresionRegular("(([!-,]|.|[0-:]|[<-{]|[}-¿])|(\\\\&(\\-|\\||;| |\t|\n|/)))+");
        this.getConjuntoSimbolos().get("Flecha").setExpresionRegular("\\-&>");
        this.getConjuntoSimbolos().get("OR").setExpresionRegular("\\|");
        this.getConjuntoSimbolos().get("PC").setExpresionRegular(";");
        this.getConjuntoSimbolos().get("ESPACIO").setExpresionRegular("(( |\n|\t)+)|(/&\\*&([ -¿])*&\\*&/)");

        this.getConjuntoSimbolos().get("Simbolo").setToken(10);
        this.getConjuntoSimbolos().get("Flecha").setToken(20);
        this.getConjuntoSimbolos().get("OR").setToken(30);
        this.getConjuntoSimbolos().get("PC").setToken(40);
        this.getConjuntoSimbolos().get("ESPACIO").setToken(-4);

        this.crearAFD();
    }
}
