/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magie.servlet;

import atos.magie.entity.Joueur;
import atos.magie.service.JoueurService;
import atos.magie.spring.AutowireServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
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
@WebServlet(name = "JoueurALaMainServlet", urlPatterns = {"/joueurALaMain"})
public class JoueurALaMainServlet extends AutowireServlet {

    @Autowired
    private JoueurService serviceJ ;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pseudo = req.getSession().getAttribute("joueur").toString();
        Long partieId = Long.parseLong(req.getSession().getAttribute("partie").toString());

        //Joueur qui a la main
        Joueur joueurALaMAin = serviceJ.joueurQuiALaMain(partieId);
        if (Objects.equals(joueurALaMAin.getId(), serviceJ.rechercheJoueurParPseudo(pseudo).getId())) {
            req.setAttribute("vous", joueurALaMAin);
        }
        req.setAttribute("main", joueurALaMAin);
        req.getRequestDispatcher("joueurALaMain.jsp").include(req, resp);

    }

}
