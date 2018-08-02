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
@WebServlet(name = "OperationsServlet", urlPatterns = {"/operations"})
public class OperationsServlet extends AutowireServlet {

    @Autowired
    JoueurService serviceJ;
    @Autowired
    PartieService serviceP;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pseudo = req.getSession().getAttribute("joueur").toString();
        Long partieId = Long.parseLong(req.getSession().getAttribute("partie").toString());

        //Joueur qui a la main
        Joueur joueurALaMAin = serviceJ.joueurQuiALaMain(partieId);
        Joueur joueurActuel = serviceJ.rechercheJoueurParPseudo(pseudo);
        if (Objects.equals(joueurALaMAin.getId(), joueurActuel.getId())) {
            req.setAttribute("valid", "yes");
        }

        List<String> sorts = serviceP.possibleSortsCollection(joueurActuel.getId());
        req.setAttribute("sorts", sorts);

        req.getRequestDispatcher("operations.jsp").include(req, resp);
    }

}
