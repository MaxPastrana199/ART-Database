package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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



public class KuenstlerController implements Initializable{
	
	public LoginModel loginModel = new LoginModel();
    @FXML
    private Label isConnected;
    
	@FXML
	private Label userLbl;
	
	private static Connection con;
	private static Statement stat;
    private PreparedStatement prep;
	private ObservableList<KuenstlerData> data;
	private ObservableList<AtelierData> atelierdata;
	private ObservableList<KunstwerkData> kunstwerkdata;
	private ObservableList<OeffnungData> oeffnungdata;
	private ObservableList<KunststilData> kunststildata;
	private ObservableList<KommentarData> kommentardata;
	private ObservableList<SammlungData> sammlungdata;
	
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
	private TableColumn column5;
	
	@FXML
	private TableView ateliertable;
	@FXML
	private TableColumn spalte1;
	
	@FXML
	private TextField suche;
	@FXML
	private Label kuenstlerid;
	
	@FXML
	private TableView kunstwerktable;
	@FXML
	private TableColumn col1;
	
	@FXML
	private TableView oeffnungtable;
	@FXML
	private TableColumn oe1;
	@FXML
	private TableColumn oe2;
	@FXML
	private TableColumn oe3;
	
	@FXML
	private TableView sammlungtable;
	@FXML
	private TableColumn sam1;
	@FXML
	private TableColumn sam2;
	@FXML
	private TableColumn sam3;
	
	@FXML
	private TableView kunststiltable;
	@FXML
	private TableColumn ku1;
	
	@FXML
	private TableView kommentartable;
	@FXML
	private TableColumn kom1;
	@FXML
	private TableColumn kom2;
	
	@FXML
	private TextArea kommentar;
	
	
	
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
	
	public void sucheAbbrechen(ActionEvent event){
        suche.setText(null);
		initialize(null,null);
		detailsKuenstler(null);
	}
	



	public void searchKuenstler(ActionEvent event){

        	 try {
             	table.setEditable(true);

                 con = SqliteConnection.Connector();
                 stat = con.createStatement();

                 data = FXCollections.observableArrayList();
                 prep =con.prepareStatement("select * from Benutzer JOIN Kuenstler ON Benutzer.benutzer_id = Kuenstler.benutzer_id where Benutzer.name = ?");
		         prep.setString(1, suche.getText());
		         ResultSet rs = prep.executeQuery();

                 while (rs.next()) {
                     data.add(new KuenstlerData(rs.getString("benutzer_id"),rs.getString("vorname"), rs.getString("name"), rs.getString("land"), rs.getString("stadt")));
                 }
                 column1.setCellValueFactory(new PropertyValueFactory("benutzer_id"));
                 column2.setCellValueFactory(new PropertyValueFactory("vorname"));
                 column3.setCellValueFactory(new PropertyValueFactory("name"));
                 column4.setCellValueFactory(new PropertyValueFactory("land"));
                 column5.setCellValueFactory(new PropertyValueFactory("stadt"));
                 table.setItems(null);
                 table.setItems(data);
       
                 
             } catch (Exception e) {
                 e.printStackTrace();
                 System.out.println("Error on Building Data");
             }
        }
	
