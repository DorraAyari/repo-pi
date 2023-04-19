/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.gui.back.coach;

import com.infantry.MainApp;
import com.infantry.entities.Coach;
import com.infantry.entities.User;
import com.infantry.gui.back.MainWindowController;
import com.infantry.services.CoachService;
import com.infantry.services.UserService;
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
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author dorraayari
 */
public class CoachController implements Initializable {
    @FXML
    public Button btnAjout;
 @FXML
    public TextField nomField;
    @FXML
    public TextField occupationField;
    @FXML
    public TextField descriptionField;
    @FXML
    public TextField ageField;
    @FXML
    public TextField heightField;
    @FXML
    public ImageView imageIV;
    @FXML
    public TextField weightField;
    @FXML
    public Text topText;
    Coach currentCoach;

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
    
   @Override
public void initialize(URL url, ResourceBundle rb) {

        currentCoach = ShowAllController.currentCoach;

    if (currentCoach != null) {
        topText.setText("Modifier coach");
        btnAjout.setText("Modifier");

        try {
            nomField.setText(currentCoach.getNom());
            occupationField.setText(currentCoach.getOccupation());
            descriptionField.setText(currentCoach.getDescription());
ageField.setText(String.valueOf(currentCoach.getAge()));
            heightField.setText(currentCoach.getHeight());
                        weightField.setText(currentCoach.getWeight());

            selectedImagePath = FileSystems.getDefault().getPath(currentCoach.getImage());
            if (selectedImagePath.toFile().exists()) {
                imageIV.setImage(new Image(selectedImagePath.toUri().toString()));
            }

        } catch (NullPointerException ignored) {
            System.out.println("NullPointerException");
        }
    } else {
        topText.setText("Ajouter coach");
        btnAjout.setText("Ajouter");
    }
}
       @FXML
    private void modifier(ActionEvent event) {

        if (controleDeSaisie()) {

            String imagePath;
            if (imageEdited) {
                imagePath = currentCoach.getImage();
            } else {
                createImageFile();
                imagePath = selectedImagePath.toString();
            }
  
 
            Coach coach = new Coach(
                    nomField.getText(),
                     descriptionField.getText(),
                        weightField.getText(),
                      heightField.getText(),
                    occupationField.getText(),
                   
                   
                Integer.parseInt(ageField.getText()),


                    imagePath
            );

                if (currentCoach == null) {
        if (CoachService.getInstance().add(coach)) {
            AlertUtils.makeSuccessNotification("Coach ajouté avec succés");
            MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_COACH);
        } else {
            AlertUtils.makeError("Le coach existe déjà");
        }
    } else {
        coach.setId(currentCoach.getId());
                coach.setNom(currentCoach.getNom());
                coach.setDescription(currentCoach.getDescription());
                coach.setWeight(currentCoach.getWeight());
                 coach.setHeight(currentCoach.getHeight());

                coach.setOccupation(currentCoach.getOccupation());
                coach.setAge(currentCoach.getAge());

        if (CoachService.getInstance().edit(coach)) {
            AlertUtils.makeSuccessNotification("Coach modifié avec succés");
            ShowAllController.currentCoach = null;
            MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_COACH);
        } else {
            AlertUtils.makeError("Le coach existe déjà");
        }
    }

            
            if (selectedImagePath != null) {
                createImageFile();
            }
        }
    }

     private boolean controleDeSaisie() {


        if (nomField.getText().isEmpty()) {
            AlertUtils.makeInformation("nom ne doit pas etre vide");
            return false;
        }
       

        if (descriptionField.getText().isEmpty()) {
            AlertUtils.makeInformation("description ne doit pas etre vide");
            return false;
        }


        if (weightField.getText().isEmpty()) {
            AlertUtils.makeInformation("weight ne doit pas etre vide");
            return false;
        }


        if (heightField.getText().isEmpty()) {
            AlertUtils.makeInformation("height ne doit pas etre vide");
            return false;
        }


        if (occupationField.getText().isEmpty()) {
            AlertUtils.makeInformation("occupation ne doit pas etre vide");
            return false;
        }
                if (ageField.getText().isEmpty()) {

            AlertUtils.makeInformation("age ne doit pas etre vide");
            return false;
                }
    // Vérifier si l'âge est un nombre entier positif
    try {
        int age = Integer.parseInt(ageField.getText());
        if (age < 0) {
            throw new NumberFormatException();
        }
    } catch (NumberFormatException e) {
              AlertUtils.makeInformation("Veuillez entrer un nombre entier positif pour l'âge.");

        return false;
    }

        if (selectedImagePath == null) {
            AlertUtils.makeInformation("Veuillez choisir une image");
            return false;
        }


        return true;
    }
}
