import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.ListSelectionModel;

import java.awt.Toolkit;

import javax.swing.JTextField;

import java.awt.Choice;

public class DEStore extends UnicastRemoteObject implements DES {

	static StoreApplicationLayer appLayer;

	private JFrame frmDestoreDistributedManagement;
	private JTextField txtProductName;
	private JTable productTable = new JTable();
	private JTable customerTable = new JTable();
	private JTable lowStockTable = new JTable();
	private JTable noStockTable = new JTable();
	private JTable salesTable = new JTable();;

	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				try {
					DEStore window = new DEStore();
					window.frmDestoreDistributedManagement.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public DEStore() throws RemoteException {
		initialize();
	}

	// Initialize the GUI
	public void initialize() {

		StoreDataLayer dataLayer = new StoreDataLayer();
		appLayer = new StoreApplicationLayer(dataLayer);

		frmDestoreDistributedManagement = new JFrame();
		frmDestoreDistributedManagement.setResizable(false);
		frmDestoreDistributedManagement
				.setTitle("DE-Store Distributed Management System");
		frmDestoreDistributedManagement.setIconImage(Toolkit
				.getDefaultToolkit().getImage(
						"C:\\Users\\Chris\\Desktop\\Coursework\\lib\\DESIcon"));
		frmDestoreDistributedManagement.setBounds(100, 100, 650, 464);
		frmDestoreDistributedManagement
				.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDestoreDistributedManagement.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 644, 435);
		frmDestoreDistributedManagement.getContentPane().add(tabbedPane);

		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				productTable.setModel(appLayer.getProducts());
				customerTable.setModel(appLayer.getCustomers());
				lowStockTable.setModel(appLayer.getLowStock());
				noStockTable.setModel(appLayer.getNoStock());
				salesTable.setModel(appLayer.getSales());
			}
		};
		tabbedPane.addChangeListener(changeListener);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Products", null, panel, null);
		panel.setLayout(null);

		JButton btnAddProduct = new JButton("Add Product");
		btnAddProduct.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAddProduct.setBounds(10, 323, 125, 32);
		panel.add(btnAddProduct);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 619, 301);
		panel.add(scrollPane);

		productTable.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				customerTable.setModel(appLayer.getCustomers());
			}
		});
		productTable.setEnabled(true);
		productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(productTable);
		productTable.setModel(appLayer.getProducts());

		JLabel label = new JLabel("?");
		label.setToolTipText("<html>\r\n<b>ADD PRODUCT</b><br>\r\nTo add a new product to the database click the button labelled \"Add Product\"<br>\r\nand fill the input fields in with the products information. Note: you cannot <br>\r\ntwo products with the same name.<br><br>\r\n\r\n<b>EDIT PRODUCT</b><br>\r\nTo edit an existing product in the database, select an item from the list by<br>\r\nclicking on it and then click the button labelled \"Edit Product\". Then change<br>\r\nany product information using the input fields. Select done when ready to make<br>\r\nthe changed.<br><br>\r\n\r\n<b>DELETE PRODUCT</b><br>\r\nTo delete and existing product from the database, select an item from the list by<br>\r\nclicking on it and the click the button labelled \"Delete Product\".<br>\r\n<html>");
		label.setIcon(new ImageIcon(
				DEStore.class
						.getResource("/com/sun/java/swing/plaf/windows/icons/Inform.gif")));
		label.setBounds(598, 364, 31, 32);
		panel.add(label);

		JButton btnEditProduct = new JButton("Edit Product");
		btnEditProduct.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnEditProduct.setBounds(145, 323, 125, 32);
		panel.add(btnEditProduct);

		JButton btnDeleteProduct = new JButton("Delete Product");
		btnDeleteProduct.setIcon(null);
		btnDeleteProduct.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnDeleteProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (productTable.getSelectionModel().isSelectionEmpty()) {
					JOptionPane.showMessageDialog(
							frmDestoreDistributedManagement,
							"Please select a product to delete from the table",
							"Warning", JOptionPane.WARNING_MESSAGE);
				} else {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(
							frmDestoreDistributedManagement,
							"Are you sure you want to delete this product?",
							"Warning", dialogButton);
					if (dialogResult == 0) {
						String name = (String) productTable.getValueAt(
								productTable.getSelectedRow(), 0);
						String result = appLayer.deleteProduct(name);
						System.out.println(result);
						productTable.setModel(appLayer.getProducts());
						customerTable.setModel(appLayer.getCustomers());
						lowStockTable.setModel(appLayer.getLowStock());
						noStockTable.setModel(appLayer.getNoStock());
						salesTable.setModel(appLayer.getSales());
					}
				}
			}
		});
		btnDeleteProduct.setBounds(280, 323, 125, 32);
		panel.add(btnDeleteProduct);

		JButton btnSearchProduct = new JButton("Search Products");
		btnSearchProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String name = txtProductName.getText();
				if (name.equals("  Product name to search")) {
					JOptionPane.showMessageDialog(
							frmDestoreDistributedManagement,
							"Enter a name to search", "Warning",
							JOptionPane.WARNING_MESSAGE);
				} else {
					Product foundProduct = appLayer.searchProduct(name);
					if (foundProduct == null) {
						JOptionPane.showMessageDialog(
								frmDestoreDistributedManagement,
								"No product found with name: " + name,
								"Warning", JOptionPane.WARNING_MESSAGE);
					} else {
						txtProductName.setText("  Product name to search");
						FoundProductDialog dialog = new FoundProductDialog(
								foundProduct);
						dialog.setModalityType(ModalityType.APPLICATION_MODAL);
						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setLocationRelativeTo(frmDestoreDistributedManagement);
						WindowListener exitListener = new WindowAdapter() {
							@Override
							public void windowClosed(WindowEvent e) {
								productTable.setModel(appLayer.getProducts());
								customerTable.setModel(appLayer.getCustomers());
								lowStockTable.setModel(appLayer.getLowStock());
								noStockTable.setModel(appLayer.getNoStock());
								salesTable.setModel(appLayer.getSales());
							}
						};
						dialog.addWindowListener(exitListener);

						dialog.setVisible(true);
					}
				}
			}
		});
		btnSearchProduct.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSearchProduct.setBounds(280, 364, 125, 32);
		panel.add(btnSearchProduct);

		txtProductName = new JTextField();
		txtProductName.setText("  Product name to search");
		txtProductName.setBounds(10, 364, 271, 33);
		panel.add(txtProductName);
		txtProductName.setColumns(10);

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Inventory Control", null, panel_2, null);
		panel_2.setLayout(null);

		JLabel lblProductsLowOn = new JLabel("Products low on stock:");
		lblProductsLowOn.setBounds(10, 11, 169, 14);
		panel_2.add(lblProductsLowOn);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 36, 619, 141);
		panel_2.add(scrollPane_2);

		scrollPane_2.setViewportView(lowStockTable);
		lowStockTable.setModel(appLayer.getLowStock());

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 213, 619, 140);
		panel_2.add(scrollPane_3);

		scrollPane_3.setViewportView(noStockTable);
		noStockTable.setModel(appLayer.getNoStock());

		JLabel lblProductsOutOf = new JLabel("Products out of stock:");
		lblProductsOutOf.setBounds(10, 188, 169, 14);
		panel_2.add(lblProductsOutOf);

		Choice choice = new Choice();
		choice.setBounds(283, 371, 63, 32);
		choice.add("10");
		choice.add("20");
		choice.add("30");
		choice.add("40");
		choice.add("50");
		panel_2.add(choice);

		JButton btnRequestOrderFor = new JButton("Request order for more stock");
		btnRequestOrderFor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Registry registry = LocateRegistry.getRegistry(null);
					CIS stub = (CIS) registry.lookup("CIS");
					if (lowStockTable.getSelectionModel().isSelectionEmpty()) {
						if (noStockTable.getSelectionModel().isSelectionEmpty()) {
							JOptionPane
									.showMessageDialog(
											frmDestoreDistributedManagement,
											"Select a product from either table to order more stock of",
											"Warning",
											JOptionPane.WARNING_MESSAGE);
						} else {
							String product = (String) noStockTable.getValueAt(
									noStockTable.getSelectedRow(), 0);
							int quantity = Integer.parseInt(choice
									.getSelectedItem());
							stub.sendOrder(product, quantity);
						}
					} else {
						String product = (String) lowStockTable.getValueAt(
								lowStockTable.getSelectedRow(), 0);
						int quantity = Integer.parseInt(choice
								.getSelectedItem());
						stub.sendOrder(product, quantity);
					}
					productTable.setModel(appLayer.getProducts());
					customerTable.setModel(appLayer.getCustomers());
					lowStockTable.setModel(appLayer.getLowStock());
					noStockTable.setModel(appLayer.getNoStock());
					salesTable.setModel(appLayer.getSales());

				} catch (Exception e) {
					System.out.println("AccountClient: exception:");
					e.printStackTrace();
				}
			}
		});
		btnRequestOrderFor.setBounds(363, 364, 225, 32);
		panel_2.add(btnRequestOrderFor);

		JLabel lblNumberOfProducts = new JLabel(
				"Number of products to request: ");
		lblNumberOfProducts.setBounds(97, 373, 182, 14);
		panel_2.add(lblNumberOfProducts);

		JLabel label_1 = new JLabel("?");
		label_1.setIcon(new ImageIcon(DEStore.class
				.getResource("/javax/swing/plaf/metal/icons/ocean/info.png")));
		label_1.setToolTipText("<html>\r\n<b>LOW STOCK</b><br>\r\nItems with a quantity less than 20 will show up here as a product low on stock, and<br> products with a quantity of 0 will show up here as a product out of stock. Click on a <br>product, select the number of products you want delivered and click \"Request order for <br>more stock\" to contact the Central Inventory System for a stock replen. <br>\r\n</html>");
		label_1.setBounds(598, 364, 31, 32);
		panel_2.add(label_1);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Customers", null, panel_1, null);
		panel_1.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 11, 619, 301);
		panel_1.add(scrollPane_1);

		scrollPane_1.setViewportView(customerTable);
		scrollPane.setViewportView(productTable);

		customerTable.setEnabled(true);
		customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		customerTable.setModel(appLayer.getCustomers());

		JButton btnAddCustomer = new JButton("Add Customer");
		btnAddCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddCustomerDialog dialog = new AddCustomerDialog();
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setLocationRelativeTo(frmDestoreDistributedManagement);
				WindowListener exitListener = new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						productTable.setModel(appLayer.getProducts());
						customerTable.setModel(appLayer.getCustomers());
						lowStockTable.setModel(appLayer.getLowStock());
						noStockTable.setModel(appLayer.getNoStock());
						salesTable.setModel(appLayer.getSales());
					}
				};
				dialog.addWindowListener(exitListener);
				dialog.setVisible(true);
			}
		});
		btnAddCustomer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAddCustomer.setBounds(10, 323, 125, 32);
		panel_1.add(btnAddCustomer);

		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("Sales", null, panel_3, null);
		panel_3.setLayout(null);

		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(10, 11, 619, 385);
		panel_3.add(scrollPane_4);

		scrollPane_4.setViewportView(salesTable);
		salesTable.setModel(appLayer.getSales());

		txtProductName.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtProductName.getText().equals("  Product name to search")) {
					txtProductName.setText("");
					txtProductName.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtProductName.getText().isEmpty()) {
					txtProductName.setForeground(Color.GRAY);
					txtProductName.setText("  Product name to search");
				}
			}
		});

		btnEditProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (productTable.getSelectionModel().isSelectionEmpty()) {
					JOptionPane.showMessageDialog(
							frmDestoreDistributedManagement,
							"Please select a product to update from the table",
							"Warning", JOptionPane.WARNING_MESSAGE);
				} else {
					String name = (String) productTable.getValueAt(
							productTable.getSelectedRow(), 0);
					int price = (int) productTable.getValueAt(
							productTable.getSelectedRow(), 1);
					int quantity = (int) productTable.getValueAt(
							productTable.getSelectedRow(), 2);
					String promotion = (String) productTable.getValueAt(
							productTable.getSelectedRow(), 3);
					Product updateProduct = new Product(name, price, quantity,
							promotion);
					EditProductDialog dialog = new EditProductDialog(
							updateProduct);
					dialog.setModalityType(ModalityType.APPLICATION_MODAL);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocationRelativeTo(frmDestoreDistributedManagement);
					WindowListener exitListener = new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							productTable.setModel(appLayer.getProducts());
							customerTable.setModel(appLayer.getCustomers());
							lowStockTable.setModel(appLayer.getLowStock());
							noStockTable.setModel(appLayer.getNoStock());
						}
					};
					dialog.addWindowListener(exitListener);

					dialog.setVisible(true);
				}

			}
		});

		btnAddProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddProductDialog dialog = new AddProductDialog();
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setLocationRelativeTo(frmDestoreDistributedManagement);
				WindowListener exitListener = new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						productTable.setModel(appLayer.getProducts());
						customerTable.setModel(appLayer.getCustomers());
						lowStockTable.setModel(appLayer.getLowStock());
						noStockTable.setModel(appLayer.getNoStock());
					}
				};
				dialog.addWindowListener(exitListener);
				dialog.setVisible(true);
			}
		});
	}

	@Override
	public void receiveOrder(String product, int quantity)
			throws RemoteException {
		// Method invoked by the central inventory system
		StoreDataLayer dataLayer = new StoreDataLayer();
		appLayer = new StoreApplicationLayer(dataLayer);
		Product foundProduct = appLayer.searchProduct(product);
		int newQuantity = foundProduct.getQuantity() + quantity;
		String result = appLayer.editProduct(product, foundProduct.getPrice(),
				newQuantity, foundProduct.getPromo());
		System.out.println(result);
	}

	@Override
	public void receiveSale(int saleCustomer, String saleProduct,
			int saleQuantity) throws RemoteException {
		// Method invoked by the sales system. Calculates sale value and updates
		// customer/product info as necessary
		System.out.println("Sale Received");
		StoreDataLayer dataLayer = new StoreDataLayer();
		appLayer = new StoreApplicationLayer(dataLayer);
		int customerID = saleCustomer;
		String product = saleProduct;
		int quantity = saleQuantity;
		int saleValue = 0;
		Product foundProduct = appLayer.searchProduct(saleProduct);
		int productValue = foundProduct.getPrice();
		if (foundProduct.getPromo().equals("Buy 1 get 1 free")) {
			if (quantity % 2 == 0) {
				saleValue = (productValue * (quantity / 2)) + 5;
			} else {
				saleValue = (productValue * (quantity / 2)) + productValue + 5;
			}
		} else if (foundProduct.getPromo().equals("3 for 2")) {
			int offerquant = quantity / 3;
			int newQuant = quantity - offerquant;
			saleValue = (productValue * newQuant) + 5;
		} else if (foundProduct.getPromo().equals("Free delivery")) {
			saleValue = (productValue * quantity);
		} else if (foundProduct.getPromo().equals("None")) {
			saleValue = (productValue * quantity) + 5;
		}
		int loyaltyPoints = saleValue / 10;
		int newQuantity = foundProduct.getQuantity() - quantity;
		appLayer.editProduct(foundProduct.getName(), foundProduct.getPrice(),
				newQuantity, foundProduct.getPromo());
		String result = appLayer.addSale(customerID, product, quantity, saleValue);
		System.out.println(result);
		appLayer.editCustomer(customerID, loyaltyPoints);
	}

}
