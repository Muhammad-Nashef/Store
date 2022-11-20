package storePack;

import java.text.NumberFormat;

public class Product {
	
	private int productId;
    private String Name;
    private Date date;
    private double Price;
    private double bulkPrice;
    private int bulkQuantity;
    
    
    public Product() {
		this.productId = 1234576;
		this.Name = "Initial";
		this.setDate(new Date(1,1,2000));
		this.setPrice(1);
		this.setBulkPrice(1);
		this.setBulkQuantity(1);
    }
    
    public Product(int productId, String name, Date d, double price, double bulkPrice,int bulkQuantity) {
		this.productId = productId;
		this.setName(name);
		this.setDate(d);
		this.setPrice(price);
		this.setBulkPrice(bulkPrice);
		this.setBulkQuantity(bulkQuantity);
	}
    
	public int getProductId() {
		return this.productId ;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public double getBulkPrice() {
		return this.bulkPrice;
	}
	public int getBulkQuantity(){
		return this.bulkQuantity;
	}
	public void setBulkPrice(double bulkPrice) throws IllegalArgumentException {
		if(bulkPrice>=0)
			this.bulkPrice=bulkPrice;
		else
			throw new IllegalArgumentException("Bulk price must be greater than 0!");
	}
	public void setBulkQuantity(int bulkQuantity) throws IllegalArgumentException {
		if(bulkQuantity>=0)
			this.bulkQuantity=bulkQuantity;
		else
			throw new IllegalArgumentException("Bulk quantity must be greater than 0!");
	}
	
	public String getName() {
		return this.Name;
	}
	public void setName(String name) throws IllegalArgumentException {
		if(!name.equals(""))
			this.Name = name;
		else
			throw new IllegalArgumentException("Name cant be empty!");
	}
	public Date getDate() {
		return this.date;
	}
	public void setDate(Date date) throws IllegalArgumentException  {
		if(date.isValid(date.getDay(),date.getMonth(),date.getYear()))
			this.date = date;
		else
			throw new IllegalArgumentException("Invalid date");
	}
	public double getPrice() {
		return this.Price;
	}
	public void setPrice(double price) throws IllegalArgumentException {
		if(price >= 0)
		this.Price = price;
		else
			throw new IllegalArgumentException("Price must be greater than 0!");
	}
	
	public String toString() {
		return this.getName() + " " + NumberFormat.getCurrencyInstance().format(this.Price) + " (" + NumberFormat.getCurrencyInstance().format(bulkPrice)
				+ " for " + this.bulkQuantity + ")";
	}
	public boolean equals(Object P) {
        if (P instanceof Product) {
            Product other = (Product) P;
            return this.productId == other.productId;
        } else {
            return false;
        }
	}

	public double totalPricePerProduct(int quantity) {
		double cuprice=0;
		if(quantity>=this.getBulkQuantity()&&this.getBulkQuantity()!=0&&this.getBulkPrice()!=0)
			cuprice +=((quantity/this.getBulkQuantity())*this.getBulkPrice()+((quantity % this.getBulkQuantity())*this.getPrice()));
		else
			cuprice +=quantity *this.getPrice();
		return cuprice;
	}
}
