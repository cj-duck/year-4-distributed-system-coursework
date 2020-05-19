
public class Sale {
	private int customerID;
	private String product;
	private int quantity;
	private int value;
	
	public Sale(int customerID, String product, int quantity, int value)
	{
		this.setCustomerID(customerID);
		this.setProduct(product);
		this.setQuantity(quantity);
		this.setValue(value);
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
