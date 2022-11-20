package storePack;

// for future development, we can calculate profits each day using a table in the DB
// and display them for the manager in manager's GUI

public class StockToOrder {
	Stock stock;
	Order order;
	private int OrderCount;
	
	
	public StockToOrder(Stock stock, Order order) {
		this.stock = stock;
		this.order = order;
		this.OrderCount = 0;
	}
	
	public StockToOrder(Stock stock, Order order, int OrderCount) {
		this.stock = stock;
		this.order = order;
		this.OrderCount = OrderCount;
	}

	public int getOrderCount() {
		return OrderCount;
	}

	public void setOrderCount(int orderCount) {
		OrderCount = orderCount;
	}
	
	public String toString() {
		return stock.toString() + order.toString() + OrderCount;
	}
	
	
}
