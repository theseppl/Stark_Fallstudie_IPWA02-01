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
    
    public boolean isPersonInputValid() {
        String id = personDAO.getPersonId();
        Person p = personDAO.getPerson();

        boolean idInput = id != null && !id.trim().isEmpty();
        boolean personalDatesInput = p != null &&
            p.getFirstName() != null && !p.getFirstName().trim().isEmpty() &&
            p.getLastName() != null && !p.getLastName().trim().isEmpty() &&
            p.getPhoneNumber() != null && !p.getPhoneNumber().trim().isEmpty();

        return idInput || personalDatesInput;
    }

    public String saveNet() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ghostNetPersistenceUnit");
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();

            if (!anonym) {
                // 🔒 Validierung: Entweder ID oder alle Felder müssen ausgefüllt sein
                if (!isPersonInputValid()) {
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Bitte geben Sie entweder eine Personen-ID oder alle drei Felder (Vorname, Nachname, Telefon) ein.", null));
                    return null;
                }

                Person person = personDAO.getPerson();
                String personIdStr = personDAO.getPersonId();

                if (personIdStr != null && !personIdStr.isBlank()) {
                    try {
                        Long personId = Long.parseLong(personIdStr);
                        Person existingById = em.find(Person.class, personId);
                        if (existingById != null) {
                            net.setReportingPerson(existingById);
                            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("existingPersonId", existingById.getID());
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN, "Personen-ID nicht gefunden", null)); //bei falscher Nummer
                            return null;
                        }
                    } catch (NumberFormatException ex) {
                        FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ungültige Personen-ID", null)); //z.B. bei Buchstaben
                        return null;
                    }
                } else {
                    Person existing = findExistingPerson(em, person);
                    if (existing != null) {
                        net.setReportingPerson(existing);
                    } else {
                        em.persist(person);
                        net.setReportingPerson(person);
                        newPersonId = person.getID();
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

        // Flash-Attribute für Weiterleitung
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("newPersonId", newPersonId);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("northSouth", net.getNorthSouth());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("latDegree", net.getLatituteDegree());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("latMinute", net.getLatituteMinute());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("latSeconds", net.getLatituteSeconds());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("eastWest", net.getEastWest());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("longDegree", net.getLongituteDegree());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("longMinute", net.getLongituteMinute());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("longSeconds", net.getLongituteSeconds());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("netSize", net.getNetSize());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("sightingDate", net.getNotificationDate ());

        return "uebersichtMeldung.xhtml?faces-redirect=true";
    }
    
    public List<Netz> getAllNets() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ghostNetPersistenceUnit");
        EntityManager em = emf.createEntityManager();
        List<Netz> nets = null;

        try {
            nets = em.createQuery("SELECT n FROM Netz n", Netz.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }

        return nets;
    }

}

