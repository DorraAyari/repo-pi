/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import com.infantry.utils.DatabaseConnection;
import java.sql.SQLException;
import java.util.List;
import entities.Package;
import entities.RP;
import entities.Reservation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author mrdje
 */
public class RPService implements IServiceRP{
    
    
        Connection cnx;
    String sql="";

    public RPService() {
        cnx = DatabaseConnection.getInstance().getConnection();

    
    
    }

    /**
     *
     * @return
     */
public List<RP> recuperer() {
    try {
        PreparedStatement st = cnx.prepareStatement("SELECT r.id AS id_reservation, p.id AS id_package, r.start, r.finish, p.choices \n" +
"FROM reservation r  \n" +
"JOIN package p ON r.user_id = p.user_id");
        ResultSet rs = st.executeQuery();
        List<RP> RPs = new ArrayList<>();
        while (rs.next()) {
            RP rp = new RP();
            rp.setIdR(rs.getInt("id_reservation"));
            rp.setIdP(rs.getInt("id_package"));
            rp.setStart(rs.getDate("start"));
            rp.setFinish(rs.getDate("finish"));
            rp.setChoices(rs.getString("choices"));

            RPs.add(rp);
        }
        return RPs;
    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }
}


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    @Override
    public void ajouter(Object t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void modifier(Object t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void supprimer(Object t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   
    
}
