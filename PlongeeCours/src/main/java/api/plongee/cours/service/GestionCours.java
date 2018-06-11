/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.plongee.cours.service;

import api.plongee.cours.domain.Cours;
import api.plongee.cours.domain.Piscine;
import api.plongee.cours.exception.CoursIntrouvableException;
import api.plongee.cours.exception.CoursTropRemplisException;
import api.plongee.membre.exception.MembreIntrouvableException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Marine
 */
public interface GestionCours {
    
    public Cours creerCours(String nomCours, Integer niveauCible, Date dateDebut, Integer duree, Integer enseignant,String idPiscine);
    
    public Cours participerCours(String idCours, Integer idMembre) throws MembreIntrouvableException,CoursIntrouvableException, CoursTropRemplisException;
    
    public List<Cours> afficherCours(Integer idMembre) throws MembreIntrouvableException,CoursIntrouvableException;
    
     public void supprimerCours(String idCours) throws CoursIntrouvableException;
     
    public List<Piscine> recupererPiscines();
    
    public List<Piscine> afficherPiscines();
    
    public long nombreDeCoursPositionnes();
}
