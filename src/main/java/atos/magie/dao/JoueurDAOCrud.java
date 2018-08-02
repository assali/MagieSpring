/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magie.dao;

import atos.magie.entity.Joueur;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Administrateur
 */
public interface JoueurDAOCrud extends CrudRepository<Joueur, Long> {

    public Joueur findOneByPseudo(String pseudo);

    @Query("Select j from Joueur j join j.partie p where j.etat=atos.magie.entity.Joueur.EtatJoueur.A_LA_MAIN AND p.id=?1")
    public Joueur rechercheJoueurQuiALaMainParPartieId(long partieId);

    // public long recupererNbJoueursParPartieId(long partieId)
    public long countByPartieId(long partieId);

    // public long rechercheMAXOrdre(long partieId) 
    //May cause problem
    @Query("Select MAX(j.ordre) From Joueur j join j.partie p where p.id=?1")
    public long maxOrdreByPartieId(long partieId);

    // public void ajoute(Joueur joueur)
    //save
    
    //public void modifier(Joueur joueur)
    //Save
    
    //public Joueur rechercherParId(long joueurId)
    //findOne
    
    // public Joueur rechercheJoueurParPartieEtOrdre(long partieId, long ordre)
    public Joueur findOneByOrdreAndPartieId(long ordre, long partieId);

    //join j.partie p where p.id=?1 AND j.id!=?2
    @Query("Select j from Joueur j join j.partie p where p.id=:partieId AND j.id!=:joueurId")
    public List<Joueur> listerAdversaires(@Param("partieId")long partieId,@Param("joueurId") long joueurId);

  
    @Query("Select j from Joueur j join j.partie p where p.id=?1 EXCEPT Select j from Joueur j join j.partie p where p.id=?1 AND j.etat=atos.magie.entity.Joueur.EtatJoueur.PERDU")
    public List<Joueur> determineSiPlusQueUnJoueurDansPartie(long partieId);

    
    //public List<Joueur> listerJoueursParPartieId(long partieId)
    public List<Joueur> findAllByPartieId(long partieId);
}
