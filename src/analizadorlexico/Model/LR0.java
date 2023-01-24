/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Model;

import analizadorlexico.Controller.AnalizadorLexico;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import org.javatuples.Triplet;

/**
 *
 * @author Israel Morales
 */
public class LR0 {
    
    private Gramatica gramatica;
    private Table<String, String, CeldaTablaLR0> tablaLR0;
    private HashMap<EstadoLR0, Integer> conjuntosS;
    private ArrayList<Triplet<ArrayList<String>, String, String>> registroLR0;
    public LR0(Gramatica gramatica) {
        this.gramatica = gramatica;
        registroLR0 = new ArrayList<>();
    }
    public void generarTablaLR0() {
        tablaLR0 = HashBasedTable.create();
        conjuntosS = new HashMap<>();
        int id = 0;
        // Generar primer estado        
        ItemLR0 itemInicial = new ItemLR0(0, 1);
        EstadoLR0 estadoAuxiliar = new EstadoLR0(itemInicial);
        EstadoLR0 estadoS0 = cerradura(estadoAuxiliar);
        if(crearEstadoLR0(conjuntosS,estadoS0,id)) id++;
        Queue<EstadoLR0> estadosPorAnalizar = new LinkedList<>();
        estadosPorAnalizar.add(estadoS0);
        List<EstadoLR0> listaEstadosAnalizados = new ArrayList<>();
        // Analizar estados        
        while (!estadosPorAnalizar.isEmpty()) {
            EstadoLR0 estadoActual = estadosPorAnalizar.remove();
            HashSet<ClaseNodo> listaSimbolosIrA = new HashSet<>();
            for (ItemLR0 item : estadoActual.getConjuntoItems())
                if (item.getPosicionPunto() < gramatica.getArregloReglas().get(item.getIndiceRegla()).size())
                    listaSimbolosIrA.add(gramatica.getArregloReglas().get(item.getIndiceRegla()).get(item.getPosicionPunto()));
            for (ClaseNodo simboloIrA : listaSimbolosIrA) {
                EstadoLR0 estadoSn = irA(estadoActual, simboloIrA);
                if ((!estadosPorAnalizar.contains(estadoSn)) && (!listaEstadosAnalizados.contains(estadoSn))) {
                    estadosPorAnalizar.add(estadoSn);
                    if(crearEstadoLR0(conjuntosS,estadoSn,id)) id++;
                }
                String accion = simboloIrA.isEsTerminal() ? "d" : "";
                tablaLR0.put(String.valueOf(conjuntosS.get(estadoActual)),simboloIrA.getSimbolo(),new CeldaTablaLR0(conjuntosS.get(estadoSn), accion));
            }
            if (!listaEstadosAnalizados.contains(estadoActual)) {
                listaEstadosAnalizados.add(estadoActual);
            }
        }
        for (EstadoLR0 estadoActual : listaEstadosAnalizados){
            for (ItemLR0 itemActual : estadoActual.getConjuntoItems()) {
                if (itemActual.getPosicionPunto() == gramatica.getArregloReglas().get(itemActual.getIndiceRegla()).size()) {
                    HashSet<ClaseNodo> follow = gramatica.Follow(gramatica.getArregloReglas().get(itemActual.getIndiceRegla()).get(0));
                    for (ClaseNodo simboloFollow : follow) {
                        String accion = itemActual.getIndiceRegla() == 0 ? "Aceptar" : "r";
                        tablaLR0.put(String.valueOf(conjuntosS.get(estadoActual)), simboloFollow.getSimbolo(), new CeldaTablaLR0(itemActual.getIndiceRegla(), accion));
                    }
                }
            }
        }
    }
    public EstadoLR0 cerradura(EstadoLR0 conjuntoCerradura) {
        HashSet<ItemLR0> resultadoCerradura = new HashSet<>();
        Stack<ItemLR0> pilaEstados = new Stack<>();
        for (ItemLR0 itemCerradura : conjuntoCerradura.getConjuntoItems())
            pilaEstados.push(itemCerradura);
        while (!pilaEstados.isEmpty()) {
            ItemLR0 itemActual = pilaEstados.pop();
            if (resultadoCerradura.contains(itemActual))
                continue;
            resultadoCerradura.add(itemActual);
            if (itemActual.getPosicionPunto() >= gramatica.getArregloReglas().get(itemActual.getIndiceRegla()).size())
                continue;
            // Se obtiene el simbolo que está después del punto en el itemActual            
            ClaseNodo simboloPunto = gramatica.getArregloReglas().get(itemActual.getIndiceRegla()).get(itemActual.getPosicionPunto());
            if (simboloPunto.isEsTerminal())
                continue;
            for (List<ClaseNodo> regla : gramatica.getArregloReglas()) {
                Integer indiceRegla = gramatica.getArregloReglas().indexOf(regla);
                if (simboloPunto.equals(regla.get(0)))
                    pilaEstados.push(new ItemLR0(indiceRegla, 1));
            }
        }
        return new EstadoLR0(resultadoCerradura);
    }
    public boolean analizarCadena(String cadena){
        AnalizadorLexico analizadorLexico = new AnalizadorLexico(cadena,gramatica.getAfdGramatica());
        Stack<String> pilaLR0 = new Stack<>();
        Integer filaEstadoLR0 = 0;
        pilaLR0.push(filaEstadoLR0.toString());
        int columnaToken = analizadorLexico.yylex();
        while (true){
            ArrayList<String> elementosPila = new ArrayList<>();
            for (String elemento : pilaLR0)
                elementosPila.add(elemento);
            ClaseNodo columnaClaseNodo = Gramatica.$;
            if (columnaToken != 0) {
                for (ClaseNodo simboloGramatica : gramatica.getConjuntoSimbolos().values())
                    if (simboloGramatica.getToken() == columnaToken) {
                        columnaClaseNodo = simboloGramatica;
                        break;
                    }
            }
            if (!tablaLR0.contains(filaEstadoLR0.toString(),columnaClaseNodo.getSimbolo())) {
                registroLR0.add(new Triplet<>(elementosPila, cadena.substring(analizadorLexico.getInitLexema()), "Cadena no aceptada"));
                return false;
            }
            CeldaTablaLR0 celda = tablaLR0.get(filaEstadoLR0.toString(),columnaClaseNodo.getSimbolo());
            if (celda.getAccion().equals("Aceptar")) {
                String accion = celda.getAccion();
                registroLR0.add(new Triplet<>(elementosPila,cadena.substring(analizadorLexico.getInitLexema()),accion));
                return true;
            }
            if (celda.getAccion().equals("d")){
                pilaLR0.push(columnaClaseNodo.getSimbolo());
                pilaLR0.push(celda.getIndiceRegla().toString());
                filaEstadoLR0 = celda.getIndiceRegla();
                String accion = celda.getAccion() + celda.getIndiceRegla();
                registroLR0.add(new Triplet<>(elementosPila,cadena.substring(analizadorLexico.getInitLexema()),accion));
                columnaToken = analizadorLexico.yylex();
                continue;
            }
            if (celda.getAccion().equals("r")){
                int cantidadPop = (gramatica.getArregloReglas().get(celda.getIndiceRegla()).size() - 1) * 2;
                for (int i=0;i<cantidadPop;i++)
                    pilaLR0.pop();
                pilaLR0.push(gramatica.getArregloReglas().get(celda.getIndiceRegla()).get(0).getSimbolo());
                String columnaCadena = pilaLR0.pop();
                String filaEstado = pilaLR0.peek();
                filaEstadoLR0 = tablaLR0.get(filaEstado,columnaCadena).getIndiceRegla();
                pilaLR0.push(columnaCadena);
                pilaLR0.push(filaEstadoLR0.toString());
                String regla = "";
                String accion = celda.getAccion() + celda.getIndiceRegla() + ", ";
                for (ClaseNodo simboloGramatica : gramatica.getArregloReglas().get(celda.getIndiceRegla()))
                    if (regla.isEmpty())
                        regla = regla.concat(simboloGramatica.getSimbolo() + " -> ");
                    else                        regla = regla.concat(simboloGramatica.getSimbolo() + " ");
                accion = accion.concat(regla);
                registroLR0.add(new Triplet<>(elementosPila,cadena.substring(analizadorLexico.getInitLexema()),accion));
            }
        }
    }
    public EstadoLR0 mover(EstadoLR0 conjuntoItemsMover, ClaseNodo simbolo) {
        HashSet<ItemLR0> resultadoMover = new HashSet<>();
        for (ItemLR0 itemActual : conjuntoItemsMover.getConjuntoItems()) {
            if (itemActual.getPosicionPunto() >= gramatica.getArregloReglas().get(itemActual.getIndiceRegla()).size())
                continue;
            if (gramatica.getArregloReglas().get(itemActual.getIndiceRegla()).get(itemActual.getPosicionPunto()).equals(simbolo)) {
                ItemLR0 itemMover = new ItemLR0(itemActual.getIndiceRegla(), itemActual.getPosicionPunto() + 1);
                resultadoMover.add(itemMover);
            }
        }
        return new EstadoLR0(resultadoMover);
    }
    public EstadoLR0 irA(EstadoLR0 conjuntoItemsIrA, ClaseNodo simbolo) {
        return cerradura(mover(conjuntoItemsIrA, simbolo));
    }
    public void imprimirTablaLR0(){
        String separador = "\t\t", texto = "\n" + separador;
        for(String columna : this.tablaLR0.columnKeySet())
            if (columna.length() > 3)
                texto = texto.concat(columna.substring(0,3) + "\t\t");
            else                texto = texto.concat(columna + separador);
        texto = texto.concat("\n");
        for(String fila : this.tablaLR0.rowKeySet()){
            texto = texto.concat(fila + separador);
            for(String columna : this.tablaLR0.columnKeySet()){
                //Obtener valor de la tabla y convertir en String                
                String celda = "";
                if (this.tablaLR0.get(fila, columna) != null) {
                    CeldaTablaLR0 celdaTablaLR0 = this.tablaLR0.get(fila, columna);
                    if(celdaTablaLR0.getAccion().equalsIgnoreCase("Aceptar")){
                        celda="Aceptar";
                    }else {
                        celda = String.format("%s%s", this.tablaLR0.get(fila, columna).getAccion(), this.tablaLR0.get(fila, columna).getIndiceRegla().toString());
                    }
                }
                texto = texto.concat(celda + separador);
            }
            texto = texto.concat("\n");
        }
        System.out.println(texto);
    }
    
    public boolean crearEstadoLR0(HashMap<EstadoLR0, Integer> conjuntoEstados,EstadoLR0 estado, int id){
        if (!conjuntoEstados.containsKey(estado)) {
            conjuntoEstados.put(estado, id);
            return true;
        }
        return false;
    }
}
