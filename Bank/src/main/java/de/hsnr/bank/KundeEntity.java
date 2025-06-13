/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import java.util.Date;

/**
 *
 * @author muell
 */
@Entity
@Inheritance(strategy =  InheritanceType.SINGLE_TABLE)
public class KundeEntity  {
    
    private String Kundennummer;
    private String Name;
    private String Adresse;
    private String Kundenstatus;
    private Date Geburtsdatum;
    private String Telefonnummer;
    private String Email;
}

