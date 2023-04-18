package com.infantry.gui.front.user;

import com.infantry.MainApp;
import com.infantry.entities.User;
import com.infantry.gui.front.MainWindowController;
import com.infantry.services.UserService;
import com.infantry.utils.AlertUtils;
import com.infantry.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EditProfileController implements Initializable {

    @FXML
    public TextField emailTF;
    @FXML
    public TextField nomTF;
    @FXML
    public TextField prenomTF;
    @FXML
    public TextField numeroTF;
    @FXML
    public ImageView imageIV;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    User currentUser;
    Path selectedImagePath;
    boolean imageEdited;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        currentUser = MainApp.getSession();

        topText.setText("Modifier mon profil");
        btnAjout.setText("Modifier");

        try {
            emailTF.setText(currentUser.getEmail());

            nomTF.setText(currentUser.getNom());
            prenomTF.setText(currentUser.getPrenom());
            numeroTF.setText(currentUser.getNumero());
            selectedImagePath = FileSystems.getDefault().getPath(currentUser.getPhoto());
            if (selectedImagePath.toFile().exists()) {
                imageIV.setImage(new Image(selectedImagePath.toUri().toString()));
            }

        } catch (NullPointerException ignored) {
            System.out.println("NullPointerException");
        }
    }

    @FXML
    private void manage(ActionEvent event) {

        if (controleDeSaisie()) {

            String imagePath;
            if (imageEdited) {
                imagePath = currentUser.getPhoto();
            } else {
                createImageFile();
                imagePath = selectedImagePath.toString();
            }

            User user = new User(
                    emailTF.getText(),
                    "ROLE_USER",
                    currentUser.getPassword(),
                    nomTF.getText(),
                    prenomTF.getText(),
                    numeroTF.getText(),
                    imagePath
            );

            user.setId(currentUser.getId());
            if (UserService.getInstance().edit(user)) {
                MainApp.setSession(user);
                AlertUtils.makeSuccessNotification("Profile modifié avec succés");
            } else {
                AlertUtils.makeError("Could not edit user");
            }


            if (selectedImagePath != null) {
                createImageFile();
            }

            MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MY_PROFILE);
        }
    }

    @FXML
    public void chooseImage(ActionEvent actionEvent) {

        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(MainApp.mainStage);
        if (file != null) {
            selectedImagePath = Paths.get(file.getPath());
            imageIV.setImage(new Image(file.toURI().toString()));
        }
    }

    public void createImageFile() {
        try {
            Path newPath = FileSystems.getDefault().getPath("src/com/infantry/images/uploads/" + selectedImagePath.getFileName());
            Files.copy(selectedImagePath, newPath, StandardCopyOption.REPLACE_EXISTING);
            selectedImagePath = newPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean controleDeSaisie() {


        if (emailTF.getText().isEmpty()) {
            AlertUtils.makeInformation("email ne doit pas etre vide");
            return false;
        }
        if (!Pattern.compile("^(.+)@(.+)$").matcher(emailTF.getText()).matches()) {
            AlertUtils.makeInformation("Email invalide");
            return false;
        }


        if (nomTF.getText().isEmpty()) {
            AlertUtils.makeInformation("nom ne doit pas etre vide");
            return false;
        }


        if (prenomTF.getText().isEmpty()) {
            AlertUtils.makeInformation("prenom ne doit pas etre vide");
            return false;
        }


        if (numeroTF.getText().isEmpty()) {
            AlertUtils.makeInformation("numero ne doit pas etre vide");
            return false;
        }
        if (numeroTF.getText().length() != 8) {
            AlertUtils.makeInformation("numero non valide");
            return false;
        }


        if (selectedImagePath == null) {
            AlertUtils.makeInformation("Veuillez choisir une image");
            return false;
        }


        return true;
    }
}