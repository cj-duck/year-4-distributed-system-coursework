import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class SSServer {
	public SSServer(){}
	public static void main(String args[]) {
		try {
			SalesSystem ss = new SalesSystem();
			SalesSystem.start();
			SS stub = (SS) ss;
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("SS", stub);
			System.out.println("Sales system ready");
		} catch (Exception e) {
			System.out.println("Sales sysem: exception:");
			e.printStackTrace();
		}
	}
}