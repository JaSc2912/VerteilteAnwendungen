/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.dataaccess;

import jakarta.persistence.Entity;
import java.util.Date;

/**
 *
 * @author muell
 */
@Entity
public class KundeEntity  {
    
    private String kundennummer;
    private String name;
    private String adresse;
    private String kundenstatus;
    private Date geburtsdatum;
    private String telefonnummer;
    private String email;
}

