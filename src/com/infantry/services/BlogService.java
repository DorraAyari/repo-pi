/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.services;

import com.infantry.entities.Blog;
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
 * @author EMNA
 */
public class BlogService implements IService<Blog>{
    private static BlogService instance;
    PreparedStatement preparedStatement;
    Connection connection;
  private Connection conn;
    public BlogService() {
        conn=DatabaseConnection.getInstance().getConnection();
    }
public static BlogService getInstance() {
        if (instance == null) {
            instance = new BlogService();
        }
        return instance;
    }
      @Override
    public void insert(Blog t) {
        String requete ="insert into blog(nom,description,moredescreption,slogan)"+"values('"+t.getNom()+"','"+t.getDescription()+"','"+t.getMoredescreption()+"','"+t.getSlogan()+"')";
    try {
        Statement ste =conn.createStatement();
        ste.executeUpdate(requete);
    } catch (SQLException ex) {
        Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
      

    public void insertPst(Blog p) {
        String requete = "insert into blog (nom,description,moredescreption,slogan,image)"
                + " values (?,?,?,?,?)";
        try {
            PreparedStatement pst=conn.prepareStatement(requete);
            pst.setString(1, p.getNom());
            pst.setString(2, p.getDescription());
            pst.setString(3, p.getMoredescreption());
             pst.setString(4, p.getSlogan());
            
             pst.setString(5, p.getImage()); // set the image field
        
            pst.executeUpdate();
 
        } catch (SQLException ex) {
            Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
        }
             
    }
public void update(Blog blog) {
    String req = "UPDATE blog SET nom = ?, description = ?, moredescreption = ?, slogan = ?, image = ? WHERE id = ?";
    try {
        PreparedStatement pst = conn.prepareStatement(req);
        pst.setString(1, blog.getNom());
        pst.setString(2, blog.getDescription());
        pst.setString(3, blog.getMoredescreption());
        pst.setString(4, blog.getSlogan());
        pst.setString(5, blog.getImage());
        pst.setInt(6, blog.getId());
        pst.executeUpdate();
        System.out.println("blog updated successfully");
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
}

@Override
public void delete(int blogId) {
    String sql = "DELETE FROM blog WHERE id = ?";
    try (PreparedStatement statement = conn.prepareStatement(sql)) {
        statement.setInt(1, blogId);
        System.out.println("SQL query: " + statement.toString());
        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted == 0) {
            throw new SQLException("Failed to delete blog, no rows affected.");
        }
        System.out.println(rowsDeleted + " rows deleted.");
    } catch (SQLException ex) {
        System.out.println("Error deleting blog: " + ex.getMessage());
    }
}




      @Override
    public List<Blog> readAll() {
               List<Blog> list = new ArrayList<>();
               String requete= "select * from blog";

    try { 
        Statement st = conn.createStatement();
        ResultSet rs= st.executeQuery(requete);
        while(rs.next()){

            list.add(new Blog(rs.getString("nom"),
                    rs.getString("description"),
                    rs.getString("moredescreption"),
                    rs.getString("slogan"),
                       rs.getInt("id"),
                     rs.getString("image")));
        }
    } catch (SQLException ex) {
        Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
    }
    
      public Blog readById(int id) {

        try {
          PreparedStatement  preparedStatement = conn.prepareStatement("SELECT * FROM blog WHERE `id` LIKE ?");

           preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Blog blog = new Blog(
                        resultSet.getString("nom"),
                        resultSet.getString("description"),
                        resultSet.getString("moredescreption"),
                        resultSet.getString("slogan"),
                        resultSet.getInt("id"),
                        resultSet.getString("image")

                );

              
                    return blog;
             

            }
        } catch (SQLException e) {
            System.out.println("Aucun id : " + e.getMessage());
        }

        return null;
    }
      
    public boolean checkExist(Blog blog) {
        try {
            preparedStatement = conn.prepareStatement("SELECT * FROM `blog` WHERE `nom` = ?");

            preparedStatement.setString(1, blog.getNom());

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();

        } catch (SQLException exception) {
            System.out.println("Error (readAll) sdp : " + exception.getMessage());
        }
        return false;
    }
  public boolean add(Blog blog) {

        if (checkExist(blog)) {
            return false;
        }

        String request = "insert into blog (nom,description,moredescreption,slogan,image)"
                + " values (?,?,?,?,?)";
        try {
            preparedStatement = conn.prepareStatement(request);

            preparedStatement.setString(1, blog.getNom());
            preparedStatement.setString(2, blog.getDescription());
             preparedStatement.setString(3, blog.getMoredescreption());
            preparedStatement.setString(4, blog.getSlogan());
           
            preparedStatement.setString(5, blog.getImage());

            preparedStatement.executeUpdate();
            System.out.println("Blog added");
            return true;
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.out.println("Error (add) blog : " + exception.getMessage());
        }
        return false;
    }
public boolean edit(Blog blog) {
    
String request = "UPDATE `blog` SET `nom` = ?, `description` = ?, `moredescreption` = ?, `slogan` = ?, `image` = ? WHERE `id` = ?";


    try {
        preparedStatement = conn.prepareStatement(request);
        preparedStatement.setString(1, blog.getNom());
        preparedStatement.setString(2, blog.getDescription());
        preparedStatement.setString(3, blog.getMoredescreption());
        preparedStatement.setString(4, blog.getSlogan());
       
        preparedStatement.setString(5, blog.getImage());
        preparedStatement.setInt(6, blog.getId());
        preparedStatement.executeUpdate();
        System.out.println("Blog edited");
        return true;
    } catch (SQLException exception) {
        System.out.println("Error (edit) blog : " + exception.getMessage());
    }
    return false;
}

    
}
