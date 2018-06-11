/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.plongee.cours.domain;

import javax.persistence.OneToOne;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.Id;
/**
 *
 * @author marin
 */
@Document
public class Piscine {
    @Id
    private String id;
    private String nom;
    private String adresse;
    private String telephone;
    @OneToOne
    private GeoPoint2D position;
    private String saison;

    public Piscine(String nom, String adresse, String telephone, GeoPoint2D position, String saison) {
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.position = position;
        this.saison = saison;
    }

    public Piscine() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public GeoPoint2D getPosition() {
        return position;
    }

    public void setPosition(GeoPoint2D position) {
        this.position = position;
    }

    public String getSaison() {
        return saison;
    }

    public void setSaison(String saison) {
        this.saison = saison;
    }
    
    
}
