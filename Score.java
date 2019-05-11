import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;

class Score{
	private int score;

	Score(){
	}

	void setScore(int score){
		this.score = score;
	}

	int getScore(){
		return score;
	}

	void upScore(int score){
		this.score += score;
	}

	void draw(Graphics g){
		g.setColor(Color.black);
		g.drawString("score : " + this.score, 1080, 150);
	}
}