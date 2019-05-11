import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Color;

class Player extends GameObject{
	private int speed;

	Player(){
		this.x = 480;
        this.y = 600;
        this.width = 90;
        this.height = 10;
        this.speed = 30;
	}

	int getx(){
		return this.x;
	}

	int gety(){
		return this.y;
	}

	void move(int keycode){
		switch (keycode) {
            case KeyEvent.VK_LEFT :
                if(x <= speed - 1){
                    break;
                }else{
                    x -= speed;
                    break;
                }
            case KeyEvent.VK_RIGHT :
                if(x >= 1050 - width - speed + 1){
                    break;
                }else{
                    x += speed;
                    break;
                }
        }	
	}

	void levelUp(){
		this.speed += 10;
	}

	void draw(Graphics g){
		if(this.isAlive()){
			g.setColor(Color.black);
			g.fillRect(x, y, width, height);
		}
	}

	void init(){
		this.x = 480;
        this.y = 600;
        this.speed = 30;
	}
}