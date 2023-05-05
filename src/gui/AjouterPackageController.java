package gui;

import entities.Package;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.PackageService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class AjouterPackageController implements Initializable {

    @FXML
    private TextArea choicesTextArea;

    private PackageService packageService;
    private Package main;

    // connect main class to controller
    public void setMain(Package main) {
        this.main = main;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        packageService = new PackageService();
    }

    // add package with input validation
    @FXML

private void addPackage(ActionEvent event) throws EmailException {
    String choices = choicesTextArea.getText();

    if (choices.trim().isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please enter package choices");
        alert.showAndWait();
        return;
    }

    Package packageObj = new Package(choices);
    try {
        packageService.ajouter(packageObj);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Package added successfully");
        alert.showAndWait();
        
        // create and send the email message
        Email email = new SimpleEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(587);
        email.setAuthenticator(new DefaultAuthenticator("mr.djebbi@gmail.com", "wbifhnjnavzxibru"));
        email.setSSLOnConnect(true);
        try {
            email.setFrom("noreplyinfantryy@gmail.com");
            email.setSubject("Package ajouté");
            email.setMsg("Un nouveau package a été ajouté avec succès!");
            email.addTo("zoubeir.ezzine@esprit.tn");//7ot mail nta3ek hna
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }

    } catch (SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Error while adding package");
        alert.showAndWait();
        System.out.println("Error while adding package: " + e.getMessage());
    }
}

    

    @FXML
    private void retourner(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuGestionPackage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void afficherPackages(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherPackage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
