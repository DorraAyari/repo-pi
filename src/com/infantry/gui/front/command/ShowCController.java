/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.gui.front.command;

import com.infantry.gui.front.produit.*;
import com.infantry.entities.Produit;
import com.infantry.services.ProduitServise;
import com.infantry.utils.Constants;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
public class ShowCController implements Initializable {
 @FXML
private TableView<Produit> produitTable;

@FXML
private TableColumn<Produit, String> nomColumn;

@FXML
private TableColumn<Produit, Integer> prixColumn;

@FXML
private TextField nomField;

@FXML
private TextField prixField;

@Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

@FXML
private void ajouterProduit(ActionEvent event) {
   

 
}

    

}

