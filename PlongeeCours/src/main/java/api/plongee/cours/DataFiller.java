/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.plongee.cours;

import api.plongee.cours.domain.Cours;
import api.plongee.cours.domain.Piscine;
import api.plongee.cours.service.GestionCours;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Pour remplir la BD
 * @author marin
 */
@Component
public class DataFiller implements CommandLineRunner{

     
     @Autowired
     GestionCours gestionCours;
     
     @Override
     @Transactional
    public void run(String... strings) throws Exception {

       List<Piscine> l = gestionCours.recupererPiscines();
        Cours c = gestionCours.creerCours("Vidage de masque", 1, Calendar.getInstance().getTime(), 40, 4,l.get(1).getId());

        

	
    }
}
