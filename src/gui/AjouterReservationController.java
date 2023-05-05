package gui;
import entities.Reservation;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.ReservationService;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class AjouterReservationController {



    @FXML
    private DatePicker startF;

    @FXML
    private DatePicker finishF;

    @FXML
    private TextField capacityF;

    @FXML
    private DatePicker dayF;

    @FXML
    private Button ajouterBtn;

    @FXML
    private Button retournerBtn;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        // TODO
    }

    @FXML
private void ajouterReservation() {
LocalDate start = startF.getValue();
LocalDate finish = finishF.getValue();
String capacityStr = capacityF.getText();
LocalDate day = dayF.getValue();
if (start == null || finish == null || capacityStr.isEmpty() || day == null) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error Message");
    alert.setHeaderText(null);
    alert.setContentText("Please fill in all the required fields");
    alert.showAndWait();
    return;
}

DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
LocalDateTime currentDateTime = LocalDateTime.now();
Date startDate;
Date finishDate;
int capacity;
Date dayDate;
try {
    startDate = Date.from(LocalDateTime.of(start, currentDateTime.toLocalTime()).atZone(ZoneId.systemDefault()).toInstant());
    finishDate = Date.from(LocalDateTime.of(finish, currentDateTime.toLocalTime()).atZone(ZoneId.systemDefault()).toInstant());
    capacity = Integer.parseInt(capacityStr);
    dayDate = Date.from(day.atStartOfDay(ZoneId.systemDefault()).toInstant());
} catch (Exception e) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error Message");
    alert.setHeaderText(null);
    alert.setContentText("Invalid date format");
    alert.showAndWait();
    return;
}

if (startDate.after(finishDate)) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error Message");
    alert.setHeaderText(null);
    alert.setContentText("Start date must be before finish date");
    alert.showAndWait();
    return;
}

Reservation reservation = new Reservation(startDate, finishDate, capacity, dayDate);

try {
    ReservationService reservationService = new ReservationService();
    reservationService.ajouter(reservation);
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Success Message");
    alert.setHeaderText(null);
    alert.setContentText("Reservation added successfully");
    alert.showAndWait();

    // create the email message
} catch (SQLException e) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error Message");
    alert.setHeaderText(null);
    alert.setContentText("An error occurred while adding the reservation");
    alert.showAndWait();
}
}
    
        @FXML
    private void afficherRes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherReservation.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
          @FXML
    private void retourner(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuGestionReservation.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
