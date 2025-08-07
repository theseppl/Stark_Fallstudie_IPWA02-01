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

    public Person getPerson() {
        return person;
    }

    public String setPerson() {
  //      this.person = person;

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ghostNetPersistenceUnit");
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
            emf.close();
        }
        
        return "index";
    }
}
