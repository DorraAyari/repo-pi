package com.infantry.gui.front.coach;

import com.infantry.entities.Coach;
import com.infantry.gui.front.MainWindowController;
import com.infantry.services.CoachService;
import com.infantry.utils.Constants;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ShowAllController implements Initializable {

    public static Coach currentCoach;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;

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
    private void onSearchFieldKeyReleased(KeyEvent event) {
        displayData();
    }

    void displayData() {
        
    String searchTerm = searchField.getText();
    List<Coach> coachesToDisplay = listCoach;
    if (!searchTerm.isEmpty()) {
        coachesToDisplay = CoachService.getInstance().searchByName(searchTerm);
    }
        Collections.reverse(coachesToDisplay);

        mainVBox.getChildren().clear();

        Collections.reverse(listCoach);

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

    public Parent makeCoachModel(
            Coach coach
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_PRODUIT)));

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

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    private void specialAction(Coach coach) {
        // implementation
    }
}
