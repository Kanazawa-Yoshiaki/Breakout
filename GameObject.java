import java.awt.*;
import java.awt.event.*;

class GameObject{
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	public boolean isAlive = true;

	GameObject(){
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void delete() {
		this.isAlive = false;
	}

	public boolean collideWith(GameObject object) {
		if(!object.isAlive()){ 
			return false; 
		}

		return x < object.x + object.width && object.x < x + width &&
y < object.y + object.height && object.y < y + height;
	}
}