/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Models.AFN;

import analizadorlexico.Models.AFD.AFD;
import analizadorlexico.Models.analizador.sintactico.ExpresionRegularToAFN;
import analizadorlexico.Models.enums.ERtoAFNTokens;
import analizadorlexico.Models.enums.GramaticaDeGramaticasTokens;
import static analizadorlexico.Models.enums.SimbolosEspeciales.EPSILON;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Israel Morales
 */
public class AFN {
    public static HashSet<AFN> conjuntoAFNsUnionLexico = new HashSet<>();
    public static HashSet<AFN> conjuntoAFNs = new HashSet<>();
    private int idAFN;
    private Estado estadoInicial;
    private HashSet<Estado> estados;
    private HashSet<Estado> estadosAceptacion;
    private HashSet<Character> Alfabeto;

    public static HashSet<AFN> getConjuntoAFNs() {
        return conjuntoAFNs;
    }

    public static void setConjuntoAFNs(HashSet<AFN> conjuntoAFNs) {
        AFN.conjuntoAFNs = conjuntoAFNs;
    }
    
    public static HashSet<AFN> getConjuntoAFNsUnionLexico() {
        return conjuntoAFNsUnionLexico;
    }

    public static void setConjuntoAFNsUnionLexico(HashSet<AFN> conjuntoAFNsUnionLexico) {
        AFN.conjuntoAFNsUnionLexico = conjuntoAFNsUnionLexico;
    }

    public int getIdAFN() {
        return idAFN;
    }

    public void setIdAFN(int idAFN) {
        this.idAFN = idAFN;
    }

