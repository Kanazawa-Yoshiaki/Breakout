import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;

class Title{
	Title(){

	}

	public void draw(Graphics g){
		g.setColor(Color.black);
		g.drawString("PRESS SPACE", 560, 500);
        Graphics2D g2 = (Graphics2D)g;
        Font font = new Font("Arial", Font.BOLD, 24);
        g2.setFont(font);
		g2.drawString("BREAKOUT", 540, 300);
	}
}