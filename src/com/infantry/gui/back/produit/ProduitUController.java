/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.gui.back.produit;

import com.infantry.MainApp;
import com.infantry.entities.Produit;
import com.infantry.gui.back.MainWindowController;
import com.infantry.services.ProduitServise;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.controlsfx.control.Notifications;

/**
 * FXML ProduitUController class
 *
 * @author AA
 */
public class ProduitUController implements Initializable {

    @FXML
    private Text topText;
    @FXML
    private TextField nomField;
    @FXML
    private ImageView imageIV;
    @FXML
    private TextField prixField;
    @FXML
    private TextField descriptionField;
    @FXML
    private Button btnAjout;
 @FXML
    public Text topText1;
    Produit currentproduit;

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

        currentproduit = ShowAllPController.currentproduit;

        if (currentproduit != null) {
            topText.setText("Modifier produit");
            btnAjout.setText("Modifier");

            try {
                nomField.setText(currentproduit.getNom());
               
                descriptionField.setText(currentproduit.getDescription());
                prixField.setText(String.valueOf(currentproduit.getPrix()));
            

                selectedImagePath = FileSystems.getDefault().getPath(currentproduit.getImage());
                if (selectedImagePath.toFile().exists()) {
                    imageIV.setImage(new Image(selectedImagePath.toUri().toString()));
                }

            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter produit");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void modifier(ActionEvent event) {
        

        if (controleDeSaisie()) {

            String imagePath;
            if (imageEdited) {
                imagePath = currentproduit.getImage();
            } else {
                createImageFile();
                imagePath = selectedImagePath.toString();
            }

            Produit produit = new Produit(
                    nomField.getText(),
                    descriptionField.getText(),
                  
                    Integer.parseInt(prixField.getText()),
                    imagePath
            );

            if (currentproduit == null) {
                if (ProduitServise.getInstance().add(produit)) {
                    AlertUtils.makeSuccessNotification("produit ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_PRODUITT);
                } else {
                    AlertUtils.makeError("Le Produit existe déjà");
                }
            } else {
                produit.setId(currentproduit.getId());
                produit.setNom(nomField.getText());
                produit.setDescription(descriptionField.getText());
           
                produit.setPrix(Integer.parseInt(prixField.getText()));

                if (ProduitServise.getInstance().edit(produit)) {
                    AlertUtils.makeSuccessNotification("produit modifié avec succés");
                    ShowAllPController.currentproduit = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_PRODUITT);
                } 
            }

            if (selectedImagePath != null) {
                createImageFile();
            }
        }
         Notifications.create()
        .title("produit modifier")
        .text("Le produit a été modifié avec succès.")
        .showInformation();
       
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

       
    
        if (prixField.getText().isEmpty()) {

            AlertUtils.makeInformation("prix ne doit pas etre vide");
            return false;
        }
        // Vérifier si l'âge est un nombre entier positif
        try {
            int age = Integer.parseInt(prixField.getText());
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
