package storePack;

import java.io.File;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Formatter;

public class CustomerOrder {
	private Order order;
	private Customer customer;
	private static int counter=0;
	
	public CustomerOrder(Order order,Customer customer) {
		this.order=order;
		this.customer=customer;
	}
	public void printReceipt() {
		PrintStream writeToFile;
		try {
			writeToFile = new PrintStream(new File("Receipts/" + customer.getName() + ".txt"));
			counter++;
			writeToFile.println("\t\t\t\t\t-- Order receipt "+ order.getOrderId() + "--");
			writeToFile.println("Date:"+ order.getOrderDate());
			writeToFile.println("Customer: " + customer.getName());
			writeToFile.println("Phone Number : " + customer.getPhoneNumber());
			writeToFile.println("Customer email : " + customer.getEmail());
			Formatter formatter = new Formatter();
			writeToFile.println(formatter.format("\n\n%s"+ "%25s"+ "%s"+ "%18s"+ "%s"+ "%22s" +"%s", "Product","","Price","","Quantity","","Total price"));
			writeToFile.println();
			Collection<Product>Products = order.getPurchasedProducts().keySet();
			for (Product p : Products) {
				writeToFile.format("%s" +"%25s" + "%.2f" + "%25s" +"%d"+ "%30s"+"%.2f", p.getName(),"",p.getPrice(),"",order.getPurchasedProducts().get(p),
						"",p.totalPricePerProduct(order.getPurchasedProducts().get(p)));
				writeToFile.println();
			}	
			writeToFile.println("\n\nTotal : " + Math.round(order.getTotal() * 100)/100.0 );
			String creditcardNumber=customer.getCreditCardNum();
			String creditCardNew="";
			for(int i=0;i<(customer.getCreditCardNum().length())-4;i++) 
				creditCardNew+='*';
			
			creditCardNew+=creditcardNumber.subSequence(customer.getCreditCardNum().length()-4, customer.getCreditCardNum().length());
			writeToFile.println("Credit card : " + creditCardNew );
			writeToFile.println("\t\t\t\t\tThank you have a nice day!");
			writeToFile.close();
		}catch (Exception e) {
			System.out.println(e);
		}
	}
}
