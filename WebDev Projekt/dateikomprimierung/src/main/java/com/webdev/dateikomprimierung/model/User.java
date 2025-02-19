package com.webdev.dateikomprimierung.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer komprimierung;
    private String nachname;
    private String vorname;
    private String zugriffslink;

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getKomprimierung() {
        return komprimierung;
    }

    public void setKomprimierung(Integer komprimierung) {
        this.komprimierung = komprimierung;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getZugriffslink() {
        return zugriffslink;
    }

    public void setZugriffslink(String zugriffslink) {
        this.zugriffslink = zugriffslink;
    }
}
