package com.infantry.gui.front;

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

public class TopBarController implements Initializable {

    private final Color COLOR_GRAY = new Color(0.9, 0.9, 0.9, 1);
    private final Color COLOR_PRIMARY = Color.web("#B24D0A");
    private final Color COLOR_DARK = new Color(1, 1, 1, 0.65);
    private Button[] liens;

    @FXML
    private Button btnProfile;
    
    @FXML
    private Button btnReservation;
      @FXML
    private Button btnPack;
 @FXML
    private Button btnCoach;
@FXML
    private Button btnCours;
    @FXML
    private AnchorPane mainComponent;
    @FXML
    private Button btnRec;
 @FXML
    private Button btnProduit;
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        liens = new Button[]{
                btnProfile,btnCoach,btnCours, btnReservation,btnPack,btnRec,btnProduit

        };

        mainComponent.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));

        for (Button lien : liens) {
            lien.setTextFill(COLOR_DARK);
            lien.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));
            Animations.animateButton(lien, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
        }

        btnProfile.setTextFill(Color.WHITE);


    }

    @FXML
    private void showProfile(ActionEvent event) {
        goToLink(Constants.FXML_FRONT_MY_PROFILE);

        btnProfile.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnProfile, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }
     @FXML
    private void showCoach(ActionEvent event) {
        goToLink(Constants.FXML_FRONT_MY_COCHLIST);

        btnCoach.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnCoach, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }
 @FXML
    private void showCours(ActionEvent event) {
        goToLink(Constants.FXML_FRONT_MY_COURSLIST);

        btnCours.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnCours, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }
    @FXML
    private void showPackage(ActionEvent event) {
        goToLink(Constants.FXML_FRONT_MY_PACK);

        btnPack.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnPack, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }
    
    
     @FXML
    private void showReservation(ActionEvent event) {
        goToLink(Constants.FXML_FRONT_MY_RESERVATION);

        btnReservation.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnReservation, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }
      @FXML
    private void showProduits(ActionEvent event) {
        goToLink(Constants. FXML_FRONT_LISTPRODUIT);

        btnProduit.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnProfile, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
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

    @FXML
    private void ShowReclamation(ActionEvent event) {
               
        goToLink(Constants.FXML_FRONT_LISTRECLAMTIO);

        btnRec.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnRec, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }

}
