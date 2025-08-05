package ghostNetFishing;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;

@Named("netzController")
@RequestScoped
public class NetzController {

    private Netz netz = new Netz(); // wird z. B. über ein Formular gefüllt

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

        return "index"; // z. B. zur Übersicht der Netze
    }
}

