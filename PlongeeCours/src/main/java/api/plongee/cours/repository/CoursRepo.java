/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.plongee.cours.repository;

import api.plongee.cours.domain.Cours;
import api.plongee.cours.domain.Participant;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

/**
 *
 * @author Marine
 */
@RepositoryRestController
public interface CoursRepo  extends MongoRepository<Cours, String>{
    
    /**
     *
     * @param pidMembre
     * @return
     */
   /* @Query(value="{\"participants.idMembre\":?0}")
    public List<Cours> findAllByParticipants(Integer pidMembre);*/
    
    public List<Cours> findAllByParticipants(Participant participant);
    public List<Cours> findAllByEnseignant(Integer enseignant);
}
