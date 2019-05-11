import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Color;

class Block extends GameObject{
	Block(int x, int y){
		this.x = x;
        this.y = y;
        this.width = 60;
        this.height = 10;
	}

	void draw(Graphics g){
		if(this.isAlive()){
			g.setColor(Color.black);
			g.fillRect(x, y, width, height);
		}
	}

	void alive(){
    	this.isAlive = true;
    }

    public Item fall() {
        return new Item(this.x + 30, this.y);
    }
}