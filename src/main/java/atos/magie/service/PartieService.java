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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class PartieService {

    @Autowired
    private PartieDAOCrud daocrud;

    @Autowired
    private JoueurDAOCrud daoJ;
    
    @Autowired
    private CarteDAOCrud daoC;
    
    @Autowired
    private JoueurService jService ;
    
    @Autowired
    private CarteService cService ;

    public List<String> possibleSortsCollection(long joueurId) {
        List<String> possibleSorts = new ArrayList<>();

        if (cService.isJoueurALaCarte(Carte.Ingredient.CORNE_DE_LICORNE, joueurId) && cService.isJoueurALaCarte(Carte.Ingredient.BAVE_DE_CRAPAUD, joueurId)) {
            possibleSorts.add("INVISIBILITE");
        }
        if (cService.isJoueurALaCarte(Carte.Ingredient.CORNE_DE_LICORNE, joueurId) && cService.isJoueurALaCarte(Carte.Ingredient.MANDRAGORE, joueurId)) {
            possibleSorts.add("PHILTRE DAMOUR");
        }
        if (cService.isJoueurALaCarte(Carte.Ingredient.BAVE_DE_CRAPAUD, joueurId) && cService.isJoueurALaCarte(Carte.Ingredient.LAPIS_LAZULI, joueurId) && (cService.recupererNbCartesParJoueurId(joueurId) >= 3)) {
            possibleSorts.add("HYPNOSE");
        }
        if (cService.isJoueurALaCarte(Carte.Ingredient.LAPIS_LAZULI, joueurId) && cService.isJoueurALaCarte(Carte.Ingredient.AILE_DE_CHAUVE_SOURIS, joueurId)) {
            possibleSorts.add("DIVINATION");
        }
        if (cService.isJoueurALaCarte(Carte.Ingredient.MANDRAGORE, joueurId) && cService.isJoueurALaCarte(Carte.Ingredient.AILE_DE_CHAUVE_SOURIS, joueurId)) {
            possibleSorts.add("SOMMEIL PROFOND");
        }

        return possibleSorts;

    }

    /**
     * Liste les parties dont aucun joueur n'est à l'etat a la main ou gagne
     *
     * @return
     */
    //done with crud
    public List<Partie> listerPartiesNonDemarrees() {
        //return dao.listerPartiesNonDemarrees();
        return daocrud.listerPartiesNonDemarrees();

    }
    //done with crud

    public List<Partie> listerPartiesDemarrees() {
        return daocrud.listerPartiesDemarres();

    }

    //we pass the  paramters 
    //done with crud
    public Partie creerNouvellePartie(String nom) {
        Partie p = new Partie();
        p.setNom(nom);
        daocrud.save(p);
        return p;
    }

    public Partie recupererPartieParId(long partieId) {
        // return dao.recherchePartieParId(partieId);
        return daocrud.findOne(partieId);
    }

    public void demarrerPartie(long idPartie) {

        // recherche partie par son id en DB
        // Partie p = dao.recherchePartieParId(idPartie);
        Partie p = daocrud.findOne(idPartie);

        // Erreur si pas au moins 2 joueurs dans la partie
        if (jService.recupererNbJoueursParPartieId(idPartie) < 2) {
            throw new RuntimeException("Erreur : nb joueurs moins 2");
        }

        // passe le joueur d'ordre 1 à etat a la main
        jService.passeJoueurOrdre1EtatALaMain(idPartie);

        // distribue 7 cartes d'ingrédients au hasard à chaque joueur de la partie
        for (Joueur j : p.getJoueurs()) {

            cService.distribue7CartesParJoueurIdEtPartieId(j.getId(), idPartie);
        }

    }

    public boolean finPartie(long idPartie) {
        //Partie p = dao.recherchePartieParId(idPartie);
        Partie p = daocrud.findOne(idPartie);
        boolean partieFini = false;
        int nbJoueursPerdus = 0;
        boolean someonewon = false;
        for (int i = 0; i < p.getJoueurs().size(); i++) {
            if (p.getJoueurs().get(i).getEtat() == Joueur.EtatJoueur.PERDU) {
                nbJoueursPerdus++;
            }
            if (p.getJoueurs().get(i).getEtat() == Joueur.EtatJoueur.GAGNE) {
                someonewon = true;
            }

        }
        if ((nbJoueursPerdus == p.getJoueurs().size() - 1) && (someonewon == true)) {
            partieFini = true;
        }
        return partieFini;
    }

    public void sortSommeilProfond(long partieId, long joueurId, long victimId) {
        // Joueur joueurQuiLance = daoJ.rechercherParJoueurIdEtPartieId(joueurId, partieId);
        Joueur joueurVictim = daoJ.findOne(victimId);
        joueurVictim.setEtat(Joueur.EtatJoueur.SOMMEIL_PROFOND);
        daoJ.save(joueurVictim);
    }

    public List<Joueur> sortDivination(long partieId, long joueurId) {
        //Joueur joueurQuiLance = daoJ.rechercherParJoueurIdEtPartieId(joueurId, partieId);

        //List<Joueur> res = new ArrayList<>();
        //res = 
        return daoJ.listerAdversaires(joueurId, partieId);

        //return res;
    }

    public void sortHypnose(long partieId, long joueurId, long victimId, long carteId) {

        Joueur joueurQuiLance = daoJ.findOne(joueurId);

        Joueur joueurVictim = daoJ.findOne(victimId);
        List<Carte> victimCartes = cService.listerCarteParJoueurId(victimId, partieId);
        List<Carte> CartesAuHasard = new ArrayList<>();
        Carte cartePourEchange = daoC.findOne(carteId);

        if (victimCartes.size() < 3) {
            throw new RuntimeException("Erreur : victim possède moins de 3 cartes");
        } else {
            int arriveAMax = 0;
            Random r = new Random();
            int num;
            while (arriveAMax != 3) {
                num = r.nextInt(victimCartes.size());
                if (victimCartes.get(num).getIsChosen() != 1) {
                    victimCartes.get(num).setIsChosen(1);
                    daoC.save(victimCartes.get(num));
                    CartesAuHasard.add(victimCartes.get(num));
                    arriveAMax++;
                }
            }
            num = r.nextInt(3);
            Carte c = CartesAuHasard.get(num);
            cartePourEchange.setJoueur(joueurVictim);
            daoC.save(cartePourEchange);

            c.setJoueur(joueurQuiLance);
            c.setIsChosen(0);
            daoC.save(c);

            for (Carte carte : CartesAuHasard) {
                carte.setIsChosen(0);
                daoC.save(carte);
            }

        }
    }

    public void sortPhiltreAmour(long partieId, long joueurId, long victimId) {

        Joueur joueurQuiLance = daoJ.findOne(joueurId);
        Joueur joueurVictim = daoJ.findOne(victimId);
        List<Carte> victimCartes = cService.listerCarteParJoueurId(victimId, partieId);
        List<Carte> CartesAuHasard = new ArrayList<>();
        double moitie = 0;

        if (victimCartes.size() == 1) {
            joueurVictim.setEtat(Joueur.EtatJoueur.PERDU);
            Carte c = joueurVictim.getCartes().get(0);
            c.setJoueur(joueurQuiLance);
            joueurQuiLance.getCartes().add(joueurVictim.getCartes().get(0));
            daoC.save(c);
            daoJ.save(joueurVictim);
            return;
        }
        if (victimCartes.size() % 2 == 0) {
            moitie = victimCartes.size() / 2;
        } else {
            moitie = Math.ceil((double) victimCartes.size() / 2);
        }
        int arriveAMax = 0;
        Random r = new Random();
        int num;
        while (arriveAMax != moitie) {
            num = r.nextInt(victimCartes.size());
            if (victimCartes.get(num).getIsChosen() != 1) {
                victimCartes.get(num).setIsChosen(1);

                daoC.save(victimCartes.get(num));
                CartesAuHasard.add(victimCartes.get(num));
                arriveAMax++;
            }
        }
        for (Carte carte : CartesAuHasard) {

            carte.setJoueur(joueurQuiLance);
            carte.setIsChosen(0);
            joueurQuiLance.getCartes().add(carte);
            daoC.save(carte);

        }
    }

    public void sortInvisibilite(long partieId, long joueurId) {
        Joueur joueurQuiLance = daoJ.findOne(joueurId);

        List<Joueur> adversaires = daoJ.listerAdversaires(joueurId, partieId);
        List<Carte> CartesAuHasard = new ArrayList<>();
        Random r = new Random();
        int num;
        for (Joueur adversaire : adversaires) {
            num = r.nextInt(adversaire.getCartes().size());
            CartesAuHasard.add(adversaire.getCartes().get(num));

        }
        for (Carte carte : CartesAuHasard) {
            carte.setJoueur(joueurQuiLance);
            joueurQuiLance.getCartes().add(carte);
            daoC.save(carte);

        }

    }

}
