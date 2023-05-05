/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.entities;

import java.util.Objects;

/**
 *
 * @author AA
 */
public class Produit {
    int id;
    String nom , description ;
    int prix;
    private String image;

    public Produit() {
    }

    public Produit(String nom, String description, int prix, String image) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.image = image;
    }

    public Produit(int id, String nom, String description, int prix, String image) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.image = image;
    }
    

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public int getPrix() {
        return prix;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", nom=" + nom + ", description=" + description + ", prix=" + prix + ", image=" + image + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.nom);
        hash = 23 * hash + Objects.hashCode(this.description);
        hash = 23 * hash + this.prix;
        hash = 23 * hash + Objects.hashCode(this.image);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produit other = (Produit) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }
    
    
    
}
