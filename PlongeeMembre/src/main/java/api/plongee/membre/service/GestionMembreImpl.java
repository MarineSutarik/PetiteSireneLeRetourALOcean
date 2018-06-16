/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.plongee.membre.service;

import api.plongee.membre.domain.Adresse;
import api.plongee.membre.domain.Enseignant;
import api.plongee.membre.domain.Membre;
import api.plongee.membre.domain.President;
import api.plongee.membre.domain.Secretaire;
import api.plongee.membre.enumeration.TypeMembre;
import static api.plongee.membre.enumeration.TypeMembre.*;
import api.plongee.membre.domain.Paiement;
import api.plongee.membre.exception.MembreIntrouvableException;
import api.plongee.membre.repo.AdresseRepo;
import api.plongee.membre.repo.EnseignantRepo;
import api.plongee.membre.repo.MembreRepo;
import api.plongee.membre.repo.PaiementRepo;
import api.plongee.membre.repo.PresidentRepo;
import api.plongee.membre.repo.SecretaireRepo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jdk.nashorn.internal.objects.NativeString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marine
 */
@Service 
public class GestionMembreImpl  implements GestionMembre{
    @Autowired
    private MembreRepo membreRepo;
    
    @Autowired
    private AdresseRepo adresse;
    
    @Autowired
    private PaiementRepo paiement;
    
    @Autowired
    private EnseignantRepo enseignant;
@Autowired
    private SecretaireRepo secretaire;
 @Autowired
    private PresidentRepo president; 
 /**
  * 
  * @param nom
  * @param prenom
  * @param adresseMail
  * @param login
  * @param password
  * @param dateDebutCertificat
  * @param aPaye
  * @param niveauExpertise
  * @param numLicence
  * @param pays
  * @param ville
  * @param type
  * creer un membre 
  * @return le membre créé avec un id généré
  */
    @Override
 
    public Membre creerMembre(String nom, String prenom, String adresseMail, String login, String password, Date dateDebutCertificat, Date aPaye, Integer niveauExpertise, String numLicence, String pays, String ville, TypeMembre type) {
      Adresse a = new Adresse( pays, ville);
        a = adresse.save(a);
        Calendar cal = Calendar.getInstance(); 
        cal.set(1900, 1, 1, 1, 1);
        if (aPaye == null ) aPaye = cal.getTime();
        if (dateDebutCertificat == null ) aPaye = cal.getTime();
         Membre m = null;
        switch (type){
            case Membre :
                 m = new Membre(nom, prenom, adresseMail, login,password, dateDebutCertificat, aPaye,  niveauExpertise, numLicence, a);
                 m  = membreRepo.save(m);
                 break;
            case Secretaire :
                 Secretaire s  = new Secretaire(nom, prenom, adresseMail, login,password, dateDebutCertificat, aPaye,  niveauExpertise, numLicence, a);
                 m  = secretaire.save(s);
                 
                 break;
            case President :
               President p = new President(nom, prenom, adresseMail, login,password, dateDebutCertificat, aPaye,  niveauExpertise, numLicence, a);
                m  = president.save(p);
                 break;
            case Enseignant :
                 Enseignant e = new Enseignant(nom, prenom, adresseMail, login,password, dateDebutCertificat, aPaye,  niveauExpertise, numLicence, a);
                 m  = enseignant.save(e);
                 break;
                 
        }
        
        
        return m;
    }
/**
 * modifie un membre
 * @param idMembre
 * @param m
 * @return un membre
 * @throws MembreIntrouvableException 
 */
    @Override
    public Membre updateMembre(Integer idMembre, Membre m)  throws MembreIntrouvableException {
        System.out.println("id = "+idMembre);
        Membre membreActuel = this.membreRepo.getOne(idMembre);
        if (membreActuel==null) throw new MembreIntrouvableException();
        membreActuel.getAdresse().setPays(m.getAdresse().getPays());
        membreActuel.getAdresse().setPays(m.getAdresse().getVille());
        membreActuel.setAdresseMail(m.getAdresseMail());
        membreActuel.setLogin(m.getLogin());
        membreActuel.setNom(m.getNom());
        membreActuel.setNumLicence(m.getNumLicence());
        membreActuel.setPassword(m.getPassword());
        membreActuel.setPrenom(m.getPrenom());
        membreActuel.setaPaye(m.getAPaye()); 
        return this.membreRepo.save(membreActuel);
    }
/**
 * 
 * supprime un membre
 * @param idMembre  
 * @throws MembreIntrouvableException 
 */
    @Override
    public void deleteMembre(Integer idMembre) throws MembreIntrouvableException {
        Membre membreActuel = this.membreRepo.getOne(idMembre);
        if (membreActuel==null) throw new MembreIntrouvableException();
        else this.membreRepo.delete(membreActuel);
    }
/**
 * Permet à un membre de se connecter
 * @param login
 * @param password
 * @return un membre
 * @throws MembreIntrouvableException 
 */
    @Override
    public Membre seconnecter(String login, String password) throws MembreIntrouvableException {
        Membre m =  membreRepo.findMembreByLogin(login);
        if (!m.getPassword().equals(password.trim()) || m ==null )
            throw new MembreIntrouvableException();
        
        return m;
    }
/**
 * paye la cotisation d'un membre, plus précisement créer un objet Paiement et actualise la date du dernier paiement
 * @param IBAN
 * @param somme
 * @param idMembre
 * @throws MembreIntrouvableException 
 */
    @Override
    public void payerCotisation(String IBAN, float somme,Integer idMembre) throws MembreIntrouvableException {
         Membre m = this.membreRepo.getOne(idMembre);
        if (m==null) throw new MembreIntrouvableException();
        Paiement p = new Paiement(IBAN,somme, m.getIdMembre());
        paiement.save(p);
       
        m.setaPaye(Calendar.getInstance().getTime());
        membreRepo.save(m);
    }
/**
 * afficher tous les membres, ainsi que la date de leur cotisations
 * @return une liste de membre
 */
    @Override
    public List<Membre> consulterCotisation() {
        ArrayList<Membre> r = new   ArrayList<Membre> ();
        for( Membre m : membreRepo.findAll()){
            r.add(m);
        }
        return r;
    }

