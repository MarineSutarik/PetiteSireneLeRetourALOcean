/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.plongee.membre.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author Marine
 */
@Entity
@Table(name = "President")
public class President extends Membre{
     public President(){
         super();
     }
            
    
     public President( String nom, String prenom, String adresseMail, String login, String password, Date dateDebutCertificat, Date aPaye,  Integer niveauExpertise, String numLicence, Adresse adresse) {
        super(  nom,  prenom,  adresseMail,  login,  password,  dateDebutCertificat,  aPaye,   niveauExpertise,  numLicence,  adresse); 
    } 
}
