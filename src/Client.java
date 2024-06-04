import raytracer.Image;
import raytracer.Scene;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
	public static void main(String[] args) throws RemoteException, NotBoundException {
		String ip = "localhost";
		try {
			ip = args[0];
		} catch (Exception e) {
			System.out.println("No IP provided, using localhost");
		}

		Registry registry = LocateRegistry.getRegistry(ip);
		Dispatcher dispatcher = (Dispatcher) registry.lookup("dispatcher");
		Distant distant = new Distant();
		dispatcher.register(distant);

		int[] parts = dispatcher.requestPart();
		Scene scene = dispatcher.getScene();
		while (parts != null) {
			System.out.println("Calculating part " + parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[3]);
			Image image = distant.calculate(scene, parts[0], parts[1], parts[2], parts[3]);
			dispatcher.setImage(image, parts[0], parts[1]);
			parts = dispatcher.requestPart();
		}
	}
}
