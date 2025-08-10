package ghostNetFishing;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

@Named("netzDAO")
@RequestScoped
public class NetzDAO {

    @Inject
    private PersonDAO personDAO;
    private Netz net = new Netz();
    private boolean anonym;

    public Netz getNet() {
        return net;
    }

    public boolean isAnonym() {
        return anonym;
    }

    public void setAnonym(boolean anonym) {
        this.anonym = anonym;
    }

    public void setNet(Netz net) {
        this.net = net;
    }

    private Person findExistingPerson(EntityManager em, Person person) {
        String jpql = "SELECT p FROM Person p WHERE LOWER(TRIM(p.firstName)) = :firstName AND LOWER(TRIM(p.lastName)) = :lastName AND TRIM(p.phoneNumber) = :phone";
        List<Person> result = em.createQuery(jpql, Person.class)
            .setParameter("firstName", person.getFirstName().trim().toLowerCase())
            .setParameter("lastName", person.getLastName().trim().toLowerCase())
            .setParameter("phone", person.getPhoneNumber().trim())
            .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    public String saveNet() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ghostNetPersistenceUnit");
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();

            if (!anonym) {
                Person person = personDAO.getPerson(); // vom Formular

                // 🔍 Prüfen, ob eine Personen-ID eingegeben wurde
                String personIdStr = personDAO.getPersonId();
                if (personIdStr != null && !personIdStr.isBlank()) {
                    try {
                        Long personId = Long.parseLong(personIdStr);
                        Person existingById = em.find(Person.class, personId);
                        if (existingById != null) {
                            net.setReportingPerson(existingById);
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN, "Personen-ID nicht gefunden", null));
                            return null;
                        }
                    } catch (NumberFormatException ex) {
                        FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ungültige Personen-ID", null));
                        return null;
                    }
                } else {
                    // 🔁 Kein ID → Name + Telefonnummer prüfen
                    Person existing = findExistingPerson(em, person);
                    if (existing != null) {
                        net.setReportingPerson(existing);
                    } else {
                        em.persist(person);
                        net.setReportingPerson(person);
                        FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Ihre neue Personen-ID: " + person.getID(), null));
                    }
                }
            }

            em.persist(net); // Netz speichern
            t.commit();

        } catch (Exception e) {
            if (t.isActive()) t.rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Speichern!", null));
            return null;
        } finally {
            em.close();
            emf.close();
        }

        return "index"; // oder deine Zielseite
    }
}
