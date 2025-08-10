package ghostNetFishing;

import java.io.ObjectOutputStream.PutField;
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
    private Long newPersonId; // 🆕 ID der neu gespeicherten Person

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

    public Long getNeuePersonId() {
        return newPersonId;
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
                    Person existing = findExistingPerson(em, person);
                    if (existing != null) {
                        net.setReportingPerson(existing);
                    } else {
                        em.persist(person);
                        net.setReportingPerson(person);
                        newPersonId = person.getID(); // 🆕 ID speichern
                    }
                }
            }

            em.persist(net);
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
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("newPersonId", newPersonId);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("latDegree", net.getLatituteDegree());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("latMinute", net.getLatituteMinute());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("latSeconds", net.getLatituteSeconds());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("longDegree", net.getLongituteDegree());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("longMinute", net.getLongituteMinute());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("longSeconds", net.getLongituteSeconds());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("netSize", net.getNetSize());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("sightingDate", net.getDateDe());

        return "uebersichtMeldung.xhtml?faces-redirect=true";
    }
}

