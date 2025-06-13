/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.dataaccess;

import de.hsnr.bank.dataaccess.BankkontoEntity;
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
public class TransaktionEntity  {
    
    private BankkontoEntity Bankkonto;
    private String Transaktionsnummer;
    private String Empfeanger;
    private double Betrag;
    private Date Transaktionsdatum;
    private String Transaktionsstatus;
    private String Transaktionsart;
}

