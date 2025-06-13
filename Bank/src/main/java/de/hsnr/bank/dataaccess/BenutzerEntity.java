/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.dataaccess;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class BenutzerEntity {

    @Column(name = "BENUTZERKENNUNG")
    @Id
    private String benutzername;
    @Column(name = "PASSWORT")
    private String passwort;
    @Column(name = "NAME")
    String name;
    @Column(name = "TELEFONNUMMER")
    private String telefonnummer;
    @Column(name = "ROLLE")
    String rolle;
}
