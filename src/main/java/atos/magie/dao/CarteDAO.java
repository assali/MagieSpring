/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magie.dao;

import atos.magie.entity.Carte;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Administrateur
 */
public class CarteDAO {

    public List<Carte> listerCarteParJoueurId(long joueurId, long partieId) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("select c from Carte c join c.joueur j JOIN j.partie p where j.id=:idJoueur And p.id=:idPartie");

        query.setParameter("idJoueur", joueurId);
        query.setParameter("idPartie", partieId);

        return query.getResultList();
    }

    public void ajouterNouvelleCarte(Carte c) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();

        em.persist(c);

        em.getTransaction().commit();
    }

    public void modifierCarte(Carte c) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();

        em.merge(c);

        em.getTransaction().commit();
    }

    public Carte rechercheParId(long carteId) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("select c from Carte c where c.id=:id");
        query.setParameter("id", carteId);

        return (Carte) query.getSingleResult();
    }

  
    public void supprimerCarte(long carteId) {

        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("Delete From Carte c where c.id=:idCarte");
        query.setParameter("idCarte", carteId);
        em.getTransaction().begin();
        query.executeUpdate();

        em.getTransaction().commit();

    }

    public long recupererNbCartesParJoueurId(long joueurId) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("Select COUNT(c.id) From Joueur j join j.cartes c where  j.id=:idJoueur");
        // query.setParameter("idPartie", partieId);
        query.setParameter("idJoueur", joueurId);
        Object res = query.getSingleResult();
        if (res == null) {
            return 0;
        }
        return (long) res;
    }

    public boolean isJoueurALaCarte(Carte.Ingredient carte, long joueurId) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("Select c from Joueur j join j.cartes c where c.ingredient=:carte AND j.id=:idJoueur");
        query.setParameter("carte", carte);
        query.setParameter("idJoueur", joueurId);

        if (!query.getResultList().isEmpty()) {

            return true;
        } else {
            return false;
        }
    }

}
