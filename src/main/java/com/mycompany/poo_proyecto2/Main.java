/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo_proyecto2;

import Modelo.Entidades.*;
import Modelo.Metodos.*;
import Utilidades.Archivos;
import Vista.MenuPrincipal;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Main 
{
    public static void main(String args[])
    {
        System.out.println("Adios mundo");
        //XD
        
        //Archivos<ArrayList<OrdenDeTrabajoCorrectivo>> archivo = new Archivos<ArrayList<OrdenDeTrabajoCorrectivo>>() ;
        //archivo.EscribirArchivo("ordenesDeTrabajo", (new ArrayList<OrdenDeTrabajoCorrectivo>()) );
        

        SwingUtilities.invokeLater(()->{
            new MenuPrincipal().setVisible(true);
        });
    }
}
