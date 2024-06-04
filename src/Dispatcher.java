import raytracer.Image;
import raytracer.Scene;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public interface Dispatcher extends Remote {
	void register(Distant d) throws RemoteException;
	void setImage(Image img, int x, int y) throws RemoteException;
	int[] requestPart() throws RemoteException;
	Scene getScene() throws RemoteException;
}
