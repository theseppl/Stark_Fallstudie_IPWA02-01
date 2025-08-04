package ghostNetFishing;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import jakarta.annotation.PostConstruct;
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
	private Date today;


	
//	@Inject 
//	private Aufgabenliste aufgabenliste;

	@PostConstruct
	public void init() {
	    Calendar cal = Calendar.getInstance();
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);
	    today = cal.getTime();
	}

	public Date getDateDe() {
	    return dateDe;
	}
	
	public LocalDate getTodayLocal() {
	    return LocalDate.now();
	}


	public void setDateDe(Date dateDe) {
	    this.dateDe = dateDe;
	}

	public Date getToday() {

	    return today;
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