/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.plongee.cours.domain;

import api.plongee.cours.exception.CoursTropRemplisException;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Marine
 */

@Document(collection="cours")
public class Cours implements Serializable {
    @Id
    private String id;
    private String nomCours ;
    private Integer niveauCible ;
    @OneToOne
    private Creneau creneau;
    private Integer enseignant;
    @OneToOne
    private Piscine piscine;
    private Participant[] participants ;
    
    public Cours(String nomCours, Integer niveauCible, Creneau creneau, Integer enseignant, Participant[] participants,Piscine piscine) {
        this.nomCours = nomCours;
        this.niveauCible = niveauCible;
        this.creneau = creneau;
        this.enseignant = enseignant;
        this.participants = participants;
    }

    public Cours() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Piscine getPiscine() {
        return piscine;
    }

    public void setPiscine(Piscine piscine) {
        this.piscine = piscine;
    }

    public String getIdCours() {
        return id;
    }

    public void setIdCours(String idCours) {
        this.id = idCours;
    }

    public String getNomCours() {
        return nomCours;
    }

    public void setNomCours(String nomCours) {
        this.nomCours = nomCours;
    }

    public Integer getNiveauCible() {
        return niveauCible;
    }

    public void setNiveauCible(Integer niveauCible) {
        this.niveauCible = niveauCible;
    }

    public Creneau getCreneau() {
        return creneau;
    }

    public void setCreneau(Creneau creneau) {
        this.creneau = creneau;
    }

    public Integer getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Integer enseignant) {
        this.enseignant = enseignant;
    }

    public Participant[]  getParticipants() {
        return participants;
    }

    public void setParticipants(Participant[]  participants) {
        this.participants = participants;
    }
    
     public void addParticipant(Participant  participant) throws CoursTropRemplisException {
        boolean trouve = false;
         for(int i =0;i <participants.length && !trouve;i++){
            if (participants[i] == null){
                participants[i] = participant;
                trouve = true;
            }
        }
        if (!trouve) throw new CoursTropRemplisException();
    }
       
}
