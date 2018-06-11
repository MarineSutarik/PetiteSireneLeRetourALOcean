/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.plongee.cours.repository;

import api.plongee.cours.domain.Creneau;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.mongodb.repository.MongoRepository; 

/**
 *
 * @author Marine
 */

public interface CreneauRepository extends MongoRepository<Creneau, String> {
    
}
