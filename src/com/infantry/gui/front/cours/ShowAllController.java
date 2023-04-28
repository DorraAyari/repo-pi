package com.infantry.gui.front.cours;

import com.infantry.entities.Cours;
import com.infantry.entities.User;
import com.infantry.gui.LoginController;
import com.infantry.gui.front.sms.SendSMS;
import javax.mail.*;
import javax.mail.internet.*;

import com.infantry.services.ServiceCours;
import com.infantry.services.UserService;
import com.infantry.utils.Constants;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ShowAllController implements Initializable {

    public static Cours currentCours;
    User currentUser;
    boolean btnreserver = false; // Nouvelle variable pour stocker l'état de la condition

    @FXML
    public VBox mainVBox;
    List<Cours> listCours;
    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public Button btnAjout;
    // other fields and methods

@Override
public void initialize(URL url, ResourceBundle rb) {
    listCours = ServiceCours.getInstance().readAll();
    // Récupérer la valeur de currentUser à partir de la classe LoginController
    currentUser = LoginController.currentUser;
    displayData(currentUser);
    // Dans la méthode initialize(), récupérez l'état du bouton à partir du fichier de configuration
}


    public void mailing() {
        // Recipient's email address
        String to = "dorra.ayari@gmail.com";
        // Sender's email address
        String from = "dorraayari.esprit@outlook.com";
        // Sender's email password
        String password = "doudou1234";

        // Setup mail server properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Create a new session with an authenticator
        Session session;
        session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        try {
            // Create a new message
            Message message = new MimeMessage(session);
            // Set the sender, recipient, subject and body of the message
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Rappel Sur votre Reservation");

            message.setText("Bonjour Dorra Votre Reservation a été ajoutée avec succès"
                    + " !\n"
                    + "merci de votre visite sur notre plateforme "
            );

            // Send the message
            Transport.send(message);
            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            System.out.println("Failed to send email. Error message: " + e.getMessage());
        }
    }
void displayData(User currentUser) {
    mainVBox.getChildren().clear();
    listCours = ServiceCours.getInstance().readAll();
    List<User> userList = UserService.getInstance().getAll();

    Collections.reverse(listCours);

    if (!listCours.isEmpty()) {
        for (Cours cours : listCours) {
            // Pass the currentUser object to the makeCoachModel method, or the first user in the userList if currentUser is null
            mainVBox.getChildren().add(makeCoachModel(cours, currentUser != null ? currentUser : userList.get(0)));
        }
    } else {
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setPrefHeight(200);
        stackPane.getChildren().add(new Text("Aucune donnée"));
        mainVBox.getChildren().add(stackPane);
    }
}


public Parent makeCoachModel(Cours cours, User currentUser) {
     

    Parent parent = null;
    try {
        parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_COURS)));
        
        // Null check for currentUser
        if (currentUser == null) {
            // Do something, or return null
            return null;
        }

        HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
        ((Text) innerContainer.lookup("#nomText")).setText("Nom : " + cours.getNom());
        ((Text) innerContainer.lookup("#descriptionText")).setText("Description : " + cours.getDescription());
        ((Text) innerContainer.lookup("#reservationText")).setText("Reservation : " + cours.getReservation());
        ((Text) innerContainer.lookup("#nb_places_totalText")).setText("Places Total : " + cours.getNbPlacesTotal());
        Button reserverButton = new Button("Reserver");

      reserverButton.setOnAction(event -> {
    if (cours.hasUser(currentUser)) {
        // Afficher une alerte indiquant que l'utilisateur a déjà réservé ce cours
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de réservation");
        alert.setHeaderText(null);
        alert.setContentText("Vous avez déjà réservé ce cours !");
        alert.showAndWait();
    } else if (cours.getNbPlacesTotal() > 0) {
        // Afficher une alerte indiquant que la réservation a réussi
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Réservation effectuée avec succès");
        alert.setHeaderText(null);
        alert.setContentText("Réservation effectuée avec succès !");
        alert.showAndWait();
        // Mettre à jour la réservation et les places disponibles
        int nbPlacesReservees = cours.getReservation();
        nbPlacesReservees++;
        cours.setReservation(nbPlacesReservees);

        int nbPlacesDispo = cours.getNbPlacesTotal();
        nbPlacesDispo--;
        cours.setNbPlacesTotal(nbPlacesDispo);

        ((Text) innerContainer.lookup("#reservationText")).setText("Reservation : " + cours.getReservation());
        ((Text) innerContainer.lookup("#nb_places_totalText")).setText("Places Total : " + cours.getNbPlacesTotal());

        // Ajouter l'utilisateur actuel au cours et sauvegarder le cours
        cours.addUser(currentUser);
        ServiceCours.getInstance().edit(cours);
      //  mailing();
   SendSMS sm = new SendSMS();
    sm.sendSMS(cours);
        

    } else {
        // Afficher une alerte indiquant qu'il n'y a plus de places disponibles
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de réservation");
        alert.setHeaderText(null);
        alert.setContentText("Il n'y a plus de places disponibles pour ce cours !");
        alert.showAndWait();
    }
});

      
        innerContainer.getChildren().add(reserverButton);

            // Récupérer le nom de la salle correspondante à partir de son id
            int idSalle = cours.getSalle_idd();
            String salleNom = "";
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Infantry", "root", "root");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT nom FROM salle WHERE id = " + idSalle);
                if (rs.next()) {
                    salleNom = rs.getString("nom");
                }
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ((Text) innerContainer.lookup("#nomSalleText")).setText("Salle : " + salleNom);

            // Récupérer le nom du coach correspondant à partir de son id
            int idCoach = cours.getCoach_idd();
            String coachNom = "";
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/Infantry", "root", "root");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT nom FROM coach WHERE id = " + idCoach);
                if (rs.next()) {
                    coachNom = rs.getString("nom");
                }
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ((Text) innerContainer.lookup("#nomCoachText")).setText("Coach : " + coachNom);

            Path selectedImagePath = FileSystems.getDefault().getPath(cours.getImage());
            if (selectedImagePath.toFile().exists()) {
                ((ImageView) innerContainer.lookup("#imageIV")).setImage(new Image(selectedImagePath.toUri().toString()));
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

}
