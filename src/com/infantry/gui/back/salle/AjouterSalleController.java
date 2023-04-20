/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.gui.back.salle;

import com.infantry.entities.Salle;
import com.infantry.gui.back.MainWindowController;
import com.infantry.services.SalleService;
import com.infantry.utils.AlertUtils;
import com.infantry.utils.Constants;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 *
 * @author dorraayari
 */
public class AjouterSalleController implements Initializable {

    @FXML
    public Button btnAjout;
    @FXML
    public TextField nomField;

    public TextField descriptionField;

    @FXML
    public Text topText;
    Salle currentSalle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        currentSalle = ShowAllController.currentSalle;

        
            topText.setText("Ajouter salle");
            btnAjout.setText("Ajouter");
        
    }

    @FXML
    private void modifier(ActionEvent event) {

        if (controleDeSaisie()) {

            Salle salle = new Salle(
                    nomField.getText(),
                    descriptionField.getText()
            );

           
                if (SalleService.getInstance().add(salle)) {
                    AlertUtils.makeSuccessNotification("salle ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_SALLE);
               
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

        return true;
    }
}
