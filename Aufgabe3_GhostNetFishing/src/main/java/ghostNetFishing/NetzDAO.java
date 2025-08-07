package ghostNetFishing;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;

@Named("netzDAO")
@RequestScoped
public class NetzDAO {

    private Netz netz = new Netz(); // wird z. B. über ein Formular gefüllt
    private boolean anonym;

    public Netz getNetz() {
        return netz;
    }

    public String setNetz() {
  //      this.netz = netz;

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ghostNetPersistenceUnit");
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();
            em.persist(netz); // oder em.merge(netz), falls es schon gespeichert ist
            t.commit();
        } catch (Exception e) {
            if (t.isActive()) t.rollback();
            e.printStackTrace(); // oder logger.error(...)
        } finally {
            em.close();
            emf.close();
        }

        if (anonym) {
            return "index"; // Zurück zur Übersicht
        } else {
            return "person"; // Weiter zur nächsten Seite
        } // z. B. zur Übersicht der Netze
    }
    
    public boolean isAnonym() {
        return anonym;
    }

    public void setAnonym(boolean anonym) {
        this.anonym = anonym;
    }
}

