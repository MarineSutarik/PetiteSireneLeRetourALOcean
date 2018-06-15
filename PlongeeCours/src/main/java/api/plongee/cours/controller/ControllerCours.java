/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.plongee.cours.controller;

import api.plongee.cours.domain.Cours;
import api.plongee.cours.domain.Piscine;
import api.plongee.cours.exception.CoursIntrouvableException;
import api.plongee.cours.exception.CoursTropRemplisException;
import api.plongee.membre.exception.MembreIntrouvableException;
import api.plongee.cours.service.GestionCours;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/api/cours")
@CrossOrigin(origins = "http://localhost:8082")
public class ControllerCours {
    @Autowired
    private GestionCours gestionCours;
    
    
    /*
    Pour creer un cours
    
    Requête de test, sachant qu'il faut mettre dans un piscine, un id d'une piscine crée :
    
    {
    nomCours:"vidage de masque",
    niveauCible :"1",
    dateDebut : "26/05/18",
    duree : "40",
    enseignant:"4",
    piscine:"..."
    
    }
    
    */
    @PostMapping("/creation")
    @ResponseBody
    public Cours creerCours(@RequestBody String js
           
    ) throws  ParseException{
        JSONObject jsonObj = new JSONObject(js);
               String nomCours = jsonObj.getString("nomCours");
              Integer niveauCible= Integer.parseInt(jsonObj.getString("niveauCible"));
              String dateDebut= jsonObj.getString("dateDebut");
             Integer duree= Integer.parseInt(jsonObj.getString("duree"));
              Integer enseignant= Integer.parseInt(jsonObj.getString("enseignant"));
               String piscine= jsonObj.getString("piscine");
         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        Date d = sdf.parse(dateDebut);
        return gestionCours.creerCours(nomCours, niveauCible, d, duree, enseignant,piscine);
    }
    /**
     * Permet de participer à un cours
     * @param idCours
     * @param idMembre
     * @return
     * @throws MembreIntrouvableException
     * @throws CoursIntrouvableException
     * @throws CoursTropRemplisException 
     */
    @PutMapping("/participation/{idMembre}")
    @ResponseBody
    public Cours participerCours(@RequestBody String idCours,@PathVariable("idMembre") Integer idMembre) throws MembreIntrouvableException, CoursIntrouvableException, CoursTropRemplisException{
        return gestionCours.participerCours( idCours, idMembre);
    }
           
    /**
     * Permet d'afficher un membre
     * @param idMembre
     * @return
     * @throws MembreIntrouvableException
     * @throws CoursIntrouvableException 
     */ 
    @GetMapping("/afficher/{idMembre}")
    @ResponseBody
    public List<Cours> afficherCours(@PathVariable("idMembre") Integer idMembre) throws MembreIntrouvableException,CoursIntrouvableException{
        return gestionCours.afficherCours(idMembre);
    }
    /**
     * Permet de supprimer un membre à partir de son id
     * @param idCours
     * @throws CoursIntrouvableException 
     */
    @DeleteMapping("/supprimer")
    @ResponseBody
    public void supprimer(@RequestBody String idCours) throws CoursIntrouvableException{
         gestionCours.supprimerCours(idCours);
    }
    /**
     * Permet d'afficher les différentes piscines de Toulouse sauvegardées en BD
     * @return une liste de piscine
     */
     @GetMapping("/afficherPiscines")
    @ResponseBody
    public List<Piscine> afficherPiscines()  {
         return gestionCours.afficherPiscines();
    }
}
