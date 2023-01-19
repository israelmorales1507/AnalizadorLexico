/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Model;

/**
 *
 * @author Israel Morales
 */
public class ParametrosER {

    AFN afn = new AFN();

    public AFN getAfn() {
        return afn;
    }

    public void setAfn(AFN afn) {
        this.afn = afn;
    }
    
    public ParametrosER() {
        this.afn = new AFN();
    }
    
    public ParametrosER(AFN afn){
        this.afn = afn;
    }
}
