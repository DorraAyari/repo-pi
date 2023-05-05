/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.gui.back.command;


import com.infantry.entities.Command;
import com.infantry.entities.Produit;
import com.infantry.gui.back.MainWindowController;
import static com.infantry.gui.back.produit.ShowAllPController.currentproduit;
import com.infantry.gui.front.produit.ShowProduitController;
import com.infantry.services.Commandservise;
import com.infantry.services.ProduitServise;
import com.infantry.utils.AlertUtils;
import com.infantry.utils.Constants;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author AA
 */
public class ShowAllController implements Initializable {

    @FXML
    private Text id_cmdText;
    
   
    @FXML
    private VBox mainVBox;
    @FXML
    private Button btnpdf;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    

    @FXML
    private void ToPdf(ActionEvent event) {
    }
   
  //private void supprimerCoach( Produit produit) {
               // currentproduit = null;
//int id =Produit.getId();
  //  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
  //  alert.setTitle("Confirmation de suppression");
       //     alert.setHeaderText(null);
       // alert.setContentText("Etes vous sûr de vouloir supprimer produit ?");

    //Optional<ButtonType> result = alert.showAndWait();
   // if (result.get() == ButtonType.OK){
       // Command c =new Command(produit.getId());
      //  ProduitServise.getInstance().insert( command.getId());
        // refresh data after deleting coach
       // listproduit = Commandservise.getInstance().readAll();
        
   // }
   // }
    }
