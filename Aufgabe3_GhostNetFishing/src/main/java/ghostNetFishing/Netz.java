package ghostNetFishing;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import jakarta.inject.Named;
import jakarta.persistence.*;


@Entity
@Named("net")
public class Netz {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long ID;
	
	private String northSouth;
    private int latituteDegree;
    private int latituteMinute;
    private int latituteSeconds;
    
    private String eastWest;
    private int longituteDegree;
    private int longituteMinute;
    private int longituteSeconds;
    
    private int netSize = 1;
    private LocalDate notificationDate = LocalDate.now();
    private String status = "gemeldet";
    
    @ManyToOne
    @JoinColumn(name = "reportingPerson_ID")
    private Person reportingPerson;
    
    public Netz() {}

	public Long getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = (long) iD;
	}

	public int getLatituteDegree() {
		return latituteDegree;
	}

	public void setLatituteDegree(int latituteDegree) {
		this.latituteDegree = latituteDegree;
	}

	public int getLatituteMinute() {
		return latituteMinute;
	}

	public void setLatituteMinute(int latituteMinute) {
		this.latituteMinute = latituteMinute;
	}

	public int getLatituteSeconds() {
		return latituteSeconds;
	}

	public void setLatituteSeconds(int latituteSeconds) {
		this.latituteSeconds = latituteSeconds;
	}

	public int getLongituteDegree() {
		return longituteDegree;
	}

	public void setLongituteDegree(int longituteDegree) {
		this.longituteDegree = longituteDegree;
	}

	public int getLongituteMinute() {
		return longituteMinute;
	}

	public void setLongituteMinute(int longituteMinute) {
		this.longituteMinute = longituteMinute;
	}

	public int getLongituteSeconds() {
		return longituteSeconds;
	}

	public void setLongituteSeconds(int longituteSeconds) {
		this.longituteSeconds = longituteSeconds;
	}

	public int getNetSize() {
		return netSize;
	}

	public void setNetSize(int netSize) {
		this.netSize = netSize;
	}

	public String getNorthSouth() {
		return northSouth;
	}

	public void setNorthSouth(String northSouth) {
		this.northSouth = northSouth;
	}

	public String getEastWest() {
		return eastWest;
	}

	public void setEastWest(String eastWest) {
		this.eastWest = eastWest;
	}

	public String getNotificationDate () {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy").withLocale(Locale.GERMAN);
	    return notificationDate.format(formatter);
	}

	public void setNotificationDate (LocalDate notificationDate) {
		this.notificationDate = notificationDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Person getReportingPerson() {
		return reportingPerson;
	}

	public void setReportingPerson(Person reportingPerson) {
		this.reportingPerson = reportingPerson;
	}

}

