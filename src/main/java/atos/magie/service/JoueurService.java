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
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrateur
 */
@Service
@Transactional
public class JoueurService {

    @Autowired
    private JoueurDAOCrud daoJoueur;
    @Autowired
    private PartieDAOCrud daoPartie;

    @Autowired
    private CarteService servCarte ;

    public Joueur rejoindrePartie(String pseudo, String avatar, long idPartie) {
//recherche si le joueur existe deja
        //boolean isNew = false; 
        Joueur joueur = daoJoueur.findOneByPseudo(pseudo);

        if (joueur == null) {
//Le jouer n'existe pas encore
            joueur = new Joueur();
            joueur.setPseudo(pseudo);
            joueur.setNbPartiejouees(0L);
            joueur.setNbPartiesGagnees(0L);
        }
        joueur.setAvatar(avatar);
        joueur.setEtat(Joueur.EtatJoueur.N_A_PAS_LA_MAIN);
        joueur.setOrdre(daoJoueur.maxOrdreByPartieId(idPartie) + 1);
        joueur.setNbPartiejouees(joueur.getNbPartiejouees() + 1);

        //Do Not Froget to associate in two directions
        Partie partie = daoPartie.findOne(idPartie);
        joueur.setPartie(partie);
        partie.getJoueurs().add(joueur);

        if (joueur.getId() == null) {
            daoJoueur.save(joueur);
        } else {
            daoJoueur.save(joueur);
        }

        daoPartie.save(partie);
        return joueur;
    }

    //Passe Tour
    public void passTour(long partieId, long joueurId) {
        passeJoueurSuivant(partieId);
        Joueur j = daoJoueur.findOne(joueurId);
        if (j.getEtat() == Joueur.EtatJoueur.GAGNE) {
            return;
        }
        if (j.getEtat() == Joueur.EtatJoueur.A_LA_MAIN) {
            return;
        }
        servCarte.distribueCarteParJoueurIdEtPartieId(joueurId, partieId);
    }

    public boolean determineSiPlusQueUnJoueurDansPartie(long partieId) {
        List<Joueur> list = daoJoueur.determineSiPlusQueUnJoueurDansPartie(partieId);
        return list.size() == 1;
    }

