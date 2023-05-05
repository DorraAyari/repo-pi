package gui;

import com.google.zxing.BarcodeFormat;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import com.google.zxing.WriterException;
import java.util.List;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Reservation;
import java.io.FileOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.ReservationService;
public class AfficherReservationController implements Initializable {

    @FXML
    private TableView<Reservation> tableview;


    
   @FXML
    private TableColumn<Reservation, Integer> idCol;
    @FXML
    private TableColumn<Reservation, Integer> useridCol;
    @FXML
    private TableColumn<Reservation, Date> startCol;
    @FXML
    private TableColumn<Reservation, Date> finishCol;
    @FXML
    private TableColumn<Reservation, Integer> capacityCol;
    @FXML
    private TableColumn<Reservation, Date> dayCol;
    @FXML
    private TextField idF;
  @FXML
    private TextField idField;
    @FXML
    private DatePicker startField;
    @FXML
    private DatePicker finishField;
    @FXML
    private TextField capacityField;
    @FXML
    private DatePicker dayField;
    


    // Initializes the controller class.
@Override

public void initialize(URL url, ResourceBundle rb) {
           // Instantiate the service service and the services list.
       




        

    ReservationService reservationService = new ReservationService();
    idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    useridCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
    startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
    finishCol.setCellValueFactory(new PropertyValueFactory<>("finish"));
    capacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
    dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));


// Set up the on-click listener for the table view.
tableview.setOnMouseClicked((event) -> {
    if (event.getClickCount() > 1) {
        Reservation reservation = tableview.getSelectionModel().getSelectedItem();
        if (reservation != null) {
            idField.setText(Integer.toString(reservation.getId()));
           startField.setValue(reservation.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
finishField.setValue(reservation.getFinish().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            capacityField.setText(Integer.toString(reservation.getCapacity()));
Date date = reservation.getDay();
java.util.Date utilDate = new java.util.Date(date.getTime());
LocalDate localDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
dayField.setValue(localDate);        }
    }
});

}





@FXML
private void modifier(ActionEvent event) throws SQLException {
//int id = Integer.parseInt(idField.getText());
LocalDate start = startField.getValue();
LocalDate finish = finishField.getValue();
int capacity = Integer.parseInt(capacityField.getText());
LocalDate day = dayField.getValue();
if (start == null || finish == null || day == null) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error Message");
    alert.setHeaderText(null);
    alert.setContentText("Please fill in all the required fields");
    alert.showAndWait();
    return;
}

LocalDateTime startTime = LocalDateTime.of(start, LocalTime.now());
LocalDateTime finishTime = LocalDateTime.of(finish, LocalTime.now());
LocalDateTime dayTime = LocalDateTime.of(day, LocalTime.now());

Reservation reservation = new Reservation( Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant()),
        Date.from(finishTime.atZone(ZoneId.systemDefault()).toInstant()), capacity,
        Date.from(dayTime.atZone(ZoneId.systemDefault()).toInstant()));
