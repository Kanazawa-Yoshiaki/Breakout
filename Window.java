import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

class Window extends Canvas implements Runnable, KeyListener{
	Image imgBuf;
	Graphics gBuf;
	Title title;
	Player player;
	ArrayList<Block> blocks;
	Ball ball;
	Score score;
	ArrayList<Item> items;
	GameClear gameClear;
	GameOver gameOver;
	int cnt = 10;
	static boolean ballflag;
	int highscore = 0;
	int level = 1;
	Random rand = new Random();

	/* 時間計測用 */
	int count = 0;

	/* アイテム用フラグ */
	int drawflag = 0;

	/* キー判定用 */
	boolean keyshot;
	int keycode;

	/* シーン切り替え用 */
	int scene;
	static final int SCENE_TITLE = 0;
	static final int SCENE_GAMEMAIN = 1;
	static final int SCENE_GAMECLEAR = 2;
	static final int SCENE_GAMEOVER = 3;

	/* アイテム出現率（分母は０〜９までの１０） */
	static final int ITEM_RATE = 1; 

	Window(){
		addKeyListener(this);
		setFocusable(true);	
		setBackground(Color.white);

		title = new Title();
		player = new Player();
		blocks = new ArrayList<Block>();
		ball = new Ball();
		items = new ArrayList<Item>();
		for(int i=0; i<cnt; i++) {
            blocks.add(new Block(20+i*(1050/cnt), 20));
            blocks.add(new Block(20+i*(1050/cnt), 50));
        }
        score = new Score();
        gameClear = new GameClear();
        gameOver = new GameOver();
	}

	public void init(){
		scene = SCENE_TITLE;
		keyshot = false;
		ballflag = false;
		score.setScore(0);
		player.init();
		ball.init(480, 600);
		ball.setLife(2);
		for(Block block: blocks){  
            block.alive(); 
        }
        level = 1;
        ball.reset();
        for(Item item: items){
        	item.delete();
        }
	}

	public void initThread()
	{
		Thread thread = new Thread(this);
		thread.start();
	}

	public void paint(Graphics g){
		g.drawImage(imgBuf, 0, 0, this);
	}

	public void update(Graphics g){
		paint(g);
	}

	public void run(){
		while(true){
			imgBuf = createImage(1200, 700);
			gBuf = imgBuf.getGraphics();

			switch(scene){
				case 0:
					title.draw(gBuf);
					if(keyshot == true){
						scene = SCENE_GAMEMAIN;
					}
					break;
				case 1:
					gameMain();
					break;
				case 2:
					gameClear.draw(gBuf);
					if(highscore < score.getScore()){
						highscore = score.getScore();
					}
					if(keyshot == true){
						init();
					}
					break;
				case 3:
					gameOver.draw(gBuf);
					if(highscore < score.getScore()){
						highscore = score.getScore();
					}
					if(keyshot == true){
						init();
					}
					break;
			}

			repaint();
			count++;

			try{
				Thread.sleep(20);				
			}catch(InterruptedException e){

			}
		}
	}

	public void gameMain(){
		int num; 

		player.draw(gBuf);

		if(!ball.isAlive()){
			ball.init(player.getx(), player.gety());
			ball.alive();
		}
		ball.draw(gBuf);

		if(ballflag == true){
			ball.update(0);
		}

		for(Block block: blocks){  
            block.draw(gBuf); 
        }
		
		gBuf.setColor(Color.black);
        for(int i = 0; i <700 ; i++){
            gBuf.drawString("|",1050, i);
        }

        for(Block block: blocks){
        	if(block.collideWith(ball) && block.isAlive()){
        		score.upScore(10);
        		block.delete();
        		if(rand.nextInt(10) <= ITEM_RATE){
	        		items.add(block.fall());
	        	}

        		if(ball.getdx() == -1 && ball.getdy() == -1){
        			ball.setdy(1);
        		}else if(ball.getdx() == 1 && ball.getdy() == 1){
        			ball.setdy(-1);
        		}else if(ball.getdx() == 1 && ball.getdy() == -1){
        			ball.setdy(1);
        		}else if(ball.getdx() == -1 && ball.getdy() == 1){
        			ball.setdy(-1);
        		}
        	}
        }

        for(Item item: items){
        	item.draw(gBuf);
        	item.update();

        	num = rand.nextInt(10);
        	gBuf.setColor(Color.red);

        	if(player.collideWith(item)){
        		item.delete();
        		if(num <= 4){
        			score.upScore(50);
        			count = 0;
        			drawflag = 1;
        		}else if(num >= 5){
        			ball.setLife(ball.getLife()+1);
        			count = 0;
        			drawflag = 2;
        		}
        	}

        	if(item.gety() >= 700){
        		item.delete();
        	}
        }

        if(drawflag == 1 && count < 50){
        	gBuf.drawString("SCORE +50", 540, 300);
        }

        if(drawflag == 2 && count < 50){
        	gBuf.drawString("LIFE +1", 540, 300);
        }

        if(ball.getdx() == -1){
        	if(ball.collideWith(player)){
        		ball.setdy(-1);
        	}
        }
        if(ball.getdx() == 1){
        	if(ball.collideWith(player)){
        		ball.setdy(-1);
        	}
        }

        gBuf.setColor(Color.black);
        gBuf.drawString("highscore : " + highscore, 1080, 100);
        score.draw(gBuf);
        gBuf.drawString("level : " + level, 1080, 200);

        if(!blockCheck() && level == 5){
        	scene = SCENE_GAMECLEAR;
        }

        if(!blockCheck() && level != 5){
        	level++;
        	for(Block block: blocks){  
            	block.alive(); 
        	}	
        	ball.levelUp();
        	player.levelUp();
        }

        if(ball.getLife() == 0){
        	scene = SCENE_GAMEOVER;
        }
	}
	
	public boolean blockCheck(){
		for(Block block: blocks){
			if(block.isAlive()){
				return true;
			}
		}

		return false;
	}

	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		player.move(keycode);
		ball.update(keycode);

		if (keycode == KeyEvent.VK_SPACE)
		{
			keyshot = true;
			if(scene == SCENE_GAMEMAIN){
				ballflag = true;
			}
		}
		
		if (keycode == KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
		}
	}

	public void keyReleased(KeyEvent e) {
	 	int keycode = e.getKeyCode();

		if (keycode == KeyEvent.VK_SPACE)
		{
			keyshot = false;
		}
	}

	public void keyTyped(KeyEvent e){
  	}
}