/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.plongee.membre.controller;

import api.plongee.membre.domain.Adresse;
import api.plongee.membre.domain.Membre;
import api.plongee.membre.domain.Paiement;
import api.plongee.membre.enumeration.TypeMembre;
import api.plongee.membre.exception.MembreIntrouvableException;
import api.plongee.membre.exception.PaiementIntrouvableException;
import api.plongee.membre.exception.TypeMembreInvalideException;
import api.plongee.membre.service.GestionMembre;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Marine
 * Les différents contrôlleurs de la partie membre
 */
@RestController
@RequestMapping("/api/membre")
@CrossOrigin(origins = "http://localhost:8082")
public class ControllerMembre {
           
    @Autowired
    private GestionMembre gestionMembre;
    
    /**
     * 
     * Créer un nouveau membre et génére son id
     * 
     * Une requête de test :
     
    {nom:"Marine",
    prenom:"SUTARIK",
    adresseMail:"marine@toto.fr",
    login:"toto",
    password:"toto",
    dateDebutCertificat:"05/07/2017",
    niveauExpertise:"1",
    numLicence:"coijioj",
    pays:"France",
    ville:"Saint-Lys",
    type:"Membre" }
 
     * @param js
     * @return
     * @throws api.plongee.membre.exception.TypeMembreInvalideException
     * @throws java.text.ParseException
     */
    @PostMapping("/creation")
    @ResponseBody
    public Membre creerMembre(@RequestBody String js ) throws TypeMembreInvalideException, ParseException{
        JSONObject jsonObj = new JSONObject(js);
                     String nom = jsonObj.getString("nom");
              String prenom= jsonObj.getString("prenom");
              String adresseMail= jsonObj.getString("adresseMail");
             String login= jsonObj.getString("login");
              String password= jsonObj.getString("password");
              String dateDebutCertificat= jsonObj.getString("dateDebutCertificat");
              Integer niveauExpertise= Integer.parseInt(jsonObj.getString("niveauExpertise"));
              String numLicence= jsonObj.getString("numLicence");
              String pays= jsonObj.getString("pays");
             String ville= jsonObj.getString("ville");
             String type= jsonObj.getString("type");
        TypeMembre t = null; 
        switch (type) {
            case "Membre":
                t = TypeMembre.Membre;
                break;
            case "President" :
                t = TypeMembre.President;
                break;
            case "Secretaire" :
                t = TypeMembre.Secretaire;
                break;
            case "Enseignant" :
                t = TypeMembre.Enseignant;
                break;
             default :
                throw new TypeMembreInvalideException();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        Date d = sdf.parse(dateDebutCertificat);
        return gestionMembre.creerMembre( nom, prenom, adresseMail, login, password, d,null, niveauExpertise, numLicence, pays, ville, t);
    }
    /*
    Modifie un membre et l'enregistre
    
    Pour tester la requête :
    
         {nom:"Toto",
    prenom:"SUTARIK",
    adresseMail:"marine@toto.fr",
    login:"toto",
    password:"toto",
    dateDebutCertificat:"05/07/2017",
 aPaye:"05/07/2017",
    niveauExpertise:"1",
    numLicence:"coijioj",
    pays:"France",
    ville:"Saint-Lys",
    type:"Membre" }
    */
    
    /**
     *
     * @param param
     * @param id
     * @return 
     * @throws TypeMembreInvalideException
     * @throws ParseException
     * @throws api.plongee.membre.exception.MembreIntrouvableException
     */
    @PutMapping("/modification/{id}")
    @ResponseBody
    public Membre modifier(@RequestBody String param, @PathVariable("id") Integer id) throws TypeMembreInvalideException, ParseException, MembreIntrouvableException{
        
        JSONObject jsonObj = new JSONObject(param);
                     String nom = jsonObj.getString("nom");
              String prenom= jsonObj.getString("prenom");
              String adresseMail= jsonObj.getString("adresseMail");
             String login= jsonObj.getString("login");
              String password= jsonObj.getString("password");
              Integer niveauExpertise= Integer.parseInt(jsonObj.getString("niveauExpertise"));
              String numLicence= jsonObj.getString("numLicence");
              String pays= jsonObj.getString("pays");
             String ville= jsonObj.getString("ville");
             
           
        Adresse a = new Adresse(pays, ville);
       
       
        Membre m = new Membre(nom, prenom, adresseMail, login, password, niveauExpertise, numLicence, a);
        return this.gestionMembre.updateMembre(id, m,pays,ville);
        
    }
    /**
     * supprime un id
     * @param id
     * @throws MembreIntrouvableException 
     */
      @DeleteMapping("/suppression/{id}")
    public void supprimer( @PathVariable("id") Integer id) throws MembreIntrouvableException{
        this.gestionMembre.deleteMembre(id);
    }

    /**
     * Permet de tester le mot de passe et le login pour qu'un membre se connecte. Renvois l'objet du membre.
     * @param param
     * @return
     * @throws MembreIntrouvableException
     */
    @PutMapping("/connexion")
    @ResponseBody
    public Membre connexion( @RequestBody String param) throws MembreIntrouvableException{
         
         JSONObject jsonObj = new JSONObject(param);
         String login = jsonObj.getString("login");
         String password= jsonObj.getString("password");
        return this.gestionMembre.seconnecter(login, password);
    }
    
    /**
     * Permet de valider le paiement d'un membre
     * @param id
     * @param param
     * @throws MembreIntrouvableException 
     */
    @PutMapping("/paiement/{id}")
    @ResponseBody
    public void payer( @PathVariable("id") Integer id, @RequestBody String param) throws MembreIntrouvableException{
         
         JSONObject jsonObj = new JSONObject(param);
         String IBAN = jsonObj.getString("IBAN");
         float somme= Float.parseFloat(jsonObj.getString("somme"));
         this.gestionMembre.payerCotisation(IBAN, somme,id);
    }
    /**
     * Permet de returner une liste de membre, la partie front s'occuper d'afficher les informations utiles
     * @return
     * @throws MembreIntrouvableException 
     */
    @GetMapping("/consultation")
    @ResponseBody
    public List consulter() throws MembreIntrouvableException{         
        return this.gestionMembre.consulterCotisation();
    }
    /**
     * affiche les statistisques
     * @return 
     */
    @GetMapping("/statistiques")
    @ResponseBody
    public Map<String,String> voirStatistiques() {         
        return this.gestionMembre.consulterStatistiques();
    }
    /**
     * permet de donner un certificat pour un membre
     * @param id
     * @throws MembreIntrouvableException 
     */
    @PutMapping("/certificat/{id}")
    @ResponseBody
    public void donnerCertificat(@PathVariable("id") Integer id) throws MembreIntrouvableException {         
        this.gestionMembre.donnerCertificat(id);
    }
    /**
     * affiche un membre pour son id
     * @param id
     * @return
     * @throws MembreIntrouvableException 
     */
    @GetMapping("/afficher/{id}")
    @ResponseBody
    public Membre afficherMembre(@PathVariable("id") Integer id) throws MembreIntrouvableException {         
        return this.gestionMembre.afficherMembre(id);
    }
    /**
     * return le type d'un membre à partir de son id ( si c'est un enseignant un président ou un simple membre
     * @param id
     * @return
     * @throws api.plongee.membre.exception.PaiementIntrouvableException 
     */
    @PutMapping("/valider/{id}")
    @ResponseBody
    public Paiement valider(@PathVariable("id") Integer id) throws  PaiementIntrouvableException {         
        return this.gestionMembre.validerPaiement(id);
    }
    @GetMapping("/listePaiement")
    @ResponseBody
    public List<Paiement> getListePaiement()  {         
        return this.gestionMembre.afficherPaiementNonValides();
    }

}
