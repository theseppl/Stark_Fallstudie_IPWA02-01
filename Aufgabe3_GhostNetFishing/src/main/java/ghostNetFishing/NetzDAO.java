package ghostNetFishing;

import java.io.Serializable;
import java.util.List;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

/**
 * DAO-Klasse zur Verwaltung von Netz-Aktivitäten.
 * Beinhaltet Validierung, Speicherung von Netzen und durch Aktionen verbundene Personen
 */

@Named("netzDAO")
@ViewScoped
public class NetzDAO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// Zugriff auf die eingegebene oder ausgewählte Person
    @Inject
    private PersonDAO personDAO;
    // Das aktuell zu speichernde Netz
    private Netz net = new Netz();
    // Flag zur Kennzeichnung einer anonymen Meldung
    private boolean anonym;
    // ID einer neu gespeicherten Person (falls erforderlich)
    private Long newPersonId;
    // EntityManagerFactory für Datenbankzugriffe
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ghostNetPersistenceUnit");
    
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
    public Long getNewPersonId() {
        return newPersonId;
    }
    private boolean personValid;
    public boolean isPersonValid() {
        return personValid;
    }

    /**
     * Sucht eine existierende Person anhand von Vorname, Nachname und Telefonnummer.
     * Vermeidet doppelte Einträge bei manueller Eingabe.
     */
    private Person findExistingPerson(EntityManager em, Person person) {
        String jpql = "SELECT p FROM Person p WHERE LOWER(TRIM(p.firstName)) = :firstName AND LOWER(TRIM(p.lastName)) = :lastName AND TRIM(p.phoneNumber) = :phone";
        List<Person> result = em.createQuery(jpql, Person.class)
            .setParameter("firstName", person.getFirstName().trim().toLowerCase())
            .setParameter("lastName", person.getLastName().trim().toLowerCase())
            .setParameter("phone", person.getPhoneNumber().trim())
            .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
    
    /**
     * Prüft, ob entweder eine Personen-ID oder alle Pflichtfelder für eine neue Person ausgefüllt sind.
     */
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
    
    /**
     * Speichert ein Netz in der Datenbank.
     * Validiert die Personenangaben und verknüpft das Netz mit einer existierenden oder neuen Person.
     */
    public String saveNet() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();
            if (!anonym) {
            	// Validierung der Personenangaben
                if (!isPersonInputValid()) {
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Bitte geben Sie entweder eine Personen-ID oder alle drei Felder (Vorname, Nachname, Telefon) ein.", null));
                    return null;
                }
                Person person = personDAO.getPerson();
                String personIdStr = personDAO.getPersonId();
                
                // Verarbeitung nach Eingabe einer Personen-ID
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
                	// Verarbeitung bei manueller Eingabe
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
            // Netz speichern
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
        }

        // Übergabe von Daten für die nächste Seite via Flash-Context
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("newPersonId", newPersonId);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("locationLat", net.getNorthSouth() + " " +
                net.getLatituteDegree() + "°" + net.getLatituteMinute() + "'" + net.getLatituteSeconds() + "″");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("locationLong", net.getEastWest() + " " +
                net.getLongituteDegree() + "°" + net.getLongituteMinute() + "'" + net.getLongituteSeconds()+ "″");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("netSize", net.getNetSize());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("sightingDate", net.getNotificationDate ());

        return "uebersichtMeldung.xhtml?faces-redirect=true";
    }
    
    //  Liefert alle Netze mit einem bestimmten Status aus der Datenbank.
    public List<Netz> getAllNets(String status) {
        EntityManager em = emf.createEntityManager();
        List<Netz> nets = null;

        try {
        	nets = em.createQuery("SELECT n FROM Netz n WHERE n.status = :status", Netz.class)
                    .setParameter("status", status)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return nets;
    }
    
    /**
     * Aktualisiert den Status eines ausgewählten Netzes und verknüpft es mit einer Person.
     * Leitet anschließend abhängig vom neuen Status auf die passende Übersichtsseite weiter.
     */
    public String selectNet(Netz selectedNet, String statusNew) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();
            Netz managedNet = em.find(Netz.class, selectedNet.getId());
            if (managedNet != null) {

                // Person anhand ID oder Eingabedaten ermitteln
                Person person = null;
                String personIdStr = personDAO.getPersonId();

                if (personIdStr != null && !personIdStr.isBlank()) {
                    try {
                        Long personId = Long.parseLong(personIdStr);
                        person = em.find(Person.class, personId);
                    } catch (NumberFormatException e) {
                        // Ungültige ID ignorieren
                    }
                } else {
                    person = findExistingPerson(em, personDAO.getPerson());
                    if (person == null) {
                        em.persist(personDAO.getPerson());
                        person = personDAO.getPerson();
                    }
                }

                // Person als bergende Person zuweisen
                if (person != null) {
                    managedNet.setRecoveringPerson(person);
                }

                // Status aktualisieren und speichern
                managedNet.setStatus(statusNew);
                em.merge(managedNet);

                // Flash-Attribute für die nächste Seite setzen
                FacesContext fc = FacesContext.getCurrentInstance();
                fc.getExternalContext().getFlash().put("netzId", managedNet.getId());
                fc.getExternalContext().getFlash().put("netzSize", managedNet.getNetSize());
                fc.getExternalContext().getFlash().put("notificationDate", managedNet.getNotificationDate());
                fc.getExternalContext().getFlash().put("locationLat", managedNet.getNorthSouth() + " " +
                    managedNet.getLatituteDegree() + "°" + managedNet.getLatituteMinute() + "'" + managedNet.getLatituteSeconds() + "″");
                fc.getExternalContext().getFlash().put("locationLong", managedNet.getEastWest() + " " +
                    managedNet.getLongituteDegree() + "°" + managedNet.getLongituteMinute() + "'" + managedNet.getLongituteSeconds() + "″");
            }
            t.commit();
        } catch (Exception e) {
            if (t.isActive()) t.rollback();
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler beim Aktualisieren!", null));
            return null;
        } finally {
            em.close();
        }

        // Weiterleitung abhängig vom neuen Status
        if ("Bergung bevorstehend".equalsIgnoreCase(statusNew)) {
            return "uebersichtBergung.xhtml?faces-redirect=true";
            } 
        else if ("geborgen".equalsIgnoreCase(statusNew)) {
            return "uebersichtBergungErfolg.xhtml?faces-redirect=true";
        }
        else {
            return "index.xhtml?faces-redirect=true";
        }
    }
    
    /**
     * Prüft, ob eine Person zur Bergung eines Netzes gültig ist.
     * Entweder über ID oder durch vollständige Eingabe der Personendaten.
     * Legt bei Bedarf eine neue Person an.
     */
    public void checkPerson() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction t = em.getTransaction();

        try {
            t.begin();
            String personIdStr = personDAO.getPersonId();
            Person person = personDAO.getPerson();

            if (personIdStr != null && !personIdStr.isBlank()) {
                try {
                    Long personId = Long.parseLong(personIdStr);
                    Person existingById = em.find(Person.class, personId);
                    personValid = existingById != null;
                } catch (NumberFormatException e) {
                    personValid = false;
                }
            } else if (person != null &&
                       person.getFirstName() != null && !person.getFirstName().trim().isEmpty() &&
                       person.getLastName() != null && !person.getLastName().trim().isEmpty() &&
                       person.getPhoneNumber() != null && !person.getPhoneNumber().trim().isEmpty()) {

                Person existing = findExistingPerson(em, person);
                if (existing != null) {
                    personValid = true;
                } else {
                    em.persist(person);
                    newPersonId = person.getID();
                    personValid = true;
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Neue Person wurde angelegt", null));
                }
            } else {
                personValid = false;
            }

            t.commit();
        } catch (Exception e) {
            if (t.isActive()) t.rollback();
            e.printStackTrace();
            personValid = false;
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler bei der Personenprüfung", null));
        } finally {
            em.close();
        }
    }
}

