package ghostNetFishing;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

@Named("personDAO")
@RequestScoped
public class PersonDAO {
	private Person person = new Person(); // wird z. B. über ein Formular gefüllt
	private String personId; //zur Prüfung ob ID bereits vorhanden
	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ghostNetPersistenceUnit"); //Eine EntityManagerFactory für Klasse
	
	public String getPersonId() {
	    return personId;
	}

	public void setPersonId(String personId) {
	    this.personId = personId;
	}

    public Person getPerson() {
        return person;
    }

    public String setPerson() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();
            em.persist(person); // oder em.merge(person), falls es schon gespeichert ist
            t.commit();
        } catch (Exception e) {
            if (t.isActive()) t.rollback();
            e.printStackTrace(); // oder logger.error(...)
        } finally {
            em.close();
        }
        
        return "index";
    }
}
