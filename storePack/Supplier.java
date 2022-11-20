package storePack;

import java.time.LocalDate;

public class Supplier {

	private String supplierId;
	private String supplierName;
	private boolean isLocal;
	private Date StartDate;
	private static final float SeniorYears15 = 2000;
	private static final float SeniorYears10 = 1000;
	private static final float SeniorYears5  = 500;
	
	public Supplier(String supplierId,String supplierName , boolean isLocal, Date StartDate) {
		this.supplierId=supplierId;
		this.setSupplierName(supplierName);
		this.setIsLocal(isLocal);
		this.setStartDate(StartDate);
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public boolean getIsLocal() {
		return isLocal;
	}
	public void setIsLocal(boolean isLocal) {
		this.isLocal = isLocal;
	}
	public Date getStartDate() {
		return StartDate;
	}
	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}
	public String toString() {
		return "Supplier id: " + this.getSupplierId() + "\n ,Supplier name : " + this.supplierName + "\n ,Is local: " +  this.getIsLocal();
	}
	public float CalculateBonus() { // based on how many years the supplier has worked
		if (isLocal) {
		int SeniorYears = (LocalDate.now().getYear() - StartDate.getYear());
			
			if (SeniorYears > 15) {
				return SeniorYears15;
			}else if (SeniorYears > 10) {
				return SeniorYears10;
			}else if (SeniorYears > 5) {
				return SeniorYears5;
			}else return 0;
		}
		return 0;
	}
	public boolean equals(Object o) {
		if (o instanceof Supplier) {
			Supplier other = (Supplier) o;
			return supplierId.equals(other.supplierId);
		} else {
			return false;
		}
	}
}
