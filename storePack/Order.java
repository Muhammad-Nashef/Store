package storePack;

import java.util.*;
public class Order {

	private final int OrderId;
	private Date OrderDate;
	private Map<Product, Integer> purchasedProducts;
	private double Total;
	public static int IdCounter =1;
	
	public Order(Date d,Map<Product, Integer> Products, double total) {
		this.OrderId = IdCounter++;
		this.OrderDate = d;
		this.purchasedProducts = Products;
		this.Total = total;
	}
	public Order(Date date) {
		this.OrderId = IdCounter++;
		this.OrderDate=date;
		this.purchasedProducts=new HashMap<>();
		this.Total=0;
	}
	public Order(Date d,Map<Product, Integer> Products) {
		this.OrderId = IdCounter++;
		this.OrderDate = d;
		this.purchasedProducts = Products;
		this.Total=0;
	}
	
	public int getOrderId() {
		return OrderId;
	}

	public Date getOrderDate() {
		return OrderDate;
	}
	public void setOrderDate(Date orderDate) {
		OrderDate = orderDate;
	}
	public Map <Product, Integer> getPurchasedProducts() {
		return purchasedProducts;
	}
	public void setPurchasedProducts(Map<Product, Integer> purchasedProducts) {
		this.purchasedProducts = purchasedProducts;
	}
	
	public double getTotal() {
		this.Total=0;
		for(Product ppp : this.purchasedProducts.keySet()) {
		    this.Total += ppp.totalPricePerProduct(this.purchasedProducts.get(ppp));
		}
		return this.Total;
	}
	public void setTotal(double total) {
		Total = total;
	}
	
	public void AddProduct(Product product, int quantity) {
		if(checkIfExist(product)) 
			this.purchasedProducts.replace(product, quantity);
		else
		this.purchasedProducts.put(product,quantity);
	}
	
	public void DeleteProduct(Product product) {
		Collection <Product> products = this.purchasedProducts.keySet();
		for (Product p : products) {
			if (p.getProductId() == product.getProductId()) {
				this.purchasedProducts.remove(p);
				break;
			}
		}
	}
	
	private boolean checkIfExist(Product p) { // check if this product exists
		if(this.purchasedProducts.size()!=0) 
			for (Product run : this.purchasedProducts.keySet()) 
				if (run.equals(p)) 
				return true;
		else
			return false;
		return false;
	}
	public String toString() {
		return "Order id: " + this.getOrderId() + "\nOrder date: " + this.getOrderDate() +
				"\n Purchased products: " + this.getPurchasedProducts() + "\norder total: " + this.getTotal();
	}
}
