package storePack;

import java.util.*;

public class SuppliesStock {
	private Stock stock;
	private Supplier supplier;
	double total;
	private Date deliveryDate;
	private HashMap<Product, Integer> DeliveredProducts;

	public SuppliesStock(Stock stock, Supplier supplier, double total, Date date, HashMap<Product, Integer> DP) {
		this.stock = stock;
		this.supplier = supplier;
		this.total = total;
		this.deliveryDate = date;
		DeliveredProducts = DP;
	}

	public void addDeliveredProd(Product p, int qty) {
		DeliveredProducts.put(p, qty);
	}

	public int getProductVal(Product p) {
		for(Product ppProduct : DeliveredProducts.keySet()) {
			if(p.getProductId()==ppProduct.getProductId())
				return  DeliveredProducts.get(ppProduct);
		}
		return 0;
	}
	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public HashMap<Product, Integer> getDeliveredProducts() {
		return DeliveredProducts;
	}

	public void setDeliveredProducts(HashMap<Product, Integer> deliveredProducts) {
		DeliveredProducts = deliveredProducts;
	}

	public String tosString() {
		return "\nStock: " + this.getStock() + "\nSupplier: " + this.getSupplier() +
				"\nOrder total: " + this.getTotal() + "\nEstimated delivery date: "+
				this.getDeliveryDate() + "\nDelivered products: " + this.getDeliveredProducts();
	}
	public boolean equals(Object o) {
		if (o instanceof SuppliesStock) {
			SuppliesStock other = (SuppliesStock) o;
			return this.stock.equals(other.getStock()) && this.getSupplier().equals(other.getSupplier()) &&
					this.DeliveredProducts.equals(other.getDeliveredProducts());
		} else {
			return false;
		}
	}
}
