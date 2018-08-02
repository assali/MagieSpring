/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magie.servlet;

import atos.magie.service.JoueurService;
import atos.magie.spring.AutowireServlet;
import java.io.File;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Administrateur
 */
@WebServlet(name = "RejoindrePartieServlet", urlPatterns = {"/rejoindre-partie"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, maxFileSize = 1024 * 1024 * 50, maxRequestSize = 1024 * 1024 * 100)
public class RejoindrePartieServlet extends AutowireServlet {

    @Autowired
    JoueurService serviceJ ;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("rejoindre-partie.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {

//            String savePath = "C:\\Users\\Administrateur\\Documents\\NetBeansProjects\\MagieMagieWeb\\web\\images";
//            File fileSaveDir = new File(savePath);
//            if (!fileSaveDir.exists()) {
//                fileSaveDir.mkdir();
//            }
            ///
            //request is an instance of HttpServletRequest
            File uploadDirectory = new File(req.getSession().getServletContext().getRealPath("/uploads"));

            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }
            String pseudo = req.getParameter("pseudo");
            Part part = req.getPart("avatar");
            Long partieId = Long.parseLong(req.getParameter("id"));

            req.getSession().setAttribute("partie", partieId);
            req.getSession().setAttribute("joueur", pseudo);
            String fileName = part.getSubmittedFileName();
            // File reportFile = new File(reportDirectory.getAbsolutePath(), fileName);

            String requestUrl = req.getRequestURL().toString();
            requestUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/") + 1) + "uploads/" + fileName;

            part.write(uploadDirectory + File.separator + fileName);
            serviceJ.rejoindrePartie(pseudo, requestUrl, partieId);
            resp.sendRedirect("liste-joueurs?id=" + partieId);
            // return requestUrl;
            ///

            //old code
            //String pseudo = req.getParameter("pseudo");
            //Part part = req.getPart("avatar");
            // Long partieId = Long.parseLong(req.getParameter("id"));
            // req.getSession().setAttribute("partie", partieId);
            //req.getSession().setAttribute("joueur", pseudo);
            //String fileName = part.getSubmittedFileName();
            // Random r = new Random();
            //int a = r.nextInt(1000000);
            //  serviceJ.rejoindrePartie(pseudo, "images/"+a+".png", partieId);
            //serviceJ.rejoindrePartie(pseudo, "/images/" + a + ".png", partieId);
            //part.write(savePath + File.separator + a + ".png");
            //part.write("images/"+pseudo + fileName);
            //resp.sendRedirect("liste-joueurs?id=" + partieId);
            //old code
        } catch (Exception e) {
        }
    }

}
