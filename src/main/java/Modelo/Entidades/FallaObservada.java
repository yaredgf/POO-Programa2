/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.Entidades;

/**
 *
 * @author Usuario
 */
public class FallaObservada {
    private int idFalla;
    private String causa;
    private String accionesTomadas;

    public FallaObservada()
    {
        this.idFalla = -1;
        this.causa = null;
        this.accionesTomadas = null;
    }


    public FallaObservada(int idFalla, String causa, String accionesTomadas)
    {
        this.idFalla = idFalla;
        this.causa = causa;
        this.accionesTomadas = accionesTomadas;
    }


    public void setIdFalla(int idFalla)
    {
        this.idFalla = idFalla;
    }

    public int getIdFalla()
    {
        return this.idFalla;
    }
    public void setCausa(String causa)
    {
        this.causa = causa;
    }

    public String getCausa()
    {
        return this.causa;
    }
    public void setAccionesTomadas(String accionesTomadas)
    {
        this.accionesTomadas = accionesTomadas;
    }

    public String getAccionesTomadas()
    {
        return this.accionesTomadas;
    }
}
