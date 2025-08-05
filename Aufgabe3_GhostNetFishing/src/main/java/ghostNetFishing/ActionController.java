package ghostNetFishing;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named("actionController")
@ViewScoped
public class ActionController implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /* Netz Daten
    private LocalDate dateDe;
    private LocalDate today = LocalDate.now();
    private int latituteSliderDegree;
    private int latituteSliderMinute;
    private int latituteSliderSeconds;
    private int longituteSliderDegree;
    private int longituteSliderMinute;
    private int longituteSliderSeconds;
    private int netSizeSlider = 1;
    private String northSouthRadio;
    private String eastWestRadio;

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

    // 🧩 Zusätzliche Methode, damit maxDate im DatePicker funktioniert
    public Date getTodayAsDate() {
        return Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    // 🧩 Konvertiere dateDe für Anzeige oder Übergabe
    public Date getDateDeAsDate() {
        if (dateDe == null) return null;
        return Date.from(dateDe.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

  */ 
    
    
    
    
    
    
    

    // Navigation
    public String impressum() {
        return "impressum";
    }

    public String index() {
        return "index";
    }

    public String notification() {
        return "meldung";
    }
    
    
    
    
    
}
