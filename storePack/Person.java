package storePack;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Person {
	
	private String Id;
	private String Name;
	private String Email;
	private Date birthdayDate;
	private static final int CorrectIdLength =9;
	
	public Person(String id, String name, String email,Date birthdayDate) {
		this.setId(id);
		this.setName(name);
		this.setEmail(email);
		this.setBirthdayDate(birthdayDate);
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) throws IllegalArgumentException {
		String reg = "[0-9]+";
		Pattern pat = Pattern.compile(reg);
		Matcher m = pat.matcher(id);
		if(id !=null && id.length()==Person.CorrectIdLength) {
			if(m.matches())
				this.Id=id;
			else {
				throw new IllegalArgumentException("Id must contain only numbers 0-9");
			}
		}
			else {
				throw new IllegalArgumentException("Id cant be empty or less than " + Person.CorrectIdLength + " digits");
			}	
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) throws IllegalArgumentException  {
		if(email!= null && email.contains("@") && email.length()>2)
		this.Email = email;
		else {
			throw new IllegalArgumentException("Email must contain @ at least length 2");
		}
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) throws IllegalArgumentException {
		if(!name.equals(""))
		this.Name = name;
		else 
			throw new IllegalArgumentException("name cant be empty");
	}
	public Date getBirthdayDate() {
		return birthdayDate;
	}
	
	public void setBirthdayDate(Date birthdayDate) {
		this.birthdayDate = birthdayDate;
	}
	public String toString() {
		return ("Id : " + this.Id + "\n Name : " + this.Name + "\n Email : " + this.Email);
	}
	public String IsBirdthdayToday() {
		if(new Date(LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(),LocalDate.now().getYear()).equals(birthdayDate))
		return "Happy Birthday " + this.Name ;
		else
		{
			return "Your birthday is not today!";
		}
	}
	public boolean equals(Object o) {
		if (o instanceof Person) {
			Person other = (Person) o;
			return Id.equals(other.Id);
		} else {
			return false;
		}
	}

}
