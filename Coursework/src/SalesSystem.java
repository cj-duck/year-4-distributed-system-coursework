import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.awt.Toolkit;


public class SalesSystem extends UnicastRemoteObject implements SS{

	private JFrame frmSalesSystem;
	private JTextField txtSaleCust;
	private JTextField txtSaleProduct;
	private JTextField txtSaleQuantity;

	/**
	 * Launch the application.
	 */
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SalesSystem window = new SalesSystem();
					window.frmSalesSystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SalesSystem() throws RemoteException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSalesSystem = new JFrame();
		frmSalesSystem.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Chris\\Desktop\\Coursework\\lib\\SSIcon"));
		frmSalesSystem.setTitle("Sales");
		frmSalesSystem.setResizable(false);
		frmSalesSystem.setBounds(100, 100, 201, 185);
		frmSalesSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSalesSystem.getContentPane().setLayout(null);
		
		JLabel lblNewSale = new JLabel("New Sale");
		lblNewSale.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewSale.setBounds(10, 11, 83, 14);
		frmSalesSystem.getContentPane().add(lblNewSale);
		
		JLabel lblCustomerid = new JLabel("Customer ID:");
		lblCustomerid.setBounds(10, 36, 86, 14);
		frmSalesSystem.getContentPane().add(lblCustomerid);
		
		txtSaleCust = new JTextField();
		txtSaleCust.setBounds(99, 33, 86, 20);
		frmSalesSystem.getContentPane().add(txtSaleCust);
		txtSaleCust.setColumns(10);
		
		JLabel lblProduct = new JLabel("Product:");
		lblProduct.setBounds(10, 64, 83, 14);
		frmSalesSystem.getContentPane().add(lblProduct);
		
		txtSaleProduct = new JTextField();
		txtSaleProduct.setBounds(99, 61, 86, 20);
		frmSalesSystem.getContentPane().add(txtSaleProduct);
		txtSaleProduct.setColumns(10);
		
		txtSaleQuantity = new JTextField();
		txtSaleQuantity.setColumns(10);
		txtSaleQuantity.setBounds(99, 89, 86, 20);
		frmSalesSystem.getContentPane().add(txtSaleQuantity);
		
		JLabel lblQuantity = new JLabel("Quantity:");
		lblQuantity.setBounds(10, 92, 72, 14);
		frmSalesSystem.getContentPane().add(lblQuantity);
		
		JButton btnNewButton = new JButton("Make Sale");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
				Registry registry = LocateRegistry.getRegistry(null);
				DES stub = (DES) registry.lookup("DES");
				int saleCustomer = Integer.parseInt(txtSaleCust.getText());
				String saleProduct = txtSaleProduct.getText();
				int saleQuantity = Integer.parseInt(txtSaleQuantity.getText());
				
					stub.receiveSale(saleCustomer, saleProduct, saleQuantity);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				txtSaleCust.setText("");
				txtSaleProduct.setText("");
				txtSaleQuantity.setText("");
			}
		});
		btnNewButton.setBounds(10, 120, 108, 23);
		frmSalesSystem.getContentPane().add(btnNewButton);
	}
}
