/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Model;

import java.util.ArrayList;

/**
 *
 * @author Israel Morales
 */
public class Estado {

    public static int ContadorEstados = 0;
    private int idEstado;
    private boolean edoAceptacion;
    private int token;
    public ArrayList<Transicion> listTransicions = new ArrayList<>();

    public static int getContadorEstados() {
        return ContadorEstados;
    }

    public static void setContadorEstados(int contadorEstados) {
        ContadorEstados = contadorEstados;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public boolean isEdoAceptacion() {
        return edoAceptacion;
    }

    public void setEdoAceptacion(boolean edoAceptacion) {
        this.edoAceptacion = edoAceptacion;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public ArrayList<Transicion> getListTransicions() {
        return listTransicions;
    }

    public void setListTransicions(ArrayList<Transicion> listTransicions) {
        this.listTransicions = listTransicions;
    }

    @Override
    public String toString() {
        return "Estado{" + "idEstado=" + idEstado + ", edoAceptacion=" + edoAceptacion + ", token=" + token + ", listTransicions=" + listTransicions.toString() + '}';
    }
    
    public Estado() {
        edoAceptacion = false;
        token = -1;
        idEstado = ContadorEstados++;
    }
}
