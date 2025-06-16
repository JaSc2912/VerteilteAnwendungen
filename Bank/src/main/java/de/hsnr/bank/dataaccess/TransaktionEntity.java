/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.dataaccess;

import jakarta.persistence.*;
import java.util.Date;


/**
 *
 * @author muell
 */
@Entity
public class TransaktionEntity  {
    @Id
    @Column(name = "TRANSAKTIONSNUMMER")
    private String Transaktionsnummer;
    @ManyToOne
    @Column(name = "BANKKONTO")
    private BankkontoEntity Bankkonto;
    @Column(name = "EMPFAENGER")
    private String Empfeanger;
    @Column(name = "BETRAG")
    private double Betrag;
    @Column(name = "TRANSAKTIONSDATUM")
    private Date Transaktionsdatum;
    @Column(name = "TRANSAKTIONSSTATUS")
    private String Transaktionsstatus;
    @Column(name = "TRANSAKTIONSART")
    private String Transaktionsart;
}

