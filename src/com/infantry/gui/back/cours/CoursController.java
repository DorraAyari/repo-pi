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
import com.infantry.services.CoachService;
import com.infantry.services.SalleService;
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
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author dorraayari
 */
public class CoursController implements Initializable {

    @FXML
    private ComboBox<Salle> salleComboBox;
    @FXML
    private ComboBox<Coach> coachComboBox;
    @FXML
    private Button btnAjout;
    @FXML
    private Button addButtonn;
    @FXML
    private TextField nomField;

    @FXML
    private ImageView imageIV;

    private String imagePath = "";

    @FXML
    private TextField imageField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField nbPlacesTotalField;

    @FXML
    private TextField reservationField;
    @FXML
    public Text topText;
    Cours currentCours;

    Path selectedImagePath;
    boolean imageEdited;

    /**
     * Initializes the controller class.
     */
    @FXML
    public void chooseImage(ActionEvent actionEvent) {

        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(MainApp.mainStage);
        if (file != null) {
            selectedImagePath = Paths.get(file.getPath());
            imageIV.setImage(new Image(file.toURI().toString()));
        }
    }

    public void createImageFile() {
        try {
            Path newPath = FileSystems.getDefault().getPath("src/com/infantry/images/uploads/" + selectedImagePath.getFileName());
            Files.copy(selectedImagePath, newPath, StandardCopyOption.REPLACE_EXISTING);
            selectedImagePath = newPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<String> getCoach_nom() {
    List<String> coachNoms = new ArrayList<>();
    try {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Infantry", "root", "root");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT nom FROM coach");
        while (rs.next()) {
            coachNoms.add(rs.getString("nom"));
        }
        rs.close();
        stmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return coachNoms;
}
public List<String> getSalle_nom() {
    List<String> salleNoms = new ArrayList<>();
    try {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Infantry", "root", "root");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT nom FROM salle");
        while (rs.next()) {
            salleNoms.add(rs.getString("nom"));
        }
        rs.close();
        stmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return salleNoms;
}
@Override
public void initialize(URL url, ResourceBundle rb) {
    
    SalleService salleService = new SalleService();
    ObservableList<Salle> salleList = FXCollections.observableArrayList(salleService.readAll());
    Collections.sort(salleList, Comparator.comparing(Salle::getNom));
    salleComboBox.setItems(salleList);

    CoachService coachService = new CoachService();
    ObservableList<Coach> coachList = FXCollections.observableArrayList(coachService.readAll());
    Collections.sort(coachList, Comparator.comparing(Coach::getNom));
    coachComboBox.setItems(coachList);

    salleComboBox.setConverter(new StringConverter<Salle>() {
    @Override
    public String toString(Salle salle) {
        return salle.getNom();
    }

    @Override
    public Salle fromString(String nom) {
        return null;
    }
});
    coachComboBox.setConverter(new StringConverter<Coach>() {
        @Override
        public String toString(Coach coach) {
            return coach.getNom();
        }

        @Override
        public Coach fromString(String nom) {
            return coachList.stream().filter(coach -> coach.getNom().equals(nom)).findFirst().orElse(null);
        }
    });


    currentCours = ShowAllController.currentCours;

    if (currentCours != null) {
        topText.setText("Modifier cours");
        btnAjout.setText("Modifier");

        try {
            nomField.setText(currentCours.getNom());
            descriptionField.setText(currentCours.getDescription());
            nbPlacesTotalField.setText(String.valueOf(currentCours.getNbPlacesTotal()));
            reservationField.setText(String.valueOf(currentCours.getReservation()));

            salleComboBox.setValue(currentCours.getSalle());
            coachComboBox.setValue(currentCours.getCoach());

            selectedImagePath = FileSystems.getDefault().getPath(currentCours.getImage());
            if (selectedImagePath.toFile().exists()) {
                imageIV.setImage(new Image(selectedImagePath.toUri().toString()));
            }

        } catch (NullPointerException ignored) {
            System.out.println("NullPointerException");
        }
    } else {
        topText.setText("Ajouter cours");
        btnAjout.setText("Ajouter");
    }
}


@FXML
private void ajouter(ActionEvent event) {
    if (controleDeSaisie()) {
        String imagePath;
        if (imageEdited) {
            imagePath = currentCours.getImage();
        } else {
            createImageFile();
            imagePath = selectedImagePath.toString();
        }

        Cours cours = new Cours(nomField.getText(), descriptionField.getText(),
                Integer.parseInt(nbPlacesTotalField.getText()), Integer.parseInt(reservationField.getText()),
                salleComboBox.getValue(), coachComboBox.getValue(), imagePath);

       
        if (currentCours == null) {
            if (ServiceCours.getInstance().add(cours)) {
                AlertUtils.makeSuccessNotification("Cours ajouté avec succés");
                MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_COURS);
            } else {
                AlertUtils.makeError("Le cours existe déjà");
            }
        } else {
            cours.setId(currentCours.getId());
            if (ServiceCours.getInstance().edit(cours)) {
                AlertUtils.makeSuccessNotification("Cours modifié avec succés");
                MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_COURS);
            } else {
                AlertUtils.makeError("Erreur lors de la modification du cours");
            }

            if (selectedImagePath != null) {
                createImageFile();
            }
        }
    }
}


    private boolean controleDeSaisie() {
 if (salleComboBox.getValue() == null) {
            AlertUtils.makeInformation("Veuillez choisir une salle");
            return false;
        }

        if (coachComboBox.getValue() == null) {
            AlertUtils.makeInformation("Veuillez choisir un coach");
            return false;
        }

        if (nomField.getText().isEmpty()) {
            AlertUtils.makeInformation("nom ne doit pas etre vide");
            return false;
        }

        if (descriptionField.getText().isEmpty()) {
            AlertUtils.makeInformation("description ne doit pas etre vide");
            return false;
        }

        if (nbPlacesTotalField.getText().isEmpty()) {
            AlertUtils.makeInformation("Nembre de place ne doit pas etre vide");
            return false;
        }

        if (reservationField.getText().isEmpty()) {

            AlertUtils.makeInformation("reservationField ne doit pas etre vide");
            return false;
        }
        // Vérifier si l'âge est un nombre entier positif
        try {
            int age = Integer.parseInt(reservationField.getText());
            if (age < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            AlertUtils.makeInformation("Veuillez entrer un nombre entier positif pour la reservation.");

            return false;
        }
        if (nbPlacesTotalField.getText().isEmpty()) {

            AlertUtils.makeInformation("nbPlacesTotal ne doit pas etre vide");
            return false;
        }
        // Vérifier si l'âge est un nombre entier positif
        try {
            int age = Integer.parseInt(nbPlacesTotalField.getText());
            if (age < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            AlertUtils.makeInformation("Veuillez entrer un nombre entier positif pour la nbPlacesTotalField.");

            return false;
        }

        if (selectedImagePath == null) {
            AlertUtils.makeInformation("Veuillez choisir une image");
            return false;
        }

        return true;
    }
}
