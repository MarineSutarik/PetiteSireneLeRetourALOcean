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
public class ControllerCours {
    @Autowired
    private GestionCours gestionCours;
    
    
    /*
    Requête de test :
    
    {
    nomCours:"vidage de masque",
    niveauCible :"1",
    dateDebut : "26/05/18",
    duree : "40",
    enseignant:"4"
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
    
    @PutMapping("/participation/{idMembre}")
    @ResponseBody
    public Cours participerCours(@RequestBody String idCours,@PathVariable("idMembre") Integer idMembre) throws MembreIntrouvableException, CoursIntrouvableException, CoursTropRemplisException{
        return gestionCours.participerCours( idCours, idMembre);
    }
           
     
    @GetMapping("/afficher/{idMembre}")
    @ResponseBody
    public List<Cours> afficherCours(@PathVariable("idMembre") Integer idMembre) throws MembreIntrouvableException,CoursIntrouvableException{
        return gestionCours.afficherCours(idMembre);
    }
    
        @DeleteMapping("/supprimer/{idCours}")
    @ResponseBody
    public void supprimer(@PathVariable("idMembre") String idCours) throws CoursIntrouvableException{
         gestionCours.supprimerCours(idCours);
    }
    
            @GetMapping("/afficherPiscines")
    @ResponseBody
    public List<Piscine> afficherPiscines()  {
         return gestionCours.afficherPiscines();
    }
}
