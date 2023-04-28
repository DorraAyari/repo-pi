package com.infantry.gui;

import com.infantry.MainApp;
import com.infantry.entities.User;
import com.infantry.services.UserService;
import com.infantry.utils.AlertUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public TextField inputEmail;
    @FXML
    public PasswordField inputPassword;
    @FXML
    public Button btnSignup;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }


    public void signUp(ActionEvent actionEvent) {
        MainApp.getInstance().loadSignup();
    }

    public void login(ActionEvent actionEvent) {
        User user = UserService.getInstance().checkUser(inputEmail.getText(), inputPassword.getText());

        if (user != null) {
            System.out.println(user.getRoles());
            if (user.getRoles().equals("\"ROLE_ADMIN\"")){
                MainApp.getInstance().loadBack();
            } else {
                MainApp.getInstance().login(user);
            }
        } else {
            AlertUtils.makeError("Identifiants invalides");
        }
    }

}
