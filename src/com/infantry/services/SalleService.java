/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.services;

import com.infantry.entities.Salle;
import com.infantry.utils.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataSource;

/**
 *
 * @author dorraayari
 */
public class SalleService  implements IService<Salle>{
 private static SalleService instance;
    PreparedStatement preparedStatement;
    Connection connection;
  private Connection conn;
      public SalleService() {
        conn=DatabaseConnection.getInstance().getConnection();
    }
public static SalleService getInstance() {
        if (instance == null) {
            instance = new SalleService();
        }
        return instance;
    }
      @Override
    public void insert(Salle t) {
        String requete ="insert into salle(nom,description)"+"values('"+t.getNom()+"','"+t.getDescription()+"')";
    try {
        Statement ste =conn.createStatement();
        ste.executeUpdate(requete);
    } catch (SQLException ex) {
        Logger.getLogger(SalleService.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
 public void insertPst(Salle p) {
        String requete = "insert into salle (nom,description)"
                + " values (?,?)";
        try {
            PreparedStatement pst=conn.prepareStatement(requete);
            pst.setString(1, p.getNom());
            pst.setString(2, p.getDescription());
         
        
            pst.executeUpdate();
 
        } catch (SQLException ex) {
            Logger.getLogger(SalleService.class.getName()).log(Level.SEVERE, null, ex);
        }
             
    }
   
   @Override
public void delete(int salleId) {
    String sql = "DELETE FROM salle WHERE id = ?";
    try (PreparedStatement statement = conn.prepareStatement(sql)) {
        statement.setInt(1, salleId);
        System.out.println("SQL query: " + statement.toString());
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted == 0) {
            throw new SQLException("Failed to delete salle, no rows affected.");
        }
        System.out.println(rowsDeleted + " rows deleted.");
    } catch (SQLException ex) {
        System.out.println("Error deleting salle: " + ex.getMessage());
    }
}
  public void update(Salle salle) {
    String req = "UPDATE salle SET nom = ?, description = ? WHERE id = ?";
    try {
        PreparedStatement pst = conn.prepareStatement(req);
        pst.setString(1, salle.getNom());
        pst.setString(2, salle.getDescription());
     
        pst.setInt(3, salle.getId());
        pst.executeUpdate();
        System.out.println("salle updated successfully");
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
}

         @Override
    public List<Salle> readAll() {
               List<Salle> list = new ArrayList<>();
               String requete= "select * from salle";

    try { 
        Statement st = conn.createStatement();
        ResultSet rs= st.executeQuery(requete);
        while(rs.next()){

            list.add(new Salle(rs.getInt("id"),rs.getString("nom"),
                    rs.getString("description")
                   
                       ));
        }
    } catch (SQLException ex) {
        Logger.getLogger(SalleService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
    }
    @Override
    public Salle readById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
 public boolean checkExist(Salle salle) {
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM `salle` WHERE `nom` = ?");

            preparedStatement.setString(1, salle.getNom());

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException exception) {
            System.out.println("Error (readAll) sdp : " + exception.getMessage());
        }
        return false;
    }
  public boolean add(Salle salle) {

        if (checkExist(salle)) {
            return false;
        }

        String request = "insert into salle (nom,description)"
                + " values (?,?)";
        try {
            preparedStatement = conn.prepareStatement(request);

            preparedStatement.setString(1, salle.getNom());
            preparedStatement.setString(2, salle.getDescription());
           

            preparedStatement.executeUpdate();
            System.out.println("Salle added");
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("Error (add) salle : " + exception.getMessage());
        }
        return false;
    }
public boolean edit(Salle salle) {
    
String request = "UPDATE `salle` SET `nom` = ?, `description` = ? WHERE `id` = ?";


    try {
        preparedStatement = conn.prepareStatement(request);
        preparedStatement.setString(1, salle.getNom());
        preparedStatement.setString(2, salle.getDescription());
       
        preparedStatement.setInt(3, salle.getId());
        preparedStatement.executeUpdate();
        System.out.println("Salle edited");
        return true;
    } catch (SQLException exception) {
        System.out.println("Error (edit) salle : " + exception.getMessage());
    }
    return false;
}


   
}

