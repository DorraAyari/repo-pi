package gui;

import entities.Reservation;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import services.ReservationService;

public class MenuGestionReservationController implements Initializable {

    private ReservationService reservationService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reservationService = new ReservationService();
    }

    @FXML
    private void gererReservation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterReservation.fxml"));
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
    private void statistiques(ActionEvent event) throws SQLException {
        // Get all the dates used in the input
        List<String> datesStr = reservationService.getAllDates();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Convert the dates from String to LocalDate
        List<LocalDate> dates = datesStr.stream()
                .map(str -> LocalDate.parse(str, formatter))
                .collect(Collectors.toList());

        // Count the number of reservations created on each date
        Map<LocalDate, Long> counts = new HashMap<>();
        List<Reservation> reservations = reservationService.recuperer();
        for (LocalDate date : dates) {
            long count = reservations.stream()
                    .filter(r -> r.getDay().equals(java.sql.Date.valueOf(date)))
                    .count();
            counts.put(date, count);
        }

        // Create a bar chart to display the counts
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Statistiques");
        xAxis.setLabel("Date");
        yAxis.setLabel("Nombre de réservations");

        // Add the data to the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Map.Entry<LocalDate, Long> entry : counts.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey().toString(), entry.getValue()));
        }
        chart.getData().add(series);

        // Display the chart in a dialog box
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Statistiques");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setContent(chart);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.showAndWait();
    }
    
      @FXML
    private void retourner(ActionEvent event) throws IOException {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPrinicipale.fxml"));
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