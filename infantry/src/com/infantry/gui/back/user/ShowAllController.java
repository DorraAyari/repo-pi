package com.infantry.gui.back.user;

import com.infantry.entities.User;
import com.infantry.gui.back.MainWindowController;
import com.infantry.services.UserService;
import com.infantry.utils.AlertUtils;
import com.infantry.utils.Constants;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;

public class ShowAllController implements Initializable {

    public static User currentUser;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;
    @FXML
    public TextField searchTF;
    @FXML
    public ComboBox<String> sortCB;
    @FXML
    public Button exelButton;

    List<User> listUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listUser = UserService.getInstance().getAll();
        sortCB.getItems().addAll("Email", "Nom", "Prenom", "Numero"); // trier par nom prenom ...
        displayData("");
    }

    void displayData(String searchText) {
        mainVBox.getChildren().clear();

        Collections.reverse(listUser);

        if (!listUser.isEmpty()) {
            for (User user : listUser) {
                if (user.getEmail().toLowerCase().startsWith(searchText.toLowerCase())) {
                    mainVBox.getChildren().add(makeUserModel(user));
                }// recherche par mail sino showAll tet3ada true 

            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeUserModel(
            User user
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_USER)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#emailText")).setText("Email : " + user.getEmail());
            ((Text) innerContainer.lookup("#rolesText")).setText("Roles : " + user.getRoles());
            ((Text) innerContainer.lookup("#nomText")).setText("Nom : " + user.getNom());
            ((Text) innerContainer.lookup("#prenomText")).setText("Prenom : " + user.getPrenom());
            ((Text) innerContainer.lookup("#numeroText")).setText("Numero : " + user.getNumero());

            Path selectedImagePath = FileSystems.getDefault().getPath(user.getPhoto());
            if (selectedImagePath.toFile().exists()) {
                ((ImageView) innerContainer.lookup("#imageIV")).setImage(new Image(selectedImagePath.toUri().toString()));
            }

            ((Button) innerContainer.lookup("#genererPDFButton")).setOnAction((event) -> genererPDF(user));
            ((Button) innerContainer.lookup("#editButton")).setOnAction((event) -> modifierUser(user));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((event) -> supprimerUser(user));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    private void modifierUser(User user) {
        currentUser = user;
        MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_USER);
    }

    private void supprimerUser(User user) {
        currentUser = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer user ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            if (UserService.getInstance().delete(user.getId())) {
                MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_USER);
            } else {
                AlertUtils.makeError("Could not delete user");
            }
        }
    }


    @FXML
    public void sort(ActionEvent actionEvent) {
        Constants.compareVar = sortCB.getValue();
        Collections.sort(listUser);
        displayData("");
    }

    @FXML
    private void search(KeyEvent event) {
        displayData(searchTF.getText());
    }


    public void genererExcel(ActionEvent ignored) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        try {
            FileChooser chooser = new FileChooser();
            // Set extension filter
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Excel Files(.xls)", ".xls");
            chooser.getExtensionFilters().add(filter);

            HSSFSheet workSheet = workbook.createSheet("sheet 0");

            HSSFFont fontBold = workbook.createFont();
            fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            HSSFCellStyle styleBold = workbook.createCellStyle();
            styleBold.setFont(fontBold);

            Row row1 = workSheet.createRow((short) 0);

            row1.createCell(1).setCellValue("Email");
            row1.createCell(2).setCellValue("Roles");
            row1.createCell(3).setCellValue("Password");
            row1.createCell(4).setCellValue("Nom");
            row1.createCell(5).setCellValue("Prenom");
            row1.createCell(6).setCellValue("Numero");
            row1.createCell(7).setCellValue("Photo");

            int i = 0;
            for (User user : listUser) {
                i++;
                Row row2 = workSheet.createRow((short) i);

                row2.createCell(1).setCellValue(user.getEmail());
                row2.createCell(2).setCellValue(user.getRoles());
                row2.createCell(3).setCellValue(user.getPassword());
                row2.createCell(4).setCellValue(user.getNom());
                row2.createCell(5).setCellValue(user.getPrenom());
                row2.createCell(6).setCellValue(user.getNumero());
                row2.createCell(7).setCellValue(user.getPhoto());
            }

            workbook.write(Files.newOutputStream(Paths.get("reclamation.xls")));
            Desktop.getDesktop().open(new File("reclamation.xls"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void genererPDF(User user) {
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, Files.newOutputStream(Paths.get("user.pdf")));
            document.open();

            com.itextpdf.text.Font font = new com.itextpdf.text.Font();
            font.setSize(20);

            document.add(new Paragraph("- User -"));

            try {
                com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(user.getPhoto());
                image.scaleAbsoluteWidth(300);
                image.scaleAbsoluteHeight(300);
                image.isScaleToFitHeight();
                document.add(image);
            } catch (FileNotFoundException e) {
                AlertUtils.makeError("Photo not found, PDF will display without image");
            }

            document.add(new Paragraph("Email : " + user.getEmail()));
            document.add(new Paragraph("Roles : " + user.getRoles()));
            document.add(new Paragraph("Password : " + user.getPassword()));
            document.add(new Paragraph("Nom : " + user.getNom()));
            document.add(new Paragraph("Prenom : " + user.getPrenom()));
            document.add(new Paragraph("Numero : " + user.getNumero()));

            document.newPage();
            document.close();

            writer.close();

            Desktop.getDesktop().open(new File("user.pdf"));
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