	public void detailsKuenstler(ActionEvent event){

   	 try {
        	ateliertable.setEditable(true);

            con = SqliteConnection.Connector();
            stat = con.createStatement();

            atelierdata = FXCollections.observableArrayList();
            prep =con.prepareStatement("SELECT Gebaeude.name FROM Gebaeude INNER JOIN Atelier ON Gebaeude.gebaeude_id=Atelier.atelier_id INNER JOIN Gehoert ON Atelier.atelier_id=Gehoert.atelier_id INNER JOIN Kuenstler ON Gehoert.kuenstler_id=Kuenstler.benutzer_id INNER JOIN Benutzer ON Kuenstler.benutzer_id=Benutzer.benutzer_id WHERE Benutzer.name=?");
	        prep.setString(1, suche.getText());
	        ResultSet rs = prep.executeQuery();

            while (rs.next()) {
                atelierdata.add(new AtelierData(rs.getString("name")));
            }
            spalte1.setCellValueFactory(new PropertyValueFactory("name"));

            ateliertable.setItems(null);
            ateliertable.setItems(atelierdata);
  
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
   	try {
    	kunstwerktable.setEditable(true);

        con = SqliteConnection.Connector();
        stat = con.createStatement();

        kunstwerkdata = FXCollections.observableArrayList();
        prep =con.prepareStatement("SELECT Kunstwerk.kunstwerkname FROM Kunstwerk INNER JOIN Erstellt ON Kunstwerk.kunstwerk_id=Erstellt.kunstwerk_id INNER JOIN Kuenstler ON Erstellt.benutzer_id=Kuenstler.benutzer_id Inner JOIN Benutzer ON Kuenstler.benutzer_id=Benutzer.benutzer_id WHERE Benutzer.name= ?");
        prep.setString(1, suche.getText());
        ResultSet rs = prep.executeQuery();

        while (rs.next()) {
            kunstwerkdata.add(new KunstwerkData(rs.getString("kunstwerkname")));
        }
        col1.setCellValueFactory(new PropertyValueFactory("kunstwerkname"));

        kunstwerktable.setItems(null);
        kunstwerktable.setItems(kunstwerkdata);

        
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error on Building Data");
    }
   	try {
    	oeffnungtable.setEditable(true);

        con = SqliteConnection.Connector();
        stat = con.createStatement();

        oeffnungdata = FXCollections.observableArrayList();
        prep =con.prepareStatement("SELECT Oeffnungszeit.von, Oeffnungszeit.bis, Oeffnungszeit.wochentag FROM Oeffnungszeit INNER JOIN Hat_Atelier ON Oeffnungszeit.oeffnungszeit_id=Hat_Atelier.oeffnungszeit_id INNER JOIN Atelier ON Hat_Atelier.atelier_id=Atelier.atelier_id INNER JOIN Gehoert ON Atelier.atelier_id=Gehoert.atelier_id INNER JOIN Kuenstler ON Gehoert.kuenstler_id=Kuenstler.benutzer_id INNER JOIN Benutzer ON Kuenstler.benutzer_id=Benutzer.benutzer_id WHERE Benutzer.name=?");
        prep.setString(1, suche.getText());
        ResultSet rs = prep.executeQuery();

        while (rs.next()) {
        	oeffnungdata.add(new OeffnungData(rs.getString("von"),rs.getString("bis"),rs.getString("wochentag")));
        }
        oe1.setCellValueFactory(new PropertyValueFactory("von"));
        oe2.setCellValueFactory(new PropertyValueFactory("bis"));
        oe3.setCellValueFactory(new PropertyValueFactory("wochentag"));

        oeffnungtable.setItems(null);
        oeffnungtable.setItems(oeffnungdata);

        
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error on Building Data");
    }
   	
   	try {
    	sammlungtable.setEditable(true);

        con = SqliteConnection.Connector();
        stat = con.createStatement();

        sammlungdata = FXCollections.observableArrayList();
        prep =con.prepareStatement("SELECT Sammlung.name, Zeitraum_Sammlung.anfangsdatum, Zeitraum_Sammlung.anfangsdatum FROM Sammlung JOIN Zeitraum_Sammlung  ON Sammlung.sammlung_id=Zeitraum_Sammlung.sammlung_id JOIN Gebaeude ON Zeitraum_Sammlung.gebaeude_id=Gebaeude.gebaeude_id JOIN Atelier ON Gebaeude.gebaeude_id=Atelier.atelier_id JOIN Gehoert ON Atelier.atelier_id=Gehoert.atelier_id JOIN Benutzer ON Gehoert.kuenstler_id=Benutzer.benutzer_id WHERE Benutzer.name=?");
        prep.setString(1, suche.getText());
        ResultSet rs = prep.executeQuery();

        while (rs.next()) {
        	sammlungdata.add(new SammlungData(rs.getString("name"),rs.getString("anfangsdatum"),rs.getString("enddatum")));
        }
        sam1.setCellValueFactory(new PropertyValueFactory("name"));
        sam2.setCellValueFactory(new PropertyValueFactory("name"));
        sam3.setCellValueFactory(new PropertyValueFactory("enddatum"));

        sammlungtable.setItems(null);
        sammlungtable.setItems(sammlungdata);

        
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error on Building Data");
    }
   	
   	try {
    	kunststiltable.setEditable(true);

        con = SqliteConnection.Connector();
        stat = con.createStatement();

        kunststildata = FXCollections.observableArrayList();
        prep =con.prepareStatement("SELECT Bevorzugt.kunststilname FROM Bevorzugt INNER JOIN Benutzer ON Bevorzugt.benutzer_id=Benutzer.benutzer_id WHERE Benutzer.name=?");
        prep.setString(1, suche.getText());
        ResultSet rs = prep.executeQuery();

        while (rs.next()) {
        	kunststildata.add(new KunststilData(rs.getString("kunststilname")));
        }
        ku1.setCellValueFactory(new PropertyValueFactory("kunststilname"));

        kunststiltable.setItems(null);
        kunststiltable.setItems(kunststildata);

        
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error on Building Data");
    }
   	try {
    	kommentartable.setEditable(true);

        con = SqliteConnection.Connector();
        stat = con.createStatement();

        kommentardata = FXCollections.observableArrayList();
        prep =con.prepareStatement("SELECT Kommentar_Kuenstler.kuenstler_benutzer_id, Kommentar_Kuenstler.text FROM Kommentar_Kuenstler INNER JOIN Benutzer ON Kommentar_Kuenstler.kuenstler_benutzer_id=Benutzer.benutzer_id WHERE Benutzer.name=?");
        prep.setString(1, suche.getText());
        ResultSet rs = prep.executeQuery();

        while (rs.next()) {
        	kommentardata.add(new KommentarData(rs.getString("kuenstler_benutzer_id"),rs.getString("text")));
        }
        kom1.setCellValueFactory(new PropertyValueFactory("kuenstler_benutzer_id"));
        kom2.setCellValueFactory(new PropertyValueFactory("text"));

        kommentartable.setItems(null);
        kommentartable.setItems(kommentardata);
        
        kommentartable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 1){

                        kommentar.setText((String) kom2.getCellData(kommentartable.getSelectionModel().getSelectedIndex()));                            
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

		            prep = con.prepareStatement("insert into Kommentar_Kuenstler(text,kuenstler_benutzer_id,benutzer_id) values(?,?,(SELECT benutzer_id FROM Benutzer WHERE benutzername=?));");
		            prep.setString(1, kommentar.getText());
		            prep.setString(2, kuenstlerid.getText());
		            prep.setString(3, userLbl.getText());
		            prep.execute();
		            kommentar.setText(null);

		        } catch (SQLException ex) {
		        }
       
	}
	
		
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		

        
        try {
        	table.setEditable(true);

            con = SqliteConnection.Connector();
            stat = con.createStatement();

            data = FXCollections.observableArrayList();
            ResultSet rs = con.createStatement().executeQuery("select * from benutzer JOIN Kuenstler ON Benutzer.benutzer_id=Kuenstler.benutzer_id");
            while (rs.next()) {
                data.add(new KuenstlerData(rs.getString("benutzer_id"), rs.getString("vorname"), rs.getString("name"), rs.getString("land"), rs.getString("stadt")));
            }
            column1.setCellValueFactory(new PropertyValueFactory("benutzer_id"));
            column2.setCellValueFactory(new PropertyValueFactory("vorname"));
            column3.setCellValueFactory(new PropertyValueFactory("name"));
            column4.setCellValueFactory(new PropertyValueFactory("land"));
            column5.setCellValueFactory(new PropertyValueFactory("stadt"));

            table.setItems(null);
            table.setItems(data);
            
            table.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                        if(mouseEvent.getClickCount() == 1){
                            suche.setText((String) column3.getCellData(table.getSelectionModel().getSelectedIndex()));
                            kuenstlerid.setText((String) column1.getCellData(table.getSelectionModel().getSelectedIndex()));
                        }
                    }
                }
            });
                
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fehler bei tabelle einlesen");
        }
	}
	
	
	
	
	
	// ObservableList Daten
	
	public static class KuenstlerData {

        private StringProperty benutzer_id;
		private StringProperty vorname;
        private StringProperty name;
        private StringProperty land;
        private StringProperty stadt;


        private KuenstlerData(String benutzer_id, String vorname, String name, String land, String stadt) {
            this.benutzer_id = new SimpleStringProperty(benutzer_id);
            this.vorname = new SimpleStringProperty(vorname);
            this.name = new SimpleStringProperty(name);
            this.land = new SimpleStringProperty(land);
            this.stadt = new SimpleStringProperty(stadt);
        }

        public StringProperty benutzer_idProperty() {
            return benutzer_id;
        }
        public StringProperty vornameProperty() {
            return vorname;
        }
        public StringProperty nameProperty() {
            return name;
        }
        public StringProperty landProperty() {
            return land;
        }
        public StringProperty stadtProperty() {
            return stadt;
        }

    }
	
	public static class AtelierData {
        private StringProperty name;

        private AtelierData(String name) {
            this.name = new SimpleStringProperty(name);
        }
        
        public StringProperty nameProperty() {
            return name;
        }
    }
	
	
	public static class KunstwerkData {
        private StringProperty kunstwerkname;

        private KunstwerkData(String kunstwerkname) {
            this.kunstwerkname = new SimpleStringProperty(kunstwerkname);
        }
        
        public StringProperty kunstwerknameProperty() {
            return kunstwerkname;
        }
    }
	
	public static class KunststilData {
        private StringProperty kunststilname;

        private KunststilData(String kunststilname) {
            this.kunststilname = new SimpleStringProperty(kunststilname);
        }
        
        public StringProperty kunststilnameProperty() {
            return kunststilname;
        }
    }
	public static class KommentarData {
        private StringProperty kuenstler_benutzer_id;
        private StringProperty text;

        private KommentarData(String kuenstler_benutzer_id,String text) {
            this.kuenstler_benutzer_id = new SimpleStringProperty(kuenstler_benutzer_id);
            this.text = new SimpleStringProperty(text);
        }
        
        public StringProperty kuenstler_benutzer_idProperty() {
            return kuenstler_benutzer_id;
        }
        public StringProperty textProperty() {
            return text;
        }
    }
	
	
	public static class OeffnungData {
        private StringProperty von;
        private StringProperty bis;
        private StringProperty wochentag;

        private OeffnungData(String von,String bis,String wochentag) {
            this.von = new SimpleStringProperty(von);
            this.bis = new SimpleStringProperty(bis);
            this.wochentag = new SimpleStringProperty(wochentag);
        }
        
        public StringProperty vonProperty() {
            return von;
        }
        public StringProperty bisProperty() {
            return bis;
        }
        public StringProperty wochentagProperty() {
            return wochentag;
        }
    }
	
	public static class SammlungData {
        private StringProperty name;
        private StringProperty anfangsdatum;
        private StringProperty enddatum;

        private SammlungData(String name,String anfangsdatum,String enddatum) {
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
	

	

}