    @Override
    public Map<String, String> consulterStatistiques() {
        HashMap<String,String> h = new HashMap<String,String>();
    
   //nombre de membres
    String k = "Nombre de membre(s)";
    String v = membreRepo.count()+" membre(s)";
    h.put(k, v);
    
    //nombre d'enseignants
     k = "Nombre d'enseignants";
     v = enseignant.count()+" enseignant(s)";
    h.put(k, v);
    
     //nombre de cours positionnés
     k = "Nombre de cours positionnés";
     v =+ // gestionCours.nombreDeCoursPositionnes()
             0+
             " cour(s)";
    h.put(k, v);   
    
    //nombre de cotisations prévues
     k = "Nombre  de cotisations prévues";
    Calendar cal =  Calendar.getInstance();
    cal.set( Calendar.getInstance().get(Calendar.YEAR), 01, 01, 0, 1);
     v =membreRepo.countByAPayeLessThan(cal.getTime())+" cotisation(s)";
    h.put(k, v);
    
    
    //nombre de cotisations réglées 
     k = "Nombre de cotisations réglées";
     v =membreRepo.countByAPayeGreaterThan(cal.getTime())+" cotisation(s)";
    h.put(k, v);
    
    return h;
        
    }
    /**
     * Met à jour la date à laquel le membre a donné un certificat
     * @param idMembre
     * @throws MembreIntrouvableException 
     */
    @Override
    public void donnerCertificat(Integer idMembre) throws MembreIntrouvableException{
        Membre m = membreRepo.getOne(idMembre);
        m.setDateDebutCertificat(Calendar.getInstance().getTime());
        membreRepo.save(m);
    }

    /***
     * affihcher un membre à partir de son idMembre
     * @param idMembre
     * @return
     * @throws MembreIntrouvableException 
     */
    @Override
    public Membre afficherMembre(Integer idMembre ) throws MembreIntrouvableException{
        Membre m =  this.membreRepo.getOne(idMembre);
        if (m==null) throw new MembreIntrouvableException();
        return m;
    }

    /**
     * retourne le type d'un idMembre 
     * @param idMembre
     * @return
     * @throws MembreIntrouvableException 
     */
    @Override
    public String getType(Integer idMembre) throws MembreIntrouvableException {
        Membre m =  this.membreRepo.findOne(idMembre);
        if(m==null) throw new MembreIntrouvableException();
      return m.getClass().getSimpleName().split("_")[0];
    }

  
    
}
