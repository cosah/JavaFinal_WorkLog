
public class Entry {
	
	private int primaryKey;
	private String date, serial, model, service, status, location,
		part, event, notes;
	
	public Entry() {
		primaryKey = 0;
		date = "";
		serial = "";
		model = "";
		service = "";
		status = "";
		location = "";
		part = "";
		event = "";
		notes = "";
	}
	
	public Entry(int key, String cDate, String cSerial, String cModel,
			String cService, String cStatus, String cLocation, String cPart,
			String cEvent, String cNotes) {
		primaryKey = key;
		date = cDate;
		serial = cSerial;
		model = cModel;
		service = cService;
		status = cStatus;
		location = cLocation;
		part = cPart;
		event = cEvent;
		notes = cNotes;
	}
	
	public Entry(Entry copyEntry) {
		primaryKey = copyEntry.getKey();
		date = copyEntry.getDate();
		serial = copyEntry.getSerial();
		model = copyEntry.getModel();
		service = copyEntry.getService();
		status = copyEntry.getStatus();
		location = copyEntry.getLocation();
		part = copyEntry.getPart();
		event = copyEntry.getEvent();
		notes = copyEntry.getNotes();
	}
	
	public void setKey(int nKey) {
		primaryKey = nKey;
	}
	public void setDate(String nDate) {
		date = nDate;
	}
	public void setSerial(String nSerial) {
		serial = nSerial;
	}
	public void setModel(String nModel) {
		model = nModel;
	}
	public void setService(String nService) {
		service = nService;
	}
	public void setStatus(String nStatus) {
		status = nStatus;
	}
	public void setLocation(String nLocation) {
		location = nLocation;
	}
	public void setPart(String nPart) {
		part = nPart;
	}
	public void setEvent(String nEvent) {
		event = nEvent;
	}
	public void setNotes(String nNotes) {
		notes = nNotes;
	}
	
	public int getKey() {
		return primaryKey;
	}
	public String getDate() {
		return date;
	}
	public String getSerial() {
		return serial;
	}
	public String getModel() {
		return model;
	}
	public String getService() {
		return service;
	}
	public String getStatus() {
		return status;
	}
	public String getLocation() {
		return location;
	}
	public String getPart() {
		return part;
	}
	public String getEvent() {
		return event;
	}
	public String getNotes() {
		return notes;
	}
	
	public String toString() {
		return String.valueOf(primaryKey) + "," + date + "," + serial + "," + model + "," +
				service + "," + status + "," + location + "," + part + "," + event + "," + notes;
	}
}
