import raytracer.Image;
import raytracer.Scene;

import java.io.Serializable;

public class Distant implements Serializable {
	public Image calculate(Scene scene, int x0, int y0, int w, int h) {
		return scene.compute(x0, y0, w, h);
	}
}
