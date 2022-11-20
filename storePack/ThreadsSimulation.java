package storePack;
import java.util.*;
import java.util.concurrent.*;

/*

	This class will simulate the multithreaded stock update process.
	The only reason we added this class is because we can't open multiple GUI's simultaneously, in our GUI we showed the idea of how
	the manager or the customer experience will go.
	hence, this will show you how our multithreaded process works.

*/

public class ThreadsSimulation extends Thread {
	Store store;

	public ThreadsSimulation(Store store) {
		this.store = store;
	}

	public void SimulateThreads(Store store, Product product) {// Product => Milk , arbitary
		
		// Cached ThreadPools will reuse previously created threads when they are
		// available, this will improve performance
		ExecutorService CustomersES = Executors.newCachedThreadPool();
		ExecutorService SupplierES  = Executors.newCachedThreadPool();
		Random random = new Random();
		int generatedNumber = random.nextInt((7-2)+2) + 1; 
		for (int i = 0; i < generatedNumber; i++) { 
			final int count = i;
			SupplierES.execute(new Runnable() { // when a supplier delivers products
				@Override
				public void run() {
						System.out.println("Supplier - " + (count + 1));
						// 10 - is an arbitary value
						store.getStock().updateQuantity(product, (store.getStock().getProductQuantity(product) + 10)); 
				}
			});
			CustomersES.execute(new Runnable() { // when the customer buys a product
				@Override
				public void run() {
						System.out.println("Customer - " + (count + 1));
						// 5 - is an arbitary value
						store.getStock().updateQuantity(product, (store.getStock().getProductQuantity(product) - 5)); 
				}
			});
		}

		// Ensure that the ThreadPools are shutdown, it will finish previously submitted
		// tasks hence no new tasks will be accepted
		CustomersES.shutdown();
		SupplierES.shutdown();
		
		try {
			// wait for the threads to complete their task, after shutdown request
			CustomersES.awaitTermination(10, TimeUnit.SECONDS); 
			SupplierES.awaitTermination(10, TimeUnit.SECONDS);
			System.out.println("\nAwait termination...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
