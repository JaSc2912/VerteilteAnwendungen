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
public class BankkontoEntity  {
    
    private String iban;
    private String kontoart;
    private double kontostand;
    private Date kontoeröffnung;
    private String kontostatus;
    private KundeEntity kunde;
  
}
