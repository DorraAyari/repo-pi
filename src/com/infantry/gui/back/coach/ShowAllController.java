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
import java.io.FileOutputStream;
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
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import javafx.scene.Node;
/**
 * FXML Controller class
 *
 * @author dorraayari
 */
public class ShowAllController implements Initializable {
    public static Coach currentCoach;
    @FXML
private TextField searchField;
@FXML
private Button searchButton;

@FXML
    public VBox mainVBox;
    List<Coach> listCoach;
  @FXML
    public Text topText;
    public Button addButton;
    @FXML
    public Button btnAjout;
    // other fields and methods

@Override
public void initialize(URL url, ResourceBundle rb) {
    
    listCoach = CoachService.getInstance().readAll();
    System.out.println("List size: " + listCoach.size());

    displayData(); // display the coaches when the view is first loaded
}


@FXML
private void ajouter(ActionEvent event) throws IOException {
    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_COACHA);
}
void displayData() {
    mainVBox.getChildren().clear();

    String searchTerm = searchField.getText();
    List<Coach> coachesToDisplay = listCoach;
    if (!searchTerm.isEmpty()) {
        coachesToDisplay = CoachService.getInstance().searchByName(searchTerm);
    }

    Collections.reverse(coachesToDisplay);

    if (!coachesToDisplay.isEmpty()) {
        for (Coach coach : coachesToDisplay) {
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



@FXML
private void onSearchFieldKeyReleased(KeyEvent event) {
    displayData();
}


    public Parent makeCoachModel(
            Coach coach
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_COACH)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
((Text) innerContainer.lookup("#nomText")).setText("Nom : " + coach.getNom());
System.out.println("Coach name: " + coach.getNom());
            ((Text) innerContainer.lookup("#descriptionText")).setText("Description : " + coach.getDescription());
            ((Text) innerContainer.lookup("#weightText")).setText("Poids : " + coach.getWeight());
            ((Text) innerContainer.lookup("#heightText")).setText("Taille : " + coach.getHeight());
            ((Text) innerContainer.lookup("#occupationText")).setText("Occupation : " + coach.getOccupation());
((Text) innerContainer.lookup("#ageText")).setText("Age : " + coach.getAge());

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
     void displayData(boolean ascending) {
    mainVBox.getChildren().clear();

    String searchTerm = searchField.getText();
    List<Coach> coachesToDisplay = listCoach;
    if (!searchTerm.isEmpty()) {
        coachesToDisplay = CoachService.getInstance().searchByName(searchTerm);
    }

    Comparator<Coach> comparator = Comparator.comparing(Coach::getNom);
    if (!ascending) {
        comparator = comparator.reversed();
    }
    coachesToDisplay = coachesToDisplay.stream().sorted(comparator).collect(Collectors.toList());

    if (!coachesToDisplay.isEmpty()) {
        for (Coach coach : coachesToDisplay) {
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
          CoachService cr = new   CoachService();;

            List<Coach> metiers = cr.readAll();
            writer.write("id,nom,description,age,height,weight,occupation,image\n");
            for (Coach obj : metiers) {
               writer.write(obj.getId());
                writer.write(",");
                writer.write(obj.getNom());
                writer.write(",");
                writer.write(obj.getDescription());
                writer.write(",");
               
                writer.write(obj.getHeight());
                writer.write(",");
                  writer.write(obj.getWeight());
                writer.write(",");
                 writer.write(obj.getAge());
                writer.write(",");
                  writer.write(obj.getOccupation());
                writer.write(",");
                writer.write(obj.getImage());
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

