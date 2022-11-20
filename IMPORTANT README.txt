
 					       ^   Project Summary   ^    
			================================================================

							    Idea
	
	Our project helps the manager to manage his store efficiently, and the customer to have a better experience buying from this store

			================================================================	

						     	  Main menu

	Our GUI showcases the idea of having two different softwares, one for the customer and the other for the manager,
      if we separate them and let the customer buy products using it while the manager is updating the stock,
	it will do that using synchronised threads,
	as a result, we also added a simulation to show how this process (update stock) works

	We added a dynamic label for the manager and the customer, to greet them at each time of the day (morning, afternoon, evening)

			================================================================					

							   Manager
 
	You need to enter a password for verification (so that only the manager will have access),
		 use "123" thats the default password (for simplicity)
	The manager's will be represented, Hire employee, Fire Employee, Add product to stock, Update product details
	,check and order, back
	
	The manager login has a show password option, if its not enabled the password will be hidden
	also, if u press caps-lock, it will notify you in a red font that you are typing in uppercase.
	
	Hire employee: 
		Enter the employee's details to hire him
		
	Fire employee:
		Enter the employee's id to fire him
		
	Add product to stock:
		Enter new product details to add it to the stock
		
	Update product details:
		Choose a product -> change any field you desire 
	
	Check and order:
		Choose a product -> enter the supplier's id and the quantity you need 
		(the id's for the current supplier for the store are 434137614 or 213312111), the manager can add new supplier
	
		i.e a "Milk" supplier with id of 434137614, you just need to enter the quantity then press

		Order Product:
			the supplier takes the order and delivers it when its ready
	
	Check for vacation:
		Enter an employee id i.e "245678521", Vacation from i.e "a date that starts from today onwards at least" ,
		To i.e "enter a desired date" this feature will tell u if this employee is eligible to start a vacation
		we also added the required checks.

	View Suppliers:
		This feature will show u all of the currently available suppliers for this store

	View Employees:
		This feature will show u all of the currently working employees in this store

	Back: 
		Will take you to the main page

			================================================================

							   Customer

	Upon clicking the customer button, you will be presented with a sign up page
	Fields restrictions are: 
		customer id -> 9 digits
		customer email -> example@abc
		customer phone number -> 10 digits
	Then press the "Browse Store" button
	
	The shopping cart page will appear, add the amount you need 
	(if the amount you need is bigger than that in the stock, a message will appear accordingly)

	Pay:
		Enter the credit card number you signed up with, if the transaction is accepted a message will appear telling that
		and your receipt will be printed for you (named after your name) in the receipts folder, 
		press ok and it will take you to the main menu


			================================================================

							    Exit

	If you press "Exit" the program will end (not only the GUI), your changes on stock will be saved


					Hopefully you enjoy our project :)