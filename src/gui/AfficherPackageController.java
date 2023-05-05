package gui;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import entities.Package;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.PackageService;

public class AfficherPackageController implements Initializable{

    @FXML
    private TableView<Package> tableView;

    @FXML
    private TableColumn<Package, Integer> idCol;

    @FXML
    private TableColumn<Package, Integer> useridCol;

    @FXML
    private TableColumn<Package, String> choicesCol;

    @FXML
    private TextField idField;

    @FXML
    private TextField iduserField;

    @FXML
    private TextField choicesField;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button generatePdfButton;
    
    
    
@Override
public void initialize(URL url, ResourceBundle rb) {
    idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    useridCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
    choicesCol.setCellValueFactory(new PropertyValueFactory<>("choices"));

    tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            idField.setText(String.valueOf(newSelection.getId()));
            iduserField.setText(String.valueOf(newSelection.getUserId()));
            choicesField.setText(newSelection.getChoices());
        }
    });

    loadPackages(); // Call the method to load packages into the table view.
}








private PackageService packageService = new PackageService();
@FXML
private void Edit(ActionEvent event) {
    // get the selected package from the table view
    Package selectedPackage = tableView.getSelectionModel().getSelectedItem();
    if (selectedPackage != null) {
        // update the selected package with the input data from the text fields
        String choices = choicesField.getText();

        selectedPackage.setChoices(choices);

        try {
            // call the modifier function from PackageService to update the package in the database
            packageService.modifier(selectedPackage);
        } catch (SQLException e) {
            // handle the exception
        }

        // refresh the table view
        tableView.refresh();
    }
}

 @FXML
private void Delete(ActionEvent event) {
    // get the selected package from the table view
    Package selectedPackage = tableView.getSelectionModel().getSelectedItem();
    if (selectedPackage != null) {
        try {
            // call the supprimer function from PackageService to delete the package from the database
            packageService.supprimer(selectedPackage);
        } catch (SQLException e) {
            // handle the exception
        }

        // remove the selected package from the table view's items
        tableView.getItems().remove(selectedPackage);
    }
}

@FXML
private void generatePdf(ActionEvent event) throws FileNotFoundException, DocumentException {
    // create a new PDF document
    Document document = new Document();

    // create a new PDF writer
    PdfWriter.getInstance(document, new FileOutputStream("packages.pdf"));

    // open the document
    document.open();

    // add a title to the document
    Paragraph title = new Paragraph("Packages");
    title.setAlignment(Element.ALIGN_CENTER);
    document.add(title);

    // add a table to the document
    PdfPTable table = new PdfPTable(3);
    table.addCell("ID");
    table.addCell("User ID");
    table.addCell("Choices");

    // populate the table with data from the packages list
    ObservableList<Package> packages = tableView.getItems();
    for (Package p : packages) {
        table.addCell(Integer.toString(p.getId()));
        table.addCell(Integer.toString(p.getUserId()));
        table.addCell(p.getChoices());
    }

    // add the table to the document
    document.add(table);

    // close the document
    document.close();

    // show a message to the user
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("PDF Generated");
    alert.setHeaderText(null);
    alert.setContentText("The PDF report has been generated.");
    alert.showAndWait();
}
public void loadPackages() {
    try {
        // Instantiate the package service and the packages list.
        PackageService packageService = new PackageService();
        List<Package> packages = packageService.recuperer();
        ObservableList<Package> obs = FXCollections.observableArrayList(packages);
        tableView.setItems(obs);

        // Set the cell value factories for the table columns.
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        useridCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        choicesCol.setCellValueFactory(new PropertyValueFactory<>("choices"));

        // Set up the on-click listener for the table view.
        tableView.setOnMouseClicked((event) -> {
            if (event.getClickCount() > 1) {
                Package p = tableView.getSelectionModel().getSelectedItem();
                if (p != null) {
                    idField.setText(Integer.toString(p.getId()));
                    iduserField.setText(Integer.toString(p.getUserId()));
                    choicesField.setText(p.getChoices());
                }
            }
        });

    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}


    @FXML
    private void retourner(ActionEvent event) {
               try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterPackage.fxml"));
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
    private void consult(ActionEvent event) {
               try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ConsulterPackage.fxml"));
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
