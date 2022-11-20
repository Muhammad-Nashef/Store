package storePack;

import java.util.*;

public class ManagerSupplier {
	private Manager manager;
	private Supplier supplier;
	private HashMap<Product, Integer> OrderedProducts;
	private float total;
	
	public ManagerSupplier(Manager manager,Supplier supplier,HashMap<Product, Integer> OrderedProducts) {
		this.manager=manager;
		this.supplier=supplier;
		this.OrderedProducts=OrderedProducts;
		this.setTotal(0);
	}
	public ManagerSupplier(Manager manager,Supplier supplier) {
		this.manager=manager;
		this.supplier=supplier;
		this.OrderedProducts=new HashMap<Product,Integer>();
		this.setTotal(0);
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	
	public Manager getManager() {
		return manager;
	}
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public void setOrderedProducts(HashMap<Product, Integer> orderedProducts) {
		OrderedProducts = orderedProducts;
	}
	public HashMap<Product, Integer> getOrderedProducts(){
		return OrderedProducts;
	}
	public void setDeliveredProducts(HashMap<Product, Integer> orderedProducts) {
		OrderedProducts = orderedProducts;
	}
	public void orderProduct(Product p,int qty) {
		this.OrderedProducts.put(p, qty);
	}
	public float calculateOrder() {
		if (!OrderedProducts.isEmpty()) {
			
			for (Map.Entry<Product, Integer> entry : OrderedProducts.entrySet()) {
				total += entry.getKey().getPrice() * entry.getValue();
			}
			total += supplier.CalculateBonus();
			return total;
		}
		return 0;
	}
	public boolean equals(Object o) {
		if (o instanceof ManagerSupplier) {
			ManagerSupplier other = (ManagerSupplier) o;
			return this.manager.equals(other.getManager()) && this.getSupplier().equals(other.getSupplier()) &&
					this.getOrderedProducts().equals(other.getOrderedProducts());
		} else {
			return false;
		}
	}
}
