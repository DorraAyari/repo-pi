package com.infantry.gui;

import com.infantry.MainApp;
import com.infantry.entities.User;
import com.infantry.services.UserService;
import com.infantry.utils.AlertUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class RegisterController implements Initializable {

    @FXML
    public TextField emailTF;
    @FXML
    public TextField passwordTF;
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

    Path selectedImagePath;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        topText.setText("Inscription");
        btnAjout.setText("S'inscrire");
    }

    @FXML
    private void manage(ActionEvent event) {

        if (controleDeSaisie()) {


            createImageFile();
            String imagePath = selectedImagePath.toString();

            User user = new User(
                    emailTF.getText(),
                    "ROLE_USER",
                    passwordTF.getText(),
                    nomTF.getText(),
                    prenomTF.getText(),
                    numeroTF.getText(),
                    imagePath
            );

            if (UserService.getInstance().add(user)) {
                try {
                    sendMail(emailTF.getText());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                AlertUtils.makeSuccessNotification("Inscription effectué avec succés");
                MainApp.getInstance().loadLogin();
            } else {
                AlertUtils.makeError("Existe deja !");
            }
        }
    }

    public static void sendMail(String recepient) throws Exception {
        System.out.println("Preparing to send email");
        Properties properties = new Properties();

        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");

        //Your gmail address
        String myAccountEmail = "pidev.app.esprit@gmail.com";
        //Your gmail password
        String password = "jkemsuddgeptmcsb";

        //Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        //Prepare email message
        Message message = prepareMessage(session, myAccountEmail, recepient);

        //Send mail
        if (message != null){
            Transport.send(message);
            System.out.println("Mail sent successfully");
        } else {
            System.out.println("Error sending the mail");
        }
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Welcome");
            String htmlCode = "<h1>Notification</h1> <br/> <h2><b>Welcome to infantry</b></h2>";
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return null;
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

    @FXML
    public void connexion(ActionEvent actionEvent) {
        MainApp.getInstance().loadLogin();
    }

    private boolean controleDeSaisie() {


        if (emailTF.getText().isEmpty()) {
            AlertUtils.makeInformation("Email ne doit pas etre vide");
            return false;
        }
        if (!Pattern.compile("^(.+)@(.+)$").matcher(emailTF.getText()).matches()) {
            AlertUtils.makeInformation("Email invalide");
            return false;
        }


        if (passwordTF.getText().isEmpty()) {
            AlertUtils.makeInformation("Password ne doit pas etre vide");
            return false;
        }


        if (nomTF.getText().isEmpty()) {
            AlertUtils.makeInformation("Nom ne doit pas etre vide");
            return false;
        }


        if (prenomTF.getText().isEmpty()) {
            AlertUtils.makeInformation("Prenom ne doit pas etre vide");
            return false;
        }


        if (numeroTF.getText().isEmpty()) {
            AlertUtils.makeInformation("Numero ne doit pas etre vide");
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