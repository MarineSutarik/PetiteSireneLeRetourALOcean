/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api.plongee.membre.repo;

import api.plongee.membre.domain.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author marin
 */
public interface PaiementRepo  extends JpaRepository <Paiement, Integer>{
    
}
