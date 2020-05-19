import java.util.*;
import java.sql.*;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class StoreDataLayer implements StoreDataLayerInterface {

	public StoreDataLayer() {

	}

	// Adding a new product to the database
	public boolean addProduct(Product product) {
		try {
			Connection conn = DriverManager
					.getConnection("jdbc:mysql://localhost/destore?user=java&password=java");
			Statement statement = conn.createStatement();
			String update = "INSERT INTO products (Name, Price, Quantity, Promotion) "
					+ "VALUES ('"
					+ product.getName()
					+ "', '"
					+ product.getPrice()
					+ "', '"
					+ product.getQuantity()
					+ "', '" + product.getPromo() + "')";
			statement.executeUpdate(update);
			statement.close();
			conn.close();
			System.out.println("New product added successful");
			return true;
		} catch (SQLException sqe) {
			System.err.println("Error performing SQL Update");
			System.err.println(sqe.getMessage());
			System.exit(-1);
		}
		return false;
	}

	// Creating a tablemodel for all products in database
	public TableModel getProducts() {
		try {
			Connection conn = DriverManager
					.getConnection("jdbc:mysql://localhost/destore?user=java&password=java");
			Statement statement = conn.createStatement();
			String SQL = "SELECT * from products ORDER BY Name ASC";
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

	// Editing a product in the database
	public boolean editProduct(Product product) {
		try {
			Connection conn = DriverManager
					.getConnection("jdbc:mysql://localhost/destore?user=java&password=java");
			Statement statement = conn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			String query = "SELECT * FROM Products WHERE Name = '"
					+ product.getName() + "'";
			ResultSet results = statement.executeQuery(query);
			results.first();
			results.updateInt("Price", product.getPrice());
			results.updateInt("Quantity", product.getQuantity());
			results.updateString("Promotion", product.getPromo());
			results.updateRow();
			System.out.println("Product update successful");
			statement.close();
			conn.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	// Deleting a product from database
	@Override
	public boolean deleteProduct(String product) {
		try {
			Connection conn = DriverManager
					.getConnection("jdbc:mysql://localhost/destore?user=java&password=java");
			Statement statement = conn.createStatement();
			String SQL = "DELETE FROM Products WHERE Name = '" + product + "'";
			statement.executeUpdate(SQL);
			System.out.println("Product delete successful");
			statement.close();
			conn.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	// searching for a product in the database
	@Override
	public Product searchProduct(String name) {
		try {
			Connection conn = DriverManager
					.getConnection("jdbc:mysql://localhost/destore?user=java&password=java");
			Statement statement = conn.createStatement();
			name = name.toUpperCase();
			String SQL = "SELECT * FROM Products WHERE upper(Name) = '" + name + "'";
			ResultSet results = statement.executeQuery(SQL);
			if(results.next() == false) {
				return null;
			} else {
				String foundName = results.getString(1);
				int foundPrice = results.getInt(2);
				int foundQuantity = results.getInt(3);
				String foundPromo = results.getString(4);
				Product foundProduct = new Product(foundName, foundPrice, foundQuantity, foundPromo);
				
				return foundProduct;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// creating a tablemodel from customers in database
	@Override
	public TableModel getCustomers() {
		try {
			Connection conn = DriverManager
					.getConnection("jdbc:mysql://localhost/destore?user=java&password=java");
			Statement statement = conn.createStatement();
			String SQL = "SELECT * from customers ORDER BY id ASC";
			ResultSet rs = statement.executeQuery(SQL);
			ResultSetMetaData metaData = rs.getMetaData();
			Vector<String> columnNames = new Vector<String>();
			int columnCount = metaData.getColumnCount();
			columnNames.add("ID");
			columnNames.add("First Name");
			columnNames.add("Second Name");
			columnNames.add("Loyalty Points");
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

	// adding a customer to the database
	@Override
	public boolean addCustomer(Customer customer) {
		try {
			Connection conn = DriverManager
					.getConnection("jdbc:mysql://localhost/destore?user=java&password=java");
			Statement statement = conn.createStatement();
			String update = "INSERT INTO customers (first_name, second_name, loyalty_points) "
					+ "VALUES ('"
					+ customer.getFirst()
					+ "', '"
					+ customer.getSecond()
					+ "', '"
					+ customer.getLoyalty()
					+ "')";
			// Execute the statement
			statement.executeUpdate(update);
			// Release resources held by the statement
			statement.close();
			// Release resources held by the connection. This also ensures that
			// the INSERT
			// completes
			conn.close();
			System.out.println("New customer added successful");
			return true;
		} catch (SQLException sqe) {
			System.err.println("Error performing SQL Update");
			System.err.println(sqe.getMessage());
			System.exit(-1);
		}
		return false;
	}

	// creating tablemodel of products low in stock from database
	@Override
	public TableModel getLowStock() {
		try {
			Connection conn = DriverManager
					.getConnection("jdbc:mysql://localhost/destore?user=java&password=java");
			Statement statement = conn.createStatement();
			// where to change the definition for low stock
			String SQL = "SELECT * from products WHERE Quantity<20 AND Quantity!=0 ORDER BY Name ASC";
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

	// creating a tablemodel for products with no stock from database
	@Override
	public TableModel getNoStock() {
		try {
			Connection conn = DriverManager
					.getConnection("jdbc:mysql://localhost/destore?user=java&password=java");
			Statement statement = conn.createStatement();
			String SQL = "SELECT * from products WHERE Quantity=0 ORDER BY Name ASC";
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

	// adding a sale to the database
	@Override
	public boolean addSale(Sale newSale) {
		try {
			Connection conn = DriverManager
					.getConnection("jdbc:mysql://localhost/destore?user=java&password=java");
			Statement statement = conn.createStatement();
			String update = "INSERT INTO sales (customerID, product, quantity, value) "
					+ "VALUES ('"
					+ newSale.getCustomerID()
					+ "', '"
					+ newSale.getProduct()
					+ "', '"
					+ newSale.getQuantity()
					+ "', '"
					+ newSale.getValue()
					+ "')";
			// Execute the statement
			statement.executeUpdate(update);
			// Release resources held by the statement
			statement.close();
			// Release resources held by the connection. This also ensures that
			// the INSERT
			// completes
			conn.close();
			System.out.println("New sale added successful");
			return true;
		} catch (SQLException sqe) {
			System.err.println("Error performing SQL Update");
			System.err.println(sqe.getMessage());
			System.exit(-1);
		}
		return false;
	}
	
	// creating tablemodel of sales from the database
	@Override
	public TableModel getSales() {
		try {
			Connection conn = DriverManager
					.getConnection("jdbc:mysql://localhost/destore?user=java&password=java");
			Statement statement = conn.createStatement();
			String SQL = "SELECT * from sales";
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

	// edit a customer in the database
	@Override
	public boolean editCustomer(int id, int points) {
		try {
			Connection conn = DriverManager
					.getConnection("jdbc:mysql://localhost/destore?user=java&password=java");
			Statement statement = conn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			String query = "SELECT * FROM customers WHERE id = '"
					+ id + "'";
			ResultSet results = statement.executeQuery(query);
			results.first();
			int currentPoints = results.getInt(4);
			results.updateInt("loyalty_points", currentPoints + points);

			results.updateRow();
			System.out.println("customer update successful");
			statement.close();
			conn.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
