/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magie.dao;

import atos.magie.entity.Joueur;
import atos.magie.entity.Partie;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Administrateur
 */
public class PartieDAO {

    //we always start with the logic
    public List<Partie> listerPartiesNonDemarrees() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery(" Select p from Partie p EXCEPT SELECT p from Partie p join p.joueurs j where j.etat=:etat_gagne or j.etat=:etat_alamain");
        query.setParameter("etat_gagne", Joueur.EtatJoueur.GAGNE);
        query.setParameter("etat_alamain", Joueur.EtatJoueur.A_LA_MAIN);
        return query.getResultList();
    }

    public List<Partie> listerPartiesDemarres() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery(" Select p from Partie p INTERSECT SELECT p from Partie p join p.joueurs j where j.etat=:etat_gagne or j.etat=:etat_alamain");
        query.setParameter("etat_gagne", Joueur.EtatJoueur.GAGNE);
        query.setParameter("etat_alamain", Joueur.EtatJoueur.A_LA_MAIN);
        return query.getResultList();
    }

    //ajouter au lieu de creer
    public void ajouterPartie(Partie p) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();

        em.persist(p);

        em.getTransaction().commit();
    }

    public Partie recherchePartieParId(Long partieId) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
//        Query query = em.createQuery("Select p from Partie p where p.id=:idPartie");
//        query.setParameter("idPartie", partieId);
//        return (Partie) query.getSingleResult();

        //the method find is simpler than make a query sometimes cause an error
        return em.find(Partie.class, partieId);
    }
 
    public void modifier(Partie p) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();
        em.merge(p);
        em.getTransaction().commit();
    }

}
