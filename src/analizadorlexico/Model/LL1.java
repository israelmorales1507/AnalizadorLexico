/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Model;

import analizadorlexico.Controller.AnalizadorLexico;
import java.util.ArrayList;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import org.javatuples.Triplet;

/**
 *
 * @author Israel Morales
 */
public class LL1 {

    private Gramatica gramatica;
    private Table<String, String, CeldaTablaLL1> tablaLL1;
    private ArrayList<Triplet<ArrayList<String>, String, String>> registroLL1;

    public LL1(Gramatica gramatica) {
        this.gramatica = gramatica;
        registroLL1 = new ArrayList<>();
    }

    public Gramatica getGramatica() {
        return gramatica;
    }

    public void setGramatica(Gramatica gramatica) {
        this.gramatica = gramatica;
    }

    public Table<String, String, CeldaTablaLL1> getTablaLL1() {
        return tablaLL1;
    }

    public void setTablaLL1(Table<String, String, CeldaTablaLL1> tablaLL1) {
        this.tablaLL1 = tablaLL1;
    }

    public ArrayList<Triplet<ArrayList<String>, String, String>> getRegistroLL1() {
        return registroLL1;
    }

    public void setRegistroLL1(ArrayList<Triplet<ArrayList<String>, String, String>> registroLL1) {
        this.registroLL1 = registroLL1;
    }

    
    public void generarTablaLL1() {
        tablaLL1 = HashBasedTable.create();
        for (List<ClaseNodo> regla : gramatica.getArregloReglas()) {
            ClaseNodo fila = regla.get(0);
            HashSet<ClaseNodo> columnas;
            List<ClaseNodo> ladoDerecho = regla.subList(1, regla.size());
            CeldaTablaLL1 celdaLL1 = new CeldaTablaLL1(gramatica.getArregloReglas().indexOf(regla), ladoDerecho);
            columnas = gramatica.First(ladoDerecho);
            if (columnas.contains(Gramatica.epsilon)) {
                columnas.remove(Gramatica.epsilon);
                columnas.addAll(gramatica.Follow(fila));
            }
            for (ClaseNodo columna : columnas) {
                tablaLL1.put(fila.getSimbolo(), columna.getSimbolo(), celdaLL1);
            }
        }
        // Revertir el orden de la lista para ingresarla a la pila
        HashSet<CeldaTablaLL1> conjuntoCeldas = new HashSet<>();
        for (CeldaTablaLL1 celdaLL1 : tablaLL1.values()) {
            if (conjuntoCeldas.contains(celdaLL1)) {
                continue;
            }
            Collections.reverse(celdaLL1.getListaSimbolosGramatica());
            conjuntoCeldas.add(celdaLL1);
        }
    }

    public void imprimirTablaLL1() {
        String separador = "\t\t", texto = "\n" + separador;
        for (String columna : this.tablaLL1.columnKeySet()) {
            if (columna.length() > 3) {
                texto = texto.concat(columna.substring(0, 3) + "\t\t");
            } else {
                texto = texto.concat(columna + separador);
            }
        }
        texto = texto.concat("\n");
        for (String fila : this.tablaLL1.rowKeySet()) {
            texto = texto.concat(fila + separador);
            for (String columna : this.tablaLL1.columnKeySet()) {
                //Obtener valor de la tabla y convertir en String
                String celda = "";
                if (this.tablaLL1.get(fila, columna) != null) {
                    celda = this.tablaLL1.get(fila, columna).getIndiceRegla().toString();
                }
                texto = texto.concat(celda + separador);
            }
            texto = texto.concat("\n");
        }
        System.out.println(texto);
    }

    public boolean analizarCadena(String cadena) {
        AnalizadorLexico analizadorLexico = new AnalizadorLexico(cadena, gramatica.getAfdGramatica());
        Stack<ClaseNodo> pilaLL1 = new Stack<>();
        pilaLL1.push(gramatica.getSimboloGramaticaInicial());
        int columnaToken = analizadorLexico.yylex();
        while (true) {
            ArrayList<String> elementosPila = new ArrayList<>();
            for (ClaseNodo simboloGramaticaPila : pilaLL1) {
                elementosPila.add(simboloGramaticaPila.getSimbolo());
            }
            ClaseNodo columnaClaseNodo = Gramatica.$;
            if (columnaToken != 0) {
                for (ClaseNodo simboloGramatica : gramatica.getConjuntoSimbolos().values()) {
                    if (simboloGramatica.getToken() == columnaToken) {
                        columnaClaseNodo = simboloGramatica;
                        break;
                    }
                }
            }
            // La cadena es aceptada si la pila está vacía y el token es el del fin de la cadena
            if (pilaLL1.isEmpty()) {
                if (columnaToken == CaracteresEspeciales.FIN) {
                    registroLL1.add(new Triplet<>(elementosPila, cadena.substring(analizadorLexico.getInitLexema()), "Aceptar cadena"));
                    return true;
                }
                registroLL1.add(new Triplet<>(elementosPila, cadena.substring(analizadorLexico.getInitLexema()), "Cadena no aceptada"));
                return false;
            }
            ClaseNodo filaClaseNodo = pilaLL1.pop();
            // Los simbolos son iguales
            if (filaClaseNodo == columnaClaseNodo) {
                registroLL1.add(new Triplet<>(elementosPila, cadena.substring(analizadorLexico.getInitLexema()), "POP"));
                columnaToken = analizadorLexico.yylex();
                continue;
            }
            if (gramatica.getSimbolosNoTerminales().contains(filaClaseNodo)) {
                // Si no existe la celda, es un error
                if (!tablaLL1.contains(filaClaseNodo.getSimbolo(), columnaClaseNodo.getSimbolo())) {
                    registroLL1.add(new Triplet<>(elementosPila, cadena.substring(analizadorLexico.getInitLexema()), "Cadena no aceptada"));
                    return false;
                }
                // Recuperar contenido de la celda
                CeldaTablaLL1 celda = tablaLL1.get(filaClaseNodo.getSimbolo(), columnaClaseNodo.getSimbolo());
                String regla = "";
                for (ClaseNodo simboloGramatica : gramatica.getArregloReglas().get(celda.getIndiceRegla())) {
                    if (regla.isEmpty()) {
                        regla = regla.concat(simboloGramatica.getSimbolo() + " -> ");
                    } else {
                        regla = regla.concat(simboloGramatica.getSimbolo() + " ");
                    }
                }
                // Ingresar el contenido de la celda en la pila
                for (ClaseNodo simboloGramaticaRegla : celda.getListaSimbolosGramatica()) {
                    if (!simboloGramaticaRegla.equals(Gramatica.epsilon)) // TODO Revisar este epsilon
                    {
                        pilaLL1.push(simboloGramaticaRegla);
                    }
                }
                registroLL1.add(new Triplet<>(elementosPila, cadena.substring(analizadorLexico.getInitLexema()), regla));
                continue;
            }
            return false;
        }
    }
}
