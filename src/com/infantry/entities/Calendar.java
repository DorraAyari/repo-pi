/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.entities;

import java.util.Date;

/**
 *
 * @author dorraayari
 */
public class Calendar {
    String title,description,background_color,border_color,text_color;
    Date start,end;
    int id;
      Salle salle_id;
    Coach coach_id;
    Cours cours_id;
    int salle_idd;
    int coach_idd;
 String coach_nom;
    String salle_nom;
        String cours_nom;

    public Calendar(String title, String description, String background_color, String border_color, String text_color, Date start, Date end, int id, Salle salle_id, Coach coach_id, Cours cours_id, int salle_idd, int coach_idd, String coach_nom, String salle_nom, String cours_nom) {
        this.title = title;
        this.description = description;
        this.background_color = background_color;
        this.border_color = border_color;
        this.text_color = text_color;
        this.start = start;
        this.end = end;
        this.id = id;
        this.salle_id = salle_id;
        this.coach_id = coach_id;
        this.cours_id = cours_id;
        this.salle_idd = salle_idd;
        this.coach_idd = coach_idd;
        this.coach_nom = coach_nom;
        this.salle_nom = salle_nom;
        this.cours_nom = cours_nom;
    }

    public Calendar(String title, String description, String background_color, String border_color, String text_color, Date start, Date end, Salle salle_id, Coach coach_id, Cours cours_id, int salle_idd, int coach_idd, String coach_nom, String salle_nom, String cours_nom) {
        this.title = title;
        this.description = description;
        this.background_color = background_color;
        this.border_color = border_color;
        this.text_color = text_color;
        this.start = start;
        this.end = end;
        this.salle_id = salle_id;
        this.coach_id = coach_id;
        this.cours_id = cours_id;
        this.salle_idd = salle_idd;
        this.coach_idd = coach_idd;
        this.coach_nom = coach_nom;
        this.salle_nom = salle_nom;
        this.cours_nom = cours_nom;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getBackground_color() {
        return background_color;
    }

    public String getBorder_color() {
        return border_color;
    }

    public String getText_color() {
        return text_color;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public int getId() {
        return id;
    }

    public Salle getSalle_id() {
        return salle_id;
    }

    public Coach getCoach_id() {
        return coach_id;
    }

    public Cours getCours_id() {
        return cours_id;
    }

    public int getSalle_idd() {
        return salle_idd;
    }

    public int getCoach_idd() {
        return coach_idd;
    }

    public String getCoach_nom() {
        return coach_nom;
    }

    public String getSalle_nom() {
        return salle_nom;
    }

    public String getCours_nom() {
        return cours_nom;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBackground_color(String background_color) {
        this.background_color = background_color;
    }

    public void setBorder_color(String border_color) {
        this.border_color = border_color;
    }

    public void setText_color(String text_color) {
        this.text_color = text_color;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSalle_id(Salle salle_id) {
        this.salle_id = salle_id;
    }

    public void setCoach_id(Coach coach_id) {
        this.coach_id = coach_id;
    }

    public void setCours_id(Cours cours_id) {
        this.cours_id = cours_id;
    }

    public void setSalle_idd(int salle_idd) {
        this.salle_idd = salle_idd;
    }

    public void setCoach_idd(int coach_idd) {
        this.coach_idd = coach_idd;
    }

    public void setCoach_nom(String coach_nom) {
        this.coach_nom = coach_nom;
    }

    public void setSalle_nom(String salle_nom) {
        this.salle_nom = salle_nom;
    }

    public void setCours_nom(String cours_nom) {
        this.cours_nom = cours_nom;
    }

    @Override
    public String toString() {
        return "Calendar{" + "title=" + title + ", description=" + description + ", background_color=" + background_color + ", border_color=" + border_color + ", text_color=" + text_color + ", start=" + start + ", end=" + end + ", id=" + id + ", salle_id=" + salle_id + ", coach_id=" + coach_id + ", cours_id=" + cours_id + ", salle_idd=" + salle_idd + ", coach_idd=" + coach_idd + ", coach_nom=" + coach_nom + ", salle_nom=" + salle_nom + ", cours_nom=" + cours_nom + '}';
    }
        
}
