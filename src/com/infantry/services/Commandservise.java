/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.services;

import com.infantry.entities.Command;
import com.infantry.entities.Produit;
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

/**
 *
 * @author AA
 */
public class Commandservise implements IService<Command>{
      private static ServiceCours instance;
    PreparedStatement preparedStatement;
    private Connection conn;

    public Commandservise() {
        conn = DatabaseConnection.getInstance().getConnection();
    }

    public static ServiceCours getInstance() {
        if (instance == null) {
            instance = new ServiceCours();
        }
        return instance;
    }


    @Override
    public void insert(Command t) {
      {
        String requete = "insert into command(cmd_id)" + "values('" + t.getCmd_id() + "')";
        try {
            Statement ste = conn.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(ProduitServise.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }
     
       
    @Override
    public boolean edit(Command t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
      public boolean add(Command command) {return false;
}
      
      
      
      public boolean addCommand(Command command) {

        String request = "insert into command (cmd_id)"
                + " values (?)";
        try {
            preparedStatement = conn.prepareStatement(request);
           
            preparedStatement.setInt(1, command.getCmd_id().getId());
          


            preparedStatement.executeUpdate();
            System.out.println("commander avec succé");
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("Error (add) command : " + exception.getMessage());
        }
        return false;
    }

    @Override
    public void delete(int cId) {
        String sql = "DELETE FROM command WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, cId);
            System.out.println("SQL query: " + statement.toString());
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                throw new SQLException("Failed to delete cours, no rows affected.");
            }
            System.out.println(rowsDeleted + " rows deleted.");
        } catch (SQLException ex) {
            System.out.println("Error deleting salle: " + ex.getMessage());
        }
    }

    @Override
    public void update(Command t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Command> readAll() { List<Command> list = new ArrayList<>();
        String requete = "SELECT p.*, c.nom as cmd_id "
                + "FROM produit p "
                + "JOIN command c ON c.cmd_id = p.id "
               ;

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {

               // list.add(new Command (rs.getInt("id"),
                    //    rs.getInt("cmd_id")
                      
               // ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalleService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Command readById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
