import java.awt.EventQueue;

import javax.swing.JFrame;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class CentralInventorySystem extends UnicastRemoteObject implements CIS {

	private JFrame frmCentralInventorySystem;
	private JTable table = new JTable();

	/**
	 * Launch the application.
	 */
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CentralInventorySystem window = new CentralInventorySystem();
					window.frmCentralInventorySystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void sendOrder(String productName, int quantity)
			throws RemoteException {
		try {
			Connection conn = DriverManager
					.getConnection("jdbc:mysql://localhost/destore?user=java&password=java");
			Statement statement = conn.createStatement();
			String update = "INSERT INTO cis (Product, Quantity) "
					+ "VALUES ('" + productName + "', '" + quantity + "')";
			statement.executeUpdate(update);
			statement.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public CentralInventorySystem() throws RemoteException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws RemoteException {

		frmCentralInventorySystem = new JFrame();
		frmCentralInventorySystem.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Chris\\Desktop\\Coursework\\lib\\CISIcon"));
		frmCentralInventorySystem.setTitle("Central Inventory System");
		frmCentralInventorySystem.setResizable(false);
		frmCentralInventorySystem.setBounds(100, 100, 310, 300);
		frmCentralInventorySystem
				.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCentralInventorySystem.getContentPane().setLayout(null);

		JLabel lblOrderRequests = new JLabel("Order Requests");
		lblOrderRequests.setBounds(96, 11, 115, 14);
		frmCentralInventorySystem.getContentPane().add(lblOrderRequests);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 34, 284, 185);
		frmCentralInventorySystem.getContentPane().add(scrollPane);
		table.setModel(getOrders());
		scrollPane.setViewportView(table);

		JButton btnSendOrder = new JButton("Send Selected Order");
		btnSendOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Registry registry = LocateRegistry.getRegistry(null);
					DES stub = (DES) registry.lookup("DES");
					String product = (String) table.getValueAt(
							table.getSelectedRow(), 0);
					int quantity = (int) table.getValueAt(
							table.getSelectedRow(), 1);
					stub.receiveOrder(product, quantity);
					removeOrder(product, quantity);
					table.setModel(getOrders());
				} catch (Exception e) {
					System.out.println("AccountClient: exception:");
					e.printStackTrace();
				}
			}
		});
		btnSendOrder.setBounds(10, 228, 152, 32);
		frmCentralInventorySystem.getContentPane().add(btnSendOrder);

		JButton btnNewButton = new JButton("Get Orders");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				table.setModel(getOrders());
			}
		});
		btnNewButton.setBounds(179, 228, 115, 32);
		frmCentralInventorySystem.getContentPane().add(btnNewButton);
	}

	protected void removeOrder(String product, int quantity) {
		try {
			Connection conn = DriverManager
					.getConnection("jdbc:mysql://localhost/destore?user=java&password=java");
			Statement statement = conn.createStatement();
			String SQL = "DELETE FROM cis WHERE Product = '" + product + "' AND Quantity = '" + quantity + "'";
			statement.executeUpdate(SQL);
			statement.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public DefaultTableModel getOrders() {
		try {
			Connection conn = DriverManager
					.getConnection("jdbc:mysql://localhost/destore?user=java&password=java");
			Statement statement = conn.createStatement();
			String SQL = "SELECT * from cis";
			ResultSet rs = statement.executeQuery(SQL);
			ResultSetMetaData metaData = rs.getMetaData();
			Vector<String> columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			for (int column = 1; column <= columnCount; column++) {
				columnNames.add(metaData.getColumnName(column));
			}
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			while (rs.next()) {
				Vector<Object> vector = new Vector<Object>();
				for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
					vector.add(rs.getObject(columnIndex));
				}
				data.add(vector);
			}
			conn.close();
			DefaultTableModel model = new DefaultTableModel(data, columnNames);
			return model;
		} catch (SQLException sqe) {
			System.err.println("Error performing SQL Update");
			System.err.println(sqe.getMessage());
			System.exit(-1);
		}
		return null;
	}
}
