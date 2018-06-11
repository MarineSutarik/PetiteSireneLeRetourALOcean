/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.plongee.cours.service;

import api.plongee.cours.service.GestionCours;
import api.plongee.cours.domain.Cours;
import api.plongee.cours.domain.Creneau;
import api.plongee.cours.domain.GeoPoint2D;
import api.plongee.cours.domain.Membre;

import api.plongee.cours.domain.Participant;
import api.plongee.cours.domain.Piscine;
import api.plongee.cours.exception.CoursIntrouvableException;
import api.plongee.cours.exception.CoursTropRemplisException;
import api.plongee.membre.exception.MembreIntrouvableException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import api.plongee.cours.repository.CreneauRepository;
import api.plongee.cours.repository.CoursRepo;
import api.plongee.cours.repository.ParticipantRepo;
import api.plongee.cours.repository.PiscineRepo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Marine
 */
@Service 
public class GestionCoursImpl implements GestionCours{

    @Autowired
    CoursRepo coursRepo;
    
    @Autowired
    CreneauRepository creneauRepo;

    
    @Autowired
    ParticipantRepo participantRepo;
    
     @Autowired
    PiscineRepo piscineRepo;
    
    /**
     *
     * @param nomCours
     * @param niveauCible
     * @param dateDebut
     * @param duree
     * @param enseignant
     * @return
     */
    @Override
    public Cours creerCours(String nomCours, Integer niveauCible, Date dateDebut, Integer duree,  Integer enseignant, String idPiscine) {
        Creneau creneau = new Creneau(dateDebut, duree);
        creneau=creneauRepo.save(creneau);
        Piscine p = piscineRepo.findOne(idPiscine);
        Cours c = new Cours (nomCours, niveauCible,creneau, enseignant, new Participant[4],p );
        Cours insert = coursRepo.save(c);
        return insert;
    }

    @Override
    public Cours participerCours(String idCours, Integer idMembre) throws MembreIntrouvableException,CoursIntrouvableException, CoursTropRemplisException {
        Membre m = null;
               try {

		URL url = new URL("http://localhost:8080/api/membre/afficher/"+idMembre);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		//conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		
		
		JSONObject  json =  new JSONObject (br.readLine());
                 m = new Membre(json.getInt("idMembre"), json.getString("nom"), json.getString("prenom"));
		conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  } 
        
       
       Cours c = coursRepo.findOne(idCours);
       if (c==null) throw new CoursIntrouvableException();
       Participant p = new Participant(m.getIdMembre(), m.getNom(), m.getPrenom());
       c.addParticipant(p);
       coursRepo.save(c);
       participantRepo.save(p);
       return c;
    }

    @Override
    public List<Cours> afficherCours(Integer idMembre) throws MembreIntrouvableException, CoursIntrouvableException {
      Membre m = null;
      String type ="";
               try {

		URL url = new URL("http://localhost:8080/api/membre/afficher/"+idMembre);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		//conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		
		
		JSONObject  json =  new JSONObject (br.readLine());
                 m = new Membre(json.getInt("idMembre"), json.getString("nom"), json.getString("prenom"));
                 
                  url = new URL("http://localhost:8080/api/membre/type/"+idMembre);
		 conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		//conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		 br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		
		
		  type =  br.readLine();
		conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  } 
        if (m==null) throw new MembreIntrouvableException();
        List<Cours> c  = null;
        if ( type.equals("Enseignant")){
            
            Participant p = participantRepo.findOne(idMembre);
            c = coursRepo.findAllByParticipants(participantRepo.findOne(idMembre));
            c.addAll(coursRepo.findAllByEnseignant(idMembre));
        if(c==null)throw new CoursIntrouvableException();
        }else{
        Participant p = participantRepo.findOne(idMembre);
        c = coursRepo.findAllByParticipants(participantRepo.findOne(idMembre));
        if(c==null)throw new CoursIntrouvableException();
        }
        return c;
    }

    @Override
    public void supprimerCours(String idCours) throws CoursIntrouvableException {
        Cours c = this.coursRepo.findOne(idCours);
        if (c==null) throw new CoursIntrouvableException();
        else this.coursRepo.delete(c);
    }

    @Override
    public List<Piscine> recupererPiscines() {
        List<Piscine> l = new ArrayList<Piscine>();        
        try {

		URL url = new URL("https://data.toulouse-metropole.fr/api/records/1.0/search/?dataset=piscines");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		
		
		JSONObject json =  new JSONObject(br.readLine());
                Piscine p = null;
                 GeoPoint2D g = null;
                 JSONObject geometrie = null;
                 JSONObject fields = null;
                JSONArray records = (JSONArray) json.get("records");
                for (int i = 0 ; i <records.length();i++){
                    json = (JSONObject) records.get(i);
                   
                    geometrie = json.getJSONObject("geometry");
                    g = new GeoPoint2D(((JSONArray)geometrie.get("coordinates")).getInt(0),((JSONArray)geometrie.get("coordinates")).getInt(1));
                    fields = (JSONObject) json.get("fields");
                    p = new Piscine(fields.getString("nom_complet"), fields.getString("adresse"), fields.getString("telephone"), g, fields.getString("saison"));
                    l.add(p);
                    piscineRepo.save(p);
                }
                
		conn.disconnect();

	  } catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	  }
        return l;
    }

    @Override
    public List<Piscine> afficherPiscines() {
        return piscineRepo.findAll();
    }

    @Override
    public long nombreDeCoursPositionnes() {
        return coursRepo.count();
    }
    
}
