package ghostNetFishing;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.*;


@Entity
@Named("person")
public class Person {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long ID;
	
	private String lastName;
	private String firstName;
	private String phoneNumber;

	@OneToMany(mappedBy = "reportingPerson", cascade = CascadeType.ALL)
	private List<Netz> reportedNets = new ArrayList<>();
	
	public Person() {}
	
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Netz> getReportedNets() {
		return reportedNets;
	}

	public void setReportedNets(List<Netz> reportedNets) {
		this.reportedNets = reportedNets;
	}
	
}
