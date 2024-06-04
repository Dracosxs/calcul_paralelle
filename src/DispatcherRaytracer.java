import raytracer.Disp;
import raytracer.Image;
import raytracer.Scene;

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;

public class DispatcherRaytracer implements Dispatcher {
	Disp disp;
	Scene scene;
	ArrayList<Distant> distants = new ArrayList<>();
	ArrayList<int[]> parts = new ArrayList<>();

	public DispatcherRaytracer(Scene scene, int nbParts) {
		int size = 2048;
		disp = new Disp("Raytracer", size, size);
		this.scene = scene;
		int w = size / nbParts;
		int h = size / nbParts;
		for (int i = 0; i < nbParts; i++) {
			for (int j = 0; j < nbParts; j++) {
				parts.add(new int[]{i * w, j * h, w, h});
			}
		}
	}

	@Override
	public void register(Distant d) throws RemoteException {
		distants.add(d);
	}

	@Override
	public synchronized void setImage(Image img, int x, int y) throws RemoteException {
		disp.setImage(img, x, y);
	}

	@Override
	public synchronized int[] requestPart() throws RemoteException {
		try {
			System.out.println("Sending part to " + RemoteServer.getClientHost());
		} catch (ServerNotActiveException ignored) {
		}

		if (parts.isEmpty()) return null;

		return parts.remove(0);
	}

	@Override
	public Scene getScene() throws RemoteException {
		return this.scene;
	}
}
