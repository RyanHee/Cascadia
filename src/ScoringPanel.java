import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.*;

//fix animation part of code
public class ScoringPanel extends JPanel{
	private BufferedImage testPanel;
	private JPanel p;//added end panel
	private int prog;
	//private Game game;
	public ScoringPanel(JPanel panel/*Game game*/) {
		p = panel;//added end panel
		prog = 0;
		try {
			testPanel = ImageIO.read(new File("img/test cascadia.png"));
		}
		catch(Exception e) {
			
		}
	}
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(testPanel, 0, 0, getWidth(), getHeight(), null);
		g.setFont(new Font("Arial", Font.PLAIN, 13));
		int cnt = 0;
		int i = 0;
		while(prog>=(cnt*4+i)) {
			//System.out.println("stuff"+prog+"yeet"+cnt+"grr"+i);
			if(cnt >= 0 && cnt < 5) {//each player, each animal score
				g.drawString("AScore", getWidth()*(33+32*i)/256, getHeight()*(2+cnt)/15);
			}
			else if(cnt == 5) {//each player, total animal score
				g.drawString("Animal Score", getWidth()*(33+32*i)/256, getHeight()*(2+cnt)/15);
			}
			else if(cnt >= 6 && cnt < 11) {//each player, each habitat score
				g.drawString("HScore", getWidth()*(27+32*i)/256, getHeight()*(57+8*(cnt-6))/104);
			}
			else if(cnt >= 11 && cnt < 16) {//each player, habitat bonus score
				g.drawString("HBonus", getWidth()*(43+32*i)/256, getHeight()*(57+8*(cnt-11))/104);
			}
			else if(cnt == 16){//each player, total habitat score
				g.drawString("Habitat Score",  getWidth()*(27+32*i)/256, getHeight()*(58+8*(cnt-11))/104);
			}
			else if(cnt >= 17 && cnt < 18) {//nature token
				g.drawString("Final", getWidth()*(195+12*i)/256, getHeight()*(8+4*(cnt-17))/30);
			}
			else {//final score
				g.drawString("Final", getWidth()*(195+12*i)/256, getHeight()*(8+4*(cnt-18))/30);
			}
			i++;
			if(i == 4) {
				i = 0;
				cnt++;
			}
		}
		prog++;
		if(prog<73) {
			wait(200);
			repaint();
		}
	}
	
	public static void wait(int x){
        try{
            Thread.sleep(x);
        }  
        catch (Exception E){

        }
    }
}
