/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.gui.back.blog;

import com.infantry.entities.Blog;
import com.infantry.gui.back.MainWindowController;
import com.infantry.services.BlogService;
import com.infantry.utils.Constants;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author EMNA
 */
public class ShowAllBlogController implements Initializable {
    public static Blog currentBlog;
@FXML
private ScrollPane scrollPane;
@FXML
    public VBox mainVBox;
    List<Blog> listBlog;
  @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public Button btnAjout;
    // other fields and methods

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listBlog = BlogService.getInstance().readAll();
        displayData();
    }


@FXML
private void ajouter(ActionEvent event) throws IOException {
    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_BLOG);
}


    void displayData() {
        mainVBox.getChildren().clear();

        Collections.reverse(listBlog);

        if (!listBlog.isEmpty()) {
            for (Blog blog : listBlog) {
                mainVBox.getChildren().add(makeBlogModel(blog));
            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeBlogModel(
            Blog blog
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_BLOG)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#nomText")).setText("Sujet : " + blog.getNom());
            ((Text) innerContainer.lookup("#descriptionText")).setText("Description : " + blog.getDescription());
            ((Text) innerContainer.lookup("#moredescreptionText")).setText("More Description : " + blog.getMoredescreption());
            ((Text) innerContainer.lookup("#sloganText")).setText("Slogan : " + blog.getSlogan());

            Path selectedImagePath = FileSystems.getDefault().getPath(blog.getImage());
            if (selectedImagePath.toFile().exists()) {
                ((ImageView) innerContainer.lookup("#imageIV")).setImage(new Image(selectedImagePath.toUri().toString()));
            }

            ((Button) innerContainer.lookup("#editButton")).setOnAction((event) -> modifierBlog(blog));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((event) -> supprimerBlog(blog));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

     private void modifierBlog(Blog blog) {
        currentBlog = blog;

         MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_BLOG);
    }


    private void supprimerBlog(Blog blog) {
                currentBlog = null;

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation de suppression");
            alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer blog ?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK){
        BlogService.getInstance().delete(blog.getId());
        // refresh data after deleting blog
        listBlog = BlogService.getInstance().readAll();
        displayData();
    }
    }
    private void specialAction(Blog blog) {
        // implementation
    }
}

