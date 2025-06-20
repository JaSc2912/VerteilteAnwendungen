/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package de.hsnr.bank.usecases.Interfaces;

/**
 *
 * @author jannn
 */
public interface IKundePflegen {
    public void addKunde(String name, String vorname, String adresse, String telefonnummer);

    public void deleteKunde(int kundeId);

    public void updateKunde(int kundeId, String name, String vorname, String adresse, String telefonnummer);
}
