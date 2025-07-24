package toDoListPack;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named("aufgabenListe")
@ApplicationScoped
public class Aufgabenliste implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Aufgabenliste instance = new Aufgabenliste();
	private List<Aufgabe> liste = new ArrayList<Aufgabe>();
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

	public Aufgabenliste() {
		try {
			liste.add(new Aufgabe("tue dies", "Jane", dateFormat.parse("04.05.1984"), true, 14));
			liste.add(new Aufgabe("tue das", "Basti", dateFormat.parse("09.04.1981"), false, 8));
			liste.add(new Aufgabe("tue jenes", "Nelie", dateFormat.parse("13.06.2007"), false, 22));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static Aufgabenliste getInstance() {
		return instance;
	}

	public List<Aufgabe> getListe() {
		return liste;
	}
}
