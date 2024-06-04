import raytracer.Scene;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Serveur {
	public static void main(String[] args) throws RemoteException {
		DispatcherRaytracer dispatcher = new DispatcherRaytracer(new Scene("C:\\Users\\mathe\\Desktop\\etude\\BUT\\2A\\Programmation_repartie\\calcul_paralelle\\resources\\simple.txt", 512, 512), 4);
		Dispatcher dispatcherExported = (Dispatcher) UnicastRemoteObject.exportObject(dispatcher, 0);
		Registry registry = LocateRegistry.getRegistry();
		registry.rebind("dispatcher", dispatcherExported);
		System.out.println("Dispatcher bound");
	}
}
