/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.gui.front.produit;

import com.infantry.entities.Produit;
import com.infantry.entities.User;
import com.infantry.services.ProduitServise;
import com.infantry.utils.Constants;
import com.mysql.cj.xdevapi.Session;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdk.nashorn.internal.parser.Token;
//import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author AA
 */
public class ShowProduitController implements Initializable {
 public static Produit currentproduit;
@FXML
    public VBox mainVBox;
    List<Produit> listproduit;
  @FXML
    public Text topText;
    public Button addButton;
    public Button btnAjout;
    public Button AjouterButton;
    // other fields and methods
    List<Produit> produit = new ArrayList<>();
public Text nomText;
public Text descriptionText ;
 public Text prixText ;
 public ImageView imageI ;
    @FXML
    private TextField searchField;
    
    // other fields and methods
   private final ObservableList<Produit> data = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listproduit = ProduitServise.getInstance().readAll();
        displayData();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> RechercheAV());
        
    }

  public void handleSearch(){}



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

   
      private void displaySearchResults(List<Produit> searchResults) {
    listproduit = searchResults; // mettre à jour la liste globale de blogs pour refléter les résultats de recherche
    displayData(); // afficher les résultats de recherche
}
       
@FXML
public void searchButtonClicked(ActionEvent event) {
    String searchText = searchField.getText();
    List<Produit> searchResults = performSearch(searchText); // appel de votre méthode de recherche
    displaySearchResults(searchResults);
}

private List<Produit> performSearch(String searchText) {
    if (searchText == null || searchText.isEmpty()) {
        return listproduit; // retourne la liste complète si la recherche est vide
    }
    String searchLower = searchText.toLowerCase(); // convertir la requête en minuscules pour une correspondance insensible à la casse
    return listproduit.stream()
        .filter(produit -> produit.getNom().toLowerCase().contains(searchLower))
        .collect(Collectors.toList());
}
  
    public Parent makeCoachModel(
            Produit produit
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_PRODUIT)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#nomText")).setText("Nom : " + produit.getNom());
            ((Text) innerContainer.lookup("#descriptionText")).setText("Description : " + produit.getDescription());
            ;
            ((Text) innerContainer.lookup("#prixText")).setText("prix : " + produit.getPrix());

            Path selectedImagePath = FileSystems.getDefault().getPath(produit.getImage());
            if (selectedImagePath.toFile().exists()) {
                ((ImageView) innerContainer.lookup("#imageIV")).setImage(new Image(selectedImagePath.toUri().toString()));
            }
((Button) innerContainer.lookup("#AjouterButton")).setOnAction((event)->ProduitAdd(produit));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }
    
    void addToCart(ActionEvent event) {
    // get the currently selected product
    HBox selectedProduct = (HBox) ((VBox) ((Button) event.getSource()).getParent()).getParent();

    // create a map to store the product information
    Map<String, String> productInfo = new HashMap<>();
    productInfo.put("nom", ((Text) selectedProduct.lookup("#nomText")).getText().replace("Nom : ", ""));
    productInfo.put("prix", ((Text) selectedProduct.lookup("#prixText")).getText().replace("prix : ", ""));

    // add the product information to the cart list
    //cartList.add(Produit);
}


 private void ProduitAdd(Produit produit) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.FXML_FRONT_MODEL_COMMAND));
        Parent root = loader.load();
        ShowProduitController controller = loader.getController();
        controller.setBlog(produit);
        System.out.println("Nom du produit : " + produit.getNom());
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    // Notifications.create()
      //  .title("produit modifier")
     //   .text("Le produit a été modifié avec succès.")
     //   .showInformation();
}
public void initData(String nom, String description, int prix, String imagePath) {

    nomText.setText(nom);
    descriptionText.setText(description);
    

    Path selectedImagePath = FileSystems.getDefault().getPath(imagePath);
    if (selectedImagePath.toFile().exists()) {
        imageI.setImage(new Image(selectedImagePath.toUri().toString()));
    }

    System.out.println("Contenu du command : " + nom);
}
    
     
    public void setBlog(Produit produit) {
    this.produit = listproduit;
    nomText.setText(produit.getNom());
    descriptionText.setText(produit.getDescription());
    
    prixText.setText(String.valueOf(produit.getPrix()));

    String imagePath = produit.getImage();
    if (imagePath != null && !imagePath.isEmpty()) {
        Path selectedImagePath = FileSystems.getDefault().getPath(imagePath);
        if (selectedImagePath.toFile().exists()) {
            imageI.setImage(new Image(selectedImagePath.toUri().toString()));
        }
    }
}
    public void RechercheAV(){
      String query = searchField.getText();
            ObservableList<Produit> filteredList = FXCollections.observableArrayList();
                      ProduitServise cr = new   ProduitServise();
            List<Produit> li =cr.readAll();
        ObservableList<Produit> data = FXCollections.observableArrayList(li);
            for (Produit service : li) {
                if (service.getNom().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(service);
                }
            }
   // listproduit.setItems(filteredList);
    
}
   
} 
    



