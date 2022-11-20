package storePack;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StoreFrame extends JFrame {
	private JPanel mainPanel;
	private JPanel currentPanel;
	private Store store;
	private String FileName;

	public StoreFrame(Store store) {
		this.store = store;
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

	public void launchGui() {
		JPanel JP1 = new JPanel();
		JPanel JP2 = new JPanel();
		JPanel JP3 = new JPanel();
		JP1.setLayout(new BorderLayout(1, 2));
		JP2.setLayout(new GridLayout(1, 3));
		JP3.setLayout(new BorderLayout());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setTitle(store.getStoreName());
		JButton managerBtn = new JButton("Manager");
		managerBtn.setBorder(new LineBorder(Color.LIGHT_GRAY));
		JButton customerBtn = new JButton("Customer");
		customerBtn.setBorder(new LineBorder(Color.LIGHT_GRAY));
		JButton exitBtn = new JButton("Exit");
		exitBtn.setBorder(new LineBorder(Color.LIGHT_GRAY));
		JP2.add(managerBtn);
		JP2.add(customerBtn);
		JP2.add(exitBtn);
		JLabel storeIcon = new JLabel();
		storeIcon.setIcon(new ImageIcon("images/store_icon.jpg"));
		JP1.add(storeIcon, BorderLayout.WEST);
		JLabel titleLabel = new JLabel("          Welcome to " + store.getStoreName());
		Font font = new Font("Courier", Font.BOLD, 14);
		Font font2 = new Font("Harlow Solid Italic", Font.BOLD, 14);
		titleLabel.setFont(font);
		JP1.add(titleLabel, BorderLayout.CENTER);
		JP3.add(JP1, BorderLayout.NORTH);
		JP3.add(JP2, BorderLayout.CENTER);
		JLabel InfoLabel = new JLabel("Since : " + store.getFoundation_Date().getYear());
		InfoLabel.setFont(font2);
		JP3.add(InfoLabel, BorderLayout.SOUTH);
		add(JP3);
		pack();
		setSize(400, 150);
		setLocationRelativeTo(null);
		setVisible(true);
		mainPanel = JP3;
		currentPanel = JP3;
		ManagerGui(managerBtn);
		CustomerGui(customerBtn);
// check for Received products and update stock
		store.ReceivedOrders();
		Exit(exitBtn);
	}

	public void Exit(JButton ExitBtn) { // exit, terminate program and save this stock
		ExitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				store.getStock().saveStock(getFileName());
				System.out.println("\t\t\tProgram has been terminated\n");
				System.exit(0);
			}
		});
	}

	public void CustomerGui(JButton customerBtn) {
		customerBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remove(currentPanel);
				JPanel mergePanel = new JPanel(new BorderLayout());
				JPanel panel1 = new JPanel();
				JPanel scanInf = new JPanel();
				scanInf.setLayout(new GridLayout(8, 2));
				scanInf.add(new Label("Customer id :"));
				JTextField CId = new JTextField();
				scanInf.add(CId);
				scanInf.add(new Label("Customer name :"));
				JTextField CN = new JTextField();
				scanInf.add(CN);
				scanInf.add(new Label("Customer email :"));
				JTextField CEmail = new JTextField();
				scanInf.add(CEmail);
				scanInf.add(new Label("Customer credit card number :"));
				JTextField CCreditCardNum = new JTextField();
				scanInf.add(CCreditCardNum);
				scanInf.add(new Label("Customer address :"));
				JTextField CAddress = new JTextField();
				scanInf.add(CAddress);
				scanInf.add(new Label("Customer phone number :"));
				JTextField CPhoneNumber = new JTextField();
				scanInf.add(CPhoneNumber);
				scanInf.add(new Label("Customer Birthday:"));
				JTextField CBirthday = new JTextField();
				scanInf.add(CBirthday);
				JButton continueBtn = new JButton("Browse store");
				JButton Cancel = new JButton("Cancel");
				continueBtn.setBorder(new LineBorder(Color.LIGHT_GRAY));
				Cancel.setBorder(new LineBorder(Color.LIGHT_GRAY));
				scanInf.add(continueBtn);
				scanInf.add(Cancel);
				setSize(450, 400);
				setTitle("Customer details");
				setLocationRelativeTo(null);
				currentPanel = mergePanel;
				panel1.add(new JLabel(Date.decodePeriod(Calendar.getInstance())));
				mergePanel.add(panel1, BorderLayout.NORTH);
				mergePanel.add(scanInf, BorderLayout.CENTER);
				add(mergePanel);
				continueBtn.addActionListener(new ActionListener() { // advance to browse the store
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							Customer cc = new Customer(CId.getText(), CN.getText(), CEmail.getText(),
									CCreditCardNum.getText(), CAddress.getText(), CPhoneNumber.getText(),
									Date.splitDate(CBirthday.getText()));
							store.addElementToVector(store.getCustomers(), cc);
							remove(currentPanel);
							Date currentDate = new Date(LocalDate.now().getDayOfMonth(),
									LocalDate.now().getMonthValue(), LocalDate.now().getYear());
							Order oo = new Order(currentDate);
							setTitle("Store");
							JPanel panel2 = new JPanel();
							panel2.add(new JLabel(Date.decodePeriod(Calendar.getInstance()) + " " + cc.getName()));
							JPanel panel3 = new JPanel(new BorderLayout());
							JPanel jp = new JPanel();
							jp.setLayout(new BorderLayout());
							JTextField total = new JTextField(NumberFormat.getCurrencyInstance().format(0), 12);
							total.setEditable(false);
							total.setEnabled(false);
							total.setDisabledTextColor(Color.BLACK);
							JPanel p = new JPanel();
							p.setBackground(new Color(237, 237, 247));
							JLabel l = new JLabel("Order total");
							l.setForeground(new Color(0, 0, 0));
							p.add(l);
							p.add(total);
							jp.add(p, BorderLayout.NORTH);
							p = new JPanel(new GridLayout(store.getStock().getProducts().size(), 1));
							for (Product ppp : store.getStock().getProducts().keySet())
								addItem(ppp, p, oo, store.getStock(), total, cc);
							jp.add(p, BorderLayout.CENTER);
							JPanel subPanel = new JPanel();
							subPanel.setLayout(new GridLayout(1, 2));
							JButton PayBtn = new JButton("Pay");
							PayBtn.setBorder(new LineBorder(Color.LIGHT_GRAY));
							JButton CancelBtn = new JButton("Cancel");
							Cancel.setBorder(new LineBorder(Color.LIGHT_GRAY));
							subPanel.add(PayBtn);
							subPanel.add(CancelBtn);
							jp.add(subPanel, BorderLayout.SOUTH);
							panel3.add(panel2, BorderLayout.NORTH);
							panel3.add(jp, BorderLayout.CENTER);
							add(panel3);
							remove(currentPanel);
							currentPanel = panel3;
							Cancel(CancelBtn);
							Pay(PayBtn, oo, cc);
							pack();
							setLocationRelativeTo(null);
						} catch (Exception error) {
							JOptionPane.showMessageDialog(null, error.getMessage(), "Invalid value",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				Cancel.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						backTo(store.getStoreName(), mainPanel);
						setSize(400, 150);
						setLocationRelativeTo(null);
					}
				});
			}
		});

	}

	private void updateTotal(Order order, JTextField total) { // update the total after each choice
		double amount = order.getTotal();
		total.setText(NumberFormat.getCurrencyInstance().format(amount));
	}

	private void addItem(final Product product, JPanel p, Order order, Stock stock, JTextField total,
			Customer customer) { // adds the product to the product view
		JPanel sub = new JPanel(new FlowLayout(FlowLayout.LEFT));
		sub.setBackground(new Color(237, 237, 247));
		final JTextField quantity = new JTextField(3);
		quantity.setHorizontalAlignment(SwingConstants.CENTER);
		quantity.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateProduct(product, quantity, order, stock, total, customer);
				quantity.transferFocus();
			}
		});
		quantity.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				updateProduct(product, quantity, order, stock, total, customer);
			}
		});
		sub.add(quantity);
		JLabel l = new JLabel("" + product);
		l.setForeground(new Color(0, 0, 0));
		sub.add(l);
		p.add(sub);
	}

	private void updateProduct(Product product, JTextField quantity, Order order, Stock stock, JTextField total,
			Customer customer) { // updates this product in the order
		int number;
		String text = quantity.getText().trim();
		try {
			number = Integer.parseInt(text);
		} catch (NumberFormatException error) {
			number = 0;
		}
		if (number <= 0 && text.length() > 0) {
			JOptionPane.showMessageDialog(null, "Quantity must be 0 or more", "Invalid value",
					JOptionPane.ERROR_MESSAGE);
			Toolkit.getDefaultToolkit().beep();
			quantity.setText("");
			number = 0;
		}
		if (number > stock.getProducts().get(product)) {
			JOptionPane.showMessageDialog(null, "Sorry,currently unavailable quantity", "Invalid value",
					JOptionPane.ERROR_MESSAGE);
			Toolkit.getDefaultToolkit().beep();
			quantity.setText("");
			number = 0;
		}
		order.AddProduct(product, number);
		updateTotal(order, total);
	}

	public void Pay(JButton PayBtn, Order order, Customer customer) { // prompts for creditcard number and print the
																		// receipt
		PayBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Boolean payed = false;
				String CreditCardNumber = (String) JOptionPane.showInputDialog(null, "Credit card number:",
						"Credit Card", JOptionPane.PLAIN_MESSAGE, new ImageIcon("images/CreditImg.jpg"), null, "");
				for (int num = 0; num < store.getCustomers().size(); num++) {
					if (store.getCustomers().get(num).getCreditCardNum().equals(CreditCardNumber)) {
						payed = true;
						break;
					}
				}
				if (payed) {
					Set<Product> pSet = store.getStock().getProducts().keySet();
					for (Product prod : pSet) {
						if (order.getPurchasedProducts().containsKey(prod)) {
							store.getStock().updateQuantity(prod, (store.getStock().getProducts().get(prod)
									- order.getPurchasedProducts().get(prod)));
						}
					}
					CustomerOrder CO = new CustomerOrder(order, customer);
					CO.printReceipt();
					JOptionPane.showMessageDialog(null, "Thank you for your purchase\nReceipt printed!", "Successfully",
							JOptionPane.PLAIN_MESSAGE);
					backTo(store.getStoreName(), mainPanel);
					setSize(400, 150);
					setLocationRelativeTo(null);
				} else {
					JOptionPane.showMessageDialog(null, "Unavailable credit card", "Invalid value",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	public void Cancel(JButton b) {
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				remove(currentPanel);
				currentPanel = mainPanel;
				add(mainPanel);
				setSize(400, 150);
				setLocationRelativeTo(null);
			}
		});
	}

	public void ManagerGui(JButton managerBtn) {
		managerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPasswordField passF = new JPasswordField();
				JPanel subP = new JPanel();
				JPanel ButtonsPanel = new JPanel(new GridLayout(1, 2));
				JPanel mergeJPanel = new JPanel(new GridLayout(2, 1));
				JPanel loginPanel = new JPanel(new BorderLayout());
				subP.setLayout(new GridLayout(4, 1));
				subP.add(new JLabel("Enter the password:"));
				subP.add(passF);
				JLabel cap = new JLabel();
				subP.add(cap);
				JCheckBox showPass = new JCheckBox("Show password");
				subP.add(showPass);
				JButton Enter = new JButton("Enter");
				Enter.setBorder(new LineBorder(Color.LIGHT_GRAY));
				JButton Cancel = new JButton("Cancel");
				Cancel.setBorder(new LineBorder(Color.LIGHT_GRAY));
				ButtonsPanel.add(Enter);
				ButtonsPanel.add(Cancel);
				remove(currentPanel);
				mergeJPanel.add(subP);
				mergeJPanel.add(ButtonsPanel);
				loginPanel.add(subP, BorderLayout.CENTER);
				loginPanel.add(ButtonsPanel, BorderLayout.SOUTH);
				currentPanel = loginPanel;
				add(loginPanel);
				setSize(270, 130);
				setTitle("Manager login");
				setLocationRelativeTo(null);
				passF.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK)) {
							cap.setVisible(true);
							cap.setForeground(Color.red);
							cap.setText("Warning:caps lock is on!");
						} else {
							cap.setVisible(false);
						}
					}
				});
				showPass.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (showPass.isSelected()) {
							passF.setEchoChar((char) 0);
						} else
							passF.setEchoChar('*');
					}
				});
				Enter.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (passF.getPassword() != null) {
							if (Arrays.equals(passF.getPassword(), Manager.getPassword().toCharArray())) {
								JPanel panel = new JPanel(new BorderLayout());
								JPanel panel1 = new JPanel();
								JLabel label = new JLabel();
								Font font = new Font("Courier", Font.BOLD, 14);
								label.setFont(font);
								label.setText(
										Date.decodePeriod(Calendar.getInstance()) + " " + store.getManager().getName());
								panel1.add(label);
								JPanel managerPanel = new JPanel();
								managerPanel.setLayout(new GridLayout(3, 3));
								setTitle("Store Management");
								setSize(550, 200);
								JButton HireEmpBtn = new JButton("Hire employee");
								HireEmpBtn.setBorder(new LineBorder(Color.LIGHT_GRAY));
								JButton FireEmpBtn = new JButton("Fire employee");
								FireEmpBtn.setBorder(new LineBorder(Color.LIGHT_GRAY));
								JButton AddProdToStockBtn = new JButton("Add product to stock");
								AddProdToStockBtn.setBorder(new LineBorder(Color.LIGHT_GRAY));
								JButton UpdateProdBtn = new JButton("Update product details");
								UpdateProdBtn.setBorder(new LineBorder(Color.LIGHT_GRAY));
								JButton CheckNOrderBtn = new JButton("Check & Order");
								CheckNOrderBtn.setBorder(new LineBorder(Color.LIGHT_GRAY));
								JButton CheckForVacation = new JButton("Check for vacation");
								CheckForVacation.setBorder(new LineBorder(Color.LIGHT_GRAY));
								JButton ViewSuppliers = new JButton("View Suppliers");
								ViewSuppliers.setBorder(new LineBorder(Color.LIGHT_GRAY));
								JButton ViewEmployees = new JButton("View Employees");
								ViewEmployees.setBorder(new LineBorder(Color.LIGHT_GRAY));
								JButton BackBtn = new JButton("Back");
								BackBtn.setBorder(new LineBorder(Color.LIGHT_GRAY));
								managerPanel.add(HireEmpBtn);
								managerPanel.add(FireEmpBtn);
								managerPanel.add(AddProdToStockBtn);
								managerPanel.add(UpdateProdBtn);
								managerPanel.add(CheckNOrderBtn);
								managerPanel.add(CheckForVacation);
								managerPanel.add(ViewSuppliers);
								managerPanel.add(ViewEmployees);
								managerPanel.add(BackBtn);
								remove(loginPanel);
								panel.add(panel1, BorderLayout.NORTH);
								panel.add(managerPanel, BorderLayout.CENTER);
								add(panel);
								currentPanel = panel;
								hireEmployee(HireEmpBtn);
								FireEmployee(FireEmpBtn);
								addProduct(AddProdToStockBtn);
								updateProduct(UpdateProdBtn);
								checkAndOrder(CheckNOrderBtn);
								CheckForVacation(CheckForVacation);
								viewSuppliers(ViewSuppliers);
								viewEmployees(ViewEmployees);
								back(BackBtn);
								setLocationRelativeTo(null);
							} else {
								JOptionPane.showMessageDialog(null, "Invalid password", "Error",
										JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				});
				Cancel.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						backTo(store.getStoreName(), mainPanel);
						setSize(400, 150);
						setLocationRelativeTo(null);
					}
				});
			}
		});
	}

	public void CheckForVacation(JButton CheckForVac) {
		CheckForVac.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel subCurrent = currentPanel;
				remove(currentPanel);
				JPanel panel1 = new JPanel(new GridLayout(4, 2));
				panel1.add(new JLabel("Employee id :"));
				JTextField EmpId = new JTextField();
				panel1.add(EmpId);
				panel1.add(new JLabel("Vacation From (date):"));
				JTextField VacStartDate = new JTextField();
				panel1.add(VacStartDate);
				panel1.add(new JLabel("To:"));
				JTextField VacEndDate = new JTextField();
				panel1.add(VacEndDate);
				JButton CheckBtn = new JButton("Check");
				CheckBtn.setBorder(new LineBorder(Color.LIGHT_GRAY));
				JButton BackBtn = new JButton("Back");
				BackBtn.setBorder(new LineBorder(Color.LIGHT_GRAY));
				panel1.add(CheckBtn);
				panel1.add(BackBtn);
				add(panel1);
				currentPanel = panel1;
				setSize(500, 200);
				setLocationRelativeTo(null);
				CheckBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int EmpIdx = store.getEmpIdx(EmpId.getText());
						if (EmpIdx != -1) {
							try {
								Employee employee = store.getEmployees().get(EmpIdx);
								String res = employee.checkForVacation(Date.splitDate(VacStartDate.getText()),
										Date.splitDate(VacEndDate.getText()));
								JOptionPane.showMessageDialog(null, res, "Vacation", JOptionPane.PLAIN_MESSAGE);

							} catch (Exception e2) {
								JOptionPane.showMessageDialog(null, e2.getMessage(), "Error",
										JOptionPane.ERROR_MESSAGE);
							}

						} else {
							JOptionPane.showMessageDialog(null, "Employee not found!", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				BackBtn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						backTo("Store Management", subCurrent);
						setSize(450, 200);
						setLocationRelativeTo(null);

					}
				});
			}
		});
	}

	public void viewEmployees(JButton viewEmployees) {
		viewEmployees.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JList<Employee> list = new JList<>(store.getEmployees().toArray(new Employee[0]));
				JScrollPane scp = new JScrollPane(list);
				JPanel subCurrent = currentPanel;
				JButton BackBtn = new JButton("Back");
				BackBtn.setBorder(new LineBorder(Color.LIGHT_GRAY));
				JPanel sub = new JPanel();
				sub.setLayout(new BorderLayout());
				remove(currentPanel);
				currentPanel = sub;
				sub.add(scp, BorderLayout.CENTER);
				sub.add(BackBtn, BorderLayout.SOUTH);
				add(sub);
				setTitle("Employees");
				setSize(600, 300);
				setLocationRelativeTo(null);
				BackBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						backTo("Store Management", subCurrent);
						setSize(450, 200);
						setLocationRelativeTo(null);
					}
				});
			}
		});
	}

	public void viewSuppliers(JButton viewSuppliers) {
		viewSuppliers.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JList<Supplier> Suplist = new JList<>(store.getSuppliers().toArray(new Supplier[0]));
				JScrollPane scp = new JScrollPane(Suplist);
				JPanel subCurrent = currentPanel;
				JButton BackBtn = new JButton("Back");
				BackBtn.setBorder(new LineBorder(Color.LIGHT_GRAY));
				JPanel sub = new JPanel();
				sub.setLayout(new BorderLayout());
				remove(currentPanel);
				currentPanel = sub;
				sub.add(scp, BorderLayout.CENTER);
				sub.add(BackBtn, BorderLayout.SOUTH);
				add(sub);
				setTitle("Suppliers");
				setSize(400, 200);
				setLocationRelativeTo(null);
				BackBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						backTo("Store Management", subCurrent);
						setSize(450, 200);
						setLocationRelativeTo(null);
					}
				});
			}
		});
	}

	public void checkAndOrder(JButton CheckNOrderBtn) { // orderes the selected product
		CheckNOrderBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean flag = false;
				JPanel subCurrent = currentPanel;
				Vector<JButton> list = new Vector<JButton>();
				if (!flag)
					for (Product pro : store.getStock().getProducts().keySet()) {
						flag = true;
						JButton element = new JButton("ID: " + pro.getProductId() + " -- name: " + pro.getName());
						element.setBorder(new LineBorder(Color.LIGHT_GRAY));
						list.add(element);
					}
				JPanel sub = new JPanel(new GridLayout(Math.round(list.size() / 2), list.size() / 2));
				for (int j = 0; j < list.size(); j++) {
					sub.add(list.get(j));
				}
				remove(currentPanel);
				add(sub);
				setTitle("Products in the stock");
				setLocationRelativeTo(null);
				pack();
				for (int j = 0; j < list.size(); j++) {
					JButton tempButton = list.get(j);
					list.get(j).addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							tempButton.setBackground(Color.green);
							JPanel sub2 = new JPanel(new GridLayout(3, 2));
							sub2.add(new Label("Supplier id: "));
							JTextField sId = new JTextField();
							sub2.add(new Label("Quantity order: "));
							JTextField qty = new JTextField();
							sub2.add(sId);
							sub2.add(qty);
							sub.removeAll();
							remove(sub);
							setTitle("Order product");
							JButton OrderProductBtn = new JButton("Order Product");
							OrderProductBtn.setBorder(new LineBorder(Color.LIGHT_GRAY));
							JButton CancelBtn = new JButton("Cancel");
							CancelBtn.setBorder(new LineBorder(Color.LIGHT_GRAY));
							sub2.add(OrderProductBtn);
							sub2.add(CancelBtn);
							add(sub2);
							pack();
							currentPanel = sub2;
							setSize(300, 100);
							setLocationRelativeTo(null);
							OrderProductBtn.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									try {
										boolean suppFlag = false;
										int o = 0;
										for (; o < store.suppliers.size(); o++) {
											if (store.getSuppliers().get(o).getSupplierId().equals(sId.getText())) {
												suppFlag = true;
												break;
											}
										}
										if (!suppFlag) {
											JOptionPane.showMessageDialog(null, "Unavailable supplier", "Error",
													JOptionPane.ERROR_MESSAGE);
										}

										if (Integer.parseInt(qty.getText()) < 0) {
											JOptionPane.showMessageDialog(null, "Please enter a positive number",
													"Error", JOptionPane.ERROR_MESSAGE);
										} else if (Integer.parseInt(qty.getText()) > 0 && suppFlag) {
											Product searchedP = null;
											for (Product searchP : store.getStock().getProducts().keySet()) {
												if (tempButton.getText()
														.contains(String.valueOf(searchP.getProductId()))) {
													searchedP = searchP;
												}
											}
											ManagerSupplier MS = new ManagerSupplier(store.getManager(),
													store.suppliers.get(o));
											MS.orderProduct(searchedP, Integer.parseInt(qty.getText()));
											float totalValue = MS.calculateOrder();
											MS.setTotal(totalValue);
											store.MSO.add(MS);
											Date todayDate = new Date(LocalDate.now().getDayOfMonth(),
													LocalDate.now().getMonthValue(), LocalDate.now().getYear());
											// estimated delivery is in 10 days (default value for simplicity)
											todayDate.addDays(10);
											SuppliesStock SuppliesS = new SuppliesStock(store.getStock(),
													store.suppliers.get(o), totalValue, todayDate,
													MS.getOrderedProducts());
											store.addElementToVector(store.getSuppliesStocks(), SuppliesS);
											JOptionPane.showMessageDialog(
													null, "The product " + tempButton.getText()
															+ " is ordered from supplier " + sId.getText(),
													"Ordered!", JOptionPane.PLAIN_MESSAGE);
											backTo("Store Management", subCurrent);
											setSize(450, 200);
											setLocationRelativeTo(null);
										}
									} catch (NumberFormatException e1) {
										JOptionPane.showMessageDialog(null, "Please enter a number", "Error",
												JOptionPane.ERROR_MESSAGE);
									} catch (Exception e2) {
										JOptionPane.showMessageDialog(null, e2.getMessage(), "Error",
												JOptionPane.ERROR_MESSAGE);
									}
								}
							});
							CancelBtn.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									backTo(store.getStoreName(), subCurrent);
									setSize(450, 200);
									setLocationRelativeTo(null);
								}
							});
						}
					});
				}
			}
		});
	}

	private void backTo(String title, JPanel panel) { // removes current panel and adds the panel received in the
													  // argument
		remove(currentPanel);
		currentPanel = panel;
		add(panel);
		setTitle(title);
		setLocationRelativeTo(null);
	}

	public void back(JButton backBtn) {
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backTo(store.getStoreName(), mainPanel);
				setSize(400, 150);
				setLocationRelativeTo(null);
			}
		});
	}

	public void addProduct(JButton addProductBtn) { // adds product to stock (specified for the manager)
		addProductBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel sub = new JPanel(new BorderLayout());
				JPanel sub2 = new JPanel(new GridLayout(8, 2));
				sub2.add(new Label("Product id: "));
				JTextField pId = new JTextField();
				sub2.add(pId);
				sub2.add(new Label("Product name: "));
				JTextField pName = new JTextField();
				sub2.add(pName);
				sub2.add(new Label("Product date: "));
				JTextField pDate = new JTextField();
				sub2.add(pDate);
				sub2.add(new Label("Price per unit: "));
				JTextField pricePerUnit = new JTextField();
				sub2.add(pricePerUnit);
				sub2.add(new Label("Bulk price: "));
				JTextField BulkP = new JTextField();
				sub2.add(BulkP);
				sub2.add(new Label("Bulk Quantity: "));
				JTextField BulkQ = new JTextField();
				sub2.add(BulkQ);
				sub2.add(new Label("Available Quantity: "));
				JTextField AvailQty = new JTextField();
				sub2.add(AvailQty);
				JPanel subCurrent = currentPanel;
				remove(currentPanel);
				JButton Add = new JButton("Add Product");
				Add.setBorder(new LineBorder(Color.LIGHT_GRAY));
				JButton Cancel = new JButton("Cancel");
				Cancel.setBorder(new LineBorder(Color.LIGHT_GRAY));
				sub2.add(Add);
				sub2.add(Cancel);
				sub.add(sub2);
				currentPanel = sub;
				add(sub);
				setSize(300, 280);
				setLocationRelativeTo(null);
				setTitle("Add Product to stock");
				Add.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (Integer.parseInt(AvailQty.getText()) >= 0) {
							String reg = "[0-9]+";
							Pattern pat = Pattern.compile(reg);
							Matcher m = pat.matcher(pId.getText());
							if (!pId.getText().equals("") && m.matches()) {
								try {
									Date newDate = Date.splitDate(pDate.getText());
									Product newProduct = new Product(Integer.parseInt(pId.getText()), pName.getText(),
											newDate, Double.parseDouble(pricePerUnit.getText()),
											Double.parseDouble(BulkP.getText()), Integer.parseInt(BulkQ.getText()));
									boolean ifexist = store.getStock().ifExist(newProduct);
									if (!ifexist) {
										store.getStock().AddProduct(newProduct, Integer.parseInt(AvailQty.getText()));
										JOptionPane.showMessageDialog(null,
												"Product with id " + pId.getText()
														+ " has been successfully added to the stock!",
												"Success", JOptionPane.PLAIN_MESSAGE);
										backTo("Store Management", subCurrent);
										setSize(450, 200);
										setLocationRelativeTo(null);
									} else
										JOptionPane.showMessageDialog(null, "Product already exists!", "Error",
												JOptionPane.ERROR_MESSAGE);
								} catch (Exception e1) {
									JOptionPane.showMessageDialog(null, e1.getMessage(), "Error",
											JOptionPane.ERROR_MESSAGE);
								}

							} else
								JOptionPane.showMessageDialog(null, "Id cant be empty,it must be digits!", "Error",
										JOptionPane.ERROR_MESSAGE);

						} else
							JOptionPane.showMessageDialog(null, "Available quantity must be greater than 0", "Error",
									JOptionPane.ERROR_MESSAGE);
					}
				});
				Cancel.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						backTo("Store Management", subCurrent);
						setSize(450, 200);
						setLocationRelativeTo(null);
					}
				});
			}
		});
	}

	public void hireEmployee(JButton b) {
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel sub = new JPanel(new BorderLayout());
				JPanel sub2 = new JPanel(new GridLayout(8, 2));
				sub2.add(new Label("Employee id:"));
				JTextField eId = new JTextField();
				sub2.add(eId);
				sub2.add(new Label("Employee name:"));
				JTextField eName = new JTextField();
				sub2.add(eName);
				sub2.add(new Label("Employee email:"));
				JTextField eEmail = new JTextField();
				sub2.add(eEmail);
				sub2.add(new Label("Employee salary:"));
				JTextField eSalary = new JTextField();
				sub2.add(eSalary);
				sub2.add(new Label("Employee join date:"));
				JTextField eJoinDate = new JTextField();
				sub2.add(eJoinDate);
				sub2.add(new Label("Employee Birthday:"));
				JTextField eBirthDayDate = new JTextField();
				sub2.add(eBirthDayDate);
				sub2.add(new Label("Employee vacation days:"));
				JTextField vacationDays = new JTextField();
				sub2.add(vacationDays);
				JButton Hire = new JButton("Hire");
				Hire.setBorder(new LineBorder(Color.LIGHT_GRAY));
				JButton Cancel = new JButton("Cancel");
				Cancel.setBorder(new LineBorder(Color.LIGHT_GRAY));
				sub2.add(Hire);
				sub2.add(Cancel);
				sub.add(sub2);
				JPanel subCurrent = currentPanel;
				remove(currentPanel);
				add(sub);
				currentPanel = sub;
				setSize(320, 250);
				setTitle("Hire employee");
				setLocationRelativeTo(null);
				Hire.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							Employee emp = new Employee(eId.getText(), eName.getText(), eEmail.getText(),
									Double.parseDouble(eSalary.getText()), Date.splitDate(eJoinDate.getText()),
									Integer.parseInt(vacationDays.getText()), Date.splitDate(eBirthDayDate.getText()));
							store.addElementToVector(store.getEmployees(), emp);
							JOptionPane.showMessageDialog(null,
									"The new employee " + eName.getText() + " successfully added!", "Hiring",
									JOptionPane.PLAIN_MESSAGE);
							backTo("Store Management", subCurrent);
							setSize(450, 200);
							setLocationRelativeTo(null);
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				Cancel.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						backTo("Store Management", subCurrent);
						setSize(450, 200);
						setLocationRelativeTo(null);
					}
				});
			}
		});
	}

	public void updateProduct(JButton b) { // updates product details (specified for the manager)
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean flag = false;
				Vector<JButton> list = new Vector<JButton>();
				if (!flag)
					for (Product pro : store.getStock().getProducts().keySet()) {
						flag = true;
						JButton element = new JButton("ID: " + pro.getProductId() + " -- name: " + pro.getName());
						element.setBorder(new LineBorder(Color.LIGHT_GRAY));
						list.add(element);
					}
				JPanel sub = new JPanel(new GridLayout(Math.round(list.size() / 2), list.size() / 2));
				for (int j = 0; j < list.size(); j++) {
					sub.add(list.get(j));
				}
				JPanel subCurrent = currentPanel;
				remove(currentPanel);
				add(sub);
				setTitle("Products in the stock");
				setLocationRelativeTo(null);
				pack();
				for (int j = 0; j < list.size(); j++) {
					JButton btemp = list.get(j);
					list.get(j).addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							btemp.setBackground(Color.green);
							JPanel sub2 = new JPanel(new GridLayout(9, 2));
							JButton Update = new JButton("Update");
							Update.setBorder(new LineBorder(Color.LIGHT_GRAY));
							JButton Cancel = new JButton("Cancel");
							Cancel.setBorder(new LineBorder(Color.LIGHT_GRAY));
							sub2.add(new Label("Product id:"));
							JLabel pIdLabel = new JLabel();
							sub2.add(pIdLabel);
							sub2.add(new Label("Product name: "));
							JTextField pName = new JTextField();
							sub2.add(pName);
							sub2.add(new Label("Product date: "));
							JTextField pDate = new JTextField();
							sub2.add(pDate);
							sub2.add(new Label("Price per unit: "));
							JTextField pricePerUnit = new JTextField();
							sub2.add(pricePerUnit);
							sub2.add(new Label("Bulk price: "));
							JTextField BulkP = new JTextField();
							sub2.add(BulkP);
							sub2.add(new Label("Bulk Quantity: "));
							JTextField BulkQ = new JTextField();
							sub2.add(BulkQ);
							sub2.add(new Label("Available Quantity: "));
							JTextField AvailQty = new JTextField();
							sub2.add(AvailQty);
							sub2.add(Update);
							sub2.add(Cancel);
							for (Product p22 : store.getStock().getProducts().keySet()) {
								if (btemp.getText().contains(String.valueOf(p22.getProductId()))) {
									pIdLabel.setText(String.valueOf(p22.getProductId()));
									pName.setText(p22.getName());
									pDate.setText(p22.getDate().toString());
									pricePerUnit.setText(String.valueOf(p22.getPrice()));
									BulkP.setText(String.valueOf(p22.getBulkPrice()));
									BulkQ.setText(String.valueOf(p22.getBulkQuantity()));
									AvailQty.setText(String.valueOf(store.getStock().getProducts().get(p22)));
									break;
								}
							}
							sub.removeAll();
							remove(sub);
							add(sub2);
							setTitle("Update product");
							pack();
							setSize(270, 260);
							currentPanel = sub2;
							setLocationRelativeTo(null);
							Update.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									Product product = new Product();
									Set<Product> pSet = store.getStock().getProducts().keySet();
									for (Product prod : pSet) {
										if (Integer.parseInt(pIdLabel.getText()) == prod.getProductId())
											product = prod;
									}
									if (Integer.parseInt(AvailQty.getText()) >= 0) {
										store.getStock().updateQuantity(product, Integer.parseInt(AvailQty.getText()));
										try {
											Date date = Date.splitDate(pDate.getText());
											Product newProduct = new Product(Integer.parseInt(pIdLabel.getText()),
													pName.getText(), date, Double.parseDouble(pricePerUnit.getText()),
													Double.parseDouble(BulkP.getText()),
													Integer.parseInt(BulkQ.getText()));

											ManagesStock managesStock = new ManagesStock(store.getManager(),
													store.getStock(),
													new Date(LocalDate.now().getDayOfMonth(),
															LocalDate.now().getMonthValue(),
															LocalDate.now().getYear()));

											managesStock.UpdateProductDetails(newProduct,
													Integer.parseInt(AvailQty.getText()));
											JOptionPane.showMessageDialog(null,
													managesStock.ConfirmUpdateMessage(newProduct), "Success",
													JOptionPane.PLAIN_MESSAGE);
											backTo("Store Management", subCurrent);
											setSize(450, 200);
											setLocationRelativeTo(null);
										} catch (Exception e2) {
											JOptionPane.showMessageDialog(null, e2.getMessage(), "Error",
													JOptionPane.ERROR_MESSAGE);
										}
									} else
										JOptionPane.showMessageDialog(null, "Available quantity must be greater than 0",
												"Error", JOptionPane.ERROR_MESSAGE);
								}
							});
							Cancel.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									backTo("Store Management", subCurrent);
									setSize(450, 200);
									setLocationRelativeTo(null);
								}
							});
						}
					});
				}
			}
		});
	}

	public void FireEmployee(JButton FireEmpBtn) {
		FireEmpBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel sub = new JPanel(new BorderLayout());
				JPanel sub2 = new JPanel(new GridLayout(2, 2));
				sub2.add(new Label("Employee id:"));
				JTextField eId = new JTextField();
				sub2.add(eId);
				JPanel subCurrent = currentPanel;
				remove(currentPanel);
				JButton Hire = new JButton("Fire");
				Hire.setBorder(new LineBorder(Color.LIGHT_GRAY));
				JButton Cancel = new JButton("Cancel");
				Cancel.setBorder(new LineBorder(Color.LIGHT_GRAY));
				sub2.add(Hire);
				sub2.add(Cancel);
				sub.add(sub2);
				currentPanel = sub;
				add(sub);
				setSize(270, 100);
				setTitle("Fire employee");
				setLocationRelativeTo(null);
				Hire.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int indexOfEmp = store.getEmpIdx(eId.getText());
						if (indexOfEmp != -1) {
							store.deleteElementFromVector(store.employees, store.getEmployees().get(indexOfEmp));
							JOptionPane.showMessageDialog(null, "Employee with the id: " + eId.getText() + " fired",
									"Success", JOptionPane.PLAIN_MESSAGE);
							backTo("Store Management", subCurrent);
							setSize(450, 200);
							setLocationRelativeTo(null);
						} else {
							JOptionPane.showMessageDialog(null, " No employee id matches the id:" + eId.getText(),
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				Cancel.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						backTo("Store Management", subCurrent);
						setSize(450, 200);
						setLocationRelativeTo(null);
					}
				});
			}
		});
	}
}