    //joueur suivant
    public void passeJoueurSuivant(long partieId) {

        //1- recuperer joueur qui a la main = joueurQuiALaMain
        Joueur joueurQuiALaMain = daoJoueur.rechercheJoueurQuiALaMainParPartieId(partieId);

        if (joueurQuiALaMain.getCartes().isEmpty()) {
            joueurQuiALaMain.setEtat(Joueur.EtatJoueur.PERDU);
            daoJoueur.save(joueurQuiALaMain);
        }
        //2- Determine si tous autres joueurs ont perdu
        //et passe le joueur a l'etat gagné si c'est le cas 
        //puis quitte la fonction
        //une autre solution  
        //if (servPartie.finPartie(partieId))
        if (determineSiPlusQueUnJoueurDansPartie(partieId)) {
            joueurQuiALaMain.setEtat(Joueur.EtatJoueur.GAGNE);
            joueurQuiALaMain.setNbPartiesGagnees(joueurQuiALaMain.getNbPartiesGagnees() + 1);
            daoJoueur.save(joueurQuiALaMain);
            return;
        }
        //  else {
        //la partie n'est pas fini
        //3-recuperer ordre Max des joueurs de la partie
        long ordreMax = daoJoueur.maxOrdreByPartieId(partieId);
        //joueurEvalue= joueurQuiAlaMain
        Joueur joueurEvalue = joueurQuiALaMain;
        //long ordreProchain;
        // boolean prochainNonTrouve = true;
        while (true) {

            //si joueurEvalue est le dernier joueur alors on evalue
            if (joueurEvalue.getOrdre() >= ordreMax) {
                //ordreProchain = 1;
                joueurEvalue = daoJoueur.findOneByOrdreAndPartieId(1L, partieId);
            } else {
                //ordreProchain = joueurQuiALaMain.getOrdre() + 1;
                joueurEvalue = daoJoueur.findOneByOrdreAndPartieId(joueurEvalue.getOrdre() + 1, partieId);
            }
            //Joueur prochain = daoJoueur.recupererJoueurProchain(partieId, ordreProchain);

            //Return si tout les joueurs non étiminés etaient en sommeil profond et q'on la  a juste réveillés
            if (joueurEvalue.getId() == joueurQuiALaMain.getId()) {
//                joueurEvalue.setEtat(Joueur.EtatJoueur.A_LA_MAIN);
//                daoJoueur.modifier(joueurEvalue);
                return;
            }

            if (joueurEvalue.getEtat() == Joueur.EtatJoueur.SOMMEIL_PROFOND) {
                joueurEvalue.setEtat(Joueur.EtatJoueur.N_A_PAS_LA_MAIN);
                daoJoueur.save(joueurEvalue);
                //prochainNonTrouve = true;
                //si joueurEvalue pas la main alors c'est lui qui prend la main
            } else if (joueurEvalue.getEtat() == Joueur.EtatJoueur.N_A_PAS_LA_MAIN) {
                if (joueurEvalue.getCartes().isEmpty()) {
                    joueurEvalue.setEtat(Joueur.EtatJoueur.PERDU);
                    daoJoueur.save(joueurEvalue);
                } else {
                    joueurEvalue.setEtat(Joueur.EtatJoueur.A_LA_MAIN);
                    daoJoueur.save(joueurEvalue);
                }
                //if the user doesnt have any cart
                if (joueurQuiALaMain.getCartes().isEmpty()) {
                    joueurQuiALaMain.setEtat(Joueur.EtatJoueur.PERDU);
                    daoJoueur.save(joueurQuiALaMain);
                } else if (determineSiPlusQueUnJoueurDansPartie(partieId)) {
                    joueurQuiALaMain.setEtat(Joueur.EtatJoueur.GAGNE);
                    joueurQuiALaMain.setNbPartiesGagnees(joueurQuiALaMain.getNbPartiesGagnees() + 1);
                    daoJoueur.save(joueurQuiALaMain);
                    //return;
                } else {
                    joueurQuiALaMain.setEtat(Joueur.EtatJoueur.N_A_PAS_LA_MAIN);
                    daoJoueur.save(joueurQuiALaMain);
                }
                // prochainNonTrouve = false;
                return;
            }
        }
        // }
    }

//    public boolean isJoueurPerdu(long joueurId) {
//        Joueur j = daoJoueur.rechercherParId(joueurId);
//        if (j.getCartes().size() == 0) {
//            return true;
//        }
//        return false;
//
//    }
    public Joueur joueurQuiALaMain(long partieId) {
        return daoJoueur.rechercheJoueurQuiALaMainParPartieId(partieId);
    }

    void passeJoueurOrdre1EtatALaMain(long idPartie) {
        Joueur j = daoJoueur.findOneByOrdreAndPartieId(1L, idPartie);
        j.setEtat(Joueur.EtatJoueur.A_LA_MAIN);
        daoJoueur.save(j);
    }

    public Joueur rechercheJoueurParId(long joueurId) {
        return daoJoueur.findOne(joueurId);
    }

    public Joueur rechercheJoueurParPseudo(String pseudo) {
        return daoJoueur.findOneByPseudo(pseudo);
    }

  
    public List<Joueur> recupererMesAdversaires(long partieId, long joueurId) {
        return daoJoueur.listerAdversaires(partieId, joueurId);
    }

    public long recupererNbJoueursParPartieId(long partieId) {
        return (long) daoJoueur.countByPartieId(partieId);

    }

    public List<Joueur> listerJoueursParPartieId(long partieId) {
        return daoJoueur.findAllByPartieId(partieId);
    }

}
