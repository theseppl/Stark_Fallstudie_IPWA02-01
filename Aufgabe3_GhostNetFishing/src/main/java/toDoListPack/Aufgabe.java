package toDoListPack;

// Kommentar zum Commit-Test II
import java.util.Date;
import jakarta.persistence.*;

@Entity
public class Aufgabe {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int ID;

	private String text;
	private String person;
	Date faelligAm;
	Boolean erledigt;
	float aufwand;
	
	public Aufgabe() {}


	public Aufgabe(String text, String person) {
		this.text = text;
		this.person = person;
	}
	
	public Aufgabe(String text, String person, Date faelligAm, Boolean erledigt, float aufwand) {
		super();
		this.text = text;
		this.person = person;
		this.faelligAm = faelligAm;
		this.erledigt = erledigt;
		this.aufwand = aufwand;
	}
	
	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getFaelligAm() {
		return faelligAm;
	}

	public void setFaelligAm(Date faelligAm) {
		this.faelligAm = faelligAm;
	}

	public Boolean getErledigt() {
		return erledigt;
	}

	public void setErledigt(Boolean erledigt) {
		this.erledigt = erledigt;
	}

	public float getAufwand() {
		return aufwand;
	}

	public void setAufwand(float aufwand) {
		this.aufwand = aufwand;
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	
}
