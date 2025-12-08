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
import java.util.Date;

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
        OrdenDeTrabajoCorrectivo o = new OrdenDeTrabajoCorrectivo();
        OrdenDeTrabajoM o1 = new OrdenDeTrabajoM();
        o1.Eliminar(3);
        o.setId(3);
        o.setIdEquipo(3);
        o.setFechaEjecucion(new Date());
        o.setObservacionesIniciales("Wake from your sleep\n" +
                "The drying of your tears\n" +
                "Today, we escape, we escape\n" +
                "Pack and get dressed\n" +
                "Before your father hears us\n" +
                "Before all hell breaks loose\n" +
                "Breathe, keep breathing\n" +
                "Don't lose your nerve\n" +
                "Breathe, keep breathing\n" +
                "I can't do this alone\n" +
                "Sing us a song\n" +
                "A song to keep us warm\n" +
                "There's such a chill, such a chill\n" +
                "And you can laugh\n" +
                "A spineless laugh\n" +
                "We hope your rules and wisdom choke you\n" +
                "Now we are one in everlasting peace\n" +
                "We hope that you choke, that you choke x3");
        o1.Nuevo(o);
        

        SwingUtilities.invokeLater(()->{
            new MenuPrincipal().setVisible(true);
        });
    }
}
