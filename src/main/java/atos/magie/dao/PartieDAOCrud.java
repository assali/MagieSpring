/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magie.dao;

import atos.magie.entity.Partie;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Administrateur
 */
public interface PartieDAOCrud extends CrudRepository<Partie, Long> {

    @Query("Select p from Partie p EXCEPT SELECT p from Partie p join p.joueurs j where j.etat=atos.magie.entity.Joueur.EtatJoueur.GAGNE or j.etat=atos.magie.entity.Joueur.EtatJoueur.A_LA_MAIN")
    public List<Partie> listerPartiesNonDemarrees();

    @Query(" Select p from Partie p INTERSECT SELECT p from Partie p join p.joueurs j where j.etat=atos.magie.entity.Joueur.EtatJoueur.GAGNE or j.etat=atos.magie.entity.Joueur.EtatJoueur.A_LA_MAIN")
    public List<Partie> listerPartiesDemarres();
         
    //Save
    // public void ajouterPartie(Partie p);
    
    //findOne
    //public Partie recherchePartieParId(Long partieId);
   
    
    //save
    // public void modifier(Partie p);
}
