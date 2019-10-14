/**
* Assignment: Final Project
* Author: Anthony Shephard
* Project Purpose: use JavaFX and file IO to create an application for logging PC repair jobs in a .csv file.
* Input:  serial number, model, service type, status, part, event, notes
* Desired Output:  csv file, or searched for content
* Variables and Classes:  WorkLog, SearchFrame, FileHandler, Entry
* Formulas:  
* Description of the main algorithm: 
* December 9, 2018
**/
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import java.io.*;
import java.time.LocalDate;

public class WorkLog extends Application{
	
	//Fields
	static public Label primaryKey, currentDate, labDate, labKey, labSerial, labModel, labService,
		labStatus, labLocation, labPart, labEvent, labSearch;
	static public TextField serial, model, partNum, eventNum;
	static public RadioButton part, labor, depot, mic, vec;
	static public ComboBox<String> status, searchBy;
	static public TextArea notes;
	static public Button clear, submit, search;
	static public ToggleGroup serviceGroup = new ToggleGroup();
	static public ToggleGroup location = new ToggleGroup();
	private FileHandler myFile = new FileHandler();
	static public Stage searchStage = new Stage();

	public static void main(String[] args) throws IOException{
		launch(args);
	}
	
	public void start(Stage primaryStage) {
		
		primaryStage.setTitle("Work Log");
		
		labDate = new Label("Date:");
		currentDate = new Label(LocalDate.now().toString());
		labKey = new Label("Work Order:");
		primaryKey = new Label(Integer.toString(myFile.getKey()));
		labSerial = new Label("Serial:");
		serial = new TextField();
		labModel = new Label("Model:");
		model = new TextField();
		
		HBox box1 = new HBox(10, labDate, currentDate, labKey, primaryKey, labSerial, serial, labModel, model);
		box1.setAlignment(Pos.CENTER);
		
		labStatus = new Label("Status:");
		status = new ComboBox<>();
		status.getItems().addAll("Complete", "Parts Ordered", "In Progress", "Monitored Return");
		status.setValue("In Progress");
		labService = new Label("Service Type:");
		part = new RadioButton("Part Replacement");
		labor = new RadioButton("Labor Only");
		depot = new RadioButton("Repair Depot");
		part.setToggleGroup(serviceGroup);
		labor.setToggleGroup(serviceGroup);
		depot.setToggleGroup(serviceGroup);
		labor.setSelected(true);
		
		HBox box2 = new HBox(10, labStatus, status, labService, part, labor, depot);
		box2.setAlignment(Pos.CENTER);
		
		labLocation = new Label("Location:");
		mic = new RadioButton("MIC");
		vec = new RadioButton("VEC");
		mic.setToggleGroup(location);
		vec.setToggleGroup(location);
		vec.setSelected(true);
		
		VBox box3 = new VBox(10, mic, vec);
		box3.setAlignment(Pos.CENTER);
		
		HBox box10 = new HBox(10, labLocation, box3);
		box10.setAlignment(Pos.CENTER);
		
		labPart = new Label("Part:");
		partNum = new TextField();
		labEvent = new Label("Event:");
		eventNum = new TextField();
		
		HBox box8 = new HBox(10, labPart, partNum);
		box8.setAlignment(Pos.CENTER);
		
		HBox box9 = new HBox(10, labEvent, eventNum);
		box9.setAlignment(Pos.CENTER);
		
		VBox box4 = new VBox(10, box8, box9);
		box3.setAlignment(Pos.CENTER);
		
		HBox box5 = new HBox(40, box10, box4);
		box5.setAlignment(Pos.CENTER);
		
		clear = new Button("Clear");
		submit = new Button("Submit");
		search = new Button("Search");
		
		labSearch = new Label("Search By:");
		searchBy = new ComboBox<>();
		searchBy.getItems().addAll("Serial", "Model", "Service Type", "Status", "Location", "Part", "Event");
		searchBy.setValue("Serial");
		
		HBox box6 = new HBox(10, clear, labSearch, searchBy, search, submit);
		box6.setAlignment(Pos.CENTER_RIGHT);
		
		notes = new TextArea("Notes");
		notes.setWrapText(true);
		notes.setPrefColumnCount(30);
		notes.setPrefRowCount(3);
		
		VBox box7 = new VBox(10, box1, box2, box5, notes, box6);
		box7.setAlignment(Pos.CENTER);
		box7.setPadding(new Insets(30));
		
		submit.setOnAction(new submitHandler());
		search.setOnAction(new searchHandler());
		
		Scene scene = new Scene(box7);
		
		primaryStage.setScene(scene);
		
		primaryStage.show();

	}
	
	//event handler for submit button
	class submitHandler implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent event) {
			//create Entry object and store field data in it
			Entry newSub = new Entry(Integer.parseInt(primaryKey.getText()), currentDate.getText(), serial.getText(), model.getText(),
					(String) ((RadioButton) serviceGroup.getSelectedToggle()).getText(), status.getValue(),
					(String) ((RadioButton) location.getSelectedToggle()).getText(), partNum.getText(), eventNum.getText(), notes.getText());
			//Write Entry data to CSV file with FileHandler object
			try {
				myFile.toFile(newSub);
			}
			catch(IOException e) {
				System.out.println("IO Excetpion has occured!");
			}
			//Get next PrimaryKey value from CSV file and apply it to primary key field in GUI
			primaryKey.setText(Integer.toString(myFile.getKey()));
		}
	}
	
	//event handler for search button
	class searchHandler implements EventHandler<ActionEvent>{
		
		@Override
		public void handle(ActionEvent event) {
			if(searchStage.isShowing()) {
				searchStage.close();
			}
			
			int searchByIndex = searchBy.getSelectionModel().getSelectedIndex() + 2;
			
			Entry newSub = new Entry(Integer.parseInt(primaryKey.getText()), currentDate.getText(), serial.getText(), model.getText(),
					(String) ((RadioButton) serviceGroup.getSelectedToggle()).getText(), status.getValue(),
					(String) ((RadioButton) location.getSelectedToggle()).getText(), partNum.getText(), eventNum.getText(), notes.getText());
			
			String searchString = newSub.toString().split(",")[searchByIndex];
			
			SearchFrame sFrame = new SearchFrame(searchString, searchByIndex);
			sFrame.start(searchStage);
			
		}
	}

}
