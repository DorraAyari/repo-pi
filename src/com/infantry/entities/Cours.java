/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.entities;

import com.infantry.services.ServiceCours;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dorraayari
 */
public class Cours {
 // autres champs et méthodes

    private boolean btnreserver = false;
  private List<Cours> coursReserves; // ajout de la liste de cours réservés
    
    // constructeurs, getters, setters, etc.

    public boolean hasReservedCours(Cours cours) {
        return coursReserves.contains(cours);
    }

    public void reserveCours(Cours cours) {
        coursReserves.add(cours);
    }
    public boolean hasUser(User user) {
        boolean result = userList.contains(user);
        btnreserver = result;
        return result;
    }

    public boolean hasUserReserved() {
        return btnreserver;
    }
    int id,nb_places_total,reservation;
    String nom,image,description;
    Salle salle_id;
    Coach coach_id;
    int salle_idd;
    int coach_idd;
 String coach_nom;
    String salle_nom;


    public boolean isBtnreserver() {
        return btnreserver;
    }

    public void setBtnreserver(boolean btnreserver) {
        this.btnreserver = btnreserver;
    }
     private List<User> userList = new ArrayList<>();

    public void addUser(User user) {
        userList.add(user);
    }

  




public Cours(int id, String nom, String image, String description, int nb_places_total, 
             int reservation, int coach_id, String coach_nom, int salle_id, String salle_nom) {
    this.id = id;
    this.nom = nom;
    this.image = image;
    this.description = description;
    this.nb_places_total = nb_places_total;
    this.reservation = reservation;
    this.coach_idd = coach_id;
    this.coach_nom = coach_nom;
    this.salle_idd = salle_id;
    this.salle_nom = salle_nom;
}

public Cours(String nom, String image, String description, int nb_places_total, 
             int reservation, String coach_nom, String salle_nom) {
    this.id = id;
    this.nom = nom;
    this.image = image;
    this.description = description;
    this.nb_places_total = nb_places_total;
    this.reservation = reservation;
   
    this.coach_nom = coach_nom;
   
    this.salle_nom = salle_nom;
}
public Cours(int id,String nom, String image, String description, int nb_places_total, 
             int reservation, String coach_nom, String salle_nom) {
    this.id = id;
    this.nom = nom;
    this.image = image;
    this.description = description;
    this.nb_places_total = nb_places_total;
    this.reservation = reservation;
   
    this.coach_nom = coach_nom;
   
    this.salle_nom = salle_nom;
}

    public Cours(int id, String nom, String image, String description, int nbPlacesTotal, int reservation, Salle salle_id, Coach cours_id, String salle_nom, String coach_nom) {
        this.id = id;
        this.nom = nom;

        this.image = image;
        this.description = description;
        this.nb_places_total = nbPlacesTotal;
        this.reservation = reservation;
        this.salle_id = salle_id;
        this.coach_id = cours_id;
          this.coach_nom = coach_nom;
          this.salle_nom = salle_nom;

    }

    public Cours(String nom, String image, String description, int nbPlacesTotal, int reservation, Salle salle_id, Coach cours_id) {
        this.nom = nom;
        this.image = image;
        this.description = description;
        this.nb_places_total = nbPlacesTotal;
        this.reservation = reservation;
        this.salle_id = salle_id;
        this.coach_id = cours_id;
    }
    public Cours(String nom, String description, int nbPlacesTotal, int reservation, Salle salle_id, Coach cours_id, String image) {
            
        this.nom = nom;
        this.image = image;
        this.description = description;
        this.nb_places_total = nbPlacesTotal;
        this.reservation = reservation;
        this.salle_id = salle_id;
        this.coach_id = cours_id;
    }
      public Cours(String nom, String description, int nbPlacesTotal, int reservation, String image) {
            
        this.nom = nom;
        this.image = image;
        this.description = description;
        this.nb_places_total = nbPlacesTotal;
        this.reservation = reservation;
       
    }
    public Cours(int id, int nb_places_total, int reservation, String nom, String image, String description, String coach_nom, String salle_nom) {
        this.id = id;
        this.nb_places_total = nb_places_total;
        this.reservation = reservation;
        this.nom = nom;
        this.image = image;
        this.description = description;
        this.coach_nom = coach_nom;
        this.salle_nom = salle_nom;
    }

  
public Cours(int id, String nom,   String description,int nb_places_total, int reservation,String image) {
        this.id = id;
        this.nb_places_total = nb_places_total;
        this.reservation = reservation;
        this.nom = nom;
        this.image = image;
        this.description = description;
     
    }
public Cours( String nom,   String description,int nb_places_total, int reservation, String salle_nom, String coach_nom,String image) {
        this.nb_places_total = nb_places_total;
        this.reservation = reservation;
        this.nom = nom;
          this.coach_nom = coach_nom;
        this.salle_nom = salle_nom;
        this.image = image;
        this.description = description;
     
    }
public List<String> getCoach_nom() {
    List<String> coachNoms = new ArrayList<>();
    try {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Infantry", "root", "root");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT nom FROM coach");
        while (rs.next()) {
            coachNoms.add(rs.getString("nom"));
        }
        rs.close();
        stmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return coachNoms;
}
public List<String> getSalle_nom() {
    List<String> salleNoms = new ArrayList<>();
    try {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Infantry", "root", "root");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT nom FROM salle");
        while (rs.next()) {
            salleNoms.add(rs.getString("nom"));
        }
        rs.close();
        stmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return salleNoms;
}

public Cours(int id,String nom, String description, Salle salle_id, String coach_nom,String image) {
        this.nom = nom;
        this.image = image;
        this.description = description;
    
        this.salle_id = salle_id;
        this.coach_nom = coach_nom;
    }

 

  

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getSalle_idd() {
        return salle_idd;
    }

    public int getCoach_idd() {
        return coach_idd;
    }

   
 // Getter pour coach
    public Coach getCoach() {
        return coach_id;
    }
 public Salle getSalle() {
        return salle_id;
    }
    // Setter pour coach
    public void setCoach(Coach coach) {
        this.coach_id = coach;
    }
     // Setter pour coach
    public void setSalle(Salle salle) {
        this.salle_id = salle;
    }
    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public int getNbPlacesTotal() {
        return nb_places_total;
    }

    public int getReservation() {
        return reservation;
    }

    public Salle getSalle_id() {
        return salle_id;
    }

    public Coach getCours_id() {
        return coach_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }




public String setSalle_nom(String salle_nom) {
    return this.salle_nom=salle_nom;
}
    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNbPlacesTotal(int nbPlacesTotal) {
        this.nb_places_total = nbPlacesTotal;
    }

    public void setReservation(int reservation) {
        this.reservation = reservation;
    }

    public void setSalle_id(Salle salle_id) {
        this.salle_id = salle_id;
    }

    public void setCours_id(Coach cours_id) {
        this.coach_id = cours_id;
    }

   
    
}
