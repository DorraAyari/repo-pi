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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

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
private TextField searchField;
@FXML
private Button searchButton;

   
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
         String searchTerm = searchField.getText();
    List<Salle> salleToDisplay = listSalle;
    if (!searchTerm.isEmpty()) {
        salleToDisplay =SalleService.getInstance().searchByName(searchTerm);
    }
            Collections.reverse(salleToDisplay);

        mainVBox.getChildren().clear();

        Collections.reverse(listSalle);

   if (!salleToDisplay.isEmpty()) {
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
private void onSearchFieldKeyReleased(KeyEvent event) {
    displayData();
}
     void displayData(boolean ascending) {
    mainVBox.getChildren().clear();

    String searchTerm = searchField.getText();
    List<Salle> salleToDisplay = listSalle;
    if (!searchTerm.isEmpty()) {
        salleToDisplay = SalleService.getInstance().searchByName(searchTerm);
    }

    Comparator<Salle> comparator = Comparator.comparing(Salle::getNom);
    if (!ascending) {
        comparator = comparator.reversed();
    }
    salleToDisplay = salleToDisplay.stream().sorted(comparator).collect(Collectors.toList());

    if (!salleToDisplay.isEmpty()) {
        for (Salle salle : salleToDisplay) {
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
    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_SALLEA);
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
 @FXML
private void sortByNameAscending(ActionEvent event) {
    displayData(true);
}

@FXML
private void sortByNameDescending(ActionEvent event) {
    displayData(false);
}

@FXML
    private void ToPdf(ActionEvent event) {
        
         try {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream("/Users/dorraayari/Downloads/javafx/myfile.txt"), "UTF-8"));
          SalleService cr = new   SalleService();;

            List<Salle> metiers = cr.readAll();
            writer.write("id,nom,description\n");
            for (Salle obj : metiers) {
               writer.write(obj.getId());
                writer.write(",");
                writer.write(obj.getNom());
                writer.write(",");
                writer.write(obj.getDescription());
                writer.write(",");
              
            
                writer.write("\n");

            }
            writer.flush();
            writer.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("EXCEL ");

            alert.setHeaderText("EXCEL");
            alert.setContentText("Enregistrement effectué avec succès!");

            alert.showAndWait();
        } catch (Exception e) {
            System.out.println("Failed to send message: " + e.getMessage());
        }
         
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
