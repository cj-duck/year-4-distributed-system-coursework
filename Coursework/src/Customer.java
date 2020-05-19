
public class Customer {
	private String first;
	private String second;
	private int loyalty;
	
	public Customer(String first, String second, int loyalty)
	{
		this.setFirst(first);
		this.setSecond(second);
		this.setLoyalty(loyalty);
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
	}

	public int getLoyalty() {
		return loyalty;
	}

	public void setLoyalty(int loyalty) {
		this.loyalty = loyalty;
	}
}
