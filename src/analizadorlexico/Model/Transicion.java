/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Model;

/**
 *
 * @author Israel Morales
 */
public class Transicion {

    private char simbInf;
    private char simbSup;
    private Estado estado;

    public char getSimbInf() {
        return simbInf;
    }

    public void setSimbInf(char simbInf) {
        this.simbInf = simbInf;
    }

    public char getSimbSup() {
        return simbSup;
    }

    public void setSimbSup(char simbSup) {
        this.simbSup = simbSup;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Transicion(char simb, Estado estado) {
        this.simbInf = simb;
        this.simbSup = simb;
        this.estado = estado;
    }

    public Transicion(char simbInf, char simbSup, Estado estado) {
        this.simbInf = simbInf;
        this.simbSup = simbSup;
        this.estado = estado;
    }

    public Transicion() {
        this.estado = null;
    }

    public void SetTransicionCompleta(char simbInf, char simbSup, Estado estado) {
        this.simbInf = simbInf;
        this.simbSup = simbSup;
        this.estado = estado;
    }

    public void SetTransicionCompleta(char simb, Estado estado) {
        this.simbInf = simb;
        this.simbSup = simb;
        this.estado = estado;
    }

    public Estado GetEstadoTransicion(char simb) {
        if (simbInf <= simb && simb <= simbSup) {
            return estado;
        }
        return null;
    }
}
