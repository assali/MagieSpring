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
@WebServlet(name = "ListerCartesServlet", urlPatterns = {"/liste-cartes"})
public class ListerCartesServlet extends AutowireServlet {

    @Autowired
    private PartieService serviceP;
    @Autowired
    private CarteService serviceC;
    @Autowired
    private JoueurService serviceJ;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pseudo = req.getSession().getAttribute("joueur").toString();
        Long partieId = Long.parseLong(req.getSession().getAttribute("partie").toString());

        Joueur joueur = serviceJ.rechercheJoueurParPseudo(pseudo);

        List<Carte> cartes = serviceC.listerCarteParJoueurId(joueur.getId(), partieId);
        req.setAttribute("cartes", cartes);
        req.getRequestDispatcher("liste-cartes.jsp").include(req, resp);
    }

}
