/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atos.magie.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Administrateur
 */
@Entity
public class Carte implements Serializable {

    // Attention : capital letter
    public enum Ingredient {
        CORNE_DE_LICORNE,
        BAVE_DE_CRAPAUD,
        MANDRAGORE,
        LAPIS_LAZULI,
        AILE_DE_CHAUVE_SOURIS

    }
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column it is NOT obligatory 
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Ingredient ingredient;

    @ManyToOne
    @JoinColumn
    private Joueur joueur;
    
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    private int isChosen;

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public int getIsChosen() {
        return isChosen;
    }

    public void setIsChosen(int isChosen) {
        this.isChosen = isChosen;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Carte)) {
            return false;
        }
        Carte other = (Carte) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "atos.maggie.entity.Carte[ id=" + id + " ]";
    }

}