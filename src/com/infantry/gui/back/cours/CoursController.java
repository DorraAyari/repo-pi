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
@FXML
private void ajouterCours(ActionEvent event) {
    if (controleDeSaisie()) {
        String imagePath;
        createImageFile();
        imagePath = selectedImagePath.toString();
        Cours cours = new Cours(nomField.getText(), descriptionField.getText(),
                Integer.parseInt(nbPlacesTotalField.getText()), Integer.parseInt(reservationField.getText()),
                imagePath);
        Salle salle = salleComboBox.getValue();
        if (salle == null) {
            // Display an error message and return
            AlertUtils.makeInformation("Veuillez sélectionner une salle.");
            return;
        }
        cours.setSalle_id(salle);

        Coach coach = coachComboBox.getValue();
        if (coach == null) {
            // Display an error message and return
            AlertUtils.makeInformation("Veuillez sélectionner une coach.");
            return;
        }
        cours.setCours_id(coach);

        if (ServiceCours.getInstance().add(cours)) {
            AlertUtils.makeSuccessNotification("Cours ajouté avec succés");
            MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_COURS);
        } else {
            AlertUtils.makeError("Le cours existe déjà");
        }
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SalleService salleService = new SalleService();
        ObservableList<Salle> salleList = FXCollections.observableArrayList(salleService.readAll());
        Collections.sort(salleList, Comparator.comparing(Salle::getNom)); // trier par nom
        salleComboBox.setItems(salleList);
        salleComboBox.setConverter(new StringConverter<Salle>() {
            @Override
            public String toString(Salle salle) {
                return salle.getNom(); // retourne le nom de la salle
            }

            @Override
            public Salle fromString(String nom) {
                // Pas besoin d'implémenter cette méthode car elle ne sera pas utilisée
                return null;
            }
        });

        CoachService coachService = new CoachService();
        ObservableList<Coach> coachList = FXCollections.observableArrayList(coachService.readAll());
        Collections.sort(coachList, Comparator.comparing(Coach::getNom)); // trier par nom
        coachComboBox.setItems(coachList);
        coachComboBox.setConverter(new StringConverter<Coach>() {
            @Override
            public String toString(Coach coach) {
                return coach.getNom(); // retourne le nom du coach
            }

            @Override
            public Coach fromString(String nom) {
                // Pas besoin d'implémenter cette méthode car elle ne sera pas utilisée
                return null;
            }
        });

        currentCours = ShowAllController.currentCours;

        if (currentCours != null) {
            topText.setText("Modifier cours");
            btnAjout.setText("Modifier");
            List<String> salleNoms = new ArrayList<>();
            List<String> coachNoms = new ArrayList<>();

            try {
                nomField.setText(currentCours.getNom());
                descriptionField.setText(currentCours.getDescription());
                nbPlacesTotalField.setText(String.valueOf(currentCours.getNbPlacesTotal()));
                reservationField.setText(String.valueOf(currentCours.getReservation()));

                selectedImagePath = FileSystems.getDefault().getPath(currentCours.getImage());
                if (selectedImagePath.toFile().exists()) {
                    imageIV.setImage(new Image(selectedImagePath.toUri().toString()));
                }

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Infantry", "root", "root");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT c.*, s.nom as salle_nom, co.nom as coach_nom "
                        + "FROM cours c "
                        + "JOIN salle s ON c.salle_id = s.id "
                        + "JOIN coach co ON c.coach_id = co.id "
                        + "WHERE c.id = " + currentCours.getId());
                while (rs.next()) {
                    salleNoms.add(rs.getString("salle_nom"));
                    coachNoms.add(rs.getString("coach_nom"));
                }
                rs.close();
                stmt.close();
                conn.close();

            } catch (SQLException e) {
                e.printStackTrace();
            
             } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
            // Sélectionner la salle correspondant au cours courant
            salleComboBox.getSelectionModel().select(salleComboBox.getItems().stream()
                    .filter(salle -> salleNoms.contains(salle.getNom()))
                    .findFirst()
                    .orElse(null));

// Sélectionner le coach correspondant au cours courant
            coachComboBox.getSelectionModel().select(coachComboBox.getItems().stream()
                    .filter(coach -> coachNoms.contains(coach.getNom()))
                    .findFirst()
                    .orElse(null));

       
        } else {
            topText.setText("Ajouter coach");
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
                    imagePath);
            Salle salle = salleComboBox.getValue();
            if (salle == null) {
                // Display an error message and return
                AlertUtils.makeInformation("Veuillez sélectionner une salle.");

                return;
            }
            cours.setSalle_id(salle);

            Coach coach = coachComboBox.getValue();
            if (coach == null) {
                // Display an error message and return
                AlertUtils.makeInformation("Veuillez sélectionner une coach.");
                return;
            }
            cours.setCours_id(coach);

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

        Salle salle = salleComboBox.getValue();
        if (salle == null) {
            // Display an error message and return
            AlertUtils.makeInformation("Veuillez sélectionner une salle.");

            return false;

        }

        Coach coach = coachComboBox.getValue();
        if (coach == null) {
            // Display an error message and return
            AlertUtils.makeInformation("Veuillez sélectionner une coach.");
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
