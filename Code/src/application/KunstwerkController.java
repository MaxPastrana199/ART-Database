package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import application.KuenstlerController.AtelierData;
import application.KuenstlerController.KommentarData;
import application.KuenstlerController.KuenstlerData;
import application.KuenstlerController.KunststilData;
import application.KuenstlerController.KunstwerkData;
import application.KuenstlerController.OeffnungData;
import application.MuseumController.MuseumData;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class KunstwerkController implements Initializable{
	
	public LoginModel loginModel = new LoginModel();
    @FXML
    private Label isConnected;
    
	@FXML
	private Label userLbl;
	
    
	private static Connection con;
	private static Statement stat;
    private PreparedStatement prep;
	private ObservableList<KunstwerkData> data;	
	private ObservableList<KuenstlerData> kuenstlerdata;
	private ObservableList<KommentarData> kommentardata;
	private ObservableList<BewertungData> bewertungdata;
	private ObservableList<AbdruckData> abdruckdata;
	private ObservableList<BestellungData> bestelldata;
	private ObservableList<MuseumData> museumdata;

    
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
    private TableView bestellungtable;
	@FXML
	private TableColumn best1;
	@FXML
	private TableColumn best2;
	@FXML
	private TableColumn best3;
	
    @FXML 
    private TableView museumtable;
	@FXML
	private TableColumn mus1;
	@FXML
	private TableColumn mus2;
	@FXML
	private TableColumn mus3;

	
    @FXML 
    private TableView kuenstlertable;
	@FXML
	private TableColumn kuen1;
	@FXML
	private TableColumn kuen2;
	
    @FXML 
    private TableView kommentartable;
	@FXML
	private TableColumn kom1;
	
    @FXML 
    private TableView bewertungtable;
	@FXML
	private TableColumn bew1;
	
	@FXML
	private TextField suche;
	@FXML
	private TextArea kommentar;
	@FXML
	private Label kunstwerkid;
	@FXML
	private TextField bewertung;
	@FXML
	private TextField abdruck_id;
	@FXML
	private TextField hoehe;
	@FXML
	private TextField breite;
	@FXML
	private TextField preis;
	@FXML
	private TextField filter;
	
	@FXML
	private DatePicker bestdate;
	@FXML
	private DatePicker ausdate;
	
	
	
	public void GetUser(String user) {
		// TODO Auto-generated method stub
		userLbl.setText(user);
		
	}
	
	
	
	public void BackToBenutzerMenue(ActionEvent event) {
		
		try {
			((Node)event.getSource()).getScene().getWindow().hide();
			
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();			
			Pane root = loader.load(getClass().getResource("/application/BenutzerMenue.fxml").openStream());
			BenutzerMenueController menueController = (BenutzerMenueController)loader.getController();
			menueController.GetUser(userLbl.getText());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e){
			// TODO: handle exception
		}		
	}
	
	public void searchKunstwerk(ActionEvent event){
		
   	 	try {
        	table.setEditable(true);

            con = SqliteConnection.Connector();
            stat = con.createStatement();

            data = FXCollections.observableArrayList();
            prep =con.prepareStatement("select * from Kunstwerk where kunstwerkname = ?");
	        prep.setString(1, suche.getText());
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
  
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
   }
	
	public void filterKunststil(ActionEvent event){
		
   	 	try {
        	table.setEditable(true);

            con = SqliteConnection.Connector();
            stat = con.createStatement();

            data = FXCollections.observableArrayList();
            prep =con.prepareStatement("select * from Kunstwerk where kunststilname = ?");
	        prep.setString(1, filter.getText());
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
  
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
   }
	
	public void sucheAbbrechen(ActionEvent event){
        suche.setText(null);
        filter.setText(null);
		initialize(null,null);
	}
	
	
	public void detailsKunstwerk(ActionEvent event){

	   	 try {
	        	kuenstlertable.setEditable(true);

	            con = SqliteConnection.Connector();
	            stat = con.createStatement();

	            kuenstlerdata = FXCollections.observableArrayList();
	            prep =con.prepareStatement("SELECT Benutzer.vorname, Benutzer.name FROM Benutzer INNER JOIN Erstellt ON Benutzer.benutzer_id=Erstellt.benutzer_id INNER JOIN Kunstwerk ON Erstellt.kunstwerk_id=Kunstwerk.kunstwerk_id WHERE Kunstwerk.kunstwerkname=?");
		        prep.setString(1, suche.getText());
		        ResultSet rs = prep.executeQuery();

	            while (rs.next()) {
	            	kuenstlerdata.add(new KuenstlerData(rs.getString("vorname"),rs.getString("name")));
	            }
	            kuen1.setCellValueFactory(new PropertyValueFactory("vorname"));
	            kuen2.setCellValueFactory(new PropertyValueFactory("name"));

	            kuenstlertable.setItems(null);
	            kuenstlertable.setItems(kuenstlerdata);
	  
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("Error on Building Data");
	        }   	 
	   	 
	   	 try {
	        	museumtable.setEditable(true);

	            con = SqliteConnection.Connector();
	            stat = con.createStatement();

	            museumdata = FXCollections.observableArrayList();
	            prep =con.prepareStatement("SELECT Gebaeude.name, Zeitraum_ausgestellt.anfangsdatum, Zeitraum_ausgestellt.enddatum FROM Gebaeude JOIN Zeitraum_ausgestellt ON Gebaeude.gebaeude_id=Zeitraum_ausgestellt.museum_id JOIN Kunstwerk ON Zeitraum_ausgestellt.kunstwerk_id=Kunstwerk.kunstwerk_id WHERE Kunstwerk.kunstwerkname=?");
		        prep.setString(1, suche.getText());
		        ResultSet rs = prep.executeQuery();

	            while (rs.next()) {
	            	museumdata.add(new MuseumData(rs.getString("name"),rs.getString("anfangsdatum"),rs.getString("enddatum")));
	            }
	            mus1.setCellValueFactory(new PropertyValueFactory("name"));
	            mus2.setCellValueFactory(new PropertyValueFactory("anfangsdatum"));
	            mus3.setCellValueFactory(new PropertyValueFactory("enddatum"));

	            museumtable.setItems(null);
	            museumtable.setItems(museumdata);
	  
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("Error on Building Data");
	        }  
	   	 
	    	try {
	        	kommentartable.setEditable(true);

	            con = SqliteConnection.Connector();
	            stat = con.createStatement();

	            kommentardata = FXCollections.observableArrayList();
	            prep =con.prepareStatement("SELECT Kommentar_Kunstwerk.text FROM Kommentar_Kunstwerk INNER JOIN Kunstwerk ON Kommentar_Kunstwerk.kunstwerk_id=Kunstwerk.kunstwerk_id WHERE Kunstwerk.kunstwerkname=?");
	            prep.setString(1, suche.getText());
	            ResultSet rs = prep.executeQuery();

	            while (rs.next()) {
	            	kommentardata.add(new KommentarData(rs.getString("text")));
	            }
	            kom1.setCellValueFactory(new PropertyValueFactory("text"));

	            kommentartable.setItems(null);
	            kommentartable.setItems(kommentardata);
	            
	            
	            kommentartable.setOnMouseClicked(new EventHandler<MouseEvent>() {
	                @Override
	                public void handle(MouseEvent mouseEvent) {
	                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
	                        if(mouseEvent.getClickCount() == 1){

	                            kommentar.setText((String) kom1.getCellData(kommentartable.getSelectionModel().getSelectedIndex()));                            
	                        }
	                    }
	                }
	            }); 

	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("Error on Building Data");
	        }
	    	
	    	try {
	        	bewertungtable.setEditable(true);

	            con = SqliteConnection.Connector();
	            stat = con.createStatement();

	            bewertungdata = FXCollections.observableArrayList();
	            prep =con.prepareStatement("SELECT Bewertung.punkte FROM Bewertung INNER JOIN Kunstwerk ON Bewertung.kunstwerk_id=Kunstwerk.kunstwerk_id WHERE Kunstwerk.kunstwerkname=?");
	            prep.setString(1, suche.getText());
	            ResultSet rs = prep.executeQuery();

	            while (rs.next()) {
	            	bewertungdata.add(new BewertungData(rs.getString("punkte")));
	            }
	            bew1.setCellValueFactory(new PropertyValueFactory("punkte"));

	            bewertungtable.setItems(null);
	            bewertungtable.setItems(bewertungdata);
	            
	            
	            bewertungtable.setOnMouseClicked(new EventHandler<MouseEvent>() {
	                @Override
	                public void handle(MouseEvent mouseEvent) {
	                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
	                        if(mouseEvent.getClickCount() == 1){

	                        	bewertung.setText((String) bew1.getCellData(bewertungtable.getSelectionModel().getSelectedIndex()));                            
	                        }
	                    }
	                }
	            }); 

	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("Error on Building Data");
	        }
	    	
	    	try {
	        	abdrucktable.setEditable(true);

	            con = SqliteConnection.Connector();
	            stat = con.createStatement();

	            abdruckdata = FXCollections.observableArrayList();
	            prep =con.prepareStatement("SELECT Abdruck.abdruck_id, Abdruck.preis, Abdruck.hoehe,Abdruck.breite FROM Abdruck INNER JOIN Kunstwerk ON  Abdruck.kunstwerk_id=Kunstwerk.kunstwerk_id WHERE Kunstwerk.kunstwerkname=?");
	            prep.setString(1, suche.getText());
	            ResultSet rs = prep.executeQuery();

	            while (rs.next()) {
	            	abdruckdata.add(new AbdruckData(rs.getString("abdruck_id"),rs.getString("preis"),rs.getString("hoehe"),rs.getString("breite")));
	            }
	            abd1.setCellValueFactory(new PropertyValueFactory("abdruck_id"));
	            abd2.setCellValueFactory(new PropertyValueFactory("preis"));
	            abd3.setCellValueFactory(new PropertyValueFactory("hoehe"));
	            abd4.setCellValueFactory(new PropertyValueFactory("breite"));

	            abdrucktable.setItems(null);
	            abdrucktable.setItems(abdruckdata);
	            
	            
	            abdrucktable.setOnMouseClicked(new EventHandler<MouseEvent>() {
	                @Override
	                public void handle(MouseEvent mouseEvent) {
	                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
	                        if(mouseEvent.getClickCount() == 1){

	                        	abdruck_id.setText((String) abd1.getCellData(abdrucktable.getSelectionModel().getSelectedIndex()));                            
	                        	preis.setText((String) abd2.getCellData(abdrucktable.getSelectionModel().getSelectedIndex()));                            
	                        	hoehe.setText((String) abd3.getCellData(abdrucktable.getSelectionModel().getSelectedIndex()));                            
	                        	breite.setText((String) abd4.getCellData(abdrucktable.getSelectionModel().getSelectedIndex()));                            
	                        }
	                    }
	                }
	            }); 

	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("Error on Building Data");
	        }
	    	
	    
	   	 
	   }
	
	
	public void kommentarSpeichern(ActionEvent event) throws InterruptedException{

			try {

					//nullValidationLabel2.setText("");
		            prep = con.prepareStatement("insert into Kommentar_Kunstwerk(text,kunstwerk_id,benutzer_id) values(?,?,(SELECT benutzer_id FROM Benutzer WHERE benutzername=?));");
		            prep.setString(1, kommentar.getText());
		            prep.setString(2, kunstwerkid.getText());
		            prep.setString(3, userLbl.getText());
		            prep.execute();
		            kommentar.setText(null);

		        } catch (SQLException ex) {
		        }
       
	}
	
	public void bewertungSpeichern(ActionEvent event) throws InterruptedException{

			try {

		            prep = con.prepareStatement("insert into Bewertung(punkte,benutzer_id,kunstwerk_id) values(?,(SELECT benutzer_id FROM Benutzer WHERE benutzername=?),?);");
		            prep.setString(1, bewertung.getText());
		            prep.setString(2, userLbl.getText());
		            prep.setString(3, kunstwerkid.getText());
		            prep.execute();

		        } catch (SQLException ex) {
		        }
       
	}
	
	
	public void bestellungSpeichern(ActionEvent event) throws InterruptedException{

			try {

		            prep = con.prepareStatement("insert into Bestellung(bestelldatum,auslieferungsdatum,benutzer_id,abdruck_id) values(?,?,(SELECT benutzer_id FROM Benutzer WHERE benutzername=?),?);");
		            prep.setString(1, bestdate.getEditor().getText());
		            prep.setString(2, ausdate.getEditor().getText());
		            prep.setString(3, userLbl.getText());
		            prep.setString(4, abdruck_id.getText());
		            prep.execute();
		            bewertung.setText(null);

		        } catch (SQLException ex) {

		        }
       
	}
	
	
	public void showBestellung(ActionEvent event) throws InterruptedException{
 		try {
        	bestellungtable.setEditable(true);

            con = SqliteConnection.Connector();
            stat = con.createStatement();

            bestelldata = FXCollections.observableArrayList();
            prep =con.prepareStatement("select Bestellung.bestellung_id, Bestellung.bestelldatum, Bestellung.auslieferungsdatum FROM Bestellung INNER JOIN Benutzer ON Bestellung.benutzer_id=Benutzer.benutzer_id WHERE benutzername=?");
            prep.setString(1, userLbl.getText());
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
            	bestelldata.add(new BestellungData(rs.getString("bestellung_id"), rs.getString("bestelldatum"), rs.getString("auslieferungsdatum")));
            }
            

            best1.setCellValueFactory(new PropertyValueFactory("bestellung_id"));
            best2.setCellValueFactory(new PropertyValueFactory("bestelldatum"));
            best3.setCellValueFactory(new PropertyValueFactory("auslieferungsdatum"));


            bestellungtable.setItems(null);
            bestellungtable.setItems(bestelldata);
            
                
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fehler bei tabelle einlesen");
        }
       
	}
	


	@Override
	public void initialize(URL location, ResourceBundle resources) {


		try {
        	table.setEditable(true);

            con = SqliteConnection.Connector();
            stat = con.createStatement();

            data = FXCollections.observableArrayList();
            ResultSet rs = con.createStatement().executeQuery("select * from kunstwerk");
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
                            suche.setText((String) column3.getCellData(table.getSelectionModel().getSelectedIndex()));
                            kunstwerkid.setText((String) column1.getCellData(table.getSelectionModel().getSelectedIndex()));
                        }
                    }
                }
            });
                
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fehler bei tabelle einlesen");
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


        private AbdruckData(String abdruck_id, String preis, String hoehe, String breite) {
            this.abdruck_id = new SimpleStringProperty(abdruck_id);
            this.preis = new SimpleStringProperty(preis);
            this.hoehe = new SimpleStringProperty(hoehe);
            this.breite = new SimpleStringProperty(breite);


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
        
    }
	
    public static class BestellungData {

        private StringProperty bestellung_id;
        private StringProperty bestelldatum;
        private StringProperty auslieferungsdatum;


        private BestellungData(String bestellung_id, String bestelldatum, String auslieferungsdatum) {
            this.bestellung_id = new SimpleStringProperty(bestellung_id);
            this.bestelldatum = new SimpleStringProperty(bestelldatum);
            this.auslieferungsdatum = new SimpleStringProperty(auslieferungsdatum);


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
    }
    
    public static class MuseumData {

        private StringProperty name;
        private StringProperty anfangsdatum;
        private StringProperty enddatum;


        private MuseumData(String name, String anfangsdatum, String enddatum) {
            this.name = new SimpleStringProperty(name);
            this.anfangsdatum = new SimpleStringProperty(anfangsdatum);
            this.enddatum = new SimpleStringProperty(enddatum);


        }

        public StringProperty nameProperty() {
            return name;
        }
        public StringProperty anfangsdatumProperty() {
            return anfangsdatum;
        }
        public StringProperty enddatumProperty() {
            return enddatum;
        }
    }
    
	public static class KuenstlerData {
        private StringProperty vorname;
        private StringProperty name;

        private KuenstlerData(String vorname, String name) {
            this.vorname = new SimpleStringProperty(vorname);
            this.name = new SimpleStringProperty(name);
        }
        
        public StringProperty vornameProperty() {
            return vorname;
        }
        public StringProperty nameProperty() {
            return name;
        }
    }
	
	public static class KommentarData {
        private StringProperty text;

        private KommentarData(String text) {
            this.text = new SimpleStringProperty(text);
        }
       
        public StringProperty textProperty() {
            return text;
        }
    }
	
	public static class BewertungData {
        private StringProperty punkte;

        private BewertungData(String punkte) {
            this.punkte = new SimpleStringProperty(punkte);
        }
       
        public StringProperty punkteProperty() {
            return punkte;
        }
    }
	


}
