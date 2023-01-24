/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Controller;

import analizadorlexico.Models.AFD.AFD;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Israel Morales
 */
public class UtilFile {

    public UtilFile() {
    }

    public void GuardarArchivo(Object serObj, String filepath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");
        } catch (Exception e) {
            System.out.println("Error... " + e.toString());
        }
    }

    public String AbirArchivoAFD(File archivo) {
        try {
            FileInputStream fileIn = new FileInputStream(archivo.getAbsolutePath());
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object obj = objectIn.readObject();
            AFD.conjuntoAFDs =(HashSet<AFD>) obj;
            objectIn.close();
            return "Archivo abierto correctamente! : "+archivo.getName();
        } catch (Exception ex) {
            return "Error en el archivo: "+ex.getMessage();
        }
    }
}
