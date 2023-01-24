/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizadorlexico.Models.AFD;

import com.google.common.collect.Table;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;

/**
 *
 * @author Israel Morales
 */
public class AFD implements Serializable{
    private static final long serialVersionUID  =-4216387367063579242L;
    public static HashSet<AFD> conjuntoAFDs = new HashSet<>();
    private int idAFD;
    private String nombreArchivo;
    private HashSet<Character> Alfabeto;
    private HashMap<Integer,Integer> estadosAceptacion;
    private Table<Integer,String,Integer> Tabla;
    private final String CARPETA = "AFDs/";

    
    public static HashSet<AFD> getConjuntoAFDs() {
        return conjuntoAFDs;
    }

    public static void setConjuntoAFDs(HashSet<AFD> conjuntoAFDs) {
        AFD.conjuntoAFDs = conjuntoAFDs;
    }

    
    public int getIdAFD() {
        return idAFD;
    }

    public void setIdAFD(int idAFD) {
        this.idAFD = idAFD;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public HashSet<Character> getAlfabeto() {
        return Alfabeto;
    }

    public void setAlfabeto(HashSet<Character> Alfabeto) {
        this.Alfabeto = Alfabeto;
    }

    public HashMap<Integer, Integer> getEstadosAceptacion() {
        return estadosAceptacion;
    }

    public void setEstadosAceptacion(HashMap<Integer, Integer> estadosAceptacion) {
        this.estadosAceptacion = estadosAceptacion;
    }

    public Table<Integer, String, Integer> getTabla() {
        return Tabla;
    }

    public void setTabla(Table<Integer, String, Integer> Tabla) {
        this.Tabla = Tabla;
    }

    
    public AFD(int idAFD, String nombreArchivo, HashSet<Character> Alfabeto, HashMap<Integer, Integer> estadosAceptacion, Table<Integer, String, Integer> Tabla) {
        this.idAFD = idAFD;
        this.nombreArchivo = nombreArchivo;
        this.Alfabeto = Alfabeto;
        this.estadosAceptacion = estadosAceptacion;
        this.Tabla = Tabla;
    }

    public AFD(HashSet<Character> alfabeto, HashMap<Integer, Integer> estadosAceptacion, Table<Integer, String, Integer> Tabla) {
        this.Alfabeto = alfabeto;
        this.estadosAceptacion = estadosAceptacion;
        this.Tabla = Tabla;
    }
    
    public AFD(int idAFD,HashSet<Character> alfabeto, HashMap<Integer, Integer> estadosAceptacion, Table<Integer, String, Integer> Tabla) {
        this.idAFD = idAFD;
        this.Alfabeto = alfabeto;
        this.estadosAceptacion = estadosAceptacion;
        this.Tabla = Tabla;
    }

    public AFD(String nombreArchivo) throws IOException, ClassNotFoundException {
        this.nombreArchivo = nombreArchivo;
        this.Alfabeto = new HashSet<>();
        this.recuperarObjetoAFD(nombreArchivo);
    }

    public AFD(String nombreArchivo, int idAFD) throws IOException, ClassNotFoundException {
        this.nombreArchivo = nombreArchivo;
        this.Alfabeto = new HashSet<>();
        this.recuperarObjetoAFD(nombreArchivo);
        this.idAFD = idAFD;
    }

    public void crearArchivo(String ruta){
        try {
            File archivo = new File(CARPETA+ruta);
            String texto = this.generarTexto();

            if(!archivo.exists()){
                archivo.getParentFile().mkdir();
                archivo.createNewFile();
            }

            FileWriter fw = new FileWriter(archivo);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(texto);
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String generarTexto(){
        String texto = "", separador = "\t";
        //Guarda el alfabeto
        for (char simbolo : getAlfabeto())
            texto = texto.concat(simbolo + separador);
        texto = texto.concat("\n");

        for(Integer idEstado : this.Tabla.rowKeySet()){
            for(String simbolo : this.Tabla.columnKeySet()){
                //Obtener valor de la tabla y convertir en String
                String transicion = Objects.requireNonNull(this.Tabla.get(idEstado, simbolo)).toString();
                texto = texto.concat(transicion + separador);
            }
            //Obtener token y convertir en String
            String token = "-1";
            if(this.getEstadosAceptacion().get(idEstado) != null)
                token = this.getEstadosAceptacion().get(idEstado).toString();
            texto = texto.concat(token + "\n");
        }
        return texto;
    }

    public String leerArchivo(){
        String texto  = "";
        try {
            Scanner input = new Scanner(new File(nombreArchivo));
            while (input.hasNextLine()) {
                texto = texto.concat(input.nextLine()+"\n");
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return texto;
    }

    public void guardarObjetoAFD(String nombreArchivo) throws IOException{
        String path = String.format("%s%s.afd", CARPETA,nombreArchivo);
        FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
        ObjectOutputStream objectOutputStreamAFD = new ObjectOutputStream(fileOutputStream);

        objectOutputStreamAFD.writeObject(this);
        fileOutputStream.flush();
        objectOutputStreamAFD.flush();
        objectOutputStreamAFD.close();
        fileOutputStream.close();
    }

    public AFD recuperarObjetoAFD(String nombreArchivo) throws ClassNotFoundException, IOException{
        String path = String.format("%s%s.afd", CARPETA,nombreArchivo);
        //log.info(path);
        // Recuperación del objeto
        FileInputStream fileInputStream = new FileInputStream(new File(path));
        ObjectInputStream objectInputStreamAFD = new ObjectInputStream(fileInputStream);

        // Read objects
        AFD afdRecuperado = (AFD) objectInputStreamAFD.readObject();


        objectInputStreamAFD.close();
        fileInputStream.close();
        
        return afdRecuperado;
    }

    public AFD recuperarObjetoAFD() throws ClassNotFoundException, IOException{
        String path = String.format("%s%s.afd", CARPETA,this.nombreArchivo);
        //log.info(path);
        // Recuperación del objeto
        FileInputStream fileInputStream = new FileInputStream(new File(path));
        ObjectInputStream objectInputStreamAFD = new ObjectInputStream(fileInputStream);

        // Read objects
        AFD afdRecuperado = (AFD) objectInputStreamAFD.readObject();

        objectInputStreamAFD.close();
        fileInputStream.close();
        
        return afdRecuperado;
    }
}
