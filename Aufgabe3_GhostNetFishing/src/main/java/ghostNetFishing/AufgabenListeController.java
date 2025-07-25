package ghostNetFishing;

import java.io.Serializable;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

@Named("controller")
@ViewScoped
public class AufgabenListeController
             implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject 
	private Aufgabenliste aufgabenliste;

	public String startEdit() {
        return "editierbar";
    }
    
    public String stopEdit() {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("todoAppPersistenceUnit");
    	
    	EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();
        t.begin();
        for (Aufgabe a : aufgabenliste.getListe())
          em.merge(a);
        t.commit();
        return "aufgabenliste";
    }
}
