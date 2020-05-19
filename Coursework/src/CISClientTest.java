import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CISClientTest {
	public static void main(String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry(null); 
			CIS stub = (CIS) registry.lookup("CIS");
			stub.sendOrder("Blue Paint", 20);
			
		} catch (Exception e) {
			System.out.println("AccountClient: exception:");
			e.printStackTrace();
		}
	}
}