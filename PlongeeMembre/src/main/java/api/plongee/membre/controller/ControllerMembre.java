/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.plongee.membre.controller;

import api.plongee.membre.domain.Adresse;
import api.plongee.membre.domain.Membre;
import api.plongee.membre.enumeration.TypeMembre;
import api.plongee.membre.exception.MembreIntrouvableException;
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
 */
@RestController
@RequestMapping("/api/membre")
public class ControllerMembre {
           
    @Autowired
    private GestionMembre gestionMembre;
    
    /**
     *
     * {nom:"Marine",
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

* 
     * @param js
     * @return
     * @throws api.plongee.membre.exception.TypeMembreInvalideException
     * @throws java.text.ParseException
     */
    @PostMapping("/creation")
    @ResponseBody
    public Membre creerMembre(@RequestBody String js
           /* Ce code ne marche pas, BodyRequest ne prend que le premier paramètres en fait
            @RequestBody String nom,
             @RequestBody String prenom,
             @RequestBody String adresseMail,
            @RequestBody String login,
             @RequestBody String password,
             @RequestBody String dateDebutCertificat,
             @RequestBody Integer niveauExpertise,
             @RequestBody String numLicence,
             @RequestBody String pays,
            @RequestBody String ville,
             @RequestBody String type
    */
    ) throws TypeMembreInvalideException, ParseException{
        JSONObject jsonObj = new JSONObject(js);
                     String nom = jsonObj.getString("nom");
              String prenom= jsonObj.getString("prenom");
              String adresseMail= jsonObj.getString("adresseMail");
             String login= jsonObj.getString("login");
              String password= jsonObj.getString("password");
              String dateDebutCertificat= jsonObj.getString("dateDebutCertificat");
              String aPaye= jsonObj.getString("aPaye");
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
        Date daPaye = sdf.parse(aPaye);
        return gestionMembre.creerMembre( nom, prenom, adresseMail, login, password, d,daPaye, niveauExpertise, numLicence, pays, ville, t);
    }
    /* Pour tester la requête :
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
              String dateDebutCertificat= jsonObj.getString("dateDebutCertificat");
              
              String aPaye= jsonObj.getString("aPaye");
              Integer niveauExpertise= Integer.parseInt(jsonObj.getString("niveauExpertise"));
              String numLicence= jsonObj.getString("numLicence");
              String pays= jsonObj.getString("pays");
             String ville= jsonObj.getString("ville");
             String type= jsonObj.getString("type");
             
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        Date dc = sdf.parse(dateDebutCertificat);
        Date dp = sdf.parse(aPaye);   
        Adresse a = new Adresse(pays, ville);
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
       
        Membre m = new Membre(nom, prenom, adresseMail, login, password, dc, dp, niveauExpertise, numLicence, a);
        return this.gestionMembre.updateMembre(id, m);
        
    }
    
      @DeleteMapping("/suppression/{id}")
    public void supprimer( @PathVariable("id") Integer id) throws MembreIntrouvableException{
        this.gestionMembre.deleteMembre(id);
    }

    /**
     *
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
    
    
    @PutMapping("/paiement/{id}")
    @ResponseBody
    public void payer( @PathVariable("id") Integer id, @RequestBody String param) throws MembreIntrouvableException{
         
         JSONObject jsonObj = new JSONObject(param);
         String IBAN = jsonObj.getString("IBAN");
         float somme= Float.parseFloat(jsonObj.getString("somme"));
         this.gestionMembre.payerCotisation(IBAN, somme,id);
    }
    
    @GetMapping("/consultation")
    @ResponseBody
    public List consulter() throws MembreIntrouvableException{         
        return this.gestionMembre.consulterCotisation();
    }
    
    @GetMapping("/statistiques")
    @ResponseBody
    public Map<String,String> voirStatistiques() {         
        return this.gestionMembre.consulterStatistiques();
    }
    
    @PutMapping("/certificat/{id}")
    @ResponseBody
    public void donnerCertificat(@PathVariable("id") Integer id) throws MembreIntrouvableException {         
        this.gestionMembre.donnerCertificat(id);
    }
    
    @GetMapping("/afficher/{id}")
    @ResponseBody
    public Membre afficherMembre(@PathVariable("id") Integer id) throws MembreIntrouvableException {         
        return this.gestionMembre.afficherMembre(id);
    }
    
    @GetMapping("/type/{id}")
    @ResponseBody
    public String getType(@PathVariable("id") Integer id) throws MembreIntrouvableException {         
        return this.gestionMembre.getType(id);
    }
    

}
