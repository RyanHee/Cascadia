import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

//fix animation part of code
public class ScoringPanel extends JPanel{
	private BufferedImage testPanel;
	private JPanel p;//added end panel
	private int prog;
	private static Game game;
	private HashMap<Integer, String> inttostring;
	public ScoringPanel(JPanel panel) {
		p = panel;//added end panel
		prog = 0;
		game = null;
		inttostring =new HashMap<>();
		inttostring.put(0, "B");
		inttostring.put(1, "E");
		inttostring.put(2, "S");
		inttostring.put(3, "H");
		inttostring.put(4, "F");
		inttostring.put(6, "M");
		inttostring.put(7, "F");
		inttostring.put(8, "D");
		inttostring.put(9, "S");
		inttostring.put(10, "L");
		inttostring.put(11, "M");
		inttostring.put(12, "F");
		inttostring.put(13, "D");
		inttostring.put(14, "S");
		inttostring.put(15, "L");

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
		//add logic to get everyone's scores
		int cnt = 0;
		int i = 0;
		while(prog>=(cnt*game.getPlayerNum()+i)) {
			Player p = game.getPlayerList()[i];
			HashMap<String, Integer>animalmp=p.getAnimalmp();
			HashMap<String, Integer>landmp=p.getLandmp();
			HashMap<String, Integer>bonusmp=p.getBonusmp();
			if(cnt >= 0 && cnt < 5) {//each player, each animal score
				g.drawString(String.valueOf(animalmp.get(inttostring.get(cnt))), getWidth()*(33+32*i)/256, getHeight()*(2+cnt)/15);
			}
			else if(cnt == 5) {//each player, total animal score
				g.drawString(String.valueOf(p.getAnimalScore()), getWidth()*(33+32*i)/256, getHeight()*(2+cnt)/15);
			}
			else if(cnt >= 6 && cnt < 11) {//each player, each habitat score
				g.drawString(landmp.get(inttostring.get(cnt)).toString(), getWidth()*(27+32*i)/256, getHeight()*(57+8*(cnt-6))/104);
			}
			else if(cnt >= 11 && cnt < 16) {//each player, habitat bonus score
				g.drawString(bonusmp.get(inttostring.get(cnt)).toString(), getWidth()*(43+32*i)/256, getHeight()*(57+8*(cnt-11))/104);
			}
			else if(cnt == 16){//each player, total habitat score
				g.drawString(String.valueOf(p.getLandScore()),  getWidth()*(27+32*i)/256, getHeight()*(58+8*(cnt-11))/104);
			}
			else if(cnt == 17) {//nature token
				g.drawString(String.valueOf(p.getNumTokens()), getWidth()*(195+12*i)/256, getHeight()*(8+4*(cnt-17))/30);
			}
			else if (cnt == 18){//final score
				g.drawString(String.valueOf(p.getScore()), getWidth()*(195+12*i)/256, getHeight()*(8+4*(cnt-17))/30);
			}
			i++;
			if(i == game.getPlayerNum() /*number of players in game*/) {
				i = 0;
				cnt++;
			}
		}
		prog++;
		if(prog<77 /*19*number of players in game +1*/) { // 19 rows*4players = 76
			wait(200);
			repaint();
		}
	}
	
	public void wait(int x){
        try{
            Thread.sleep(x);
        }  
        catch (Exception E){

        }
    }
	
	public static void updateGame(Game g) {
		game = g;
	}
}
