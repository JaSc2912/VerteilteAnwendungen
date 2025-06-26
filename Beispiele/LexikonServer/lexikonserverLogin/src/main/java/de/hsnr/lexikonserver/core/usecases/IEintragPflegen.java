/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package de.hsnr.lexikonserver.core.usecases;

import de.hsnr.lexikonserver.core.entities.Eintrag;
import jakarta.ejb.Local;

/**
 *
 * @author aschekelmann
 */
@Local
public interface IEintragPflegen {
    public Eintrag eintragHinzufuegen (String begriff, String definition, int autorid);
    public boolean eintragEntfernen (int eintragid);
    public boolean eintragAendern (int id, String begriff, String definition, int autorid);
}
