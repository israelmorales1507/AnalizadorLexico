/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Controller;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Israel Morales
 */
public class SaveFile {

    public SaveFile() {
    }
    
    public void GuardarArchivo(Object serObj, String filepath){
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");
        } catch (Exception e) {
            System.out.println("Error... "+e.toString());
        }
    }
    
    
}
