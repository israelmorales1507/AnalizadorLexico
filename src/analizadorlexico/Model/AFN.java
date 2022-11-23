/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Model;

import analizadorlexico.Model.CaracteresEspeciales;
import analizadorlexico.Model.Estado;
import analizadorlexico.Model.EstadoIj;
import analizadorlexico.Model.Transicion;
import java.util.ArrayList;
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
    private int idAFN;
    
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

    private int IndiceCaracter(char[] ArregloAlfabeto, char c) {
        int j;
        int tmp = ArregloAlfabeto.length;
        for (j = 0; j < tmp; j++) {
            if (ArregloAlfabeto[j] == c) {
                return j;
            }
        }
        return -1;
    }

    public AFN ConvertirAFNaAFD() {
        int NumEdoAFD;
        int i, j, r;
        char[] arregloAlfabeto;
        EstadoIj Ij, Ik;
        boolean existe;

        ArrayList<Estado> ConjuntoAux = new ArrayList<Estado>();
        ArrayList<EstadoIj> EstadosAFD = new ArrayList<EstadoIj>();
        Stack<EstadoIj> SinAnalizar = new Stack<EstadoIj>();

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
        NumEdoAFD = j;
        for (EstadoIj ij : EstadosAFD) {
            ConjuntoAux.clear();
            ConjuntoAux.addAll(ij.ConjuntoIj);
            ConjuntoAux.retainAll(this.estadosAceptacion);
            if (!ConjuntoAux.isEmpty()) {
                for (Estado aceptacion : ConjuntoAux) {
                    ij.trascionesAFD[alfabeto.size()] = aceptacion.getToken();
                    break;
                }
            }else{
                Ij.trascionesAFD[alfabeto.size()] = -1;
            }
        }
        
        return null;
    }
}
