package ghostNetFishing;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

@Named("netzDAO")
@RequestScoped
public class NetzDAO {

    private Netz netz = new Netz();
    private Person person = new Person(); // wird im selben Formular ausgefüllt
    private boolean anonym;
//    @Inject
//    private PersonDAO personDAO;

    public Netz getNetz() {
        return netz;
    }

    public Person getPerson() {
        return person;
    }
    

    public boolean isAnonym() {
		return anonym;
	}

	public void setAnonym(boolean anonym) {
		this.anonym = anonym;
	}

	public void setNetz(Netz netz) {
		this.netz = netz;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String setNetz() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ghostNetPersistenceUnit");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            if (!anonym) {
            	em.persist(person); // delegiert an PersonDAO
                netz.setMeldendePerson(person);
                person.getGemeldeteNetze().add(netz); 
            } else {
                netz.setMeldendePerson(null);
            }

            em.persist(netz);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        return "index.xhtml"; // oder "index.xhtml"
    }
}


