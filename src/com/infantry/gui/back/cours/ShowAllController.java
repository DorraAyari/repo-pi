/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.gui.back.cours;

import com.infantry.MainApp;
import com.infantry.entities.Coach;
import com.infantry.entities.Cours;
import com.infantry.entities.Salle;
import com.infantry.gui.back.MainWindowController;
import com.infantry.services.ServiceCours;
import com.infantry.utils.AlertUtils;
import com.infantry.utils.Constants;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author dorraayari
 */
public class ShowAllController implements Initializable {
    public static Cours currentCours;
    public static Object currentBlog;
@FXML
    public VBox mainVBox;
    List<Cours> listCours;

     
    
    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public Button btnAjout;
    // other fields and methods


    /**
     * Initializes the controller class.
     */
   

     @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listCours = ServiceCours.getInstance().readAll();
        displayData();
    }


    void displayData() {
        mainVBox.getChildren().clear();

        Collections.reverse(listCours);

        if (!listCours.isEmpty()) {
            for (Cours cours : listCours) {
                mainVBox.getChildren().add(makeCoursModel(cours));
            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }
       
   public Parent makeCoursModel(Cours cours) {
    Parent parent = null;
    try {
        parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_COURS)));

        HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
        ((Text) innerContainer.lookup("#nomText")).setText("Nom : " + cours.getNom());
        ((Text) innerContainer.lookup("#descriptionText")).setText("Description : " + cours.getDescription());
        ((Text) innerContainer.lookup("#reservationText")).setText("Reservation : " + cours.getReservation());
        ((Text) innerContainer.lookup("#nb_places_totalText")).setText("Places Total : " + cours.getNbPlacesTotal());
        
        // Récupérer le nom de la salle correspondante à partir de son id
        int idSalle = cours.getSalle_idd();
        String salleNom = "";
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Infantry", "root", "root");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nom FROM salle WHERE id = " + idSalle);
            if (rs.next()) {
                salleNom = rs.getString("nom");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ((Text) innerContainer.lookup("#nomSalleText")).setText("Salle : " + salleNom);

        // Récupérer le nom du coach correspondant à partir de son id
        int idCoach = cours.getCoach_idd();
        String coachNom = "";
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Infantry", "root", "root");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nom FROM coach WHERE id = " + idCoach);
            if (rs.next()) {
                coachNom = rs.getString("nom");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ((Text) innerContainer.lookup("#nomCoachText")).setText("Coach : " + coachNom);

        Path selectedImagePath = FileSystems.getDefault().getPath(cours.getImage());
        if (selectedImagePath.toFile().exists()) {
            ((ImageView) innerContainer.lookup("#imageIV")).setImage(new Image(selectedImagePath.toUri().toString()));
        }

        ((Button) innerContainer.lookup("#editButton")).setOnAction((event) -> modifierCours(cours));
        ((Button) innerContainer.lookup("#deleteButton")).setOnAction((event) -> supprimerCours(cours));

    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    return parent;
}

    private void modifierCours(Cours cours) {
        currentCours = cours;

         MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_COURS);
    }

@FXML
private void ajouter(ActionEvent event) throws IOException {
    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_COURSA);
}

    private void supprimerCours(Cours cours) {
                currentCours = null;

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer cours ?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK){
        ServiceCours.getInstance().delete(cours.getId());
        // refresh data after deleting coach
        listCours = ServiceCours.getInstance().readAll();
        displayData();
    }
    }
  
}
