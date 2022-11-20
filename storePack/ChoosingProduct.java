package storePack;

import java.time.LocalDate;

public class ChoosingProduct {
	Product product;
	Customer customer;
	private Date currentDate;
	
	public ChoosingProduct(Product p,Customer c) {
		this.product=p;
		this.customer=c;
		this.currentDate = new Date(LocalDate.now().getDayOfMonth(),
				LocalDate.now().getMonthValue(),LocalDate.now().getYear());
	}
	
	public Date getCurrentDate() {
		return currentDate;
	}
	
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	public String ProductIsExpired() {
		if(product.getDate() == currentDate) {
			return customer.callForHelp() + " the product " + product.getName() + " date is expired, I need a new one!";
		}
		return "The date is not expired! ";
	}

}
