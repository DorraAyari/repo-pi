package com.infantry.gui.front.user;

import com.infantry.MainApp;
import com.infantry.entities.User;
import com.infantry.gui.front.MainWindowController;
import com.infantry.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class MyProfileController implements Initializable {

    public static User currentUser;

    @FXML
    public Text emailText;
    @FXML
    public Text nomText;
    @FXML
    public Text prenomText;
    @FXML
    public Text numeroText;
    @FXML
    public ImageView imageIV;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        User user = MainApp.getSession();

        emailText.setText("Email : " + user.getEmail());
        nomText.setText("Nom : " + user.getNom());
        prenomText.setText("Prenom : " + user.getPrenom());
        numeroText.setText("Numero : " + user.getNumero());

        Path selectedImagePath = FileSystems.getDefault().getPath(user.getPhoto());
        if (selectedImagePath.toFile().exists()) {
            imageIV.setImage(new Image(selectedImagePath.toUri().toString()));
        }
    }

    @FXML
    public void editProfile(ActionEvent actionEvent) {
        currentUser = MainApp.getSession();
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_EDIT_PROFILE);
    }
}