ReservationService reservationService = new ReservationService();
reservationService.modifier(reservation);
tableview.getItems().setAll(reservationService.recuperer());
}



    @FXML
    private void retourner(ActionEvent event) {
               try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterReservation.fxml"));
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
private void supprimer(ActionEvent event) {
    ReservationService reservationService = new ReservationService();
    int id = Integer.parseInt(idField.getText());
    Reservation reservation = new Reservation(id, 0, null, null, 0, null);
    try {
        reservationService.supprimer(reservation);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("infarnyy :: Success Message");
        alert.setHeaderText(null);
        alert.setContentText("Reservation supprimée");
        alert.showAndWait();
        
        // Refresh tableview
        List<Reservation> reservations = reservationService.recuperer();
        ObservableList<Reservation> obs = FXCollections.observableArrayList(reservations);
        tableview.setItems(obs);
    } catch (SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("infantryy  :: Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Erreur lors de la suppression de la réservation");
        alert.showAndWait();
    }
}



@FXML
private void recherche(ActionEvent event) {
    ReservationService reservationService = new ReservationService();
    
    try {
        int UserId = Integer.parseInt(idF.getText());
        
        List<Reservation> reservations = null;
        
        if(UserId != 0) {
            // Search by userId
            reservations = reservationService.rechercheByUserId(UserId);
        } else {
            // Invalid search parameters
            throw new IllegalArgumentException("Invalid search parameters");
        }
        
        ObservableList<Reservation> obs = FXCollections.observableArrayList(reservations);
        tableview.setItems(obs);
    } catch (SQLException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Infarnyy:: Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Erreur lors de la recherche de réservation");
        alert.showAndWait();
    } catch (IllegalArgumentException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Infarnyy :: Error Message");
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}


    
@FXML
private void generateQrCode(ActionEvent event) {
Reservation reservation = tableview.getSelectionModel().getSelectedItem();
if (reservation != null) {
try {
    // Create QR code image
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    String qrCodeData = "reservation_" + reservation.getId();
    String qrCodeFilePath = "src/qrcodes/" + reservation.getId() + ".png";
    int qrCodeWidth = 256;
    int qrCodeHeight = 256;
    BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, qrCodeWidth, qrCodeHeight);
   Path qrCodePath = FileSystems.getDefault().getPath(qrCodeFilePath);
MatrixToImageWriter.writeToPath(bitMatrix, "PNG", qrCodePath.toFile());


    // Create PDF document
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream("reservation_" + reservation.getId() + ".pdf"));
    document.open();

    // Add title to the document
    Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE);
    Paragraph title = new Paragraph("Détails réservation", titleFont);
    title.setAlignment(Element.ALIGN_CENTER);
    title.setSpacingAfter(20);
    document.add(title);

    // Add image to the document
    try {
        String currentWorkingDir = System.getProperty("user.dir");
        Image qrCodeImage = Image.getInstance(qrCodeFilePath);
        qrCodeImage.scaleAbsolute(150f, 150f);
        document.add(qrCodeImage);
    } catch (IOException e) {
        e.printStackTrace();
    }

    // Create PDF table
    PdfPTable table = new PdfPTable(3);
    table.setWidthPercentage(100);

    // Add table headers
    PdfPCell cell1 = new PdfPCell(new Phrase("Day"));
    PdfPCell cell2 = new PdfPCell(new Phrase("Début"));
    PdfPCell cell3 = new PdfPCell(new Phrase("Fin"));
    table.addCell(cell1);
    table.addCell(cell2);
    table.addCell(cell3);

    // Add table row with selected reservation data
    table.addCell(reservation.getDay().toString());
    table.addCell(reservation.getStart().toString());
    table.addCell(reservation.getFinish().toString());

    // Add table to document
    document.add(table);

    // Close document
    document.close();

    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Le code QR et le fichier PDF ont été générés avec succès.");
    alert.showAndWait();
} catch (WriterException | IOException | DocumentException ex) {
    ex.printStackTrace();
    Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de la génération du code QR et/ou du fichier PDF.");
    alert.showAndWait();
}
}
}




public void loadReservations() {
    try {
        // Instantiate the reservation service and the reservations list.
        ReservationService reservationService = new ReservationService();
        List<Reservation> reservations = reservationService.recuperer();
        ObservableList<Reservation> obs = FXCollections.observableArrayList(reservations);
        tableview.setItems(obs);

        // Set the cell value factories for the table columns.
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        useridCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        finishCol.setCellValueFactory(new PropertyValueFactory<>("finish"));
        capacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));

        // Set up the on-click listener for the table view.
        tableview.setOnMouseClicked((event) -> {
            if (event.getClickCount() > 1) {
                Reservation reservation = tableview.getSelectionModel().getSelectedItem();
                if (reservation != null) {
                    idField.setText(Integer.toString(reservation.getId()));
                    startField.setValue(reservation.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    finishField.setValue(reservation.getFinish().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    capacityField.setText(Integer.toString(reservation.getCapacity()));
                    dayField.setValue(reservation.getDay().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                }
            }
        });

    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}




}
