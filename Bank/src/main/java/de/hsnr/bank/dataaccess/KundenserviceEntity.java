/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.bank.dataaccess;

import de.hsnr.bank.entities.Kundenservice;
import de.hsnr.bank.usecases.RolleT;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 *
 * @author jannn
 */
@Entity
@DiscriminatorValue("KUNDENSERVICE")
public class KundenserviceEntity extends BenutzerEntity {

    public KundenserviceEntity() {
        super();
    }

    public KundenserviceEntity(Kundenservice kundenservice) {
        super(kundenservice);
    }

    public Kundenservice toKundenservice() {
        Kundenservice kundenservice = new Kundenservice(this.benutzername, this.passwort);
        kundenservice.setName(this.name);
        kundenservice.setTelefonnummer(this.telefonnummer);
        kundenservice.setRolle(this.rolle);
        return kundenservice;
    }
}
