package ghostNetFishing;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

/**
 * DAO-Klasse zur Verwaltung von Person-Objekten.
 */

@Named("personDAO")
@RequestScoped
public class PersonDAO {
	private Person person = new Person();
	private String personId; 
	
	// Statische EntityManagerFactory für den Zugriff auf die Persistence Unit
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ghostNetPersistenceUnit");
	
	public String getPersonId() {
	    return personId;
	}

	public void setPersonId(String personId) {
	    this.personId = personId;
	}

    public Person getPerson() {
        return person;
    }

    // Persistiert die aktuelle Person in der Datenbank.
    public String setPerson() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();
            em.persist(person); 
            t.commit();
        } catch (Exception e) {
            if (t.isActive()) t.rollback();
            e.printStackTrace(); 
        } finally {
            em.close();
        }
        
        return "index";
    }
}
