package ghostNetFishing;

import java.time.LocalDate;
import jakarta.inject.Named;
import jakarta.persistence.*;


@Entity
@Named("net")
public class Netz {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int ID;
	
    private int latituteSliderDegree;
    private int latituteSliderMinute;
    private int latituteSliderSeconds;
    private int longituteSliderDegree;
    private int longituteSliderMinute;
    private int longituteSliderSeconds;
    private int netSizeSlider = 1;
    private String northSouthRadio;
    private String eastWestRadio;
    private LocalDate dateDe;
    private LocalDate today = LocalDate.now();
    
    public Netz() {}
    
	public Netz(int latituteSliderDegree, int latituteSliderMinute, int latituteSliderSeconds, int longituteSliderDegree,
			int longituteSliderMinute, int longituteSliderSeconds, int netSizeSlider, String northSouthRadio,
			String eastWestRadio, LocalDate dateDe, LocalDate today) {
		super();
		this.latituteSliderDegree = latituteSliderDegree;
		this.latituteSliderMinute = latituteSliderMinute;
		this.latituteSliderSeconds = latituteSliderSeconds;
		this.longituteSliderDegree = longituteSliderDegree;
		this.longituteSliderMinute = longituteSliderMinute;
		this.longituteSliderSeconds = longituteSliderSeconds;
		this.netSizeSlider = netSizeSlider;
		this.northSouthRadio = northSouthRadio;
		this.eastWestRadio = eastWestRadio;
		this.dateDe = dateDe;
		this.today = today;
	}

	public int getLatituteSliderDegree() {
		return latituteSliderDegree;
	}
	public void setLatituteSliderDegree(int latituteSliderDegree) {
		this.latituteSliderDegree = latituteSliderDegree;
	}
	public int getLatituteSliderMinute() {
		return latituteSliderMinute;
	}
	public void setLatituteSliderMinute(int latituteSliderMinute) {
		this.latituteSliderMinute = latituteSliderMinute;
	}
	public int getLatituteSliderSeconds() {
		return latituteSliderSeconds;
	}
	public void setLatituteSliderSeconds(int latituteSliderSeconds) {
		this.latituteSliderSeconds = latituteSliderSeconds;
	}
	public int getLongituteSliderDegree() {
		return longituteSliderDegree;
	}
	public void setLongituteSliderDegree(int longituteSliderDegree) {
		this.longituteSliderDegree = longituteSliderDegree;
	}
	public int getLongituteSliderMinute() {
		return longituteSliderMinute;
	}
	public void setLongituteSliderMinute(int longituteSliderMinute) {
		this.longituteSliderMinute = longituteSliderMinute;
	}
	public int getLongituteSliderSeconds() {
		return longituteSliderSeconds;
	}
	public void setLongituteSliderSeconds(int longituteSliderSeconds) {
		this.longituteSliderSeconds = longituteSliderSeconds;
	}
	public int getNetSizeSlider() {
		return netSizeSlider;
	}
	public void setNetSizeSlider(int netSizeSlider) {
		this.netSizeSlider = netSizeSlider;
	}
	public String getNorthSouthRadio() {
		return northSouthRadio;
	}
	public void setNorthSouthRadio(String northSouthRadio) {
		this.northSouthRadio = northSouthRadio;
	}
	public String getEastWestRadio() {
		return eastWestRadio;
	}
	public void setEastWestRadio(String eastWestRadio) {
		this.eastWestRadio = eastWestRadio;
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
	
}

