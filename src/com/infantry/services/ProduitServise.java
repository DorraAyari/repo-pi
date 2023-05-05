/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.services;

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
public class ProduitServise implements IService<Produit> {

    private static ProduitServise instance;
    PreparedStatement preparedStatement;
    Connection connection;
    private Connection conn;

    public ProduitServise() {
        conn = DatabaseConnection.getInstance().getConnection();
    }

    public static ProduitServise getInstance() {
        if (instance == null) {
            instance = new ProduitServise();
        }
        return instance;
    }

    @Override
    public void insert(Produit t) {
        String requete = "insert into produit(nom,description,prix,image)" + "values('" + t.getNom() + "','" + t.getDescription() + "','" + t.getPrix() + "','" + t.getImage() + "')";
        try {
            Statement ste = conn.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(ProduitServise.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertPst(Produit p) {
        String requete = "insert into produit (nom,description,prix,image)"
                + " values (?,?,?,?)";
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setString(1, p.getNom());
            pst.setString(2, p.getDescription());

            pst.setInt(3, p.getPrix());
            pst.setString(4, p.getImage()); // set the image field

            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ProduitServise.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public boolean edit(Produit p) {
        String req = "UPDATE produit SET `nom` = ?, `description` = ?, `prix` = ? , `image` = ? WHERE `id` = ? ";
        try {
            PreparedStatement pst = conn.prepareStatement(req);
            pst.setString(1, p.getNom());
            pst.setString(2, p.getDescription());
            pst.setInt(3, p.getPrix());
            pst.setString(4, p.getImage());
            pst.setInt(5, p.getId());

            pst.executeUpdate();
            System.out.println("produit updated successfully");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean add(Produit t) {
        if (checkExist(t)) {
            return false;
        }

        String request = "insert into produit (nom,description,prix,image)"
                + " values (?,?,?,?)";
        try {
            preparedStatement = conn.prepareStatement(request);

            preparedStatement.setString(1, t.getNom());
            preparedStatement.setString(2, t.getDescription());
            preparedStatement.setInt(3, t.getPrix());
            preparedStatement.setString(4, t.getImage());

            preparedStatement.executeUpdate();
            System.out.println("produit added");
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("Error (add) produit : " + exception.getMessage());
        }
        return false;
    }

    public boolean checkExist(Produit produit) {
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM `produit` WHERE `nom` = ?");

            preparedStatement.setString(1, produit.getNom());

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException exception) {
            System.out.println("Error (readAll) sdp : " + exception.getMessage());
        }
        return false;
    }

    @Override
    public void delete(int pId) {
        String sql = "DELETE FROM produit WHERE id = ?";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, pId);
            System.out.println("SQL query: " + statement.toString());
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                throw new SQLException("Failed to delete produit no rows affected.");
            }
            System.out.println(rowsDeleted + " rows deleted.");
        } catch (SQLException ex) {
            System.out.println("Error deleting cproduit: " + ex.getMessage());
        }
    }

    @Override
    public void update(Produit t) {
        String request = "UPDATE produit SET nom = ?, description = ?, prix = ? image = ? WHERE id = ?";

    }

    @Override
    public List<Produit> readAll() {
        List<Produit> list = new ArrayList<>();
        String requete = "select * from produit";

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {

                list.add(new Produit(rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getInt("prix"),
                        rs.getString("image")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitServise.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Produit readById(int id) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM produit WHERE `id` LIKE ?");

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Produit produit = new Produit(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("description"),
                        resultSet.getInt("prix"),
                        resultSet.getString("image")
                );

                return produit;

            }
        } catch (SQLException e) {
            System.out.println("Aucun id : " + e.getMessage());
        }
        return null;
    }
    public Produit getProduitById(int id) {
        String requete = "select * from produit where id = ?";
        Produit blog =  null;
        try{
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1,id);
            ResultSet rs = pst.executeQuery();
            BlogService rss = new BlogService();
           
            if(rs.next()){
            //reservation = new Reservation(rs.getInt(1), rs.getString(5), rs.getDate(2).toLocalDate(), rs.getString(3), rs.getString(4),rs.getInt(6),rs.getInt(7),ts.readByID(rs.getInt(8)),rs.getString(9));
            blog = new Produit(rs.getString(1), rs.getString(2),rs.getInt(3),rs.getString(4));

            }
        }catch (SQLException ex) {
            System.out.println("Error retrieving command: " + ex.getMessage());
        }
        return blog;
    }
    

}
