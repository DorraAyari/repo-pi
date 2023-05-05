/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.entities;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

/**
 *
 * @author User
 */
public class Reclamation {
    int id;
    String nom;
    String prenom;
    String email;
    String message;
    User user_id;
    int user_idd;
    User user; 
    String user_nom;
    String user_prenom;
    private String user_email;
    

public Reclamation(String nom, String prenom, String email, String message,User user) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.message = message;
    }

    public Reclamation() {
    }

    public Reclamation(int id, String nom, String prenom, String email, String message) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.message = message;
    }

    public Reclamation(String nom, String prenom, String email, String message) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.message = message;
    }
 public int getUser_idd() {
        return user_idd;
    }
 public User getUser() {
        return user_id;
    }
public User getUser_id() {
        return user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }
public void setUser(User user) {
        this.user_id = user;
    }

 public void setUser_id(User user_id) {
        this.user_id = user_id;
    }
public String setUser_nom(String user_nom) {
    return this.user_nom=user_nom;
}
public String setUser_email(String user_email) {
    return this.user_email=user_email;
}
public String setUser_prenom(String user_prenom) {
    return this.user_prenom=user_prenom;
}

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", message=" + message + '}';
    }
    
    
}
