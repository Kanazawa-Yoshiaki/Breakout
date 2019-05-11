import java.awt.*;
import java.awt.event.*;

class Breakout extends Frame{
	public static void main(String args[]){
		new Breakout();
	}

	Breakout(){
		super("BREAKOUT");

		addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });
		
		setSize(1200, 700);

		Window win = new Window();
		add(win);
		setVisible(true);
		win.init();
		win.initThread();
	}
}