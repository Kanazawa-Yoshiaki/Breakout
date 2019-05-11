import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Color;

class Ball extends GameObject{
	private int dx;
	private int dy;
	private int life;
	private int speed, pspeed;

	Ball(){
		this.x = 520;
        this.y = 590;
        this.width = 10;
        this.height = 10;
        this.dx = -1;
        this.dy = -1;
        this.life = 2;
        this.speed = 5;
        this.pspeed = 30;
	}

	int getx(){
		return x;
	}

	int gety(){
		return y;
	}

	int getdx(){
		return dx;
	}

	int getdy(){
		return dy;
	}

	void setdx(int x){
		this.dx = x;
	}

	void setdy(int y){
		this.dy = y;
	}

	void update(int keycode){
		if(!Window.ballflag){
			switch (keycode) {
            	case KeyEvent.VK_LEFT :
              		if(x <= 40 + pspeed - 1){
                	    break;
                	}else{
                	    x -= pspeed;
                	    break;
                	}
            	case KeyEvent.VK_RIGHT :
            	    if(x >= 1050 - 45 - pspeed + 1){
            	        break;
            	    }else{
            	        x += pspeed;
            	        break;
            	    }
        	}	
		}else{
			if(this.x <= 0 - speed + 1 && this.y >= 0){
				this.dx *= -1;
			}else if(this.y <= 0){
				this.dy *= -1;
			}else if(this.x >= 1050 - width && this.y >= 0){
				this.dx *= -1;
			}else if(this.y >= 700){
				this.delete();
				Window.ballflag = false;
				this.life -= 1;
			}else if(this.x <= 0 - speed + 1 && this.y <= 0){
				this.dx *= -1;
				this.dy *= -1;
			}else if(this.x >= 1050 - width && this.y <= 0){
				this.dx *= -1;
				this.dy *= -1;
			}

        	if(this.dx == 1){
        		this.x += speed;
        	}
        	if(this.dx == -1){
        		this.x -= speed;
        	}
        	if(this.dy == 1){
        		this.y += speed;
        	}
        	if(this.dy == -1){
        		this.y -= speed;
        	}
    	}
    }

    void init(int x, int y){
    	this.x = x + 40;
        this.y = y - 10;
        this.dx = -1;
        this.dy = -1;
    }

    void alive(){
    	this.isAlive = true;
    }

    int getLife(){
    	return life;
    }

    void setLife(int life){
    	this.life = life;
    }

    void levelUp(){
    	this.speed += 5;
    	this.pspeed += 10;
    }

    void reset(){
    	this.speed = 5;
    	this.pspeed = 30;
    }

	void draw(Graphics g){
		if(this.isAlive()){
			g.setColor(Color.black);
			g.fillOval(x, y, width, height);
		}

		g.drawString("life : " + life, 1080, 600);
	}
}