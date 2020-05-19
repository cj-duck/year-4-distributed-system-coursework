
public class Product {
	private String name;
	private int price;
	private int quantity;
	private String promo;
	
	public Product(String name, int price, int quantity, String promo)
	{
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.promo = promo;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getPromo() {
		return promo;
	}
	public void setPromo(String promo) {
		this.promo = promo;
	}
}
