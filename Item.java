import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Color;

class Item extends GameObject{
	Item(int x, int y){
		this.x = x;
        this.y = y;
        this.width = 10;
        this.height = 10;
	}

	void update(){
		this.y += 2;
	}

	int gety(){
		return this.y;
	}

	void draw(Graphics g){
		if(this.isAlive()){
			g.setColor(Color.red);
			g.fillOval(x, y, width, height);
		}
	}
}