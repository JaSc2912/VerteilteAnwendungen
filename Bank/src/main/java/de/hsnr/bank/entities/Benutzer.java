/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.entities;
import de.hsnr.bank.usecases.RolleT;
/**
 *
 * @author muell
 */
public class Benutzer {
    private String benutzername;
    private String passwort;
    private String name;
    private String telefonnummer;
    private RolleT rolle;
   
    public Benutzer(){
    }
    
    public Benutzer(String benutzername, String passwort, String name, String telefonnummer, RolleT rolle){
        this.benutzername = benutzername;
        this.name = name;
        this.telefonnummer = telefonnummer;
        this.rolle = rolle;
    }
    
    public String getBenutzername(){
        return benutzername;
    }
    
    public String getName(){
        return name;
    }
    
    public String getTelefonnummer(){
        return telefonnummer;
    }
    
    public RolleT getRolle(){
        return rolle;
    }
    
    public void setBenutzername(String Benutzername){
        this.benutzername = benutzername;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setTelefonnummer(String telefonnummer){
        this.telefonnummer = telefonnummer;
    }
}
