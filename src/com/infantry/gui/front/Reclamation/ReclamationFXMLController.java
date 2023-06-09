/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infantry.gui.front.Reclamation;

import com.infantry.entities.Pdf;
import com.infantry.entities.Reclamation;
import com.infantry.entities.User;
import com.infantry.services.ServiceReclamation;
import com.infantry.services.UserService;
import com.infantry.utils.DatabaseConnection;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import org.controlsfx.control.Notifications;
import static sun.plugin.javascript.navig.JSType.Image;

/**
 * FXML Controller class
 *
 * @author User
 */
public class ReclamationFXMLController implements Initializable {
@FXML
private ComboBox<User> userComboBox;
    
@FXML
private ComboBox<User> useremailComboBox;

@FXML
private ComboBox<User> userprenomComboBox;

    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField email;
    @FXML
    private TextField message;
    @FXML
    private TableView<Reclamation> TableReclamation;
    @FXML
    private TableColumn<Reclamation, Integer> idT;
    @FXML
    private TableColumn<Reclamation, String> NomT;
    @FXML
    private TableColumn<Reclamation, String> PrenomT;
    @FXML
    private TableColumn<Reclamation, String> EmailT;
    @FXML
    private TableColumn<Reclamation, String> MessageT;

        
    private final ObservableList<Reclamation> data = FXCollections.observableArrayList();

        
    private Statement ste;
    private Connection con;
    ServiceReclamation sr = new ServiceReclamation();
    @FXML
    private Label idreclabel;
    @FXML
    private TextField Recherche;
    @FXML
    private Pagination pagination;
   
    private final static int rowPerPage= 10;
    private final  TableView<Reclamation> table = createTable();
    private final ObservableList<Reclamation> dataa = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    UserService userService = new UserService();
    ObservableList<User> userList = FXCollections.observableArrayList(userService. getAll());
    Collections.sort(userList, Comparator.comparing(User::getNom)); // trier par nom
    
    nom.setText(userList.get(0).getNom());
    prenom.setText(userList.get(0).getPrenom());
    email.setText(userList.get(0).getEmail());
    userComboBox.setItems(userList);
     userComboBox.setConverter(new StringConverter<User>() {
        @Override
        public String toString(User user) {
            return user.getNom(); // retourne le nom de la user
        }

        @Override
        public User fromString(String nom) {
            // Pas besoin d'implémenter cette méthode car elle ne sera pas utilisée
            return null;
        }
    });
     useremailComboBox.setItems(userList);
     useremailComboBox.setConverter(new StringConverter<User>() {
        @Override
        public String toString(User user) {
            return user.getEmail(); // retourne le nom de la user
        }

        @Override
        public User fromString(String email) {
            // Pas besoin d'implémenter cette méthode car elle ne sera pas utilisée
            return null;
        }
    });
      userprenomComboBox.setItems(userList);
     userprenomComboBox.setConverter(new StringConverter<User>() {
        @Override
        public String toString(User user) {
            return user.getPrenom(); // retourne le nom de la user
        }

        @Override
        public User fromString(String prenom) {
            // Pas besoin d'implémenter cette méthode car elle ne sera pas utilisée
            return null;
            }
        });

