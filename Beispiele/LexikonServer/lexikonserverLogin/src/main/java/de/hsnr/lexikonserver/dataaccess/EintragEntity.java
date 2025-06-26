/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.hsnr.lexikonserver.dataaccess;

import de.hsnr.lexikonserver.core.entities.Eintrag;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 *
 * @author aschekelmann
 */
@Entity
public class EintragEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eintragid")
    private int eintragid;
    @ManyToOne //(cascade  = CascadeType.PERSIST)
    @JoinColumn(name="AUTORNR", referencedColumnName = "AUTORNR")
    private AutorEntity autorentity;
    private String begriff;
    private String definition;


    public EintragEntity () {
    }

    public EintragEntity (Eintrag eintrag) {
        this.eintragid = eintrag.getEintragid();
        this.begriff = eintrag.getBegriff();
        this.definition = eintrag.getDefinition();
        this.autorentity = new AutorEntity(eintrag.getAutor());

    }

    public int getEintragid() {
        return this.eintragid;
    }

    public String getBegriff() {
        return this.begriff;
    }

    public String getDefinition() {
        return this.definition;
    }

    public AutorEntity getAutorentity() {
        return this.autorentity;
    }

    public Eintrag toEintrag() {
        return new Eintrag(this.eintragid, this.begriff, this.definition, this.autorentity.toAutor());
    }   

    public void setEintragid(int eintragid) {
        this.eintragid = eintragid;
    }

    public void setBegriff(String begriff) {
        this.begriff = begriff;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setAutorentity(AutorEntity autorentity) {
        this.autorentity = autorentity;
    }

    
	
}
