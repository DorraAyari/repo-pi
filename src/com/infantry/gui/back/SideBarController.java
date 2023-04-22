package com.infantry.gui.back;

import com.infantry.MainApp;
import com.infantry.utils.Animations;
import com.infantry.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class SideBarController implements Initializable {

    private final Color COLOR_GRAY = new Color(0.9, 0.9, 0.9, 1);
    private final Color COLOR_PRIMARY = Color.web("#B24D0A");
    private final Color COLOR_DARK = new Color(1, 1, 1, 0.65);
    private Button[] liens;

    @FXML
    private Button btnUsers;
     @FXML
    private Button btnSalle;
     @FXML
    private Button btnCours;
    @FXML
    private Button btnCoach;
     @FXML
    private Button btnBlog;
    @FXML
    private AnchorPane mainComponent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        liens = new Button[]{
                btnUsers,
            btnCoach,btnCours,btnSalle,btnBlog
        };

        mainComponent.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));

        for (Button lien : liens) {
            lien.setTextFill(COLOR_DARK);
            lien.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));
            Animations.animateButton(lien, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
        }
        btnUsers.setTextFill(Color.WHITE);
        btnCoach.setTextFill(Color.WHITE);
        btnCours.setTextFill(Color.WHITE);
        btnSalle.setTextFill(Color.WHITE);
        btnBlog.setTextFill(Color.WHITE);

    }

    @FXML
    private void afficherUsers(ActionEvent event) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_USER);

        btnUsers.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnUsers, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }
    @FXML
    private void afficherCoachs(ActionEvent event) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_COACH);

        btnCoach.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnCoach, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }
 @FXML
    private void afficherCours(ActionEvent event) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_COURS);

        btnCours.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnCours, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }   
     @FXML
    private void afficherSalle(ActionEvent event) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_SALLE);

        btnSalle.setTextFill(COLOR_PRIMARY);
    }
    @FXML
    private void afficherBlogs(ActionEvent event) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_BLOG);

        btnBlog.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnBlog, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }
    private void goToLink(String link) {
        for (Button lien : liens) {
            lien.setTextFill(COLOR_DARK);
            Animations.animateButton(lien, COLOR_GRAY, COLOR_DARK, COLOR_PRIMARY, 0, false);
        }
        MainWindowController.getInstance().loadInterface(link);
    }

    @FXML
    public void logout(ActionEvent actionEvent) {
        MainApp.getInstance().logout();
    }
}
