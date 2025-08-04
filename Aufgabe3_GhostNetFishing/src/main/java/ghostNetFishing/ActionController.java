package ghostNetFishing;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

@Named("actionController")
@ViewScoped
public class ActionController
             implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date dateDe;
	private Date today = new Date();



	
//	@Inject 
//	private Aufgabenliste aufgabenliste;

	public String getDateDe() {
	    if (dateDe != null) {
	        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	        return sdf.format(dateDe);
	    } else {
	        return "";
	    }
	}

/*
	public Date getDateDe() {
		return dateDe;
	}
	*/

	public void setDateDe(Date dateDe) {
		this.dateDe = dateDe;
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public String impressum() {
        return "impressum";
    }
	
	public String index() {
        return "index";
    }
	
	public String notification() {
        return "meldung";
    }
	

 
/*	
    public String stopEdit() {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("ghostNetPersistenceUnit");
    	
    	EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        t.begin();
        for (Aufgabe a : aufgabenliste.getListe())
          em.merge(a);
        t.commit();
        return "aufgabenliste";
    }
    */
}