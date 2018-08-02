/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magie.dao;

import atos.magie.entity.Carte;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Administrateur
 */
public interface CarteDAOCrud extends CrudRepository<Carte, Long> {

    // public List<Carte> listerCarteParJoueurId(long joueurId, long partieId)
    public List<Carte> findAllByJoueurId(long joueurId);
    
    //recupererCarteParJouerIdEtIngredient(joueur.getId(), Carte.Ingredient.CORNE_DE_LICORNE)
    public Carte findOneByJoueurIdAndIngredient(long joueurId, atos.magie.entity.Carte.Ingredient ingre);

    //public void ajouterNouvelleCarte(Carte c)
    //save
    //public void modifierCarte(Carte c)
    //save
    // public Carte rechercheParId(long carteId)
    //findOne
    //public void supprimerCarte(long carteId)
    //delete
    // public long recupererNbCartesParJoueurId(long joueurId)
    public long countByJoueurId(long joueurId);

    @Query("Select c from Joueur j join j.cartes c where c.ingredient=?1 AND j.id=?2")
    public List<Carte> isJoueurALaCarte(atos.magie.entity.Carte.Ingredient carte, long joueurId);

}
