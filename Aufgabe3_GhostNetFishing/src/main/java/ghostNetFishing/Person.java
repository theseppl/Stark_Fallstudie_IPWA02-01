package ghostNetFishing;

import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
@Named("person")
public class Person {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long ID;
	
	String lastName;
	String firstName;
	String phoneNumber;
	
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
}
