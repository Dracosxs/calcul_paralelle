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

		Scene scene = dispatcher.getScene();
		/*
		while (parts != null) {
			System.out.println("Calculating part " + parts[0] + " " + parts[1] + " " + parts[2] + " " + parts[3]);
			Image image = distant.calculate(scene, parts[0], parts[1], parts[2], parts[3]);
			dispatcher.setImage(image, parts[0], parts[1]);
			parts = dispatcher.requestPart();
		}
		*/
		// do this with threads
		Thread[] threads = new Thread[4];
		for (int i = 0; i < 4; i++) {
			threads[i] = new Thread(() -> {
				int[] part;
				try {
					part = dispatcher.requestPart();
				} catch (RemoteException e) {
					throw new RuntimeException(e);
				}
				while (part != null) {
					System.out.println("Calculating part " + part[0] + " " + part[1] + " " + part[2] + " " + part[3]);
					Image image = distant.calculate(scene, part[0], part[1], part[2], part[3]);
					try {
						dispatcher.setImage(image, part[0], part[1]);
					} catch (RemoteException e) {
						System.out.println("Error while sending image");
					}
					try {
						part = dispatcher.requestPart();
					} catch (RemoteException e) {
						System.out.println("Error while requesting part");
					}
				}
			});
			threads[i].start();
		}
	}
}
