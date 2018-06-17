/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.plongee.membre.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author allan
 */
@Entity
@Table(name = "Paiement")
public class Paiement implements Serializable {
    @Id
    @GeneratedValue
    private Integer idPaiement ;
    private String refBancaire;
    @Column (nullable = false)
    private float montant;
    private boolean valide;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    private Integer idMembre;

    public Paiement() {
    }

    public Paiement( String refBancaire, float montant, Integer idMembre) {
        
        this.refBancaire = refBancaire;
        this.montant = montant;
        this.idMembre = idMembre;
        this.date = new Date();
        this.valide=false;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public boolean isValide() {
        return valide;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
    }

    public Integer getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(Integer idPaiement) {
        this.idPaiement = idPaiement;
    }

    public String getRefBancaire() {
        return refBancaire;
    }

    public void setRefBancaire(String refBancaire) {
        this.refBancaire = refBancaire;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public Integer getMembre() {
        return idMembre;
    }

    public void setMembre(Integer idMembre) {
        this.idMembre = idMembre;
    }
         
}
