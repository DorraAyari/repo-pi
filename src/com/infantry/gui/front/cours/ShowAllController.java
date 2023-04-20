package com.infantry.gui.front.cours;



import com.infantry.entities.Cours;

import com.infantry.services.ServiceCours;
import com.infantry.utils.Constants;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ShowAllController implements Initializable {
    public static Cours currentCours;

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
            mainVBox.getChildren().add(makeCoachModel(cours));
        }
    } else {
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setPrefHeight(200);
        stackPane.getChildren().add(new Text("Aucune donnée"));
        mainVBox.getChildren().add(stackPane);
    }
}

  
    public Parent makeCoachModel(
            Cours cours
    ) {
        Parent parent = null;
        try {
        parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_COURS)));
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


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

   
}

