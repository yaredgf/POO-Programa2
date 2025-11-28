package Utilidades;

import javax.swing.*;
import java.io.*;

public class Archivos<T> {
    private <T> T CrearInstancia(Class<T> clase){
        try{
            return clase.getDeclaredConstructor().newInstance();
        }catch(Exception e){
            System.out.println("Error al crear instancia de la clase, llame a Dios: "+e.getMessage());
            return null;
        }
    }
    /**
     * Lee un archivo binario y retorna el objeto que lea del archivo.
     * Si no existe el archivo que se intenta leer, se dará opción de crearlo.
     * @param nombre Nombre del archivo. Si es nulo, se lanzará un dialog de error y no se hará nada.
     * @return Null si falló la apertura del archivo. Un Objeto si tiene éxito.
     */
    public T LeerArchivo(String nombre){
        try{
            File archivo = new File("datos/Mant_"+nombre+".bin"); //Añade la extensión y estandarización para mayor compatibilidad.
            if(archivo.exists()){ //Si existe, haga las operaciones correspondientes
                FileInputStream fis = new FileInputStream(archivo);
                ObjectInputStream ois = new ObjectInputStream(fis);
                T objeto = (T)ois.readObject(); //Lee el objeto. Hace el cast directamente al objeto T.
                //Cierra los streams
                ois.close();
                fis.close();
                return objeto;
            }else{
                if(JOptionPane.showConfirmDialog(null, "El archivo de nombre \"Mant_"+nombre+".bin\" no existe, ¿Desea crearlo?", "Advertencia", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                    archivo.createNewFile();
                    return LeerArchivo(nombre);
                }
                else{
                    JOptionPane.showMessageDialog  (null,"Error al escribir en el archivo: No existe.","Error",JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog  (null,"Error al abrir el archivo: "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Escribe en un archivo binario un objeto dado.
     * @param nombre Nombre del archivo. Si es nulo, se lanzará un dialog de error y no se hará nada.
     * @param objeto Objeto que se escribirá en el archivo binario.
     * @return Booleano que determina si hubo éxito o no.
     */
    public boolean EscribirArchivo(String nombre, T objeto){
        try{
            File archivo = new File("datos/Mant_"+nombre+".bin"); //Añade la extensión y estandarización para mayor compatibilidad.
            if(archivo.exists()){ //Si existe, haga las operaciones correspondientes
                FileOutputStream fos = new FileOutputStream(archivo);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(objeto); //Escribe en el archivo el objeto.
                //Cierra los streams
                oos.close();
                fos.close();
                return true;
            }else{
                if(JOptionPane.showConfirmDialog(null, "El archivo de nombre \"Mant_"+nombre+".bin\" no existe, ¿Desea crearlo?", "Advertencia", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                    archivo.createNewFile();
                    return EscribirArchivo(nombre, objeto);
                }
                else{
                    JOptionPane.showMessageDialog  (null,"Error al escribir en el archivo: No existe.","Error",JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog  (null,"Error al escribir en el archivo: "+e.getMessage()+"\n"+e.toString(),"Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
