/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Models.AFN;

/**
 *
 * @author Israel Morales
 */
public class Transicion {
    private char simboloInferior;
    private char simboloSuperior;
    private Estado estado;

    public char getSimboloInferior() {
        return simboloInferior;
    }

    public void setSimboloInferior(char simboloInferior) {
        this.simboloInferior = simboloInferior;
    }

    public char getSimboloSuperior() {
        return simboloSuperior;
    }

    public void setSimboloSuperior(char simboloSuperior) {
        this.simboloSuperior = simboloSuperior;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    

    public Transicion(char simboloInferior, char simboloSuperior, Estado estado) {
        this.simboloInferior = simboloInferior;
        this.simboloSuperior = simboloSuperior;
        this.estado = estado;
    }
    
    public Transicion(char simbolo, Estado estado){
        this.simboloInferior = simbolo;
        this.simboloSuperior = simbolo;
        this.estado = estado;
    }

    public Estado getEstadoTransicion(char simbolo){
        return (simboloInferior <= simbolo && simbolo<=simboloSuperior)? this.estado: null;
    }
}
