package storePack;

public class ManagesStock {
	
	Manager manager;
	Stock stock;
	Date updateDate;
	
	public ManagesStock(Manager manager, Stock stock, Date updateDate) {
		this.manager = manager;
		this.stock = stock;
		this.updateDate = updateDate;
	}
	
	public void UpdateProductDetails(Product product, int AvailQty) {
		this.stock.UpdateProduct(product, AvailQty);
		
	}
	
	public String ConfirmUpdateMessage(Product product) {
		return "The manager " + manager.getName() + " updated " + product.getName() 
		+ " in stock with the id " + stock.getStockId() + " at " + updateDate;
	}
	
	public String toString() {
		return manager.toString() + stock.toString() + updateDate.toString();
	}
	
}