    public Estado getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(Estado estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public HashSet<Estado> getEstados() {
        return estados;
    }

    public void setEstados(HashSet<Estado> estados) {
        this.estados = estados;
    }

    public HashSet<Estado> getEstadosAceptacion() {
        return estadosAceptacion;
    }

    public void setEstadosAceptacion(HashSet<Estado> estadosAceptacion) {
        this.estadosAceptacion = estadosAceptacion;
    }

    public HashSet<Character> getAlfabeto() {
        return Alfabeto;
    }

    public void setAlfabeto(HashSet<Character> Alfabeto) {
        this.Alfabeto = Alfabeto;
    }
    
    

    public AFN(int idAFN, Estado estadoInicial, HashSet<Estado> estados, HashSet<Estado> estadosAceptacion, HashSet<Character> Alfabeto) {
        this.idAFN = idAFN;
        this.estadoInicial = estadoInicial;
        this.estados = estados;
        this.estadosAceptacion = estadosAceptacion;
        this.Alfabeto = Alfabeto;
    }
    
    

    public AFN() {
        this.estados = new HashSet<>();
        this.estadosAceptacion = new HashSet<>();
        this.Alfabeto = new HashSet<>();
    }

    public AFN(String expresionRegular){
        AFN afNaER = new AFN();
        afNaER.crearAFNaER();
        AFD afdAER = afNaER.toAFD();

        ExpresionRegularToAFN ERtoAFN = new ExpresionRegularToAFN(expresionRegular,afdAER);
        ERtoAFN.iniciarConversionExpresionRegularToAFN();
        AFN afn = ERtoAFN.getAfnResultante();

        this.estadoInicial = afn.getEstadoInicial();
        this.estados = afn.getEstados();
        this.estadosAceptacion = afn.getEstadosAceptacion();
        this.Alfabeto = afn.getAlfabeto();

    }

    public AFN CrearAFNBasico(char simbolo) {
        Estado estadoInicial = new Estado();
        Estado estadoAceptacion = new Estado();

        Transicion transicionEstadoInicialToAceptacion = new Transicion(simbolo, estadoAceptacion);

        estadoInicial.getTransiciones().add(transicionEstadoInicialToAceptacion);
        estadoAceptacion.setEsAceptacion(true);

        Alfabeto.add(simbolo);
        this.estadoInicial = estadoInicial;
        estados.add(estadoInicial);
        estados.add(estadoAceptacion);
        estadosAceptacion.add(estadoAceptacion);

        return this;
    }

    public AFN CrearAFNBasico(char simboloInferior, char simboloSuperior) {

        if(simboloInferior>simboloSuperior)
            throw new IllegalArgumentException("Simbolo Inferior no puede ser mas grande que el superior");

        Estado estadoInicial = new Estado();
        Estado estadoAceptacion = new Estado();

        Transicion transicionEstadoInicialToAceptacion = new Transicion(simboloInferior, simboloSuperior, estadoAceptacion);
        estadoInicial.getTransiciones().add(transicionEstadoInicialToAceptacion);
        estadoAceptacion.setEsAceptacion(true);

        for (char simboloActual = simboloInferior; simboloActual <= simboloSuperior; simboloActual++)
            Alfabeto.add(simboloActual);

        this.estadoInicial = estadoInicial;
        estados.add(estadoInicial);
        estados.add(estadoAceptacion);
        estadosAceptacion.add(estadoAceptacion);

        return this;
    }

    public AFN CrearAFNBasico(char simboloInferior, char simboloSuperior, List<Character> excepciones) {

        if(simboloInferior>simboloSuperior)
            return null;

        Estado estadoInicial = new Estado();
        Estado estadoAceptacion = new Estado();

        estadoAceptacion.setEsAceptacion(true);

        for (char simboloActual = simboloInferior; simboloActual <= simboloSuperior; simboloActual++)
            if (!excepciones.contains(simboloActual)) {
                Alfabeto.add(simboloActual);
                Transicion transicionEstadoInicialToAceptacion = new Transicion(simboloActual, estadoAceptacion);
                estadoInicial.getTransiciones().add(transicionEstadoInicialToAceptacion);
            }

        this.estadoInicial = estadoInicial;
        estados.add(estadoInicial);
        estados.add(estadoAceptacion);
        estadosAceptacion.add(estadoAceptacion);

        return this;
    }

    public AFN CrearAFNBasico(List<Character> listaSimbolos) {

        if(listaSimbolos.isEmpty())
            return null;

        Estado estadoInicial = new Estado();
        Estado estadoAceptacion = new Estado();

        for(Character simbolo : listaSimbolos){
            Transicion transicionEstadoInicialToAceptacion = new Transicion(simbolo, estadoAceptacion);
            estadoInicial.getTransiciones().add(transicionEstadoInicialToAceptacion);
            Alfabeto.add(simbolo);
        }

        estadoAceptacion.setEsAceptacion(true);

        this.estadoInicial = estadoInicial;
        estados.add(estadoInicial);
        estados.add(estadoAceptacion);
        estadosAceptacion.add(estadoAceptacion);

        return this;
    }

    public AFN opcional() {
        Estado estadoInicial = new Estado();
        Estado nuevoEstadoAceptacion = new Estado();

        estadoInicial.getTransiciones().add(new Transicion(EPSILON.getSimbolo(), this.estadoInicial));
        estadoInicial.getTransiciones().add(new Transicion(EPSILON.getSimbolo(), nuevoEstadoAceptacion));

        for (Estado estadoAceptacion : this.estadosAceptacion) {
            estadoAceptacion.getTransiciones().add(new Transicion(EPSILON.getSimbolo(), nuevoEstadoAceptacion));
            estadoAceptacion.setEsAceptacion(false);
        }

        this.estadoInicial = estadoInicial;

        this.estados.add(estadoInicial);
        this.estados.add(nuevoEstadoAceptacion);

        this.estadosAceptacion.clear(); //quitamos los antiguos estados de aceptacion 
        this.getEstadosAceptacion().add(nuevoEstadoAceptacion); //agregamos nuevo estado de aceptacion

        return this;
    }

    public AFN afnUnionOR(AFN afn2) {

        afn2 = afn2.duplicarAFN();

        Estado nuevoEstadoInicial = new Estado();
        Estado nuevoEstadoAceptacion = new Estado();

        Transicion transicionEstadoInicialToAceptacion = new Transicion(EPSILON.getSimbolo(), this.estadoInicial);
        Transicion transicionEstadoInicialToAFN2 = new Transicion(EPSILON.getSimbolo(), afn2.estadoInicial);

        nuevoEstadoInicial.getTransiciones().add(transicionEstadoInicialToAceptacion);
        nuevoEstadoInicial.getTransiciones().add(transicionEstadoInicialToAFN2);

        for (Estado estadoAceptacion : this.estadosAceptacion) {
            estadoAceptacion.getTransiciones().add(new Transicion(EPSILON.getSimbolo(), nuevoEstadoAceptacion));
            estadoAceptacion.setEsAceptacion(false);
        }

        for (Estado estadoAceptacion : afn2.estadosAceptacion) {
            estadoAceptacion.getTransiciones().add(new Transicion(EPSILON.getSimbolo(), nuevoEstadoAceptacion));
            estadoAceptacion.setEsAceptacion(false);
        }

        //Limpiamos los antiguos estados de aceptacion
        this.estadosAceptacion.clear();
        afn2.estadosAceptacion.clear();

        //Actualizamos estados de aceptacion
        nuevoEstadoAceptacion.setEsAceptacion(true);
        this.estadosAceptacion.add(nuevoEstadoAceptacion);

        this.estadoInicial = nuevoEstadoInicial; // Actualizamos nuevo estado inicial 

        // Agregamos los estados
        this.estados.addAll(afn2.estados); //agregamos los estados que tenia el AFN2
        this.estados.add(nuevoEstadoInicial);
        this.estados.add(nuevoEstadoAceptacion);
        this.Alfabeto.addAll(afn2.Alfabeto); //agregamos los simbolos del alfabeto de Afn2

        return this;
    }

    public AFN afnUnionAND(AFN afn2) {
        //Fusion de estado de aceptacion de "this" con estado inicial de afn2
        //Se conserva el estado de aceptacion de this

        afn2 = afn2.duplicarAFN();

        for(Transicion transicionToEstadoInicialAFN2: afn2.getEstadoInicial().getTransiciones()){
            for(Estado estadoAceptacion: this.estadosAceptacion){
                estadoAceptacion.getTransiciones().add(transicionToEstadoInicialAFN2);
                estadoAceptacion.setEsAceptacion(false);
            }
        }

        //Se elimina el estado inicial de "afn2" de su lista de estados //? Checar por que?
        afn2.estados.remove(afn2.estadoInicial);

        //Actualizacion de automata tras concatenacion
        this.estadosAceptacion = afn2.estadosAceptacion;
        this.estados.addAll(afn2.estados);
        this.Alfabeto.addAll(afn2.Alfabeto);

        return this;
    }

    public HashSet<Estado> cerraduraEpsilon(HashSet<Estado> conjuntoEstados)  {
        HashSet<Estado> conjuntoResultadoEstados = new HashSet<>();
        Stack<Estado> pilaEstados = new Stack<>();

        for (Estado estado : conjuntoEstados)
            pilaEstados.push(estado);

        Estado estado;

        while (!pilaEstados.isEmpty()) {
            estado = pilaEstados.pop();
            if (conjuntoResultadoEstados.contains(estado))
                continue;
            conjuntoResultadoEstados.add(estado);

            for (Transicion transiciones : estado.getTransiciones())
                if (transiciones.getSimboloInferior() == EPSILON.getSimbolo())
                    pilaEstados.push(transiciones.getEstado());
        }
        return conjuntoResultadoEstados;
    }

    //? CHECAR COMO FUNCIONA
    public HashSet<Estado> mover(HashSet<Estado> estados, char simbolo) {
        HashSet<Estado> conjuntoResultadoEstados = new HashSet<>();
        Estado aux;

        for (Estado estado : estados) {
            for (Transicion transicion : estado.getTransiciones()) {
                aux = transicion.getEstadoTransicion(simbolo);
                if (aux != null)
                    conjuntoResultadoEstados.add(aux);
            }
        }
        return conjuntoResultadoEstados;
    }

    public HashSet<Estado> ir_A(HashSet<Estado> estados, char simbolo) {
        return cerraduraEpsilon(mover(estados, simbolo));
    }

    public AFD toAFD() {
        Queue<EstadoSj> Q = new LinkedList<>();
        HashMap<HashSet<Estado>, Integer> C = new HashMap<>(); // Sj = IrA = {0,1,2}
        HashSet<Estado> A = new HashSet<>();

        Table<Integer, String, Integer> afdTabla = HashBasedTable.create();
        EstadoSj aux = new EstadoSj(new HashSet<>());
        int indice = 0;

        A.add(this.estadoInicial);
        aux.setEstados(cerraduraEpsilon(A));
        aux.setId(indice);
        C.put(aux.getEstados(), aux.getId()); // conjunto de conjuntos Sj
        Q.add(aux);// los que vamos a analizar

        while (!Q.isEmpty()) {
            aux = Q.remove(); // analizamos aux

            for (char c : this.Alfabeto) {
                EstadoSj aux2 = new EstadoSj();
                aux2.setEstados(ir_A(aux.getEstados(), c));
                if (aux2.getEstados().isEmpty()) {
                    // significa que no hubo transiciones
                    afdTabla.put(aux.getId(), Character.toString(c), -1);
                    continue; // asignar -1 al estado Sj(aux) y el caracter c
                }
                if (C.containsKey(aux2.getEstados())) {// Si Sj(aux2) ya existe en la lista(C)
                    afdTabla.put(aux.getId(), Character.toString(c), C.get(aux2.getEstados()));
                    continue;
                }
                aux2.setId(++indice);
                C.put(aux2.getEstados(), aux2.getId());
                Q.add(aux2);
                afdTabla.put(aux.getId(), Character.toString(c), C.get(aux2.getEstados()));

            }
        }
        //Obtener estados de aceptacion del AFD
        HashMap<Integer,Integer> estadosAceptacionAFD = new HashMap<>();
        for (HashSet<Estado> conjuntoEstadosSj : C.keySet()){
            boolean esAceptacion = false;
            int token = -1;
            for (Estado estado : conjuntoEstadosSj)
                if (estadosAceptacion.contains(estado)) {
                    esAceptacion = true;
                    token = estado.getToken();
                }
            if(esAceptacion)
                estadosAceptacionAFD.put(C.get(conjuntoEstadosSj),token);
        }
        //Crear AFD
        return new AFD(this.getAlfabeto(), estadosAceptacionAFD, afdTabla);
    }
    
    public AFD toAFD(int idafd) {
        Queue<EstadoSj> Q = new LinkedList<>();
        HashMap<HashSet<Estado>, Integer> C = new HashMap<>(); // Sj = IrA = {0,1,2}
        HashSet<Estado> A = new HashSet<>();

        Table<Integer, String, Integer> afdTabla = HashBasedTable.create();
        EstadoSj aux = new EstadoSj(new HashSet<>());
        int indice = 0;

        A.add(this.estadoInicial);
        aux.setEstados(cerraduraEpsilon(A));
        aux.setId(indice);
        C.put(aux.getEstados(), aux.getId()); // conjunto de conjuntos Sj
        Q.add(aux);// los que vamos a analizar

        while (!Q.isEmpty()) {
            aux = Q.remove(); // analizamos aux

            for (char c : this.Alfabeto) {
                EstadoSj aux2 = new EstadoSj();
                aux2.setEstados(ir_A(aux.getEstados(), c));
                if (aux2.getEstados().isEmpty()) {
                    // significa que no hubo transiciones
                    afdTabla.put(aux.getId(), Character.toString(c), -1);
                    continue; // asignar -1 al estado Sj(aux) y el caracter c
                }
                if (C.containsKey(aux2.getEstados())) {// Si Sj(aux2) ya existe en la lista(C)
                    afdTabla.put(aux.getId(), Character.toString(c), C.get(aux2.getEstados()));
                    continue;
                }
                aux2.setId(++indice);
                C.put(aux2.getEstados(), aux2.getId());
                Q.add(aux2);
                afdTabla.put(aux.getId(), Character.toString(c), C.get(aux2.getEstados()));

            }
        }
        //Obtener estados de aceptacion del AFD
        HashMap<Integer,Integer> estadosAceptacionAFD = new HashMap<>();
        for (HashSet<Estado> conjuntoEstadosSj : C.keySet()){
            boolean esAceptacion = false;
            int token = -1;
            for (Estado estado : conjuntoEstadosSj)
                if (estadosAceptacion.contains(estado)) {
                    esAceptacion = true;
                    token = estado.getToken();
                }
            if(esAceptacion)
                estadosAceptacionAFD.put(C.get(conjuntoEstadosSj),token);
        }
        //Crear AFD
        return new AFD(idafd,this.getAlfabeto(), estadosAceptacionAFD, afdTabla);
    }

    /* public void UnionEspecialAFNs(AFN afn, int token) {
        Estado estado;
        if (!this.agregadoAUnionLexico) {
            this.estados.clear();
            this.Alfabeto.clear();
            estado = new Estado();
            estado.getTransiciones().add(new Transicion(EPSILON.getSimbolo(), afn.getEstadoInicial()));
            for (Estado estadoAceptacion : afn.getEstadosAceptacion())
                estadoAceptacion.setToken(token);
            this.estadosAceptacion.addAll(afn.getEstadosAceptacion());
            this.estados.addAll(afn.getEstados());
            this.Alfabeto.addAll(afn.getAlfabeto());
        }
    } */

    public AFN cerraduraKleen() {
        Estado estadoIncial = new Estado();
        Estado estadoFinal = new Estado();

        estadoIncial.getTransiciones().add(new Transicion(EPSILON.getSimbolo(), this.estadoInicial));
        estadoIncial.getTransiciones().add(new Transicion(EPSILON.getSimbolo(), estadoFinal));

        for (Estado estadoAceptacion : this.estadosAceptacion) {

            estadoAceptacion.getTransiciones().add(new Transicion(EPSILON.getSimbolo(), estadoFinal));
            estadoAceptacion.getTransiciones().add(new Transicion(EPSILON.getSimbolo(), this.estadoInicial));
            estadoAceptacion.setEsAceptacion(false);
        }

        this.estadoInicial = estadoIncial;
        estadoFinal.setEsAceptacion(true);
        this.estadosAceptacion.clear();
        this.estadosAceptacion.add(estadoFinal);
        this.estados.add(estadoIncial);
        this.estados.add(estadoFinal);

        return this;
    }

    public AFN cerraduraPositiva() {
        Estado estadoInicial = new Estado();
        Estado estadoFinal = new Estado();

        estadoInicial.getTransiciones().add(new Transicion(EPSILON.getSimbolo(), this.estadoInicial));
        for (Estado estado : estadosAceptacion ){
            estado.getTransiciones().add(new Transicion(EPSILON.getSimbolo(), estadoFinal));
            estado.getTransiciones().add(new Transicion(EPSILON.getSimbolo(), this.estadoInicial));
            estado.setEsAceptacion(false);
        }

        this.estadoInicial = estadoInicial;
        estadoFinal.setEsAceptacion(true);
        this.estadosAceptacion.clear();
        this.estadosAceptacion.add(estadoFinal);
        this.estados.add(estadoInicial);
        this.estados.add(estadoFinal);

        return this;
    }

    public AFN unionLexicoAFNs() {
        //Se crea un nuevo estado inicial
        Estado nuevoEstadoInicial = new Estado();
        HashSet<AFN> estadosAeliminar = new HashSet<>();

        //Se recorren todos los AFN almacenados
        for (AFN afnUnidoAUnionLexico : conjuntoAFNsUnionLexico) {
            //Se agrega una transicion epsilon del nuevo estado inicial al estado inicial del AFN actual
            nuevoEstadoInicial.getTransiciones().add(new Transicion(EPSILON.getSimbolo(), afnUnidoAUnionLexico.estadoInicial));
            //Se agregan los estados del AFN, los estados de aceptacion y el alfabeto estadosAeliminar el AFN invocante
            this.estados.addAll(afnUnidoAUnionLexico.estados);
            this.estadosAceptacion.addAll(afnUnidoAUnionLexico.estadosAceptacion);
            this.Alfabeto.addAll(afnUnidoAUnionLexico.Alfabeto);
            //Se agrega el estado actual al hashset de los estados que se eliminaran
            estadosAeliminar.add(afnUnidoAUnionLexico);
        }

        //Se elimina del conjunto de AFNs los AFN que entran al AFN del analizador lexico
        conjuntoAFNsUnionLexico.removeAll(estadosAeliminar);
        //Se agrega el nuevo estado inicial al conjunto de estados del AFN invocante
        this.estados.add(nuevoEstadoInicial);
        //Se asigna el nuevo estado inicial al AFN invocante
        this.estadoInicial = nuevoEstadoInicial;
        return this;
    }

    public AFN duplicarAFN(){
        AFN nuevoAFN = new AFN();
        nuevoAFN.setAlfabeto(this.Alfabeto);
        // Estado de AFN original, estado del nuevo AFN
        HashMap<Estado, Estado> mapEstados = new HashMap<>();
        HashSet<Estado> estadosNuevoAFN = new HashSet<>();
        //Llena el HashMap con los pares de estados correspondientes
        for(Estado estado : this.estados){
            Estado estadoNuevoAFN = new Estado();
            mapEstados.put(estado,estadoNuevoAFN);
            estadosNuevoAFN.add(estadoNuevoAFN);
        }
        nuevoAFN.setEstados(estadosNuevoAFN);
        //Copiar atributos a los nuevos estados
        for(Estado estado : this.estados){
            Estado estadoNuevoAFN = mapEstados.get(estado);
            estadoNuevoAFN.setToken(estado.getToken());
            HashSet<Transicion> transicionesNuevoAFN = new HashSet<>();
            //Copiar las nuevas transiciones
            for(Transicion transicion : estado.getTransiciones()){
                Transicion tnuevoAFN;
                char simbolo = transicion.getSimboloInferior();
                tnuevoAFN = new Transicion( transicion.getSimboloInferior(),
                        transicion.getSimboloSuperior(),
                        mapEstados.get(transicion.getEstadoTransicion(simbolo)));
                transicionesNuevoAFN.add(tnuevoAFN);
            }
            estadoNuevoAFN.setTransiciones(transicionesNuevoAFN);
        }
        //Copiar estados de aceptacion
        HashSet<Estado> aceptacionNuevoAFN = new HashSet<>();
        for (Estado estadoAceptacion : this.estadosAceptacion){
            Estado estadoAcepNuevoAFN = mapEstados.get(estadoAceptacion);
            aceptacionNuevoAFN.add(estadoAcepNuevoAFN);
            estadoAcepNuevoAFN.setEsAceptacion(true);
        }
        nuevoAFN.setEstadosAceptacion(aceptacionNuevoAFN);
        nuevoAFN.setEstadoInicial(mapEstados.get(this.estadoInicial));
        return nuevoAFN;
    }
    public void cambiarToken(int token){
        this.getEstadosAceptacion().stream().forEach(estadoAceptacion->estadoAceptacion.setToken(token));
    }

    public AFN crearAFNaER(){
        List<Character> excepciones = new ArrayList<>(Arrays.asList('|','&','+','*','?','(',')','[',']','-','\\'));
        AFN or = new AFN();
        AFN concatenar = new AFN();
        AFN cerraduraPositiva = new AFN();
        AFN cerraduraKleen = new AFN();
        AFN opcional = new AFN();
        AFN parentesisIzquierdo = new AFN();
        AFN parentesisDerecho = new AFN();
        AFN corcheteIzquierdo = new AFN();
        AFN corcheteDerecho = new AFN();
        AFN guion = new AFN();
        AFN simbolo = new AFN();

        // Creacion de los automatas basicos para cada clase lexica
        or.CrearAFNBasico('|');
        concatenar.CrearAFNBasico('&');
        cerraduraPositiva.CrearAFNBasico('+');
        cerraduraKleen.CrearAFNBasico('*');
        opcional.CrearAFNBasico('?');
        parentesisDerecho.CrearAFNBasico(')');
        parentesisIzquierdo.CrearAFNBasico('(');
        corcheteDerecho.CrearAFNBasico(']');
        corcheteIzquierdo.CrearAFNBasico('[');
        guion.CrearAFNBasico('-');
        simbolo.CrearAFNBasico((char)0,(char)255,excepciones);

        // Se agregan los simbolos con secuencia de escape "\"
        AFN secuenciaEscape = new AFN(), simbolosEscape = new AFN();
        secuenciaEscape.CrearAFNBasico('\\');
        simbolosEscape.CrearAFNBasico(excepciones);
        secuenciaEscape.afnUnionAND(simbolosEscape);
        simbolo.afnUnionOR(secuenciaEscape);

        // Agregar tokens
        or.cambiarToken(ERtoAFNTokens.OR.getToken());
        concatenar.cambiarToken(ERtoAFNTokens.CONCATENAR.getToken());
        cerraduraPositiva.cambiarToken(ERtoAFNTokens.CERRADURA_POSITIVA.getToken());
        cerraduraKleen.cambiarToken(ERtoAFNTokens.CERRADURA_KLEEN.getToken());
        opcional.cambiarToken(ERtoAFNTokens.OPCIONAL.getToken());
        parentesisDerecho.cambiarToken(ERtoAFNTokens.PARENTESIS_DERECHO.getToken());
        parentesisIzquierdo.cambiarToken(ERtoAFNTokens.PARENTESIS_IZQUIERDO.getToken());
        corcheteDerecho.cambiarToken(ERtoAFNTokens.CORCHETE_DERECHO.getToken());
        corcheteIzquierdo.cambiarToken(ERtoAFNTokens.CORCHETE_IZQUIERDO.getToken());
        guion.cambiarToken(ERtoAFNTokens.GUION.getToken());
        simbolo.cambiarToken(ERtoAFNTokens.SIMBOLO.getToken());

        //Unir AFNs
        conjuntoAFNsUnionLexico.add(or);
        conjuntoAFNsUnionLexico.add(concatenar);
        conjuntoAFNsUnionLexico.add(cerraduraPositiva);
        conjuntoAFNsUnionLexico.add(cerraduraKleen);
        conjuntoAFNsUnionLexico.add(opcional);
        conjuntoAFNsUnionLexico.add(parentesisDerecho);
        conjuntoAFNsUnionLexico.add(parentesisIzquierdo);
        conjuntoAFNsUnionLexico.add(corcheteDerecho);
        conjuntoAFNsUnionLexico.add(corcheteIzquierdo);
        conjuntoAFNsUnionLexico.add(guion);
        conjuntoAFNsUnionLexico.add(simbolo);

        return this.unionLexicoAFNs();
    }

    public AFN crearAFNGramaticaDeGramaticas(){
        AFN afNaER = new AFN();
        afNaER.crearAFNaER();
        AFD afdAER = afNaER.toAFD();

        for(GramaticaDeGramaticasTokens token : GramaticaDeGramaticasTokens.values()){
            if(token == GramaticaDeGramaticasTokens.FIN_CADENA)
                break;
            ExpresionRegularToAFN ERtoAFN = new ExpresionRegularToAFN(token.getExpresionRegular(),afdAER);
            ERtoAFN.iniciarConversionExpresionRegularToAFN();
            ERtoAFN.getAfnResultante().cambiarToken(token.getToken());
            conjuntoAFNsUnionLexico.add(ERtoAFN.getAfnResultante());
        }
        this.unionLexicoAFNs();
        return this;
    }
}
