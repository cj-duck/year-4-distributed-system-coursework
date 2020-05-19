import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class DESServer {
	public DESServer(){}
	public static void main(String args[]) {
		try {
			DEStore des = new DEStore();
			DEStore.start();
			DES stub = (DES) des;
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("DES", stub);
			System.out.println("DEStore ready");
		} catch (Exception e) {
			System.out.println("DEStore: exception:");
			e.printStackTrace();
		}
	}
}
