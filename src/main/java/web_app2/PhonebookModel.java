package web_app2;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Past;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name = "phonebookmodel")
public class PhonebookModel {
	
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull(message = "Фамилия должна быть заполнена")
	@Size(min = 1, max = 20, message = "Фамилия должна быть заполнена")
	private String lastname;
	
	@NotNull(message = "Имя должно быть зполнено")
	@Size(min = 1, max=10, message = "Имя должно быть зполнено")
	private String firstname;

	@NotNull(message = "Рабочий телефон должен быть заполненным")
	@Pattern(regexp="\\d{3}-\\d{3}-\\d{2}-\\d{2}", message = "Рабочий телефон должен иметь формат nnn-ddd-dd-dd")
	private String workphone;	
	
	@NotNull(message = "Мобильный телефон должен быть заполненным")
	@Pattern(regexp="\\d{3}-\\d{3}-\\d{2}-\\d{2}", message = "Мобильный телефон должен иметь формат nnn-ddd-dd-dd")
	private String mobilephone;
	
	@NotNull(message = "Email должен быть заполненным")
	@Email(message = "Некорректный email")
	@Pattern(regexp="[a-zA-Z]{1,30}@[a-zA-Z.]{1,10}", message = "Email должен иметь формат (30 < symbols)+”@”+string(10 < symbols)")
	//string (30 < symbols)+”@”+string(10 < symbols)
	private String email;
	
	@NotNull(message = "Дата рождения должна быть заполнена")
	@Pattern(regexp="\\d{2}\\.\\d{2}\\.\\d{4}", message = "Дата рождения должна иметь формат dd.mm.yyyy")
	private String birthdate;
	
	private String work;

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

	public String getWorkphone() {
		return workphone;
	}

	public void setWorkphone(String workphone) {
		this.workphone = workphone;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	
	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	
}
