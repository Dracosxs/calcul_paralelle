import raytracer.Image;
import raytracer.Scene;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Dispatcher extends Remote {
	void setImage(Image img, int[] part) throws RemoteException;
	int[] requestPart() throws RemoteException;
	Scene getScene() throws RemoteException;
}
