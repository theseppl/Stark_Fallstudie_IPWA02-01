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

	public String saveNet() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ghostNetPersistenceUnit");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            if (!anonym) {
            	Person reportingPerson = personDAO.getPerson();
            	em.persist(reportingPerson);
                net.setReportingPerson(reportingPerson);
                reportingPerson.getReportedNets().add(net); 
            } else {
                net.setReportingPerson(null);
            }

            em.persist(net);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        return "index.xhtml";
    }
}


