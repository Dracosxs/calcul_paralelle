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
	ArrayList<int[]> parts = new ArrayList<>();

	public DispatcherRaytracer(Scene scene, int nbParts) {
		int size = 512;
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
	public synchronized void setImage(Image img, int[] part) throws RemoteException {
		for (int[] currentPart : parts) {
			if (currentPart[0] == part[0] && currentPart[1] == part[1]) {
				parts.remove(currentPart);
				break;
			}
		}
		disp.setImage(img, part[0], part[1]);
	}

	@Override
	public synchronized int[] requestPart() throws RemoteException {
		if (parts.isEmpty()) return null;

		try {
			System.out.println("Sending part to " + RemoteServer.getClientHost());
		} catch (ServerNotActiveException ignored) {
		}

		int[] part = parts.remove(0);
		parts.add(part);
		return part;
	}

	@Override
	public Scene getScene() throws RemoteException {
		return this.scene;
	}
}
