package services;

import com.infantry.utils.DatabaseConnection;
import entities.Package;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class PackageService implements IServiceR<Package> {

    Connection cnx;

    public PackageService() {
        cnx = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void ajouter(Package p) throws SQLException {
        String req = "INSERT INTO package (choices) VALUES (?)";
        PreparedStatement pst = cnx.prepareStatement(req);
        pst.setString(1, p.getChoices());
        pst.executeUpdate();
    }

    @Override
    public void supprimer(Package p) throws SQLException {
        String req = "DELETE FROM package WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, p.getId());
        ps.executeUpdate();
    }

    @Override
    public List<Package> recuperer() throws SQLException {
        List<Package> packages = new ArrayList<>();
        String req = "SELECT * FROM package";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Package p = new Package();
            p.setId(rs.getInt("id"));
            p.setUserId(rs.getInt("user_id"));
            p.setChoices(rs.getString("choices"));
            packages.add(p);
        }
        return packages;
    }

    public Package recupererById(int id) throws SQLException {
        String req = "SELECT * FROM package WHERE id = ?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        rs.next();
        Package p = new Package();
        p.setId(rs.getInt("id"));
        p.setUserId(rs.getInt("user_id"));
        p.setChoices(rs.getString("choices"));
        return p;
    }

    @Override
    public void modifier(Package p) throws SQLException {
        String req = "UPDATE package SET choices = ? WHERE id = ?";
        PreparedStatement pst = cnx.prepareStatement(req);
        pst.setString(1, p.getChoices());
        pst.setInt(2, p.getId());
        pst.executeUpdate();
    }

    public List<Package> rechercheByUserId(int userId) throws SQLException {
        List<Package> packages = new ArrayList<>();
        String req = "SELECT * FROM package WHERE user_id = ?";
        PreparedStatement pst = cnx.prepareStatement(req);
        pst.setInt(1, userId);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Package p = new Package();
            p.setId(rs.getInt("id"));
            p.setUserId(rs.getInt("user_id"));
            p.setChoices(rs.getString("choices"));
            packages.add(p);
        }
        return packages;
    }

    public List<Package> trierParId() throws SQLException {
        List<Package> packages = new ArrayList<>();
        String req = "SELECT * FROM package ORDER BY id";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Package p = new Package();
            p.setId(rs.getInt("id"));
            p.setUserId(rs.getInt("user_id"));
            p.setChoices(rs.getString("choices"));
            packages.add(p);
        }
        return packages;
    }
}
