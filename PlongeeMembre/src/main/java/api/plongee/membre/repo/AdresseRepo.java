/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.plongee.membre.repo;

import api.plongee.membre.domain.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 *
 * @author Marine
 */
public interface AdresseRepo extends JpaRepository <Adresse, Integer>{
    
}