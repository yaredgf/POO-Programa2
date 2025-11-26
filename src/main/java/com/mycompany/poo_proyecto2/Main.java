/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.poo_proyecto2;

import Vista.MenuPrincipal;

import javax.swing.*;

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

        SwingUtilities.invokeLater(()->{
            new MenuPrincipal().setVisible(true);
        });
    
    
    }
}
