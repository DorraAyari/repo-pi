/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import entities.RP;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.RPService;

/**
 *
 * @author mrdje
 */
public class ConsulterPackageController implements Initializable {

    @FXML
    private TableView<RP> tableconsult;
    @FXML
    private TableColumn<RP, Integer> idrCol;
    @FXML
    private TableColumn<RP, Integer> idpCol;
    @FXML
    private TableColumn<RP, Date> startCol;
    @FXML
    private TableColumn<RP, Date> finishCol;
    @FXML
    private TableColumn<RP, String> choicesCol;

 @Override
public void initialize(URL url, ResourceBundle rb) {
    idrCol.setCellValueFactory(new PropertyValueFactory<>("idR"));
    idpCol.setCellValueFactory(new PropertyValueFactory<>("idP"));
    startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
    finishCol.setCellValueFactory(new PropertyValueFactory<>("finish"));
    choicesCol.setCellValueFactory(new PropertyValueFactory<>("choices"));

    RPService rP = new RPService();
    List<RP> rPs = rP.recuperer();
    if (rPs != null) {
        tableconsult.getItems().addAll(rPs);
    } else {
        System.err.println("Failed to retrieve RP data");
    }
}

    
  
    public void retourner(ActionEvent event) throws IOException {
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherPackage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    }
    
    
}
