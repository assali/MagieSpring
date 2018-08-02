/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magie.servlet;

import atos.magie.entity.Joueur;
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
@WebServlet(name = "ListerJoueursServlet", urlPatterns = {"/liste-joueurs"})
public class ListerJoueursServlet extends AutowireServlet {

    @Autowired
    PartieService serviceP ;
    @Autowired
    JoueurService serviceJ;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Joueur> joueurs = serviceJ.listerJoueursParPartieId(Long.parseLong(req.getParameter("id")));
        req.setAttribute("listJoueurs", joueurs);

        Long nbJoueurs = serviceJ.recupererNbJoueursParPartieId(Long.parseLong(req.getParameter("id")));
        if (nbJoueurs >= 2) {
            req.setAttribute("nbjoueurs", nbJoueurs);
        }

        req.getRequestDispatcher("liste-joueurs.jsp").forward(req, resp);
    }

}
