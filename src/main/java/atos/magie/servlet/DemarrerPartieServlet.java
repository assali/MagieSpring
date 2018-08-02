/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magie.servlet;

import atos.magie.entity.Carte;
import atos.magie.entity.Joueur;
import atos.magie.service.CarteService;
import atos.magie.service.JoueurService;
import atos.magie.service.PartieService;
import atos.magie.spring.AutowireServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Administrateur
 */
@WebServlet(name = "DemarrerPartieServlet", urlPatterns = {"/demarrer-partie"})
public class DemarrerPartieServlet extends AutowireServlet {

    @Autowired
    private PartieService serviceP;
    @Autowired
    private CarteService serviceC;
    @Autowired
    private JoueurService serviceJ;
    private Carte c1;
    private Carte c2;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long partieId = Long.parseLong(req.getSession().getAttribute("partie").toString());
        serviceP.demarrerPartie(partieId);
        req.getRequestDispatcher("ecran-jeu.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pseudo = req.getSession().getAttribute("joueur").toString();
        Long partieId = Long.parseLong(req.getSession().getAttribute("partie").toString());
        Joueur joueur = serviceJ.rechercheJoueurParPseudo(pseudo);

        String action = req.getParameter("action");
        if (action != null) {
            if (action.equals("passe")) {
                serviceJ.passTour(partieId, joueur.getId());
            }

            if (action.equals("sort")) {

                String sort = req.getParameter("sorts");
                switch (sort) {
                    case "INVISIBILITE":
                        c1 = serviceC.recupererCarteParJouerIdEtIngredient(joueur.getId(), Carte.Ingredient.CORNE_DE_LICORNE);
                        c2 = serviceC.recupererCarteParJouerIdEtIngredient(joueur.getId(), Carte.Ingredient.BAVE_DE_CRAPAUD);
                        serviceC.supprimerCarteParJoueurId(joueur.getId(), c1.getId());
                        serviceC.supprimerCarteParJoueurId(joueur.getId(), c2.getId());
                        serviceP.sortInvisibilite(partieId, joueur.getId());
                        break;
                    case "PHILTRE DAMOUR":
                        //TODO Victim

                        c1 = serviceC.recupererCarteParJouerIdEtIngredient(joueur.getId(), Carte.Ingredient.CORNE_DE_LICORNE);
                        c2 = serviceC.recupererCarteParJouerIdEtIngredient(joueur.getId(), Carte.Ingredient.MANDRAGORE);
                        serviceC.supprimerCarteParJoueurId(joueur.getId(), c1.getId());
                        serviceC.supprimerCarteParJoueurId(joueur.getId(), c2.getId());
                        // serviceP.sortPhiltreAmour(partieId, joueur.getId(), Long.parseLong(victimId));

                        break;
                    case "HYPNOSE":
                        c1 = serviceC.recupererCarteParJouerIdEtIngredient(joueur.getId(), Carte.Ingredient.LAPIS_LAZULI);
                        c2 = serviceC.recupererCarteParJouerIdEtIngredient(joueur.getId(), Carte.Ingredient.BAVE_DE_CRAPAUD);
                        serviceC.supprimerCarteParJoueurId(joueur.getId(), c1.getId());
                        serviceC.supprimerCarteParJoueurId(joueur.getId(), c2.getId());
                        // System.out.println("votre Cartes List");
                        //TODO Carte

                        // serviceP.sortHypnose(partieId, joueur.getId(), Long.parseLong(victimId), Long.parseLong(carteId));
                        break;
                    case "DIVINATION":
                        c1 = serviceC.recupererCarteParJouerIdEtIngredient(joueur.getId(), Carte.Ingredient.LAPIS_LAZULI);
                        c2 = serviceC.recupererCarteParJouerIdEtIngredient(joueur.getId(), Carte.Ingredient.AILE_DE_CHAUVE_SOURIS);
                        serviceC.supprimerCarteParJoueurId(joueur.getId(), c1.getId());
                        serviceC.supprimerCarteParJoueurId(joueur.getId(), c2.getId());
                        //TODO  Lister cartes Adversaires
                        break;
                    case "SOMMEIL PROFOND":
                        c1 = serviceC.recupererCarteParJouerIdEtIngredient(joueur.getId(), Carte.Ingredient.MANDRAGORE);
                        c2 = serviceC.recupererCarteParJouerIdEtIngredient(joueur.getId(), Carte.Ingredient.AILE_DE_CHAUVE_SOURIS);
                        serviceC.supprimerCarteParJoueurId(joueur.getId(), c1.getId());
                        serviceC.supprimerCarteParJoueurId(joueur.getId(), c2.getId());

                        //TOD Victim
                        // serviceP.sortSommeilProfond(partieId, joueur.getId(), Long.parseLong(victimId));
                        break;
                }
                serviceJ.passeJoueurSuivant(partieId);
            }
        }
        req.getRequestDispatcher("ecran-jeu.jsp").forward(req, resp);

    }

}
