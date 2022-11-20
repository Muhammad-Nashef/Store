package storePack;

import java.io.*;
import java.util.*;

public class Stock {
	static int counter = 1;
	private final int StockId;
	private HashMap<Product, Integer> Products;

	public Stock() {
		this.StockId = counter++;
		this.Products = new HashMap<Product, Integer>();
	}

	public Stock(Map<Product, Integer> products) {
		this.Products = new HashMap<Product, Integer>(products);
		this.StockId = counter;
		counter++;
	}

	public int getStockId() {
		return this.StockId;
	}

	public HashMap<Product, Integer> getProducts() {
		return this.Products;
	}

	public void AddProduct(Product p, int qty) {
		this.Products.put(p, qty);
	}

	public String showSelected(Vector<Product> products) {
		String SelectedProduct = "";
		if (!products.isEmpty()) {
			for (Product product : products)
				SelectedProduct += product.toString() + " " + this.Products.get(product);
			return SelectedProduct;
		}
		return "No selected products";
	}

	public void InitializeStock(String FileName) {
		try {
			Scanner FileInput = new Scanner(new File(FileName));
			while (FileInput.hasNextLine()) {
				String buff = FileInput.nextLine();
				if (!buff.equals("")) {
					String[] splitBuff = buff.split("\\s+");
					int pId = Integer.parseInt(splitBuff[0]);
					String pName = splitBuff[1];
					String[] splitDate = splitBuff[2].split("/");
					Date pDate = new Date(Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1]),
							Integer.parseInt(splitDate[2]));
					double pPrice = Double.parseDouble(splitBuff[3]);
					double pBP = Double.parseDouble(splitBuff[4]);
					int pBQ = Integer.parseInt(splitBuff[5]);
					int pSQ = Integer.parseInt(splitBuff[6]);
					Product NewP = new Product(pId, pName, pDate, pPrice, pBP, pBQ);
					this.Products.put(NewP, pSQ);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public void saveStock(String FileName) { // Save current stock 
		try {
			PrintStream fileOut;
			fileOut = new PrintStream(new File(FileName));
			for (Product currentProduct : this.getProducts().keySet()) {
				fileOut.println(currentProduct.getProductId() + "\t\t" + currentProduct.getName() + "\t\t"
						+ currentProduct.getDate() + "\t\t" + currentProduct.getPrice() + "\t\t"
						+ currentProduct.getBulkPrice() + "\t\t" + currentProduct.getBulkQuantity() + "\t\t"
						+ this.getProducts().get(currentProduct));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// return product quantity
	public int getProductQuantity(Product product) {
		for (Product p : Products.keySet()) {
			if (p.getProductId() == product.getProductId())
				return Products.get(p);
		}
		return -1;
	}

	public void UpdateProduct(Product newp, int qty) { // update product details
		for (Product p : this.getProducts().keySet()) {
			if (p.getProductId() == newp.getProductId()) {
				this.getProducts().remove(p);
				this.getProducts().put(newp, qty);
				break;
			}
		}
	}

	// Multithreaded function to update products quantity in stock
	public synchronized void updateQuantity(Product p, int newVal) {
		for (Map.Entry<Product, Integer> entry : Products.entrySet()) {
			if (entry.getKey().getProductId() == p.getProductId()) {
				entry.setValue(0 + newVal);
			}
		}
	}

	public String toString() {
		String return_String = "";
		for (Product p : getProducts().keySet()) {
			return_String += p.toString() + " Quantity " + getProducts().get(p) + "\n";
		}
		return return_String;
	}

	public boolean ifExist(Product product) { // checks if this product exists in the stock
		for (Product p : this.getProducts().keySet()) {
			if (p.getProductId() == product.getProductId()) {
				return true;
			}
		}
		return false;
	}
}
