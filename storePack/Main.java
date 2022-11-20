package storePack;
public class Main {
	private static final String StockFileName =  "Stock/Stock.txt";

public static void main(String[] args) {
	
		
		Stock stock = new Stock();
		
		Stock StockSimulation = new Stock();
		StockSimulation.InitializeStock(StockFileName);
		
		stock.InitializeStock(StockFileName);
		
		Manager manager = new Manager("123456789", "Sam", "manager@gmail.com", 1300.90,  new Date(1,12,1983), 13, 2, 1 , new Date(1,1,1950));
		Date FoundationDate = new Date(14,12,1980);
		Store store = new Store("Foriegn Store", FoundationDate,manager,stock);
		
		Employee em1 = new Employee("245678521", "Employee1", "employee1@gmail.com", 900.30, new Date(3,2,1990), 13,new Date(1,2,1970));
		Employee em2 = new Employee("345632578", "Employee2", "employee2@gmail.com", 850.70, new Date(4,1,2000), 13,new Date(2,1,1980));
		
		store.addElementToVector(store.getEmployees(), em1);
		store.addElementToVector(store.getEmployees(), em2);
		
		Supplier ss1 = new Supplier("434137614", "Supplier1", true, new Date(12,2,1987));
		Supplier ss2 = new Supplier("213312111", "Supplier2", false, new Date(14,6,1999));
		
		
		store.addElementToVector(store.getSuppliers(), ss1);
		store.addElementToVector(store.getSuppliers(), ss2);
		
		
		Customer c1 = new Customer("344456789", "Customer1", "customer@gmail.com", "12453", "Customer house 1", "0545425445",new Date(1,1,1995));
		store.addElementToVector(store.getCustomers(), c1);	
		
		//------------------ ThreadProcessSimulation ----------------------
		
		System.out.println("\t\t\tThreads Simulation\n");
		
		store.setStock(StockSimulation);
		Product ppProduct = new Product(12349, "Milk", new Date(20,11,2022), 14.0, 10, 3);
		ThreadsSimulation threadsSimulation = new ThreadsSimulation(store);
		
		System.out.println("The Stock before: \n"+store.getStock());
		
		threadsSimulation.SimulateThreads(store, ppProduct);
		Thread.yield(); // Force the main thread to wait for the threads in simulateThreads to finish before printing the stock
		
		System.out.println("\nThe Stock after: \n" + store.getStock());
		
		//--------------------------- GUI ----------------------------------
		StoreFrame sf = new StoreFrame(store);
		sf.setFileName(StockFileName);
		store.setStock(stock);
		System.out.println("\t\t\tBooting up GUI...\n");
		sf.launchGui();
	}
}
