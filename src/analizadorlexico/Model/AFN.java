/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Israel Morales
 */
public class AFN {

    public static ArrayList<AFN> conjuntoAFN = new ArrayList<>();
    public Estado initEstado;
    public ArrayList<Estado> estadosAFN = new ArrayList<Estado>();
    public ArrayList<Estado> estadosAceptacion = new ArrayList<Estado>();
    public ArrayList<Character> alfabeto = new ArrayList<Character>();
    public boolean SeAgregoAFNUnionLexico;
    public int idAFN;

    public int getIdAFN() {
        return idAFN;
    }

    public void setIdAFN(int idAFN) {
        this.idAFN = idAFN;
    }

    public AFN() {
        idAFN = 0;
        initEstado = null;
        estadosAFN.clear();
        estadosAceptacion.clear();
        alfabeto.clear();
        SeAgregoAFNUnionLexico = false;
    }

    public AFN CrearAFNBasico(char simb) {
        Transicion t;
        Estado e1, e2;
        e1 = new Estado();
        e2 = new Estado();
        t = new Transicion(simb, e2);
        e1.listTransicions.add(t);
        e2.setEdoAceptacion(true);
        alfabeto.add(simb);
        initEstado = e1;
        estadosAFN.add(e1);
        estadosAFN.add(e2);
        estadosAceptacion.add(e2);
        SeAgregoAFNUnionLexico = false;
        return this;
    }

    public AFN CrearAFNBasico(char simbInf, char simbSup) {
        Transicion t;
        Estado e1, e2;
        e1 = new Estado();
        e2 = new Estado();
        t = new Transicion(simbInf, simbSup, e2);
        e1.listTransicions.add(t);
        e2.setEdoAceptacion(true);
        for (char i = simbInf; i <= simbSup; i++) {
            alfabeto.add(i);
        }
        initEstado = e1;
        estadosAFN.add(e1);
        estadosAFN.add(e2);
        estadosAceptacion.add(e2);
        return this;
    }
    
