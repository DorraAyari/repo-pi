/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.gui.back.blog;

import com.infantry.MainApp;
import com.infantry.entities.Blog;
import com.infantry.gui.back.MainWindowController;
import com.infantry.gui.back.cours.ShowAllController;
import com.infantry.services.BlogService;
import com.infantry.utils.AlertUtils;
import com.infantry.utils.Constants;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

/**
 *
 * @author EMNA
 */
public class BlogController implements Initializable {

    @FXML
    public Button btnAjout;
     
    @FXML
    public TextField nomField;
    
    @FXML
    public TextField descriptionField;
    
    @FXML
    public TextField moredescreptionField;
    
    @FXML
    public TextField sloganField;
    
    @FXML
    public ImageView imageIV;
    
    @FXML
    public Text topText;
    
    Blog currentBlog;

    Path selectedImagePath;
    boolean imageEdited;

    /**
     * Initializes the controller class.
     */
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        currentBlog = ShowAllBlogController.currentBlog;

        if (currentBlog != null) {
            topText.setText("Modifier blog");
            btnAjout.setText("Modifier");

            try {
                nomField.setText(currentBlog.getNom());
                descriptionField.setText(currentBlog.getDescription());
                moredescreptionField.setText(String.valueOf(currentBlog.getMoredescreption()));
                sloganField.setText(currentBlog.getSlogan());
                

                selectedImagePath = FileSystems.getDefault().getPath(currentBlog.getImage());
                if (selectedImagePath.toFile().exists()) {
                    imageIV.setImage(new Image(selectedImagePath.toUri().toString()));
                }

            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter blog");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void modifier(ActionEvent event) {

        if (controleDeSaisie()) {

            String imagePath;
            if (imageEdited) {
                imagePath = currentBlog.getImage();
            } else {
                createImageFile();
                imagePath = selectedImagePath.toString();
            }

            Blog blog = new Blog(
                    nomField.getText(),
                    descriptionField.getText(),
                    moredescreptionField.getText(),
                    sloganField.getText(),
                   
                    imagePath
            );

            if (currentBlog == null) {
                if (BlogService.getInstance().add(blog)) {
                    AlertUtils.makeSuccessNotification("Blog ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_BLOG);
                } else {
                    AlertUtils.makeError("Le blog existe déjà");
                }
            } else {
                blog.setId(currentBlog.getId());
                blog.setNom(nomField.getText());
                blog.setDescription(descriptionField.getText());
                blog.setMoredescreption(moredescreptionField.getText());
                blog.setSlogan(sloganField.getText());


                if (BlogService.getInstance().edit(blog)) {
                    AlertUtils.makeSuccessNotification("Blog modifié avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_BLOG);
                } else {
                    AlertUtils.makeError("Le blog existe déjà");
                }
            }

            if (selectedImagePath != null) {
                createImageFile();
            }
        }
    }

    private boolean controleDeSaisie() {

        if (nomField.getText().isEmpty()) {
            AlertUtils.makeInformation("nom ne doit pas etre vide");
            return false;
        }

        if (descriptionField.getText().isEmpty()) {
            AlertUtils.makeInformation("description ne doit pas etre vide");
            return false;
        }

        if (moredescreptionField.getText().isEmpty()) {
            AlertUtils.makeInformation("weight ne doit pas etre vide");
            return false;
        }

        if (sloganField.getText().isEmpty()) {
            AlertUtils.makeInformation("height ne doit pas etre vide");
            return false;
        }

     

        if (selectedImagePath == null) {
            AlertUtils.makeInformation("Veuillez choisir une image");
            return false;
        }

        return true;
    }
}

