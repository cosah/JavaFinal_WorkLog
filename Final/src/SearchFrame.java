import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import java.io.*;
import java.util.ArrayList;
import javafx.collections.ObservableList;

public class SearchFrame extends Application {
	
	private ListView<String> searchSelect;
	private String searchString;
	private int searchIndex;
	private FileHandler myfile = new FileHandler();
	ArrayList<Entry> searchList;
	ArrayList<String> refinedList = new ArrayList<>();
	
	//constructor references superclass and takes a string and an index to store for later use.
	public SearchFrame(String cSearchString, int cSearchIndex) {
		super();
		searchString = cSearchString;
		searchIndex = cSearchIndex;
	}
	
	public void start(Stage searchStage) {
		
		searchStage.setTitle("Search Selection");
		//get list of all Entries
		try {
			searchList = myfile.getSearchList();
		}
		catch(IOException e) {
			System.out.println("IOExction in SearchFrame.start");
		}
		//search list of all Entries for matching content in the specified field
		for(Entry i:searchList) {
			if(i.toString().split(",")[searchIndex].equals(searchString)) {
				refinedList.add(i.toString());
			}
		}
		//fill ListView with refined search results.
		try {
			ObservableList<String> strList = FXCollections.observableArrayList(refinedList);
			searchSelect = new ListView<>(strList);
		}
		catch(NullPointerException e) {
			searchSelect = new ListView<>();
		}
		
		//Fill WorkLog fields with Entry selected from search.
		searchSelect.getSelectionModel().selectedItemProperty().addListener(event -> {
			String[] selected = searchSelect.getSelectionModel().getSelectedItem().split(",");
			WorkLog.primaryKey.setText(selected[0]);
			WorkLog.currentDate.setText(selected[1]);
			WorkLog.serial.setText(selected[2]);
			WorkLog.model.setText(selected[3]);
			if(selected[4].equals("Part Replacement")){
				WorkLog.part.setSelected(true);
			}
			if(selected[4].equals("Labor Only")) {
				WorkLog.labor.setSelected(true);
			}
			if(selected[4].equals("Repair Depot")) {
				WorkLog.depot.setSelected(true);
			}
			WorkLog.status.getSelectionModel().select(selected[5]);
			if(selected[6].equals("MIC")) {
				WorkLog.mic.setSelected(true);
			}
			if(selected[6].equals("VEC")) {
				WorkLog.vec.setSelected(true);
			}
			WorkLog.partNum.setText(selected[7]);
			WorkLog.eventNum.setText(selected[8]);
			WorkLog.notes.setText(selected[9]);
		});
		
		Scene scene = new Scene(searchSelect);
		
		searchStage.setScene(scene);
		
		searchStage.show();

	}
	
}
