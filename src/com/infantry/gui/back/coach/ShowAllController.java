/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.gui.back.coach;

import com.infantry.entities.Coach;
import com.infantry.entities.User;
import com.infantry.services.CoachService;
import com.infantry.utils.Constants;
import com.infantry.gui.back.MainWindowController;
import static com.infantry.gui.back.user.ShowAllController.currentUser;
import com.infantry.services.UserService;
import com.infantry.utils.AlertUtils;
import com.infantry.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;
import javafx.scene.Node;
/**
 * FXML Controller class
 *
 * @author dorraayari
 */
public class ShowAllController implements Initializable {
    public static Coach currentCoach;
@FXML
private ScrollPane scrollPane;
@FXML
    public VBox mainVBox;
    List<Coach> listCoach;
  @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public Button btnAjout;
    // other fields and methods

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listCoach = CoachService.getInstance().readAll();
        displayData();
    }


@FXML
private void ajouter(ActionEvent event) throws IOException {
    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_COACH);
}


    void displayData() {
        mainVBox.getChildren().clear();

        Collections.reverse(listCoach);

        if (!listCoach.isEmpty()) {
            for (Coach coach : listCoach) {
                mainVBox.getChildren().add(makeCoachModel(coach));
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
            Coach coach
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_COACH)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#nomText")).setText("Nom : " + coach.getNom());
            ((Text) innerContainer.lookup("#descriptionText")).setText("Description : " + coach.getDescription());
            ((Text) innerContainer.lookup("#weightText")).setText("Poids : " + coach.getWeight());
            ((Text) innerContainer.lookup("#heightText")).setText("Taille : " + coach.getHeight());
            ((Text) innerContainer.lookup("#occupationText")).setText("Occupation : " + coach.getOccupation());
            ((Text) innerContainer.lookup("#ageText")).setText("age : " + coach.getOccupation());

            Path selectedImagePath = FileSystems.getDefault().getPath(coach.getImage());
            if (selectedImagePath.toFile().exists()) {
                ((ImageView) innerContainer.lookup("#imageIV")).setImage(new Image(selectedImagePath.toUri().toString()));
            }

            ((Button) innerContainer.lookup("#editButton")).setOnAction((event) -> modifierCoach(coach));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((event) -> supprimerCoach(coach));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

     private void modifierCoach(Coach coach) {
        currentCoach = coach;

         MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_COACH);
    }


    private void supprimerCoach(Coach coach) {
                currentCoach = null;

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer coach ?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK){
        CoachService.getInstance().delete(coach.getId());
        // refresh data after deleting coach
        listCoach = CoachService.getInstance().readAll();
        displayData();
    }
    }
    private void specialAction(Coach coach) {
        // implementation
    }
}

