import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CISServer {
	public CISServer(){}
	public static void main(String args[]) {
		try {
			CentralInventorySystem cis = new CentralInventorySystem();
			CentralInventorySystem.start();
			CIS stub = (CIS) cis;
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("CIS", stub);
			System.out.println("CentralInventorySystem ready");
			
		} catch (Exception e) {
			System.out.println("CentralInventorySystem: exception:");
			e.printStackTrace();
		}
	}
}
