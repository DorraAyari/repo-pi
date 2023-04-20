/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.gui.back.salle;


import com.infantry.entities.Salle;
import com.infantry.gui.back.MainWindowController;
import com.infantry.services.SalleService;
import com.infantry.utils.Constants;
import java.io.IOException;
import java.net.URL;

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

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author dorraayari
 */
public class ShowAllController implements Initializable {
    public static Salle currentSalle;
    @FXML
    public VBox mainVBox;
    List<Salle> listSalle;
  @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public Button btnAjout;

    /**
     * Initializes the controller class.
     */
      @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listSalle = SalleService.getInstance().readAll();
        displayData();
    }
    void displayData() {
        mainVBox.getChildren().clear();

        Collections.reverse(listSalle);

        if (!listSalle.isEmpty()) {
            for (Salle salle : listSalle) {
                mainVBox.getChildren().add(makeSalleModel(salle));
            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }
    
@FXML
private void ajouter(ActionEvent event) throws IOException {
    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_SALLE);
}
     public Parent makeSalleModel(Salle salle) {
    Parent parent = null;
    try {
        parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_SALLE)));

        HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
        ((Text) innerContainer.lookup("#nomText")).setText("Nom : " + salle.getNom());
        ((Text) innerContainer.lookup("#descriptionText")).setText("Description : " + salle.getDescription());
     
       

        ((Button) innerContainer.lookup("#editButton")).setOnAction((event) -> modifierSalle(salle));
        ((Button) innerContainer.lookup("#deleteButton")).setOnAction((event) -> supprimerSalle(salle));

    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    return parent;
}

    private void modifierSalle(Salle salle) {
        currentSalle = salle;

         MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_SALLE);
    }


    private void supprimerSalle(Salle salle) {
                currentSalle = null;

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer salle ?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK){
        SalleService.getInstance().delete(salle.getId());
        // refresh data after deleting coach
        listSalle = SalleService.getInstance().readAll();
        displayData();
    }
    } 
}
