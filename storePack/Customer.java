package storePack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer extends Person {
	
	private String CreditCardNum;
	private String Address;
	private String PhoneNumber;
	private static final int PhoneNumberLength = 10; 
	private static final int CreditCardMinLength = 4; 

	public Customer(String id, String name, String email,String creditCardNum, String address,String PhoneNumber , Date birthdayDate) {
		super(id,name ,email,birthdayDate);
		this.setCreditCardNum(creditCardNum);
		this.setAddress(address);
		this.setPhoneNumber(PhoneNumber);
	}
	
	public String getPhoneNumber() {
		return this.PhoneNumber;
	}
	
	public void setPhoneNumber(String PhoneNumber) throws IllegalArgumentException {
		if(PhoneNumber.length()==PhoneNumberLength) {
			this.PhoneNumber=PhoneNumber;
		}
		else
			throw new IllegalArgumentException("Phone number must be " + PhoneNumberLength + " digits");
	}

	public String getCreditCardNum() {
		return this.CreditCardNum;
	}

	public void setCreditCardNum(String creditCardNum) throws IllegalArgumentException {
		String reg = "[0-9]+";
		Pattern pat = Pattern.compile(reg);
		Matcher m = pat.matcher(creditCardNum);
		if(creditCardNum.length() > CreditCardMinLength )
			if(m.matches()) {
				this.CreditCardNum = creditCardNum;
			}
			else {
				throw new IllegalArgumentException ("Credit card number must contain only numbers 0-9");
			}
		else {
			throw new IllegalArgumentException ("Credit card number must be at least 4 digits");
		}
	}

	public String getAddress() {
		return this.Address;
	}

	public void setAddress(String address) {
		this.Address = address;
	}
	public String toString() {
		return super.toString() + "\nCredit card number: " + this.getCreditCardNum() + "\nAddress: " + this.getAddress() + "\nPhone number:" + this.getPhoneNumber() ;
	}
	
	public String callForHelp() {
		return "Hey, I'm " + getName();
	}
}
