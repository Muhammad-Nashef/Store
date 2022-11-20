package storePack;

import java.time.*;
import java.util.*;

public class Store {
	private String storeName;
	private Date foundation_Date;
	Manager manager;
	Stock stock;
	Vector<Customer> customers;
	Vector<Employee> employees;
	Vector<Supplier> suppliers;
	Vector<ManagerSupplier> MSO;
	Vector<SuppliesStock> suppliesStocks;

	public Store(String storeName, Date founDate, Manager manager, Stock stock) {
		this.storeName = storeName;
		this.foundation_Date = founDate;
		this.manager = manager;
		this.stock = stock;
		this.customers = new Vector<>();
		this.employees = new Vector<>();
		this.suppliers = new Vector<>();
		this.MSO = new Vector<>();
		this.suppliesStocks = new Vector<>();
	}

	public Store(String storeName, Date founDate, Manager manager, Stock stock, Vector<Customer> customers,
			Vector<Employee> employees, Vector<Supplier> suppliers, Vector<ManagerSupplier> MSO,
			Vector<SuppliesStock> suppliesStocks) {
		this.storeName = storeName;
		this.foundation_Date = founDate;
		this.manager = manager;
		this.stock = stock;
		this.customers = customers;
		this.employees = employees;
		this.suppliers = suppliers;
		this.MSO = MSO;
		this.suppliesStocks = suppliesStocks;
	}

	public String ReceivedOrders() { // Delivered products by date
		Date todayDate = new Date(LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(),
				LocalDate.now().getYear());
		if (!suppliesStocks.isEmpty()) {
			for (int i = 0; i < suppliesStocks.size(); i++) {
				if (suppliesStocks.get(i).getDeliveryDate().equals(todayDate)) {
					for (Product p : suppliesStocks.get(i).getDeliveredProducts().keySet()) {
						int prevQuantity = stock.getProductQuantity(p);
						int DeliveredQuantity = suppliesStocks.get(i).getDeliveredProducts().get(p);
						stock.updateQuantity(p, prevQuantity + DeliveredQuantity);
					}
				}
			}
			return "All ordered products for this date are delivered";
		}
		return "No orders for the store";
	}

	public <T> void addElementToVector(Vector<T> vector, T newelement) throws IllegalArgumentException {
		if (!vector.contains(newelement))
			vector.add(newelement);
		else
			throw new IllegalArgumentException("Already exist in the store");
	}

	public <T> void deleteElementFromVector(Vector<T> vector, T deleteElement) throws IllegalArgumentException {
		if (vector.contains(deleteElement))
			vector.remove(deleteElement);
		else
			throw new IllegalArgumentException("Is not exist");
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public Manager getManager() {
		return this.manager;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Date getFoundation_Date() {
		return foundation_Date;
	}

	public void setFoundation_Date(Date foundation_Year) {
		this.foundation_Date = foundation_Year;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Vector<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(Vector<Customer> customers) {
		this.customers = customers;
	}

	public Vector<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Vector<Employee> employees) {
		this.employees = employees;
	}

	public Vector<Supplier> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(Vector<Supplier> suppliers) {
		this.suppliers = suppliers;
	}

	public Vector<ManagerSupplier> getMSO() {
		return MSO;
	}

	public void setMSO(Vector<ManagerSupplier> mSO) {
		MSO = mSO;
	}

	public Vector<SuppliesStock> getSuppliesStocks() {
		return suppliesStocks;
	}

	public void setSuppliesStocks(Vector<SuppliesStock> suppliesStocks) {
		this.suppliesStocks = suppliesStocks;
	}

	public int getEmpIdx(String Id) {
		for (int i = 0; i < this.employees.size(); i++)
			if (employees.get(i).getId().equals(Id))
				return i;
		return -1;
	}

	public String toString() {
		return ("Store name: " + this.storeName + "\nStore foundation date: " + this.foundation_Date + "\nManager: "
				+ this.manager + "\nCustomers: " + this.customers + "\nSuppliers: " + this.suppliers + "\nEmployees: "
				+ this.employees + "\n Stock: " + this.stock + "\n Orders from suppliers:" + this.MSO);
	}
}
