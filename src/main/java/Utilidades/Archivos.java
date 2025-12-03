package Utilidades;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Archivos<T> {
    /**
     * Lee un archivo binario y retorna el objeto que lea del archivo.
     * Si no existe el archivo que se intenta leer, se dará opción de crearlo.
     * @param nombre Nombre del archivo. Si es nulo, se lanzará un dialog de error y no se hará nada.
     * @return Null si falló la apertura del archivo. Un Objeto si tiene éxito.
     */
    public T LeerArchivo(String nombre) {
        try {
            File archivo = new File("datos/Mant_" + nombre + ".bin");
            if (archivo.exists()) {
                FileInputStream fis = new FileInputStream(archivo);
                ObjectInputStream ois = new ObjectInputStream(fis);
                T objeto = (T)ois.readObject();
                ois.close();
                fis.close();
                return objeto;
            } else if (JOptionPane.showConfirmDialog((Component)null, "El archivo de nombre \"Mant_" + nombre + ".bin\" no existe, ¿Desea crearlo?", "Advertencia", 0) == 0) {
                archivo.createNewFile();
                return (T)this.LeerArchivo(nombre);
            } else {
                JOptionPane.showMessageDialog((Component)null, "Error al escribir en el archivo: No existe.", "Error", 0);
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog((Component)null, "Error al abrir el archivo: " + e.getMessage(), "Error", 0);
            return null;
        }
    }

    /**
     * Escribe en un archivo binario un objeto dado.
     * @param nombre Nombre del archivo. Si es nulo, se lanzará un dialog de error y no se hará nada.
     * @param objeto Objeto que se escribirá en el archivo binario.
     * @return Booleano que determina si hubo éxito o no.
     */
    public boolean EscribirArchivo(String nombre, T objeto) {
        try {
            File archivo = new File("datos/Mant_" + nombre + ".bin");
            if (archivo.exists()) {
                FileOutputStream fos = new FileOutputStream(archivo);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(objeto);
                oos.close();
                fos.close();
                return true;
            } else if (JOptionPane.showConfirmDialog((Component)null, "El archivo de nombre \"Mant_" + nombre + ".bin\" no existe, ¿Desea crearlo?", "Advertencia", 0) == 0) {
                archivo.createNewFile();
                return this.EscribirArchivo(nombre, objeto);
            } else {
                JOptionPane.showMessageDialog((Component)null, "Error al escribir en el archivo: No existe.", "Error", 0);
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog((Component)null, "Error al escribir en el archivo: " + e.getMessage() + "\n" + e.toString(), "Error", 0);
            return false;
        }
    }
}
