package gui;


import entities.Package;
import entities.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.PackageService;
import services.ReservationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.fxml.Initializable;


import javafx.fxml.Initializable;
import javafx.scene.Node;


import services.PackageService;



public class MenuGestionPackageController implements Initializable {
    private final PackageService packageService = new PackageService();
    private final ReservationService reservationService = new ReservationService();
    private ObservableList<Package> packageList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            packageList.setAll(packageService.recuperer());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void gererPackages(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterPackage.fxml"));
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
private void afficheStat(ActionEvent event) {
    try {
        // Get all the packages from the database
        PackageService packageService = new PackageService();
        List<Package> packageList = packageService.recuperer();

        // Get the number of packages per user
        Map<Integer, Integer> nbPackagesPerUser = new HashMap<>();
        for (Package p : packageList) {
            int userId = p.getUserId();
            if (nbPackagesPerUser.containsKey(userId)) {
                nbPackagesPerUser.put(userId, nbPackagesPerUser.get(userId) + 1);
            } else {
                nbPackagesPerUser.put(userId, 1);
            }
        }

        // Create a PieChart to display the number of packages per user
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<Integer, Integer> entry : nbPackagesPerUser.entrySet()) {
            int userId = entry.getKey();
            int nbPackages = entry.getValue();
            String userLabel = "User " + userId;
            pieChartData.add(new PieChart.Data(userLabel, nbPackages));
        }
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Nombre de packages par utilisateur");

        // Display the PieChart and wait for the user to close the window
        VBox vbox = new VBox(pieChart);
        Scene scene = new Scene(vbox);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}



 @FXML
    private void retournerPackages(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPrincipale.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
               Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
}
