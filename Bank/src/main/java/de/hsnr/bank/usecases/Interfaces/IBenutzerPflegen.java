/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package de.hsnr.bank.usecases.Interfaces;

import de.hsnr.bank.usecases.RolleT;

/**
 *
 * @author jannn
 */
public interface IBenutzerPflegen {
    boolean addBenutzer(String benutzername, String name, String passwort, String telefonnummer, RolleT rolle);
    boolean deleteBenutzer(String benutzername);
    boolean editBenutzer(String benutzername);
}
