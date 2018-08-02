/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magie.dao;

import atos.magie.entity.Carte;
import atos.magie.entity.Joueur;
import atos.magie.entity.Partie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Administrateur
 */
public class JoueurDAO {

    /**
     * Renvoie le joueur dont le pseudo existe en BD, ou renvoie null si pa
     * trouvé
     *
     * @param pseudo à recherche
     * @return
     */
    //crud done
    public Joueur rechercherParPseudo(String pseudo) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("select j From Joueur j where j.pseudo= :lePseudo");
        query.setParameter("lePseudo", pseudo);
        List<Joueur> joueursTrouves = query.getResultList();
        if (joueursTrouves.isEmpty()) {
            return null;
        }
        return joueursTrouves.get(0);
    }

    //crud done
    public Joueur rechercheJoueurQuiALaMainParPartieId(long partieId) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("Select j from Joueur j join j.partie p where j.etat=:etat AND p.id=:idPartie");
        query.setParameter("etat", Joueur.EtatJoueur.A_LA_MAIN);
        query.setParameter("idPartie", partieId);
        return (Joueur) query.getSingleResult();

    }

    //crud done   
    public long recupererNbJoueursParPartieId(long partieId) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("Select COUNT(j.id) from Joueur j Join j.partie p where p.id=:idPartie");
        query.setParameter("idPartie", partieId);
        return (long) query.getSingleResult();
    }

    //crud done
    public long rechercheMAXOrdre(long partieId) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("Select MAX(j.ordre) From Joueur j join j.partie p where p.id=:idPartie");
        query.setParameter("idPartie", partieId);
        return (long) query.getSingleResult();
    }

    //crud done
    public void ajoute(Joueur joueur) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();
        em.persist(joueur);
        em.getTransaction().commit();
    }

//crud done
    public void modifier(Joueur joueur) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        em.getTransaction().begin();
        em.merge(joueur);
        em.getTransaction().commit();
    }

//crud done
    public Joueur rechercherParId(long joueurId) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("select j From Joueur j where j.id=:id");
        query.setParameter("id", joueurId);
        List<Joueur> joueursTrouves = query.getResultList();
        if (joueursTrouves.isEmpty()) {
            return null;
        }
        return joueursTrouves.get(0);
    }

//crud done
    public Joueur rechercheJoueurParPartieEtOrdre(long partieId, long ordre) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("Select j From Joueur j join j.partie p where j.ordre=:ordre AND p.id=:idPartie");
        query.setParameter("idPartie", partieId);
        query.setParameter("ordre", ordre);
        return (Joueur) query.getSingleResult();
    }


//crud done
    public List<Joueur> listerAdversairesParPartieIdEtJoueurId(long joueurId, long partieId) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
//        Query query=em.createQuery("Select j from Partie p join p.joueurs j where p.id=:idPartie AND j.id<>:idJoueur AND j.etat=:etat");
        Query query = em.createQuery("Select j from Partie p join p.joueurs j where p.id=:idPartie AND j.id<>:idJoueur");
        query.setParameter("idJoueur", joueurId);
        query.setParameter("idPartie", partieId);
        //query.setParameter("etat", Joueur.EtatJoueur.N_A_PAS_LA_MAIN);
        return query.getResultList();
    }

//crud done
    public boolean determineSiPlusQueUnJoueurDansPartie(long partieId) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("Select j from Joueur j join j.partie p where p.id=:idPartie EXCEPT Select j from Joueur j join j.partie p where p.id=:idPartie AND j.etat=:etatPerdu");
        query.setParameter("idPartie", partieId);
        query.setParameter("etatPerdu", Joueur.EtatJoueur.PERDU);
        //ToDO
        //query.setParameter("etatSommeil", Joueur.EtatJoueur.SOMMEIL_PROFOND);

        List res = query.getResultList();
//        if (res.size() != 1) {
//            return false;
//        }
//        return true;
        return res.size() == 1;

    }

    //crud done
    public List<Joueur> listerJoueursParPartieId(long partieId) {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("Select p.joueurs from Partie p where p.id=:id");
        query.setParameter("id", partieId);
        return query.getResultList();
    }

}
