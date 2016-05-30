package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.KunstwerkController.AbdruckData;
import application.KunstwerkController.BestellungData;
import application.KunstwerkController.KunstwerkData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class KuenstlerMenueController implements Initializable {
	
	public LoginModel loginModel = new LoginModel();
    @FXML
    private Label isConnected;
	
	private static Connection con;
	private PreparedStatement prep;
	private static Statement stat;
	private ObservableList<KunstwerkData> data;
	private ObservableList<AbdruckData> abdruckdata;
	private ObservableList<BestellungData> bestelldata;
	
	@FXML
	private Label userLbl;
	@FXML
	private Label bestellid;
	@FXML
	private TextField preis;
	@FXML
	private TextField hoehe;
	@FXML
	private TextField breite;
	@FXML
	private TextField kw_id;
	
	
	
	
    @FXML 
    private TableView table;
	@FXML
	private TableColumn column1;
	@FXML
	private TableColumn column2;
	@FXML
	private TableColumn column3;
	@FXML
	private TableColumn column4;
	
    @FXML 
    private TableView abdrucktable;
	@FXML
	private TableColumn abd1;
	@FXML
	private TableColumn abd2;
	@FXML
	private TableColumn abd3;
	@FXML
	private TableColumn abd4;
	@FXML
	private TableColumn abd5;
	
    @FXML 
    private TableView bestellungtable;
	@FXML
	private TableColumn best1;
	@FXML
	private TableColumn best2;
	@FXML
	private TableColumn best3;
	@FXML
	private TableColumn best4;
	@FXML
	private TableColumn best5;
	
	@FXML
	private DatePicker ausdate;
	
	
	public void GetUser(String user) {
		// TODO Auto-generated method stub
		userLbl.setText(user);
		
	}
	
	public void SignOut(ActionEvent event) {
		
		try {
			// versteckt Login-Fenster
			((Node)event.getSource()).getScene().getWindow().hide();
			
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			
			Pane root = loader.load(getClass().getResource("/application/Login.fxml").openStream());
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e){
			// TODO: handle exception
		}		
	}
	
	/*public void ShowRelationen (ActionEvent event) {
		try {
			// versteckt Login-Fenster
			((Node)event.getSource()).getScene().getWindow().hide();
			
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			
			Pane root = loader.load(getClass().getResource("/application/Relationen.fxml").openStream());
			RelationenController relationenController = (RelationenController)loader.getController();
			relationenController.GetUser(userLbl.getText());
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} */
	
	public void abdruckSpeichern(ActionEvent event) throws InterruptedException{

			try {

		            prep = con.prepareStatement("insert into Abdruck(preis,hoehe,breite,kunstwerk_id) values(?,?,?,?);");
		            prep.setString(1, preis.getText());
		            prep.setString(2, hoehe.getText());
		            prep.setString(3, breite.getText());
		            prep.setString(4, kw_id.getText());
		            prep.execute();
		            preis.setText(null);
		            hoehe.setText(null);
		            breite.setText(null);
		            kw_id.setText(null);

		        } catch (SQLException ex) {

		        }
       
	}
	
	public void deleteAbdruck(ActionEvent event){
		try {
			ObservableList allEntries = abdrucktable.getItems();
			ObservableList entrieSelected = abdrucktable.getSelectionModel().getSelectedItems();
			prep = con.prepareStatement("delete from Abdruck where abdruck_id = ?");
			prep.setString(1, (String) abd1.getCellData(abdrucktable.getSelectionModel().getSelectedIndex()));			
           prep.execute();
           initialize(null, null);
           entrieSelected.forEach(allEntries::remove);
		} catch (SQLException ex) {
       }
	}
	
	public void showKunstwerke(ActionEvent event) throws InterruptedException{
		
		try {
        	table.setEditable(true);

            con = SqliteConnection.Connector();
            stat = con.createStatement();

            data = FXCollections.observableArrayList();
            prep =con.prepareStatement("select Kunstwerk.kunstwerk_id, Kunstwerk.entstehungsjahr, Kunstwerk.kunstwerkname, Kunstwerk.kunststilname from Kunstwerk JOIN Kuenstler ON Kunstwerk.kunstwerk_id=Kuenstler.benutzer_id JOIN Benutzer ON Kuenstler.benutzer_id=Benutzer.benutzer_id where Benutzer.benutzername=?");
            prep.setString(1, userLbl.getText());
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                data.add(new KunstwerkData(rs.getString("kunstwerk_id"), rs.getString("entstehungsjahr"), rs.getString("kunstwerkname"), rs.getString("kunststilname")));
            }
            column1.setCellValueFactory(new PropertyValueFactory("kunstwerk_id"));
            column2.setCellValueFactory(new PropertyValueFactory("entstehungsjahr"));
            column3.setCellValueFactory(new PropertyValueFactory("kunstwerkname"));
            column4.setCellValueFactory(new PropertyValueFactory("kunststilname"));


            table.setItems(null);
            table.setItems(data);
            table.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                        if(mouseEvent.getClickCount() == 1){
                            kw_id.setText((String) column1.getCellData(table.getSelectionModel().getSelectedIndex()));
                        }
                    }
                }
            });
                
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fehler bei tabelle einlesen");
        }

       
	}
	
	public void showAbdruecke(ActionEvent event) throws InterruptedException{
		
    	try {
        	abdrucktable.setEditable(true);

            con = SqliteConnection.Connector();
            stat = con.createStatement();

            abdruckdata = FXCollections.observableArrayList();
            prep =con.prepareStatement("SELECT Abdruck.abdruck_id, Abdruck.preis, Abdruck.hoehe,Abdruck.breite,Abdruck.kunstwerk_id FROM Abdruck JOIN Kunstwerk ON Abdruck.kunstwerk_id=Kunstwerk.kunstwerk_id JOIN Erstellt ON Kunstwerk.kunstwerk_id=Erstellt.kunstwerk_id JOIN Benutzer ON Erstellt.benutzer_id=Benutzer.benutzer_id WHERE Benutzer.benutzername=?");
            prep.setString(1, userLbl.getText());
            ResultSet rs = prep.executeQuery();

            while (rs.next()) {
            	abdruckdata.add(new AbdruckData(rs.getString("abdruck_id"),rs.getString("preis"),rs.getString("hoehe"),rs.getString("breite"),rs.getString("kunstwerk_id")));
            }
            abd1.setCellValueFactory(new PropertyValueFactory("abdruck_id"));
            abd2.setCellValueFactory(new PropertyValueFactory("preis"));
            abd3.setCellValueFactory(new PropertyValueFactory("hoehe"));
            abd4.setCellValueFactory(new PropertyValueFactory("breite"));
            abd5.setCellValueFactory(new PropertyValueFactory("kunstwerk_id"));

            abdrucktable.setItems(null);
            abdrucktable.setItems(abdruckdata);
            


            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }

       
	}
	
	public void showBestellung(ActionEvent event) throws InterruptedException{
 		try {
        	bestellungtable.setEditable(true);

            con = SqliteConnection.Connector();
            stat = con.createStatement();

            bestelldata = FXCollections.observableArrayList();
            prep =con.prepareStatement("SELECT Bestellung.bestellung_id, Bestellung.bestelldatum, Bestellung.auslieferungsdatum, Bestellung.benutzer_id, Bestellung.abdruck_id FROM Bestellung JOIN Abdruck ON Bestellung.abdruck_id=Abdruck.abdruck_id JOIN Kunstwerk ON Abdruck.kunstwerk_id=Kunstwerk.kunstwerk_id JOIN Erstellt ON Kunstwerk.kunstwerk_id=Erstellt.kunstwerk_id JOIN Kuenstler ON Erstellt.benutzer_id=Kuenstler.benutzer_id JOIN Benutzer ON Kuenstler.benutzer_id=Benutzer.benutzer_id WHERE Benutzer.benutzername=?");
            prep.setString(1, userLbl.getText());
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
            	bestelldata.add(new BestellungData(rs.getString("bestellung_id"), rs.getString("bestelldatum"), rs.getString("auslieferungsdatum"), rs.getString("benutzer_id"), rs.getString("abdruck_id")));
            }
            

            best1.setCellValueFactory(new PropertyValueFactory("bestellung_id"));
            best2.setCellValueFactory(new PropertyValueFactory("bestelldatum"));
            best3.setCellValueFactory(new PropertyValueFactory("auslieferungsdatum"));
            best4.setCellValueFactory(new PropertyValueFactory("benutzer_id"));
            best5.setCellValueFactory(new PropertyValueFactory("abdruck_id"));


            bestellungtable.setItems(null);
            bestellungtable.setItems(bestelldata);
            
            
            bestellungtable.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                        if(mouseEvent.getClickCount() == 1){
                            ausdate.setPromptText((String) best3.getCellData(bestellungtable.getSelectionModel().getSelectedIndex()));
                        	bestellid.setText((String) best1.getCellData(bestellungtable.getSelectionModel().getSelectedIndex()));
                        }
                    }
                }
            });
            
                
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fehler bei tabelle einlesen");
        }
       
	}
	
	public void aenderDatum(ActionEvent event){

			 try {
		            prep = con.prepareStatement("update Bestellung set auslieferungsdatum =? WHERE bestellung_id=?");
		            prep.setString(1, ausdate.getEditor().getText());
		            prep.setString(2, bestellid.getText());
		            
		            prep.execute();
		            ausdate.getEditor().setText(null);

		        } catch (SQLException ex) {
		        }
        }
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
			if(loginModel.isDbConnected()){
	            isConnected.setText("Datenbank verbunden");
	        }else{
	            isConnected.setText("Datenbank-Fehler");
	        }


		
   
		
	}
	
	
	
	
	
	public static class KunstwerkData {

        private StringProperty kunstwerk_id;
        private StringProperty entstehungsjahr;
        private StringProperty kunstwerkname;
        private StringProperty kunststilname;


        private KunstwerkData(String kunstwerk_id, String entstehungsjahr, String kunstwerkname, String kunststilname) {
            this.kunstwerk_id = new SimpleStringProperty(kunstwerk_id);
            this.entstehungsjahr = new SimpleStringProperty(entstehungsjahr);
            this.kunstwerkname = new SimpleStringProperty(kunstwerkname);
            this.kunststilname = new SimpleStringProperty(kunststilname);


        }

        public StringProperty kunstwerk_idProperty() {
            return kunstwerk_id;
        }
        public StringProperty entstehungsjahrProperty() {
            return entstehungsjahr;
        }
        public StringProperty kunstwerknameProperty() {
            return kunstwerkname;
        }
        public StringProperty kunststilnameProperty() {
            return kunststilname;
        }


    }
	public static class AbdruckData {

        private StringProperty abdruck_id;
        private StringProperty preis;
        private StringProperty hoehe;
        private StringProperty breite;
        private StringProperty kunstwerk_id;


        private AbdruckData(String abdruck_id, String preis, String hoehe, String breite, String kunstwerk_id) {
            this.abdruck_id = new SimpleStringProperty(abdruck_id);
            this.preis = new SimpleStringProperty(preis);
            this.hoehe = new SimpleStringProperty(hoehe);
            this.breite = new SimpleStringProperty(breite);
            this.kunstwerk_id = new SimpleStringProperty(kunstwerk_id);


        }

        public StringProperty abdruck_idProperty() {
            return abdruck_id;
        }
        public StringProperty preisProperty() {
            return preis;
        }
        public StringProperty hoeheProperty() {
            return hoehe;
        }
        public StringProperty breiteProperty() {
            return breite;
        }
        public StringProperty kunstwerk_idProperty() {
            return kunstwerk_id;
        }
        
    }
	
    public static class BestellungData {

        private StringProperty bestellung_id;
        private StringProperty bestelldatum;
        private StringProperty auslieferungsdatum;
        private StringProperty benutzer_id;
        private StringProperty abdruck_id;


        private BestellungData(String bestellung_id, String bestelldatum, String auslieferungsdatum, String benutzer_id, String abdruck_id) {
            this.bestellung_id = new SimpleStringProperty(bestellung_id);
            this.bestelldatum = new SimpleStringProperty(bestelldatum);
            this.auslieferungsdatum = new SimpleStringProperty(auslieferungsdatum);
            this.benutzer_id = new SimpleStringProperty(benutzer_id);
            this.abdruck_id = new SimpleStringProperty(abdruck_id);


        }

        public StringProperty bestellung_idProperty() {
            return bestellung_id;
        }
        public StringProperty bestelldatumProperty() {
            return bestelldatum;
        }
        public StringProperty auslieferungsdatumProperty() {
            return auslieferungsdatum;
        }
        public StringProperty benutzer_idProperty() {
            return benutzer_id;
        }
        public StringProperty abdruck_idProperty() {
            return abdruck_id;
        }
    }

}
