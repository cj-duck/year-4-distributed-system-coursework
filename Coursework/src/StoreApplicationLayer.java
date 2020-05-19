import javax.swing.table.TableModel;

public class StoreApplicationLayer {
	private StoreDataLayerInterface dataLayer;

	public StoreApplicationLayer(StoreDataLayerInterface dataLayer) {
		this.dataLayer = dataLayer;
	}

	public String addProduct(String name, int price, int quantity, String promo) {
		Product product = new Product(name, price, quantity, promo);
		boolean success = dataLayer.addProduct(product);
		if (success) {
			return "Product " + name + " added";
		} else {
			return "Failed to add Product " + name;
		}
	}

	public TableModel getProducts() {
		TableModel model = dataLayer.getProducts();
		return model;

	}

	public String editProduct(String name, int price, int quantity, String promo) {
		Product product = new Product(name, price, quantity, promo);
		boolean success = dataLayer.editProduct(product);
		if (success) {
			return "Product " + name + " updated";
		} else {
			return "Failed to update Product " + name;
		}
	}

	public String deleteProduct(String name) {
		String product = name;
		boolean success = dataLayer.deleteProduct(product);
		if (success) {
			return "Product " + name + " updated";
		} else {
			return "Failed to update Product " + name;
		}
	}

	public Product searchProduct(String name) {
		Product found = dataLayer.searchProduct(name);
		return found;
	}

	public TableModel getCustomers() {
		TableModel model = dataLayer.getCustomers();
		return model;
	}

	public String addCustomer(String first, String second, int loyalty) {
		Customer customer = new Customer(first, second, loyalty);
		boolean success = dataLayer.addCustomer(customer);
		if (success) {
			return "Customer " + first + " " + second + " updated";
		} else {
			return "Failed to update Customer " + first + " " + second;
		}
	}

	public TableModel getLowStock() {
		TableModel model = dataLayer.getLowStock();
		return model;
	}

	public TableModel getNoStock() {
		TableModel model = dataLayer.getNoStock();
		return model;
	}

	public String addSale(int customerID, String product, int quantity,
			int saleValue) {
		Sale newSale = new Sale(customerID, product, quantity, saleValue);
		boolean success = dataLayer.addSale(newSale);
		if (success) {
			return "Sale of " + product + " added";
		} else {
			return "Failed to add sale of  " + product;
		}
	}

	public TableModel getSales() {
		TableModel newModel = dataLayer.getSales();
		return newModel;
	}

	public String editCustomer(int customerID, int points) {
		boolean success = dataLayer.editCustomer(customerID, points);
		if(success) {
			return "Customer loyalty points updated";
		} else {
			return "Failed to update customer loyalty points";
		}
	}
}
