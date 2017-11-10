package web_app2;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.*;


@Entity
@Table(name = "jobmodel")
public class JobModel {
	
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull(message = "cannot be null")
	@Size(min = 1, max = 25, message = "Фамилия должна быть заполнена")
	private String lastname;
	
	@NotNull(message = "cannot be null")
	@Size(min = 1, max=25, message = "Имя должно быть зполнено")
	private String firstname;
	
	@NotNull(message = "cannot be null")
	@Size(min = 1, message = "Место работы не может быть пустым")
	private String workplace;
	
	@NotNull(message = "cannot be null")
	@Size(min = 1,message = "Адрес не должен быть пустым")
	private String workaddress;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getWorkplace() {
		return workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public String getWorkaddress() {
		return workaddress;
	}

	public void setWorkaddress(String workaddress) {
		this.workaddress = workaddress;
	}
	
	public String toString() {
		return firstname + " " + lastname + " " + workplace + " " + workaddress;
		
	}
}
