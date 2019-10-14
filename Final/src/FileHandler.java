import java.io.*;
import java.util.ArrayList;

public class FileHandler{
	
	private int currentKey;
	private int keyCount = 0;
	private ArrayList<Entry> reList;
	
	public FileHandler() {
		currentKey = getKey();
	}
	
	//Iterates through CSV file, checking primary key values until 
	//reaching the final value, then incrementing by 1 to get the next value.
	public void setCurrentKey() throws IOException {
		try {
			BufferedReader inputFile = new BufferedReader(new FileReader("worklog.csv"));
			String input;
			while((input = inputFile.readLine())!= null) {
				//System.out.println(input);
				//System.out.println(input.split(","));
				keyCount = Integer.parseInt(input.split(",")[0]);
			}
			currentKey = keyCount + 1;
			inputFile.close();
		}
		catch(IOException e) {
			System.out.println("IO exception in setCurrentKey");
		}
	}
	
	public int getKey() {
		try {
		setCurrentKey();
		}
		catch(IOException e) {
			System.out.println("IO Exception in getKey!");
		}
		return currentKey;
	}
	
	//Append new Entry data to CSV from GUI. either at end of file, or in place of existing entry
	public void toFile(Entry submit) throws IOException{
		if(submit.getKey() >= getKey()) {
			try {
			BufferedWriter out = new BufferedWriter(new FileWriter("worklog.csv", true)); 
	        out.write(submit.toString() + "\n"); 
	        out.close(); 
			}
			catch(IOException e){
				System.out.println("An IO error has occurred in toFile");
			}
		}
		else {
			reList = new ArrayList<>(getSearchList());
			reList.set(submit.getKey(), submit);
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter("worklog.csv", false)); 
				for(Entry reWrite:reList) {
					out.write(reWrite.toString() + "\n"); 
				}
				out.close(); 
			}
			catch(IOException e){
				System.out.println("An IO error has occurred in toFile");
				System.out.println(e.toString());
			}
		System.out.println("Committed Entry: " + submit.toString());
		}
	}
	
	//Fill an ArrayList with Entry objects populated from the CSV file
	public ArrayList<Entry> getSearchList() throws IOException {
		ArrayList<Entry> listReturn = new ArrayList<>();
		try {
			BufferedReader in = new BufferedReader(new FileReader("worklog.csv"));
			String input;
			while((input = in.readLine())!= null) {
				String[] inBuff = input.split(",");
				listReturn.add(new Entry(Integer.parseInt(inBuff[0]), inBuff[1], inBuff[2], inBuff[3],
						inBuff[4], inBuff[5], inBuff[6], inBuff[7], inBuff[8], inBuff[9]));
			}
			in.close();
		}
		catch(IOException e) {
			System.out.println("An IO error has occurred in getSearchList");
		}
		return listReturn;
	}
}
