/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.entities;

/**
 *
 * @author dorraayari
 */
public class Salle {
    int id ;
    String nom , description;

    public Salle(int id, String nom, String description) {
        this.id = id;
        this.nom = nom;
        this.description = description;
    }

    public Salle(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public Salle() {
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

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Salle{" + "id=" + id + ", nom=" + nom + ", description=" + description + '}';
    }
    
}
