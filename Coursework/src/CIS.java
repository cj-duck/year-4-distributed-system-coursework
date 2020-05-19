import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CIS extends Remote {
	void sendOrder(String product, int quantity) throws RemoteException;
}