    public AFN CrearAFNBasico(char simboloInferior, char simboloSuperior, List<Character> excepciones) {
        if(simboloInferior>simboloSuperior)
            return null;
        Estado estadoInicial = new Estado();
        Estado estadoAceptacion = new Estado();
        estadoAceptacion.setEdoAceptacion(true);
        for (char simboloActual = simboloInferior; simboloActual <= simboloSuperior; simboloActual++)
            if (!excepciones.contains(simboloActual)) {
                alfabeto.add(simboloActual);
                Transicion transicionEstadoInicialToAceptacion = new Transicion(simboloActual, estadoAceptacion);
                estadoInicial.getListTransicions().add(transicionEstadoInicialToAceptacion);
            }
        this.initEstado = estadoInicial;
        estadosAFN.add(estadoInicial);
        estadosAFN.add(estadoAceptacion);
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
            estadoInicial.getListTransicions().add(transicionEstadoInicialToAceptacion);
            alfabeto.add(simbolo);
        }
        estadoAceptacion.setEdoAceptacion(true);
        this.initEstado = estadoInicial;
        estadosAFN.add(estadoInicial);
        estadosAFN.add(estadoAceptacion);
        estadosAceptacion.add(estadoAceptacion);
        return this;
    }

    public AFN UnirAFNS(AFN f2) {
        Estado e1 = new Estado();
        Estado e2 = new Estado();
        // e1 tendra 2 trasciones, una hacia estado unicia del f1
        // y la otra inicial de f2
        Transicion t1 = new Transicion(CaracteresEspeciales.Epsilon, initEstado);
        Transicion t2 = new Transicion(CaracteresEspeciales.Epsilon, f2.initEstado);
        e1.getListTransicions().add(t1);
        e1.getListTransicions().add(t2);
        // Cambiar estados de aceptacion en f1 y f2
        for (Estado edo : this.estadosAceptacion) {
            edo.getListTransicions().add(new Transicion(CaracteresEspeciales.Epsilon, e2));
            edo.setEdoAceptacion(false);
        }
        for (Estado edo : f2.estadosAceptacion) {
            edo.getListTransicions().add(new Transicion(CaracteresEspeciales.Epsilon, e2));
            edo.setEdoAceptacion(false);
        }
        // e2 es estado de aceptacion.
        e2.setEdoAceptacion(true);
        // Limpiar estado de aceptacion de f1 y f2
        this.estadosAceptacion.clear();
        f2.estadosAceptacion.clear();
        // Estado de inicio
        this.initEstado = e1;
        // Estado de aceptacion
        this.estadosAceptacion.add(e2);
        // Estados del nuevo automata
        this.estadosAFN.addAll(f2.estadosAFN);
        this.estadosAFN.add(e1);
        this.estadosAFN.add(e2);
        this.alfabeto.addAll(f2.alfabeto);
        return this;

    }
    
    public void cambiarToken(int token){
        this.estadosAceptacion.stream().forEach(estadoAceptacion->estadoAceptacion.setToken(token));
    }

    public AFN ConcatenacionAFN(AFN f2) {
        for (Transicion t : f2.initEstado.getListTransicions()) {
            for (Estado e : this.estadosAceptacion) {
                e.listTransicions.add(t);
                e.setEdoAceptacion(false);
            }
        }
        f2.estadosAFN.remove(f2.initEstado);
        // actualizamos la info del nuevo automata
        this.estadosAceptacion = f2.estadosAceptacion;
        this.estadosAFN.addAll(f2.estadosAFN);
        this.alfabeto.addAll(f2.alfabeto);
        return this;
    }

    public AFN CerraduraPos() {
        Estado init = new Estado();
        Estado fin = new Estado();
        init.listTransicions.add(new Transicion(CaracteresEspeciales.Epsilon, this.initEstado));
        for (Estado edo : estadosAceptacion) {
            edo.listTransicions.add(new Transicion(CaracteresEspeciales.Epsilon, fin));
            edo.listTransicions.add(new Transicion(CaracteresEspeciales.Epsilon, initEstado));
            edo.setEdoAceptacion(false);
        }
        initEstado = init;
        fin.setEdoAceptacion(true);
        estadosAceptacion.clear();
        estadosAceptacion.add(fin);
        estadosAFN.add(init);
        estadosAFN.add(fin);
        return this;
    }

    public AFN CerraduraKleen() {
        Estado init = new Estado();
        Estado fin = new Estado();
        init.listTransicions.add(new Transicion(CaracteresEspeciales.Epsilon, initEstado));
        init.listTransicions.add(new Transicion(CaracteresEspeciales.Epsilon, fin));
        for (Estado edo : estadosAceptacion) {
            edo.listTransicions.add(new Transicion(CaracteresEspeciales.Epsilon, fin));
            edo.listTransicions.add(new Transicion(CaracteresEspeciales.Epsilon, initEstado));
            edo.setEdoAceptacion(false);
        }
        initEstado = init;
        fin.setEdoAceptacion(true);
        estadosAceptacion.clear();
        estadosAceptacion.add(fin);
        estadosAFN.add(init);
        estadosAFN.add(fin);

        return this;
    }

    public AFN Opcional() {
        Estado init = new Estado();
        Estado fin = new Estado();
        init.listTransicions.add(new Transicion(CaracteresEspeciales.Epsilon, initEstado));
        init.listTransicions.add(new Transicion(CaracteresEspeciales.Epsilon, fin));
        for (Estado edo : estadosAceptacion) {
            edo.listTransicions.add(new Transicion(CaracteresEspeciales.Epsilon, fin));
            edo.setEdoAceptacion(false);
        }
        initEstado = init;
        fin.setEdoAceptacion(true);
        estadosAceptacion.clear();
        estadosAceptacion.add(fin);
        estadosAFN.add(init);
        estadosAFN.add(fin);
        return this;
    }

    public ArrayList<Estado> CerraduraEpsilon(Estado e) {
        ArrayList<Estado> R = new ArrayList<Estado>();
        Stack<Estado> S = new Stack<Estado>();
        Estado tmp, edo;
        R.clear();
        S.clear();
        S.push(e);
        while (!S.isEmpty()) {
            tmp = S.pop();
            R.add(tmp);
            for (Transicion t : tmp.getListTransicions()) {
                if ((edo = t.GetEstadoTransicion(CaracteresEspeciales.Epsilon)) != null) {
                    if (!R.contains(edo)) {
                        S.push(edo);
                    }
                }
            }
        }
        return R;
    }

    public ArrayList<Estado> CerraduraEpsilon(ArrayList<Estado> conjuntoestados) {
        ArrayList<Estado> R = new ArrayList<Estado>();
        Stack<Estado> S = new Stack<Estado>();
        Estado tmp, edo;
        R.clear();
        S.clear();
        for (Estado e : conjuntoestados) {
            S.push(e);
        }
        while (!S.isEmpty()) {
            tmp = S.pop();
            R.add(tmp);
            for (Transicion t : tmp.getListTransicions()) {
                if ((edo = t.GetEstadoTransicion(CaracteresEspeciales.Epsilon)) != null) {
                    if (!R.contains(edo)) {
                        S.push(edo);
                    }
                }
            }
        }
        return R;
    }

    public ArrayList<Estado> Mover(Estado edo, char simb) {
        ArrayList<Estado> C = new ArrayList<Estado>();
        Estado tmp;
        C.clear();
        for (Transicion t : edo.getListTransicions()) {
            if ((tmp = t.GetEstadoTransicion(simb)) != null) {
                C.add(tmp);
            }
        }
        return C;
    }

    public ArrayList<Estado> Mover(ArrayList<Estado> conjuntoestados, char simb) {
        ArrayList<Estado> C = new ArrayList<Estado>();
        Estado tmp;
        C.clear();
        for (Estado edo : conjuntoestados) {
            for (Transicion t : edo.getListTransicions()) {
                if ((tmp = t.GetEstadoTransicion(simb)) != null) {
                    C.add(tmp);
                }
            }
        }
        return C;
    }

    public ArrayList<Estado> IrA(ArrayList<Estado> edos, char simb) {
        return CerraduraEpsilon(Mover(edos, simb));
    }

    public void UnionEspecialAFN(AFN f, int token) {
        Estado e;
        if (!this.SeAgregoAFNUnionLexico) {
            this.estadosAFN.clear();
            this.alfabeto.clear();
            e = new Estado();
            e.listTransicions.add(new Transicion(CaracteresEspeciales.Epsilon, f.initEstado));
            this.initEstado = e;
            this.estadosAFN.add(e);
            this.SeAgregoAFNUnionLexico = true;
        } else {
            this.initEstado.listTransicions.add(new Transicion(CaracteresEspeciales.Epsilon, f.initEstado));
        }

        for (Estado edo : f.estadosAceptacion) {
            edo.setToken(token);
        }

        this.estadosAceptacion.addAll(f.estadosAceptacion);
        this.estadosAFN.addAll(f.estadosAFN);
        this.alfabeto.addAll(f.alfabeto);
    }
    
    public AFN unionLexicoAFNs() {
        ArrayList<AFN> estadosAeliminar = new ArrayList<>();
        Estado nuevoEstadoInicial = new Estado();
        for (AFN afnUnidoAUnionLexico : conjuntoAFN) {
            nuevoEstadoInicial.listTransicions.add(new Transicion(CaracteresEspeciales.Epsilon, afnUnidoAUnionLexico.initEstado));
            this.estadosAFN.addAll(afnUnidoAUnionLexico.estadosAFN);
            this.estadosAceptacion.addAll(afnUnidoAUnionLexico.estadosAceptacion);
            this.alfabeto.addAll(afnUnidoAUnionLexico.alfabeto);
            estadosAeliminar.add(afnUnidoAUnionLexico);
        }
        this.conjuntoAFN.removeAll(estadosAeliminar);
        this.estadosAFN.add(nuevoEstadoInicial);
        this.initEstado = nuevoEstadoInicial;
        this.SeAgregoAFNUnionLexico = true;
        return this;
    }

    public AFD ConvertirAFNaAFD() {
        int j;
        EstadoIj Ij, Ik;
        boolean existe;

        ArrayList<Estado> ConjuntoAux = new ArrayList<>();
        ArrayList<EstadoIj> EstadosAFD = new ArrayList<>();
        Stack<EstadoIj> SinAnalizar = new Stack<>();

        EstadosAFD.clear();
        SinAnalizar.clear();

        j = 0;
        Ij = new EstadoIj();
        Ij.setConjuntoIj(CerraduraEpsilon(this.initEstado));
        Ij.setIdj(j);
        EstadosAFD.add(Ij);
        SinAnalizar.add(Ij);
        j++;

        while (!SinAnalizar.empty()) {
            Ij = SinAnalizar.pop();
            for (char c : alfabeto) {
                Ik = new EstadoIj();
                Ik.setConjuntoIj(IrA(Ij.getConjuntoIj(), c));
                if (Ik.ConjuntoIj.isEmpty()) {
                    continue;
                }
                existe = false;
                for (EstadoIj edoij : EstadosAFD) {
                    if (edoij.ConjuntoIj.equals(Ik.ConjuntoIj)) {
                        existe = true;
                        Ij.trascionesAFD[c] = edoij.getIdj();
                        break;
                    }
                }
                if (!existe) {
                    Ik.setIdj(j);
                    Ij.trascionesAFD[c] = Ik.getIdj();
                    EstadosAFD.add(Ik);
                    SinAnalizar.push(Ik);
                    j++;
                }
            }
        }
        for (EstadoIj I : EstadosAFD) {
            ConjuntoAux.clear();
            ConjuntoAux.addAll(I.ConjuntoIj);
            ConjuntoAux.retainAll(this.estadosAceptacion);
            if (!ConjuntoAux.isEmpty()) {
                for (Estado aceptacion : ConjuntoAux) {
                    I.trascionesAFD[alfabeto.size() - 1] = aceptacion.getToken();
                    break;
                }
            } else {
                I.trascionesAFD[CaracteresEspeciales.ARREGLO] = -1;
            }
        }
        return new AFD(idAFN, EstadosAFD, alfabeto);
    }

    public AFD ConvertirAFNaAFD(int idafd) {
        int j;
        EstadoIj Ij, Ik;
        boolean existe;

        ArrayList<Estado> ConjuntoAux = new ArrayList<>();
        ArrayList<EstadoIj> EstadosAFD = new ArrayList<>();
        Stack<EstadoIj> SinAnalizar = new Stack<>();

        EstadosAFD.clear();
        SinAnalizar.clear();

        j = 0;
        Ij = new EstadoIj();
        Ij.setConjuntoIj(CerraduraEpsilon(this.initEstado));
        Ij.setIdj(j);
        EstadosAFD.add(Ij);
        SinAnalizar.add(Ij);
        j++;

        while (!SinAnalizar.empty()) {
            Ij = SinAnalizar.pop();
            for (char c : alfabeto) {
                Ik = new EstadoIj();
                Ik.setConjuntoIj(IrA(Ij.getConjuntoIj(), c));
                if (Ik.ConjuntoIj.isEmpty()) {
                    continue;
                }
                existe = false;
                for (EstadoIj edoij : EstadosAFD) {
                    if (edoij.ConjuntoIj.equals(Ik.ConjuntoIj)) {
                        existe = true;
                        Ij.trascionesAFD[c] = edoij.getIdj();
                        break;
                    }
                }
                if (!existe) {
                    Ik.setIdj(j);
                    Ij.trascionesAFD[c] = Ik.getIdj();
                    EstadosAFD.add(Ik);
                    SinAnalizar.push(Ik);
                    j++;
                }
            }
        }
        for (EstadoIj I : EstadosAFD) {
            ConjuntoAux.clear();
            ConjuntoAux.addAll(I.ConjuntoIj);
            ConjuntoAux.retainAll(this.estadosAceptacion);
            if (!ConjuntoAux.isEmpty()) {
                for (Estado aceptacion : ConjuntoAux) {
                    I.trascionesAFD[CaracteresEspeciales.ARREGLO - 1] = aceptacion.getToken();
                    break;
                }
            } else {
                I.trascionesAFD[CaracteresEspeciales.ARREGLO - 1] = -1;
            }
        }
        return new AFD(idafd, EstadosAFD, alfabeto);
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
        AFN secuenciaEscape = new AFN(), simbolosEscape = new AFN();
        secuenciaEscape.CrearAFNBasico('\\');
        simbolosEscape.CrearAFNBasico(excepciones);
        secuenciaEscape.ConcatenacionAFN(simbolosEscape);
        simbolo.UnirAFNS(secuenciaEscape);
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
        conjuntoAFN.add(or);
        conjuntoAFN.add(concatenar);
        conjuntoAFN.add(cerraduraPositiva);
        conjuntoAFN.add(cerraduraKleen);
        conjuntoAFN.add(opcional);
        conjuntoAFN.add(parentesisDerecho);
        conjuntoAFN.add(parentesisIzquierdo);
        conjuntoAFN.add(corcheteDerecho);
        conjuntoAFN.add(corcheteIzquierdo);
        conjuntoAFN.add(guion);
        conjuntoAFN.add(simbolo);
        return this.unionLexicoAFNs();
    }
    public AFN crearAFNGramaticaDeGramaticas(){
        AFN afNaER = new AFN();
        afNaER.crearAFNaER();
        AFD afdAER = afNaER.ConvertirAFNaAFD();
        for(GramaticaDeGramaticasTokens token : GramaticaDeGramaticasTokens.values()){
            if(token == GramaticaDeGramaticasTokens.FIN_CADENA)
                break;
            ERAFN ERtoAFN = new ERAFN(token.getExpresionRegular(),afdAER);
            ERtoAFN.InitConversion();
            ERtoAFN.getResult().cambiarToken(token.getToken());
            conjuntoAFN.add(ERtoAFN.getResult());
        }
        this.unionLexicoAFNs();
        return this;
    }
}
