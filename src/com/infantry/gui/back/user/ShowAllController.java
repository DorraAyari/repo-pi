package com.infantry.gui.back.user;

import com.infantry.entities.User;
import com.infantry.gui.back.MainWindowController;
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

public class ShowAllController implements Initializable {

    public static User currentUser;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;

    List<User> listUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listUser = UserService.getInstance().getAll();
        displayData();
    }

    void displayData() {
        mainVBox.getChildren().clear();

        Collections.reverse(listUser);

        if (!listUser.isEmpty()) {
            for (User user : listUser) {
                    mainVBox.getChildren().add(makeUserModel(user));
            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeUserModel(
            User user
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_USER)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#emailText")).setText("Email : " + user.getEmail());
            ((Text) innerContainer.lookup("#rolesText")).setText("Roles : " + user.getRoles());
            ((Text) innerContainer.lookup("#nomText")).setText("Nom : " + user.getNom());
            ((Text) innerContainer.lookup("#prenomText")).setText("Prenom : " + user.getPrenom());
            ((Text) innerContainer.lookup("#numeroText")).setText("Numero : " + user.getNumero());

            Path selectedImagePath = FileSystems.getDefault().getPath(user.getPhoto());
            if (selectedImagePath.toFile().exists()) {
                ((ImageView) innerContainer.lookup("#imageIV")).setImage(new Image(selectedImagePath.toUri().toString()));
            }

            ((Button) innerContainer.lookup("#editButton")).setOnAction((event) -> modifierUser(user));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((event) -> supprimerUser(user));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    private void modifierUser(User user) {
        currentUser = user;
        MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_USER);
    }

    private void supprimerUser(User user) {
        currentUser = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer user ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            if (UserService.getInstance().delete(user.getId())) {
                MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_USER);
            } else {
                AlertUtils.makeError("Could not delete user");
            }
        }
    }

    private void specialAction(User user) {

    }
}
