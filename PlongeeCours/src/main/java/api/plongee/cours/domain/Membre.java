/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.plongee.cours.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author marine
 */
@Entity
@Table(name = "Membre")
public class Membre implements Serializable {

    @Id
    @GeneratedValue
    private Integer idMembre;
    private String nom;
    private String prenom;
   
    protected Membre(){}

    public Membre(Integer idMembre, String nom, String prenom) {
        this.idMembre = idMembre;
        this.nom = nom;
        this.prenom = prenom;
    }

    public Integer getIdMembre() {
        return idMembre;
    }

    public void setIdMembre(Integer idMembre) {
        this.idMembre = idMembre;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    

    
    
}
