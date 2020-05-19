import javax.swing.table.TableModel;

public interface StoreDataLayerInterface {

	public boolean addProduct(Product product);

	public TableModel getProducts();

	public boolean editProduct(Product product);

	public boolean deleteProduct(String product);

	public Product searchProduct(String name);

	public TableModel getCustomers();

	public boolean addCustomer(Customer customer);

	public TableModel getLowStock();

	public TableModel getNoStock();

	public boolean addSale(Sale newSale);

	TableModel getSales();

	public boolean editCustomer(int customerID, int points);
}
