/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.entities;
import de.hsnr.bank.entities.enums.RolleT;
/**
 *
 * @author muell
 */
public class Kundenservice extends Benutzer {

    public Kundenservice(String benutzername, String passwort) {
        super(benutzername, passwort);
    }

    @Override
    public RolleT getRolle() {
        return RolleT.KUNDENSERVICE;
    }

    @Override
    public String toString() {
        return "Kundenservice{" + "benutzername=" + getBenutzername() + '}';
    }
    
}
