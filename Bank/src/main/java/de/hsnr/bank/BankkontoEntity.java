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
public class BankkontoEntity  {
    
    private String IBAN;
    private String Kontoart;
    private double Kontostand;
    private Date Kontoeröffnung;
    private String Kontostatus;
    //Besitzer = Kunde
  
}
