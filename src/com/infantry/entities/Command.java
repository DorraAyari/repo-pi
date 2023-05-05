/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.entities;

/**
 *
 * @author AA
 */
public class Command {
        int id;
        Produit cmd_id;

    public Command() {
    }

    public Command(Produit cmd_id) {
        this.cmd_id = cmd_id;
    }

    public Command(int id, Produit cmd_id) {
        this.id = id;
        this.cmd_id = cmd_id;
    }
        
        

    public int getId() {
        return id;
    }

    public Produit getCmd_id() {
        return cmd_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCmd_id(Produit cmd_id) {
        this.cmd_id = cmd_id;
    }

    @Override
    public String toString() {
        return "Command{" + "id=" + id + ", cmd_id=" + cmd_id + '}';
    }
        
    
}
