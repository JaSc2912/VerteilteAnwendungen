/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package de.hsnr.bank.usecases;

/**
 *
 * @author jannn
 */
public interface IKreditantragVerwalten {
    public void addKreditantrag(Double kreditsumme, String konto, String laufzeit, Double zins);
    public void editKreditantrag(String id, Double kreditsumme, String konto, String laufzeit, Double zins);
    public void acceptKreditantrag(String id);
    public void denyKreditantrag(String id);
}
