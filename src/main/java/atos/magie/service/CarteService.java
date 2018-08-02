/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magie.service;

import atos.magie.dao.CarteDAO;
import atos.magie.dao.CarteDAOCrud;
import atos.magie.dao.JoueurDAO;
import atos.magie.dao.JoueurDAOCrud;
import atos.magie.dao.PartieDAO;
import atos.magie.dao.PartieDAOCrud;
import atos.magie.entity.Carte;
import atos.magie.entity.Joueur;
import atos.magie.entity.Partie;
import java.util.List;
import java.util.Random;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrateur
 */
@Service
@Transactional
public class CarteService {

    @Autowired
    private CarteDAOCrud dao;
    @Autowired
    private JoueurDAOCrud daoJ;
    @Autowired
    private PartieDAOCrud daoP;

    public Carte distribueCarteParJoueurIdEtPartieId(long joeurId, long partieId) {
        Joueur j = daoJ.findOne(joeurId);
        Partie p = daoP.findOne(partieId);
        Carte c = new Carte();

        //autre solution
        // Carte.Ingredient[] tabIngredients = Carte.Ingredient.values();
        Random r = new Random();
        int num = r.nextInt(5);
        //autre solution
        //int num1 = r.nextInt(tabIngredients.length);
        switch (num) {
            case 0:
                c.setIngredient(Carte.Ingredient.MANDRAGORE);
                c.setPath("images/MANDRAGORE.jpg");
                break;

            case 1:
                c.setIngredient(Carte.Ingredient.AILE_DE_CHAUVE_SOURIS);
                c.setPath("images/AILE_DE_CHAUVE_SOURIS.jpg");
                break;

            case 2:
                c.setIngredient(Carte.Ingredient.BAVE_DE_CRAPAUD);
                c.setPath("images/BAVE_DE_CRAPAUD.jpg");
                break;

            case 3:
                c.setIngredient(Carte.Ingredient.CORNE_DE_LICORNE);
                c.setPath("images/CORNE_DE_LICORNE.png");
                break;

            case 4:
                c.setIngredient(Carte.Ingredient.LAPIS_LAZULI);
                c.setPath("images/LAPIS_LAZULI.jpg");
                break;
        }
        //autre solution
        //  c.setIngredient(tabIngredients[num1]);

        c.setJoueur(daoJ.findOne(joeurId));
        c.setIsChosen(0);
        dao.save(c);

        //here
//        for (Joueur joueur : p.getJoueurs()) {
//            if (joueur.getId() == j.getId()) {
//                joueur.getCartes().add(c);
//            }
//        }
        j.getCartes().add(c);
        daoP.save(p);
        daoJ.save(j);

        return c;
    }

    public List<Carte> listerCarteParJoueurId(long joueurId, long partieId) {
        return dao.findAllByJoueurId(joueurId);
    }

    public void creerNouvelleCarte(Carte c) {
        dao.save(c);
    }

    void distribue7CartesParJoueurIdEtPartieId(Long id, long idPartie) {
        for (int i = 0; i < 7; i++) {
            distribueCarteParJoueurIdEtPartieId(id, idPartie);
        }
    }

    public void supprimerCarteParJoueurId(long joueurId, Long carteId) {
        dao.delete(carteId);
    }

    public long recupererNbCartesParJoueurId(long joueurId) {
        return dao.countByJoueurId(joueurId);
    }

    public boolean isJoueurALaCarte(Carte.Ingredient carte, long joueurId) {
       List<Carte> list= dao.isJoueurALaCarte(carte, joueurId);
         return list.size() == 1;
    }
    
   public Carte recupererCarteParJouerIdEtIngredient(long joueurId, atos.magie.entity.Carte.Ingredient ingre)
   {
   return dao.findOneByJoueurIdAndIngredient(joueurId, ingre);
   }

}
