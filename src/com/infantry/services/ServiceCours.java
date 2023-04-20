/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.services;

import com.infantry.entities.Cours;
import com.infantry.utils.DatabaseConnection;
import java.sql.Connection;
import java.sql.DriverManager;
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
public class ServiceCours implements IService<Cours> {

    private static ServiceCours instance;
    PreparedStatement preparedStatement;
    private Connection conn;

    public ServiceCours() {
        conn = DatabaseConnection.getInstance().getConnection();
    }

    public static ServiceCours getInstance() {
        if (instance == null) {
            instance = new ServiceCours();
        }
        return instance;
    }

    @Override

    public void insert(Cours p) {
        String requete = "insert into cours (nom,image,description,nb_places_total,reservation,salle_id,coach_id)"
                + " values (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setString(1, p.getNom());
            pst.setString(2, p.getImage()); // set the image field
            pst.setString(3, p.getDescription()); // set the image field
            pst.setInt(4, p.getNbPlacesTotal());
            pst.setInt(5, p.getReservation());
            pst.setInt(6, p.getSalle_id().getId());
            pst.setInt(7, p.getCours_id().getId());

            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCours.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    @Override
    public void delete(int coursId) {
        String sql = "DELETE FROM cours WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, coursId);
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
    public void update(Cours cours) {
        String req = "UPDATE cours SET nom = ?, description = ?, nb_places_total = ?, reservation = ?,image = ? WHERE id = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(req);
            pst.setString(1, cours.getNom());
            pst.setString(2, cours.getDescription());

            pst.setInt(3, cours.getNbPlacesTotal());
            pst.setInt(4, cours.getReservation());

            pst.setString(5, cours.getImage());
            pst.setInt(6, cours.getId());

            pst.executeUpdate();
            System.out.println("cours updated successfully");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override

    public List<Cours> readAll() {
        List<Cours> list = new ArrayList<>();
        String requete = "SELECT c.*, s.nom as salle_nom, co.nom as coach_nom "
                + "FROM cours c "
                + "JOIN salle s ON c.salle_id = s.id "
                + "JOIN coach co ON c.coach_id = co.id";

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {

                list.add(new Cours(rs.getInt("id"), rs.getString("nom"), rs.getString("image"),
                        rs.getString("description"), rs.getInt("nb_places_total"), rs.getInt("reservation"),
                        rs.getInt("coach_id"), rs.getString("coach_nom"), rs.getInt("salle_id"), rs.getString("salle_nom")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalleService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Cours readById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean checkExist(Cours cours) {
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM `cours` WHERE `nom` = ?");

            preparedStatement.setString(1, cours.getNom());

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException exception) {
            System.out.println("Error (readAll) sdp : " + exception.getMessage());
        }
        return false;
    }

    public boolean add(Cours cours) {

        if (checkExist(cours)) {
            return false;
        }

        String request = "insert into cours (nom,description,nb_places_total,reservation,image,salle_id,coach_id)"
                + " values (?,?,?,?,?,?,?)";
        try {
            preparedStatement = conn.prepareStatement(request);
            preparedStatement.setString(1, cours.getNom());
            preparedStatement.setString(2, cours.getDescription());

            preparedStatement.setInt(3, cours.getNbPlacesTotal());
            preparedStatement.setInt(4, cours.getReservation());

            preparedStatement.setString(5, cours.getImage());
            preparedStatement.setInt(6, cours.getSalle_id().getId());
            preparedStatement.setInt(7, cours.getCours_id().getId());


            preparedStatement.executeUpdate();
            System.out.println("Cours added");
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("Error (add) cours : " + exception.getMessage());
        }
        return false;
    }

public boolean edit(Cours cours) {
    String request = "UPDATE cours SET nom = ?, description = ?, nb_places_total = ?, reservation = ?, image = ?, salle_id = ?, coach_id = ? WHERE id = ?";

    try {
        preparedStatement = conn.prepareStatement(request);
        preparedStatement.setString(1, cours.getNom());
        preparedStatement.setString(2, cours.getDescription());
        preparedStatement.setInt(3, cours.getNbPlacesTotal());
        preparedStatement.setInt(4, cours.getReservation());
        preparedStatement.setString(5, cours.getImage());
     preparedStatement.setInt(6, cours.getSalle_id().getId());
            preparedStatement.setInt(7, cours.getCours_id().getId());

        preparedStatement.setInt(8, cours.getId());

        preparedStatement.executeUpdate();
        System.out.println("Cours edited");
        return true;
    } catch (SQLException exception) {
        System.out.println("Error (edit) cours : " + exception.getMessage());
    }
    return false;
}

}
