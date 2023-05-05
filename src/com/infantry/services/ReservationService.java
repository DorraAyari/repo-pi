package services;

import com.infantry.utils.DatabaseConnection;
import entities.Reservation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class ReservationService implements IServiceR<Reservation> {

    Connection cnx;

    public ReservationService() {
        cnx = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void ajouter(Reservation r) throws SQLException {
        String req = "INSERT INTO reservation (start, finish, capacity, day) VALUES (?, ?, ?, ?)";
        PreparedStatement pst = cnx.prepareStatement(req);
        pst.setTimestamp(1, new java.sql.Timestamp(r.getStart().getTime()));
        pst.setTimestamp(2, new java.sql.Timestamp(r.getFinish().getTime()));
        pst.setInt(3, r.getCapacity());
        pst.setDate(4, new java.sql.Date(r.getDay().getTime()));
        pst.executeUpdate();
    }

    @Override
    public void supprimer(Reservation r) throws SQLException {
        String req = "DELETE FROM reservation WHERE id = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, r.getId());
        ps.executeUpdate();
    }

    @Override
    public List<Reservation> recuperer() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String req = "SELECT * FROM reservation";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            Reservation r = new Reservation(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getTimestamp("start"),
                    rs.getTimestamp("finish"),
                    rs.getInt("capacity"),
                    rs.getDate("day")
            );
            reservations.add(r);
        }
        return reservations;
    }
    
    public List<String> getAllDates() throws SQLException {
        List<String> dates = new ArrayList<>();
        String req = "SELECT DISTINCT day FROM reservation";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            String date = rs.getDate("day").toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            dates.add(date);
        }
        return dates;
    }

    public Reservation recupererById(int id) throws SQLException {
        String req = "SELECT * FROM reservation WHERE id = ?";
        PreparedStatement st = cnx.prepareStatement(req);
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        rs.next();
        Reservation r = new Reservation(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getTimestamp("start"),
                rs.getTimestamp("finish"),
                rs.getInt("capacity"),
                rs.getDate("day")
        );
        return r;
    }

    @Override
    public void modifier(Reservation r) throws SQLException {
        String req = "UPDATE reservation SET start = ?, finish = ?, capacity = ?, day = ? WHERE id = ?";
        PreparedStatement pst = cnx.prepareStatement(req);
        pst.setTimestamp(1, new java.sql.Timestamp(r.getStart().getTime()));
        pst.setTimestamp(2, new java.sql.Timestamp(r.getFinish().getTime()));
        pst.setInt(3, r.getCapacity());
        pst.setDate(4, new java.sql.Date(r.getDay().getTime()));
        pst.setInt(5, r.getId());
        pst.executeUpdate();
    }

  public List<Reservation> rechercheByUserId(int userId) throws SQLException {
    List<Reservation> reservations = new ArrayList<>();
    String req = "SELECT * FROM reservation WHERE user_id = ?";
    PreparedStatement pst = cnx.prepareStatement(req);
    pst.setInt(1, userId);
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
        Reservation r = new Reservation(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getTimestamp("start"),
                rs.getTimestamp("finish"),
                rs.getInt("capacity"),
                rs.getDate("day")
        );
        reservations.add(r);
    }
    return reservations;
}


                }