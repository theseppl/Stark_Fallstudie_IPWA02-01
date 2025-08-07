package ghostNetFishing;

import java.time.LocalDate;
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
    private LocalDate dateDe;
    private LocalDate today = LocalDate.now();
    private String status = "gemeldet";
    
    public Netz() {}
    
    /*
	public Netz(int latituteDegree, int latituteMinute, int latituteSeconds, int longituteDegree,
			int longituteMinute, int longituteSeconds, int netSize, String northSouth,
			String eastWest, LocalDate dateDe, LocalDate today) {
		super();
		this.latituteDegree = latituteDegree;
		this.latituteMinute = latituteMinute;
		this.latituteSeconds = latituteSeconds;
		this.longituteDegree = longituteDegree;
		this.longituteMinute = longituteMinute;
		this.longituteSeconds = longituteSeconds;
		this.netSize = netSize;
		this.northSouth = northSouth;
		this.eastWest = eastWest;
		this.dateDe = dateDe;
		this.today = today;
	}
	*/

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

	public LocalDate getDateDe() {
		return dateDe;
	}

	public void setDateDe(LocalDate dateDe) {
		this.dateDe = dateDe;
	}

	public LocalDate getToday() {
		return today;
	}

	public void setToday(LocalDate today) {
		this.today = today;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}