        try {
            // TODO
            Aff();
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

           
    public void Aff() throws SQLException {
                        try {
            con = DatabaseConnection.getInstance().getConnection();
            ste = con.createStatement();
            data.clear();

            ResultSet res = ste.executeQuery("select * from Reclamation");
            while(res.next()){
                Reclamation f=new Reclamation(res.getInt(1), res.getString("nom"),res.getString("prenom"), res.getString("email"), res.getString("message"));
                data.add(f);
            }

        } catch (Exception e) {
                //Logger.getLogger(tab)
        }
            idT.setCellValueFactory(new PropertyValueFactory<>("id"));
            NomT.setCellValueFactory(new PropertyValueFactory<>("nom"));
            PrenomT.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            EmailT.setCellValueFactory(new PropertyValueFactory<>("email"));
            MessageT.setCellValueFactory(new PropertyValueFactory<>("message"));
            
            TableReclamation.setItems(data);
            RechercheAV();
            pagination.setPageFactory(this::createPage);

    }
        private Node createPage(int pageIndex){
        int fromIndex = pageIndex * rowPerPage;
        int toIndex = Math.min(fromIndex + rowPerPage,data.size());
        table.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));
        return table;
    }
     
        
       private TableView<Reclamation> createTable(){
                                      
           try {
            con = DatabaseConnection.getInstance().getConnection();
            ste = con.createStatement();
            dataa.clear();

            ResultSet res = ste.executeQuery("select * from Reclamation");
            while(res.next()){
                Reclamation f=new Reclamation(res.getInt(1), res.getString("nom"),res.getString("prenom"), res.getString("email"), res.getString("message"));
                dataa.add(f);
            }

        } catch (Exception e) {
                //Logger.getLogger(tab)
        }
                      
        TableView<Reclamation> table = new TableView<>() ;
        TableColumn<Reclamation,Integer> idTT = new TableColumn<>("id");
        TableColumn<Reclamation,String> NomTT = new TableColumn<>("nom");
        TableColumn<Reclamation,String> PrenomTT = new TableColumn<>("prenom");
        TableColumn<Reclamation,String> EmailTT = new TableColumn<>("email");
        TableColumn<Reclamation,String> MessageTT = new TableColumn<>("message");
                   
            idTT.setCellValueFactory(new PropertyValueFactory<>("id"));
            NomTT.setCellValueFactory(new PropertyValueFactory<>("nom"));
            PrenomTT.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            EmailTT.setCellValueFactory(new PropertyValueFactory<>("email"));
            MessageTT.setCellValueFactory(new PropertyValueFactory<>("message"));

            table.setItems(dataa);
               
            table.getColumns().addAll(idTT,NomTT,PrenomTT,EmailTT,MessageTT);
            
            return table;
    }

            
       public void RechercheAV(){
                // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Reclamation> filteredData = new FilteredList<>(data, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		Recherche.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(tmp -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (tmp.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} else if (tmp.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 )
                                        return true;
				     else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Reclamation> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(TableReclamation.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		TableReclamation.setItems(sortedData);
    }

    
        
    private boolean Validchamp(TextField T){
        return !T.getText().isEmpty() && T.getLength() > 2;
        //return !T.getText().isEmpty();
    }

    @FXML
    private void Ajouter(ActionEvent event) throws SQLException {
        User user =userComboBox.getValue();
        User selectedUser = userComboBox.getSelectionModel().getSelectedItem();

               
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!email.getText().matches(emailRegex)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Format email incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un email valide !");
            alert.showAndWait();
        }
        else
        {
                    if(Validchamp(nom) && Validchamp(prenom)  && Validchamp(message))
        {

         Reclamation r = new Reclamation(nom.getText(), prenom.getText() , email.getText(),message.getText(),user);
         userComboBox.getSelectionModel().clearSelection();
         sr.insert(r);
         Notifications.create()
        .title("reclamation ajouté")
        .text("La recalmation a été ajouté avec succès.")
        .showInformation();
   
        Aff();
             nom.setText("");
             prenom.setText("");
             email.setText("");
             message.setText("");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ajouté");
        alert.setHeaderText(null);
        alert.setContentText("Ajouté avec succès!");

        alert.showAndWait();

        }
        else
        {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("Verifiez les champs!");

        alert.showAndWait();
        }   
        }

    }


    @FXML
    private void Supprimer(ActionEvent event) throws SQLException {
        
                   
            TableReclamation.setItems(data);

             ObservableList<Reclamation> allReclamations,SingleReclamation ;
             allReclamations=TableReclamation.getItems();
             SingleReclamation=TableReclamation.getSelectionModel().getSelectedItems();
             Reclamation A = SingleReclamation.get(0);
             sr.delete(A.getId());
             SingleReclamation.forEach(allReclamations::remove);
             Aff();
                   
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Supprimer");
             alert.setHeaderText(null);
             alert.setContentText("Supprimé avec succès!");

             alert.showAndWait();
    }

    @FXML
    private void Modifier(ActionEvent event) throws SQLException {
                       
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!email.getText().matches(emailRegex)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Format email incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un email valide !");
            alert.showAndWait();
        }
        else
        {
          if(Validchamp(nom) && Validchamp(prenom)  && Validchamp(message))
        {

         Reclamation r = new Reclamation(Integer.valueOf(idreclabel.getText()),nom.getText(), prenom.getText() , email.getText(),message.getText());
         sr.update(r);
   
        Aff();
             nom.setText("");
             prenom.setText("");
             email.setText("");
             message.setText("");

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Modifier");
        alert.setHeaderText(null);
        alert.setContentText("Modifier avec succès!");

        alert.showAndWait();

        }
        else
        {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("Verifiez les champs!");

        alert.showAndWait();
        }   
        }
    }

    @FXML
    private void LoadData(MouseEvent event) {
                     
        Reclamation SingleReclamation=TableReclamation.getSelectionModel().getSelectedItems().get(0);

        idreclabel.setText(String.valueOf(SingleReclamation.getId()));
        nom.setText(SingleReclamation.getNom());
        prenom.setText(SingleReclamation.getPrenom());
        message.setText(SingleReclamation.getMessage());
        email.setText(SingleReclamation.getEmail());
    }

    @FXML
    private void btnPDF(ActionEvent event) throws FileNotFoundException, SQLException, DocumentException {
        
                Reclamation SingleReclamation=TableReclamation.getSelectionModel().getSelectedItems().get(0);

                Pdf p = new Pdf();
                p.add("Reclamation", SingleReclamation);
    }
    
}
