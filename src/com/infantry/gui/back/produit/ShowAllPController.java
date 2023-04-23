/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.gui.back.produit;

import com.infantry.entities.Produit;
import com.infantry.gui.back.MainWindowController;
import com.infantry.services.ProduitServise;
import com.infantry.utils.Constants;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author AA
 */
public class ShowAllPController implements Initializable {

    @FXML
    private Text topText;
    @FXML
    private Button btnAjout;
    @FXML
    private VBox mainVBox;
    
        public static Produit currentproduit;
@FXML
 
    List<Produit> listproduit;

    public Button addButton;


   
    // other fields and methods

    /**
     * Initializes the controller class.
     */
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listproduit = ProduitServise.getInstance().readAll();
        displayData();
    }


@FXML
private void ajouter(ActionEvent event) throws IOException {
    MainWindowController.getInstance().loadInterface(Constants.FXML_MANAGE_PRODUITA);
}


    void displayData() {
        mainVBox.getChildren().clear();

        Collections.reverse(listproduit);

        if (!listproduit.isEmpty()) {
            for (Produit produit : listproduit) {
                mainVBox.getChildren().add(makeCoachModel(produit));
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
            Produit produit
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#nomText")).setText("Nom : " + produit.getNom());
            ((Text) innerContainer.lookup("#descriptionText")).setText("Description : " + produit.getDescription());
            
          
            ((Text) innerContainer.lookup("#prixText")).setText("prix : " + produit.getPrix());

            Path selectedImagePath = FileSystems.getDefault().getPath(produit.getImage());
            if (selectedImagePath.toFile().exists()) {
                ((ImageView) innerContainer.lookup("#imageIV")).setImage(new Image(selectedImagePath.toUri().toString()));
            }

            ((Button) innerContainer.lookup("#editButton")).setOnAction((event) -> modifierCoach(produit));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((event) -> supprimerCoach(produit));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

     private void modifierCoach(Produit produit) {
        currentproduit =  produit;

         MainWindowController.getInstance().loadInterface(Constants.FXML_MANAGE_PRODUIT);
    }


    private void supprimerCoach( Produit  produit) {
                currentproduit = null;

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer produit ?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK){
        ProduitServise.getInstance().delete( produit.getId());
        // refresh data after deleting coach
        listproduit = ProduitServise.getInstance().readAll();
        displayData();
    }
    }
    private void specialAction( Produit  produit) {
        // implementation
    }
}
