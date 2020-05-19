import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DES extends Remote {
	void receiveOrder(String product, int quantity) throws RemoteException;

	void receiveSale(int saleCustomer, String saleProduct, int saleQuantity)
			throws RemoteException;
}
